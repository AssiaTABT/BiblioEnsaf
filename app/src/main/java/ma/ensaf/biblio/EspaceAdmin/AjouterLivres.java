package ma.ensaf.biblio.EspaceAdmin;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;

import ma.ensaf.biblio.BooksList;
import ma.ensaf.biblio.R;

public class AjouterLivres extends AppCompatActivity {
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;

    Button ajouter;
    Button annuler;
    TextView titre, autheur, description, filir, numPages;
    ImageButton imageLivre;
    ImageButton backBtn;
    ArrayList<String> CategoriesTitreArrayList, CategorieIdArrayList;
    private Uri ImageUri=null;

    private static final String TAG = "ADD_IMG_TAG";

    //private  StorageReference reference = FirebaseStorage.getInstance().getReference();

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajouter_livres);

        ajouter = (Button) findViewById(R.id.ajouterLivre);
        annuler = (Button) findViewById(R.id.annulerBtn);
        autheur = (TextView) findViewById(R.id.ajauteurlivre);
        titre = (TextView) findViewById(R.id.ajtitrelivre);
        description = (TextView) findViewById(R.id.ajdescriptionlivre);
        filir = (TextView) findViewById(R.id.ajfilierelivre);
        numPages = (TextView) findViewById(R.id.ajpageslivre);
        imageLivre = (ImageButton) findViewById(R.id.ajimagelivreBtn);

        firebaseAuth = FirebaseAuth.getInstance();

        loadBookCategories();

        annuler.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        backBtn = (ImageButton) findViewById(R.id.BackBtn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

      imageLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImgPickIntent();

            }
        });

      filir.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {
              CategoriePickDialog();
          }
      });

        ajouter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Bookdata();
            }
        });

    }

    private void ImgPickIntent(){
        Log.d(TAG, "ImgPickIntent: starting img pick intent");

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Choisissez une image"), 2);
    }

    private String SelectedCategorieId, SelectedCategorieTitre;
    private void CategoriePickDialog(){

        Log.d(TAG, "CategoriePickDialog: le dialogue de chois d'une categorie");

        //get string array of categories from arraylist
        String[] catArray = new String[CategoriesTitreArrayList.size()];

        for(int i = 0; i< CategoriesTitreArrayList.size(); i++){

            catArray[i] = CategoriesTitreArrayList.get(i);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Catégories")
                .setItems(catArray, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        //item Click
                        //get clicked item from list
                        SelectedCategorieTitre = CategoriesTitreArrayList.get(i);
                        SelectedCategorieId = CategorieIdArrayList.get(i);
                        filir.setText(SelectedCategorieTitre);

                        Log.d(TAG, "onClick: Categorie selectionnée : "+SelectedCategorieTitre);
                    }
                })
                .show();
    }

    private void loadBookCategories(){
        Log.d(TAG, "loadBookCategories: Chargement des catégories des ouvrages");
        CategoriesTitreArrayList = new ArrayList<>();
        CategorieIdArrayList = new ArrayList<>();

        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Categories");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                CategoriesTitreArrayList.clear();
                CategorieIdArrayList.clear();
                for(DataSnapshot ds:snapshot.getChildren()){

                    String CategorieId = ""+ds.child("IdCat").getValue();
                    String CategorieTitre = ""+ds.child("titre").getValue();

                    CategoriesTitreArrayList.add(CategorieTitre);
                    CategorieIdArrayList.add(CategorieId);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
   @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK ){
            if(requestCode == 2){
                Log.d(TAG, "onActivityResult: Image selectionnée");

                ImageUri = data.getData();
                Log.d(TAG, "onActivityResult: "+ImageUri);
            }else {

                Log.d(TAG, "onActivityResult: La sélection d'image est annulée !");
                Toast.makeText(this, "La sélection d'image est annulée", Toast.LENGTH_SHORT).show();
            }
            
            imageLivre.setImageURI(ImageUri);
        }
    }


    String titre1="", autheur1="", description1="", Npages1="";

    private void Bookdata(){

        Log.d(TAG, "Bookdata: Validation des informations...");

        //get data
        titre1 = titre.getText().toString().trim();
        autheur1 = autheur.getText().toString().trim();
        description1 = description.getText().toString().trim();
        Npages1 = numPages.getText().toString().trim();

        //validate data
        if(TextUtils.isEmpty(titre1)){
            Toast.makeText(this, "Enrtez le titre...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(autheur1)){
            Toast.makeText(this, "Enrtez l'autheur...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(description1)){
            Toast.makeText(this, "Donnez une description au livre...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(SelectedCategorieTitre)){
            Toast.makeText(this, "Spécifiez la filière...", Toast.LENGTH_SHORT).show();
        }else if(TextUtils.isEmpty(Npages1)){
            Toast.makeText(this, "Entrez lz nombre de pages...", Toast.LENGTH_SHORT).show();
        }else if(ImageUri == null){
            Toast.makeText(this, "Choisissez une image...", Toast.LENGTH_SHORT).show();
        }
        else {
            UploadImgToStorege();
        }
    }

    private void UploadImgToStorege(){

        Log.d(TAG, "UploadImgToStorege: Enregistrement d'image...");

        progressDialog.setMessage("Chargement...");
        progressDialog.show();

        long timestamp = System.currentTimeMillis();

        //path of image in firebase storage
        String filePathAndName = "Books/"+timestamp;

        StorageReference storageReference = FirebaseStorage.getInstance().getReference(filePathAndName);
        storageReference.putFile(ImageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Log.d(TAG, "onSuccess: Bien enregistrée...");
                        Log.d(TAG, "onSuccess: L'URL d'image");

                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful());
                        String uploadImgUrl = ""+uriTask.getResult();

                        //Upload to firebase Db
                        ajouterlivre(uploadImgUrl, timestamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Log.d(TAG, "onFailure: Echec! " +e.getMessage());
                        Toast.makeText(AjouterLivres.this, "Echec ! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }


    private void ajouterlivre(String uploadImgUrl, long timestamp) {

        progressDialog.setMessage("Ajout d'un livre");
        progressDialog.show();


        HashMap<String, Object> hashMap = new HashMap<>();

        hashMap.put("IdLivre",""+timestamp);
        hashMap.put("titre",titre1);
        hashMap.put("autheur",autheur1);
        hashMap.put("description",description1);
        hashMap.put("CategoriId",SelectedCategorieId);
        hashMap.put("numeroPages",Npages1);
        hashMap.put("imageLivre",""+uploadImgUrl);
        hashMap.put("uid",""+firebaseAuth.getUid());
        hashMap.put("timestamp",timestamp);


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Books1");
        ref.child(""+timestamp)
                .setValue(hashMap)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                progressDialog.dismiss();
                Toast.makeText(AjouterLivres.this, "Le livre à été enregistrer avec succès", Toast.LENGTH_SHORT).show();

                /*startActivity(new Intent(AjouterLivres.this, DashboardActivity.class));
                finish();*/
                Intent intent = new Intent(AjouterLivres.this, LivresActivity2.class);
                intent.putExtra("Idcc",SelectedCategorieId);
                intent.putExtra("titreee", SelectedCategorieTitre);
                startActivity(intent);
            }
        }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                progressDialog.dismiss();
                Toast.makeText(AjouterLivres.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

   /* private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }*/



}