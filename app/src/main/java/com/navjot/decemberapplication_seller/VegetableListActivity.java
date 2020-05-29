package com.navjot.decemberapplication_seller;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.StorageReference;
import com.navjot.decemberapplication_seller.model.Vegetable;
import com.squareup.picasso.Picasso;


public class VegetableListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    FirestoreRecyclerOptions<Vegetable> options;
    FirestoreRecyclerAdapter<Vegetable, MyViewHolder> adapter;
    FirebaseFirestore firebaseFirestore;
    StorageReference storageReference;
    DocumentSnapshot documentSnapshot;
    String name;
    String updatedPrice;
    Long price;
    static final int request = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vegetable_list);

        recyclerView = findViewById(R.id.VegetableRecyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();


        getData();

    }


    private void getData() {


        Query query= firebaseFirestore.collection("vegetables").orderBy("name");
        options= new FirestoreRecyclerOptions.Builder<Vegetable>().setQuery(query,Vegetable.class).build();

        adapter= new FirestoreRecyclerAdapter<Vegetable, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Vegetable model) {

                holder.textViewName.setText(model.getName());
                holder.textViewPrice.setText(""+model.getPrice());
                Log.e("Price",""+model.getPrice());
                Picasso.get().load(model.getUrl()).fit().centerCrop().into(holder.imageView);

            }

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_item,parent,false);
                return new MyViewHolder(view);
            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(adapter);
    }




    private class MyViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener
            , MenuItem.OnMenuItemClickListener {

        private ImageView imageView;
        private TextView textViewName, textViewPrice;


        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.custom_itemImage);
            textViewName = itemView.findViewById(R.id.vegetableName);
            textViewPrice = itemView.findViewById(R.id.vegetablePrice);
            imageView = itemView.findViewById(R.id.custom_itemImage);
            textViewName = itemView.findViewById(R.id.vegetableName);
            textViewPrice = itemView.findViewById(R.id.vegetablePrice);

            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            MenuItem edit = menu.add(Menu.NONE, 1, 1, "edit price");
            MenuItem delete = menu.add(Menu.NONE, 2, 2, "delete item");


            edit.setOnMenuItemClickListener(this);
            delete.setOnMenuItemClickListener(this);
        }


        @Override
        public boolean onMenuItemClick(MenuItem item) {


            final int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {

                if (item.getItemId() == 1) {

                    Toast.makeText(VegetableListActivity.this, "option not available now", Toast.LENGTH_SHORT).show();

                } else if (item.getItemId() == 2) {

                    deleteItem(getAdapterPosition());

                }

            }

            return false;
        }

        public void deleteItem(int position) {
            adapter.getSnapshots().getSnapshot(position).getReference().delete();
        }

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if(requestCode==request && resultCode==RESULT_OK && data!=null)
//        {
//            // Intent intent=getIntent();
//            Intent i= new Intent();
//           // String ifsc=data.getStringExtra("ifscData");
//            updatedPrice=data.getStringExtra("priceData");
//            Long p=Long.parseLong(updatedPrice);
//
//            firebaseFirestore.collection("vegetables").document().update("name" ,p);
//
//
//        }
 //   }
    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }
}
