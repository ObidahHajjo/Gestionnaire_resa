package fr.bts.sio.DAO;

// Importations nécessaires pour le modèle et les interactions avec la base de données
import fr.bts.sio.Models.StatutReservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe `StatutReservationDAO` : Objet d'accès aux données
 * Cette classe gère toutes les opérations entre la table `statut_reservation`
 * dans la base de données et l'application Java.
 */
public class StatutReservationDAO {

    // Attribut pour stocker la connexion à la base de données
    private Connection connection;

    /**
     * Constructeur : Reçoit une connexion SQL établie d'une autre classe.
     *
     * @param connection La connexion établie avec la base de données
     */
    public StatutReservationDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * Méthode pour mettre à jour les informations d'une réservation spécifique.
     *
     * @param statutReservation L'objet StatutReservation contenant les nouvelles valeurs
     * @throws SQLException En cas d'erreur SQL
     */
    public void modifierStatutReservation(StatutReservation statutReservation) throws SQLException {
        String sql = "UPDATE statut_reservation SET id_statut=?, libelle=? WHERE id_statut =? ";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Remplissage des paramètres de la requête
            stmt.setString(1, statutReservation.getLibelle()); // Définit le libellé
            stmt.setInt(2, statutReservation.getIdStatut());  // Définit l'ID du statut
            stmt.setInt(3, statutReservation.getIdStatut());  // Condition WHERE (id_statut)

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Gestion et journalisation des erreurs SQL
            System.err.println("Erreur lors de la mise à jour de statut : " + e.getMessage());
            throw new SQLException("Impossible de mettre à jour le statut.", e);
        }
    }

    /**
     * Méthode pour récupérer toutes les réservations enregistrées dans la table.
     *
     * @return Une liste contenant tous les objets StatutReservation
     * @throws SQLException En cas d'erreur SQL
     */
    public List<StatutReservation> chercherTousLesStatutsReservations() throws SQLException {
        List<StatutReservation> statutResersvations = new ArrayList<>(); // Liste pour stocker les résultats
        String sql = "SELECT * FROM statut_reservation";                 // Requête pour récupérer tous les enregistrements

        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql); // Exécution de la requête et récupération des résultats
            while (rs.next()) {
                // Création d'un objet StatutReservation pour chaque ligne du résultat
                StatutReservation statutReservation = new StatutReservation(
                        rs.getInt("id_statut"),      // Récupération de l'ID
                        rs.getString("libelle")      // Récupération du libellé
                );
                statutResersvations.add(statutReservation); // Stocke l'objet dans la liste
            }
        }

        // Retourne la liste complète des statuts
        return statutResersvations;
    }

    /**
     * Méthode pour récupérer un statut de réservation spécifique à partir de son ID.
     *
     * @param id L'identifiant du statut de réservation recherché
     * @return L'objet `StatutReservation` contenant les données correspondantes, ou null si non trouvé
     * @throws SQLException En cas d'erreur SQL
     */
    public StatutReservation chercherStatutReservationParId(int id) throws SQLException {
        String sql = "SELECT * FROM statut_reservation WHERE id_statut =? "; // Requête SQL pour chercher par ID

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Définit le paramètre pour la condition WHERE
            ResultSet rs = stmt.executeQuery(); // Exécution de la requête

            if (rs.next()) {
                // Si une ligne est trouvée, retourne un objet StatutReservation
                return new StatutReservation(
                        rs.getInt("id_statut"),   // Récupère l'ID
                        rs.getString("libelle")   // Récupère le libellé
                );
            }
        }

        // Retourne null si aucune ligne correspondante n'a été trouvée
        return null;
    }

    /**
     * Méthode pour supprimer un statut de réservation à partir de son ID.
     *
     * @param id L'ID du statut de réservation à supprimer
     * @throws SQLException En cas d'erreur SQL
     */
    public void supprimerStatutReservation(int id) throws SQLException {
        String sql = "DELETE FROM statut_reservation WHERE id_statut =? "; // Requête SQL pour suppression

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Définit le paramètre pour la condition WHERE
            stmt.executeUpdate(); // Exécute la requête DELETE
        }
    }
}
