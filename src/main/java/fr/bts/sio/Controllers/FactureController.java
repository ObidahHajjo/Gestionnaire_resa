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
     * @param prix Le prix total de la facture
     */
    public void ajouterFacture(String chemin, String nomFichier, float prix) {
        Facture f = new Facture(0, chemin, nomFichier, prix); // Création d'un objet Facture

            factureDAO.ajouterFacture(chemin, nomFichier, prix); // Ajout de la facture dans la base via le DAO
            if (f != null ) {
                System.out.println("Facture ajoutée avec succès !");
            }else {
                System.out.println("Erreur lors de l'ajout : ");
            }

    }

    /**
     * Afficher toutes les factures existantes
     */
    public void afficherToutesLesFactures() {
            List<Facture> factures = factureDAO.chercherToutesFactures(); // Récupère toutes les factures
            for (Facture f : factures) {
                System.out.println(f); // Affiche chaque facture (via toString())
            }
    }

    /**
     * Affiche une facture spécifique en fonction de son ID
     * @param id L'identifiant unique de la facture
     */
    public void afficherFactureParId(int id) {
            Facture f = factureDAO.chercherFactureParId(id); // Récupère la facture via son ID
            if (f != null) {
                System.out.println(f); // Affiche la facture trouvée
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + id);
            }
    }

    /**
     * Modifier une facture existante
     * @param chemin Nouveau chemin du fichier
     * @param NomFichier Nouveau nom du fichier
     * @param prix Nouveau prix
     * @param idFactures L'identifiant de la facture à modifier
     */
    public void modifierFacture(String chemin, String NomFichier, float prix, int idFactures) {
            Facture factureExistante = factureDAO.chercherFactureParId(idFactures); // Récupère la facture existante
            if (factureExistante != null) {
                // Mise à jour des données de la facture
                factureExistante.setChemin(chemin);
                factureExistante.setNomFichier(NomFichier);
                factureExistante.setPrix(prix);
                factureExistante.setIdFactures(idFactures);
                //factureDAO.modifierFacture(); // Mise à jour dans la base via le DAO
                System.out.println("Facture modifiée !");
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + idFactures);
            }
    }

    /**
     * Supprime une facture par son ID
     * @param idFactures L'identifiant de la facture à supprimer
     */
    public void supprimerFacture(int idFactures) {
            Facture f = factureDAO.chercherFactureParId(idFactures); // Récupère la facture à supprimer
            if (f != null) {
                factureDAO.supprimerFacture(idFactures); // Supprime la facture via le DAO
                System.out.println("La facture " + idFactures + " a été supprimée");
            } else {
                System.out.println("Aucune facture trouvée avec l'ID " + idFactures);
            }
    }
}

