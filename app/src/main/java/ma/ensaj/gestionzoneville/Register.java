package ma.ensaj.gestionzoneville;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.Observable;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.squareup.okhttp.ResponseBody;

import org.jetbrains.annotations.Nullable;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Register extends AppCompatActivity {
    EditText nom, prenom, email, psw;
    Button btn, btn2, imgPick;
    String path;
    ImageView image;
    Bitmap bitmap;
    String picturePath;


    private ImageView mProfile;
    private ImageView mImageAdd;
    private Button mBtnUpload;
    private TextView mText;
    private static final int PERMISSION_CODE = 1;
    private static final int PICK_IMAGE = 1;
    String filePath;
    Map config = new HashMap();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        nom = findViewById(R.id.nom);
        image = findViewById(R.id.imgAdd);
        prenom = findViewById(R.id.prenom);
        email = findViewById(R.id.email);
        psw = findViewById(R.id.psw);
        btn = findViewById(R.id.button);
        btn2 = findViewById(R.id.button3);
        mText= findViewById(R.id.txt);
        mBtnUpload = findViewById(R.id.btnUpload);
        mImageAdd = findViewById(R.id.imgAdd);
        mProfile = findViewById(R.id.imgAdd);
        // imgPick = findViewById(R.id.buttonimg);
        configCloudinary();
        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request permission to access external storage
                requestPermission();
            }
        });
        mBtnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadToCloudinary(filePath);
            }
        });




        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MediaManager.get().upload(filePath).callback(new UploadCallback() {
                    @Override
                    public void onStart(String requestId) {
                        mText.setText("start");
                    }
                    @Override
                    public void onProgress(String requestId, long bytes, long totalBytes) {
                        mText.setText("Uploading… ");
                    }
                    @Override
                    public void onSuccess(String requestId, Map resultData) {
                        mText.setText("image URL: "+resultData.get("url").toString());
                        System.out.println(resultData.get("url").toString());

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://10.0.2.2:8081/")
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();
                        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


                        String name = nom.getText().toString();
                        String pre = prenom.getText().toString();
                        String em = email.getText().toString();
                        String ps = psw.getText().toString();


                        User user = new User(name, pre, em, ps,resultData.get("url").toString());

                        Call<User> call = jsonPlaceHolderApi.addPosition(user);
                        //System.out.println("eeeeeeeeeeeeeee");
                        // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

                        call.enqueue(new Callback<User>() {

                            @Override
                            public void onResponse(Response<User> response, Retrofit retrofit) {
                                System.out.println(response.body());


                                // System.out.println(response.body().toString());

                            }

                            @Override
                            public void onFailure(Throwable t) {
                                System.out.println(t.getMessage());

                            }
                        });




                    }
                    @Override
                    public void onError(String requestId, ErrorInfo error) {
                        mText.setText("error "+ error.getDescription());
                    }
                    @Override
                    public void onReschedule(String requestId, ErrorInfo error) {
                        mText.setText("Reshedule "+error.getDescription());
                    }
                }).dispatch();


            }


        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), Login.class);
                startActivity(i);
            }
        });


    }


    private void configCloudinary() {
        config.put("cloud_name", "dq4d496km");
        config.put("api_key", "922469794564169");
        config.put("api_secret", "82nDdX1kidArETzUVRNXnSjJLdw");
        MediaManager.init(Register.this, config);
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission
                (Register.this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    Register.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    PERMISSION_CODE
            );
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                accessTheGallery();
            } else {
                Toast.makeText(Register.this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void accessTheGallery() {
        Intent i = new Intent(
                Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        );
        i.setType("image/*");
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get the image’s file location
        filePath = getRealPathFromUri(data.getData(), Register.this);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getContentResolver(),
                        data.getData());
                mProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Uri imageUri, Register activity) {
        Cursor cursor = activity.getContentResolver().query(imageUri, null, null, null, null);
        if (cursor == null) {
            return imageUri.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }

    private void uploadToCloudinary(String filePath) {


    }
}