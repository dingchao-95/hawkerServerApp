package server.hawker.com.foodshopserver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import server.hawker.com.foodshopserver.Adapter.FoodListAdapter;
import server.hawker.com.foodshopserver.Model.Food;
import server.hawker.com.foodshopserver.Retrofit.IHawkerAPI;
import server.hawker.com.foodshopserver.Utils.Common;

public class FoodList extends AppCompatActivity {

    IHawkerAPI mService;
    RecyclerView recycler_food;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        mService = Common.getAPI();

        recycler_food = (RecyclerView)findViewById(R.id.recycler_food);
        recycler_food.setLayoutManager(new GridLayoutManager(this,2));
        recycler_food.setHasFixedSize(true);

        loadListFood(Common.currentCategory.getID());
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
}
