package com.example.caloriecount;

public class UserData {
    String userName;
    String userAge;
    String userCalorie;

    public UserData(){
    }

    public UserData(String userName, String userAge, String userCalorie) {
        this.userName = userName;
        this.userAge = userAge;
        this.userCalorie = userCalorie;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserAge() {
        return userAge;
    }

    public String getUserCalorie() {
        return userCalorie;
    }
}
