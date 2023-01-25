package ma.ensaf.biblio.Filters;

import android.widget.Filter;

import java.util.ArrayList;

import ma.ensaf.biblio.Adapters.UserAdapter;
import ma.ensaf.biblio.Models.User;

public class FilterUser extends Filter {

    ArrayList<User> filterList;
    UserAdapter userAdapter;

    public FilterUser(ArrayList<User> filterList, UserAdapter userAdapter) {
        this.filterList = filterList;
        this.userAdapter = userAdapter;
    }

    @Override
    protected FilterResults performFiltering(CharSequence charSequence) {

        FilterResults results = new FilterResults();
        if(charSequence != null && charSequence.length()>0){

            charSequence = charSequence.toString().toUpperCase();
            ArrayList<User> filteredModels = new ArrayList<>();

            for(int i=0; i<filterList.size(); i++){

                if(filterList.get(i).getFullName().toUpperCase().contains(charSequence)){
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
        userAdapter.UsersArrayList = (ArrayList<User>)filterResults.values;

        userAdapter.notifyDataSetChanged();
    }
}
