package server.hawker.com.foodshopserver.Services;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Utils.Common;

public class MyFirebaseIdService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        updateTokenToServer(FirebaseInstanceId.getInstance().getToken());
    }

    private void updateTokenToServer(String token) {
        IHawkerAPI mService = Common.getAPI();
        mService.updateToken("Server_App_01",token,"1")
        .enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("DEBUG",response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("DEBUG",t.getMessage());
            }
        });
    }
}
