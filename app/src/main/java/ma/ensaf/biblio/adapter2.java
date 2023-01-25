package ma.ensaf.biblio;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;

public class adapter2  extends RecyclerView.Adapter<adapter2.HolderDem2>  {

    private Context context;
    public ArrayList<Emprun> CategorieArrayList;

    public adapter2(Context context, ArrayList<Emprun> categorieArrayList) {
        this.context = context;
        CategorieArrayList = categorieArrayList;

    }

    @NonNull
    @Override
    public adapter2.HolderDem2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_list_row, parent, false);
        return new adapter2.HolderDem2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter2.HolderDem2 holder, int position) {

        //get data
        Emprun model = CategorieArrayList.get(position);
        String titre=model.getTitre();

        //set data
        holder.demandeTitle.setText(titre);
        holder.days.setText("Days Left "+model.getDaysLeft().toString());
     //   holder.bar.setProgress(model.getPercentDaysPassed());
      holder.user.setText(model.nom);
      holder.supp.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              supp(model,holder);
          }
      });

    }

    private void supp(Emprun model, HolderDem2 holder) {
        String id = model.getBookId();
        //get id of category to delete
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Emprunts");
        ref.child(id)
                .removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Deleted successfully", Toast.LENGTH_SHORT).show();

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(context, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


    @Override
    public int getItemCount() {
        return CategorieArrayList.size();
    }




    class HolderDem2 extends RecyclerView.ViewHolder{

        TextView demandeTitle,days,user;

        ImageButton supp;


        public HolderDem2(@NonNull View itemView) {
            super(itemView);

            demandeTitle = (TextView) itemView.findViewById(R.id.titleRow);
            days=(TextView) itemView.findViewById(R.id.daysLeft);
            supp=(ImageButton) itemView.findViewById(R.id.suppCatBtn);
            user=(TextView)itemView.findViewById(R.id.authorRow);

        }
    }
}
