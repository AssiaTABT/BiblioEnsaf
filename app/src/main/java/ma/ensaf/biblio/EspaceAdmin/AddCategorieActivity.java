package ma.ensaf.biblio.EspaceAdmin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import ma.ensaf.biblio.R;

public class AddCategorieActivity extends AppCompatActivity {

    private EditText CatTilte;
    private Button addCat;

    private ImageButton backBtn;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_categorie);

        CatTilte = (EditText) findViewById(R.id.categoriyET);
        addCat = (Button) findViewById(R.id.addCatBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        addCat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AddCategorietitle();
            }
        });
    }

    String titre="";
    private void AddCategorietitle(){
        titre = CatTilte.getText().toString().trim();

        if(TextUtils.isEmpty(titre)){
            Toast.makeText(this, "Enrtez un titre...", Toast.LENGTH_SHORT).show();
        }else{
            addCategorie();
        }
    }

    private void addCategorie(){
        progressDialog.setMessage("Ajout d'une Catégorie");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("IdCat",""+timestamp);
        hashMap.put("titre",titre);
        hashMap.put("uid",""+firebaseAuth.getUid());

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.child(""+timestamp).setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(AddCategorieActivity.this, "La catégorie à été bien enregistrer ", Toast.LENGTH_SHORT).show();

                startActivity(new Intent(AddCategorieActivity.this, CategorieActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AddCategorieActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}