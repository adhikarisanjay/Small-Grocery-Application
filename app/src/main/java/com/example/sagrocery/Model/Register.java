package com.example.sagrocery.Model;

public class Register {
    private int id;
    private  String username, email, password;
    private  void setId(int id){
        this.id=id;
    }
    public  void setUsername(String username){
        this.username=username;
    }
    public  void setEmail(String email){
        this.email=email;
    }

    public  void setPassword(String password){
        this.password=password;
    }

    public int getId(){
        return id;
    }

    public String getUsername(){
        return username;
    }

    public  String getEmail(){
        return  email;
    }

    public  String getPassword(){
        return  password;
    }
}
