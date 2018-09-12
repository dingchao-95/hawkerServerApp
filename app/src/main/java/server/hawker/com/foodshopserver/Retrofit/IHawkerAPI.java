package server.hawker.com.foodshopserver.Retrofit;

import java.util.List;
import io.reactivex.Observable;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import server.hawker.com.foodshopserver.Model.Category;
import server.hawker.com.foodshopserver.Model.Food;

public interface IHawkerAPI {

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();

    @FormUrlEncoded
    @POST("server/category/add_category.php")
    Observable<String> addCategory(@Field("name") String name,
                                   @Field("imgPath") String imgPath);

    @Multipart
    @POST("server/category/upload_category_img.php")
    Call<String> uploadCategoryFile(@Part MultipartBody.Part file);

    @FormUrlEncoded
    @POST("server/category/update_category.php")
    Observable<String> updateCategory(@Field("id") String id,
                                   @Field("name") String name,
                                   @Field("imgPath") String imgPath);

    @FormUrlEncoded
    @POST("server/category/delete_category.php")
    Observable<String> deleteCategory(@Field("id") String id);

    /*
    Food list management
     */

    @FormUrlEncoded
    @POST("getfood.php")
    Observable<List<Food>>getFood(@Field("menuid")String menuID);

    @FormUrlEncoded
    @POST("server/product/add_food.php")
    Observable<String> addNewFood(@Field("name") String name,
                                   @Field("imgPath") String imgPath,
                                   @Field("price") String price,
                                   @Field("menuId") String menuId);


    @Multipart
    @POST("server/product/upload_food_img.php")
    Call<String> uploadFoodFile(@Part MultipartBody.Part file);

}
