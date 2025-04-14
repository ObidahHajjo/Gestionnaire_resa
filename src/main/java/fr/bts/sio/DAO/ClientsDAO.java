package fr.bts.sio.DAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
// Importation des classes nécessaires pour l'interaction avec la base de données
import fr.bts.sio.Models.Clients;

/**
 * La classe ClientsDAO fournit des méthodes pour interagir avec la base de données,
 * permettant de gérer les entités `Clients`.
 * Elle inclut des opérations telles que création, récupération, mise à jour et suppression
 * des enregistrements clients dans la base de données.
 */
public class ClientsDAO {
    private Connection connection;

    /**
     * Constructeur pour initialiser la connexion à la base de données.
     * Par défaut, la connexion est établie avec une base H2.
     */
    public ClientsDAO() {
        try {
            // Se connecter à la base de données H2 (ajuste l'URL si nécessaire)
            connection = DriverManager.getConnection("jdbc:h2:", "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un nouveau client dans la base de données.
     *
     * @param client L'objet `Clients` contenant les informations du client à ajouter.
     * @return `true` si l'insertion a réussi, `false` sinon.
     */
    public boolean ajouterClient(Clients client) {
        String query = "INSERT INTO clients(nom, prenom, telephone, email) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTelephone());
            stmt.setString(4, client.getEmail());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Récupère un client à partir de son identifiant unique.
     *
     * @param idClient L'identifiant unique du client à récupérer.
     * @return Un objet `Clients` contenant les informations du client, ou `null` si non trouvé.
     */
    public Clients chercherClientParId(int idClient) {
        String query = "SELECT * FROM clients WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idClient);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Clients(
                        rs.getInt("id_client"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère tous les clients de la base de données.
     *
     * @return Un objet `Une liste contenant toutes les clients.
     */
    public List<Clients> chercherTousLesClients() {
        List<Clients> clients = new ArrayList<>();
        String query = "SELECT * FROM clients";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Création et ajout d'un client à la liste
                Clients client = new Clients(
                        rs.getInt("id_clients"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("telephone"),
                        rs.getString("email")
                );
                clients.add(client);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return clients;
    }

    /**
     * Met à jour les informations d'un client existant dans la base de données.
     *
     * @param client L'objet `Clients` contenant les informations mises à jour du client.
     *               L'identifiant (`id_client`) doit déjà exister dans la base.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     */
    public boolean modifierClient(Clients client) {
        String query = "UPDATE clients SET nom = ?, prenom = ?, telephone = ?, email = ? WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, client.getNom());
            stmt.setString(2, client.getPrenom());
            stmt.setString(3, client.getTelephone());
            stmt.setString(4, client.getEmail());
            stmt.setInt(5, client.getIdClient());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Supprime un client de la base de données.
     *
     * @param idClient L'identifiant unique du client à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    public boolean supprimerClient(int idClient) {
        String query = "DELETE FROM clients WHERE id_client = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idClient);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}

