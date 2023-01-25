package ma.ensaf.biblio;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdapter5 extends ArrayAdapter<Borrow> {


    private final Activity activity;
    private ArrayList<Borrow> list;

    public MyAdapter5(Activity activity, ArrayList<Borrow> library) {
        super(activity, R.layout.library_list_row, library);
        this.activity = activity;
        this.list = library;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;

        if (rowView == null )
            rowView= LayoutInflater.from(activity).inflate(R.layout.library_list_row, parent, false);

        TextView title = (TextView) rowView.findViewById(R.id.titleRow);
        TextView author = (TextView) rowView.findViewById(R.id.authorRow);
        TextView daysLeft = (TextView) rowView.findViewById(R.id.daysLeft);
     //   ImageView image = (ImageView) rowView.findViewById(R.id.imageRow);
    //    ProgressBar progressBar = (ProgressBar) rowView.findViewById(R.id.progressRow);

        title.setText(list.get(position).getBook().getTitre());
        author.setText(list.get(position).getBook().getAuteur());
     //   Picasso.get().load(list.get(position).getBook().getImage()).into(image);
        String daysLeftString = activity.getString(R.string.daysLeft) + list.get(position).getDaysLeft();
        daysLeft.setText(daysLeftString);
    //    progressBar.setProgress(list.get(position).getPercentDaysPassed());
        return rowView;
    }
}
/*
    Context context;

    private ArrayList<Emprun> list1;

    public MyAdapter5(Context context, ArrayList<Emprun> list1) {
        this.context = context;
        this.list1=list1;
    }

    @NonNull
    @Override
    public MyAdapter5.MyViewHolder5 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.library_list_row,parent,false);
        return new MyViewHolder5(v);
    }
    @Override
    public void onBindViewHolder(@NonNull MyAdapter5.MyViewHolder5 holder, int position) {
        Emprun emprun=list1.get(position);
        Toast.makeText(context,emprun.titre+String.valueOf(emprun.daysLeft),Toast.LENGTH_SHORT).show();
       // holder.titre.setText(emprun.getTitre());
     //   holder.days.setText(emprun.getDaysLeft());
    //    holder.bar.setProgress(emprun.getPercentDaysPassed());
     //   loadBookDetails(book,holder);
     //  Picasso.get().load(emprun.getImage()).into(holder.img);
        //handle click to open details
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context,ConfirmBorrow.class);
            //    intent.putExtra("image",emprun.getImage());
                intent.putExtra("titre",emprun.getTitre());
        //        intent.putExtra("auteur",emprun.getAuteur());

                intent.putExtra("bookId",emprun.getBookId());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });


    }

    private void loadBookDetails(Book book, MyAdapter5.MyViewHolder5 holder) {
        String bookId=book.getBookId();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Emprunts");
        ref.child(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //get book informations

                String img=""+snapshot.child("image").getValue();

                //set to book model

                book.setImage(img);

                //set data to view


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

    public static class MyViewHolder5 extends RecyclerView.ViewHolder{

     TextView titre,auteur,days;
        ImageView img;
       ProgressBar bar;


        public MyViewHolder5(@NonNull View itemView) {
            super(itemView);

            img=itemView.findViewById(R.id.cardImage5);
            titre=itemView.findViewById(R.id.titleRow);
            auteur=itemView.findViewById(R.id.authorRow);
            days=itemView.findViewById(R.id.daysLeft);
            bar=itemView.findViewById(R.id.progressRow);

            //set onclick listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                }
            });
        }
    }*/


