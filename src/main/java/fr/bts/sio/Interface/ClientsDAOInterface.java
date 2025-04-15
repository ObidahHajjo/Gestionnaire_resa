package fr.bts.sio.Interface;

import fr.bts.sio.Models.Clients;

import java.util.List;

public interface ClientsDAOInterface {
    Clients AjouterClient(String nom, String prenom, String telephone, String email);
    Clients chercherClientParId(int id_client);
    List<Clients> chercherTousLesClients();
    boolean modifierClient(int id_client, String nom, String prenom, String telephone, String email);
    boolean supprimerClient(int id_client);
}
