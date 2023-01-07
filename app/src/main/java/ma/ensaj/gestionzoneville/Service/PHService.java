package ma.ensaj.gestionzoneville.Service;

import ma.ensaj.gestionzoneville.beans.Pharmacie;
import ma.ensaj.gestionzoneville.beans.PharmacieGard;
import ma.ensaj.gestionzoneville.beans.Zone;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class PHService {
    public boolean savePH(PharmacieGard pharmacieGard){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //System.out.println(pharmacie);

        Call<PharmacieGard> call = jsonPlaceHolderApi.addPG(pharmacieGard);
        //System.out.println("eeeeeeeeeeeeeee");
        // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

        call.enqueue(new Callback<PharmacieGard>() {

            @Override
            public void onResponse(Response<PharmacieGard> response, Retrofit retrofit) {
                System.out.println(response.body());
                // System.out.println(response.body().toString());

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        return true;
    }

    public boolean getGard(int id ){

        return true;
    }
}
