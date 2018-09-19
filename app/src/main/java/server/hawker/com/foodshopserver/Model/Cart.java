package server.hawker.com.foodshopserver.Model;

public class Cart {
    private int id,amount,mealTakeaway;
    private String name,link;
    private double price;

    public Cart(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getMealTakeaway() {
        return mealTakeaway;
    }

    public void setMealTakeaway(int mealTakeaway) {
        this.mealTakeaway = mealTakeaway;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
