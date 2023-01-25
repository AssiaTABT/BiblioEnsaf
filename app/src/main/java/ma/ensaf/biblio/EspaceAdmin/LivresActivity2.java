package ma.ensaf.biblio.EspaceAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import ma.ensaf.biblio.R;
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


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import ma.ensaf.biblio.Adapters.BookAdapter;
import ma.ensaf.biblio.Models.Livre;

public class LivresActivity2 extends AppCompatActivity {

    ArrayList<Livre> bookArrayList;
    BookAdapter bookadapter;
    private ImageButton backBtn;
    private RecyclerView recyclerView;

    private TextView catPage;
    private Button ajouterLivre;

    private EditText SearchBook;

    String catid, cattitre;

    private static final String TAG="IMG_LIST_TAG";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_livres2);


        //get data from intent
        Intent intent = getIntent();
        catid = intent.getStringExtra("Idcc");
        cattitre = intent.getStringExtra("titreee");

        catPage = (TextView) findViewById(R.id.catt);
        catPage.setText(cattitre);

        loadBookList();

        ajouterLivre = (Button) findViewById(R.id.addBook);
        ajouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LivresActivity2.this, AjouterLivres.class));
            }
        });

        //search btn
        SearchBook = (EditText) findViewById(R.id.searchEv);
        SearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                try {

                    bookadapter.getFilter().filter(s);

                }catch (Exception e){

                    Log.d(TAG, "onTextChanged: "+e.getMessage());

                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.BookRV);


        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void loadBookList() {
        bookArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books1");

        ref.orderByChild("CategoriId").equalTo(catid)
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        bookArrayList.clear();
                        for(DataSnapshot ds: snapshot.getChildren()){
                            Livre model = ds.getValue(Livre.class);

                            bookArrayList.add(model);
                            Log.d(TAG, "onDataChange: "+model.getIdLivre()+" "+model.getTitre());
                        }

                        //setup adapter
                        bookadapter = new BookAdapter(LivresActivity2.this, bookArrayList);
                        recyclerView.setAdapter(bookadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }
}