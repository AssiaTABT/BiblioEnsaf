package ma.ensaf.biblio.Adapters;

import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.Filters.FilterBooks;
import ma.ensaf.biblio.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import ma.ensaf.biblio.Filters.FilterBooks2;
import ma.ensaf.biblio.Models.Livre;

public class LivreAdapter extends RecyclerView.Adapter<LivreAdapter.HolderBook> implements Filterable {

    private Context context;
    public   ArrayList<Livre> BookArrayList, filterBookList;

    private FilterBooks filter;

    private  static  String TAG = "BIIK_ADAPTER_TAG";


    public LivreAdapter(Context context, ArrayList<Livre> bookArrayList) {
        this.context = context;
        BookArrayList = bookArrayList;
        this.filterBookList = bookArrayList;
    }

    @Override
    public Filter getFilter() {
        if(filter == null){
            filter = new FilterBooks(filterBookList, this);
        }
        return filter;
    }

    @NonNull
    @Override
    public HolderBook onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.listlivresitem, parent, false);
        return new HolderBook(view);

    }

    @Override
    public void onBindViewHolder(@NonNull HolderBook holder, int position) {

        //get data
        Livre model = BookArrayList.get(position);
        String id = model.getIdLivre();
        String titre = model.getTitre();
        String auteur = model.getAutheur();
        String imge = model.getImageLivre();
        String description = model.getDescription();
        String Npages = model.getNumeroPages();
        long timestamp = model.getTimestamp();

        holder.titre.setText(titre);
        holder.auteur.setText(auteur);
        holder.npages.setText(Npages);

       // loadCategorie(model, holder);
       // loadImageUrl(model, holder);

    }

   private void loadImageUrl(Livre model, HolderBook holder) {
        String ImageUrl = model.getImageLivre();
        StorageReference ref = FirebaseStorage.getInstance().getReferenceFromUrl(ImageUrl);

        ref.getBytes(100)
                .addOnSuccessListener(new OnSuccessListener<byte[]>() {
                    @Override
                    public void onSuccess(byte[] bytes) {
                        Log.d(TAG, "onSuccess: "+model.getTitre()+"Success");

                        //set to imageview
                        holder.imageLivre.setImageURI(Uri.parse(ImageUrl));
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "onFailure: Echec !"+e.getMessage());

                    }
                });
    }

    private void loadCategorie(Livre model, HolderBook holder) {

        String cateforieId = model.getCategoriId();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(cateforieId)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String categorie = ""+snapshot.child("titre").getValue();

                        //set to categorie textView
                        holder.categorie.setText(categorie);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return BookArrayList.size();
    }


    class  HolderBook extends RecyclerView.ViewHolder{

        TextView titre, auteur,npages, categorie ;
        ImageView imageLivre;
        ImageButton moreBtn;

        public HolderBook(@NonNull View itemView) {
            super(itemView);

            titre = (TextView) itemView.findViewById(R.id.item_title1);
            auteur = (TextView) itemView.findViewById(R.id.item_auteur1);
            npages = (TextView) itemView.findViewById(R.id.item_numpages1);
           // categorie = (TextView) itemView.findViewById(R.id.itemCategorie);
            moreBtn = (ImageButton) itemView.findViewById(R.id.suppBtn);
            imageLivre = (ImageView) itemView.findViewById(R.id.imageLivre);
        }
    }
}
