package ma.ensaf.biblio;



import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.ObjectInputStream;
import java.util.ArrayList;

import ma.ensaf.biblio.Models.Livre;

public class MyAdapter3 extends RecyclerView.Adapter<MyAdapter3.MyViewHolder3> {

    Context context;
    private ArrayList<Livre> list;

    public MyAdapter3(Context context, ArrayList<Livre> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyAdapter3.MyViewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.wish,parent,false);
        return new MyViewHolder3(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder3 holder, int position) {
        Livre book=list.get(position);
        loadBookDetails(book,holder);
        holder.titre.setText(book.getTitre());
        holder.auteur.setText(book.getAutheur());
        holder.description.setText(book.getDescription());
        Picasso.get().load(book.getImageLivre()).into(holder.img);
        //handle click to open details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("image",book.getImageLivre());
                intent.putExtra("titre",book.getTitre());
                intent.putExtra("auteur",book.getAutheur());
                intent.putExtra("description",book.getDescription());
                intent.putExtra("bookId",book.getIdLivre());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

        //handle click to remove from favorites
        holder.wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Details.removeFromWish(context,book.getIdLivre());
            }
        });
    }

    private void loadBookDetails(Livre book, MyViewHolder3 holder) {
        String bookId=book.getIdLivre();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Books1");
        ref.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get book informations
                String title=""+snapshot.child("titre").getValue();
                String author=""+snapshot.child("autheur").getValue();
                String desc=""+snapshot.child("description").getValue();
                String img=""+snapshot.child("imageLivre").getValue();

                //set to book model
                book.setAutheur(author);
                book.setDescription(desc);
                book.setTitre(title);
                book.setImageLivre(img);

                //set data to view
                holder.titre.setText(title);
                holder.auteur.setText(author);
                holder.description.setText(desc);
                Picasso.get().load(img).into(holder.img);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyViewHolder3 extends RecyclerView.ViewHolder{

        TextView titre,description,auteur;
        ImageView img;
        Button wish;


        public MyViewHolder3(@NonNull View itemView) {
            super(itemView);
            titre=itemView.findViewById(R.id.cardTitle3);
            description=itemView.findViewById(R.id.cardContent3);
            img=itemView.findViewById(R.id.cardImage3);

            auteur=itemView.findViewById(R.id.cardAuthor3);
            wish=itemView.findViewById(R.id.btnWish);
            //set onclick listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

