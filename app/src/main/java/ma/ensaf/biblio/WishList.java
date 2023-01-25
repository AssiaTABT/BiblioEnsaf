package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import ma.ensaf.biblio.databinding.ActivityWishListBinding;

public class WishList extends drawer_base {
    RecyclerView recyclerView;
    MyAdapter3 myAdapter;
    ArrayList<Livre> list;
    ImageView image;
    ProgressDialog progressDialog;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    ActivityWishListBinding activityWishListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityWishListBinding = ActivityWishListBinding.inflate(getLayoutInflater());
        setContentView(activityWishListBinding.getRoot());
        activityTitle("Wishlist");


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("recherche de livres");
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.wishList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadWish();




    }

    private void loadWish() {
        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(FirebaseAuth.getInstance().getUid()).child("Wishlist").addValueEventListener(new ValueEventListener() {
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
                myAdapter = new MyAdapter3(WishList.this, list);
                recyclerView.setAdapter(myAdapter);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(WishList.this, "error :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
}