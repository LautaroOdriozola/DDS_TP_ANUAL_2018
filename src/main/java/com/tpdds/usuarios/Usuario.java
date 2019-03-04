package com.tpdds.usuarios;

import javax.persistence.*;


@Entity
public class Usuario {
    @Id
    String user;
    String password; // hash ?

    public Usuario(){}

    public Usuario(String user, String password){
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
