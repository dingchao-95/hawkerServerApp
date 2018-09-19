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
import server.hawker.com.foodshopserver.Model.Order;
import server.hawker.com.foodshopserver.Model.Token;

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


    @FormUrlEncoded
    @POST("server/product/update_product.php")
    Observable<String> updateProduct(@Field("id") String id,
                                      @Field("name") String name,
                                      @Field("imgPath") String imgPath,
                                      @Field("price") String price,
                                      @Field("menuId") String menuId);

    @FormUrlEncoded
    @POST("server/product/delete_product.php")
    Observable<String> deleteProduct(@Field("id") String id);

    //Order Management
    @FormUrlEncoded
    @POST("server/order/getorders.php")
    Observable<List<Order>> getAllOrders(@Field("status") String status);

    //Token Management
    @FormUrlEncoded
    @POST("updatetoken.php")
    Call<String> updateToken(@Field("phone") String phone,
                             @Field("token") String token,
                             @Field("isServerToken") String isServerToken);

    @FormUrlEncoded
    @POST("server/order/update_order_status.php")
    Observable<String> updateOrderStatus(@Field("phone") String phone,
                                         @Field("order_id") long orderId,
                                         @Field("status") int status);

    @FormUrlEncoded
    @POST("gettoken.php")
    Call<Token> getToken(@Field("phone") String phone,
                         @Field("isServerToken") String isServerToken);



}
