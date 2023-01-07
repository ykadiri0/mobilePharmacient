package ma.ensaj.gestionzoneville.Service;

import android.content.Context;
import android.widget.Toast;

import ma.ensaj.gestionzoneville.beans.Pharmacie;
import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class UserService {
    Integer x=0;


    Context context;

    public int Login(String em , String ps){


        System.out.println(x);
        return x;
    }

    public void setPharmacie(int idu,int idph){
        System.out.println("innnnn");
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        System.out.println("id pharmacie"+idph);
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
            User user=new User(idu,new Pharmacie(idph));
        System.out.println(user);
        Call<User> call = jsonPlaceHolderApi.Updateuser(user);
        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Response<User> response, Retrofit retrofit) {
                System.out.println("response in"+response.body());
                if(response.body()!=null){
                    System.out.println("updatepharrrr  " +response.body() );

                }
                else{
                }
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        System.out.println(x);

    }
}
