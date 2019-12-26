package com.om.taskmanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.loader.content.CursorLoader;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.om.taskmanager.model.ImageResponse;
import com.om.taskmanager.model.Users;
import com.om.taskmanager.url.Url;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {
    private EditText etfirstname,etsecname,usernm,pass,conpass;
    private Button butsign;
    TextView imagename;
    Uri apuri;
    String imagePath;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        etfirstname=findViewById(R.id.firstname);
        etsecname=findViewById(R.id.lastname);
        usernm=findViewById(R.id.username);
        pass=findViewById(R.id.etpass);
        conpass=findViewById(R.id.confirmpass);
        butsign=findViewById(R.id.btnsignup);
        imageView=findViewById(R.id.imageView);
        imagename=findViewById(R.id.imagename);

        final Url url=new Url();
        checkPermission();
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPictureDialog();

            }
        });

        butsign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstname=etfirstname.getText().toString().trim();
                String lastname=etsecname.getText().toString().trim();
                String name=usernm.getText().toString().trim();
                String password=pass.getText().toString().trim();
                String confirmpassword=conpass.getText().toString().trim();
                String image=imagename.getText().toString();

                if(TextUtils.isEmpty(firstname)){
                    etfirstname.setError("Firstname is Required");
                    etfirstname.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(lastname)){
                    etsecname.setError("Lastname is Required");
                    etsecname.requestFocus();
                    return;
                }

                if(TextUtils.isEmpty(name)){
                    usernm.setError("Username is Required");
                    usernm.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    pass.setError("Password is Required");
                    pass.requestFocus();
                    return;
                }
                if(TextUtils.isEmpty(confirmpassword)){
                    conpass.setError("Confirm-Password is Required");
                    conpass.requestFocus();
                    return;
                }
                if(password.length() < 6){
                    pass.setError("Password must be greater than 6");
                    pass.requestFocus();
                    return;
                }
                if(!confirmpassword.equals(password)){
                    conpass.setError("Password does not match!");
                    conpass.requestFocus();
                    return;
                }

                if(password.equals(confirmpassword)) {
                    Users user = new Users(firstname,lastname,name, password,image);

                    //adding URL
//                    Url url = new Url();
                    //UserAPI heroesAPI= Posturl.getInstance().create(UserAPI.class);

                    final Call<Void> voidCall = url.getInstance().register(user);

                    voidCall.enqueue(new Callback<Void>() {
                        @Override
                        public void onResponse(Call<Void> call, Response<Void> response) {
                            if (!response.isSuccessful()) {
                                Toast.makeText(SignupActivity.this, "Code Error!" + response.code(), Toast.LENGTH_SHORT).show();
                                Log.d("Error", "Error" + response.code());
                                return;
                            }
                            Toast.makeText(SignupActivity.this, "You are Registerd!", Toast.LENGTH_SHORT).show();
                            clear();
//                            Intent intent = new Intent(SignupActivity.this, MainActivity.class);
//                            startActivity(intent);
                        }

                        @Override
                        public void onFailure(Call<Void> call, Throwable t) {
                            Toast.makeText(SignupActivity.this, "Error !" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                            Log.d("Error", "Error   " + t.getLocalizedMessage());
                        }
                    });
                }else{
                    Toast.makeText(SignupActivity.this, "Incorrect Password!", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });

    }
    private void showPictureDialog() {
        AlertDialog.Builder pictureDialog = new AlertDialog.Builder(this);
        pictureDialog.setTitle("Choose Photo ");
        String[] pictureDialogItems = {
                "Select Gallery",
                "Capture Camera",
                "Cancel"};
        pictureDialog.setItems(pictureDialogItems,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                choosePhotoFromGallary();
                                break;
                            case 1:
                                takePhotoFromCamera();
                                break;
                            case 2:
                                cancel();
                                break;
                        }
                    }
                });
        pictureDialog.show();
    }

    private void cancel() { }

    private void takePhotoFromCamera() {
        Intent intent=new Intent((MediaStore.ACTION_IMAGE_CAPTURE));
        if(intent.resolveActivity(SignupActivity.this.getPackageManager()) !=null){
            Toast.makeText(SignupActivity.this, "Opening Camera", Toast.LENGTH_SHORT).show();
            startActivityForResult(intent,0);
        }
    }
    private void choosePhotoFromGallary() {
        Intent intent=new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        Toast.makeText(SignupActivity.this, "Opening Gallery", Toast.LENGTH_SHORT).show();
        startActivityForResult(intent,1);
    }


    private void checkPermission() {
        if(ContextCompat.checkSelfPermission(SignupActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(SignupActivity.this,new String[]
                    {Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE},0);

        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESULT_OK) {
            if (data == null) {
                Toast.makeText(this, "Please Select Image!", Toast.LENGTH_SHORT).show();
            }
        }

        if (requestCode == 1) {
            Uri uri = data.getData();
            imageView.setImageURI(uri);
            apuri = data.getData();

        } else if (requestCode == 0) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
            apuri = getImageUri();
        }

        imagePath=ServerImage(apuri);
        File file=new File(imagePath);
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("imageFile", file.getName(), requestBody);
        Url url=new Url();
        Call<ImageResponse> call = url.getInstance().uploadImage(body);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                if (!response.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "error" + response.code(), Toast.LENGTH_SHORT).show();
                    Log.d("Error", "Error" + response.code());
                    return;
                }
                ImageResponse imageResponse = response.body();
                Toast.makeText(SignupActivity.this, "upload", Toast.LENGTH_SHORT).show();
                imagename.setVisibility(View.VISIBLE);
                imagename.setText(imageResponse.getFilename());
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                Toast.makeText(SignupActivity.this, "error" + t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                Log.d("Error", "Error   " + t.getLocalizedMessage());

            }
        });



    }

    private String ServerImage(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), uri, projection, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_ind = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_ind);
        cursor.close();
        return result;
    }

    private Uri getImageUri() {
        Uri imgUri = null;
        File m_file;
        try {
            SimpleDateFormat m_sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
            String m_curentDateandTime = m_sdf.format(new Date());
            String m_imagePath = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + m_curentDateandTime + ".jpg";
            m_file = new File(m_imagePath);
            imgUri = Uri.fromFile(m_file);
        } catch (Exception p_e) {
        }
        return imgUri;

    }

    private void clear() {
        usernm.setText("");
        pass.setText("");
        etfirstname.setText("");
        etsecname.setText("");
        conpass.setText("");
        imagename.setText("");
        imagename.setVisibility(View.INVISIBLE);
        imageView.setImageDrawable(null);
        Intent intent = new Intent(SignupActivity.this,MainActivity.class);
        startActivity(intent);
    }
}

