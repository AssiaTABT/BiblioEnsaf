package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
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

import ma.ensaf.biblio.databinding.ActivityRegisterTeacherBinding;

public class RegisterTeacherActivity extends AppCompatActivity {
    EditText fullName,email,password;
    Button registerBtn,goToLogin;
    boolean valid = true;
    FirebaseAuth auth;
    DatabaseReference ref;

    ActivityRegisterTeacherBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //   setContentView(R.layout.activity_second_page);
        binding=ActivityRegisterTeacherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //handle click begin register


        auth=FirebaseAuth.getInstance();


        fullName = findViewById(R.id.registerName12);
        email = findViewById(R.id.registerEmail12);
        password = findViewById(R.id.registerPassword12);
        registerBtn = findViewById(R.id.registerBtn12);
        goToLogin = findViewById(R.id.gotoLogin12);



        goToLogin.setOnClickListener(new View.OnClickListener() {
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


    }
    String name="",emailAdd="",mdp="",fil="";
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
    }

    private void createUserAccount() {
        DatabaseReference df = FirebaseDatabase.getInstance().getReference("Users");
        String uid = auth.getUid();

        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("uid", uid);
        userInfo.put("FullName", name);
        userInfo.put("Email", emailAdd);
        userInfo.put("Password", mdp);
        userInfo.put("userType", "Prof");

        //add data to db
        df.child(uid).setValue(userInfo).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(RegisterTeacherActivity.this, "Account created", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(RegisterTeacherActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }}