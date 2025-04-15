package fr.bts.sio.DAO;

// Importation des classes nécessaires pour l'interaction avec la base de données
import fr.bts.sio.Interface.FactureDAOInterface;
import fr.bts.sio.Models.Facture;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe `FactureDAO` :
 * Cette classe gère l'accès et les interactions avec la table `factures` dans la base de données.
 * Elle offre des méthodes pour effectuer des opérations CRUD (Create, Read, Update, Delete)
 * sur les factures.
 */
public class FactureDAO implements FactureDAOInterface {

    // Attribut représentant la connexion à la base de données
    private Connection connection;

    /**
     * Constructeur de la classe `FactureDAO`.
     *
     * @param connection Une connexion active à la base de données.
     */
    public FactureDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * CREATE : Ajoute une nouvelle facture dans la base de données.
     *
     * @param chemin     Le chemin du fichier représentant la facture.
     * @param nomFichier Le nom du fichier associé à la facture.
     * @param prix       Le prix total indiqué dans la facture.
     */
    @Override
    public Facture ajouterFacture(String chemin, String nomFichier, float prix) {
        String sql = "INSERT INTO factures (chemin, nom_fichier, prix) VALUES (?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Remplacement des paramètres "?" par les valeurs passées en arguments.
            stmt.setString(1, chemin);
            stmt.setString(2, nomFichier);
            stmt.setFloat(3, prix);

            // Exécution de la requête INSERT.
            stmt.executeUpdate();

            // Récupération de l'identifiant généré pour la facture.
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int idFacture = 0;
            if (generatedKeys.next()) {
                idFacture = generatedKeys.getInt(1);
            }

            // Création d'une instance de la facture nouvellement ajoutée.
            Facture f = new Facture(idFacture, chemin, nomFichier, prix);
            System.out.println("Facture ajoutée avec succès : " + f);
            return f;

        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la facture : " + e.getMessage());
        }return null;
    }

    /**
     * READ : Récupère une facture spécifique par son identifiant unique.
     *
     * @param id L'identifiant unique de la facture.
     * @return Une instance de `Facture` si trouvée, ou `null` si aucune facture ne correspond.
     */
    @Override
    public Facture chercherFactureParId(int id) {
        String sql = "SELECT * FROM factures WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id); // Remplacement du "?" avec l'ID de la facture.
            ResultSet rs = stmt.executeQuery(); // Exécution de la requête SQL.

            if (rs.next()) {
                // Création d'une instance de facture à partir des données retournées de la base.
                return new Facture(
                        rs.getInt("id_factures"),
                        rs.getString("chemin"),
                        rs.getString("nom_fichier"),
                        rs.getFloat("prix")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de la facture : " + e.getMessage());
        }
        return null; // Retourne `null` si aucune facture n'est trouvée.
    }

    /**
     * READ : Récupère toutes les factures présentes dans la base de données.
     *
     * @return Une liste contenant toutes les factures.
     */
    @Override
    public List<Facture> chercherToutesFactures() {
        List<Facture> factures = new ArrayList<>();
        String sql = "SELECT * FROM factures";
        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql); // Exécution de la requête SQL.

            while (rs.next()) {
                // Instanciation de chaque facture et ajout à la liste.
                Facture facture = new Facture(
                        rs.getInt("id_factures"),
                        rs.getString("chemin"),
                        rs.getString("nom_fichier"),
                        rs.getFloat("prix")
                );
                factures.add(facture);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche des factures : " + e.getMessage());
        }
        return factures; // Retourne la liste complète des factures.
    }

    /**
     * UPDATE : Met à jour une facture existante dans la base de données.
     *
     * @param idFacture  L'identifiant unique de la facture à modifier.
     * @param chemin     Le nouveau chemin de la facture.
     * @param nomFichier Le nouveau nom du fichier.
     * @param prix       Le nouveau montant total de la facture.
     */
    @Override
    public Facture modifierFacture(int idFacture, String chemin, String nomFichier, float prix) {
        String sql = "UPDATE factures SET chemin = ?, nom_fichier = ?, prix = ? WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            // Remplacement des paramètres "?" par les valeurs passées en arguments.
            stmt.setString(1, chemin);
            stmt.setString(2, nomFichier);
            stmt.setFloat(4, prix);
            stmt.setInt(5, idFacture);

            // Exécution de la requête UPDATE.
            stmt.executeUpdate();

            System.out.println("Facture (ID : " + idFacture + ") mise à jour avec succès.");
            Facture f = new Facture(idFacture, chemin, nomFichier, prix);
            return f;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la facture : " + e.getMessage());
        }  return null;
    }

    /**
     * DELETE : Supprime une facture de la base de données par son identifiant.
     *
     * @param id L'identifiant unique de la facture à supprimer.
     */
    @Override
    public boolean supprimerFacture(int id) {
        String sql = "DELETE FROM factures WHERE id_factures = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id); // Remplacement du "?" par l'identifiant de la facture.

            // Exécution de la requête DELETE.
            stmt.executeUpdate();
            System.out.println("Facture (ID : " + id + ") supprimée avec succès.");
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la facture : " + e.getMessage());
        } return false;
    }
}

