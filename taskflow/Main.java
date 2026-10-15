
package taskflow;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        GestionnaireTaches gestionnaire = new GestionnaireTaches();

        Path cheminFichier = Paths.get("data", "taches.txt");
        SauvegardeTaches sauvegarde =
                new SauvegardeTaches(cheminFichier);

        // Charger les tâches avant d'afficher le menu.
        try {
            sauvegarde.charger(gestionnaire);

            System.out.println("Chargement des tâches terminé.");
            System.out.println("Nombre de tâches chargées : "
                    + gestionnaire.getTaches().size());

        } catch (IOException e) {
            System.err.println(
                    "ERREUR : impossible de charger les données."
            );
            System.err.println("Fichier : "
                    + cheminFichier.toAbsolutePath());
            System.err.println("Détail : " + e.getMessage());
            return;
        }

        boolean continuer = true;

        while (continuer) {
            afficherMenu();

            int choix = lireEntier("Votre choix : ");

            try {
                switch (choix) {
                    case 1:
                        ajouterTache(gestionnaire);
                        break;
                    case 2:
                        gestionnaire.afficherTaches();
                        break;
                    case 3:
                        marquerTerminee(gestionnaire);
                        break;
                    case 4:
                        modifierPriorite(gestionnaire);
                        break;
                    case 5:
                        modifierEcheance(gestionnaire);
                        break;
                    case 6:
                        sauvegarderDonnees(sauvegarde, gestionnaire);
                        break;
                    case 0:
                        System.out.println(
                                "Au revoir ! Pensez à sauvegarder "
                                + "vos modifications avant de quitter."
                        );
                        continuer = false;
                        break;
                    default:
                        System.out.println("Choix invalide.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Erreur : " + e.getMessage());
            }

            System.out.println();
        }

        scanner.close();
    }

    private static void afficherMenu() {
        System.out.println("========== TASKFLOW ==========");
        System.out.println("1. Ajouter une tâche");
        System.out.println("2. Afficher les tâches");
        System.out.println("3. Marquer une tâche comme terminée");
        System.out.println("4. Modifier la priorité d'une tâche");
        System.out.println("5. Gérer l'échéance d'une tâche");
        System.out.println("6. Sauvegarder les données");
        System.out.println("0. Quitter");
    }

    private static void ajouterTache(
            GestionnaireTaches gestionnaire) {

        System.out.println("--- Ajouter une tâche ---");

        String titre;

        do {
            System.out.print("Titre de la tâche : ");
            titre = scanner.nextLine().trim();

            if (titre.isEmpty()) {
                System.out.println("Le titre ne peut pas être vide.");
            }
        } while (titre.isEmpty());

        ModeleTache.Priorite priorite = lirePriorite();
        LocalDate echeance = lireEcheance();

        ModeleTache tache =
                gestionnaire.creerTache(titre, priorite, echeance);

        System.out.println("Tâche créée. ID : " + tache.getId());
    }

    private static void marquerTerminee(
            GestionnaireTaches gestionnaire) {

        int id = lireId("ID de la tâche à terminer : ");

        if (gestionnaire.marquerTacheTerminee(id)) {
            System.out.println("Tâche marquée comme terminée.");
        } else {
            System.out.println("Aucune tâche ne possède cet ID.");
        }
    }

    private static void modifierPriorite(
            GestionnaireTaches gestionnaire) {

        int id = lireId("ID de la tâche : ");
        ModeleTache.Priorite priorite = lirePriorite();

        if (gestionnaire.modifierPrioriteTache(id, priorite)) {
            System.out.println("Priorité modifiée avec succès.");
        } else {
            System.out.println("Aucune tâche ne possède cet ID.");
        }
    }

    private static void modifierEcheance(
            GestionnaireTaches gestionnaire) {

        int id = lireId("ID de la tâche : ");
        LocalDate echeance = lireEcheance();

        if (gestionnaire.modifierEcheanceTache(id, echeance)) {
            System.out.println("Échéance modifiée avec succès.");
        } else {
            System.out.println("Aucune tâche ne possède cet ID.");
        }
    }

    private static void sauvegarderDonnees(
            SauvegardeTaches sauvegarde,
            GestionnaireTaches gestionnaire) {

        try {
            sauvegarde.sauvegarder(gestionnaire);
            System.out.println("Sauvegarde effectuée avec succès.");
        } catch (IOException e) {
            System.out.println("Échec de la sauvegarde : "
                    + e.getMessage());
        }
    }

    private static int lireId(String message) {
        int id;

        do {
            id = lireEntier(message);

            if (id <= 0) {
                System.out.println(
                        "L'identifiant doit être positif."
                );
            }
        } while (id <= 0);

        return id;
    }

    private static int lireEntier(String message) {
        while (true) {
            System.out.print(message);
            String saisie = scanner.nextLine().trim();

            try {
                return Integer.parseInt(saisie);
            } catch (NumberFormatException e) {
                System.out.println(
                        "Veuillez saisir un nombre entier valide."
                );
            }
        }
    }

    private static ModeleTache.Priorite lirePriorite() {
        while (true) {
            System.out.println("Choisissez la priorité :");
            System.out.println("1. Basse");
            System.out.println("2. Normale");
            System.out.println("3. Élevée");

            int choix = lireEntier("Votre choix : ");

            switch (choix) {
                case 1:
                    return ModeleTache.Priorite.BASSE;
                case 2:
                    return ModeleTache.Priorite.NORMALE;
                case 3:
                    return ModeleTache.Priorite.ELEVEE;
                default:
                    System.out.println("Choisissez 1, 2 ou 3.");
            }
        }
    }

    private static LocalDate lireEcheance() {
        while (true) {
            System.out.print(
                    "Échéance (AAAA-MM-JJ) ou - pour aucune : "
            );

            String saisie = scanner.nextLine().trim();

            if (saisie.equals("-")) {
                return null;
            }

            try {
                return LocalDate.parse(saisie);
            } catch (DateTimeParseException e) {
                System.out.println(
                        "Date invalide. Exemple : 2026-12-31."
                );
            }
        }
    }
}
