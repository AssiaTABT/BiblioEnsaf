package ma.ensaf.biblio.Models;

public class Categorie {
    String IdCat, titre, uid;

    public Categorie() {

    }

    public Categorie(String idCat, String titre, String uid) {
        IdCat = idCat;
        this.titre = titre;
        this.uid = uid;
    }

    public String getIdCat() {
        return IdCat;
    }

    public void setIdCat(String idCat) {
        IdCat = idCat;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
