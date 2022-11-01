package vttp2022batch1.finalProjectServer.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class User {

    Integer userId;
    String email;
    String username;
    String password;
    // String profileName;
    // String mediaType;
    // byte[] profilePic;


    public Integer getUserId() {return this.userId;}
    public void setUserId(Integer userId) {this.userId = userId;}


    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return this.username;}
    public void setUsername(String username) {this.username = username;}

    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}


    // public String getMediaType() {return this.mediaType;}
    // public void setMediaType(String mediaType) {this.mediaType = mediaType;}

    // public byte[] getProfilePic() {return this.profilePic;}
    // public void setProfilePic(byte[] profilePic) {this.profilePic = profilePic;}




    public static User create(JsonObject obj){

        User user = new User();
        user.setEmail(obj.getString("email"));
        user.setUsername(obj.getString("username"));
        user.setPassword(obj.getString("password"));

        return user;
    }

    public static JsonObject toJson(User user){
        return Json.createObjectBuilder()
            .add("email", user.getEmail())
            .add("username", user.getUsername())
            .add("password", user.getPassword())
            .build();
    }

    public static User createLogin(JsonObject obj){

        User user = new User();
        user.setUsername(obj.getString("username"));
        user.setPassword(obj.getString("password"));
        return user;
    }

    public static JsonObject toJsonLogin(User user){
        return Json.createObjectBuilder()
            .add("username", user.getUsername())
            .add("password", user.getPassword())
            .build();
    }
    
}
