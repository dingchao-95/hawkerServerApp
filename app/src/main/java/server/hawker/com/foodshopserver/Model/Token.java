package server.hawker.com.foodshopserver.Model;

public class Token {
    public String phone;
    public String token;
    public int isServerToken;

    public Token() {
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        phone = phone;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        token = token;
    }

    public int getIsServerToken() {
        return isServerToken;
    }

    public void setIsServerToken(int isServerToken) {
        this.isServerToken = isServerToken;
    }
}
