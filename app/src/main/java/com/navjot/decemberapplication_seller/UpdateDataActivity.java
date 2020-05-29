package com.navjot.decemberapplication_seller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UpdateDataActivity extends AppCompatActivity {

    EditText price;
    Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_data);

        price=findViewById(R.id.newPricee);
        update=findViewById(R.id.updateButton);
       final String pp=price.getText().toString();


        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getApplicationContext(),MainActivity.class);
                //  Intent intent2 = new Intent(IFSCActivity.this, MainActivity.class);
                intent.putExtra("priceData",pp);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
    }
}
