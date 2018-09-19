package server.hawker.com.foodshopserver;

import android.media.MediaExtractor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.hawker.com.foodshopserver.Adapter.OrderDetailAdapter;
import server.hawker.com.foodshopserver.Model.DataMessage;
import server.hawker.com.foodshopserver.Model.MyResponse;
import server.hawker.com.foodshopserver.Model.Order;
import server.hawker.com.foodshopserver.Model.Token;
import server.hawker.com.foodshopserver.Retrofit.IFCMServices;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Utils.Common;

public class ViewOrderDetail extends AppCompatActivity {

    TextView txt_order_id,txt_order_price,txt_order_comment;
    Spinner spinner_order_status;
    RecyclerView recycler_order_detail;

    //Declare value for spinner
    String[] spinner_source = new String[]{
            "Cancelled",
            "Placed",
            "Processed",
            "Done"
    };

    IHawkerAPI mService;
    IFCMServices mFCMService;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mService = Common.getAPI();
        mFCMService = Common.getFCMAPI();

        txt_order_id = (TextView)findViewById(R.id.txt_order_id);
        txt_order_price = (TextView)findViewById(R.id.txt_order_price);
        txt_order_comment = (TextView)findViewById(R.id.txt_order_comment);

        spinner_order_status = (Spinner)findViewById(R.id.spinner_order_status);
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item,
                spinner_source);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner_order_status.setAdapter(spinnerArrayAdapter);

        recycler_order_detail = (RecyclerView)findViewById(R.id.recycler_order_detail);
        recycler_order_detail.setLayoutManager(new LinearLayoutManager(this));
        recycler_order_detail.setAdapter(new OrderDetailAdapter(this));

        //set data
        txt_order_id.setText(new StringBuilder("#")
        .append(Common.currentOrder.getOrderId()));
        txt_order_price.setText(new StringBuilder("$")
        .append(Common.currentOrder.getOrderPrice()));
        txt_order_comment.setText(Common.currentOrder.getOrderComment());

        setSpinnerSelectedBasedOnOrderStatus();
    }

    private void setSpinnerSelectedBasedOnOrderStatus() {
        switch(Common.currentOrder.getOrderStatus())
        {
            case -1:
                spinner_order_status.setSelection(0);//cancelled
                break;
            case 0:
                spinner_order_status.setSelection(1);//placed
                break;
            case 1:
                spinner_order_status.setSelection(2);//processed
                break;
            case 2:
                spinner_order_status.setSelection(3);//done
                break;

        }
    }

    //creat option menu


    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_order_detail,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_save_order_detail)
            saveUpdateOrder();
        return true;
    }

    private void saveUpdateOrder() {
        final int order_status = spinner_order_status.getSelectedItemPosition()-1;
        compositeDisposable.add(mService.updateOrderStatus(Common.currentOrder.getUserPhone(),
                Common.currentOrder.getOrderId(),
                order_status)
        .observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<String>() {
            @Override
            public void accept(String s) throws Exception {
                sendOrderUpdateNotification(Common.currentOrder, order_status);
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.d("ERROR",throwable.getMessage());
            }
        }));

    }

    private void sendOrderUpdateNotification(final Order currentOrder, final int order_status) {

        //get token
        mService.getToken(currentOrder.getUserPhone(),"0")
                .enqueue(new Callback<Token>() {
                    @Override
                    public void onResponse(Call<Token> call, Response<Token> response) {
                        Token userToken = response.body();

                        DataMessage dataMessage = new DataMessage();

                        dataMessage.to = userToken.getToken();
                        Map<String,String> dataSend = new HashMap<>();
                        dataSend.put("title","your order has been updated.");
                        dataSend.put("message","Order #"+currentOrder.getOrderId()+" has been updated to "+Common.convertCodeToStatus(order_status));

                        dataMessage.setData(dataSend);

                        mFCMService.sendNotification(dataMessage)
                                .enqueue(new Callback<MyResponse>() {
                                    @Override
                                    public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                                        if(response.body().success == 1)
                                        {
                                            Toast.makeText(ViewOrderDetail.this, "Order is updated", Toast.LENGTH_SHORT).show();
                                            finish();
                                        }
                                    }

                                    @Override
                                    public void onFailure(Call<MyResponse> call, Throwable t) {
                                        Toast.makeText(ViewOrderDetail.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });

                    }

                    @Override
                    public void onFailure(Call<Token> call, Throwable t) {
                        Toast.makeText(ViewOrderDetail.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
