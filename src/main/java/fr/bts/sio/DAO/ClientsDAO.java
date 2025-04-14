package fr.bts.sio.DAO;

import java.sql.*;
// Importation des classes nécessaires pour l'interaction avec la base de données
import fr.bts.sio.Models.Clients;

public class ClientsDAO {
    private Connection connection;

    // Constructeur - initialisation de la connexion à la base de données
    public ClientsDAO() {
        try {
            // Se connecter à la base de données H2 (ajuste l'URL si nécessaire)
            connection = DriverManager.getConnection("jdbc:h2:", "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour créer un nouveau client
    public boolean createClient(Clients client) {
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

    // Méthode pour récupérer un client par son ID
    public Clients getClientById(int idClient) {
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

    // Méthode pour récupérer tous les clients
    public ResultSet getAllClients() {
        String query = "SELECT * FROM clients";
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour mettre à jour un client
    public boolean updateClient(Clients client) {
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

    // Méthode pour supprimer un client
    public boolean deleteClient(int idClient) {
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

