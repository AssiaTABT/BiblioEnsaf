package ma.ensaf.biblio;

import android.widget.Filter;



import java.util.ArrayList;

import ma.ensaf.biblio.Models.Livre;

public class FilterBooks2A extends Filter {

    ArrayList<Livre> filterList;
    MyAdapter BookAdapter;

    public FilterBooks2A(ArrayList<Livre> filterList, MyAdapter BookAdapter) {
        this.filterList = filterList;
        this.BookAdapter = BookAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length()>0){

            charSequence = charSequence.toString().toUpperCase();
            ArrayList<Livre> filteredModels = new ArrayList<>();

            for(int i=0; i<filterList.size(); i++){

                if(filterList.get(i).getTitre().toUpperCase().contains(charSequence)){
                    filteredModels.add(filterList.get(i));
                }
            }

            results.count = filteredModels.size();
            results.values = filteredModels;
        }else {

            results.count = filterList.size();
            results.values = filterList;
        }
        return results;
    }

    @Override
    protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        BookAdapter.list = (ArrayList<Livre>)filterResults.values;

        BookAdapter.notifyDataSetChanged();
    }
}
