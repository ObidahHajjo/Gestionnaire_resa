package fr.bts.sio.Models;

/**
 * Modèle représentant le statut d'une réservation.
 * Le statut permet d'indiquer l'état actuel d'une réservation (par exemple : en cours, annulée, terminée).
 */
public class StatutReservation {

    private int idStatut;
    private String libelle;

    public StatutReservation(int idStatut, String libelle) {
        this.idStatut = idStatut;
        this.libelle = libelle;
    }

    public StatutReservation() {

    }

    public int getIdStatut() {
        return idStatut;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdStatut(int idStatus) {
        this.idStatut = idStatut;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "StatutReservation{" +
                "idStatut=" + idStatut +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
