package ma.ensaj.gestionzoneville.ui.gard;

import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import ma.ensaj.gestionzoneville.R;
import ma.ensaj.gestionzoneville.beans.PharmacieGard;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class GardFragment extends Fragment {

    private GardViewModel mViewModel;
    RecyclerView recyclerView;
    SharedPreferences sharedpreferences;
    public static final String MyPREFERENCES="session";

    public static GardFragment newInstance() {
        return new GardFragment();
    }

    @Override
    public View onCreateView(@NotNull  LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.gard_fragment, container, false);
        recyclerView=view.findViewById(R.id.list);
        sharedpreferences = this.getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8081/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        JsonPlaceHolderApi jsonPlaceHolderApi = retrofit.create(JsonPlaceHolderApi.class);
        //System.out.println(pharmacie);

        Call <List<PharmacieGard>> call = jsonPlaceHolderApi.allgard(Integer.parseInt(sharedpreferences.getString("id_phar",null)));
        //System.out.println("eeeeeeeeeeeeeee");
        // MutableLiveData<List<Ville>> list1 = new MutableLiveData<>();

        call.enqueue(new Callback<List<PharmacieGard>>() {

            @Override
            public void onResponse(Response <List<PharmacieGard>> response, Retrofit retrofit) {
                System.out.println("response  "+response.body());



                MyAdapter adapter = new MyAdapter(response.body(),getActivity());

                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onFailure(Throwable t) {
                System.out.println(t.getMessage());

            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(GardViewModel.class);
        // TODO: Use the ViewModel
    }

}