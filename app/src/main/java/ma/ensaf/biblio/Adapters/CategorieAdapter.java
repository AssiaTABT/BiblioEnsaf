package ma.ensaf.biblio.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensaf.biblio.EspaceAdmin.LivresActivity2;
import ma.ensaf.biblio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import ma.ensaf.biblio.Filters.FilterCategorie;
import ma.ensaf.biblio.Models.Categorie;

public class CategorieAdapter extends RecyclerView.Adapter<CategorieAdapter.HolderCategorie> implements Filterable {

    private Context context;
    public ArrayList<Categorie> CategorieArrayList, filterList;

    private FilterCategorie filter;

    public CategorieAdapter(Context context, ArrayList<Categorie> categorieArrayList) {
        this.context = context;
        this.CategorieArrayList = categorieArrayList;
        this.filterList = categorieArrayList;
    }

    @NonNull
    @Override
    public HolderCategorie onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_category, parent, false);
        return new HolderCategorie(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderCategorie holder, int position) {

        //get data
        Categorie model = CategorieArrayList.get(position);
        String id = model.getIdCat();
        String titre = model.getTitre();
        String uid = model.getUid();

        //set data
        holder.CategorieTitle.setText(titre);

        holder.SuppCategorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //confirm delete dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Supprimer")
                        .setMessage("Supprimer cette catégorie ?")
                        .setPositiveButton("Suppression", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(context, "Suppression...", Toast.LENGTH_SHORT).show();
                                //delete Categorie
                                SupprimerCategorie(model,holder);
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        })
                        .show();
            }
        });
    //click go to OuvrageActivity
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, LivresActivity2.class);
                intent.putExtra("Idcc",id);
                intent.putExtra("titreee", titre);
                context.startActivity(intent);
            }
        });



    }

    private void SupprimerCategorie(Categorie model, HolderCategorie holder) {

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

    @Override
    public Filter getFilter() {

        if(filter == null){
            filter = new FilterCategorie(filterList, this);
        }
        return filter;
    }


    class HolderCategorie extends RecyclerView.ViewHolder{

        TextView CategorieTitle;
        ImageButton SuppCategorie;

        public HolderCategorie(@NonNull View itemView) {
            super(itemView);

            CategorieTitle = (TextView) itemView.findViewById(R.id.catTitleTV);
            SuppCategorie = (ImageButton) itemView.findViewById(R.id.suppCatBtn);
        }
    }
}
