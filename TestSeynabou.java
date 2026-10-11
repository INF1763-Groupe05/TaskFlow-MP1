package taskflow;

public class TestSeynabou {
    public static void main(String[] args) {
        GestionnaireTaches gestionnaire = new GestionnaireTaches();

        ModeleTache tache1 = new ModeleTache(
                1, "", ModeleTache.Priorite.ELEVEE);

        ModeleTache tache2 = new ModeleTache(
                2,
                "Mettre à jour le guide utilisateur",
                ModeleTache.Priorite.NORMALE);

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