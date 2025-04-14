package fr.bts.sio.Controllers;

// Importations nécessaires pour les interactions avec le modèle et la base de données
import fr.bts.sio.DAO.FactureDAO;
import fr.bts.sio.Models.Facture;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Classe FactureController
 * Cette classe agit comme un pont entre la couche interface utilisateur (ou une autre couche)
 * et la base de données en passant par le DAO pour les opérations liées aux factures.
 */
public class FactureController {

    // Une instance de FactureDAO pour exécuter les opérations sur la base de données.
    private FactureDAO factureDAO;

    /**
     * Constructeur
     * @param connection La connexion SQL pour initialiser le DAO
     */
    public FactureController(Connection connection) {
        this.factureDAO = new FactureDAO(connection);
    }

    /**
     * Ajouter une facture dans la base de données
     * @param chemin Le chemin du fichier de la facture
     * @param nomFichier Le nom du fichier
     * @param tva La TVA appliquée
     * @param prix Le prix total de la facture
     */
    public void ajouterFacture(String chemin, String nomFichier, float tva, float prix) {
        Facture f = new Facture(0, chemin, nomFichier, tva, prix); // Création d'un objet Facture
        try {
            factureDAO.ajouterFacture(f); // Ajout de la facture dans la base via le DAO
            System.out.println("Facture ajoutée avec succès !");
        } catch (SQLException e) {
            // Gestion des erreurs SQL lors de l'ajout
            System.out.println("Erreur lors de l'ajout : " + e.getMessage());
        }
    }

    /**
     * Afficher toutes les factures existantes
     */
    public void afficherToutesLesFactures() {
        try {
            List<Facture> factures = factureDAO.chercherToutesLesFactures(); // Récupère toutes les factures
            for (Facture f : factures) {
                System.out.println(f); // Affiche chaque facture (via toString())
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL lors de la récupération
            System.out.println("Erreur lors de l'affichage : " + e.getMessage());
        }
    }

    /**
     * Affiche une facture spécifique en fonction de son ID
     * @param id L'identifiant unique de la facture
     */
    public void afficherFactureParId(int id) {
        try {
            Facture f = factureDAO.chercherFactureParId(id); // Récupère la facture via son ID
            if (f != null) {
                System.out.println(f); // Affiche la facture trouvée
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + id);
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL lors de l'opération
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
    }

    /**
     * Modifier une facture existante
     * @param chemin Nouveau chemin du fichier
     * @param NomFichier Nouveau nom du fichier
     * @param tva Nouvelle TVA
     * @param prix Nouveau prix
     * @param idFactures L'identifiant de la facture à modifier
     */
    public void modifierFacture(String chemin, String NomFichier, float tva, float prix, int idFactures) {
        try {
            Facture factureExistante = factureDAO.chercherFactureParId(idFactures); // Récupère la facture existante
            if (factureExistante != null) {
                // Mise à jour des données de la facture
                factureExistante.setChemin(chemin);
                factureExistante.setNomFichier(NomFichier);
                factureExistante.setTva(tva);
                factureExistante.setPrix(prix);
                factureExistante.setIdFactures(idFactures);
                factureDAO.updateFacture(factureExistante); // Mise à jour dans la base via le DAO
                System.out.println("Facture modifiée !");
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + idFactures);
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL lors de la modification
            System.out.println("Erreur lors de la modification : " + e.getMessage());
        }
    }

    /**
     * Supprime une facture par son ID
     * @param idFactures L'identifiant de la facture à supprimer
     */
    public void supprimerFacture(int idFactures) {
        try {
            Facture f = factureDAO.chercherFactureParId(idFactures); // Récupère la facture à supprimer
            if (f != null) {
                factureDAO.supprimerFacture(idFactures); // Supprime la facture via le DAO
                System.out.println("La facture " + idFactures + " a été supprimée");
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + idFactures);
            }
        } catch (SQLException e) {
            // Gestion des erreurs SQL lors de la suppression
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
        }
    }
}

