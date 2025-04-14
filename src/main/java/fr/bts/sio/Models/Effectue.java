package fr.bts.sio.Models;

public class Effectue {
    private Facture facture;
    private TypePaiement typePaiement;

    public Effectue(Facture facture, TypePaiement typePaiement) {
        this.facture = facture;
        this.typePaiement = typePaiement;
    }

    public Effectue() {
    }

    public Facture getFacture() {
        return facture;
    }

    public TypePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public void setTypePaiement(TypePaiement typePaiement) {
        this.typePaiement = typePaiement;
    }

    @Override
    public String toString() {
        return "Effectue{" +
                "facture=" + facture +
                ", typePaiement=" + typePaiement +
                '}';
    }
}
