package taskflow;

public class TestSeynabou {
    public static void main(String[] args) {
        GestionnaireTaches gestionnaire = new GestionnaireTaches();

        ModeleTache tache1 = new ModeleTache(
                1, "Préparer le rapport", ModeleTache.Priorite.ELEVEE);

        ModeleTache tache2 = new ModeleTache(
                2,
                "Mettre à jour le guide utilisateur",
                ModeleTache.Priorite.NORMALE);
        String[] titresInvalides = {"", "   ", null};

        for (String titre : titresInvalides) {
                try {
                        new ModeleTache(3, titre, ModeleTache.Priorite.BASSE);
                        throw new AssertionError("Un titre invalide a été accepté !");
                } catch (IllegalArgumentException e) {
                        System.out.println("Titre invalide refusé : " + e.getMessage());
        }
        try {
                new ModeleTache(4, "Tester la priorité", null);
                throw new AssertionError("Une priorité null a été acceptée !");
        } catch (IllegalArgumentException e) {
                System.out.println("Priorité invalide refusée : " + e.getMessage());
        }

        }

        try {
                new ModeleTache(
                        5, "Tester le statut",
                        ModeleTache.Priorite.NORMALE,
                        null, null);

                throw new AssertionError("Un statut null a été accepté !");
        } catch (IllegalArgumentException e) {
                System.out.println("Statut invalide refusé : " + e.getMessage());
         }

         System.out.println("Date valide : "
                  + ModeleTache.validerDate("2026-10-30"));

        String[] datesInvalides = {"30/10/2026", "2026-02-30"};

        for (String date : datesInvalides) {
                try {
                        ModeleTache.validerDate(date);
                        throw new AssertionError("Une date invalide a été acceptée !");
                } catch (IllegalArgumentException e) {
                        System.out.println("Date refusée : " + date);
                }
        }
        gestionnaire.ajouterTache(tache1);
        gestionnaire.ajouterTache(tache2);

        System.out.println("Terminer la tâche 1 : "
                + gestionnaire.terminerTache(1));

        System.out.println("Terminer encore la tâche 1 : "
                + gestionnaire.terminerTache(1));

        System.out.println("Terminer une tâche inexistante : "
                + gestionnaire.terminerTache(999));
        

        System.out.println("Changer la priorité de la tâche 2 : "
                 + gestionnaire.modifierPriorite(
                        2, ModeleTache.Priorite.ELEVEE));

        System.out.println("Priorité invalide : "
                + gestionnaire.modifierPriorite(2, null));

        System.out.println("Identifiant inexistant : "
                + gestionnaire.modifierPriorite(
                         999, ModeleTache.Priorite.BASSE));
                gestionnaire.afficherTaches();
    }
}