package ma.ensaf.biblio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.R;

import java.util.ArrayList;

import ma.ensaf.biblio.Models.User;

public class ProfAdapter extends  RecyclerView.Adapter<ProfAdapter.HolderProf>{

    private Context context;
    public ArrayList<User> UsersArrayList;

    public ProfAdapter(Context context, ArrayList<User> usersArrayList) {
        this.context = context;
        UsersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public HolderProf onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_etudiants, parent, false);
        return new HolderProf(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProf holder, int position) {

        //get data
        User model = UsersArrayList.get(position);
        String fullname = model.getFullName();
        String uid = model.getUid();
        String email = model.getEmail();
        String pw = model.getPassword();
        String filiere = model.getFiliere();
        String suerType = model.getUserType();

        //set data
        holder.NomPrenom.setText(fullname);
        holder.email.setText(email);

    }

    @Override
    public int getItemCount() {
        return UsersArrayList.size();
    }


    class HolderProf extends RecyclerView.ViewHolder{

        TextView NomPrenom, email;
        //ImageButton more;

        public HolderProf(@NonNull View itemView) {
            super(itemView);

            NomPrenom = (TextView) itemView.findViewById(R.id.item_nometudiant);
            email = (TextView) itemView.findViewById(R.id.emailTextView);
           // more = (ImageButton) itemView.findViewById(R.id.morBtn);
        }
    }
}
