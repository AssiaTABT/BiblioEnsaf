package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
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

import java.util.ArrayList;

import ma.ensaf.biblio.EspaceAdmin.DashboardActivity;
import ma.ensaf.biblio.databinding.ActivityLoginBinding;

public class Login extends AppCompatActivity {
    EditText email,password;
    Button loginBtn,gotoRegister;
    boolean valid = true;
    FirebaseAuth auth;
    FirebaseFirestore store;
    TextView account;
    ActivityLoginBinding binding;
    FirebaseUser firebaseUser;

    private String SelectedProfil;
    ArrayList<String> ProfilArrayList1 = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        SplashScreen splashScreen = SplashScreen.installSplashScreen(this);
       // setContentView(R.layout.activity_login);
        binding=ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth=FirebaseAuth.getInstance();
        store=FirebaseFirestore.getInstance();
         firebaseUser=auth.getCurrentUser();

        ProfilArrayList1.add("Teacher");
        ProfilArrayList1.add("Student");

        email = findViewById(R.id.loginEmail);
        password = findViewById(R.id.loginPassword);

        loginBtn = findViewById(R.id.loginBtn);
        account=findViewById(R.id.account);





        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateData();
            }
        });

        account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //confirm delete dialog

                String[] ProfilArray = new String[3];

                ProfilArray[0] = "Teacher";
                ProfilArray[1] = "Student";
                ProfilArray[2] = "Other";

                AlertDialog.Builder builder = new AlertDialog.Builder(Login.this);
                builder.setTitle("Choose your profile")
                        .setSingleChoiceItems(ProfilArray,2, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                //item Click
                                SelectedProfil = ProfilArray[i].toString();
                            }
                        })
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                                if(SelectedProfil == "Student"){
                                    Intent intent=new Intent(Login.this,RegisterStudentsActivity.class);
                                    startActivity(intent);
                                }else if(SelectedProfil == "Teacher"){
                                    Intent intent=new Intent(Login.this,RegisterTeacherActivity.class);
                                    startActivity(intent);
                                }
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        /*.setPositiveButton("Student", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                                Toast.makeText(Login.this, "Creat account as student...", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Login.this,RegisterStudentsActivity.class);
                                startActivity(intent);

                            }
                        })
                        .setNegativeButton("Teacher", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //dialogInterface.dismiss();

                                Toast.makeText(Login.this, "Creat account as Teacher...", Toast.LENGTH_SHORT).show();
                                Intent intent=new Intent(Login.this,RegisterTeacherActivity.class);
                                startActivity(intent);
                            }
                        })*/
                        .show();
            }
        });
/*
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(email);
                checkField(password);

                if(valid){
                    auth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            Toast.makeText(Login.this,"Login successful",Toast.LENGTH_SHORT).show();
                            checkUserAccessLevel(authResult.getUser().getUid());
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });
                }
            }
        });*/




    }
    private String mail="",pwd="";
    private void validateData() {
        mail=email.getText().toString();
        pwd=password.getText().toString();

//validate data
        if(!Patterns.EMAIL_ADDRESS.matcher( mail).matches()){
            Toast.makeText(this,"Forme email invalide",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(pwd)){
            Toast.makeText(this,"Entrez mot de passe",Toast.LENGTH_SHORT).show();
        }else{
            loginUser();
        }
    }

    private void loginUser() {
        auth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    checkUserAccessLevel();

                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "can't login due to " + task.getException(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void checkUserAccessLevel() {


        //check in db
        DatabaseReference df= FirebaseDatabase.getInstance().getReference("Users");
        //extract the date from the document
        df.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
String userType=""+snapshot.child("userType").getValue();
if(userType.equals("Prof")){
    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
    Intent intent=new Intent(getApplicationContext(),Accueil.class);
    intent.putExtra("id",firebaseUser.getUid());
    startActivity(intent);

finish();
}else if(userType.equals("Student")){
    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
    Intent intent=new Intent(getApplicationContext(),Accueil.class);
    intent.putExtra("id",firebaseUser.getUid());
    startActivity(intent);
finish();
}else if(userType.equals("admin")){
    Toast.makeText(getApplicationContext(), "welcome", Toast.LENGTH_SHORT).show();
    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
    finish();
}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });}
      /*      @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Log.d("TAG","onSuccess"+documentSnapshot.getData());
                if(documentSnapshot.getString("isProf")!=null){
                    startActivity(new Intent(getApplicationContext(),Prof.class));
                    finish();
                }if(documentSnapshot.getString("isStudent")!=null){
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();
                }

            }
        });*/

/*
    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }*/

    /*public void register(View view) {
        Intent intent=new Intent(Login.this,SecondPage.class);
        startActivity(intent);
    };*/
  /*  @Override
    protected void onStart() {
        super.onStart();

        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DocumentReference df=FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {

                    startActivity(new Intent(getApplicationContext(),Prof.class));
                    finish();



                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    FirebaseAuth.getInstance().signOut();
                    startActivity(new Intent(getApplicationContext(),Login.class));
                    finish();
                }
            });

        }
    }*/
}