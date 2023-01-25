package ma.ensaf.biblio;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.joda.time.Days;
import org.joda.time.LocalDate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import ma.ensaf.biblio.databinding.ActivityAccueilBinding;
import ma.ensaf.biblio.databinding.ActivityDetailsBinding;

public class Details extends AppCompatActivity {
Button favorie,wish,borrow;
ImageButton back;
TextView textTitle,auteur;
TextView descr;
ImageView img;
String fil;
//id
String bookId,titre;

//binding
ActivityDetailsBinding binding;

DatabaseReference ref,db,bookRef,likesRef;
FirebaseUser firebaseUser;

//borrow

    static Library myLibrary;
LocalDate borrowDate,dueDate;

int daysLeft;

boolean isInMyFavorite=false;
boolean isInMyWish=false;
boolean isAvailable;
    String brDate,duDate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        firebaseUser=FirebaseAuth.getInstance().getCurrentUser();
        ref=FirebaseDatabase.getInstance().getReference("Users");
     //   fil=ref.child(firebaseUser.getUid()).child("Filiere");
      //  Toast.makeText(this, fil, Toast.LENGTH_SHORT).show();
     //   Log.e("fililere", "onCreate: "+fil);


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        borrowDate = new LocalDate();

        dueDate = borrowDate.plusDays(7);
        daysLeft= Days.daysBetween(new LocalDate() , dueDate).getDays();



        //get data from intent
        Intent intent=getIntent();
        bookId=intent.getStringExtra("bookId");
        titre=intent.getStringExtra("titre");


        //borrow
//        adapter = new LibraryListAdapter(this,myLibrary.getLibrary());

        borrow=findViewById(R.id.btnBorrow);

         //book to borrow
          bookRef=FirebaseDatabase.getInstance().getReference("Emprunts");

          borrow.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  borrowBook();
              }
          });






        back=findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();
//finish();
            }
        });

        wish=findViewById(R.id.wish);
        wish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInMyWish){
                    removeWish();}
                else {
                    addWish();
                }

            }
        });
         favorie=(Button)findViewById(R.id.fav);
         favorie.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(isInMyFavorite){
                    removeFav();}
                 else {
                 addFav();
                 }

             }
         });
      /*   favorie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInMyFavorite){
                    //in fav so remove
                    BooksList.removeFromFavorites(Details.this,bookId);
                }else{

                    BooksList.addToFavorite(Details.this,bookId);}
            }
        });*/
        textTitle=findViewById(R.id.tvTitle);
        descr=findViewById(R.id.tvDesc);
        auteur=findViewById(R.id.tvAut);
        img=findViewById(R.id.imageView2);
       // Picasso.load(getIntent ().getStringExtra ( "image")).into(imageView);
        Picasso.get().load(getIntent().getStringExtra("image")).into(img);
        //img.setImageResource(getIntent().getStringExtra("Image"));
        textTitle.setText("Titre : "+getIntent().getStringExtra("titre"));
        descr.setText("Descriptions du livre : "+getIntent().getStringExtra("description"));
        auteur.setText("Auteur : "+getIntent().getStringExtra("auteur"));




       if(firebaseUser!=null){
           checkIsFavorite();
           checkIsWish();
        }else{
            Toast.makeText(Details.this,"something went wrong",Toast.LENGTH_SHORT).show();
        }
        //handle click to add/remove fav


    }

    private void borrowBook() {

        bookRef.child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(Details.this, "This book is already borrowed", Toast.LENGTH_SHORT).show();}
                else
                {
                    ref.child(firebaseUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.hasChild("Emprunts") ){

                                Toast.makeText(Details.this, "You cannot borrow more than 1 book", Toast.LENGTH_SHORT).show();
                                borrow.setEnabled(false);
                                borrow.setTextColor(Color.RED);
                            }
                            else{
                                Map<String,Object> borrowInfo2=new HashMap<>();

                                borrowInfo2.put("bookId",bookId);
                                borrowInfo2.put("titre",titre);
                                borrowInfo2.put("uid",firebaseUser.getUid());
                                borrowInfo2.put("borrowDate",borrowDate.toString());
                                borrowInfo2.put("dueDate",dueDate.toString());
                                borrowInfo2.put("daysLeft",daysLeft);
                                bookRef.child(firebaseUser.getUid()).setValue(borrowInfo2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Details.this,"request sent",Toast.LENGTH_SHORT).show();


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Details.this,"failed to send request",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                ref.child(firebaseUser.getUid()).setValue(borrowInfo2).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(Details.this,"request sent",Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Details.this,"failed to send request",Toast.LENGTH_SHORT).show();

                                    }
                                });
                                //  borrowInfo.put("nom",db.child(firebaseUser.getUid()).child("FullName"));



                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //tester s'il y a deja un emprunt
        //send message "YOUR REQUEST HAS BEEN SENT"




    }

    private void checkIsWish() {
        ref.child(firebaseUser.getUid()).child("Wishlist").child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isInMyWish=snapshot.exists();//true if exists
                if(isInMyWish){
                    wish.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_baseline_wish,0,0);

                }else
                {
                    wish.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_baseline_wish_not,0,0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void removeWish() {
        ref.child(firebaseUser.getUid()).child("Wishlist").child(bookId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Details.this, "removed from your wishlist", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Failed to  remove from wishlist due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addWish() {
        long timestamp=System.currentTimeMillis();
        //setup data to add in firebase
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("bookId",""+bookId);
        hashMap.put("timestamp",""+timestamp);


        ref.child(firebaseUser.getUid()).child("Wishlist").child(bookId)
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Details.this, "Added to your wishlist", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Failed to  add to wishlist due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void removeFav() {


        ref.child(firebaseUser.getUid()).child("Favorites").child(bookId).removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Details.this, "removed from your favorite list", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Failed to  remove from favorites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void addFav() {
        long timestamp=System.currentTimeMillis();
        //setup data to add in firebase
        HashMap<String,Object> hashMap=new HashMap<>();
        hashMap.put("bookId",""+bookId);
        hashMap.put("timestamp",""+timestamp);


        ref.child(firebaseUser.getUid()).child("Favorites").child(bookId)
                .setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(Details.this, "Added to your favorite list", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Details.this, "Failed to  add to favorites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }




    private void checkIsFavorite() {


        ref.child(firebaseUser.getUid()).child("Favorites").child(bookId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                isInMyFavorite=snapshot.exists();//true if exists
                if(isInMyFavorite){
                    favorie.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_baseline_favorite_24,0,0);

                }else
                {
                    favorie.setCompoundDrawablesRelativeWithIntrinsicBounds(0,R.drawable.ic_baseline_favorite_border_24,0,0);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public static void removeFromFavorites(Context context,String bookId){
        //we can only remove if user is logged in
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();


            //save to db
            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
            ref.child(firebaseAuth.getUid()).child("Favorites").child(bookId)
                    .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Toast.makeText(context, "Removed successfully ", Toast.LENGTH_SHORT).show();

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "Failed to  remove from favorites due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
    }

    public static void removeFromWish(Context context,String bookId){
        //we can only remove if user is logged in
        FirebaseAuth firebaseAuth= FirebaseAuth.getInstance();


        //save to db
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child(firebaseAuth.getUid()).child("Wishlist").child(bookId)
                .removeValue().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(context, "Removed successfully ", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "Failed to  remove from wishlist due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }



}