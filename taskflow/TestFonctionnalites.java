
package taskflow;

import java.time.LocalDate;

// Tests développés avec l'aide de l'intelligence artificielle.
public class TestFonctionnalites {

    private static int reussis = 0;

    public static void main(String[] args) throws Exception {

        // TEST 1 : Modifier la priorite d'une tache
        test("modifier la priorite", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Examen");

            boolean resultat = g.modifierPrioriteTache(
                    t.getId(), ModeleTache.Priorite.ELEVEE);

            verifier(resultat, "La modification devrait reussir");
            verifier(t.getPriorite() == ModeleTache.Priorite.ELEVEE,
                    "La priorite devrait etre ELEVEE");
        });

        // TEST 2 : Refuser une priorite null
        test("priorite null refusee", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Projet");

            boolean erreur = false;

            try {
                g.modifierPrioriteTache(t.getId(), null);
            } catch (IllegalArgumentException e) {
                erreur = true;
            }

            verifier(erreur, "Une priorite null doit etre refusee");
            verifier(t.getPriorite() == ModeleTache.Priorite.NORMALE,
                    "La priorite initiale doit etre conservee");
        });

        // TEST 3 : Identifiant inexistant pour la priorite
        test("priorite identifiant inexistant", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            g.creerTache("Devoir");

            boolean resultat = g.modifierPrioriteTache(
                    999, ModeleTache.Priorite.BASSE);

            verifier(!resultat,
                    "Une tache inexistante doit retourner false");
        });

        // TEST 4 : Marquer une tache terminee
        test("terminer une tache", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Rapport");

            boolean resultat = g.marquerTacheTerminee(t.getId());

            verifier(resultat, "La tache devrait etre trouvee");
            verifier(t.getStatut() == ModeleTache.Statut.TERMINEE,
                    "La tache devrait etre terminee");
        });

        // TEST 5 : Terminer une tache sans modifier les autres
        test("terminer une seule tache", () -> {
            GestionnaireTaches g = new GestionnaireTaches();

            ModeleTache t1 = g.creerTache("Devoir 1");
            ModeleTache t2 = g.creerTache("Devoir 2");

            g.marquerTacheTerminee(t1.getId());

            verifier(t1.getStatut() == ModeleTache.Statut.TERMINEE,
                    "La premiere tache devrait etre terminee");

            verifier(t2.getStatut() == ModeleTache.Statut.A_FAIRE,
                    "La deuxieme tache doit rester A_FAIRE");
        });

        // TEST 6 : Terminer une tache deja terminee
        test("tache deja terminee", () -> {
            GestionnaireTaches g = new GestionnaireTaches();
            ModeleTache t = g.creerTache("Presentation");

            g.marquerTacheTerminee(t.getId());
            g.marquerTacheTerminee(t.getId());

            verifier(t.getStatut() == ModeleTache.Statut.TERMINEE,
                    "La tache doit rester TERMINEE");
        });

        // TEST 7 : Terminer une tache inexistante
        test("terminer une tache inexistante", () -> {
            GestionnaireTaches g = new GestionnaireTaches();

            boolean resultat = g.marquerTacheTerminee(999);

            verifier(!resultat,
                    "Une tache inexistante doit retourner false");
        });

        // TEST 8 : Modifier la priorite sans changer les autres champs
        test("priorite sans modifier les autres champs", () -> {
            GestionnaireTaches g = new GestionnaireTaches();

            LocalDate date = LocalDate.of(2026, 11, 30);

            ModeleTache t = g.creerTache(
                    "Projet final",
                    ModeleTache.Priorite.NORMALE,
                    date);

            int id = t.getId();
            String titre = t.getTitre();
            ModeleTache.Statut statut = t.getStatut();

            g.modifierPrioriteTache(
                    t.getId(), ModeleTache.Priorite.ELEVEE);

            verifier(t.getPriorite() == ModeleTache.Priorite.ELEVEE,
                    "La priorite devrait etre modifiee");

            verifier(t.getId() == id,
                    "L'identifiant ne doit pas changer");

            verifier(t.getTitre().equals(titre),
                    "Le titre ne doit pas changer");

            verifier(t.getStatut() == statut,
                    "Le statut ne doit pas changer");

            verifier(date.equals(t.getEcheance()),
                    "L'echeance ne doit pas changer");
        });

        System.out.println(
                "Tests fonctionnalites reussis : "
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

    private static void verifier(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
