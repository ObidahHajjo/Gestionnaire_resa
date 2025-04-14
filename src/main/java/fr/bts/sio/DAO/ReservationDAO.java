package fr.bts.sio.DAO;

import fr.bts.sio.Models.Employee;
import fr.bts.sio.Models.Facture;
import fr.bts.sio.Models.Reservation;
import fr.bts.sio.Models.StatutReservation;

import java.sql.*;
import java.util.List;

public class ReservationDAO {

    private Connection connection;

    public ReservationDAO(Connection connection) {
        this.connection = connection;
    }

    public void ajouteReservation(Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                               int id_employee, int id_factures, int id_statut, int id_client) {
        String sql = "INSERT INTO reservations (dateResDebut, dateResFin, nombresPersonnes, petitDejeuner, " +
                "id_employee, id_factures, id_statut, id_client) VALUES (?,?,?,?,?,?,?,?)";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setDate(1, dateResDebut);
            stmt.setDate(2, dateResFin);
            stmt.setInt(3, nombresPersonnes);
            stmt.setInt(4, petitDejeuner);
            stmt.setInt(5, id_employee);
            stmt.setInt(6, id_factures);
            stmt.setInt(7, id_statut);
            stmt.setInt(8, id_client);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erreur lors de l'ajoute  de la réservation : " + e.getMessage());

        }
    }

    public Reservation chercherReservationParId(int id_reservation) {
        String sql = "SELECT * FROM reservations WHERE id_reservation = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, id_reservation);
            ResultSet rs = stmt.executeQuery();

            EmployeeDAO EmployeeDAO = new EmployeeDAO ();
            FactureDAO FactureDAO = new FactureDAO(connection);
            StatutReservationDAO StatutReservationDAO = new StatutReservationDAO(connection);
            ClientDAO ClientDAP = new ClientDAO();

            if (rs.next()){
                Employee e = EmployeeDAO.chercherEmployeeParId(rs.getInt("id_employee"));
                Facture f = FactureDAO.chercherFactureParId(rs.getInt("id_factures"));
                StatutReservation s = StatutReservationDAO.chercherStatutReservationParId(rs.getInt("id_statut"));
                Client c = ClientDAO.chercherClientParid(rs.getInt("idClient"));

                Reservation Reservation = new Reservation(
                        rs.getInt("id_res"),
                        rs.getDate("dateResDebut"),
                        rs.getDate("dateResFin"),
                        rs.getInt("nombrePersonnes"),
                        rs.getInt("petitDejeuner"),
                        e,
                        f,
                        s,
                        c
                );
            }
        }catch(SQLException e){
            System.out.println("Erreur lors de la récupération de la chambre : " + e.getMessage());
        }
        return null;
    }

    public List<Reservation> chercherToutesLesReservations() {
        List<Reservation> reservations = new java.util.ArrayList<>();

        String sql = "SELECT * FROM reservations";
        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            ResultSet rs = stmt.executeQuery(sql);

            EmployeeDAO EmployeeDAO = new EmployeeDAO ();
            FactureDAO FactureDAO = new FactureDAO(connection);
            StatutReservationDAO StatutReservationDAO = new StatutReservationDAO(connection);
            ClientDAO ClientDAP = new ClientDAO();

            while (rs.next()){
                Employee e = EmployeeDAO.chercherEmployeeParId(rs.getInt("id_employee"));
                Facture f = FactureDAO.chercherFactureParId(rs.getInt("id_factures"));
                StatutReservation s = StatutReservationDAO.chercherStatutReservationParId(rs.getInt("id_statut"));
                Client c = ClientDAO.chercherClientParid(rs.getInt("idClient"));

                Reservation Reservation = new Reservation(
                        rs.getInt("id_res"),
                        rs.getDate("dateResDebut"),
                        rs.getDate("dateResFin"),
                        rs.getInt("nombrePersonnes"),
                        rs.getInt("petitDejeuner"),
                        e,
                        f,
                        s,
                        c
                );
                reservations.add(Reservation);
            }
        }catch (SQLException e){
            System.out.println("Erreur lors du traitement : " + e.getMessage());
        }
        return reservations;
    }

    public void modifierReservation(int idRes, Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                   int id_employee, int id_factures, int id_statut, int id_client) {
        String sql = "UPDATE reservations SET dateResDebut = ?, dateResFin = ?, nombresPersonnes = ?, petitDejeuner = ?, " +
                "id_employee = ?, id_factures = ?, id_statut = ?, id_client = ? WHERE idRes = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idRes);
            stmt.setDate(2, dateResDebut);
            stmt.setDate(3,dateResFin);
            stmt.setInt(4, nombresPersonnes);
            stmt.setInt(5,petitDejeuner);
            stmt.setInt(6,id_employee);
            stmt.setInt(7,id_factures);
            stmt.setInt(8,id_statut);
            stmt.setInt(9,id_client);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    public void supprimerReservation(int idRes) {
        String sql = "DELETE FROM reservations WHERE idRes = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idRes);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
