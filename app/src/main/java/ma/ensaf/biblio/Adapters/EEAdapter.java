package ma.ensaf.biblio.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.R;

import java.util.ArrayList;

import ma.ensaf.biblio.Models.User;

public class EEAdapter extends  RecyclerView.Adapter<EEAdapter.HolderProfEt>{

    private Context context;
    public ArrayList<User> UsersArrayList;

    public EEAdapter(Context context, ArrayList<User> usersArrayList) {
        this.context = context;
        UsersArrayList = usersArrayList;
    }

    @NonNull
    @Override
    public HolderProfEt onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_prof, parent, false);
        return new HolderProfEt(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderProfEt holder, int position) {

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
        holder.fiil.setText(filiere);

    }

    @Override
    public int getItemCount() {
        return UsersArrayList.size();
    }


    class HolderProfEt extends RecyclerView.ViewHolder{

        TextView NomPrenom, email, fiil;
        ImageView image;
        //ImageButton more;

        public HolderProfEt(@NonNull View itemView) {
            super(itemView);

            NomPrenom = (TextView) itemView.findViewById(R.id.item_nometudiant1);
            email = (TextView) itemView.findViewById(R.id.emailTextView1);
            fiil = (TextView) itemView.findViewById(R.id.FILLTextView1);
            image = (ImageView) itemView.findViewById(R.id.imageLivre);
           // more = (ImageButton) itemView.findViewById(R.id.morBtn);
        }
    }
}
