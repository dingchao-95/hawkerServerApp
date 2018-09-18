package server.hawker.com.foodshopserver.Utils;

import java.util.ArrayList;
import java.util.List;

import server.hawker.com.foodshopserver.Model.Category;
import server.hawker.com.foodshopserver.Model.Food;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Retrofit.RetrofitClient;

public class Common {
    //public static final String BASE_URL = "http://10.0.2.2/hawker/";
    public static final String BASE_URL = "http://192.168.1.22/hawker/";

    public static Category currentCategory;
    public static Food currentFood;

    public static List<Category> menuList = new ArrayList<>();

    public static IHawkerAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IHawkerAPI.class);
    }
}
