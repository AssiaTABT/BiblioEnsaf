package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.joda.time.LocalDate;

import java.util.ArrayList;

import ma.ensaf.biblio.databinding.ActivityBorrowListBinding;
import ma.ensaf.biblio.databinding.ActivityConfirmListBinding;
import ma.ensaf.biblio.databinding.ActivityFavListBinding;

public class confirmList extends drawer_base {
    /*RecyclerView recyclerView;
    MyAdapter5 myAdapter;
    ArrayList<Emprun> list;
    Borrow borrow;
    ImageView image;
    String bookId;
    ProgressDialog progressDialog;
    DatabaseReference db;
    DatabaseReference dbBook;
    FirebaseAuth firebaseAuth;
    ActivityConfirmListBinding activityConfirmListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityConfirmListBinding = ActivityConfirmListBinding.inflate(getLayoutInflater());
        setContentView(activityConfirmListBinding.getRoot());
        activityTitle("Emprunts");


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("recherche de livres");
        progressDialog.show();
        recyclerView = findViewById(R.id.confList);
        firebaseAuth=FirebaseAuth.getInstance();
      dbBook=FirebaseDatabase.getInstance().getReference("Books");
        db=FirebaseDatabase.getInstance().getReference("Emprunts");

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list=new ArrayList<>();
        myAdapter=new MyAdapter5(confirmList.this,list);
        recyclerView.setAdapter(myAdapter);
      /*  db = FirebaseDatabase.getInstance().getReference("Emprunts");
        db.child("-MurMhiBikB_bqpUB48P").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //we will only get the bookID here and we can get the others from it
                    String bookId=""+dataSnapshot.child("bookId").getValue();
                    //set id to model
                    Book bk=new Book();
                    bk.setBookId(bookId);
                   // Borrow br=new Borrow(bk);
                    //add book to list
                    list.add(bk);


                }
                myAdapter = new MyAdapter5(confirmList.this,list);
                recyclerView.setAdapter(myAdapter);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(confirmList.this, "7lmi :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });*/

    /*   db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Book bk = dataSnapshot.getValue(Book.class);
                    list.add(bk);


                }
                myAdapter.notifyDataSetChanged();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(confirmList.this,"7lmi :" + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });*/

       /* loadFavs();




    }

    private void loadFavs() {

        list = new ArrayList<>();

        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                  Emprun  bk = dataSnapshot.getValue(Emprun.class);
                   borrow=new Borrow();



             //       Toast.makeText(confirmList.this, "7lmi :"+borrow.getBook().getTitre(), Toast.LENGTH_SHORT).show();
                    //add book to list
                 //   borrow.getBook().setImage("https://firebasestorage.googleapis.com/v0/b/biblio-ca7a8.appspot.com/o/advanced-C-Sharp-programming.png?alt=media&token=36c8a88d-cbb6-4707-93f4-cca53bef3019");
                    Toast.makeText(confirmList.this, "fiya n3aaaaaaaas", Toast.LENGTH_SHORT).show();
borrow.setBorrowDate(new LocalDate());
borrow.setDueDate(new LocalDate());
                    list.add(borrow);


                }
                myAdapter = new MyAdapter5(confirmList.this,list);
                recyclerView.setAdapter(myAdapter);


                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(confirmList.this, "7lmi :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
        dbBook.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for( DataSnapshot ds:snapshot.getChildren()
                ) {
                    Toast.makeText(confirmList.this, "7lmi :"+ds.getValue(Book.class).getTitre(), Toast.LENGTH_SHORT).show();


                    borrow.setBook(ds.getValue(Book.class));


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
*/
}