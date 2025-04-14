package fr.bts.sio.Models;
public class Chambre {

    private int idChambre;
    private int numeroChambre;
    private Reservations reservation;
    private TypeChambre typeChambre;

    //constructor
    public Chambre(int idChambre, int numeroChambre, Reservations reservation, TypeChambre typeChambre) {
        this.idChambre = idChambre;
        this.numeroChambre = numeroChambre;
        this.reservation = reservation;
        this.typeChambre = typeChambre;
    }

    public Chambre() {
    }

    // Getters
    public int getIdChambre() {
        return idChambre;
    }

    public int getNumeroChambre() {
        return numeroChambre;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public TypeChambre getTypeChambre() {
        return typeChambre;
    }

    // Setters
    public void setIdChambre(int idChambre) {
        this.idChambre = idChambre;
    }

    public void setNumeroChambre(int numeroChambre) {
        this.numeroChambre = numeroChambre;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    public void setTypeChambre(TypeChambre typeChambre) {
        this.typeChambre = typeChambre;
    }

    //ToString
    @Override
    public String toString() {
        return "Chambre{" +
                "idChambre=" + idChambre +
                ", numeroChambre=" + numeroChambre +
                ", reservation=" + reservation +
                ", typeChambre=" + typeChambre +
                '}';
    }
}
