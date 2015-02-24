package com.u.juthamas.egoverment;


import java.util.ArrayList;
import java.util.List;

public class TransactionGroup {
    public String string;
    public final List<String> children = new ArrayList<String>();

    public TransactionGroup(String string){
        this.string = string;
    }
}
