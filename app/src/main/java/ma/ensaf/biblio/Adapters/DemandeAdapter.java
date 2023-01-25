package ma.ensaf.biblio.Adapters;



import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensaf.biblio.EspaceAdmin.ConfirmBorrow;
import ma.ensaf.biblio.Emprun;
import ma.ensaf.biblio.Models.Livre;
import ma.ensaf.biblio.R;

import java.util.ArrayList;

public class DemandeAdapter extends RecyclerView.Adapter<DemandeAdapter.HolderDemande>  {

    private Context context;
    public ArrayList<Emprun> CategorieArrayList;

    public DemandeAdapter(Context context, ArrayList<Emprun> categorieArrayList) {
        this.context = context;
        CategorieArrayList = categorieArrayList;

    }

    @NonNull
    @Override
    public HolderDemande onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_demande, parent, false);
        return new HolderDemande(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderDemande holder, int position) {

        //get data
        Emprun model = CategorieArrayList.get(position);
        String titre=model.getTitre();
        //set data
        holder.demandeTitle.setText("titre");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ConfirmBorrow.class);
                intent.putExtra("bookId",model.getBookId());
                intent.putExtra("uid",model.getUid());

                intent.putExtra("titre",titre);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });







    }



    @Override
    public int getItemCount() {
        return CategorieArrayList.size();
    }




    class HolderDemande extends RecyclerView.ViewHolder{

        TextView demandeTitle;


        public HolderDemande(@NonNull View itemView) {
            super(itemView);

            demandeTitle = (TextView) itemView.findViewById(R.id.bookTitleTv);

        }
    }
}
