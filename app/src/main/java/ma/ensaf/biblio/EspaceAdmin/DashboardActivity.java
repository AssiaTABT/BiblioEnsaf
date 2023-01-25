package ma.ensaf.biblio.EspaceAdmin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ma.ensaf.biblio.Demande;
import ma.ensaf.biblio.Login;
import ma.ensaf.biblio.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class DashboardActivity extends AppCompatActivity {

    CardView AjouterLivre, Livres, Etudiants, Enseignants, Demandes, empruntes;
    ImageButton LogoutBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        AjouterLivre = (CardView) findViewById(R.id.AjouterLivreCard);
        Livres = (CardView) findViewById(R.id.idLivres);
        Etudiants = (CardView) findViewById(R.id.Etudiants);
        Enseignants = (CardView) findViewById(R.id.Enseignants);
        Demandes = (CardView) findViewById(R.id.demandes);
        empruntes = (CardView) findViewById(R.id.Empruntes);
        LogoutBtn = (ImageButton) findViewById(R.id.LogoutBtn);

       /* backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });*/

        Livres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, CategorieActivity.class));
            }
        });
        Etudiants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, EEEActivity.class));
            }
        });
        Enseignants.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, ProfActivity.class));
            }
        });
        empruntes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this,borrowList.class));
            }
        });
        AjouterLivre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, AjouterLivres.class));
            }
        });
        Demandes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Demande.class));
            }
        });

        LogoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(DashboardActivity.this, Login.class));
            }
        });
    }
}