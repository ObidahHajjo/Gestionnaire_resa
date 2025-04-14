package fr.bts.sio;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DB {

    public void start() {

        System.out.println("Running App ...");
        String url = "jdbc:h2:./testdb";
        try {
            Class.forName("org.h2.Driver");
            Connection connection = DriverManager.getConnection(url);
            System.out.println("Connexion réussie à H2 !");
            Statement stmt = connection.createStatement();

            // Table creation
            stmt.execute("CREATE TABLE IF NOT EXISTS type_chambre(id_type_chambre INT PRIMARY KEY AUTO_INCREMENT, libelle VARCHAR(50));");
            System.out.println("la table type_chamrbre a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS clients(id_client INT PRIMARY KEY AUTO_INCREMENT, nom VARCHAR(50) NOT NULL, prenom VARCHAR(50) NOT NULL, telephone VARCHAR(13), email VARCHAR(50) UNIQUE NOT NULL);");
            System.out.println("la table clients a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS status_reservation(id_status INT PRIMARY KEY AUTO_INCREMENT, libelle VARCHAR(50) NOT NULL);");
            System.out.println("la table status_reservation a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS type_paiement(id_type_paiement INT PRIMARY KEY AUTO_INCREMENT, libelle VARCHAR(50) NOT NULL);");
            System.out.println("la table type_paiement a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS factures(id_factures INT PRIMARY KEY AUTO_INCREMENT, chemin VARCHAR(100) NOT NULL UNIQUE, nom_fichier VARCHAR(50) NOT NULL, prix DECIMAL(7,2) NOT NULL);");
            System.out.println("la table factures a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS role_employee(id_role INT PRIMARY KEY AUTO_INCREMENT, libelle VARCHAR(50) NOT NULL UNIQUE);");
            System.out.println("la table role_employee a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS employee(id_empmoyee INT PRIMARY KEY AUTO_INCREMENT, nom_employee VARCHAR(50) NOT NULL, email_emplyee VARCHAR(100) NOT NULL UNIQUE, mdp_employee VARCHAR(64) NOT NULL, id_role INT NOT NULL, FOREIGN KEY(id_role) REFERENCES role_employee(id_role));");
            System.out.println("la table employee a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS reservations(id_res INT PRIMARY KEY AUTO_INCREMENT, dateResDebut DATETIME NOT NULL, dateResFin DATETIME NOT NULL, numbre_personnes INT NOT NULL, petit_dejuner INT, id_empmoyee INT NOT NULL, id_factures INT NOT NULL, id_status INT NOT NULL, id_client INT NOT NULL, FOREIGN KEY(id_empmoyee) REFERENCES employee(id_empmoyee), FOREIGN KEY(id_factures) REFERENCES factures(id_factures), FOREIGN KEY(id_status) REFERENCES status_reservation(id_status), FOREIGN KEY(id_client) REFERENCES clients(id_client));");
            System.out.println("la table reservations a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS chambres(id_chambre INT PRIMARY KEY AUTO_INCREMENT, numero_chambre INT NOT NULL, id_res INT, id_type_chambre INT NOT NULL, FOREIGN KEY(id_res) REFERENCES reservations(id_res), FOREIGN KEY(id_type_chambre) REFERENCES type_chambre(id_type_chambre));");
            System.out.println("la table chambres a bien été créé");
            stmt.execute("CREATE TABLE IF NOT EXISTS effectue(id_type_paiement INT, id_factures INT, PRIMARY KEY(id_type_paiement, id_factures), FOREIGN KEY(id_type_paiement) REFERENCES type_paiement(id_type_paiement), FOREIGN KEY(id_factures) REFERENCES factures(id_factures));");
            System.out.println("la table effectue a bien été créé");

            // Fake data inserts
            for (int i = 1; i <= 10; i++) {
                stmt.execute("INSERT INTO type_chambre(libelle) VALUES('Type " + i + "')");
                stmt.execute("INSERT INTO clients(nom, prenom, telephone, email) VALUES('Nom" + i + "', 'Prenom" + i + "', '060000000" + i + "', 'client" + i + "@test.com')");
                stmt.execute("INSERT INTO status_reservation(libelle) VALUES('Status " + i + "')");
                stmt.execute("INSERT INTO type_paiement(libelle) VALUES('Paiement " + i + "')");
                stmt.execute("INSERT INTO factures(chemin, nom_fichier, prix) VALUES('chemin/facture" + i + ".pdf', 'facture" + i + ".pdf', " + (100.00 + i) + ")");
                stmt.execute("INSERT INTO role_employee(libelle) VALUES('Role " + i + "')");
                stmt.execute("INSERT INTO employee(nom_employee, email_emplyee, mdp_employee, id_role) VALUES('Employe" + i + "', 'employe" + i + "@hotel.com', 'pass" + i + "', " + i + ")");
                System.out.println("INSERT 1 :" +i);
            }

            for (int i = 1; i < 10; i++) {
                stmt.execute("INSERT INTO reservations(dateResDebut, dateResFin, numbre_personnes, petit_dejuner, id_empmoyee, id_factures, id_status, id_client) VALUES('2024-04-0" + i + " 14:00:00', '2024-04-1" + i + " 10:00:00', 2, 1, " + i + ", " + i + ", " + i + ", " + i + ")");
                stmt.execute("INSERT INTO chambres(numero_chambre, id_res, id_type_chambre) VALUES(" + (100 + i) + ", " + i + ", " + i + ")");
                stmt.execute("INSERT INTO effectue(id_type_paiement, id_factures) VALUES(" + i + ", " + i + ")");
                System.out.println("INSERT 2 : " +i);
            }

            System.out.println("Toutes les tables et les données ont été créées.");

        } catch (SQLException | ClassNotFoundException ex) {
            throw new RuntimeException(ex);
        }
    }
}
