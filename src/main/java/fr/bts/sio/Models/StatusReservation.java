public class StatusReservation {

    private int idStatus;
    private String libelle;

    public StatusReservation(int id_status, String libelle) {
        this.idStatus = id_status;
        this.libelle = libelle;
    }

    public StatusReservation() {

    }

    public int getIdStatus() {
        return idStatus;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setIdStatus(int idStatus) {
        this.idStatus = idStatus;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    @Override
    public String toString() {
        return "StatusReservation{" +
                "idStatus=" + idStatus +
                ", libelle='" + libelle + '\'' +
                '}';
    }
}
