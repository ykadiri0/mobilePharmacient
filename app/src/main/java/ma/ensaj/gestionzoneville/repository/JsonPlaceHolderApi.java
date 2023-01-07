package ma.ensaj.gestionzoneville.repository;

import android.database.Observable;



import org.json.JSONObject;

import java.util.List;

import ma.ensaj.gestionzoneville.beans.Pharmacie;
import ma.ensaj.gestionzoneville.beans.PharmacieGard;
import ma.ensaj.gestionzoneville.beans.User;
import ma.ensaj.gestionzoneville.beans.Ville;
import ma.ensaj.gestionzoneville.beans.Zone;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Part;
import retrofit.http.Query;

public interface JsonPlaceHolderApi {
    @GET("login")
    Call<User> allPositions(@Query("email") String email, @Query("psw") String psw);
    @GET("getallPG1")
    Call<List<PharmacieGard>> allgard(@Query("id") int id);
    @GET("getuser")
    Call<User> getUser(@Query("id") int id);
    @GET("getallville")
    Call<List<Ville>> allV();
    @GET("getallzonebyvillen")
    Call<List<Zone>> allZV(@Query("name") String name);

    @POST("saveuser")
    Call<User> addPosition(@Body User user);
    @POST("savePG")
    Call<PharmacieGard> addPG(@Body PharmacieGard pharmacie);
    @POST("savephar")
    Call<Pharmacie> addPharmacie(@Body Pharmacie pharmacie);
    @PUT("updateuserph")
    Call<User> Updateuser(@Body User user);

    @Multipart
    @POST("/")
    Call<ResponseBody> postImage(@Part("url") MultipartBody.Part image, @Part("name") RequestBody name);


    @Multipart
    @POST("saveimage")
    Observable<ResponseBody> updateProfile(@Part("user_id") int id,
                                           @Part("full_name") RequestBody fullName,
                                           @Part("image") MultipartBody.Part image,
                                           @Part("other") String other);

}
