package fr.bts.sio.Models;

/**
 * Modèle représentant l'opération d'effet d'un type de paiement sur une facture donnée.
 * Il relie une facture et le type de paiement utilisé pour cette facture.
 */
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
