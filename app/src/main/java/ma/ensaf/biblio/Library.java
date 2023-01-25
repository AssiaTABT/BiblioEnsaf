package ma.ensaf.biblio;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;

public class Library {

    private ArrayList<Borrow> library ;

    public Library () {
        library = new ArrayList<Borrow>();
    }

    public void sortLibrary(){
        Collections.sort(library);
    }

    public void addEntry( Borrow entry ) {
        library.add(entry);
        sortLibrary();
    }

    public void removeEntry ( Borrow entry ) {
        if (library.contains(entry)) {
            library.remove(entry);
            sortLibrary();
        }
        else Log.d ( "Library" , "Entry Not Found");
    }

    public ArrayList<Borrow> getLibrary() {
        sortLibrary();
        return library;
    }

    @Override
    public String toString() {
        return "Library{" +
                "library=" + library +
                '}';
    }
}