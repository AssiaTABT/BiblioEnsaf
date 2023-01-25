package ma.ensaf.biblio.Filters;

import android.widget.Filter;


import java.util.ArrayList;

import ma.ensaf.biblio.Models.Livre;
import ma.ensaf.biblio.Adapters.LivreAdapter;

public class FilterBooks extends Filter {

    ArrayList<Livre> filterList;
    LivreAdapter LivreAdapter;

    public FilterBooks(ArrayList<Livre> filterList, LivreAdapter LivreAdapter) {
        this.filterList = filterList;
        this.LivreAdapter = LivreAdapter;
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
        LivreAdapter.BookArrayList = (ArrayList<Livre>)filterResults.values;

        LivreAdapter.notifyDataSetChanged();
    }
}
