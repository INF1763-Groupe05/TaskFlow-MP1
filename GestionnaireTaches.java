
package taskflow;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

// Gère les tâches et garantit des identifiants positifs et uniques (Issue #11).
public class GestionnaireTaches {
    private List<ModeleTache> taches = new ArrayList<>();
    private int prochainId = 1;

    public GestionnaireTaches() { }

    /**
     * Création normale : l'appelant n'a PAS à choisir l'identifiant
     * Une priorité omise => NORMALE
     */
    public ModeleTache creerTache(String titre, ModeleTache.Priorite priorite, LocalDate echeance) {
        if (prochainId == 0) {
            throw new IllegalStateException("Aucun identifiant disponible : limite atteinte.");
        }
        ModeleTache tache = new ModeleTache(
                prochainId,
                titre,
                priorite == null ? ModeleTache.Priorite.NORMALE : priorite,
                echeance,
                ModeleTache.Statut.A_FAIRE);
        ajouterTache(tache);
        return tache;
    }

    public ModeleTache creerTache(String titre) {
        return creerTache(titre, null, null);
    }

    /**
     *  ajouterTache() creerTache() remplacerTaches ont été développées avec l'aide de l'intelligence artificielle.
     */
    public void ajouterTache(ModeleTache tache) {
        verifierTache(tache);
        for (ModeleTache existante : taches) {
            if (existante.getId() == tache.getId()) {
                throw new IllegalArgumentException("Identifiant déjà utilisé : " + tache.getId());
            }
        }
        taches.add(tache);
        if (prochainId != 0 && tache.getId() >= prochainId) {
            prochainId = suivant(tache.getId());
        }
    }

    public List<ModeleTache> getTaches() {
        return Collections.unmodifiableList(taches);
    }

    // Remplace les tâches uniquement après validation complète du lot chargé.
    public void remplacerTaches(List<ModeleTache> nouvellesTaches) {
        if (nouvellesTaches == null) {
            throw new IllegalArgumentException("Liste de tâches absente.");
        }
        Set<Integer> identifiants = new HashSet<>();
        int max = 0;
        for (ModeleTache tache : nouvellesTaches) {
            verifierTache(tache);
            if (!identifiants.add(tache.getId())) {
                throw new IllegalArgumentException("Identifiant en double : " + tache.getId());
            }
            max = Math.max(max, tache.getId());
        }
        taches.clear();
        taches.addAll(nouvellesTaches);
        prochainId = suivant(max);
    }

    private static int suivant(int id) {
        return id == Integer.MAX_VALUE ? 0 : id + 1;
    }

    private static void verifierTache(ModeleTache tache) {
        if (tache == null) {
            throw new IllegalArgumentException("La tâche ne peut pas être nulle.");
        }
        if (tache.getId() <= 0) {
            throw new IllegalArgumentException("L'identifiant doit être entier et positif.");
        }
        if (tache.getTitre() == null || tache.getTitre().trim().isEmpty()) {
            throw new IllegalArgumentException("Le titre ne peut pas être vide.");
        }
        if (tache.getPriorite() == null || tache.getStatut() == null) {
            throw new IllegalArgumentException("La priorité et le statut sont obligatoires.");
        }
    }

    // Conserve l'affichage de la classe existante, avec les libellés de l'énoncé.
    public void afficherTaches() {
        if (taches.isEmpty()) {
            System.out.println("Aucune tâche n'est présente.");
            return;
        }
        System.out.println("===== LISTE DES TÂCHES =====");
        for (ModeleTache tache : taches) {
            System.out.println("ID : " + tache.getId());
            System.out.println("Titre : " + tache.getTitre());
            System.out.println("Priorité : " + afficherPriorite(tache.getPriorite()));
            System.out.println("Échéance : " + (tache.getEcheance() == null ? "Aucune" : tache.getEcheance()));
            System.out.println("Statut : " + afficherStatut(tache.getStatut()));
            System.out.println("----------------------------");
        }
    }

    private String afficherPriorite(ModeleTache.Priorite priorite) {
        switch (priorite) {
            case BASSE: return "Basse";
            case NORMALE: return "Normale";
            case ELEVEE: return "Élevée";
            default: throw new IllegalStateException("Priorité inconnue.");
        }
    }

    private String afficherStatut(ModeleTache.Statut statut) {
        switch (statut) {
            case A_FAIRE: return "À faire";
            case TERMINEE: return "Terminée";
            default: throw new IllegalStateException("Statut inconnu.");
        }
    }
}
