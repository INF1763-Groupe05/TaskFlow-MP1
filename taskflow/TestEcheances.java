
package taskflow;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

// Tests développés avec l'aide de l'intelligence artificielle.
public class TestEcheances {

    private static int reussis = 0;

    public static void main(String[] args) throws Exception {

        // TEST 1 : Ajouter une echeance
        test("ajouter une echeance", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Devoir",
                    ModeleTache.Priorite.NORMALE, null);

            LocalDate date = LocalDate.of(2026, 11, 30);

            verifier(g.modifierEcheanceTache(t.getId(), date),
                    "La modification devrait reussir");

            verifier(date.equals(t.getEcheance()),
                    "L'echeance devrait etre enregistree");
        });

        // TEST 2 : Modifier une echeance existante
        test("modifier une echeance existante", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Examen",
                    ModeleTache.Priorite.ELEVEE,
                    LocalDate.of(2026, 11, 1));

            LocalDate nouvelleDate = LocalDate.of(2026, 12, 1);

            verifier(g.modifierEcheanceTache(t.getId(), nouvelleDate),
                    "La modification devrait reussir");

            verifier(nouvelleDate.equals(t.getEcheance()),
                    "La nouvelle date devrait remplacer l'ancienne");
        });

        // TEST 3 : Supprimer une echeance
        test("supprimer une echeance", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Projet",
                    ModeleTache.Priorite.BASSE,
                    LocalDate.of(2026, 11, 1));

            verifier(g.modifierEcheanceTache(t.getId(), null),
                    "La suppression devrait reussir");

            verifier(t.getEcheance() == null,
                    "L'echeance devrait etre absente");
        });

        // TEST 4 : Identifiant inconnu
        test("identifiant inconnu", () -> {
            GestionnaireTaches g = new GestionnaireTaches();

            verifier(!g.modifierEcheanceTache(
                    999, LocalDate.of(2026, 11, 30)),
                    "Une tache inexistante devrait retourner false");
        });

        // TEST 5 : Identifiant invalide
        test("identifiant invalide", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            boolean erreur = false;

            try {
                g.modifierEcheanceTache(0, null);
            } catch (IllegalArgumentException ex) {
                erreur = true;
            }

            verifier(erreur,
                    "Un identifiant nul devrait etre refuse");
        });

        // TEST 6 : Date impossible
        test("date impossible refusee", () -> {
            boolean erreur = false;

            try {
                LocalDate.parse("2026-02-30");
            } catch (DateTimeParseException e) {
                erreur = true;
            }

            verifier(erreur,
                    "Une date impossible doit etre refusee");
        });

        // TEST 7 : Date passee acceptee
        test("date passee acceptee", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Ancienne tache");

            LocalDate date = LocalDate.of(2020, 1, 15);

            verifier(g.modifierEcheanceTache(t.getId(), date),
                    "Une date passee doit etre acceptee");

            verifier(date.equals(t.getEcheance()),
                    "La date passee doit etre conservee");
        });

        System.out.println(
                "Tests echeances reussis : "
                + reussis + "/" + reussis);
    }

    private interface Action {
        void executer() throws Exception;
    }

    private static void test(String nom, Action action)
            throws Exception {

        action.executer();
        reussis++;

        System.out.println("OK : " + nom);
    }

    private static void verifier(boolean condition,
                                 String message) {

        if (!condition) {
            throw new AssertionError(message);
        }
    }
}

