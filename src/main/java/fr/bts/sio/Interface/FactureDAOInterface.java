package fr.bts.sio.Interface;

import fr.bts.sio.Models.Facture;

import java.util.List;

public interface FactureDAOInterface {
    Facture ajouterFacture(String chemin, String nomFichier, float prix);
    Facture chercherFactureParId(int id_facture);
    List<Facture> chercherToutesFactures();
    boolean supprimerFacture(int id_facture);
    Facture modifierFacture(int idFacture, String chemin, String nomFichier, float prix);
}
