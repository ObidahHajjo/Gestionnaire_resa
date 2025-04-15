package fr.bts.sio.Controllers;

// Importation des classes nécessaires
import fr.bts.sio.DAO.StatutReservationDAO;
import fr.bts.sio.Models.StatutReservation;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Cette classe est le contrôleur pour gérer les statuts des réservations.
 * Elle agit comme une passerelle entre la couche DAO (accès aux données) et l'application.
 */
public class StatutReservationController {

    // Instance du DAO pour gérer les opérations sur les statuts de réservation
    private StatutReservationDAO statutReservationDAO;

    /**
     * Constructeur du contrôleur qui initialise le DAO avec une connexion SQL.
     *
     * @param connection Connexion SQL utilisée pour interagir avec la base de données.
     */
    public StatutReservationController(Connection connection) {
        this.statutReservationDAO = new StatutReservationDAO(connection);
    }

    /**
     * Méthode pour afficher tous les statuts de réservation.
     * Cette méthode utilise le DAO pour récupérer tous les statuts depuis la base de données
     * et les affiche dans la console.
     */
    public void afficherToutesLesReservations() {

        // Appel de la méthode DAO pour récupérer tous les statuts
        List<StatutReservation> statutReservation = statutReservationDAO.chercherTousStatutsReservations();

        // Vérifie si des statuts sont récupérés, sinon affiche un message vide
        if (statutReservation.isEmpty()) {
            System.out.println("Aucun statut de réservation trouvé.");
        } else {
            // Parcourt et affiche tous les statuts
            for (StatutReservation s : statutReservation) {
                System.out.println(s);
            }
        }
    }

    /**
     * Méthode pour afficher un statut spécifique en fonction de son ID.
     *
     * @param id ID du statut que l'on souhaite afficher.
     */
    public void afficherStatutReservationParId(int id) {

        // Utilise le DAO pour récupérer le statut par ID
        StatutReservation s = statutReservationDAO.chercherStatutReservationParId(id);

        if (s != null) {
            // Affiche le statut trouvé
            System.out.println(s);
        } else {
            // Affiche un message si aucun statut n'est trouvé
            System.out.println("Aucun statut trouvé avec l'ID " + id);
        }
    }

    /**
     * Méthode pour modifier un statut de réservation existant.
     *
     * @param id_statut ID du statut à modifier.
     * @param libelle   Nouveau libellé du statut.
     */
    public void modifierStatut(int id_statut, String libelle) {

        // Recherche du statut par ID pour s'assurer qu'il existe
        StatutReservation s = statutReservationDAO.chercherStatutReservationParId(id_statut);

        if (s != null) {
            // Met à jour les propriétés du statut localement
            s.setIdStatut(id_statut);
            s.setLibelle(libelle);

            // Appel de la méthode DAO pour mettre à jour le statut dans la base de données
            statutReservationDAO.modifierStatutReservation(id_statut, libelle);

            System.out.println("Statut modifié avec succès.");
        } else {
            // Affiche un message d'erreur si aucun statut n'est trouvé
            System.out.println("Statut non trouvé avec l'ID " + id_statut);
        }
    }
}
