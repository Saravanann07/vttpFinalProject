package vttp2022batch1.finalProjectServer.models;

public class AuthenticationResponse {

    private final String jwt;
    private final Integer userId;
    private final String username;

    public AuthenticationResponse(String jwt, Integer userId, String username) {
        this.jwt = jwt;
        this.userId = userId;
        this.username = username;
    }

    public String getJwt() {
        return this.jwt;
    }

    public Integer getUserId(){
        return this.userId;
    }

    public String getUsername(){
        return this.username;
    }
    
}
