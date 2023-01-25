package ma.ensaf.biblio.Models;

public class User {
    String FullName, Email, Password, userType, uid, Filiere, imageProfil;

    public String getFullName() {
        return FullName;
    }

    public void setFullName(String fullName) {
        FullName = fullName;
    }

    public String getEmail() {
        return Email;
    }

    public void setImg(String img) {
        img = imageProfil;
    }
    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }
    public String getImg() {
        return imageProfil;
    }

    public String getUid() {
        return uid;
    }
    public String getFiliere() {
        return Filiere;
    }

    public User() {
    }

    public void setFiliere(String Filiere) {
        Filiere = Filiere;
    }
    public void setUid(String Uid) {
        Uid = Uid;
    }
    public void setPassword(String password) {
        Password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public User(String fullName, String email, String password, String userType, String uid, String img) {
        FullName = fullName;
        Email = email;
        Password = password;
        this.userType = userType;
        this.uid = uid;
        this.imageProfil=img;
    }


}
