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

public class adapter3  extends RecyclerView.Adapter<adapter3.HolderDem3>  {

    private Context context;
    public ArrayList<Emprun> CategorieArrayList;

    public adapter3(Context context, ArrayList<Emprun> categorieArrayList) {
        this.context = context;
        CategorieArrayList = categorieArrayList;

    }

    @NonNull
    @Override
    public adapter3.HolderDem3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.library_list_row2, parent, false);
        return new adapter3.HolderDem3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter3.HolderDem3 holder, int position) {

        //get data
        Emprun model = CategorieArrayList.get(position);
        String titre=model.getTitre();

        //set data
        holder.demandeTitle.setText(titre);
        holder.days.setText(model.getDaysLeft().toString());
        //   holder.bar.setProgress(model.getPercentDaysPassed());
   //     holder.user.setText(model.nom);


    }

    private void supp(Emprun model, HolderDem3 holder) {
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




    class HolderDem3 extends RecyclerView.ViewHolder{

        TextView demandeTitle,days;




        public HolderDem3(@NonNull View itemView) {
            super(itemView);

            demandeTitle = (TextView) itemView.findViewById(R.id.titleRow1);
            days=(TextView) itemView.findViewById(R.id.daysLeft1);


        }
    }
}
