package ma.ensaf.biblio;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.ObjectInputStream;
import java.util.ArrayList;

import ma.ensaf.biblio.Models.Livre;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>  {

    Context context;
    public ArrayList<Livre> list ;


    public MyAdapter(Context context, ArrayList<Livre> list) {
        this.context = context;
        this.list = list;

    }

    @NonNull
    @Override
    public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.book,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
          Livre book=list.get(position);

          holder.titre.setText(book.getTitre());
          holder.auteur.setText(book.getAutheur());
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
          holder.description.setText(book.getDescription());
        Picasso.get().load(book.getImageLivre()).into(holder.img);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }




    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titre,description,auteur;
        ImageView img;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            titre=itemView.findViewById(R.id.cardTitle);
            description=itemView.findViewById(R.id.cardContent);
            img=itemView.findViewById(R.id.cardImage);
            auteur=itemView.findViewById(R.id.cardAuthor);
            //set onclick listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }
    }
}
