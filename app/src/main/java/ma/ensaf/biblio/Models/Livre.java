package ma.ensaf.biblio.Models;

public class Livre {

    private String titre, IdLivre;
    private String autheur;
    private String numeroPages;
    private String description, CategoriId;

    private String imageLivre,uid;
    private long timestamp;


    public Livre(){}

    public Livre(String titre, String idLivre, String autheur, String numeroPages, String description, String categoriId, String imageLivre, String uid, long timestamp) {
        this.titre = titre;
        IdLivre = idLivre;
        this.autheur = autheur;
        this.numeroPages = numeroPages;
        this.description = description;
        CategoriId = categoriId;
        this.imageLivre = imageLivre;
        this.uid = uid;
        this.timestamp = timestamp;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getIdLivre() {
        return IdLivre;
    }

    public void setIdLivre(String idLivre) {
        IdLivre = idLivre;
    }

    public String getAutheur() {
        return autheur;
    }

    public void setAutheur(String autheur) {
        this.autheur = autheur;
    }

    public String getNumeroPages() {
        return numeroPages;
    }

    public void setNumeroPages(String numeroPages) {
        this.numeroPages = numeroPages;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategoriId() {
        return CategoriId;
    }

    public void setCategoriId(String categoriId) {
        CategoriId = categoriId;
    }

    public String getImageLivre() {
        return imageLivre;
    }

    public void setImageLivre(String imageLivre) {
        this.imageLivre = imageLivre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
}
