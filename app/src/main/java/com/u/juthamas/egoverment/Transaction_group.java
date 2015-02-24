package com.u.juthamas.egoverment;


import java.util.ArrayList;
import java.util.List;

public class Transaction_group {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public Transaction_group(String string){
        this.string = string;
    }
}
