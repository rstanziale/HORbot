package beans.auth;

public class Login {
    private boolean auth;
    private String token;
    private String username;
    private boolean developer;

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getDeveloper() {
        return developer;
    }

    public void setDeveloper(boolean developer) {
        this.developer = developer;
    }
}
