package fr.bts.sio.DAO;

import fr.bts.sio.Interface.ChambreDAOInterface;
import fr.bts.sio.Models.Chambre;
import fr.bts.sio.Models.Reservation;
import fr.bts.sio.Models.TypeChambre;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe `ChambreDAO` :
 * Cette classe est responsable de l'accès aux données pour le modèle `Chambre`.
 * Elle fournit des méthodes CRUD (Create, Read, Update, Delete) pour gérer
 * les chambres dans la base de données.
 */
public class ChambreDAO implements ChambreDAOInterface {

    // La connexion active avec la base de données
    private Connection connection;

    /**
     * Constructeur
     *
     * @param connection Connexion active à la base de données.
     */
    public ChambreDAO(Connection connection) {
        this.connection = connection;
    }

    /**
     * CREATE : Ajouter une nouvelle chambre dans la base de données.
     *
     * @param numero_chambre  Numéro de la chambre (identifiant unique dans l'hôtel).
     * @param id_type_chambre Identifiant du type de la chambre (relation avec TypeChambreDAO).
     * @param id_res          Identifiant de la réservation associée (relation avec ReservationDAO).
     */
    @Override
    public Chambre ajouteChambre(String numero_chambre, int id_type_chambre, int id_res) {
        String sql = "INSERT INTO chambres (numeroChambre, idTypeChambre, idRes) VALUES (?, ?, ?)";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            // Replacer les "?" par les données passées en paramètres
            stmt.setString(1, numero_chambre);
            stmt.setInt(2, id_type_chambre);
            stmt.setInt(3, id_res); // Correction de `setFloat` -> `setInt`
            stmt.executeUpdate(); // Exécution de la requête d'ajout

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            int idChambre = 0;
            if (generatedKeys.next()) {
                idChambre = generatedKeys.getInt(1);
            }

            // Création de l'objet Chambre
            TypeChambreDAO tc = new TypeChambreDAO(connection);
            TypeChambre typeChambre = tc.chercherTypeChambreParId(id_type_chambre);

            ReservationDAO r = new ReservationDAO(connection);
            Reservation reservation = r.chercherReservationParId(id_res);

            Chambre chambre = new Chambre(idChambre, numero_chambre, reservation, typeChambre);
            return chambre;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'ajout de la chambre : " + e.getMessage());
        }return null;
    }

    /**
     * READ (1) : Trouver une chambre par son ID.
     *
     * @param id_chambre Identifiant unique de la chambre.
     * @return L'objet `Chambre` correspondant à l'ID ou `null` si non trouvé.
     */
    @Override
    public Chambre chercherChambreParId(int id_chambre) {
        String sql = "SELECT * FROM chambres WHERE id_chambre = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_chambre);

            ResultSet rs = stmt.executeQuery();
            ReservationDAO reservationDAO = new ReservationDAO(connection);
            TypeChambreDAO typeChambreDAO = new TypeChambreDAO(connection);

            if (rs.next()) {
                // Récupère les relations nécessaires (Reservation et TypeChambre)
                Reservation res = reservationDAO.chercherReservationParId(rs.getInt("id_res"));
                TypeChambre type = typeChambreDAO.chercherTypeChambreParId(rs.getInt("id_type_chambre"));

                // Crée un objet Chambre à partir des données récupérées
                return new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getString("numero_chambre"),
                        res,
                        type
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la recherche de la chambre : " + e.getMessage());
        }
        return null; // Retourne null si pas trouvé
    }

    /**
     * READ (2) : Récupérer toutes les chambres dans la base de données.
     *
     * @return Une liste contenant toutes les chambres.
     */
    @Override
    public List<Chambre> chercherToutesChambres() {
        List<Chambre> chambres = new ArrayList<>();
        String sql = "SELECT * FROM chambres";

        try {
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // Initialisation des DAO pour récupérer les relations
            ReservationDAO reservationDAO = new ReservationDAO(connection);
            TypeChambreDAO typeChambreDAO = new TypeChambreDAO(connection);

            while (rs.next()) {
                // Récupère les relations pour chaque chambre
                Reservation res = reservationDAO.chercherReservationParId(rs.getInt("id_res"));
                TypeChambre type = typeChambreDAO.chercherTypeChambreParId(rs.getInt("id_type_chambre"));

                // Crée un objet Chambre et l'ajoute à la liste
                Chambre chambre = new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getString("numero_chambre"),
                        res,
                        type
                );
                chambres.add(chambre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des chambres : " + e.getMessage());
        }
        return chambres; // Retourne la liste complète des chambres
    }

    /**
     * UPDATE : Modifier une chambre existante dans la base de données.
     *
     * @param id_chambre     Identifiant unique de la chambre à modifier.
     * @param numero_chambre Nouveau numéro de la chambre.
     * @param id_type_chambre Nouvel identifiant du type de la chambre.
     * @param id_res         Nouvel identifiant de la réservation associée.
     */
    @Override
    public Chambre modifierChambre(int id_chambre, String numero_chambre, int id_type_chambre, int id_res) {
        String sql = "UPDATE chambres SET numero_chambre = ?, id_type_chambre = ?, id_res = ? WHERE id_chambre = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);

            // Remplacer les valeurs dans la requête avec les nouvelles données
            stmt.setString(1, numero_chambre);
            stmt.setInt(2, id_type_chambre);
            stmt.setInt(3, id_res);
            stmt.setInt(4, id_chambre);
            stmt.executeUpdate(); // Exécution de la requête UPDATE

            TypeChambreDAO tc = new TypeChambreDAO(connection);
            TypeChambre typeChambre = tc.chercherTypeChambreParId(id_type_chambre);
            ReservationDAO r = new ReservationDAO(connection);
            Reservation reservation = r.chercherReservationParId(id_res);

            Chambre c = new Chambre(id_chambre, numero_chambre, reservation, typeChambre);
            return c;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la modification de la chambre : " + e.getMessage());
        } return null;
    }

    /**
     * DELETE : Supprimer une chambre par son ID.
     *
     * @param id_chambre Identifiant unique de la chambre à supprimer.
     */
    @Override
    public boolean supprimerChambre(int id_chambre) {
        String sql = "DELETE FROM chambres WHERE id_chambre = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_chambre); // Remplace "?" par l'ID de la chambre
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression de la chambre : " + e.getMessage());
        }return false;
    }
}