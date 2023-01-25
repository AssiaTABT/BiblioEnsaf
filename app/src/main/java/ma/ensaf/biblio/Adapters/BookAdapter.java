package ma.ensaf.biblio.Adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import ma.ensaf.biblio.EspaceAdmin.DetailsLivre;
import ma.ensaf.biblio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import ma.ensaf.biblio.Filters.FilterBooks2;
import ma.ensaf.biblio.Models.Livre;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.HolderLivre>  implements Filterable {

    private Context context;
    public ArrayList<Livre> LivreArrayList, filterList;

    private FilterBooks2 filter;

    private  static  String TAG = "BIIK_ADAPTER_TAG";

    public BookAdapter(Context context, ArrayList<Livre> livreArrayList) {
        this.context = context;
        this.LivreArrayList = livreArrayList;
        this.filterList=livreArrayList;
    }

    @NonNull
    @Override
    public HolderLivre onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlivresitem, parent, false);
        return new HolderLivre(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolderLivre holder, int position) {

        Livre model = LivreArrayList.get(position);

        //get data
        String id = model.getIdLivre();
        String titre = model.getTitre();
        String auteur = model.getAutheur();
        String imge = model.getImageLivre();
        String description = model.getDescription();
        String Npages = model.getNumeroPages();
        long timestamp = model.getTimestamp();

        holder.titre.setText(titre);
        holder.auteur.setText("De : "+auteur);
        holder.npages.setText(Npages+" pages");


        holder.moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //confirm delete dialog
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Supprimer")
                        .setMessage("Supprimer ce livre ?")
                        .setPositiveButton("Suppression", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(context, "Suppression...", Toast.LENGTH_SHORT).show();
                                //delete Categorie
                                SupprimerBook(model,holder);
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

        Picasso.get().load(model.getImageLivre()).into(holder.imageLivre);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(context, DetailsLivre.class);
                intent.putExtra("image",model.getImageLivre());
                intent.putExtra("titre",model.getTitre());
                intent.putExtra("auteur",model.getAutheur());
                intent.putExtra("description",model.getDescription());
                intent.putExtra("bookId",model.getIdLivre());
                intent.putExtra("pages",model.getNumeroPages());
                intent.putExtra("loc",model.getIdLivre());


                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    private void SupprimerBook(Livre model, HolderLivre holder) {
        String id = model.getIdLivre();
        //get id of category to delete
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books1");
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


    private void loadImageUrl(Livre model, HolderLivre holder) {
        String ImageUrl = model.getImageLivre();
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(ImageUrl);

        ref.getBytes(100)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "onSuccess: "+model.getTitre()+"Succès");

                       // Log.d(TAG, "onSuccess: "+model.getTitre()+"Success");

                        //set to imageview
                       holder.imageLivre.setImageURI(Uri.parse(ImageUrl));

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
    }
    @Override
    public int getItemCount() {
        return LivreArrayList.size();
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterBooks2(filterList, this);
        }
        return filter;
    }

    class HolderLivre extends RecyclerView.ViewHolder{

        TextView titre, auteur,npages;
        ImageView imageLivre;
        ImageButton moreBtn;
        ProgressBar PrgBar;
        public HolderLivre(@NonNull View itemView) {
            super(itemView);

            titre = (TextView) itemView.findViewById(R.id.item_title1);
            auteur = (TextView) itemView.findViewById(R.id.item_auteur1);
            npages = (TextView) itemView.findViewById(R.id.item_numpages1);
            moreBtn = (ImageButton) itemView.findViewById(R.id.suppBtn);
            imageLivre = (ImageView) itemView.findViewById(R.id.imageLivre);


        }
    }
}
