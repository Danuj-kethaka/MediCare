package controller;
import model.User;

import java.util.ArrayList;

public class LoginController {
    ArrayList<User> users;

    public LoginController() {
        users = new ArrayList<>();
        createUser();
    }

    private void createUser(){
        users.add(new User("admin", "admin"));
        users.add(new User("pharmacist", "1234"));
    }

    public User validateUser(User currentUser){
        for(User user: users){
            if(user.getUsername().equals(currentUser.getUsername()) && user.getPassword().equals(currentUser.getPassword())){
                return user;
            }
        }
        return null;
    }
}
