package ma.ensaf.biblio.EspaceAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Emprun;
import ma.ensaf.biblio.R;
import ma.ensaf.biblio.adapter2;

public class borrowList  extends AppCompatActivity {

    private ArrayList<Emprun> demandeArrayList;
    private adapter2 demandeAdapter;
    private RecyclerView recyclerView;
    private ImageButton backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow_list);

        backbtn = (ImageButton) findViewById(R.id.BackBtnaa);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        recyclerView = findViewById(R.id.brList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        loadDemandes();


    }

    private void loadDemandes() {
        demandeArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Emprunts");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                demandeArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    //we will only get the bookID here and we can get the others from it
                    String bookId=""+ds.child("bookId").getValue();
                    String titre=ds.child("titre").getValue(String.class);
                    Integer day=ds.child("daysLeft").getValue(Integer.class);
                    String nom=ds.child("nom").getValue(String.class);
                 //   Integer ret=ds.child("dueDate").toString();





                    // LocalDate borrowDate=ds.child("borrowDate").getValue(LocalDate.class);
                    //   LocalDate dueDate=ds.child("dueDate").getValue(LocalDate.class);
                    String uid=ds.child("uid").getValue(String.class);
                    //set id to model
                    Emprun bk=new Emprun();
                    bk.setBookId(bookId);
                    bk.setTitre(titre);
                    //    bk.setBorrowDate(borrowDate);
                    //     bk.setDueDate(dueDate);
                    bk.setUid(uid);
                    bk.setDaysLeft(day);
                    bk.setNom(nom);


                    //add book to list

                    demandeArrayList.add(bk);


                }
                demandeAdapter = new adapter2(borrowList.this, demandeArrayList);
                recyclerView.setAdapter(demandeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
 /*  RecyclerView recyclerView;
    MyAdapter5 myAdapter;

    ArrayList<Borrow> list;
    ImageView image;
    ProgressDialog progressDialog;
    DatabaseReference db;
    FirebaseAuth firebaseAuth;
    ActivityBorrowListBinding activityFavListBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityFavListBinding = ActivityBorrowListBinding.inflate(getLayoutInflater());
        setContentView(activityFavListBinding.getRoot());
        activityTitle("Emprunts");


        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("recherche de livres");
        progressDialog.show();

        firebaseAuth=FirebaseAuth.getInstance();

        recyclerView = findViewById(R.id.brList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        loadFavs();




    }

    private void loadFavs() {

        list = new ArrayList<>();
        db = FirebaseDatabase.getInstance().getReference("Users");
        db.child(firebaseAuth.getUid()).child("Emprunts").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot ds : snapshot.getChildren()) {
                    String bookId=""+ds.child("bookId").getValue();
                  //  Integer daysLeft=ds.child("daysLeft").getValue(Integer.class);
                    String titre=ds.child("titre").getValue(String.class);
                    // LocalDate borrowDate=ds.child("borrowDate").getValue(LocalDate.class);
                    //   LocalDate dueDate=ds.child("dueDate").getValue(LocalDate.class);
                    String uid=ds.child("uid").getValue(String.class);
                    //set id to model
                    Emprun bk=new Emprun();
                    bk.setBookId(bookId);
                    bk.setTitre(titre);
                    //    bk.setBorrowDate(borrowDate);
                    //     bk.setDueDate(dueDate);
                    bk.setUid(uid);
               //     bk.setDaysLeft(daysLeft);
                    list.add(bk);



                }
                myAdapter = new MyAdapter5(borrowList.this,list);
                recyclerView.setAdapter(myAdapter);

                if (progressDialog.isShowing())
                    progressDialog.dismiss();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(borrowList.this, "7lmi :" + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }
*/
}