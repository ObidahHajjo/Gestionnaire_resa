package fr.bts.sio.DAO;

import fr.bts.sio.Models.Facture;
import fr.bts.sio.Models.RoleEmployee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe RoleEmployeeDAO fournit des méthodes pour interagir avec la base
 * de données et gérer les entités `RoleEmployee`. Elle inclut des opérations
 * pour créer, récupérer, mettre à jour et supprimer des rôles.
 */
public class RoleEmployeeDAO {

    private Connection connection;

    /**
     * Constructeur pour initialiser la connexion à la base de données.
     * Par défaut, la connexion est établie avec une base H2.
     */
    public RoleEmployeeDAO() {
        try {
            // Se connecter à la base de données H2 (ajuste l'URL si nécessaire)
            connection = DriverManager.getConnection("jdbc:h2:", "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Crée un nouveau rôle dans la base de données.
     * @param libelle le nom de la role .
     * @return `true` si l'insertion a réussi, `false` sinon.
     */
    public RoleEmployee ajouterRole(String libelle) {
        String query = "INSERT INTO role_employee(libelle) VALUES(?)";
        try  {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, libelle);
            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()){
                int id = rs.getInt(1);
                return new RoleEmployee(id, libelle);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère un rôle à partir de son identifiant unique.
     * @param idRole L'identifiant unique du rôle à récupérer.
     * @return Un objet `RoleEmployee` contenant les informations du rôle, ou `null` si non trouvé.
     */
    public RoleEmployee chercherRoleParId(int idRole) {
        String query = "SELECT * FROM role_employee WHERE id_role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRole);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new RoleEmployee(
                        rs.getInt("id_role"),
                        rs.getString("libelle")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Récupère tous les rôles dans la base de données.
     *
     * @return Un objet `ResultSet` contenant les enregistrements des rôles.
     *         Retourne `null` si la requête échoue.
     */
    public List<RoleEmployee> chercherTousLesRoles() {
        List<RoleEmployee> roles = new ArrayList<>();
        String query = "SELECT * FROM role_employee";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Création et ajout d'une facture à la liste
                RoleEmployee role = new RoleEmployee(
                        rs.getInt("id_role"),
                        rs.getString("libelle")
                );
                roles.add(role);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roles;
    }

    /**
     * Met à jour les informations d'un rôle existant dans la base de données.
     *
     * @param idRole L'identifiant unique du rôle à récupérer.
     * @param libelle le nom de la role .
     * @return `true` si la mise à jour a réussi, `false` sinon.
     */
    public RoleEmployee modifierRole(int idRole, String libelle) {
        String query = "UPDATE role_employee SET libelle = ? WHERE id_role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, libelle);
            stmt.setInt(2, idRole);
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                return new RoleEmployee(idRole, libelle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Supprime un rôle de la base de données.
     * @param idRole L'identifiant unique du rôle à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    public boolean supprimerRole(int idRole) {
        String query = "DELETE FROM role_employee WHERE id_role = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idRole);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}