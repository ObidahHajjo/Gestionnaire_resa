package fr.bts.sio.Controllers;

import fr.bts.sio.DAO.StatutReservationDAO;
import fr.bts.sio.Models.StatutReservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class StatutReservationController {

    private StatutReservationDAO statutReservationDAO;

    // Constructeur qui reçoit une connexion SQL
    public StatutReservationController(Connection connection) {
        this.statutReservationDAO = new StatutReservationDAO(connection);
    }

    // Affiche tous les statuts existants
    public void afficherToutesLesReservations() {
        try {
            List<StatutReservation> statutReservation = statutReservationDAO.chercherTousLesStatutsReservations();

            if (statutReservation.isEmpty()) {
                System.out.println("Aucun statut de réservation trouvé.");
            } else {
                for (StatutReservation s : statutReservation) {
                    System.out.println(s);
                }
            }
        } catch (Exception e) {
            System.out.println("Erreur lors de l'affichage des réservations : " + e.getMessage());
        }
    }


    // Affiche un statut spécifique par son id
    public void afficherStatutReservationParId(int id) {
        try{
            StatutReservation s = statutReservationDAO.chercherStatutReservationParId(id);
            if ( s != null){
                System.out.println(s);
            } else{
                System.out.println("Aucun statut trouvé avec l'ID " + id );
            }
        } catch (SQLException e) {
            System.out.println("erreur lors de l'affichage du statut : " + e.getMessage());
        }
    }

    public void modifierStatut(int id_statut, String libelle){
        try{
            StatutReservation s = statutReservationDAO.chercherStatutReservationParId(id_statut);
            if ( s != null){
                s.setIdStatut(id_statut);
                s.setLibelle(libelle);
                statutReservationDAO.modifierStatutReservation(s);
                System.out.println("Statut modifier");
            } else {
                System.out.println("Statut non trouver avec l'ID " + id_statut);
            }
        } catch (SQLException e) {

            System.out.println("erreur lors de la modification du statut : " + e.getMessage());
        }
    }
}

