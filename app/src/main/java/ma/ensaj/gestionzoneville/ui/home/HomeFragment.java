package ma.ensaj.gestionzoneville.ui.home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import ma.ensaj.gestionzoneville.DAO.Dao;
import ma.ensaj.gestionzoneville.MainActivity;
import ma.ensaj.gestionzoneville.Service.PharmacieService;
import ma.ensaj.gestionzoneville.Service.UserService;
import ma.ensaj.gestionzoneville.beans.Pharmacie;
import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.beans.Zone;
import ma.ensaj.gestionzoneville.databinding.FragmentHomeBinding;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    PharmacieService pharmacieService;
    UserService userService;
    AlertDialog.Builder builder;

    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES="session";
    public View onCreateView(@NotNull  LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        builder = new AlertDialog.Builder(getContext());
        TextView textView=binding.textView2;
        EditText name=binding.name;
        EditText lan=binding.lat;
        EditText lon=binding.lon;
        EditText zone=binding.zone;
        Button edit=binding.edit;
        Button save=binding.button6;
        ImageView mImageAdd = binding.imgAdd;





        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


        Call<User> call = jsonPlaceHolderApi.getUser(Integer.parseInt(sharedpreferences.getString("id_user",null)));
        //System.out.println("eeeeeeeeeeeeeee");
        // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

        call.enqueue(new Callback<User>() {


            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                System.out.println(response.body());
                String id2 = "";
                String namep="";
                String p_lat="";
                String p_long="";
                String p_zone="";
                String photoph="";
                if (response.body() != null) {
                    if (response.body().getPharmacie() != null) {
                    System.out.println("iddddddddddddddddd" + response.body().getId());
                     id2 = response.body().getPharmacie().getId().toString();
                     namep = response.body().getPharmacie().getName().toString();
                     p_lat = response.body().getPharmacie().getLat().toString();;
                     p_long = response.body().getPharmacie().getLon().toString();;
                     p_zone = response.body().getPharmacie().getZone().getName().toString();
                     photoph = response.body().getPharmacie().getPhoto().toString();}
                    int etat = 0;
                    if(response.body().getPharmacie()!=null){
                        etat = response.body().getPharmacie().getEtat();

                    }

                    save.setVisibility(View.GONE);
                    if(id2!=""){
                        if(etat==0){
                            textView.setText("L'etat de votre  pharmacie est en attente");
                        }else if(etat==1){
                            textView.setText("L'etat de votre  pharmacie est valide ");
                        }else{
                            textView.setText("L'etat de votre pharmacie est refuse");
                        }

                        name.setText(namep);
                        lan.setText(p_lat);
                        lon.setText(p_long);
                        zone.setText(p_zone);
           /* Glide.with(getActivity())
                    .load(photoph)
                    .into(mImageAdd);*/
                        System.out.println("P"+photoph.split("://")[0]+"P"+photoph.split("://")[1]);
                        Glide.with(getActivity()).load(photoph.split("://")[0]+"s://"+photoph.split("://")[1]).into(mImageAdd);



                    }else {
                        textView.setText("vous n avez pas de pharmacie voue devez l ajouter");
                        name.setVisibility(View.GONE);
                        lan.setVisibility(View.GONE);
                        lon.setVisibility(View.GONE);
                        zone.setVisibility(View.GONE);
                        edit.setVisibility(View.GONE);


                    }

                }}

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });







        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name.setEnabled(true);
                lan.setEnabled(true);
                lon.setEnabled(true);
                zone.setEnabled(true);
                edit.setText("Cancel");
                save.setVisibility(View.VISIBLE);


            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                builder.setMessage("R.string.dialog_message") .setTitle("R.string.dialog_title");

                //Setting message manually and performing action on button click
                builder.setMessage("Do you want to save ?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Toast.makeText(getContext(),"saved sucessfully",Toast.LENGTH_SHORT);
                                name.setEnabled(false);
                                lan.setEnabled(false);
                                lon.setEnabled(false);
                                zone.setEnabled(false);
                                edit.setText("edit");
                                save.setVisibility(View.GONE);
                                Toast.makeText(getContext(),"you choose yes action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getContext(),"you choose no action for alertbox",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("AlertDialogExample");
                alert.show();
            }
        });

        //homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
       // homeViewModel.getText().observe(getViewLifecycleOwner(),);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}