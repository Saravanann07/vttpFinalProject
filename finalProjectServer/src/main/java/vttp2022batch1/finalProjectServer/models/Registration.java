package vttp2022batch1.finalProjectServer.models;

import org.springframework.web.multipart.MultipartFile;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Registration {

    Integer userId;
    String email;
    String username;
    String password;
    MultipartFile profilePic;


    public Integer getUserId() {return this.userId;}
    public void setUserId(Integer userId) {this.userId = userId;}

    public String getEmail() {return this.email;}
    public void setEmail(String email) {this.email = email;}

    public String getUsername() {return this.username;}
    public void setUsername(String username) { this.username = username;}

    public String getPassword() {return this.password;}
    public void setPassword(String password) {this.password = password;}

    public MultipartFile getProfilePic() {return this.profilePic;}
    public void setProfilePic(MultipartFile profilePic) {this.profilePic = profilePic;}


    public static Registration create (JsonObject obj){

        Registration reg = new Registration();
        reg.setEmail(obj.getString("email"));
        reg.setUsername(obj.getString("username"));
        reg.setPassword(obj.getString("password"));
        
        return reg;
    }

    public static JsonObject toJson(Registration reg){
        return Json.createObjectBuilder()
            .add("email", reg.getEmail())
            .add("username", reg.getUsername())
            .add("password", reg.getPassword())
            .build();
    }


    
}
