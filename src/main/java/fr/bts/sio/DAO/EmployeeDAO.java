package fr.bts.sio.DAO;

import fr.bts.sio.Models.Employee;
import fr.bts.sio.Models.RoleEmployee;
import java.sql.*;

/**
 * La classe EmployeeDAO fournit des méthodes pour interagir avec la base de
 * données pour la gestion des entités `Employee`. Elle inclut des opérations
 * telles que la création, la récupération, la mise à jour et la suppression
 * des employés.
 */
public class EmployeeDAO {

    private Connection connection;

    // Constructeur - initialisation de la connexion à la base de données
    /**
     * Constructeur pour initialiser la connexion à la base de données.
     * Par défaut, la connexion est établie avec une base H2.
     */
    public EmployeeDAO() {
        try {
            // Se connecter à la base de données H2 (ajuste l'URL si nécessaire)
            connection = DriverManager.getConnection("jdbc:", "", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour créer un nouvel employé
    /**
     * Crée un nouvel employé dans la base de données.
     *
     * @param employee L'objet `Employee` contenant les informations de l'employé à ajouter.
     * @return `true` si l'insertion a réussi, `false` sinon.
     */
    public boolean chercherEmployeeParId(Employee employee) {
        String query = "INSERT INTO employee(nom_employee, email_emplyee, mdp_employee, id_role) VALUES(?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getNomEmployee());
            stmt.setString(2, employee.getEmailEmployee());
            stmt.setString(3, employee.getMdpEmployee());
            stmt.setInt(4, employee.getRole().getIdRole()); // Assurez-vous que le rôle existe dans la base
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour récupérer un employé par son ID
    /**
     * Récupère un employé à partir de son identifiant unique.
     *
     * @param idEmployee L'identifiant unique de l'employé à récupérer.
     * @return Un objet `Employee` contenant les informations de l'employé, ou `null` si non trouvé.
     */
    public Employee getEmployeeById(int idEmployee) {
        String query = "SELECT e.id_empmoyee, e.nom_employee, e.email_emplyee, e.mdp_employee, r.id_role, r.libelle " +
                "FROM employee e " +
                "JOIN role_employee r ON e.id_role = r.id_role " +
                "WHERE e.id_empmoyee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEmployee);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                RoleEmployee role = new RoleEmployee(rs.getInt("id_role"), rs.getString("libelle"));
                return new Employee(
                        rs.getInt("id_empmoyee"),
                        rs.getString("nom_employee"),
                        rs.getString("email_emplyee"),
                        rs.getString("mdp_employee"),
                        role
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour récupérer tous les employés
    /**
     * Récupère tous les employés de la base de données.
     *
     * @return Un objet `ResultSet` contenant tous les enregistrements des employés.
     *         Retourne `null` en cas d'échec.
     */
    public ResultSet getAllEmployees() {
        String query = "SELECT e.id_empmoyee, e.nom_employee, e.email_emplyee, e.mdp_employee, r.id_role, r.libelle " +
                "FROM employee e " +
                "JOIN role_employee r ON e.id_role = r.id_role";
        try (Statement stmt = connection.createStatement()) {
            return stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour mettre à jour un employé
    /**
     * Met à jour les informations d'un employé existant dans la base de données.
     *
     * @param employee L'objet `Employee` contenant les informations mises à jour de l'employé.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     */
    public boolean updateEmployee(Employee employee) {
        String query = "UPDATE employee SET nom_employee = ?, email_emplyee = ?, mdp_employee = ?, id_role = ? WHERE id_empmoyee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, employee.getNomEmployee());
            stmt.setString(2, employee.getEmailEmployee());
            stmt.setString(3, employee.getMdpEmployee());
            stmt.setInt(4, employee.getRole().getIdRole()); // Assurez-vous que le rôle existe
            stmt.setInt(5, employee.getIdEmployee());
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Méthode pour supprimer un employé
    /**
     * Supprime un employé de la base de données.
     *
     * @param idEmployee L'identifiant unique de l'employé à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    public boolean deleteEmployee(int idEmployee) {
        String query = "DELETE FROM employee WHERE id_empmoyee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEmployee);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
