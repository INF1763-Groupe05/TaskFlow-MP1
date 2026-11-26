package taskflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Arrays;

// Tests développés avec l'aide de l'intelligence artificielle.

public class TestSauvegarde {
    private static int reussis = 0;
    private static Path dossier;

    public static void main(String[] args) throws Exception {
        dossier = Files.createTempDirectory("taskflow-tests-");
        try {
            test("sauvegarde et rechargement de tous les champs", () -> {
                Path fichier = dossier.resolve("complet.tsv");
                GestionnaireTaches g = new GestionnaireTaches();
                ModeleTache t = g.creerTache("Vérifier le serveur", ModeleTache.Priorite.ELEVEE,
                        LocalDate.of(2026, 11, 30));
                t.setStatut(ModeleTache.Statut.TERMINEE);
                new SauvegardeTaches(fichier).sauvegarder(g);
                GestionnaireTaches relu = new GestionnaireTaches();
                new SauvegardeTaches(fichier).charger(relu);
                ModeleTache r = relu.getTaches().get(0);
                verifier(r.getId() == t.getId(), "Identifiant perdu");
                verifier(r.getTitre().equals(t.getTitre()), "Titre perdu");
                verifier(r.getPriorite() == t.getPriorite(), "Priorité perdue");
                verifier(r.getEcheance().equals(t.getEcheance()), "Échéance perdue");
                verifier(r.getStatut() == t.getStatut(), "Statut perdu");
            });
            test("accents, emojis, séparateurs, retours de ligne conservés", () -> {
                Path fichier = dossier.resolve("special.tsv");
                String titre = "Été; tab\tligne\nsuivante | ☕ 😀";
                GestionnaireTaches g = new GestionnaireTaches();
                g.creerTache(titre);
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                stockage.sauvegarder(g);
                GestionnaireTaches charge = new GestionnaireTaches();
                stockage.charger(charge);
                verifier(charge.getTaches().get(0).getTitre().equals(titre), "Titre altéré");
            });
            test("absence d'échéance", () -> {
                Path fichier = dossier.resolve("sans-date.tsv");
                GestionnaireTaches g = new GestionnaireTaches();
                g.creerTache("Sans date");
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                stockage.sauvegarder(g);
                GestionnaireTaches charge = new GestionnaireTaches();
                stockage.charger(charge);
                verifier(charge.getTaches().get(0).getEcheance() == null, "Date devrait être absente");
            });
            test("identifiants uniques même après rechargement", () -> {
                Path fichier = dossier.resolve("ids.tsv");
                GestionnaireTaches g = new GestionnaireTaches();
                g.ajouterTache(new ModeleTache(12, "Ancienne", ModeleTache.Priorite.NORMALE));
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                stockage.sauvegarder(g);
                GestionnaireTaches charge = new GestionnaireTaches();
                stockage.charger(charge);
                verifier(charge.creerTache("Nouvelle").getId() == 13, "Collision après chargement");
            });
            test("fichier absent = liste vide", () -> {
                GestionnaireTaches g = new GestionnaireTaches();
                g.creerTache("Ancienne");
                new SauvegardeTaches(dossier.resolve("inexistant.tsv")).charger(g);
                verifier(g.getTaches().isEmpty(), "Une liste vide était attendue");
            });
            test("fichier vide rejeté sans modifier ni fichier ni mémoire", () -> {
                Path fichier = dossier.resolve("vide.tsv");
                Files.write(fichier, new byte[0]);
                verifierChargementInvalide(fichier);
            });
            test("en-tête erroné rejeté", () -> {
                Path fichier = dossier.resolve("entete.tsv");
                Files.write(fichier, "INVALIDE\n".getBytes(StandardCharsets.UTF_8));
                verifierChargementInvalide(fichier);
            });
            test("date impossible rejetée", () -> {
                Path fichier = dossier.resolve("date.tsv");
                ecrireBrut(fichier, "TASKFLOW_V1\n1\tVGl0cmU=\tNORMALE\t2026-02-30\tA_FAIRE\n");
                verifierChargementInvalide(fichier);
            });
            test("statut invalide rejeté", () -> {
                Path fichier = dossier.resolve("statut.tsv");
                ecrireBrut(fichier, "TASKFLOW_V1\n1\tVGl0cmU=\tNORMALE\t-\tEN_COURS\n");
                verifierChargementInvalide(fichier);
            });
            test("identifiants dupliqués rejetés", () -> {
                Path fichier = dossier.resolve("dup.tsv");
                ecrireBrut(fichier, "TASKFLOW_V1\n2\tVGl0cmU=\tNORMALE\t-\tA_FAIRE\n"
                        + "2\tVGl0cmUy\tBASSE\t-\tA_FAIRE\n");
                verifierChargementInvalide(fichier);
            });
            test("colonnes manquantes rejetées", () -> {
                Path fichier = dossier.resolve("colonnes.tsv");
                ecrireBrut(fichier, "TASKFLOW_V1\n1\tVGl0cmU=\tNORMALE\t-\n");
                verifierChargementInvalide(fichier);
            });
            test("titre base64 invalide rejeté", () -> {
                Path fichier = dossier.resolve("base64.tsv");
                ecrireBrut(fichier, "TASKFLOW_V1\n1\t@INVALID@\tNORMALE\t-\tA_FAIRE\n");
                verifierChargementInvalide(fichier);
            });
            test("échec d'écriture signalé par IOException", () -> {
                Path cibleDossier = Files.createDirectory(dossier.resolve("destination-dossier"));
                GestionnaireTaches g = new GestionnaireTaches();
                g.creerTache("Valide");
                attendreIOException(() -> new SauvegardeTaches(cibleDossier).sauvegarder(g));
            });
            test("tâche devenue invalide : sauvegarde refusée", () -> {
                Path fichier = dossier.resolve("donnees-invalides.tsv");
                GestionnaireTaches g = new GestionnaireTaches();
                ModeleTache t = g.creerTache("À sauvegarder");
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                stockage.sauvegarder(g);
                byte[] precedent = Files.readAllBytes(fichier);
                t.setTitre("  ");
                attendreIOException(() -> stockage.sauvegarder(g));
                verifier(Arrays.equals(precedent, Files.readAllBytes(fichier)),
                        "La sauvegarde existante ne doit pas être remplacée par des données invalides");
            });
            test("une deuxième sauvegarde remplace correctement l'ancienne", () -> {
                Path fichier = dossier.resolve("deux-sauvegardes.tsv");
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                GestionnaireTaches g = new GestionnaireTaches();
                g.creerTache("Première");
                stockage.sauvegarder(g);
                g.creerTache("Deuxième");
                stockage.sauvegarder(g);
                GestionnaireTaches charge = new GestionnaireTaches();
                stockage.charger(charge);
                verifier(charge.getTaches().size() == 2, "Deux tâches attendues");
            });
            test("sauvegarder puis relire une liste vide", () -> {
                Path fichier = dossier.resolve("liste-vide.tsv");
                SauvegardeTaches stockage = new SauvegardeTaches(fichier);
                stockage.sauvegarder(new GestionnaireTaches());
                GestionnaireTaches charge = new GestionnaireTaches();
                stockage.charger(charge);
                verifier(charge.getTaches().isEmpty(), "Liste vide attendue");
            });
        } finally {
            try (java.util.stream.Stream<Path> fichiers = Files.walk(dossier)) {
                for (Path p : (Iterable<Path>) fichiers.sorted(java.util.Comparator.reverseOrder())::iterator) {
                    Files.deleteIfExists(p);
                }
            }
        }
        System.out.println("Tests sauvegarde réussis : " + reussis + "/" + reussis);
    }

    private static void verifierChargementInvalide(Path fichier) throws Exception {
        byte[] avant = Files.readAllBytes(fichier);
        GestionnaireTaches g = new GestionnaireTaches();
        g.creerTache("Préexistante");
        attendreIOException(() -> new SauvegardeTaches(fichier).charger(g));
        verifier(Arrays.equals(avant, Files.readAllBytes(fichier)), "Fichier altéré");
        verifier(g.getTaches().size() == 1, "La liste mémoire a été modifiée");
        verifier(g.getTaches().get(0).getTitre().equals("Préexistante"), "Tâche perdue");
    }
    private static void ecrireBrut(Path fichier, String contenu) throws IOException {
        Files.write(fichier, contenu.getBytes(StandardCharsets.UTF_8));
    }
    private interface Action { void executer() throws Exception; }
    private static void test(String nom, Action action) throws Exception {
        action.executer();
        reussis++;
        System.out.println("OK : " + nom);
    }
    private static void attendreIOException(Action action) throws Exception {
        try {
            action.executer();
        } catch (IOException ex) {
            return;
        }
        throw new AssertionError("Une IOException était attendue.");
    }
    private static void verifier(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
