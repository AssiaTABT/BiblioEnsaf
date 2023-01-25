package ma.ensaf.biblio.EspaceAdmin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import ma.ensaf.biblio.R;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

public class DetailsLivre extends AppCompatActivity {

    Button favorie,wish,borrow;
    ImageButton back;
    TextView textTitle,auteur, pages, localisation;
    TextView descr;
    ImageView img;

    //id
    String bookId,titre;

    //binding


    DatabaseReference ref,db,bookRef;
    FirebaseUser firebaseUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_livre);

        textTitle=findViewById(R.id.tvTitle);
        descr=findViewById(R.id.tvDesc);
        auteur=findViewById(R.id.tvAut);
        pages=findViewById(R.id.tvPages);
        localisation=findViewById(R.id.tvLoc);
        img=findViewById(R.id.imageView2);

        back = (ImageButton) findViewById(R.id.BackBtnbb);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        //Picasso.load(getIntent ().getStringExtra ( "image")).into(imageView);
        Picasso.get().load(getIntent().getStringExtra("image")).into(img);
        //img.setImageResource(getIntent().getStringExtra("Image"));
        textTitle.setText("Titre : "+getIntent().getStringExtra("titre"));
        descr.setText("Description: "+getIntent().getStringExtra("description"));
        auteur.setText("Auteur : "+getIntent().getStringExtra("auteur"));
        pages.setText("Numéro de pages : "+getIntent().getStringExtra("pages"));
        localisation.setText("Location : "+getIntent().getStringExtra("loc"));
    }
}