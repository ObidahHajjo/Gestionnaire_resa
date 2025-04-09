package fr.bts.sio.Models;

public class Employee {

    private int idEmployee;
    private String nomEmployee;
    private String emailEmployee;
    private String mdpEmployee;
    private RoleEmployee role;

    //constructor
    public Employee(int idEmployee, String nomEmployee, String emailEmployee, String mdpEmployee, RoleEmployee role) {
        this.idEmployee = idEmployee;
        this.nomEmployee = nomEmployee;
        this.emailEmployee = emailEmployee;
        this.mdpEmployee = mdpEmployee;
        this.role = role;
    }

    public Employee() {
    }

    // Getters
    public int getIdEmployee() {
        return idEmployee;
    }

    public String getNomEmployee() {
        return nomEmployee;
    }

    public String getEmailEmployee() {
        return emailEmployee;
    }

    public String getMdpEmployee() {
        return mdpEmployee;
    }

    public RoleEmployee getRole() {
        return role;
    }

    // Setters
    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public void setNomEmployee(String nomEmployee) {
        this.nomEmployee = nomEmployee;
    }

    public void setEmailEmployee(String emailEmployee) {
        this.emailEmployee = emailEmployee;
    }

    public void setMdpEmployee(String mdpEmployee) {
        this.mdpEmployee = mdpEmployee;
    }

    public void setRole(RoleEmployee role) {
        this.role = role;
    }

    //ToString
    @Override
    public String toString() {
        return "Employee{" +
                "idEmployee=" + idEmployee +
                ", nomEmployee='" + nomEmployee + '\'' +
                ", emailEmployee='" + emailEmployee + '\'' +
                ", mdpEmployee='" + mdpEmployee + '\'' +
                ", role=" + role +
                '}';
    }
}
