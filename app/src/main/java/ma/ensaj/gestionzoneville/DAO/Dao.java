package ma.ensaj.gestionzoneville.DAO;

import android.os.AsyncTask;
import android.view.View;

import androidx.collection.ArraySet;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class Dao extends AsyncTask<List<Ville>, Void, Void> {
    public static List<Ville> list;
   /* public static List<Ville> getAllVille(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.50.5:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);

        Call<List<Ville>> call = jsonPlaceHolderApi.allPositions();
        //System.out.println("eeeeeeeeeeeeeee");
       // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();
        list=new ArrayList<>();
        call.enqueue(new Callback<List<Ville>>() {

            @Override
            public void onResponse(Response<List<Ville>> response, Retrofit retrofit) {
                System.out.println(response.body());
                for (Ville c : response.body()) {
                   list.add(c);
                }
               // System.out.println(response.body().toString());

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });

        return list;*/


    @Override
    protected Void doInBackground(List<Ville>... lists) {
        //list=getAllVille();
        return null;
    }
}
