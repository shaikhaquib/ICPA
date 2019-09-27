package com.delaroystudios.teacherassistant;

/**
 * Created by Vishakh on 19-04-2018.
 */

public class Loginretrieve {
    public Loginretrieve(int i, String string, String string1, String string2, String string3, String string4) {
        id=i;
        name=string;
        username=string1;
        passwword=string2;
        confirmpasswword=string3;
        phone=string4;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasswword() {
        return passwword;
    }

    public void setPasswword(String passwword) {
        this.passwword = passwword;
    }

    public String getConfirmpasswword() {
        return confirmpasswword;
    }

    public void setConfirmpasswword(String confirmpasswword) {
        this.confirmpasswword = confirmpasswword;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    int id;
    String name;
    String username;
    String passwword;
    String confirmpasswword;
    String phone;
}
