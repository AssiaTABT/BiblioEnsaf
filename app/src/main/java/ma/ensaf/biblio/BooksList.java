package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;

import ma.ensaf.biblio.Models.Livre;
import ma.ensaf.biblio.databinding.ActivityBooksListBinding;
import ma.ensaf.biblio.databinding.ActivityMainBinding;

public class BooksList extends drawer_base {

RecyclerView recyclerView;
ProgressDialog progressDialog;
DatabaseReference db;
FirebaseAuth auth;
MyAdapter myAdapter;
Button fav;
ArrayList<Livre> list;
    String Id;
    String catid, cattitre;
ActivityBooksListBinding activityBooksListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityBooksListBinding= ActivityBooksListBinding.inflate(getLayoutInflater());
        setContentView(activityBooksListBinding.getRoot());
        activityTitle("Livres");


        //loadBookList();
        Intent intent = getIntent();
        catid = intent.getStringExtra("Idcc");
        cattitre = intent.getStringExtra("titreee");

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("recherche de livres");
        progressDialog.show();



        recyclerView = (RecyclerView) findViewById(R.id.booksList);

        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        list=new ArrayList<>();
        myAdapter=new MyAdapter(BooksList.this,list);
        recyclerView.setAdapter(myAdapter);

        db=FirebaseDatabase.getInstance().getReference("Books1");
       db.orderByChild("CategoriId").equalTo(catid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Livre bk = dataSnapshot.getValue(Livre.class);
                    list.add(bk);


                }
                myAdapter.notifyDataSetChanged();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BooksList.this,"7lmi :" + error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });





       // EventChangedListener();

    }
    private void loadBookList() {
       list = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books1");

        ref.orderByChild("CategoriId").equalTo(catid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        list.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                           // Livre model = ds.getValue(Livre.class);

                            Livre model = new Livre();
                            String name = ds.child("titre").getValue().toString();
                            String Email = ds.child("autheur").getValue().toString();
                            String filiere1 = ds.child("Filiere").getValue().toString();
                            model.setTitre(name);
                            model.setAutheur(Email);

                            Toast.makeText(BooksList.this, ""+name, Toast.LENGTH_SHORT).show();
                            list.add(model);
                            //Log.d(TA, "onDataChange: "+model.getIdLivre()+" "+model.getTitre());
                        }

                        //setup adapter
                        myAdapter = new MyAdapter(BooksList.this, list);
                        recyclerView.setAdapter(myAdapter);

                        if(progressDialog.isShowing())
                            progressDialog.dismiss();

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }

   /* private void getBookData() {
        db.child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for(DataSnapshot snapshot1 : snapshot.getChildren() ){

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void EventChangedListener() {
        db.child("Books").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error !=null)
                {
                    if(progressDialog.isShowing())
                        progressDialog.dismiss();
                    Log.e("Firestore error",error.getMessage());
                return;
            }
            for(DocumentChange dc:value.getDocumentChanges()){
                if(dc.getType()== DocumentChange.Type.ADDED){
                    list.add(dc.getDocument().toObject(Book.class));

                }

                myAdapter.notifyDataSetChanged();
                if(progressDialog.isShowing())
                    progressDialog.dismiss();
            }}
        });
    }
*/

    public static void addToFavorite(Context context, String bookId)
    {
        //we can only add if user is logged in
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();;
        if(firebaseAuth.getCurrentUser()==null){
            Toast.makeText(context,"not log in",Toast.LENGTH_SHORT).show();
        }else{
       long timestamp=System.currentTimeMillis();
       //setup data to add in firebase
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("bookId",""+bookId);
        hashMap.put("timestamp",""+timestamp);

        //save to db
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Favorites").child(bookId)
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Added to your favorite list", Toast.LENGTH_SHORT).show();

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Failed to  add to favorites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });}

    }
    public static void removeFromFavorites(Context context,String bookId){
        //we can only remove if user is logged in
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();

        if(firebaseAuth.getCurrentUser()==null){
            Toast.makeText(context,"not log in",Toast.LENGTH_SHORT).show();
        }else{


        //save to db
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(
               firebaseAuth.getUid()).child("Favorites").child(bookId)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Removed successfully ", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to  remove from favorites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });}
    }

}