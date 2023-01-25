package ma.ensaf.biblio;



import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import java.time.LocalDate;
import java.util.ArrayList;

public class MyAdapter4 extends RecyclerView.Adapter<MyAdapter4.MyViewHolder4> {

    Context context;

    private ArrayList<Borrow> list1;

    public MyAdapter4(Context context,ArrayList<Borrow> list1) {
        this.context = context;

        this.list1=list1;
    }

    @NonNull
    @Override
    public MyAdapter4.MyViewHolder4 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.library_list_row,parent,false);
        return new MyViewHolder4(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter4.MyViewHolder4 holder, int position) {
        Book book=list1.get(position).getBook();
        loadBookDetails(book,holder);
        holder.titre.setText(book.getTitre());
        holder.auteur.setText(book.getAuteur());
        String daysLeftString = "Days Left: " + list1.get(position).getDaysLeft();
        holder.days.setText(daysLeftString);
        holder.progressBar.setProgress(list1.get(position).getPercentDaysPassed());

        Picasso.get().load(book.getImage()).into(holder.img);
        //handle click to open details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,Details.class);
                intent.putExtra("image",book.getImage());
                intent.putExtra("titre",book.getTitre());
                intent.putExtra("auteur",book.getAuteur());
                intent.putExtra("description",book.getDescription());
                intent.putExtra("bookId",book.getBookId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    private void loadBookDetails(Book book, MyAdapter4.MyViewHolder4 holder) {
        String bookId=book.getBookId();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Books");
        ref.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get book informations
                String title=""+snapshot.child("titre").getValue();
                String author=""+snapshot.child("auteur").getValue();
                String desc=""+snapshot.child("description").getValue();
                String img=""+snapshot.child("image").getValue();

                //set to book model
                book.setAuteur(author);
                book.setDescription(desc);
                book.setTitre(title);
                book.setImage(img);

                //set data to view
                holder.titre.setText(title);
                holder.auteur.setText(author);

                Picasso.get().load(img).into(holder.img);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public int getItemCount() {
        return list1.size();
    }

    public static class MyViewHolder4 extends RecyclerView.ViewHolder{

        TextView titre,auteur;
        ImageView img;
        TextView days;
        ProgressBar progressBar;


        public MyViewHolder4(@NonNull View itemView) {
            super(itemView);
            titre=itemView.findViewById(R.id.titleRow);
         //   img=itemView.findViewById(R.id.imageRow);
            auteur=itemView.findViewById(R.id.authorRow);
            days=itemView.findViewById(R.id.daysLeft);
       //     progressBar=itemView.findViewById(R.id.progressRow);
            //set onclick listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}

