package fr.bts.sio.DAO;

import fr.bts.sio.Models.Chambre;
import fr.bts.sio.Models.Reservation;
import fr.bts.sio.Models.TypeChambre;

import java.sql.*;
import java.util.List;
import java.util.Scanner;

public class ChambreDAO {

    // Attribut représentant la connexion à la base de données
    private Connection connection;

    public ChambreDAO(Connection connection) {
        this.connection = connection;

    }

    public void ajouteChambre(String numero_chambre, int id_type_chambre, int id_res) {
        String sql = "INSERT INTO chambres (numero_chambre, id_type_chambre, id_res) VALUES (?, ?, ?)";


        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            // Remplace les (?) par les données de l'objet 'chambres'
            stmt.setString(1, numero_chambre);
            stmt.setInt(2,id_type_chambre);
            stmt.setFloat(3,id_res);
        } catch (SQLException e) {
            System.out.println("Erreur de lors de l'ajoute de la chambre :  " + e.getMessage());
        }
    }

    public Chambre chercherChambreParId(int id_chambre) {
        String sql = "SELECT * FROM chambres WHERE id_chambre = ?";

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id_chambre);
            ResultSet rs = stmt.executeQuery();
            ReservationDAO ReservationDAO = new ReservationDAO(connection);
            TypeChambreDAO TypeChambreDAO = new TypeChambreDAO(connection);

            if(rs.next()){
                Reservation res = ReservationDAO.chercherReservationParId(rs.getInt("id_res"));
                TypeChambre type = TypeChambreDAO.chercherTypeChambreParId(rs.getInt("id_type_chambre"));
                return new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getString("numero_chambre"),
                        res,
                        type
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération de la chambre : " + e.getMessage());
        }
        return null;
    }

    public List<Chambre> chercherToutesChambres() {
        List<Chambre> chambres = new java.util.ArrayList<>();

        String sql = "SELECT * FROM chambres";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);

            ReservationDAO ReservationDAO = new ReservationDAO(connection);
            TypeChambreDAO TypeChambreDAO = new TypeChambreDAO(connection);

            while (rs.next()){
                Reservation res = ReservationDAO.chercherReservationParId(rs.getInt("id_res"));
                TypeChambre type = TypeChambreDAO.chercherTypeChambreParId(rs.getInt("id_type_chambre"));
                Chambre Chambre = new Chambre(
                        rs.getInt("id_chambre"),
                        rs.getString("numero_chambre"),
                        res,
                        type
                );
                chambres.add(Chambre);
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors du traitement de la recherche des chambres : " + e.getMessage());
        }return chambres;
    }

    public void modifierChambre(int id_chambre, String numero_chambre, int id_type_chambre, int id_res) {
        String sql = "UPDATE chambres SET numero_chambre = ?, id_type_chambre = ?, id_res = ? WHERE id_chambre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id_chambre);
            stmt.setString(2, numero_chambre);
            stmt.setInt(3, id_type_chambre);
            stmt.setInt(4, id_res);
            stmt.executeUpdate();

        } catch(SQLException e){
            System.out.println("Erreur de modification de la chambre : " + e.getMessage());
        }

    }

    public void supprimerChambre(int id_chambre) {
        String sql = "DELETE FROM chambres WHERE id_chambre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id_chambre);
            stmt.executeUpdate();
        } catch(SQLException e){
            System.out.println("Erreur de suppression de la chambre : " + e.getMessage());
        }
    }

}
