package fr.bts.sio.Interface;

import fr.bts.sio.Models.Reservation;

import java.sql.Date;
import java.util.List;

public interface ReservationDAOInterface {
    Reservation ajouteReservation(Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                  int idEmployee, int idFactures, int idStatut, int idClient);
    Reservation chercherReservationParId(int idRes);
    List<Reservation> chercherToutesLesReservations();
    Reservation modifierReservation(int idRes, Date dateResDebut, Date dateResFin, int nombresPersonnes, int petitDejeuner,
                                    int idEmployee, int idFactures, int idStatut, int idClient);
    boolean supprimerReservation(int idRes);
}
