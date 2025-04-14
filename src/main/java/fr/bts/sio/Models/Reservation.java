package fr.bts.sio.Models;

import java.util.Date;

/**
 * Modèle représentant une réservation.
 * Une réservation contient plusieurs détails comme l'identifiant, dates de début et de fin,
 * nombre de personnes, petit-déjeuner, employé associé, facture, statut de la réservation, et le client.
 */
public class Reservation {

    private int idRes;
    private Date dateResDebut;
    private Date dateResFin;
    private int nombrePersonnes;
    private int petitDejeuner;
    private Employee employee;
    private Facture facture;
    private StatutReservation statutReservation;
    private Clients client;

    public Reservation(int idRes, Date dateResDebut, Date dateResFin, int nombrePersonnes, int petitDejeuner, Employee employee, Facture facture, StatutReservation statutReservation, Clients client) {
        this.idRes = idRes;
        this.dateResDebut = dateResDebut;
        this.dateResFin = dateResFin;
        this.nombrePersonnes = nombrePersonnes;
        this.petitDejeuner = petitDejeuner;
        this.employee = employee;
        this.facture = facture;
        this.statutReservation = statutReservation;
        this.client = client;
    }

    public Reservation() {
    }

    public int getIdRes() {
        return idRes;
    }

    public Date getDateResDebut() {
        return dateResDebut;
    }

    public Date getDateResFin() {
        return dateResFin;
    }

    public int getNombrePersonnes() {
        return nombrePersonnes;
    }

    public int getPetitDejeuner() {
        return petitDejeuner;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Facture getFacture() {
        return facture;
    }

    public StatutReservation getStatutReservation() {
        return statutReservation;
    }

    public Clients getClient() {
        return client;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public void setDateResDebut(Date dateResDebut) {
        this.dateResDebut = dateResDebut;
    }

    public void setDateResFin(Date dateResFin) {
        this.dateResFin = dateResFin;
    }

    public void setNombrePersonnes(int nombrePersonnes) {
        this.nombrePersonnes = nombrePersonnes;
    }

    public void setPetitDejeuner(int petitDejeuner) {
        this.petitDejeuner = petitDejeuner;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public void setStatutReservation(StatutReservation statutReservation) {
        this.statutReservation = statutReservation;
    }

    public void setClient(Clients client) {
        this.client = client;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "idRes=" + idRes +
                ", dateResDebut=" + dateResDebut +
                ", dateResFin=" + dateResFin +
                ", nombrePersonnes=" + nombrePersonnes +
                ", petitDejeuner=" + petitDejeuner +
                ", employee=" + employee +
                ", facture=" + facture +
                ", statutReservation=" + statutReservation +
                ", client=" + client +
                '}';
    }
}
