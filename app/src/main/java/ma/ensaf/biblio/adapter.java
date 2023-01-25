package ma.ensaf.biblio;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import ma.ensaf.biblio.EspaceAdmin.ConfirmBorrow;

public class adapter extends RecyclerView.Adapter<adapter.HolderDem>  {

    private Context context;
    public ArrayList<Emprun> CategorieArrayList;

    public adapter(Context context, ArrayList<Emprun> categorieArrayList) {
        this.context = context;
        CategorieArrayList = categorieArrayList;

    }

    @NonNull
    @Override
    public adapter.HolderDem onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dem, parent, false);
        return new adapter.HolderDem(view);
    }

    @Override
    public void onBindViewHolder(@NonNull adapter.HolderDem holder, int position) {

        //get data
        Emprun model = CategorieArrayList.get(position);
        String titre=model.getTitre();
        //set data
        holder.demandeTitle.setText(titre);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ConfirmBorrow.class);
                intent.putExtra("bookId",model.getBookId());
                intent.putExtra("uid",model.getUid());
                intent.putExtra("titre",titre);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });







    }



    @Override
    public int getItemCount() {
        return CategorieArrayList.size();
    }




    class HolderDem extends RecyclerView.ViewHolder{

        TextView demandeTitle;


        public HolderDem(@NonNull View itemView) {
            super(itemView);

            demandeTitle = (TextView) itemView.findViewById(R.id.bookTitleTv);

        }
    }
}
