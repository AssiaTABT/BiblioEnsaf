package ma.ensaf.biblio.EspaceAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;


import ma.ensaf.biblio.Adapters.CategorieAdapter;
import ma.ensaf.biblio.Models.Categorie;
import ma.ensaf.biblio.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class CategorieActivity extends AppCompatActivity {

    private Button ajouterCat;
    private FirebaseAuth firebaseAuth;
    private ImageButton backBtn;
    private ArrayList<Categorie> CategorieArrayList;
    private CategorieAdapter categorieAdapter;
    private RecyclerView recyclerView;
    private EditText SearchCategorie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorie);

        firebaseAuth = FirebaseAuth.getInstance();

        loadCategories();

        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ajouterCat = (Button) findViewById(R.id.addcategorie);
        recyclerView = (RecyclerView) findViewById(R.id.categoriesRV);
        SearchCategorie = (EditText) findViewById(R.id.searchEv);

        SearchCategorie.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                try {

                    categorieAdapter.getFilter().filter(charSequence);

                }catch (Exception e){

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        ajouterCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CategorieActivity.this, AddCategorieActivity.class));
            }
        });
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
                categorieAdapter = new CategorieAdapter(CategorieActivity.this,CategorieArrayList);

                //set Adapter to RecyclerView
                recyclerView.setAdapter(categorieAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}