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
     *
     * @param connection Objet Connection permettant de communiquer avec la base de données.
     */
    public FactureDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * CREATE : Ajouter une facture dans la base de données.
     *
     * @param chemin     Chemin du fichier de la facture.
     * @param nomFichier Nom du fichier de la facture.
     * @param tva        TVA associée à la facture.
     * @param prix       Prix total enregistré dans la facture.
     */
    public void ajouterFacture(String chemin, String nomFichier, float tva, float prix) {
        String sql = "INSERT INTO factures (chemin, nom_fichier, tva, prix) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            // Remplacement des paramètres "?" par les valeurs passées en argument
            stmt.setString(1, chemin);
            stmt.setString(2, nomFichier);
            stmt.setFloat(3, tva);
            stmt.setFloat(4, prix);

            // Exécution de la requête INSERT
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
        }
    }

    /**
     * READ (1) : Lire une facture spécifique par son ID.
     *
     * @param id L'identifiant unique de la facture à récupérer.
     * @return Un objet `Facture` correspondant à l'ID, ou `null` si la facture n'est pas trouvée.
     */
    public Facture chercherFactureParId(int id) {
        String sql = "SELECT * FROM factures WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
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
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de la facture : " + e.getMessage());
        }
        return null; // Retourne `null` si aucune facture n'est trouvée
    }

    /**
     * READ (2) : Récupérer toutes les factures présentes dans la base de données.
     *
     * @return Une liste contenant toutes les factures.
     */
    public List<Facture> chercherToutesFactures() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        try {
            Statement stmt = connection.createStatement();
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
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des factures : " + e.getMessage());
        }
        return factures; // Retourne la liste complète des factures
    }

    /**
     * UPDATE : Modifier une facture existante dans la base de données.
     *
     * @param idFacture  Identifiant unique de la facture à modifier.
     * @param chemin     Nouveau chemin de la facture.
     * @param nomFichier Nouveau nom du fichier de la facture.
     * @param tva        Nouvelle TVA associée à la facture.
     * @param prix       Nouveau prix total de la facture.
     */
    public void modifierFacture(int idFacture, String chemin, String nomFichier, float tva, float prix) {
        String sql = "UPDATE factures SET chemin = ?, nomFichier = ?, tva = ?, prix = ? WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            // Mise à jour des champs avec les nouvelles valeurs
            stmt.setString(1, chemin);
            stmt.setString(2, nomFichier);
            stmt.setFloat(3, tva);
            stmt.setFloat(4, prix);
            stmt.setInt(5, idFacture);

            // Exécution de la requête UPDATE
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
        }
    }

    /**
     * DELETE : Supprimer une facture par son ID.
     *
     * @param id L'identifiant unique de la facture à supprimer.
     */
    public void supprimerFacture(int id) {
        String sql = "DELETE FROM factures WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id); // Passage de l'ID de la facture à supprimer

            // Exécution de la requête DELETE
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la facture : " + e.getMessage());
        }
    }
}

