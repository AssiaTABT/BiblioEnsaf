package ma.ensaf.biblio.EspaceAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Adapters.LivreAdapter;
import ma.ensaf.biblio.Models.Livre;
import ma.ensaf.biblio.R;

public class OuvragesActivity extends AppCompatActivity {

    private ArrayList<Livre> BookArrayList;
    private LivreAdapter livreadapter;
    private RecyclerView recyclerView;
    private ImageButton backBtn;
    private EditText SearchBook;
    private Button ajouterLivre;
    private TextView catPage;

    private String categorieId, categorieTitre;

    private static String TAG="IMG_LIST_TAG";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ouvrages);

        //get data from intent
        Intent intent = getIntent();
         categorieId =  intent.getStringExtra("Idcc");
         categorieTitre = intent.getStringExtra("titreee");

         catPage = (TextView) findViewById(R.id.catt);
         catPage.setText(categorieTitre);

        SearchBook = (EditText) findViewById(R.id.searchEv);

        loadBooks();
        SearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                //search as and when user type each letter
                try {
                    livreadapter.getFilter().filter(s);
                }catch (Exception e){
                    Log.d(TAG, "onTextChanged: "+e.getMessage());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        ajouterLivre = (Button) findViewById(R.id.addBook);
        ajouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(OuvragesActivity.this, AjouterLivres.class));
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.idLivresBiblio);
    }

    private void loadBooks() {

        BookArrayList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books1");

        ref.orderByChild("CategoriId").equalTo(categorieId)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        BookArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            Livre model = ds.getValue(Livre.class);
                            BookArrayList.add(model);

                            Log.d(TAG, "onDataChange: "+model.getIdLivre()+" "+model.getTitre());

                        }
                        livreadapter = new LivreAdapter(OuvragesActivity.this, BookArrayList);
                        recyclerView.setAdapter(livreadapter);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}