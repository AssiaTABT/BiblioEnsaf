package ma.ensaf.biblio;

public class Book {
   String Titre,Auteur,Description,Image,bookId;
   int Views=0;
   //boolean IsAvailable,

    public int getViews() {
        return Views;
    }

    public int setViews(int views) {
        return Views = views;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Book(String titre, String auteur, String description, String image) {
        Titre = titre;
        Auteur = auteur;
        Description = description;
        Image = image;
    }
    public Book(String titre, String description) {
        Titre = titre;

        Description = description;

    }
public  Book(){}
    public String getTitre() {
        return Titre;
    }

    public String getAuteur() {
        return Auteur;
    }

    public String getDescription() {
        return Description;
    }

    public String getImage() {
        return Image;
    }

    public void setTitre(String titre) {
        Titre = titre;
    }

    public void setAuteur(String auteur) {
        Auteur = auteur;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setImage(String image) {
        Image = image;
    }
}
