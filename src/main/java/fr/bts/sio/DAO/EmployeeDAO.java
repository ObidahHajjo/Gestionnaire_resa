package fr.bts.sio.DAO;

import fr.bts.sio.Models.Clients;
import fr.bts.sio.Models.Employee;
import fr.bts.sio.Models.Facture;
import fr.bts.sio.Models.RoleEmployee;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * La classe EmployeeDAO fournit des méthodes pour interagir avec la base de
 * données pour la gestion des entités `Employee`. Elle inclut des opérations
 * telles que la création, la récupération, la mise à jour et la suppression
 * des employés.
 */
public class EmployeeDAO {

    private Connection connection;
    private RoleEmployeeDAO roleEmployeeDAO;

    // Constructeur - initialisation de la connexion à la base de données
    /**
     * Constructeur pour initialiser la connexion à la base de données.
     * Par défaut, la connexion est établie avec une base H2.
     */
    public EmployeeDAO(Connection connection) {
        this.connection = connection;
        this.roleEmployeeDAO = new RoleEmployeeDAO();
    }

    // Méthode pour créer un nouvel employé
    /**
     * Crée un nouvel employé dans la base de données.
     * @param nomEmployee le nom d'employee
     * @param emailEmployee l'email d'employé
     * @param mdpEmployee le mot de passe d'employé
     * @param idRole l'id de role
     * @return `Employee` si l'insertion a réussi, `null` sinon.
     */
    public Employee ajouterEmployee(String nomEmployee,String emailEmployee,String mdpEmployee, int idRole) {
        String query = "INSERT INTO employee(nom_employee, email_emplyee, mdp_employee, id_role) VALUES(?, ?, ?, ?)";
        try {
            PreparedStatement stmt = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            stmt.setString(1, nomEmployee);
            stmt.setString(2, emailEmployee);
            stmt.setString(3, mdpEmployee);
            stmt.setInt(4, idRole); // Assurez-vous que le rôle existe dans la base
            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                int id = rs.getInt(1);
                return new Employee(id, nomEmployee,emailEmployee, mdpEmployee, roleEmployeeDAO.chercherRoleParId(idRole));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour récupérer un employé par son ID
    /**
     * Récupère un employé à partir de son identifiant unique.
     * @param idEmployee L'identifiant unique de l'employé à récupérer.
     * @return Un objet `Employee` contenant les informations de l'employé, ou `null` si non trouvé.
     */
    public Employee chercherEmployeeParId(int idEmployee) {
        String query = "SELECT e.id_employee, e.nom_employee, e.email_employee, e.mdp_employee, r.id_role, r.libelle " +
                "FROM employee e " +
                "JOIN role_employee r ON e.id_role = r.id_role " +
                "WHERE e.id_employee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, idEmployee);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Employee(
                        rs.getInt("id_employee"),
                        rs.getString("nom_employee"),
                        rs.getString("email_employee"),
                        rs.getString("mdp_employee"),
                        roleEmployeeDAO.chercherRoleParId(rs.getInt("id_role"))
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
     * @return Une liste contenant tous les  employés.
     * Retourne `null` en cas d'échec.
     */
    public List<Employee> chercherTousLesEmployees() {
        List<Employee> employees = new ArrayList<>();
        String query = "SELECT e.id_employee, e.nom_employee, e.email_employee, e.mdp_employee, r.id_role, r.libelle " +
                "FROM employee e " +
                "JOIN role_employee r ON e.id_role = r.id_role";
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                // Création et ajout d'un employe à la liste
                Employee employee = new Employee(
                        rs.getInt("id_employee"),
                        rs.getString("nom_employee"),
                        rs.getString("nom_fichier"),
                        rs.getString("email_employee"),
                        roleEmployeeDAO.chercherRoleParId(rs.getInt("id_role"))
                );
                employees.add(employee);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employees;
    }


    // Méthode pour mettre à jour un employé
    /**
     * Met à jour les informations d'un employé existant dans la base de données.
     * @param idEmployee L'identifiant de l'employé.
     * @param nomEmployee Le nom de l'employé.
     * @param emailEmployee L'email de l'employé.
     * @param mdpEmployee Le mot de passe de l'employé.
     * @param idRole L'identifiant du rôle de l'employé.
     * @return `true` si la mise à jour a réussi, `false` sinon.
     */
    public Employee modifierEmployee(int idEmployee, String nomEmployee, String emailEmployee, String mdpEmployee, int idRole) {
        String query = "UPDATE employee SET nom_employee = ?, email_emplyee = ?, mdp_employee = ?, id_role = ? WHERE id_empmoyee = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nomEmployee);
            stmt.setString(2, emailEmployee);
            stmt.setString(3, mdpEmployee);
            stmt.setInt(4, idRole); // Utilisation de l'identifiant du rôle
            stmt.setInt(5, idEmployee);
            int rowsAffected = stmt.executeUpdate();
            return new Employee(idEmployee, nomEmployee, emailEmployee, mdpEmployee, roleEmployeeDAO.chercherRoleParId(idRole));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Méthode pour supprimer un employé
    /**
     * Supprime un employé de la base de données.
     * @param idEmployee L'identifiant unique de l'employé à supprimer.
     * @return `true` si la suppression a réussi, `false` sinon.
     */
    public boolean supprimerEmployee(int idEmployee) {
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

    /**
     * Chercher un employé par email
     * @param email
     * @return objet 'Employee"
     */
    public Employee chercherEmployeeParEmail(String email) {
        try{
            String sql = "SELECt * FROM employee WHERE email_emplyee = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1,email);
            ResultSet rs =stmt.executeQuery();
            if(rs.next()) {
                RoleEmployeeDAO roleEmployee = new RoleEmployeeDAO();
                return new Employee(
                        rs.getInt("id_employee"),
                        rs.getString("nom_employee"),
                        rs.getString("email_emplyee"),
                        rs.getString("mdp_employee"),
                        roleEmployee.chercherRoleParId(rs.getInt("id_role"))
                );
            }
        }catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
