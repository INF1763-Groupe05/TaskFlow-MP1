package taskflow;
import taskflow.ModeleTache;
import java.util.ArrayList;
import java.util.List;

public class GestionnaireTaches {

    private List<ModeleTache> taches;

    public GestionnaireTaches() {
        taches = new ArrayList<>();
    }

    //Ajoute une tâche à la liste.

    public void ajouterTache(ModeleTache tache) {
        taches.add(tache);
    }

    // Ma partie : marquer une tâche comme terminée.
    public boolean terminerTache(int id) {
        for (ModeleTache tache : taches) {
             if (tache.getId() == id) {
                tache.setStatut(ModeleTache.Statut.TERMINEE);
                return true;
            }
        }
        return false;
    }
    // Modifie la priorité d'une tâche grâce à son identifiant.
    public boolean modifierPriorite(
            int id, ModeleTache.Priorite nouvellePriorite) {

        if (nouvellePriorite == null) {
            return false;
        }

        for (ModeleTache tache : taches) {
            if (tache.getId() == id) {
                tache.setPriorite(nouvellePriorite);
                return true;
            }
        }

        return false;
    }
   
     //Retourne la liste des tâches.

     
     
    public List<ModeleTache> getTaches() {
        return taches;
    }

   
     //Affiche toutes les tâches.
    
    public void afficherTaches() {

        if (taches.isEmpty()) {
            System.out.println();
            System.out.println("Aucune tâche n'est présente.");
            return;
        }

        System.out.println();
        System.out.println("===== LISTE DES TÂCHES =====");

        for (ModeleTache tache : taches) {

            System.out.println();
            System.out.println("ID : " + tache.getId());
            System.out.println("Titre : " + tache.getTitre());

            System.out.println(
                    "Priorité : "
                    + afficherPriorite(tache.getPriorite())
            );

            if (tache.getEcheance() == null) {
                System.out.println("Échéance : Aucune");
            } else {
                System.out.println(
                        "Échéance : " + tache.getEcheance()
                );
            }

            System.out.println(
                    "Statut : "
                    + afficherStatut(tache.getStatut())
            );

            System.out.println("----------------------------");
        }
    }

    private String afficherPriorite(
            ModeleTache.Priorite priorite) {

        switch (priorite) {
            case BASSE:
                return "Faible";

            case NORMALE:
                return "Moyenne";

            case ELEVEE:
                return "Élevée";

            default:
                return "Pas de priorité";
        }
    }

    private String afficherStatut(
            ModeleTache.Statut statut) {

        switch (statut) {
            case A_FAIRE:
                return "À faire";

            case TERMINEE:
                return "Terminée";

            default:
                return "Pas de statut";
        }
    }
}
