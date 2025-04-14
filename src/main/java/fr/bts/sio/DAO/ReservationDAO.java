package fr.bts.sio.DAO;

import fr.bts.sio.Models.*;

import java.sql.*;
import java.util.List;

/**
 * Classe `ReservationDAO` : Objet d'Accès aux Données (DAO) pour les réservations.
 * Cette classe gère toutes les opérations CRUD (Create, Read, Update, Delete)
 * pour la table `reservations` dans la base de données.
 */
public class ReservationDAO {

    // Attribut représente la connexion à la base de données
    private Connection connection;

    /**
     * Constructeur : initialise la connexion à la base de données.
     *
     * @param connection connexion active avec la base de données.
     */
    public ReservationDAO(Connection connection) {
        this.connection = connection; // Initialise la connexion.
    }

    /**
     * Ajoute une nouvelle réservation à la base de données.
     *
     * @param dateResDebut    Date de début de la réservation.
     * @param dateResFin      Date de fin de la réservation.
     * @param nombresPersonnes Le nombre de personnes pour la réservation.
     * @param petitDejeuner   Indique si le petit déjeuner est inclus.
     * @param id_employee     ID de l'employé gérant cette réservation.
     * @param id_factures     ID de la facture associée.
     * @param id_statut       ID du statut de la réservation.
     * @param id_client       ID du client ayant effectué la réservation.
     */
    public void ajouteReservation(Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                  int id_employee, int id_factures, int id_statut, int id_client) {
        // Requête SQL pour insérer une réservation
        String sql = "INSERT INTO reservations (dateResDebut, dateResFin, nombresPersonnes, petitDejeuner, " +
                "id_employee, id_factures, id_statut, id_client) VALUES (?,?,?,?,?,?,?,?)";

        try {
            // Préparation de la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            // Définition des paramètres de la requête
            stmt.setDate(1, dateResDebut);
            stmt.setDate(2, dateResFin);
            stmt.setInt(3, nombresPersonnes);
            stmt.setInt(4, petitDejeuner);
            stmt.setInt(5, id_employee);
            stmt.setInt(6, id_factures);
            stmt.setInt(7, id_statut);
            stmt.setInt(8, id_client);

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Affiche un message d'erreur en cas de problème
            System.out.println("Erreur lors de l'ajoute de la réservation : " + e.getMessage());
        }
    }

    /**
     * Recherche une réservation spécifique à partir de son identifiant.
     *
     * @param id_reservation ID de la réservation recherché.
     * @return Un objet `Reservation` si trouvé, sinon `null`.
     */
    public Reservation chercherReservationParId(int id_reservation) {
        // Requête SQL pour chercher une réservation par ID
        String sql = "SELECT * FROM reservations WHERE id_reservation = ?";

        try {
            // Préparation de la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id_reservation);

            // Exécution de la requête
            ResultSet rs = stmt.executeQuery();

            // DAO nécessaires pour récupérer les relations associées
            EmployeeDAO employeeDAO = new EmployeeDAO();
            FactureDAO factureDAO = new FactureDAO(connection);
            StatutReservationDAO statutReservationDAO = new StatutReservationDAO(connection);
            ClientsDAO clientDAO = new ClientsDAO();

            // Si un résultat est trouvé
            if (rs.next()) {
                Employee e = employeeDAO.chercherEmployeeParId(rs.getInt("id_employee"));
                Facture f = factureDAO.chercherFactureParId(rs.getInt("id_factures"));
                StatutReservation s = statutReservationDAO.chercherStatutReservationParId(rs.getInt("id_statut"));
                Clients c = clientDAO.chercherClientParId(rs.getInt("idClient"));

                // Retourne un objet Reservation créé avec les données récupérées
                return new Reservation(
                        rs.getInt("id_res"),
                        rs.getDate("dateResDebut"),
                        rs.getDate("dateResFin"),
                        rs.getInt("nombrePersonnes"),
                        rs.getInt("petitDejeuner"),
                        e, f, s, c
                );
            }
        } catch (SQLException e) {
            // Capture et affiche les erreurs SQL
            System.out.println("Erreur lors de la récupération de la réservation : " + e.getMessage());
        }

        // Retourne `null` si aucune réservation n'a été trouvée
        return null;
    }

    /**
     * Récupère une liste de toutes les réservations de la base de données.
     *
     * @return Une liste contenant toutes les réservations.
     */
    public List<Reservation> chercherToutesLesReservations() {
        // Liste pour stocker les réservations trouvées
        List<Reservation> reservations = new java.util.ArrayList<>();

        // Requête SQL pour récupérer toutes les réservations
        String sql = "SELECT * FROM reservations";

        try {
            // Préparation de la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            // DAO nécessaires pour récupérer les relations associées
            EmployeeDAO employeeDAO = new EmployeeDAO();
            FactureDAO factureDAO = new FactureDAO(connection);
            StatutReservationDAO statutReservationDAO = new StatutReservationDAO(connection);
            ClientsDAO clientDAO = new ClientsDAO();

            // Parcourt chaque ligne du résultat
            while (rs.next()) {
                Employee e = employeeDAO.chercherEmployeeParId(rs.getInt("id_employee"));
                Facture f = factureDAO.chercherFactureParId(rs.getInt("id_factures"));
                StatutReservation s = statutReservationDAO.chercherStatutReservationParId(rs.getInt("id_statut"));
                Clients c = clientDAO.chercherClientParId(rs.getInt("idClient"));

                // Crée un objet Reservation à partir des données récupérées
                Reservation reservation = new Reservation(
                        rs.getInt("id_res"),
                        rs.getDate("dateResDebut"),
                        rs.getDate("dateResFin"),
                        rs.getInt("nombrePersonnes"),
                        rs.getInt("petitDejeuner"),
                        e, f, s, c
                );
                reservations.add(reservation); // Ajoute à la liste
            }
        } catch (SQLException e) {
            // Capture et affiche les erreurs SQL
            System.out.println("Erreur lors du traitement : " + e.getMessage());
        }

        // Retourne la liste des réservations
        return reservations;
    }

    /**
     * Modifie une réservation existante dans la base de données.
     *
     * @param idRes           ID de la réservation.
     * @param dateResDebut    Date de début de la réservation.
     * @param dateResFin      Date de fin de la réservation.
     * @param nombresPersonnes Nombre de personnes.
     * @param petitDejeuner   Indique si le petit déjeuner est inclus.
     * @param id_employee     ID de l'employé.
     * @param id_factures     ID de la facture.
     * @param id_statut       ID du statut.
     * @param id_client       ID du client.
     */
    public void modifierReservation(int idRes, Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                    int id_employee, int id_factures, int id_statut, int id_client) {
        // Requête SQL pour mettre à jour une réservation
        String sql = "UPDATE reservations SET dateResDebut = ?, dateResFin = ?, nombresPersonnes = ?, petitDejeuner = ?, " +
                "id_employee = ?, id_factures = ?, id_statut = ?, id_client = ? WHERE idRes = ?";

        try {
            // Création de la requête préparée
            PreparedStatement stmt = connection.prepareStatement(sql);

            // Définition des paramètres
            stmt.setDate(1, dateResDebut);
            stmt.setDate(2, dateResFin);
            stmt.setInt(3, nombresPersonnes);
            stmt.setInt(4, petitDejeuner);
            stmt.setInt(5, id_employee);
            stmt.setInt(6, id_factures);
            stmt.setInt(7, id_statut);
            stmt.setInt(8, id_client);
            stmt.setInt(9, idRes);

            // Exécution de la mise à jour
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Capture et affiche les erreurs
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    /**
     * Supprime une réservation existante à partir de son identifiant.
     *
     * @param idRes ID de la réservation à supprimer.
     */
    public void supprimerReservation(int idRes) {
        // Requête SQL pour suppression
        String sql = "DELETE FROM reservations WHERE idRes = ?";

        try {
            // Préparation de la requête
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, idRes);

            // Exécution de la requête
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Capture et affiche les erreurs
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}
