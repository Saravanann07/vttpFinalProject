package vttp2022batch1.finalProjectServer.models;

public class AuthenticationRequest {

    private String username;
    private String email;
    private String password;
    
    public AuthenticationRequest() {

    }
    
    public AuthenticationRequest(String email, String username, String password) {
        this.setEmail(email);;
        this.setUsername(username);;
        this.setPassword(password);
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


    
}
