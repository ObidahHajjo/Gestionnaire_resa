package fr.bts.sio.Interface;

import fr.bts.sio.Models.Employee;

import java.util.List;

public interface EmployeeDAOInterface {

    boolean ajouterEmployee(Employee employee);
    Employee chercherEmployeeParId(int id_employee);
    List<Employee> chercherTousLesEmployees();
    boolean modifierEmployee(int idEmployee, String nomEmployee, String emailEmployee, String mdpEmployee, int idRole);
    boolean supprimerEmployee(int id_employee);
    Employee chercherEmployeeParEmail(String emailEmployee);
}
