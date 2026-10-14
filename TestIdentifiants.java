package taskflow;

import java.time.LocalDate;
import java.util.Arrays;

// Tests développés avec l'aide de l'intelligence artificielle.
public class TestIdentifiants {
    private static int reussis = 0;

    public static void main(String[] args) throws Exception {
        test("identifiant positif et automatique", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            verifier(g.creerTache("Tâche A").getId() == 1, "Premier ID attendu : 1");
            verifier(g.creerTache("Tâche B").getId() == 2, "Deuxième ID attendu : 2");
        });
        test("même titre, IDs différents", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            verifier(g.creerTache("Même titre").getId() != g.creerTache("Même titre").getId(),
                    "Les identifiants doivent être différents");
        });
        test("ajout existant puis génération suivante", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            g.ajouterTache(new ModeleTache(42, "Déjà présente", ModeleTache.Priorite.NORMALE));
            verifier(g.creerTache("Nouvelle").getId() == 43, "ID 43 attendu");
        });
        test("refus d'un ID dupliqué", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            g.creerTache("Une tâche");
            attendreErreur(IllegalArgumentException.class, () ->
                    g.ajouterTache(new ModeleTache(1, "Une autre", ModeleTache.Priorite.BASSE)));
            verifier(g.getTaches().size() == 1, "Le doublon n'a pas été ajouté");
        });
        test("refus ID nul ou négatif", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            attendreErreur(IllegalArgumentException.class, () ->
                    g.ajouterTache(new ModeleTache(0, "Invalide", ModeleTache.Priorite.NORMALE)));
            attendreErreur(IllegalArgumentException.class, () ->
                    g.ajouterTache(new ModeleTache(-1, "Invalide", ModeleTache.Priorite.NORMALE)));
        });
        test("chargement conserve les IDs et évite les collisions", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            g.remplacerTaches(Arrays.asList(
                    new ModeleTache(9, "Première", ModeleTache.Priorite.ELEVEE,
                            LocalDate.of(2026, 12, 1), ModeleTache.Statut.TERMINEE),
                    new ModeleTache(3, "Deuxième", ModeleTache.Priorite.NORMALE)));
            verifier(g.getTaches().get(0).getId() == 9, "L'ID 9 doit être inchangé");
            verifier(g.getTaches().get(1).getId() == 3, "L'ID 3 doit être inchangé");
            verifier(g.creerTache("Après rechargement").getId() == 10, "ID 10 attendu");
        });
        test("lot de rechargement avec doublon refusé sans altérer la liste", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            g.creerTache("Préexistante");
            attendreErreur(IllegalArgumentException.class, () ->
                    g.remplacerTaches(Arrays.asList(
                            new ModeleTache(5, "A", ModeleTache.Priorite.NORMALE),
                            new ModeleTache(5, "B", ModeleTache.Priorite.NORMALE))));
            verifier(g.getTaches().size() == 1, "La liste doit rester intacte");
        });
        test("titre vide refusé dans la création normale", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            attendreErreur(IllegalArgumentException.class, () -> g.creerTache("   "));
            verifier(g.creerTache("Valide").getId() == 1, "Aucun ID ne doit être perdu");
        });
        test("priorité par défaut, statut initial et échéance absente", () -> {
            ModeleTache t = new GestionnaireTaches().creerTache("Défaut");
            verifier(t.getPriorite() == ModeleTache.Priorite.NORMALE, "NORMALE attendue");
            verifier(t.getStatut() == ModeleTache.Statut.A_FAIRE, "A_FAIRE attendu");
            verifier(t.getEcheance() == null, "Échéance absente attendue");
        });
        System.out.println("Tests identifiants réussis : " + reussis + "/" + reussis);
    }

    private interface Action { void executer() throws Exception; }
    private static void test(String nom, Action action) throws Exception {
        action.executer();
        reussis++;
        System.out.println("OK : " + nom);
    }
    private static void verifier(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
    private static void attendreErreur(Class<? extends Throwable> type, Action action) throws Exception {
        try {
            action.executer();
        } catch (Throwable ex) {
            if (type.isInstance(ex)) return;
            throw new AssertionError("Mauvais type d'erreur : " + ex, ex);
        }
        throw new AssertionError("Erreur attendue : " + type.getSimpleName());
    }
}
