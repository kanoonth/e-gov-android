package com.mapfap.persistence;

/**
 * Created by mapfap on 2/28/15.
 */
public class Transaction {

    public static final String TYPE_SQL = "S";
    public static final String TYPE_FILE = "F";

    public String code;
    public String type;
    public String content;

}
