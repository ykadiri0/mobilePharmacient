package ma.ensaj.gestionzoneville.ui.home;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import ma.ensaj.gestionzoneville.DAO.Dao;
import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.repository.JsonPlaceHolderApi;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class HomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;
    private final MutableLiveData<List<Ville>> listVille;

    public void init(){
        List<Ville> list=new ArrayList<>();
        Dao dao=new Dao();
        dao.execute(list);
    }

    public HomeViewModel() {
        //listVille= (MutableLiveData<List<Ville>>) Dao.getAllVille();

        listVille=new MutableLiveData<List<Ville>>();
        System.out.println();



        listVille.setValue(Dao.list);
        System.out.println("DAO Lidtttt  "+Dao.list);
        System.out.println(listVille.getValue());
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");

    }

    public LiveData<String> getText() {
        return mText;
    }
}