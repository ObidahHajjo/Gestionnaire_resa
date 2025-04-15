package fr.bts.sio.Interface;

import fr.bts.sio.Models.StatutReservation;

import java.util.List;

public interface StatutReservationDAOInterface {
    StatutReservation modifierStatutReservation(int idStatut, String libelle);
    List<StatutReservation> chercherTousStatutsReservations();
    StatutReservation chercherStatutReservationParId(int id);
    boolean supprimerStatutReservation(int id);
}
