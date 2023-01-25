package ma.ensaf.biblio;



import org.joda.time.LocalDate;
import org.joda.time.Days;



public class Borrow implements Comparable {
    Book book ;
    LocalDate borrowDate; // Borrow Date immutable
    LocalDate dueDate;

    public void setBook(Book book) {
        this.book = book;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Borrow() {
    }

    //Constructor using numOfDays; Initialises borrowDate to (now); and dueDate to borrowDate.day  + numDays;
    public Borrow(Book book ) {
        this.book = book;
        this.borrowDate = new LocalDate();
        this.dueDate = borrowDate.plusDays(7);
    }


    //To String
    @Override
    public String toString() {
        return "Entry{" +
                "book=" + book.getTitre() +
                ", borrowDate=" + borrowDate +
                ", dueDate=" + dueDate +
                ", Days Left = " + getDaysLeft() +
                ", Percentage = " + getPercentDaysPassed() +
                '}';
    }




    //Getters and Setters
    public Book getBook() {
        return book;
    }

    public int getPercentDaysPassed() {
        //Returns Percentage of Days passed if it is > 10;
        // If 10 or less returns 10, for graphical clarity on progress bar
        double passed = Days.daysBetween(borrowDate ,new LocalDate()).getDays();
        double totalPeriod = Days.daysBetween(borrowDate, dueDate).getDays();
        if (totalPeriod == 0 ) return 100;
        if ( ((passed / totalPeriod)*100) <= 10 ) return 10;
        else return  (int)  ((passed / totalPeriod)*100);

    }

    public int getDaysLeft() {
        if (Days.daysBetween(new LocalDate() , dueDate).getDays() <0 )
            return 0;
        return Days.daysBetween(new LocalDate() , dueDate).getDays();

    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public int compareTo(Object another) {
        if ( this.getDaysLeft() > ((Borrow)another).getDaysLeft()) return 1;
        else if (this.getDaysLeft() < ((Borrow) another).getDaysLeft()) return -1;
        else return 0;
    }
}
