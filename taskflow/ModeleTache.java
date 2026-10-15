package taskflow;

import java.time.LocalDate;

public class ModeleTache {

    public enum Priorite {
        BASSE,
        NORMALE,
        ELEVEE
    }

    public enum Statut {
        A_FAIRE,
        TERMINEE
    }

    private int id;
    private String titre;
    private Priorite priorite;
    private LocalDate echeance;
    private Statut statut;

    public ModeleTache(
            int id,
            String titre,
            Priorite priorite,
            LocalDate echeance,
            Statut statut) {

        this.id = id;
        this.titre = titre;
        this.priorite = priorite;
        this.echeance = echeance;
        this.statut = statut;
    }

    // Constructeur pour la création d'une nouvelle tâche
    public ModeleTache(
            int id,
            String titre,
            Priorite priorite) {

        this.id = id;
        this.titre = titre;
        this.priorite = priorite;
        this.echeance = null;
        this.statut = Statut.A_FAIRE;
    }

    public int getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public LocalDate getEcheance() {
        return echeance;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }
}
