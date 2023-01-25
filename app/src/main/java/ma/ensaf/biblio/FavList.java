package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Models.Livre;
import ma.ensaf.biblio.databinding.ActivityBooksListBinding;
import ma.ensaf.biblio.databinding.ActivityFavListBinding;

public class FavList extends drawer_base {
    RecyclerView recyclerView;
    MyAdapter2 myAdapter;
    ArrayList<Livre> list;
    ImageView image;
    ProgressDialog progressDialog;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    ActivityFavListBinding activityFavListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFavListBinding = ActivityFavListBinding.inflate(getLayoutInflater());
        setContentView(activityFavListBinding.getRoot());
        activityTitle("Favorites");


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("recherche de livres");
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.favList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFavs();




    }

    private void loadFavs() {
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(FirebaseAuth.getInstance().getUid()).child("Favorites").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    //we will only get the bookID here and we can get the others from it
                    String bookId=""+dataSnapshot.child("bookId").getValue();
                    //set id to model

                    Livre bk=new Livre();
                    bk.setIdLivre(bookId);
                    //add book to list
                    list.add(bk);


                }
                myAdapter = new MyAdapter2(FavList.this, list);
                recyclerView.setAdapter(myAdapter);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(FavList.this, "7lmi :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}