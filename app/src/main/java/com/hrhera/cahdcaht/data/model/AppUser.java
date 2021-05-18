package com.hrhera.cahdcaht.data.model;

import java.util.ArrayList;
import java.util.List;

public class AppUser {
    String name;
    List<String>phones=new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getPhones() {
        return phones;
    }

    public void setPhones(List<String> phones) {
        this.phones = phones;
    }
}
