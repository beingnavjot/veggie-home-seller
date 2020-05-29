package com.navjot.decemberapplication_seller;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.navjot.decemberapplication_seller.model.Vegetable;


import java.io.ByteArrayOutputStream;

public class AddVegetableActivity extends AppCompatActivity {

    Button choose, upload;
    ImageView image;
    int PICK_IMAGE_REQUEST = 111;

    Bitmap bitmap;
    EditText nametv, pricetv;
    Context context;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    private Uri filePath;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vegetable);

        choose = findViewById(R.id.choose);
        upload = findViewById(R.id.upload);
        nametv = findViewById(R.id.name);
        pricetv = findViewById(R.id.price);
        progressBar = findViewById(R.id.progressbar);

        firebaseFirestore = firebaseFirestore.getInstance();
        storageReference = FirebaseStorage.getInstance().getReference("vegetables");

        image = findViewById(R.id.image);

        // final String uid=firebaseAuth.getCurrentUser().getUid();

        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_PICK);
                // intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                // startActivityForResult(intent,PICK_IMAGE_REQUEST);


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                //    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                //    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                //    byte[] imageBytes = baos.toByteArray();
                //      final String url = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                //  Task<Uri> url= storageReference.getDownloadUrl();

                uploadData();


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);

                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }

    private void uploadData() {


        final String name = nametv.getText().toString();
        String p = pricetv.getText().toString();
        final Long price = Long.parseLong(p);

        if (name == null) {
            nametv.setError("enter vegetable name");
        }
        if (price == null) {
            pricetv.setError("enter vegetable price");
        } else {


            progressBar.setVisibility(View.VISIBLE);


            ///////////

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            byte[] imageBytes = baos.toByteArray();
            UploadTask uploadTask = FirebaseStorage.getInstance().getReference().child("vegetables").child(name + ".jpg").putBytes(imageBytes);
//
            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> downloadUrl = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                    String url = downloadUrl.toString();
                    Log.i("URL =======", url);

                    Vegetable vegetable = new Vegetable(name, url, price);


                    firebaseFirestore.collection("vegetables").document().set(vegetable)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        Toast.makeText(AddVegetableActivity.this, " Uploaded to firebase", Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
//                                                    Intent intent = new Intent(AddVegetableActivity.this, HomeActivity.class);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    startActivity(intent);
                                    } else {
                                        Toast.makeText(AddVegetableActivity.this, "not uploaded  ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(context, "not uploaded", Toast.LENGTH_SHORT).show();
                        }
                    });


            //////////



     /*       if (filePath != null) {
                StorageReference filereference = storageReference.child(name
                        + "." + getFileExtension(filePath));

                filereference.putFile(filePath)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                                byte[] imageBytes = baos.toByteArray();
                               // final String url = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                                    Task<Uri> downloaduri=taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                String url=downloaduri.toString();
                                Log.e("Download URL==== ",url);
                                Vegetable vegetable = new Vegetable(name, url,price);





                                firebaseFirestore.collection("vegetables").document().set(vegetable)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isComplete()) {
                                                    Toast.makeText(AddVegetableActivity.this, " Uploaded to firebase", Toast.LENGTH_SHORT).show();
                                                    progressBar.setVisibility(View.GONE);
//                                                    Intent intent = new Intent(AddVegetableActivity.this, HomeActivity.class);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    startActivity(intent);
                                                } else {
                                                    Toast.makeText(AddVegetableActivity.this, "not uploaded  ", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });




                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else {
                Toast.makeText(context, "no image selected", Toast.LENGTH_SHORT).show();
            }
    */
        }
    }

}












































































/*

public class AddVegetableActivity extends AppCompatActivity {

    Button choose, upload;
    ImageView image;
    int PICK_IMAGE_REQUEST = 111;

    Bitmap bitmap;
    ProgressDialog progressDialog;
    EditText nametv, pricetv;
    Context context;
    StorageReference storageReference;
    FirebaseFirestore firebaseFirestore;
    private Uri filePath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vegetable);

        choose = findViewById(R.id.choose);
        upload = findViewById(R.id.upload);
        nametv = findViewById(R.id.name);
        pricetv = findViewById(R.id.price);
        firebaseFirestore = firebaseFirestore.getInstance();
        storageReference= FirebaseStorage.getInstance().getReference();

        image=findViewById(R.id.image);



        choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //  intent.setAction(Intent.ACTION_PICK);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), PICK_IMAGE_REQUEST);
                // startActivityForResult(intent,PICK_IMAGE_REQUEST);


            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String name = nametv.getText().toString();
                String p = pricetv.getText().toString();
                Long price = Long.parseLong(p);

                if (name == null) {
                    nametv.setError("enter vegetable name");
                }
                if (price == null) {
                    pricetv.setError("enter vegetable price");
                } else {

                    progressDialog = new ProgressDialog(AddVegetableActivity.this);
                    progressDialog.setMessage("Uploading, please wait...");
                    progressDialog.show();


                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] imageBytes = baos.toByteArray();
                    final String url = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    UploadTask image_path=storageReference.child()

                    if (filePath != null)
                    {
                        //String url=
                    }



                    //upload data to firebase  -------------------


                    Vegetable vegetable = new Vegetable(name, url, price);


                    firebaseFirestore.collection("vegetables").document().set(vegetable)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        Toast.makeText(AddVegetableActivity.this, " Uploaded to firebase", Toast.LENGTH_SHORT).show();

                                        Intent intent = new Intent(AddVegetableActivity.this, HomeActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                    } else {
                                        Toast.makeText(AddVegetableActivity.this, "not uploaded  ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                    progressDialog.dismiss();
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();

            try {
                //getting image from gallery
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                //2 Picasso.with(this).load(filePath).into(image);
                //  filePath.setImageUri(filePath);



                //Setting image to ImageView
                image.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private String getFileExtension(Uri uri)
    {
        ContentResolver cr=getContentResolver();
        MimeTypeMap mime=MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(uri));
    }
}
*/














































/*
     upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final String name = nametv.getText().toString();
                String p = pricetv.getText().toString();
                final Long price = Long.parseLong(p);

                if (name == null) {
                    nametv.setError("enter vegetable name");
                }
                if (price == null) {
                    pricetv.setError("enter vegetable price");
                } else {


                    progressBar.setVisibility(View.VISIBLE);

                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 60, baos);
                    byte[] imageBytes = baos.toByteArray();
                    //  final String url = Base64.encodeToString(imageBytes, Base64.DEFAULT);
                    final UploadTask image_path = storageReference.child("url").child("image" + ".jpg").putBytes(imageBytes);

                    image_path.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                            if (task.isSuccessful()) {

                                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri downloadUrl) {
                                        //do something with downloadurl


                                        Map<String, String> data = new HashMap<>();

                                        data.put("name", name);
                                        data.put("price", price + "");
                                        data.put("url", downloadUrl.toString());


                                        //upload data to firebase  -------------------

                                        firebaseFirestore.collection("vegetables").document().set(data)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isComplete()) {
                                                            Toast.makeText(AddVegetableActivity.this, " Uploaded to firebase", Toast.LENGTH_SHORT).show();
                                                            progressBar.setVisibility(View.GONE);
//                                    Intent intent = new Intent(AddVegetableActivity.this, HomeActivity.class);
//                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                    startActivity(intent);
                                                        } else {
                                                            Toast.makeText(AddVegetableActivity.this, "not uploaded  ", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });


                                    }
                                });

//                               storeData(task,
//                                       name,
//                                       price );


                            } else {
                                String error = task.getException().getMessage().toString();
                                Toast.makeText(context, "Error: " + error, Toast.LENGTH_SHORT).show();
                              //  progressBar.setVisibility(View.GONE);
                            }


                        }
                    });


                    //upload data to firebase  -------------------


                    //   Vegetable vegetable = new Vegetable(name, url, price);

/*
                    firebaseFirestore.collection("vegetables").document().set(data)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isComplete()) {
                                        Toast.makeText(AddVegetableActivity.this, " Uploaded to firebase", Toast.LENGTH_SHORT).show();

                                            Intent intent = new Intent(AddVegetableActivity.this, HomeActivity.class);
                                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                           startActivity(intent);
                                    } else {
                                        Toast.makeText(AddVegetableActivity.this, "not uploaded  ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
*/


