package fr.bts.sio.Controllers;

import fr.bts.sio.Models.Clients;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import fr.bts.sio.DAO.ClientsDAO;

import java.sql.Connection;
import java.util.List;

public class ClientsController {

    @FXML
    private TextField textFieldNom;

    @FXML
    private TextField textFieldPrenom;

    @FXML
    private TextField textFieldTelephone;

    @FXML
    private TextField textFieldEmail;

    @FXML
    private Button buttonSauvegarder;

    @FXML
    private Button buttonCreer;

    @FXML
    private Button buttonModifier;

    @FXML
    private Button buttonEffacer;

    @FXML
    private ListView listViewClients;

    // Une instance de FactureDAO pour exécuter les opérations sur la base de données.
    private ClientsDAO clientsDAO;

    /**
     * Constructeur
     * @param connection La connexion SQL pour initialiser le DAO
     */
    public ClientsController(Connection connection) {
        this.clientsDAO = new ClientsDAO();
    }

    /**
     * Ajouter une client dans la base de données
     * @param nom Le nom du client
     * @param prenom Le prénom du client
     * @param telephone Le numéro de téléphone du client
     * @param email l'email du client
     */
    public void ajouterClient(String nom, String prenom, String telephone, String email) {
        Clients c = new Clients(0, nom, prenom, telephone, email); // Création d'un objet Clients
        clientsDAO.createClient(c); // Ajout de la facture dans la base via le DAO
        System.out.println("Client ajouté avec succès !");
    }

    /**
     * Afficher toutes les clients existants
     */
    public void afficherTousLesClients() {
        List<Clients> clients = clientsDAO.getAllClients(); // Récupère toutes les factures
        for (Clients c : clients) {
            System.out.println(c); // Affiche chaque client (via toString())
        }
    }

    /**
     * Affiche un client spécifique en fonction de son ID
     * @param id L'identifiant unique du client
     */
    public void afficherClientParId(int id) {
        Clients c = clientsDAO.getClientById(id); // Récupère le client via son ID
        if (c != null) {
            System.out.println(c); // Affiche la facture trouvée
        } else {
            System.out.println("Aucun client trouvé avec l'ID " + id);
        }
   }

    /**
     * Modifier un client existant
     * @param nom Nouveau nom du client
     * @param prenom Nouveau prénom du client
     * @param telephone Nouveau numéro de téléphone
     * @param email Nouvel email
     * @param idClient L'identifiant de client à modifier
     */
    public void modifierClient(String nom, String prenom, String telephone, String email, int idClient) {
        Clients clientExistant = clientsDAO.getClientById (idClient); // Récupère la facture existante
        if (clientExistant != null) {
            // Mise à jour des données de la facture
            clientExistant.setNom(nom);
            clientExistant.setPrenom(prenom);
            clientExistant.setTelephone(telephone);
            clientExistant.setEmail(email);
            clientExistant.setIdClient(idClient);
            clientsDAO.updateClient(clientExistant); // Mise à jour dans la base via le DAO
            System.out.println("Client modifié !");
        } else {
            System.out.println("Aucun client trouvé avec l'ID " + idClient);
        }
    }

    /**
     * Supprime un client par son ID
     * @param idClient L'identifiant du client à supprimer
     */
    public void supprimerClient(int idClient) {
        Clients c = clientsDAO.getClientById(idClient); // Récupère le client à supprimer
        if (c != null) {
            clientsDAO.deleteClient(idClient); // Supprime le client via le DAO
            System.out.println("Le client " + idClient + " a été supprimée");
        } else {
            System.out.println("Aucun client trouvé avec l'ID " + idClient);
        }
    }

}