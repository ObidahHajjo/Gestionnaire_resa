package fr.bts.sio.DAO;

// Importations nécessaires pour le modèle et les interactions avec la base de données
import fr.bts.sio.Interface.StatutReservationDAOInterface;
import fr.bts.sio.Models.StatutReservation; // Classe modèle représentant le statut de réservation
import java.sql.*; // Importation pour gérer les connexions et requêtes SQL
import java.util.ArrayList; // Importation pour manipuler les listes
import java.util.List; // Interface pour définir une liste générique

/**
 * Classe `StatutReservationDAO` : Objet d'Accès aux Données (DAO).
 * Cette classe gère toutes les opérations entre la table `statut_reservation`
 * dans la base de données et l'application Java.
 */
public class StatutReservationDAO implements StatutReservationDAOInterface {

    // Attribut pour stocker la connexion à la base de données
    private Connection connection;

    /**
     * Constructeur : Initialise la connexion à la base de données.
     *
     * @param connection La connexion établie avec la base de données.
     */
    public StatutReservationDAO(Connection connection) {
        this.connection = connection; // Initialise l'attribut de connexion
    }

    /**
     * Méthode pour modifier un statut de réservation existant dans la base de données.
     *
     * @param idStatut L'objet `StatutReservation` contenant les nouvelles valeurs.
     */
    @Override
    public StatutReservation modifierStatutReservation(int idStatut, String libelle) {
        // Requête SQL pour mettre à jour un statut existant
        String sql = "UPDATE statut_reservation SET libelle=? WHERE id_statut=?";

        try {
            // Prépare la requête avec la connexion
            PreparedStatement stmt = connection.prepareStatement(sql);

            // Définit les valeurs des paramètres :
            stmt.setInt(1, idStatut); // Définit l'ID (dans WHERE)
            stmt.setString(2, libelle);  // Définit le libellé

            // Exécute la mise à jour
            stmt.executeUpdate();
            StatutReservation st = new StatutReservation(idStatut, libelle);
            return st;
        } catch (SQLException e) {
            // Capture et affiche les erreurs survenues lors de la mise à jour
            System.out.println("Erreur lors de la mise à jour du statut : " + e.getMessage());
        } return null;
    }

    /**
     * Méthode pour récupérer tous les statuts de réservation enregistrés dans la base de données.
     *
     * @return Une liste contenant tous les objets `StatutReservation` récupérés.
     */
    @Override
    public List<StatutReservation> chercherTousStatutsReservations() {
        // Liste pour stocker les statuts récupérés
        List<StatutReservation> statutReservations = new ArrayList<>();

        // Requête SQL pour récupérer tous les enregistrements
        String sql = "SELECT * FROM statut_reservation";

        try {
            // Crée un statement pour exécuter la requête
            Statement stmt = connection.createStatement();
            // Exécute la requête et récupère les résultats
            ResultSet rs = stmt.executeQuery(sql);

            // Parcourt chaque ligne du résultat
            while (rs.next()) {
                // Crée un objet StatutReservation à partir des données
                StatutReservation statutReservation = new StatutReservation(
                        rs.getInt("idStatut"), // Récupère la colonne `id_statut`
                        rs.getString("libelle") // Récupère la colonne `libelle`
                );
                // Ajoute l'objet à la liste des résultats
                statutReservations.add(statutReservation);
            }
        } catch (SQLException e) {
            // Capture et affiche les erreurs survenues lors de la récupération
            System.out.println("Erreur lors de la recherche des statuts : " + e.getMessage());
        }

        // Retourne la liste des statuts
        return statutReservations;
    }

    /**
     * Méthode pour rechercher un statut de réservation spécifique par son identifiant.
     *
     * @param id L'identifiant du statut de réservation.
     * @return Un objet `StatutReservation` correspondant, ou `null` si aucun statut n'est trouvé.
     */
    @Override
    public StatutReservation chercherStatutReservationParId(int id) {
        // Requête SQL pour rechercher un statut donné par son ID
        String sql = "SELECT * FROM statut_reservation WHERE id_statut=?";

        try {
            // Préparation de la requête SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id); // Définit la valeur du paramètre (id_statut)

            // Exécute la requête et récupère le résultat
            ResultSet rs = stmt.executeQuery();

            // Vérifie si un résultat est trouvé
            if (rs.next()) {
                // Crée et retourne un objet StatutReservation si une ligne est trouvée
                return new StatutReservation(
                        rs.getInt("idStatut"), // Récupère la colonne `id_statut`
                        rs.getString("libelle") // Récupère la colonne `libelle`
                );
            }
        } catch (SQLException e) {
            // Capture et affiche les erreurs survenues lors de la recherche
            System.out.println("Erreur lors de la recherche du statut : " + e.getMessage());
        }

        // Retourne `null` si aucun résultat n'a été trouvé
        return null;
    }

    /**
     * Méthode pour supprimer un statut de réservation de la base de données par son identifiant.
     *
     * @param id L'identifiant du statut de réservation à supprimer.
     */
    @Override
    public boolean supprimerStatutReservation(int id) {
        // Requête SQL pour supprimer un statut par son ID
        String sql = "DELETE FROM statut_reservation WHERE id_statut=?";

        try {
            // Préparation de la requête SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id); // Définit la valeur du paramètre (id_statut)

            // Exécute la requête de suppression
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            // Capture et affiche les erreurs survenues lors de la suppression
            System.out.println("Erreur lors de la suppression du statut : " + e.getMessage());
        }return false;
    }
}