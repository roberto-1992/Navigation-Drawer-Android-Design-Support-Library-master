package com.videumcorp.desarrolladorandroid.navigationdrawerandroiddesignsupportlibrary.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class RcycloDatabaseHelper extends SQLiteOpenHelper {

    public static final String DB_NAME = "rcyclo";
    public static final int DB_VERSION = 1;

    public RcycloDatabaseHelper(Context context){ super(context, DB_NAME, null, DB_VERSION); }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE ESTABLISHMENT (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "EMAIL TEXT, " + "PASSWORD TEXT, " + "PHONE TEXT, " + "ADDRESS TEXT, " + "WASTE TEXT, " + "ACTIVO TEXT);");
        db.execSQL("CREATE TABLE COMPANY       (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME TEXT, " + "EMAIL TEXT, " + "PASSWORD TEXT, " + "PHONE TEXT, " + "ADDRESS TEXT, " + "ACTIVO TEXT);");
        db.execSQL("CREATE TABLE CONTAINER     (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "NAME_CONTAINER TEXT, " + "LATLONG TEXT, " + "ESTABLISHMENT TEXT, " + "COMPANY TEXT, " + "ESTADO TEXT, " + "ACTIVO TEXT, " + "WASTE TEXT);");

        insertEstablishment(db, "Fundacion San Jose", "sanJose@gmail.com", "admin", "800 212 200", "Santiago", "papel");
        insertEstablishment(db, "Fundacion Maria Ayuda",    "mariaAyuda@gmail.com",     "admin",    "23 28 0100",       "Santiago",         "plastico");
        insertEstablishment(db, "CENFA",                    "cenfa@gmail.com",          "admin",    "22 59 4187",       "Valparaiso",       "vidrio");
        insertEstablishment(db, "COAR",                     "coar@gmail.com",           "admin",    "27 32 2821",       "Santiago",         "lata");

        insertCompany(db, "Jumbo", "jumbo@gmail.com", "admin", "6032424", "Valparaiso");
        insertCompany(db, "Entel",  "entel@gmail.com", "admin", "6032424",  "Santiago");
        insertCompany(db, "Torre",  "torre@gmail.com", "admin", "6032424",  "Santiago");

        insertContainer(db,"Basurerito", "lat/lng: (-33.440070,-70.598046)", "Fundacion San Jose", "Jumbo", "Lleno", "ACTIVO", "papel");
        insertContainer(db,"Basurerito2", "lat/lng: (-33.434729,-70.637362)", "CENFA", "Jumbo", "Medio", "ACTIVO", "vidrio");
        insertContainer(db,"Basurerito3", "lat/lng: (-20.239486,-70.146185)", "Fundacion San Jose", "Jumbo", "Vacio", "INACTIVO", "papel");
        insertContainer(db,"Basurerito4", "lat/lng: (-33.440070,-70.598046)", "Fundacion San Jose", "Jumbo", "Vacio", "INACTIVO", "papel");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){}

    public static void  insertEstablishment(SQLiteDatabase db, String name, String email, String password, String phone, String address, String waste) {
        ContentValues establishmentValues = new ContentValues();
        establishmentValues.put("NAME", name);
        establishmentValues.put("EMAIL", email);
        establishmentValues.put("PASSWORD", password);
        establishmentValues.put("PHONE", phone);
        establishmentValues.put("ADDRESS", address);
        establishmentValues.put("WASTE", waste);
        establishmentValues.put("ACTIVO", "ACTIVO");
        db.insert("ESTABLISHMENT", null, establishmentValues);
    }

    public static void  insertCompany(SQLiteDatabase db, String name, String email, String password, String phone, String address) {
        ContentValues companyValues = new ContentValues();
        companyValues.put("NAME", name);
        companyValues.put("EMAIL", email);
        companyValues.put("PASSWORD", password);
        companyValues.put("PHONE", phone);
        companyValues.put("ADDRESS", address);
        companyValues.put("ACTIVO", "ACTIVO");

        db.insert("COMPANY", null, companyValues);
    }



    public static void  insertContainer(SQLiteDatabase db, String nameContainer, String latlong, String establishmentName, String companyName, String estado, String activo, String waste) {
        ContentValues containerValues = new ContentValues();
        containerValues.put("NAME_CONTAINER", nameContainer);
        containerValues.put("LATLONG", latlong);
        containerValues.put("ESTABLISHMENT", establishmentName);
        containerValues.put("COMPANY", companyName);
        containerValues.put("ESTADO", estado);
        containerValues.put("ACTIVO", activo);
        containerValues.put("WASTE", waste);
        db.insert("CONTAINER", null, containerValues);
    }

}