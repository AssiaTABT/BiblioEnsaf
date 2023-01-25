package ma.ensaf.biblio;

import org.joda.time.Days;
import org.joda.time.LocalDate;

public class Emprun {

    LocalDate borrowDate,dueDate;
    String uid,bookId,titre,nom;
    Integer daysLeft;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getTitre() {
        return titre;
    }



    public void setTitre(String titre) {
        this.titre = titre;
    }
    public int getPercentDaysPassed() {
        //Returns Percentage of Days passed if it is > 10;
        // If 10 or less returns 10, for graphical clarity on progress bar
        double passed = Days.daysBetween(borrowDate ,new LocalDate()).getDays();
        double totalPeriod = Days.daysBetween(borrowDate, dueDate).getDays();
        if (totalPeriod == 0 ) return 100;
        if ( ((passed / totalPeriod)*100) <= 7 ) return 7;
        else return  (int)  ((passed / totalPeriod)*100);

    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }


    public void setDaysLeft(Integer daysLeft) {
        this.daysLeft = daysLeft;
    }

    public Integer getDaysLeft() {
        return daysLeft;
    }

    public Emprun() {
    }

}

