package fr.bts.sio.DAO;

// Importation des classes nécessaires pour l'interaction avec la base de données
import fr.bts.sio.Models.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe `FactureDAO` :
 * Cette classe est responsable de l'accès aux données pour le modèle `Facture`.
 * Elle fournit des méthodes pour effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur la table `factures` de la base de données.
 */
public class FactureDAO {

    // Attribut représentant la connexion à la base de données
    private Connection connection;

    /**
     * Constructeur
     * @param connection Objet Connection permettant de communiquer avec la base de données
     */
    public FactureDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * CREATE : Ajouter une facture dans la base de données
     * @param facture L'objet Facture contenant les données à insérer
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL
     */
    public void ajouterFacture(Facture facture) throws SQLException {
        String sql = "INSERT INTO factures (chemin, nom_fichier, tva, prix) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Remplacement des paramètres "?" par les données de l'objet `facture`
            stmt.setString(1, facture.getChemin());
            stmt.setString(2, facture.getNomFichier());
            stmt.setFloat(3, facture.getTva());
            stmt.setFloat(4, facture.getPrix());
            // Exécution de la requête INSERT
            stmt.executeUpdate();
        }
    }

    /**
     * READ (1) : Lire une facture spécifique par son ID
     * @param id L'identifiant unique de la facture à récupérer
     * @return Un objet Facture correspondant à l'ID, ou null si non trouvé
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL
     */
    public Facture getFactureById(int id) throws SQLException {
        String sql = "SELECT * FROM factures WHERE id_factures = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Passage de l'ID recherché
            ResultSet rs = stmt.executeQuery(); // Exécution de la requête
            if (rs.next()) {
                // Construction de l'objet Facture à partir des données retournées
                return new Facture(
                        rs.getInt("id_factures"),
                        rs.getString("chemin"),
                        rs.getString("nom_fichier"),
                        rs.getFloat("tva"),
                        rs.getFloat("prix")
                );
            }
        }
        return null; // Retourne null si aucune facture trouvée
    }

    /**
     * READ (2) : Récupérer toutes les factures présentes dans la base
     * @return Une liste contenant toutes les factures
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL
     */
    public List<Facture> getAllFactures() throws SQLException {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql); // Exécution de la requête
            while (rs.next()) {
                // Création et ajout d'une facture à la liste
                Facture facture = new Facture(
                        rs.getInt("id_factures"),
                        rs.getString("chemin"),
                        rs.getString("nom_fichier"),
                        rs.getFloat("tva"),
                        rs.getFloat("prix")
                );
                factures.add(facture);
            }
        }
        return factures; // Retourne la liste complète des factures
    }

    /**
     * UPDATE : Modifier une facture existante
     * @param facture L'objet Facture contenant les nouvelles données
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL
     */
    public void updateFacture(Facture facture) throws SQLException {
        String sql = "UPDATE factures SET chemin = ?, nom_fichier = ?, tva = ?, prix = ? WHERE id_factures = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Mise à jour des champs avec les nouvelles valeurs
            stmt.setString(1, facture.getChemin());
            stmt.setString(2, facture.getNomFichier());
            stmt.setFloat(3, facture.getTva());
            stmt.setFloat(4, facture.getPrix());
            stmt.setInt(5, facture.getIdFactures()); // Indication de l'ID pour la clause WHERE
            stmt.executeUpdate(); // Exécution de l'UPDATE
        }
    }

    /**
     * DELETE : Supprimer une facture par son ID
     * @param id L'identifiant unique de la facture à supprimer
     * @throws SQLException En cas d'erreur lors de l'exécution de la requête SQL
     */
    public void supprimerFacture(int id) throws SQLException {
        String sql = "DELETE FROM factures WHERE id_factures = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id); // Passage de l'ID de la facture à supprimer
            stmt.executeUpdate();
        }
    }
}

