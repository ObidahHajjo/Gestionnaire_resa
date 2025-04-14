package fr.bts.sio.Models;

public class TypeChambre {

    private int idTypeChambre;
    private String libelle;

    //constructor
    public TypeChambre(int idTypeChambre, String libelle) {
        this.idTypeChambre = idTypeChambre;
        this.libelle = libelle;
    }

    public TypeChambre() {
    }

    // Getters
    public int getIdTypeChambre() {
        return idTypeChambre;
    }

    public String getLibelle() {
        return libelle;
    }
    //Setters
    public void setIdTypeChambre(int idTypeChambre) {
        this.idTypeChambre = idTypeChambre;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    //ToString
    @Override
    public String toString() {
        return "TypeChambre{" +
                "idTypeChambre=" + idTypeChambre +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
