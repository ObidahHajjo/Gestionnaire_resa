package fr.bts.sio.Interface;

import fr.bts.sio.Models.Chambre;

import java.util.List;

public interface ChambreDAOInterface {
    Chambre ajouteChambre(String numero_chambre, int id_type_chambre, int id_res);
    Chambre chercherChambreParId(int id_chambre);
    List<Chambre> chercherToutesChambres();
    Chambre modifierChambre(int id_chambre, String numero_chambre, int id_type_chambre, int id_res);
    boolean supprimerChambre(int id_chambre);

}
