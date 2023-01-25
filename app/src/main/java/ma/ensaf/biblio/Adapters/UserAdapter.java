package ma.ensaf.biblio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.EspaceAdmin.OuvragesActivity;
import ma.ensaf.biblio.R;

import java.util.ArrayList;

import ma.ensaf.biblio.Filters.FilterUser;
import ma.ensaf.biblio.Models.User;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.HolderUser> implements Filterable {

    private Context context;
    public ArrayList<User> UsersArrayList, filterList;

    private FilterUser filter;

    public UserAdapter(Context context, ArrayList<User> UsersArrayList) {
        this.context = context;
        this.UsersArrayList = UsersArrayList;
        this.filterList = UsersArrayList;
    }

    @NonNull
    @Override
    public HolderUser onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_etudiants, parent, false);
        return new HolderUser(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderUser holder, int position) {

        //get data
        User model = UsersArrayList.get(position);
        String fullname = model.getFullName();
        String uid = model.getUid();

        //set data
        holder.fullname.setText(fullname);

    //click go to DetailsUserActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, OuvragesActivity.class);
                intent.putExtra("fullnameUser", fullname);
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return UsersArrayList.size();
    }

    @Override
    public Filter getFilter() {

        if(filter == null){
            filter = new FilterUser(filterList, this);
        }
        return filter;
    }


    class HolderUser extends RecyclerView.ViewHolder{

        TextView fullname;

        public HolderUser(@NonNull View itemView) {
            super(itemView);

            fullname = (TextView) itemView.findViewById(R.id.item_nometudiant);
        }
    }
}
