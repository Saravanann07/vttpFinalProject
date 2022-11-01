package vttp2022batch1.finalProjectServer.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;

public class Response {

    private int code = 0;
    private String message = "";
    private String data = "{}";

    public int getCode() {return this.code;}
    public void setCode(int code) {this.code = code;}

    public String getMessage() {return this.message;}
    public void setMessage(String message) { this.message = message;}

    public String getData() {return this.data;}
    public void setData(String data) {this.data = data;}

    public static JsonObject toJson(Response response){
        return Json.createObjectBuilder()
                    .add("message", response.getMessage())
                    .add("code", response.getCode())
                    .add("data", response.getData())
                    .build();
    }
}
