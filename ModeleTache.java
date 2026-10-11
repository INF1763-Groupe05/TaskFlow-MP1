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
        setTitre(titre);
        setPriorite(priorite);
        this.echeance = echeance;
        setStatut(statut);
    }

    // Constructeur pour la création d'une nouvelle tâche
    public ModeleTache(
            int id,
            String titre,
            Priorite priorite) {

        this.id = id;
        setTitre(titre);
        setPriorite(priorite);
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
        if (titre == null || titre.isBlank()) {
            throw new IllegalArgumentException(
                    "Le titre ne doit pas être vide.");
        }
        this.titre = titre;
    }

    public void setPriorite(Priorite priorite) {
        if (priorite == null) {
            throw new IllegalArgumentException(
                    "La priorité doit être BASSE, NORMALE ou ELEVEE.");
        }
        this.priorite = priorite;
    }

    public void setEcheance(LocalDate echeance) {
        this.echeance = echeance;
    }

    public void setStatut(Statut statut) {
        if (statut == null) {
            throw new IllegalArgumentException(
                    "Le statut doit être A_FAIRE ou TERMINEE.");
        }
        this.statut = statut;
    }


    // Valide une date au format AAAA-MM-JJ.
    public static LocalDate validerDate(String texte) {
        if (texte == null
                || !texte.matches("[0-9]{4}-[0-9]{2}-[0-9]{2}")) {
            throw new IllegalArgumentException(
                    "La date doit respecter le format AAAA-MM-JJ.");
        }

        try {
            return LocalDate.parse(texte);
        } catch (java.time.format.DateTimeParseException e) {
            throw new IllegalArgumentException(
                    "La date indiquée n'existe pas.");
        }
     }
    }