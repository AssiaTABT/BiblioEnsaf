package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class drawer_base extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout dl;

    @Override
    public void setContentView(View view) {
        dl = (DrawerLayout) getLayoutInflater().inflate(R.layout.activity_drawer_base, null);
        FrameLayout container = dl.findViewById(R.id.activityContainer);
        container.addView(view);
        super.setContentView(dl);

        Toolbar toolbar = dl.findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView nv = dl.findViewById(R.id.nav_view);
        nv.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle tl = new ActionBarDrawerToggle(this, dl, toolbar, R.string.open, R.string.close);
        dl.addDrawerListener(tl);
        tl.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        dl.closeDrawer(GravityCompat.START);


        switch(item.getItemId()){
            case R.id.idhome:
                startActivity(new Intent(this,Accueil.class));
                break;

            case R.id.etudiant_Parcourir:
                startActivity(new Intent(this,CatActivityUser.class));
                break;
            case R.id.etudiant_emprunt:
                startActivity(new Intent(this,borrowList2.class));
                break;
            case R.id.favorite:
                startActivity(new Intent(this,FavList.class));
                break;
            case R.id.listWish:
                startActivity(new Intent(this,WishList.class));
                break;
            case R.id.Recommendation:
                startActivity(new Intent(this,Recommend.class));
                break;
            case R.id.Logout:
                startActivity(new Intent(this,Login.class));
                FirebaseAuth.getInstance().signOut();
                break;


        }
        return false;
    }

    protected void activityTitle(String title){
        if(getSupportActionBar()!=null){
            getSupportActionBar().setTitle(title);
        }
    }
}