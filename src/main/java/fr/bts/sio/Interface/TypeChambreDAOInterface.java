package fr.bts.sio.Interface;

import fr.bts.sio.Models.TypeChambre;

import java.util.List;

public interface TypeChambreDAOInterface {
    TypeChambre ajouterTypeChambre(String libelle);
    TypeChambre chercherTypeChambreParId(int idTypeChambre);
    List<TypeChambre> chercherToutesTypeChambres();
    TypeChambre modifierTypeChambre(int idTypeChambre, String libelle);
    boolean supprimerTypeChambre(int idTypeChambre);
}
