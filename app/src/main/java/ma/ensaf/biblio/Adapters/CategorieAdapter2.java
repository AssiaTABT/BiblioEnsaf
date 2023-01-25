package ma.ensaf.biblio.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ma.ensaf.biblio.BooksList;
import ma.ensaf.biblio.Filters.FilterCategorie;
import ma.ensaf.biblio.Models.Categorie;
import ma.ensaf.biblio.R;

public class CategorieAdapter2 extends RecyclerView.Adapter<CategorieAdapter2.HolderCategorie2>  {

    private Context context;
    public ArrayList<Categorie> CategorieArrayList, filterList;

    private FilterCategorie filter;

    public CategorieAdapter2(Context context, ArrayList<Categorie> categorieArrayList) {
        this.context = context;
        CategorieArrayList = categorieArrayList;
        //filterList = categorieArrayList;
    }

    @NonNull
    @Override
    public HolderCategorie2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category2, parent, false);
        return new HolderCategorie2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategorie2 holder, int position) {

        //get data
        Categorie model = CategorieArrayList.get(position);
        String id = model.getIdCat();
        String titre = model.getTitre();
        String uid = model.getUid();

        //set data
        holder.CategorieTitle.setText(titre);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, BooksList.class);
                intent.putExtra("Idcc",id);
                intent.putExtra("titreee", titre);
                context.startActivity(intent);
            }
        });
    }

    private void SupprimerCategorie(Categorie model, HolderCategorie2 holder) {

        String id = model.getIdCat();
        //get id of category to delete
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Supprimer avec succès", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public int getItemCount() {
        return CategorieArrayList.size();
    }

   /* @Override
    public Filter getFilter() {

        if(filter == null){
            filter = new FilterCategorie(filterList, this);
        }
        return filter;
    }*/


    class HolderCategorie2 extends RecyclerView.ViewHolder{

        TextView CategorieTitle;


        public HolderCategorie2(@NonNull View itemView) {
            super(itemView);

            CategorieTitle = (TextView) itemView.findViewById(R.id.catTitleTV2);

        }
    }
}
