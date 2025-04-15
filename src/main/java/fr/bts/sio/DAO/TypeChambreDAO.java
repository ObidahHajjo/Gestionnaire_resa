package fr.bts.sio.DAO; // Définit le package dans lequel se trouve cette classe

// Importation des classes nécessaires
import fr.bts.sio.Models.TypeChambre; // Modèle représentant un type de chambre
import java.sql.*; // Pour les interactions SQL
import java.util.List; // Pour manipuler les listes
import java.util.ArrayList; // Permet de créer des listes dynamiques

/**
 * Cette classe est un DAO (Data Access Object) qui gère les opérations CRUD
 * (Create, Read, Update, Delete) pour les entités `TypeChambre` dans une base de données.
 */
public class TypeChambreDAO {

    // Attribut représentant une connexion à la base de données
    private Connection connection;

    /**
     * Constructeur permettant d'injecter une connexion à la base de données.
     *
     * @param connection La connexion active à la base de données.
     */
    public TypeChambreDAO(Connection connection) {
        this.connection = connection; // Initialise la connexion
    }

    /**
     * CREATE : Ajoute un nouveau type de chambre dans la base de données.
     *
     * @param libelle Le libellé ou la description du type de chambre.
     */
    public TypeChambre ajouterTypeChambre(String libelle) {
        // Requête SQL pour insérer un nouveau type de chambre
        String sql = "INSERT INTO TypeChambre (libelle) VALUES (?)";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, libelle); // Définit le premier paramètre (libelle)

            // Exécution de la requête
            stmt.executeUpdate();

            // Récupération de l'ID généré
            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int idTypeChambre = 0;
            if (generatedKeys.next()) {
                idTypeChambre = generatedKeys.getInt(1); // Récupère l'ID généré
            }

            // Création d'un objet TypeChambre pour représenter le type ajouté
            TypeChambre tc = new TypeChambre(idTypeChambre, libelle);
            System.out.println("Type de chambre ajouté avec succès : " + tc);
            return tc;
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors de l'ajout du type de chambre : " + e.getMessage());
        }  return null;
    }

    /**
     * READ : Recherche un type de chambre dans la base de données par son identifiant.
     *
     * @param idTypeChambre L'identifiant du type de chambre.
     * @return Un objet `TypeChambre` si trouvé, sinon `null`.
     */
    public TypeChambre chercherTypeChambreParId(int idTypeChambre) {
        // Requête SQL pour rechercher un type de chambre par son ID
        String sql = "SELECT * FROM TypeChambre WHERE id_type_chambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTypeChambre); // Définit l'ID à rechercher

            // Exécution de la requête
            ResultSet rs = stmt.executeQuery();

            // Si un résultat est trouvé, créer un objet TypeChambre
            if (rs.next()) {
                return new TypeChambre(
                        rs.getInt("idTypeChambre"), // Récupère l'ID
                        rs.getString("libelle") // Récupère le libellé
                );
            }
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors de la récupération du type de chambre : " + e.getMessage());
        }
        return null; // Retourne null si aucun résultat trouvé
    }

    /**
     * READ : Récupère la liste de tous les types de chambres dans la base de données.
     *
     * @return Une liste contenant tous les types de chambres.
     */
    public List<TypeChambre> chercherToutesTypeChambres() {
        // Initialisation d'une liste pour stocker les résultats
        List<TypeChambre> typeChambres = new ArrayList<>();

        // Requête SQL pour sélectionner tous les types de chambres
        String sql = "SELECT * FROM type_chambre";

        try {
            // Création d'une instruction SQL
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql); // Exécution de la requête

            // Parcourt chaque ligne du ResultSet
            while (rs.next()) {
                // Création d'un objet TypeChambre à partir des données récupérées
                TypeChambre typeChambre = new TypeChambre(
                        rs.getInt("idTypeChambre"), // Récupère l'ID
                        rs.getString("libelle") // Récupère le libellé
                );
                typeChambres.add(typeChambre); // Ajoute l'objet TypeChambre à la liste
            }
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors de la récupération des types de chambres : " + e.getMessage());
        }
        return typeChambres; // Retourne la liste des types de chambres
    }

    /**
     * UPDATE : Modifie un type de chambre existant dans la base de données.
     *
     * @param idTypeChambre L'identifiant du type de chambre à modifier.
     * @param libelle       Le nouveau libellé du type de chambre.
     */
    public TypeChambre modifierTypeChambre(int idTypeChambre, String libelle) {
        // Requête SQL pour mettre à jour un type de chambre
        String sql = "UPDATE TypeChambre SET libelle = ? WHERE id_type_chambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, libelle); // Définit le nouveau libellé
            stmt.setInt(2, idTypeChambre); // Définit l'ID à modifier

            // Exécution de la requête
            stmt.executeUpdate();
            System.out.println("Type de chambre (ID: " + idTypeChambre + ") modifié avec succès.");
            TypeChambre tc= new TypeChambre(idTypeChambre, libelle);
            return tc;
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors de la modification du type de chambre : " + e.getMessage());
        } return null;
    }

    /**
     * DELETE : Supprime un type de chambre de la base de données par son identifiant.
     *
     * @param idTypeChambre L'identifiant du type de chambre à supprimer.
     */
    public boolean supprimerTypeChambre(int idTypeChambre) {
        // Requête SQL pour supprimer un type de chambre
        String sql = "DELETE FROM TypeChambre WHERE id_type_chambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTypeChambre); // Définit l'ID à supprimer

            // Exécution de la requête DELETE
            stmt.executeUpdate();
            System.out.println("Type de chambre (ID: " + idTypeChambre + ") supprimé avec succès.");
            return true;
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors de la suppression du type de chambre : " + e.getMessage());
        } return false;
    }
}