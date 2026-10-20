
package taskflow;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;

// Tests développés avec l'aide de l'intelligence artificielle.
public class TestSauvegardeEcheance {

    public static void main(String[] args) throws Exception {

        Path fichier = Files.createTempFile("test-echeance-", ".tsv");
        Path fichierInvalide = Files.createTempFile("test-invalide-", ".tsv");

        try {
            GestionnaireTaches gestionnaire = new GestionnaireTaches();
            SauvegardeTaches sauvegarde = new SauvegardeTaches(fichier);

            // TEST 1 : Sauvegarder et charger une echeance
            ModeleTache tache = gestionnaire.creerTache(
                    "Faire le projet",
                    ModeleTache.Priorite.NORMALE,
                    LocalDate.of(2026, 11, 30)
            );

            sauvegarde.sauvegarder(gestionnaire);

            GestionnaireTaches charge = new GestionnaireTaches();
            sauvegarde.charger(charge);

            if (!LocalDate.of(2026, 11, 30).equals(
                    charge.getTaches().get(0).getEcheance())) {
                throw new AssertionError("Erreur test 1");
            }

            System.out.println("Test 1 reussi : echeance sauvegardee et chargee");

            // TEST 2 : Modifier une echeance
            gestionnaire.modifierEcheanceTache(
                    tache.getId(),
                    LocalDate.of(2026, 12, 15)
            );

            sauvegarde.sauvegarder(gestionnaire);

            charge = new GestionnaireTaches();
            sauvegarde.charger(charge);

            if (!LocalDate.of(2026, 12, 15).equals(
                    charge.getTaches().get(0).getEcheance())) {
                throw new AssertionError("Erreur test 2");
            }

            System.out.println("Test 2 reussi : echeance modifiee");

            // TEST 3 : Supprimer une echeance
            gestionnaire.modifierEcheanceTache(tache.getId(), null);

            sauvegarde.sauvegarder(gestionnaire);

            charge = new GestionnaireTaches();
            sauvegarde.charger(charge);

            if (charge.getTaches().get(0).getEcheance() != null) {
                throw new AssertionError("Erreur test 3");
            }

            System.out.println("Test 3 reussi : echeance supprimee");

            // TEST 4 : Verifier l'identifiant conserve
            if (charge.getTaches().get(0).getId() != tache.getId()) {
                throw new AssertionError("Erreur test 4");
            }

            System.out.println("Test 4 reussi : identifiant conserve");

            // TEST 5 : Nouvel identifiant apres rechargement
            ModeleTache nouvelleTache = charge.creerTache(
                    "Nouvelle tache apres chargement"
            );

            if (nouvelleTache.getId() != tache.getId() + 1) {
                throw new AssertionError(
                        "Erreur test 5 : prochain identifiant incorrect");
            }

            System.out.println(
                    "Test 5 reussi : nouvel identifiant correct apres chargement");

            // TEST 6 : Verifier le message d'erreur
            Files.write(
                    fichierInvalide,
                    "ENTETE_INVALIDE\n".getBytes(StandardCharsets.UTF_8)
            );

            GestionnaireTaches gestionnaireTest = new GestionnaireTaches();
            SauvegardeTaches sauvegardeInvalide =
                    new SauvegardeTaches(fichierInvalide);

            boolean erreurDetectee = false;

            try {
                sauvegardeInvalide.charger(gestionnaireTest);
            } catch (IOException e) {
                erreurDetectee = true;

                if (e.getMessage() == null
                        || !e.getMessage().contains("en-tête")) {
                    throw new AssertionError(
                            "Erreur test 6 : message d'erreur incorrect");
                }
            }

            if (!erreurDetectee) {
                throw new AssertionError(
                        "Erreur test 6 : aucune erreur detectee");
            }

            System.out.println(
                    "Test 6 reussi : message d'erreur verifie");

            // TEST 7 : Conserver les taches si le chargement echoue
            GestionnaireTaches gestionnaireExistant =
                    new GestionnaireTaches();

            ModeleTache tacheExistante =
                    gestionnaireExistant.creerTache("Tache importante");

            try {
                sauvegardeInvalide.charger(gestionnaireExistant);
                throw new AssertionError(
                        "Erreur test 7 : chargement invalide accepte");
            } catch (IOException e) {
                // Erreur attendue
            }

            if (gestionnaireExistant.getTaches().size() != 1
                    || gestionnaireExistant.getTaches().get(0).getId()
                    != tacheExistante.getId()) {
                throw new AssertionError(
                        "Erreur test 7 : tache existante perdue");
            }

            System.out.println(
                    "Test 7 reussi : taches conservees apres erreur");

            System.out.println("Tous les tests ont reussi : 7/7");

        } finally {
            Files.deleteIfExists(fichier);
            Files.deleteIfExists(fichierInvalide);
        }
    }
}
