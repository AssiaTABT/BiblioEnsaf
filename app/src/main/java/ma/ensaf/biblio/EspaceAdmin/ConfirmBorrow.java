package ma.ensaf.biblio.EspaceAdmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.HashMap;

import ma.ensaf.biblio.R;

public class ConfirmBorrow extends AppCompatActivity {
Button refuse,accept;
String bookId,UserId,titre;
DatabaseReference ref,db,book;
    LocalDate borrowDate,dueDate;
    private ImageButton back;


    TextView textTitle,location,nom,role;
    TextView descr;
    ImageView img;

    int daysLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_borrow);

        back = (ImageButton)findViewById(R.id.BackBtndd);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        refuse=findViewById(R.id.refuse);
        accept=findViewById(R.id.accept);

        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        borrowDate = new LocalDate();

        dueDate = borrowDate.plusDays(7);
        daysLeft= Days.daysBetween(new LocalDate() , dueDate).getDays();

        //get data from intent
        Intent intent=getIntent();

        bookId=intent.getStringExtra("bookId");
        UserId=intent.getStringExtra("uid");
        titre= intent.getStringExtra("titre");

        nom=findViewById(R.id.nomTv);
        role=findViewById(R.id.roleTv);
        img=findViewById(R.id.imageView4);

        db=FirebaseDatabase.getInstance().getReference("Users");
        Query query1=db.orderByChild("uid").equalTo(UserId);
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    // String bk=dataSnapshot1.child("auteur").getValue(String.class);
                    String Name=dataSnapshot1.child("FullName").getValue(String.class);
                    String iden=dataSnapshot1.child("userType").getValue(String.class);

                    role.setText(iden);
                    nom.setText(Name);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        book=FirebaseDatabase.getInstance().getReference();
        Query query=book.child("Books1").orderByChild("IdLivre").equalTo(bookId);
        query.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot1: snapshot.getChildren()){
                    // String bk=dataSnapshot1.child("auteur").getValue(String.class);
                    String image=dataSnapshot1.child("imageLivre").getValue(String.class);

                    Picasso.get().load(image).into(img);

                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        textTitle=findViewById(R.id.titreTv);


       //  Picasso.load(getIntent ().getStringExtra ( "image")).into(img);
      //  Picasso.get().load(getIntent().getStringExtra("image")).into(img);
        //img.setImageResource(getIntent().getStringExtra("Image"));
        textTitle.setText(getIntent().getStringExtra("titre"));

//        auteur.setText(getIntent().getStringExtra("auteur"));


        ref= FirebaseDatabase.getInstance().getReference("Emprunts").child(bookId).child("uid");

       // UserId= ref.child(bookId).child("uid").getValue(String.class);

        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference("Emprunts").child(bookId).removeValue();
                finish();
            }
        });
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String userId=snapshot.getValue(String.class);

                        HashMap<String,Object> hashMap=new HashMap<>();
                        hashMap.put("bookid",""+bookId);
                        hashMap.put("borrowDate",borrowDate.toString());
                        hashMap.put("titre",titre);
                        hashMap.put("dueDate",dueDate.toString());
                        hashMap.put("daysLeft",daysLeft);
                        db.child(userId).child("Emprunts").setValue(hashMap);
                        Toast.makeText(ConfirmBorrow.this,"success",Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });


                //setup data to upload
              /*  HashMap<String,Object> hashMap=new HashMap<>();
                hashMap.put("bookid",""+bookId);
                hashMap.put("borrowDate",borrowDate.toString());
                hashMap.put("dueDate",dueDate.toString());
                hashMap.put("daysLeft",daysLeft);
                db.child(UserId).child("Emprunts").setValue(hashMap);
                finish();
                Toast.makeText(ConfirmBorrow.this, ref.child(bookId).child("uid").getValue(String.class), Toast.LENGTH_SHORT).show();
             //   ref.child(bookId).removeValue();
*/
            }
        });
    }
}