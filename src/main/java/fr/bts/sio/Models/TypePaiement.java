package fr.bts.sio.Models;

/**
 * Modèle représentant un type de paiement.
 * Chaque type de paiement est défini par un identifiant unique et un libellé (par exemple : "Carte bancaire", "Espèces").
 */
public class TypePaiement {

    private int idTypePaiement;
    private String libelle;

    public TypePaiement(int idTypePaiement, String libelle) {
        this.idTypePaiement = idTypePaiement;
        this.libelle = libelle;
    }

    public TypePaiement() {
    }

    public int getIdTypePaiement() {
        return idTypePaiement;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdTypePaiement(int idTypePaiement) {
        this.idTypePaiement = idTypePaiement;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "typePaiement{" +
                "idTypePaiement=" + idTypePaiement +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
