package ma.ensaf.biblio.Filters;

import android.widget.Filter;

import ma.ensaf.biblio.Adapters.CategorieAdapter;
import ma.ensaf.biblio.Models.Categorie;

import java.util.ArrayList;


public class FilterCategorie extends Filter {

    ArrayList<Categorie> filterList;
    CategorieAdapter categorieAdapter;

    public FilterCategorie(ArrayList<Categorie> filterList, CategorieAdapter categorieAdapter) {
        this.filterList = filterList;
        this.categorieAdapter = categorieAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length()>0){

            charSequence = charSequence.toString().toUpperCase();
            ArrayList<Categorie> filteredModels = new ArrayList<>();

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
        categorieAdapter.CategorieArrayList = (ArrayList<Categorie>)filterResults.values;

        categorieAdapter.notifyDataSetChanged();
    }
}
