package fr.bts.sio.Models;

public class RoleEmployee {

    private int idRole;
    private String libelle;

    //constructor
    public RoleEmployee(int idRole, String libelle) {
        this.idRole = idRole;
        this.libelle = libelle;
    }

    public RoleEmployee() {
    }

    // Getters
    public int getIdRole() {
        return idRole;
    }

    public String getLibelle() {
        return libelle;
    }

    // Setters
    public void setIdRole(int idRole) {
        this.idRole = idRole;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    //ToString
    @Override
    public String toString() {
        return "RoleEmployee{" +
                "idRole=" + idRole +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}