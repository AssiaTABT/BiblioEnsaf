package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Demande extends AppCompatActivity {

    private ArrayList<Emprun> demandeArrayList;
    private adapter demandeAdapter;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);

        recyclerView = findViewById(R.id.demandesRV);
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
                    //add book to list
                    Toast.makeText(Demande.this, bk.getBookId(), Toast.LENGTH_SHORT).show();
                    demandeArrayList.add(bk);


                }
                demandeAdapter = new adapter(Demande.this, demandeArrayList);
                recyclerView.setAdapter(demandeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}