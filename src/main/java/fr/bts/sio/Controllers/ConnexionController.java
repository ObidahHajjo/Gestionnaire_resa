package fr.bts.sio.Controllers;

import fr.bts.sio.App;
import fr.bts.sio.DAO.EmployeeDAO;
import fr.bts.sio.Models.Employee;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Connection;
import org.mindrot.jbcrypt.BCrypt;


public class ConnexionController {
    private final Connection connection;
    @FXML
    private TextField login;
    @FXML
    private TextField password;
    @FXML
    private Label error;
    @FXML
    private Label loginError;
    @FXML
    private Label passwordError;
    @FXML
    private Button valider;


    public ConnexionController(Connection connection) {
        this.connection = connection;
    }

    @FXML
    public void onClearClick() {
        login.clear();
        password.clear();
        error.setText("");
        loginError.setText("");
        passwordError.setText("");
    }

    @FXML
    public void onCloseClick() {
        Platform.exit();
    }

    @FXML
    public void onVerifyClick() throws IOException{
        error.setText("");
        loginError.setText("");
        passwordError.setText("");
        String login = this.login.getText();
        String pass = password.getText();

        Employee employee = chercherEmployeeParEmail(login);
        boolean isMatch = verifierMDP(pass, employee.getMdpEmployee());

        if(employee == null && !isMatch) {
            error.setText("Login and password are incorrect!");
            return;
        }
        if(employee == null) {
            loginError.setText("Email est incorrect!");
            return;
        }
        if(!isMatch) {
            passwordError.setText("Password is incorrect!");
            return;
        }

        switch (employee.getRole().getLibelle()) {
            case "admin" -> admin();
            case  "employee" -> employee();
        }
    }

    public void admin() throws IOException{
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource("dashboard-admin.fxml"));
            Scene scene = new Scene(loader.load(), 650, 400);

            // Access the controller if you need to pass data
            DashboardAdminController controller = loader.getController();
            // controller.setEmployee(currentEmployee); // optional

            Stage stage = (Stage) valider.getScene().getWindow();
            stage.setTitle("Admin Dashboard");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void employee() throws IOException{
        try {
            FXMLLoader loader= new FXMLLoader();
            loader.setLocation(App.class.
                    getResource("dashboard-employee.fxml"));
            Scene scene = new Scene(loader.load(), 1440, 1080);
            Stage stage = (Stage) valider.getScene().getWindow();
            stage.setTitle("Accueil");
            stage.setScene(scene);
            stage.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private Employee chercherEmployeeParEmail(String email) {
        EmployeeDAO employeeDAO = new EmployeeDAO(connection);
        return employeeDAO.chercherEmployeeParEmail(email);
    }
    //TODO : Ajouter la méthode ChercherEmployeeParEmail()
    private boolean verifierMDP(String mdp, String mdpHashe) {;
        return BCrypt.checkpw(mdp, mdpHashe);
    }
}
