package server.hawker.com.foodshopserver.Retrofit;

import java.util.List;
import io.reactivex.Observable;

import retrofit2.http.GET;
import server.hawker.com.foodshopserver.Model.Category;

public interface IHawkerAPI {

    @GET("getmenu.php")
    Observable<List<Category>> getMenu();


}
