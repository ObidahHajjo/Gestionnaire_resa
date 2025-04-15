package fr.bts.sio.Models;

/**
 * Modèle représentant une facture.
 * Chaque facture contient des informations comme son identifiant, chemin du fichier, nom du fichier,
 * taux de TVA et le prix total.
 */
public class Facture {

    // Attributs de la classe
    private int idFactures;       // Identifiant unique de la facture
    private String chemin;        // Chemin du fichier de la facture
    private String nomFichier;    // Nom du fichier de la facture
    private float prix;           // Prix total associé à la facture

    /**
     * Constructeur avec paramètres
     * @param idFactures L'identifiant unique de la facture
     * @param chemin Le chemin du fichier de la facture
     * @param nomFichier Le nom du fichier de la facture
     * @param prix Le prix total
     */
    public Facture(int idFactures, String chemin, String nomFichier, float prix) {
        this.idFactures = idFactures;
        this.chemin = chemin;
        this.nomFichier = nomFichier;
        this.prix = prix;
    }

    /**
     * Constructeur par défaut
     * Permet de créer une facture sans initialisation d'attributs.
     */
    public Facture() {
    }

    // Getters : Méthodes pour accéder aux attributs (lecture uniquement)
    public int getIdFactures() {
        return idFactures;
    }

    public String getChemin() {
        return chemin;
    }

    public String getNomFichier() {
        return nomFichier;
    }


    public float getPrix() {
        return prix;
    }

    // Setters : Méthodes pour modifier les attributs (écriture)
    public void setIdFactures(int idFactures) {
        this.idFactures = idFactures;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }


    public void setPrix(float prix) {
        this.prix = prix;
    }

    /**
     * Méthode `toString`
     * Utilisée pour afficher une représentation textuelle de la facture.
     * @return Une chaîne de caractères décrivant la facture
     */
    @Override
    public String toString() {
        return "Facture{" +
                "idFactures=" + idFactures +
                ", chemin='" + chemin + '\'' +
                ", nomFichier='" + nomFichier + '\'' +
                ", tva=" +
                ", prix=" + prix +
                '}';
    }
}