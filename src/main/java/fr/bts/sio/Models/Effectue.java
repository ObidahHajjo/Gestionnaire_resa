package fr.bts.sio.Models;

public class Effectue {
    private Facture facture;
    private typePaiement typePaiement;

    public Effectue(Facture facture, fr.bts.sio.Models.typePaiement typePaiement) {
        this.facture = facture;
        this.typePaiement = typePaiement;
    }

    public Effectue() {
    }

    public Facture getFacture() {
        return facture;
    }

    public fr.bts.sio.Models.typePaiement getTypePaiement() {
        return typePaiement;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }

    public void setTypePaiement(fr.bts.sio.Models.typePaiement typePaiement) {
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
