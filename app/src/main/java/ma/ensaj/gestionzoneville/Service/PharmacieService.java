package ma.ensaj.gestionzoneville.Service;

import ma.ensaj.gestionzoneville.beans.Pharmacie;
import ma.ensaj.gestionzoneville.beans.Zone;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PharmacieService {
    private int x;

    public int savePH(String n, Double l, Double o,  String z,String s,String photo){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        Pharmacie pharmacie=new Pharmacie(n,l,o,new Zone(Integer.parseInt(z)),photo);
        //System.out.println(pharmacie);

        Call<Pharmacie> call = jsonPlaceHolderApi.addPharmacie(pharmacie);
        //System.out.println("eeeeeeeeeeeeeee");
        // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

        call.enqueue(new Callback<Pharmacie>() {

            @Override
            public void onResponse(Response<Pharmacie> response, Retrofit retrofit) {
                System.out.println(response.body());
                x=response.body().getId();
                UserService userService=new UserService();
                System.out.println("before");
                userService.setPharmacie(Integer.parseInt(s),response.body().getId());
                // System.out.println(response.body().toString());
            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        return x;
    }
}
