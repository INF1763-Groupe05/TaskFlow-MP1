package taskflow;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.CodingErrorAction;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

/*
  * L'intelligence artificielle a été utilisée
 * dans la conception et à l'implémentation
 * des méthodes de SauvegardeTaches.
 */



public class SauvegardeTaches {

    private static final String ENTETE = "TASKFLOW_V1";
    private final Path chemin;

    public SauvegardeTaches(Path chemin) {
        if (chemin == null) {
            throw new IllegalArgumentException("Le chemin du fichier est obligatoire.");
        }
        this.chemin = chemin;
    }

    /* Écriture explicite : n'annonce le succès que si l'appel retourne normalement. */
    public void sauvegarder(GestionnaireTaches gestionnaire) throws IOException {
        if (gestionnaire == null) {
            throw new IllegalArgumentException("Le gestionnaire est obligatoire.");
        }
        Path destination = chemin.toAbsolutePath();
        Path dossier = destination.getParent();
        Files.createDirectories(dossier);
        Path temporaire = Files.createTempFile(dossier, "taskflow-", ".tmp");
        try {
            try (BufferedWriter writer = Files.newBufferedWriter(
                    temporaire, StandardCharsets.UTF_8, StandardOpenOption.TRUNCATE_EXISTING)) {
                writer.write(ENTETE);
                writer.newLine();
                for (ModeleTache tache : gestionnaire.getTaches()) {
                    if (tache == null || tache.getId() <= 0 || tache.getTitre() == null
                            || tache.getTitre().trim().isEmpty() || tache.getPriorite() == null
                            || tache.getStatut() == null) {
                        throw new IOException("Impossible de sauvegarder une tâche invalide.");
                    }
                    String titreEncode = Base64.getEncoder().encodeToString(
                            tache.getTitre().getBytes(StandardCharsets.UTF_8));
                    String date = tache.getEcheance() == null ? "-" : tache.getEcheance().toString();
                    writer.write(tache.getId() + "\t" + titreEncode + "\t"
                            + tache.getPriorite().name() + "\t" + date + "\t"
                            + tache.getStatut().name());
                    writer.newLine();
                }
            }
            try {
                Files.move(temporaire, destination,
                        StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException ex) {
                Files.move(temporaire, destination, StandardCopyOption.REPLACE_EXISTING);
            }
        } finally {
            Files.deleteIfExists(temporaire);
        }
    }

    /*
     * chargement automatique à invoquer au démarrage.
     * fichier absent : liste vide. Fichier invalide : IOException, état mémoire intact.
     */
    public void charger(GestionnaireTaches gestionnaire) throws IOException {
        if (gestionnaire == null) {
            throw new IllegalArgumentException("Le gestionnaire est obligatoire.");
        }
        if (!Files.exists(chemin)) {
            gestionnaire.remplacerTaches(new ArrayList<>());
            return;
        }
        List<ModeleTache> chargees = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(chemin, StandardCharsets.UTF_8)) {
            String entete = reader.readLine();
            if (!ENTETE.equals(entete)) {
                throw new IOException("Fichier mal formé : en-tête TASKFLOW_V1 absent.");
            }
            String ligne;
            int numeroLigne = 1;
            while ((ligne = reader.readLine()) != null) {
                numeroLigne++;
                try {
                    chargees.add(lireTache(ligne));
                } catch (IllegalArgumentException | CharacterCodingException ex) {
                    throw new IOException("Fichier mal formé à la ligne " + numeroLigne
                            + " : " + ex.getMessage(), ex);
                }
            }
        }
        try {
            gestionnaire.remplacerTaches(chargees);
        } catch (IllegalArgumentException ex) {
            throw new IOException("Fichier de tâches invalide : " + ex.getMessage(), ex);
        }
    }

    private static ModeleTache lireTache(String ligne) throws CharacterCodingException {
        String[] champs = ligne.split("\t", -1);
        if (champs.length != 5) {
            throw new IllegalArgumentException("cinq champs attendus.");
        }
        int id = Integer.parseInt(champs[0]);
        byte[] titreBinaire = Base64.getDecoder().decode(champs[1]);
        String titre = StandardCharsets.UTF_8.newDecoder()
                .onMalformedInput(CodingErrorAction.REPORT)
                .onUnmappableCharacter(CodingErrorAction.REPORT)
                .decode(ByteBuffer.wrap(titreBinaire)).toString();
        ModeleTache.Priorite priorite = ModeleTache.Priorite.valueOf(champs[2]);
        LocalDate echeance;
        try {
            echeance = "-".equals(champs[3]) ? null : LocalDate.parse(champs[3]);
        } catch (DateTimeParseException ex) {
            throw new IllegalArgumentException("échéance incorrecte : " + champs[3], ex);
        }
        ModeleTache.Statut statut = ModeleTache.Statut.valueOf(champs[4]);
        return new ModeleTache(id, titre, priorite, echeance, statut);
    }
}





