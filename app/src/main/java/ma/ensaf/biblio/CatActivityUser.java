package ma.ensaf.biblio;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.Adapters.CategorieAdapter2;
import ma.ensaf.biblio.Models.Categorie;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CatActivityUser extends AppCompatActivity {

    private Button ajouterCat;
    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn;
    private ArrayList<Categorie> CategorieArrayList;
    private CategorieAdapter2 categorieAdapter;
    private RecyclerView recyclerView;
    private EditText SearchCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cat_user);

        firebaseAuth = FirebaseAuth.getInstance();

        loadCategories();



        recyclerView = (RecyclerView) findViewById(R.id.categoriesRV2);



    }

    private void loadCategories() {

        CategorieArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CategorieArrayList.clear();
                for (DataSnapshot ds: snapshot.getChildren()){
                    //get data
                    Categorie model = ds.getValue(Categorie.class);

                    //add to arraylist
                    CategorieArrayList.add(model);
                }
                //setupAdapter
                categorieAdapter = new CategorieAdapter2(CatActivityUser.this,CategorieArrayList);

                //set Adapter to RecyclerView
                recyclerView.setAdapter(categorieAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}