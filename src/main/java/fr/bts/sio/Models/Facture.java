public class Facture {

    private int idFactures;
    private String chemin;
    private String nomFichier;
    private float tva;
    private float prix;

    public Facture(int idFactures, String chemin, String nomFichier, float tva, float prix) {
        this.idFactures = idFactures;
        this.chemin = chemin;
        this.nomFichier = nomFichier;
        this.tva = tva;
        this.prix = prix;
    }

    public Facture() {

    }

    public int getIdFactures() {
        return idFactures;
    }

    public String getChemin() {
        return chemin;
    }

    public String getNomFichier() {
        return nomFichier;
    }

    public float getTva() {
        return tva;
    }

    public float getPrix() {
        return prix;
    }

    public void setIdFactures(int idFactures) {
        this.idFactures = idFactures;
    }

    public void setChemin(String chemin) {
        this.chemin = chemin;
    }

    public void setNomFichier(String nomFichier) {
        this.nomFichier = nomFichier;
    }

    public void setTva(float tva) {
        this.tva = tva;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    @Override
    public String toString() {
        return "Facture{" +
                "idFactures=" + idFactures +
                ", chemin='" + chemin + '\'' +
                ", nomFichier='" + nomFichier + '\'' +
                ", tva=" + tva +
                ", prix=" + prix +
                '}';
    }
}
