package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.cast.framework.media.ImagePicker;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.HashMap;

import ma.ensaf.biblio.databinding.ActivityMainBinding;
import ma.ensaf.biblio.databinding.ActivityProfilBinding;

public class profil extends drawer_base {
TextView fullName,filiere,email,name,email2;
ImageView profileImg;
String Id;
User user;
Button upPic;

private Uri ImageUri;
 private  static final String TAG="ADD_IMG_TAG";
    FirebaseAuth auth;
    // creating a variable for
    // our Firebase Database.
    FirebaseDatabase firebaseDatabase;

    // creating a variable for our
    // Database Reference for Firebase.
    DatabaseReference df;

ActivityProfilBinding activityProfilBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilBinding= ActivityProfilBinding.inflate(getLayoutInflater());
        setContentView(activityProfilBinding.getRoot());
        activityTitle("Profil");

        upPic = (Button) findViewById(R.id.updatePic);
        auth=FirebaseAuth.getInstance();
        Id=auth.getCurrentUser().getUid();
        df=FirebaseDatabase.getInstance().getReference("Users");
        if(Id!=null){
            getUserData();
        }

        upPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addimag();
            }
        });

        name=findViewById(R.id.name);
        email2=findViewById(R.id.email);
        profileImg=findViewById(R.id.profileTv);
        profileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //open gallery
                ImgPickIntent();

            }
        });





    }

    private void addimag() {
        Log.d(TAG, "UploadImgToStorege: Enregistrement d'image...");



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
                        ajouter(uploadImgUrl, timestamp);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Log.d(TAG, "onFailure: Echec! " +e.getMessage());
                        Toast.makeText(profil.this, "Echec ! "+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void ajouter(String uploadImgUrl, long timestamp) {

        HashMap<String, Object> hashMap = new HashMap<>();


        hashMap.put("imageprofil",""+uploadImgUrl);



        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.child(FirebaseAuth.getInstance().getUid()).child("img")

                .setValue(uploadImgUrl)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                        Toast.makeText(profil.this, "Profil updated", Toast.LENGTH_SHORT).show();


                    }
                }).addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(profil.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void ImgPickIntent() {
        Log.d(TAG, "ImgPickIntent: starting img pick intent");

        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(Intent.createChooser(galleryIntent, "Choisissez une image"), 2);
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

            profileImg.setImageURI(ImageUri);
        }
    }

    private void getUserData() {
        df.child(Id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                email2.setText( snapshot.child("Email").getValue(String.class));
                name.setText( snapshot.child("FullName").getValue(String.class));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



   /* @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==1000){
            if(resultCode== Activity.RESULT_OK){
                Uri imageUri=data.getData();
                profileImg.setImageURI(imageUri);

            }
        }
    }*/

  /*  protected void onStart() {
        super.onStart();

         /*   df.child(Id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    fullName.setText( snapshot.child("FullName").getValue().toString());
                    filiere.setText( snapshot.child("Filiere").getValue(String.class));
                    email.setText( snapshot.child("Email").getValue(String.class));
                    email2.setText( snapshot.child("Email").getValue(String.class));
                    name.setText( snapshot.child("FullName").getValue(String.class));
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(profil.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
                }
            });*/



       //     DocumentReference df=FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
         //   df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
        //        @Override
        //        public void onSuccess(DocumentSnapshot documentSnapshot) {
        //            fullName.setText(documentSnapshot.getString("FullName"));
           /*        filiere.setText(documentSnapshot.getString("Filière"));
                    email.setText(documentSnapshot.getString("Email"));
                    email2.setText(documentSnapshot.getString("Email"));
                    name.setText(documentSnapshot.getString("FullName"));

                }
            });*/

    }