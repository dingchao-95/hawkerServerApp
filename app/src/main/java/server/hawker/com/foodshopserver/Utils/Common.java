package server.hawker.com.foodshopserver.Utils;

import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Retrofit.RetrofitClient;

public class Common {
    public static final String BASE_URL = "http://10.0.2.2/hawker/";
    public static IHawkerAPI getAPI()
    {
        return RetrofitClient.getClient(BASE_URL).create(IHawkerAPI.class);
    }
}
