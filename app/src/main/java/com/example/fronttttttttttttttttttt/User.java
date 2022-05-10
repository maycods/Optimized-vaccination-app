package com.example.fronttttttttttttttttttt;

public class User {
    public String fullname,mail,password,tel;
    static int nbrU;
    public User(){
        nbrU++;
    }

    public User(String fullname,String  mail
            ,String tel,String password){
        this.fullname=fullname;
        this.password=password;
        this.mail=mail;
        this.tel=tel;
        nbrU=0;
    }
}
