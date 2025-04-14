package fr.bts.sio.DAO;

import fr.bts.sio.Models.Employee;
import fr.bts.sio.Models.RoleEmployee;
import java.sql.*;

public class EmployeeDAO {

    private Connection connection;

    // Constructeur - initialisation de la connexion à la base de données
    public EmployeeDAO() {
        try {
            // Se connecter à la base de données H2 (ajuste l'URL si nécessaire)
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Méthode pour créer un nouvel employé
    public boolean createEmployee(Employee employee) {
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
