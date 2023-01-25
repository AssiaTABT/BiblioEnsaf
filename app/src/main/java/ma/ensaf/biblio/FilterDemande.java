package ma.ensaf.biblio;

/*
public class FilterDemande extends Filter {

    ArrayList<Emprun> filterList;
    CategorieAdapter categorieAdapter;

    public FilterDemande(ArrayList<Emprun> filterList, CategorieAdapter categorieAdapter) {
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
}*/
