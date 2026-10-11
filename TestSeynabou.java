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

        gestionnaire.afficherTaches();
    }
}