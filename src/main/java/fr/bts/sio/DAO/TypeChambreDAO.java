package fr.bts.sio.DAO;  // Définit le package dans lequel se trouve cette classe

// Importation des classes nécessaires
import fr.bts.sio.Models.TypeChambre; // Modèle représentant un type de chambre
import java.sql.*; // Pour les interactions SQL
import java.util.List; // Pour manipuler les listes

/**
 * Cette classe est un DAO (Data Access Object) qui permet de gérer les opérations CRUD
 * (Create, Read, Update, Delete) pour les entités TypeChambre dans une base de données.
 */
public class TypeChambreDAO {

    // Attribut représentant une connexion à la base de données
    private Connection connection;

    /**
     * Constructeur permettant d'injecter une connexion à la base de données.
     * @param connection la connexion à la base de données
     */
    public TypeChambreDAO(Connection connection) {
        this.connection = connection; // Initialise la connexion
    }

    /**
     * Ajoute un nouveau type de chambre dans la base de données.
     * @param idTypeChambre l'identifiant unique du type de chambre
     * @param libelle le libellé ou la description du type de chambre
     */
    public void ajouterTypeChambre(int idTypeChambre, String libelle) {
        // Requête SQL pour insérer un nouveau type de chambre
        String sql = "INSERT INTO TypeChambre (idTypeChambre, libelle) VALUES (?, ?)";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTypeChambre); // Définit le premier paramètre (idTypeChambre)
            stmt.setString(2, libelle); // Définit le deuxième paramètre (libelle)

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (Exception e) { // Capture des exceptions
            // Affiche un message en cas d'erreur lors de l'ajout
            System.out.println("Erreur lors de l'ajout du type de chambre : " + e.getMessage());
        }
    }

    /**
     * Recherche un type de chambre dans la base de données par son identifiant.
     * @param idTypeChambre l'identifiant du type de chambre
     * @return un objet TypeChambre si trouvé, sinon null
     */
    public TypeChambre chercherTypeChambreParId(int idTypeChambre) {
        // Requête SQL pour rechercher un type de chambre par son ID
        String sql = "SELECT * FROM TypeChambre WHERE idTypeChambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTypeChambre); // Définit le paramètre ID

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
        return null; // Retourne null si rien n'est trouvé ou en cas d'erreur
    }

    /**
     * Récupère la liste de tous les types de chambres de la base de données.
     * @return une liste contenant tous les types de chambres
     */
    public List<TypeChambre> chercherTotutesTypeChambres() {
        // Initialisation d'une liste vide pour stocker les résultats
        List<TypeChambre> typeChambres = new java.util.ArrayList<>();

        // Requête SQL pour sélectionner tous les types de chambres
        String sql = "SELECT * FROM TypeChambre";

        try {
            // Création d'une instruction SQL
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql); // Exécution de la requête

            // Parcourt chaque ligne du ResultSet
            while (rs.next()) {
                // Crée un objet TypeChambre à partir des données récupérées
                TypeChambre typeChambre = new TypeChambre(
                        rs.getInt("idTypeChambre"), // Récupère l'ID
                        rs.getString("libelle") // Récupère le libellé
                );
                typeChambres.add(typeChambre); // Ajoute l'objet à la liste
            }
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur lors du traitement de la recherche des types de chambres : " + e.getMessage());
        }
        return typeChambres; // Retourne la liste des types de chambres
    }

    /**
     * Modifie un type de chambre existant dans la base de données.
     * @param idTypeChambre l'identifiant du type de chambre à modifier
     * @param libelle le nouveau libellé du type de chambre
     */
    public void modifierTypeChambre(int idTypeChambre, String libelle) {
        // Requête SQL pour mettre à jour un type de chambre
        String sql = "UPDATE TypeChambre SET libelle = ? WHERE idTypeChambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, libelle); // Définit le nouveau libellé
            stmt.setInt(2, idTypeChambre); // Définit l'ID du type à modifier

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur de modification du type de chambre : " + e.getMessage());
        }
    }

    /**
     * Supprime un type de chambre de la base de données en fonction de son identifiant.
     * @param idTypeChambre l'identifiant du type de chambre à supprimer
     */
    public void supprimerTypeChambre(int idTypeChambre) {
        // Requête SQL pour supprimer un type de chambre
        String sql = "DELETE FROM TypeChambre WHERE idTypeChambre = ?";

        try {
            // Préparation de l'instruction SQL
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idTypeChambre); // Définit l'ID du type à supprimer

            // Exécution de la suppression
            stmt.executeUpdate();
        } catch (SQLException e) { // Capture des erreurs SQL
            System.out.println("Erreur de suppression du type de chambre : " + e.getMessage());
        }
    }
}