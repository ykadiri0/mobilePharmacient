package ma.ensaj.gestionzoneville;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ma.ensaj.gestionzoneville.Service.UserService;
import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import ma.ensaj.gestionzoneville.ui.gallery.GalleryFragment;
import ma.ensaj.gestionzoneville.ui.home.HomeFragment;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Login extends AppCompatActivity {
    EditText email, psw;
    Button btn;
    UserService userService;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES = "session";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        userService = new UserService();
         sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        email = findViewById(R.id.email);
        psw = findViewById(R.id.psw);
        btn = findViewById(R.id.button2);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String em = email.getText().toString();
                String ps = psw.getText().toString();


                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://10.0.2.2:8081/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);


                Call<User> call = jsonPlaceHolderApi.allPositions(em, ps);
                //System.out.println("eeeeeeeeeeeeeee");
                // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

                call.enqueue(new Callback<User>() {


                    @Override
                    public void onResponse(Response<User> response, Retrofit retrofit) {
                        System.out.println(response.body());
                        if (response.body() != null) {
                            System.out.println("iddddddddddddddddd" + response.body().getId());
                            SharedPreferences.Editor editor = sharedpreferences.edit();

                            editor.putString("id_user", String.valueOf(response.body().getId()));
                            if(response.body().getPharmacie()!=null) {
                                editor.putString("id_phar", String.valueOf(response.body().getPharmacie().getId()));
                                editor.putString("p_name", String.valueOf(response.body().getPharmacie().getName()));
                                editor.putString("p_lat", String.valueOf(response.body().getPharmacie().getLat()));
                                editor.putString("p_lon", String.valueOf(response.body().getPharmacie().getLon()));
                                editor.putString("p_lon", String.valueOf(response.body().getPharmacie().getLon()));
                                editor.putString("etat", String.valueOf(response.body().getPharmacie().getEtat()));
                                editor.putString("photoph", String.valueOf(response.body().getPharmacie().getPhoto()));

                                if(response.body().getPharmacie().getZone()!=null) {
                                    editor.putString("p_zone", String.valueOf(response.body().getPharmacie().getZone().getName()));
                                }
                            }
                            editor.commit();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                        }}

                    @Override
                    public void onFailure(Throwable t) {
                        System.out.println(t.getMessage());

                    }
                });
            }
        });

    }


}

