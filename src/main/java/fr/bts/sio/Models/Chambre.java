package fr.bts.sio.Models;
public class Chambre {

    private int idChambre;
    private String numeroChambre;
    private Reservation reservation;
    private TypeChambre typeChambre;

    //constructor
    public Chambre(int idChambre, String numeroChambre, Reservation reservation, TypeChambre typeChambre) {
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

    public String getNumeroChambre() {
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

    public void setNumeroChambre(String numeroChambre) {
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
