package ma.ensaj.gestionzoneville.ui.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import ma.ensaj.gestionzoneville.R;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.cloudinary.android.MediaManager;
import com.cloudinary.android.callback.ErrorInfo;
import com.cloudinary.android.callback.UploadCallback;
import com.google.android.material.textfield.TextInputLayout;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ma.ensaj.gestionzoneville.Register;
import ma.ensaj.gestionzoneville.Service.PharmacieService;
import ma.ensaj.gestionzoneville.Service.UserService;
import ma.ensaj.gestionzoneville.Service.VilleService;
import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.beans.Zone;
import ma.ensaj.gestionzoneville.databinding.FragmentGalleryBinding;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class GalleryFragment extends Fragment {


    private ImageView mProfile;
    private ImageView mImageAdd;
    private Button mBtnUpload;
    private TextView mText;
    private static final int PERMISSION_CODE = 1;
    private static final int PICK_IMAGE = 1;
    String filePath;
    Map config = new HashMap();








    PharmacieService pharmacieService;
    VilleService villeService;
    UserService userService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES="session";
    private FragmentGalleryBinding binding;
    AutoCompleteTextView editTextFilledExposedDropdown,editTextFilledExposedDropdown2;
    public View onCreateView(@NotNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        mText= binding.txt;
        //mBtnUpload = binding.btnUpload;
        mImageAdd = binding.imgAdd;
        mProfile = binding.imgAdd;


        userService=new UserService();
        EditText name = binding.name;
        EditText  lat= binding.lat;
        EditText lon = binding.lon;
        TextInputLayout ic1=binding.ic1;
        TextInputLayout ic2=binding.ic2;
        TextInputLayout ic3=binding.ic3;
        TextInputLayout ic4=binding.ic4;
        TextInputLayout ic5=binding.ic5;

        editTextFilledExposedDropdown =
                root.findViewById(R.id.filled_exposed_dropdown);
        editTextFilledExposedDropdown2 =
                root.findViewById(R.id.filled_exposed_dropdown2);

        // EditText zone = binding.zone;
        Button btn =binding.button4;
        final TextView textView = binding.textGallery;
        galleryViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        pharmacieService=new PharmacieService();
        System.out.println("idddd :"+sharedpreferences.getString("id_phar",null));
        if (sharedpreferences.getString("id_phar",null)!=null) {
            mProfile.setVisibility(View.GONE);
            mImageAdd.setVisibility(View.GONE);
            mProfile.setVisibility(View.GONE);
            mImageAdd.setVisibility(View.GONE);
//            mBtnUpload.setVisibility(View.GONE);
             mText.setVisibility(View.GONE);
            name.setVisibility(View.GONE);
            lat.setVisibility(View.GONE);
            lon.setVisibility(View.GONE);
            editTextFilledExposedDropdown.setVisibility(View.GONE);
            editTextFilledExposedDropdown2.setVisibility(View.GONE);
            btn.setVisibility(View.GONE);
            ic5.setVisibility(View.GONE);
            ic4.setVisibility(View.GONE);
            ic3.setVisibility(View.GONE);
            ic2.setVisibility(View.GONE);
            ic1.setVisibility(View.GONE);


        }



        //configCloudinary();
        mImageAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //request permission to access external storage
                requestPermission();
            }
        });


        Map<String,Integer> zoneid =new HashMap<String, Integer>();
        villeService=new VilleService();



        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<List<Ville>> call = jsonPlaceHolderApi.allV();
        call.enqueue(new Callback<List<Ville>>() {
            @Override
            public void onResponse(Response<List<Ville>> response, Retrofit retrofit) {
                System.out.println("response in"+response.body());
                ArrayList<String> type =new ArrayList<String>() ;
                for(int i=0; i<response.body().size() ;i++) {

                    type.add(response.body().get(i).getName());
                }

                ArrayAdapter<String> adapter =
                        new ArrayAdapter<String>(
                                getActivity(),
                                R.layout.dropdown_menu_popup_item,
                                type);


                editTextFilledExposedDropdown.setAdapter(adapter);

            }


            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        editTextFilledExposedDropdown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println(editTextFilledExposedDropdown.getText().toString());
                Call<List<Zone>> call = jsonPlaceHolderApi.allZV(editTextFilledExposedDropdown.getText().toString());
                call.enqueue(new Callback<List<Zone>>() {
                    @Override
                    public void onResponse(Response<List<Zone>> response, Retrofit retrofit) {
                        System.out.println("response in"+response.body());
                        ArrayList<String> type2 =new ArrayList<String>() ;
                        for(int i=0; i<response.body().size() ;i++) {
                            zoneid.put(response.body().get(i).getName(),response.body().get(i).getId());
                            type2.add(response.body().get(i).getName());
                        }

                        ArrayAdapter<String> adapter2 =
                                new ArrayAdapter<String>(
                                        getActivity(),
                                        R.layout.dropdown_menu_popup_item,
                                        type2);


                        editTextFilledExposedDropdown2.setAdapter(adapter2);
                        Set set=zoneid.entrySet();//Converting to Set so that we can traverse
                        Iterator itr=set.iterator();
                        while(itr.hasNext()){
                            //Converting to Map.Entry so that we can get key and value separately
                            Map.Entry entry=(Map.Entry)itr.next();
                            System.out.println(entry.getKey()+" "+entry.getValue());
                        }

                    }


                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });
            }
        });



        villeService.getVilles();


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

                        String id2 = sharedpreferences.getString("id_user",null);
                        String n=name.getText().toString();
                        Double l=Double.parseDouble(lat.getText().toString());
                        Double o=Double.parseDouble(lon.getText().toString());
                        Set set=zoneid.entrySet();//Converting to Set so that we can traverse
                        int zone=0;
                        Iterator itr=set.iterator();
                        while(itr.hasNext()){
                            Map.Entry entry=(Map.Entry)itr.next();
                            //Converting to Map.Entry so that we can get key and value separately
                            if(editTextFilledExposedDropdown2.getText().toString().equals(entry.getKey().toString())){
                                System.out.println("id issssssssssssss" +entry.getValue());
                                zone=Integer.parseInt(entry.getValue().toString());
                                break;
                            }

                            System.out.println(entry.getKey()+" "+entry.getValue());
                        }
                        // String z=zone.getText().toString();
                        int p= pharmacieService.savePH(n,l,o,String.valueOf(zone),id2,resultData.get("url").toString());
                        SharedPreferences.Editor editor = sharedpreferences.edit();

                        editor.putString("id_phar", p+"");
                        editor.commit();


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




        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }















    private void configCloudinary() {
        config.put("cloud_name", "dq4d496km");
        config.put("api_key", "922469794564169");
        config.put("api_secret", "82nDdX1kidArETzUVRNXnSjJLdw");
        MediaManager.init(getContext(), config);
    }


    private void requestPermission() {
        if (ContextCompat.checkSelfPermission
                (getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
        ) {
            accessTheGallery();
        } else {
            ActivityCompat.requestPermissions(
                    getActivity(),
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
                Toast.makeText(getActivity(), "permission denied", Toast.LENGTH_SHORT).show();
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
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get the image’s file location
        filePath = getRealPathFromUri(data.getData(),getActivity());
        if (requestCode == PICK_IMAGE ) {
            try {
                //set picked image to the mProfile
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(
                        getActivity().getContentResolver(),
                        data.getData());
                mProfile.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String getRealPathFromUri(Uri imageUri, Activity activity) {
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