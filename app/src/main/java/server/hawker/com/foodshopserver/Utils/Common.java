package server.hawker.com.foodshopserver.Utils;

import java.util.ArrayList;
import java.util.List;

import server.hawker.com.foodshopserver.Model.Category;
import server.hawker.com.foodshopserver.Model.Food;
import server.hawker.com.foodshopserver.Model.Order;
import server.hawker.com.foodshopserver.Retrofit.FCMRetrofitClient;
import server.hawker.com.foodshopserver.Retrofit.IFCMServices;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Retrofit.RetrofitClient;

public class Common {
    //public static final String BASE_URL = "http://10.0.2.2/hawker/";
    public static final String BASE_URL = "http://192.168.1.22/hawker/";
    public static final String FCM_URL = "https://fcm.googleapis.com/";

    public static Category currentCategory;
    public static Food currentFood;
    public static Order currentOrder;

    public static List<Category> menuList = new ArrayList<>();

    public static IHawkerAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IHawkerAPI.class);
    }

    public static IFCMServices getFCMAPI()
    {
        return FCMRetrofitClient.getClient(FCM_URL).create(IFCMServices.class);
    }

    public static String convertCodeToStatus(int orderStatus) {
        switch (orderStatus)
        {
            case 0:
                return "Placed";
            case 1:
                return "Processing";
            case 2:
                return "Food is done";
            case -1:
                return "Cancelled";
            default:
                return "Error in orders.";

        }
    }
}
