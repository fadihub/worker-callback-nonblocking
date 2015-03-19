package io.advantageous.qbit.example.recommendationengine;

/* Domain object. */
public class User {

    private final String userName;

    public User(String userName){
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }
}
