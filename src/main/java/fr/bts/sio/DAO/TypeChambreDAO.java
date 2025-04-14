package fr.bts.sio.DAO;

import fr.bts.sio.Models.TypeChambre;

import java.lang.reflect.Type;
import java.sql.*;
import java.util.List;

public class TypeChambreDAO {
    private Connection connection;

    public TypeChambreDAO(Connection connection) {
        this.connection = connection;

    }

    public void ajouterTypeChambre(int idTypeChambre, String libelle) {
        String sql = "INSERT INTO TypeChambre (idTypeChambre, libelle) VALUES (?, ?)";

        try (PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idTypeChambre);
            stmt.setString(2, libelle);

        }catch(Exception e){
            System.out.println("Erreur lors de l'ajout du type de chambre : " + e.getMessage());
        }

    }

    public TypeChambre chercherTypeChambreParId(int idTypeChambre) {
        String sql = "SELECT * FROM TypeChambre WHERE idTypeChambre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idTypeChambre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()){
                return new TypeChambre(
                        rs.getInt("idTypeChambre"),
                        rs.getString("libelle")
                );
            }
        }catch(SQLException e){
            System.out.println("Erreur lors de la récupération du type de chambre : " + e.getMessage());
        }
        return null;
    }

    public List<TypeChambre> chercherTotutesTypeChambres() {
        List<TypeChambre> typeChambres = new java.util.ArrayList<>();

        String sql = "SELECT * FROM TypeChambre";
        try (Statement stmt = connection.createStatement()){
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                TypeChambre typeChambre = new TypeChambre(
                        rs.getInt("idTypeChambre"),
                        rs.getString("libelle")
                );
                typeChambres.add(typeChambre);
            }
        }catch(SQLException e){
            System.out.println("Erreur lors du traitement de la recherche des types de chambres : " + e.getMessage());
        } return typeChambres;
    }

    public void modifierTypeChambre(int idTypeChambre, String libelle) {
        String sql = "UPDATE TypeChambre SET libelle = ? WHERE idTypeChambre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idTypeChambre);
            stmt.setString(2, libelle);
        }catch(SQLException e){
            System.out.println("Erreur de modification du type de chambre : " + e.getMessage());
        }
    }

    public void supprimerTypeChambre(int idTypeChambre) {
        String sql = "DELETE FROM TypeChambre WHERE idTypeChambre = ?";

        try(PreparedStatement stmt = connection.prepareStatement(sql)){
            stmt.setInt(1, idTypeChambre);
            stmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Erreur de suppression du type de chambre : " + e.getMessage());
        }
    }

}
