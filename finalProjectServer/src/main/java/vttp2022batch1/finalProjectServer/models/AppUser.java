package vttp2022batch1.finalProjectServer.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class AppUser {

    Integer userId;
    String email;
    String username;
    String password;


    public Integer getUserId() {return this.userId;}
    public void setUserId(Integer userId) {this.userId = userId;}


    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return this.username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}







    public static AppUser create(JsonObject obj){

        AppUser user = new AppUser();
        user.setEmail(obj.getString("email"));
        user.setUsername(obj.getString("username"));
        user.setPassword(obj.getString("password"));

        return user;
    }

    public static JsonObject toJson(AppUser user){
        return Json.createObjectBuilder()
            .add("email", user.getEmail())
            .add("username", user.getUsername())
            .add("password", user.getPassword())
            .build();
    }
    
}
