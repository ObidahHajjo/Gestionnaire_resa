package fr.bts.sio.Controllers;

// Importations nécessaires pour les interactions avec le modèle et la base de données
import fr.bts.sio.DAO.FactureDAO;
import fr.bts.sio.DAO.ReservationDAO;
import fr.bts.sio.Models.*;
import fr.bts.sio.Utils.ConvertirDate;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 * Classe ReservationController
 * Cette classe agit comme un pont entre la couche interface utilisateur (ou une autre couche)
 * et la base de données en passant par le DAO pour les opérations liées aux réservations.
 */
public class ReservationController {

    // Une instance de ReservationDAO pour exécuter les opérations sur la base de données.
    private ReservationDAO reservationDAO;

    /**
     * Constructeur
     * @param connection La connexion SQL pour initialiser le DAO
     */
    public ReservationController(Connection connection) {
        this.reservationDAO = new ReservationDAO(connection);
    }

    /**
     * Ajouter une reservation dans la base de données
     * @param reservation La réservation à ajouter
     */
    public void ajouterReservation(Reservation reservation) {
        java.sql.Date dateResDebut = ConvertirDate.convertirVersSQL(reservation.getDateResDebut());
        java.sql.Date dateResFin = ConvertirDate.convertirVersSQL(reservation.getDateResFin());
        int nombresPersonnes = reservation.getNombrePersonnes();
        int petitDejeuner = reservation.getPetitDejeuner();
        int id_employee = reservation.getEmployee().getIdEmployee();
        int id_factures = reservation.getFacture().getIdFactures();
        int id_statut = reservation.getStatutReservation().getIdStatut();
        int id_client = reservation.getClient().getIdClient();
        reservationDAO.ajouterReservation(dateResDebut, dateResFin, nombresPersonnes, petitDejeuner,
                id_employee, id_factures, id_statut, id_client); // Ajout de la réservation dans la base via le DAO
        System.out.println("Réservation ajoutée avec succès !");

    }

    /**
     * Afficher toutes les réservations existantes
     */
    public void afficherToutesRéservations() {
           List<Reservation> reservations = reservationDAO.chercherToutesLesReservations(); // Récupère toutes les réservations
        for (Reservation f : reservations) {
            System.out.println(f); // Affiche chaque réservation (via toString())
        }
     }

    /**
     * Affiche une réservtion spécifique en fonction de son ID
     * @param id L'identifiant unique de la réservation
     */
    public void afficherTeservationParId(int id) {
        Reservation r = reservationDAO.chercherReservationParId(id); // Récupère la réservation via son ID
        if (r != null) {
            System.out.println(r); // Affiche la réservation trouvée
        } else {
            System.out.println("Aucune réservation trouvée avec l'ID " + id);
        }
    }

    /**
     * Modifier une réservation existante
     * @param reservation La réservation a modifié
     * @param dateResDebut Nouvelle date de début de réservation
     * @param dateResDebut Nouvelle date de fin de réservation
     * @param nombresPersonnes Nouveau nombre de personnes pour la réservation
     * @param petitDejeuner Nouveau nombre de petit-déjeuner
     * @param employee Nouvel employé ayant netré la réservation
     * @param employee Nouvelle Facture pour la réservation
     * @param statut Nouveau statut de la réservation
     * @param client Nouveau client pour la réservation
     */
    public Reservation modifierReservation(Reservation reservation, Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                    Employee employee, Facture facture, StatutReservation statut, Clients client) {
        int idRes = reservation.getIdRes();
        int idEmployee = employee.getIdEmployee();
        int idFacture = facture.getIdFactures();
        int idStatut = statut.getIdStatut();
        int idClient = client.getIdClient();
        java.sql.Date dateResDebutSql = ConvertirDate.convertirVersSQL(dateResDebut);
        java.sql.Date dateResFinSql = ConvertirDate.convertirVersSQL(dateResFin);

        reservation = reservationDAO.modifierReservation(idRes, dateResDebutSql, dateResFinSql, nombresPersonnes, petitDejeuner,
                idEmployee, idFacture, idStatut, idClient);
        return reservation;
    }

    /**
     * Supprime une réservation par son ID
     * @param reservation la réservation à supprimer
     */
    public boolean supprimerReservation(Reservation reservation) {
        if (reservation != null) {
            int idRes = reservation.getIdRes();
            if(reservationDAO.chercherReservationParId(idRes) != null) {
                reservationDAO.supprimerReservation(idRes); // Supprime la réservation via le DAO
                reservation = null;
                System.out.println("La réservation " + idRes + " a été supprimée");
                return true;
            } else {
                System.out.println("L'objet n'existe pas en base de données !");
                return false;
            }
         } else {
            System.out.println("L'objet est null");
            return false;
        }
    }


}
