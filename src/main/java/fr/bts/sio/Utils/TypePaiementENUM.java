package fr.bts.sio.Utils;

/**
 * L'énumération TypePaiementENUM représente les différents types de paiements disponibles.
 * Elle associe un identifiant unique (idTypePaiement) et un libellé pour chaque type de paiement.
 */
public enum TypePaiementENUM {
    /**
     * Représente le paiement par carte bancaire.
     */
    CARTE_BANCAIRE(1, "Carte Bancaire"),

    /**
     * Représente le paiement via PayPal.
     */
    PAYPAL(2, "PayPal"),

    /**
     * Représente le paiement par chèque.
     */
    CHEQUE(3, "Chèque"),

    /**
     * Représente le paiement par virement bancaire.
     */
    VIREMENT(4, "Virement");

    private final int idTypePaiement;
    private final String libelle;

    /**
     * Constructeur de l'énumération TypePaiementENUM.
     *
     * @param idTypePaiement L'identifiant unique pour le type de paiement.
     * @param libelle Le libellé décrivant le type de paiement.
     */
    TypePaiementENUM(int idTypePaiement, String libelle) {
        this.idTypePaiement = idTypePaiement;
        this.libelle = libelle;
    }

    /**
     * Récupère l'identifiant unique associé au type de paiement.
     *
     * @return L'identifiant du type de paiement (entier).
     */
    public int getIdTypePaiement() {
        return idTypePaiement;
    }

    /**
     * Récupère le libellé décrivant le type de paiement.
     *
     * @return Le libellé du type de paiement (chaîne de caractères).
     */
    public String getLibelle() {
        return libelle;
    }

    /**
     * Trouve un type de paiement à partir de son identifiant unique.
     *
     * @param id L'identifiant unique du type de paiement.
     * @return Le type de paiement correspondant à l'identifiant.
     * @throws IllegalArgumentException Si l'identifiant fourni ne correspond à aucun type de paiement.
     */
    public static TypePaiementENUM fromId(int id) {
        for (TypePaiementENUM type : TypePaiementENUM.values()) {
            if (type.getIdTypePaiement() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid TypePaiementENUM id: " + id);
    }

    /**
     * Retourne une représentation sous forme de chaîne du type de paiement.
     *
     * @return Le libellé du type de paiement.
     */
    @Override
    public String toString() {
        return libelle;
    }
}