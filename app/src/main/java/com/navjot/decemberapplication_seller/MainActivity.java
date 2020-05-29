package com.navjot.decemberapplication_seller;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import android.os.Handler;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.Adapter;
import android.widget.TextView;
import android.widget.Toast;


import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.navjot.decemberapplication_seller.model.FinalOrder;


import java.util.List;

import io.opencensus.internal.Utils;

public class MainActivity extends AppCompatActivity {


    // List<FinalOrder> list;

    RecyclerView recyclerView;
    FirestoreRecyclerOptions<FinalOrder> options;
    //  FirestoreRecyclerAdapter<FinalOrder,MyViewHolder> adapter;
    FirestoreRecyclerAdapter adapter;

    FirebaseFirestore firebaseFirestore;
    SwipeRefreshLayout swipeRefreshLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //  setTheme(R.style.Theme_AppCompat); //this line i added
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final SwipeRefreshLayout swipeRefreshLayout =  findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Toast.makeText(MainActivity.this, "refreshed", Toast.LENGTH_SHORT).show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);

            }
        });


        recyclerView = findViewById(R.id.OrderRecyclerView);
        firebaseFirestore = FirebaseFirestore.getInstance();

        getSupportActionBar().setTitle("Orders");


        Query query = firebaseFirestore.collection("orders")
                .orderBy("finalOrderDate", Query.Direction.DESCENDING);//.limit

       // firebaseFirestore.g



        options = new FirestoreRecyclerOptions.Builder<FinalOrder>().setQuery(query, FinalOrder.class).build();

        adapter = new FirestoreRecyclerAdapter<FinalOrder, MyViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull FinalOrder model) {

                holder.tvOrderID.setText(model.getFinalOrderID());
                holder.tvDate.setText(model.getFinalOrderDate());
                holder.tvTime.setText(model.getFinalOrderTime());
                holder.tvItems.setText(model.getFinalOrderedItems());
                holder.tvMethod.setText(model.getFinalOrderPaymentMethod());

                holder.tvName.setText(model.getFinalOrderDeliveryName());
                holder.tvPhone.setText(model.getFinalOrderDeliveryPhone());
                holder.tvAddr.setText(model.getFinalOrderDeliveryAddr());
                holder.tvTown.setText(model.getFinalOrderDeliveryTown());
                holder.tvCity.setText(model.getFinalOrderDeliveryCity());

                holder.tvPrice.setText(model.getFinalOrderPrice());



            }



            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_itemorder, parent, false);
                return new MyViewHolder(view);
            }
        };


        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setHasFixedSize(true);
//        AnimationSet set = new AnimationSet(true);
//
//        Animation animation = new AlphaAnimation(0.0f, 1.0f);
//        animation.setDuration(500);
//        set.addAnimation(animation);
//
//        animation = new TranslateAnimation(
//                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF, 0.0f,
//                Animation.RELATIVE_TO_SELF, -1.0f, Animation.RELATIVE_TO_SELF, 0.0f
//        );
//        animation.setDuration(100);
//        set.addAnimation(animation);
//
//      LayoutAnimationController  controller = new LayoutAnimationController(set, 0.5f);
//
//        adapter = new Adapter(poetNameSetGets, this);
//        recyclerView.setLayoutAnimation(controller);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }



    private class MyViewHolder extends RecyclerView.ViewHolder {


        private TextView tvOrderID, tvPrice, tvDate, tvTime, tvItems, tvName, tvPhone, tvAddr, tvTown, tvCity, tvMethod;
        CardView cardView;


        public MyViewHolder(@NonNull final View itemView) {
            super(itemView);


            tvOrderID = itemView.findViewById(R.id.tvid);
            tvPrice = itemView.findViewById(R.id.tvprice);
            tvDate = itemView.findViewById(R.id.tvdate);
            tvTime = itemView.findViewById(R.id.tvtime);

            tvItems = itemView.findViewById(R.id.tvOrderedItems);
            tvName = itemView.findViewById(R.id.tvDeliveryName);
            tvPhone = itemView.findViewById(R.id.tvDeliveryPhoneno);
            tvAddr = itemView.findViewById(R.id.tvDeliveryAddr);

            tvTown = itemView.findViewById(R.id.tvDeliveryTown);
            tvCity = itemView.findViewById(R.id.tvDeliveryCity);
            tvMethod = itemView.findViewById(R.id.tvPaymentMethod);


            cardView=itemView.findViewById(R.id.cardView);

/*
            ItemTouchHelper helper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
                @Override
                public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {

                    return false;
                }

                @Override
                public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {


                    Toast.makeText(getApplicationContext(), "SMS sent.",
                            Toast.LENGTH_LONG).show();


                    //adapter.notifyDataSetChanged();
                }

                @Override
                public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                    // view the background view
                }

            });


            helper.attachToRecyclerView(recyclerView);
*/


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public boolean onLongClick(View v) {
//
//
//                    final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                    builder.setMessage("Accept order ?");
//                    builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialogInterface, int i) {
//
//
                            String Phoneno = tvPhone.getText().toString();
//
                     SmsManager smsManager = SmsManager.getDefault();
                            smsManager.sendTextMessage(Phoneno, null,
                                    "Hi there, your order has been accepted. your order will be delivered within 2 hours      " +
                                            "-regards(veggie@home support team)", null, null);
                           Toast.makeText(getApplicationContext(), "SMS sent.",
                                    Toast.LENGTH_LONG).show();

                          cardView.setCardBackgroundColor(getColor(R.color.colorLightGreen));
//
//
//                        }
//                    }).setNegativeButton("Cancel", null);
//
//                    AlertDialog dialog = builder.create();
//                    dialog.show();


                    return true;


                }
            });


        }
    }

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


//
//    final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//                    builder.setMessage("Are You Sure to Logout ?");
//                            builder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
//@Override
//public void onClick(DialogInterface dialogInterface, int i) {
//
////
////                            String Phoneno=tvPhone.getText().toString();
////
////                            SmsManager smsManager = SmsManager.getDefault();
////                            smsManager.sendTextMessage(Phoneno, null,
////                                    "Hi there, your order has been accepted. your order will be delivered within 2 hours      " +
////                                            "-regards(veggie@home support team)", null, null);
//        Toast.makeText(getApplicationContext(), "SMS sent.",
//        Toast.LENGTH_LONG).show();
//
//
//        }
//        }).setNegativeButton("Cancel", null);
//
//        AlertDialog dialog = builder.create();
//        dialog.show();