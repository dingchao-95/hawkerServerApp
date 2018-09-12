package server.hawker.com.foodshopserver;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.ipaulpro.afilechooser.utils.FileUtils;

import java.io.File;
import java.util.List;
import java.util.UUID;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import server.hawker.com.foodshopserver.Adapter.FoodListAdapter;
import server.hawker.com.foodshopserver.Model.Food;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Utils.Common;
import server.hawker.com.foodshopserver.Utils.ProgressRequestBody;
import server.hawker.com.foodshopserver.Utils.UploadCallBack;

public class FoodList extends AppCompatActivity implements UploadCallBack {

    private static final int PICK_FILE_REQUEST = 1111 ;
    IHawkerAPI mService;
    RecyclerView recycler_food;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    FloatingActionButton btn_add;

    ImageView img_browser;
    EditText edt_food_name,edt_food_price;

    Uri selected_uri=null;
    String uploaded_img_path="";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK)
        {
            if(requestCode == PICK_FILE_REQUEST)
            {
                if(data != null)
                {
                    selected_uri = data.getData();
                    Log.d("selectedUri",selected_uri.getPath());
                    if(selected_uri != null && !selected_uri.getPath().isEmpty())
                    {
                        img_browser.setImageURI(selected_uri);
                        uploadFileToServer();
                    }
                    else
                    {
                        Toast.makeText(this, "Cannot upload file to server", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        mService = Common.getAPI();

        btn_add = (FloatingActionButton)findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddFoodDialog();
            }
        });


        recycler_food = (RecyclerView)findViewById(R.id.recycler_food);
        recycler_food.setLayoutManager(new GridLayoutManager(this,2));
        recycler_food.setHasFixedSize(true);

        loadListFood(Common.currentCategory.getID());
    }

    private void showAddFoodDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new food/drink");

        View view = LayoutInflater.from(this).inflate(R.layout.add_new_product, null);

        edt_food_name = (EditText)view.findViewById(R.id.edt_food_name);
        edt_food_price = (EditText)view.findViewById(R.id.edt_food_price);
        img_browser = (ImageView)view.findViewById(R.id.img_browser);

        //event
        img_browser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(Intent.createChooser(FileUtils.createGetContentIntent(),"Select a file"),
                        PICK_FILE_REQUEST);
            }
        });

        //set view
        builder.setView(view);
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                uploaded_img_path="";
                selected_uri=null;
            }

        }).setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(edt_food_name.getText().toString().isEmpty())
                {
                    Toast.makeText(FoodList.this, "Please enter name of product", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(edt_food_price.getText().toString().isEmpty())
                {
                    Toast.makeText(FoodList.this, "Please enter price of product", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(uploaded_img_path.isEmpty())
                {
                    Toast.makeText(FoodList.this, "Please select image of a product you want to add", Toast.LENGTH_SHORT).show();
                    return;
                }

                compositeDisposable.add(mService.addNewFood(edt_food_name.getText().toString(),
                        uploaded_img_path,
                        edt_food_price.getText().toString(),
                        Common.currentCategory.ID)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                Toast.makeText(FoodList.this, s, Toast.LENGTH_SHORT).show();
                                loadListFood(Common.currentCategory.getID());
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                Toast.makeText(FoodList.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }));

            }
        }).show();
    }

    private void uploadFileToServer() {
        if(selected_uri != null)
        {
            File file = FileUtils.getFile(this, selected_uri);


            String fileName = new StringBuilder(UUID.randomUUID().toString())
                    .append(FileUtils.getExtension(file.toString())).toString();

            ProgressRequestBody requestFile = new ProgressRequestBody(file,this);

            final MultipartBody.Part body = MultipartBody.Part.createFormData("uploaded_file",fileName,requestFile);

            new Thread(new Runnable() {
                @Override
                public void run() {
                    mService.uploadFoodFile(body)
                            .enqueue(new Callback<String>() {
                                @Override
                                public void onResponse(Call<String> call, Response<String> response) {
                                    //after uploaded , we get file name and return string of link
                                    uploaded_img_path = new StringBuilder(Common.BASE_URL)
                                            .append("server/product/product_img/")
                                            .append(response.body().toString())
                                            .toString();
                                    Log.d("IMGPath", uploaded_img_path);
                                }

                                @Override
                                public void onFailure(Call<String> call, Throwable t) {
                                    Toast.makeText(FoodList.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }).start();
        }
    }



    private void loadListFood(String id) {
        compositeDisposable.add(mService.getFood(id).observeOn(AndroidSchedulers.mainThread())
        .subscribeOn(Schedulers.io())
        .subscribe(new Consumer<List<Food>>() {
            @Override
            public void accept(List<Food> foods) throws Exception {
                displayFoodList(foods);
            }
        }));
    }

    private void displayFoodList(List<Food> foods) {
        FoodListAdapter foodListAdapter = new FoodListAdapter(this,foods);
        recycler_food.setAdapter(foodListAdapter);
    }

    @Override
    protected void onResume() {
        loadListFood(Common.currentCategory.getID());
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }

    @Override
    protected void onStop() {
        compositeDisposable.clear();
        super.onStop();
    }

    @Override
    public void onProgressUpdate(int percentage) {

    }
}
