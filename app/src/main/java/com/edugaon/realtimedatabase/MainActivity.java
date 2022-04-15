package com.edugaon.realtimedatabase;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    TextInputLayout registerName, registerEmail, registerPassword, registerMobileNumber;
    CircleImageView profile_image;
    CardView selectImageViewRealtime;
    StorageReference storageReference;
    MaterialButton submitButtonRealtime, uploadImageButtonRealtime;
    ProgressDialog progressDialog;
    MaterialTextView getName, getEmail, getMobileNumber, getPassword;
    DatabaseReference reference;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseAuth.getInstance();

        registerName = findViewById(R.id.nameInputRealtime);
        getName = findViewById(R.id.getName);
        getEmail = findViewById(R.id.getEmail);
        getPassword = findViewById(R.id.getPassword);
        getMobileNumber = findViewById(R.id.getMobileNumber);
        registerEmail = findViewById(R.id.email_inputRealtime);
        registerPassword = findViewById(R.id.mobileNumber_inputReal);
        registerMobileNumber = findViewById(R.id.password_inputLayoutReal);
        profile_image = findViewById(R.id.profile_image);
        selectImageViewRealtime = findViewById(R.id.selectImageViewRealtime);
        submitButtonRealtime = findViewById(R.id.submitButtonRealtime);
        DAOEUserHelper dao = new DAOEUserHelper();
        uploadImageButtonRealtime = findViewById(R.id.uploadImageButtonRealtime);
        selectImageViewRealtime.setOnClickListener(view -> selectImageView());
        uploadImageButtonRealtime.setOnClickListener(view -> uploadImage());
        submitButtonRealtime.setOnClickListener((View view) -> {
//            String userName = getName.getText().toString();
//            if (userName.isEmpty())
//                readDate(userName);
//            else Toast.makeText(this, "hello world", Toast.LENGTH_SHORT).show();
            UserDetails userHelper = new UserDetails((Objects.requireNonNull(registerName.getEditText())).getText().toString(), (Objects.requireNonNull(registerPassword.getEditText())).getText().toString(), (Objects.requireNonNull(registerMobileNumber.getEditText())).getText().toString(), (Objects.requireNonNull(registerEmail.getEditText())).getText().toString());
            dao.add(userHelper).addOnSuccessListener(suc ->
                    Toast.makeText(this, "upload successfully", Toast.LENGTH_SHORT).show()).addOnFailureListener(er -> Toast.makeText(this, "something is missing" + er.getMessage(), Toast.LENGTH_SHORT).show());
        });
    }
    private  void uploadImage(){
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("uploading file...");
        progressDialog.show();
        SimpleDateFormat format = new SimpleDateFormat("yyyy_MM_dd_HH_ss", Locale.CANADA);
        Date now = new Date();
        String fileName = format.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/"+fileName);
        storageReference.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    profile_image.setImageURI(null);
                    Toast.makeText(MainActivity.this, "Successfully upload", Toast.LENGTH_SHORT).show();
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                }).addOnFailureListener(e -> {
                    if (progressDialog.isShowing())
                        progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Fail upload ", Toast.LENGTH_SHORT).show();
                });
    }
    private void readDate(String userName){
        reference = FirebaseDatabase.getInstance().getReference("UserDetails");
        reference.child(userName).get().addOnCompleteListener(task -> {
            if (task.isSuccessful()){
                if (task.getResult().exists()){
                    Toast.makeText(MainActivity.this, "Successfully", Toast.LENGTH_SHORT).show();
                    DataSnapshot dataSnapshot = task.getResult();
                    String email = String.valueOf(dataSnapshot.child("email").getValue());
                    String mobileNumber = String.valueOf(dataSnapshot.child("mobileNumber").getValue());
                    String name = String.valueOf(dataSnapshot.child("name").getValue());
                    String password = String.valueOf(dataSnapshot.child("password").getValue());
                    getEmail.setText(email);
                    getMobileNumber.setText(mobileNumber);
                    getName.setText(name);
                    getPassword.setText(password);
                }else{
                    Toast.makeText(MainActivity.this, "Filer", Toast.LENGTH_LONG).show();
                }
            }else {
                Toast.makeText(MainActivity.this, "",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void selectImageView(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            profile_image.setImageURI(imageUri);
        }
    }
}
//            HashMap<String, Object> hashMap = new HashMap<>();
//            hashMap.put("name", registerName.getEditText().getText().toString().trim());
//            hashMap.put("email", registerEmail.getEditText().getText().toString().trim());
//            hashMap.put("mobileNumber", registerMobileNumber.getEditText().getText().toString().trim());
//            hashMap.put("password", registerPassword.getEditText().getText().toString().trim());
//            UserDetails userHelper = new UserDetails(registerName.getEditText().getText().toString(), registerPassword.getEditText().getText().toString(), registerEmail.getEditText().getText().toString(), registerMobileNumber.getEditText().getText().toString());
//            dao.remove("-N-WKdN6T8kc64IyDk9V").addOnSuccessListener(suc -> {
//                Toast.makeText(this, "remove new users in realtime database", Toast.LENGTH_SHORT).show();
//            }).addOnFailureListener(er -> {
//                Toast.makeText(this, "something is missing" + er.getMessage(), Toast.LENGTH_SHORT).show();
//            });
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == pic_id) {
//            Bitmap photo = (Bitmap) data.getExtras().get("data");
//            profile_image.setImageBitmap(photo);
//        }
//    }
//        imageViewRealtime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                startActivityForResult(new Intent(MediaStore.ACTION_IMAGE_CAPTURE), pic_id);
//            }
//        });
