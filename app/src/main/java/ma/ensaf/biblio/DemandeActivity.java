package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import ma.ensaf.biblio.Adapters.DemandeAdapter;

public class DemandeActivity extends AppCompatActivity {
    private ArrayList<Emprun> demandeArrayList;
    private DemandeAdapter demandeAdapter;
    private RecyclerView recyclerView;
    private ImageButton back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demande);
        recyclerView =findViewById(R.id.demandesRV);


        back = (ImageButton) findViewById(R.id.BackBtncc);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
                    //get data
               //     Emprun model = ds.getValue(Emprun.class);
            String bk=ds.child("bookId").getValue(String.class);
              Emprun model=new Emprun();
              model.setBookId(bk);
                    //add to arraylist
                    demandeArrayList.add(model);
                }
                //setupAdapter
                demandeAdapter = new DemandeAdapter(DemandeActivity.this,demandeArrayList);

                //set Adapter to RecyclerView
                recyclerView.setAdapter(demandeAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}