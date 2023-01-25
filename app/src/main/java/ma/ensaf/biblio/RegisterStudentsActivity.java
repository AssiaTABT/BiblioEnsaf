package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.splashscreen.SplashScreen;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Map;
import java.util.zip.Inflater;

import ma.ensaf.biblio.databinding.ActivityRegisterStudentsBinding;
import ma.ensaf.biblio.databinding.ActivityRegisterStudentsBinding;

public class RegisterStudentsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private Spinner spin;
    EditText fullName,email,password,phone;
    Button registerBtn,goToLogin;
    boolean valid = true;
    CheckBox prof,student;
    FirebaseAuth auth;
    FirebaseFirestore store;
    String fil;
    TextView already;
    String s;
    ActivityRegisterStudentsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
           super.onCreate(savedInstanceState);
    /*    try {
        Thread.sleep(2000);
    } catch (InterruptedException e) {
        e.printStackTrace();
    }
    SplashScreen splashScreen = SplashScreen.installSplashScreen(this);*/
    //   setContentView(R.layout.activity_second_page);
    binding=ActivityRegisterStudentsBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    //handle click begin register



    auth=FirebaseAuth.getInstance();
    store=FirebaseFirestore.getInstance();

    fullName = findViewById(R.id.registerName);
    email = findViewById(R.id.registerEmail);
    password = findViewById(R.id.registerPassword);

    registerBtn = findViewById(R.id.registerBtn);
    already=findViewById(R.id.already);

    spin=findViewById(R.id.spinner);


    ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(RegisterStudentsActivity.this,R.array.filieres, android.R.layout.simple_spinner_item);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    spin.setAdapter(adapter);
    // fil=spin.getSelectedItemPosition();

    //   spin.setOnItemSelectedListener(this);
       spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
             fil=parent.getItemAtPosition(position).toString();
            Toast.makeText(getApplicationContext(), String.valueOf(fil), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    });


        already.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(),Login.class));
            finish();
        }
    });
        registerBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            validateData();

        }
    });


    //check box validation
     /*   if(!(prof.isChecked()) ||(student.isChecked() ))
        {
            Toast.makeText(SecondPage.this,"Select the account type",Toast.LENGTH_SHORT).show();
            return;

        }
        //only one checkbox should be checked
        student.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){

                    prof.setChecked(false);
                }

            }
        });
        prof.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(buttonView.isChecked()){
                    student.setChecked(false);
                }

            }
        });*/





      /*  registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkField(fullName);
                checkField(email);
                checkField(password);
                if(student.isChecked())
                {checkField(phone);}

                validateData();



                if(valid){
                    //start the user registration process
                    auth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {


                            if(prof.isChecked()){

                                FirebaseUser user=auth.getCurrentUser();
                                Toast.makeText(SecondPage.this,"Account created",Toast.LENGTH_SHORT).show();
                                DocumentReference df=store.collection("Users").document(user.getUid());
                                Map<String,Object> userInfo=new HashMap<>();
                                userInfo.put("FullName",fullName.getText().toString());
                                userInfo.put("Email",email.getText().toString());
                                userInfo.put("isProf","1");
                                df.set(userInfo);
                                startActivity(new Intent(getApplicationContext(),Prof.class));
                                finish();
                            }
                            if(student.isChecked()){
                                FirebaseUser user=auth.getCurrentUser();
                                Toast.makeText(SecondPage.this,"Account created",Toast.LENGTH_SHORT).show();
                                DocumentReference df=store.collection("Users").document(user.getUid());
                                Map<String,Object> userInfo=new HashMap<>();
                                userInfo.put("FullName",fullName.getText().toString());
                                userInfo.put("Email",email.getText().toString());
                                userInfo.put("isStudent","1");
                                userInfo.put("Filière",phone.getText().toString());
                                df.set(userInfo);
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                finish();

                            }
                            //specify if the user is admin

                            //save data

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(SecondPage.this,"Account creation failed",Toast.LENGTH_SHORT).show();

                        }
                    });
                }


            }
        });*/



}
    String name="",emailAdd="",mdp="";
    private void validateData() {
        //validate data
        name=fullName.getText().toString().trim();
        emailAdd=email.getText().toString().trim();
        mdp=password.getText().toString().trim();


        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Entrez votre nom",Toast.LENGTH_SHORT).show();
        }
        else  if(!Patterns.EMAIL_ADDRESS.matcher( emailAdd).matches()){
            Toast.makeText(this,"Forme email invalide",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(mdp)){
            Toast.makeText(this,"Entrez mot de passe",Toast.LENGTH_SHORT).show();
        }

        else{
            createUserAccount();
        }
      /* if(prof.isChecked()){
        if(TextUtils.isEmpty(name)){
            Toast.makeText(this,"Entrez votre nom",Toast.LENGTH_SHORT).show();
        }
        else  if(!Patterns.EMAIL_ADDRESS.matcher( emailAdd).matches()){
            Toast.makeText(this,"Forme email invalide",Toast.LENGTH_SHORT).show();
        }
        else  if(TextUtils.isEmpty(mdp)){
            Toast.makeText(this,"Entrez mot de passe",Toast.LENGTH_SHORT).show();
        }else{
            createUserAccount();
        }}
        else if(student.isChecked()){
           if(TextUtils.isEmpty(name)){
               Toast.makeText(this,"Entrez votre nom",Toast.LENGTH_SHORT).show();
           }
           else  if(!Patterns.EMAIL_ADDRESS.matcher( emailAdd).matches()){
               Toast.makeText(this,"Forme email invalide",Toast.LENGTH_SHORT).show();
           }
           else  if(TextUtils.isEmpty(mdp)){
               Toast.makeText(this,"Entrez mot de passe",Toast.LENGTH_SHORT).show();
           } }else  if(TextUtils.isEmpty(fil)){
           Toast.makeText(this,"Entrez votre filière",Toast.LENGTH_SHORT).show();
       }else {
               createUserAccount();
          }*/
    }


    private void createUserAccount() {
        auth.createUserWithEmailAndPassword(emailAdd,mdp).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {

                updateUserInfo();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterStudentsActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUserInfo() {

        String uid= auth.getUid();
        DatabaseReference df= FirebaseDatabase.getInstance().getReference("Users");
        Map<String,Object> userInfo=new HashMap<>();
        userInfo.put("uid",uid);
        userInfo.put("FullName",name);
        userInfo.put("Email",emailAdd);
        userInfo.put("Password",mdp);
        userInfo.put("userType","admin");


        userInfo.put("Filiere",fil);

        //add data to db
        df.child(uid).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterStudentsActivity.this,"Account created",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(),Accueil.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterStudentsActivity.this,""+e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        int getid = parent.getSelectedItemPosition();
      //  fil=position;
        Toast.makeText(this, String.valueOf(position), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}