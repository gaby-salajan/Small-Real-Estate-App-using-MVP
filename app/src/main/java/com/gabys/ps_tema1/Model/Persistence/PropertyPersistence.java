package com.gabys.ps_tema1.Model.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gabys.ps_tema1.Model.DB.DBConnection;
import com.gabys.ps_tema1.Model.Property;

import java.util.ArrayList;

public class PropertyPersistence {
    private SQLiteDatabase database;

    public PropertyPersistence(Context context) {
        this.database = DBConnection.getInstance(context).getConnection();
    }
    public ArrayList<Property> getAvailableProperties() {
        ArrayList<Property> properties = new ArrayList<>();

        Cursor cursor = database.query("Properties", null, "isAvailable = 1", null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                int rooms = cursor.getInt(cursor.getColumnIndexOrThrow("roomsNo"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                int isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("isAvailable"));
                String imageURL = cursor.getString(cursor.getColumnIndexOrThrow("imageURL"));

                properties.add(new Property(id, title, location, rooms, type, price, isAvailable == 1, imageURL));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return properties;
    }

    public ArrayList<Property> getProperties() {
        ArrayList<Property> properties = new ArrayList<>();
        Cursor cursor = database.query("Properties", null, null, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                int rooms = cursor.getInt(cursor.getColumnIndexOrThrow("roomsNo"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                int isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("isAvailable"));
                String imageURL = cursor.getString(cursor.getColumnIndexOrThrow("imageURL"));

                properties.add(new Property(id, title, location, rooms, type, price, isAvailable == 1, imageURL));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return properties;
    }

    public Property getProperty(int id) {
        Property property = null;
        Cursor cursor = database.query("Properties", null, "id = " + id, null, null, null, null);

        if (cursor != null){
            property = new Property();
            cursor.moveToFirst();
        }


        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String title = cursor.getString(cursor.getColumnIndexOrThrow("title"));
                String location = cursor.getString(cursor.getColumnIndexOrThrow("location"));
                int rooms = cursor.getInt(cursor.getColumnIndexOrThrow("roomsNo"));
                String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
                float price = cursor.getFloat(cursor.getColumnIndexOrThrow("price"));
                int isAvailable = cursor.getInt(cursor.getColumnIndexOrThrow("isAvailable"));
                String imageURL = cursor.getString(cursor.getColumnIndexOrThrow("imageURL"));

                property = new Property(id, title, location, rooms, type, price, isAvailable == 1, imageURL);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return property;
    }

    public long addProperty(Property property) {
        ContentValues values = new ContentValues();

        values.put("title", property.getTitle());
        values.put("location", property.getLocation());
        values.put("roomsNo", property.getRoomsNo());
        values.put("type", property.getType());
        values.put("price", property.getPrice());
        values.put("isAvailable", property.isAvailable() ? 1 : 0);
        values.put("imageURL", property.getImageURL());

        return database.insert("Properties", null, values);
    }

    public void updateProperty(Property property) {
        ContentValues values = new ContentValues();

        values.put("title", property.getTitle());
        values.put("location", property.getLocation());
        values.put("roomsNo", property.getRoomsNo());
        values.put("type", property.getType());
        values.put("price", property.getPrice());
        values.put("isAvailable", property.isAvailable() ? 1 : 0);
        values.put("imageURL", property.getImageURL());

        String[] whereArgs = {String.valueOf(property.getId())};
        database.update("Properties", values, "id = ?", whereArgs);
    }

    public int deleteProperty(int id) {
        String[] whereArgs = {String.valueOf(id)};
        return database.delete("Properties", "id = ?", whereArgs);
    }

    public void createPropertyTable() {
        String query = "CREATE TABLE IF NOT EXISTS Properties (" +
                "id INTEGER primary key autoincrement, " +
                "title TEXT, " +
                "location TEXT, " +
                "roomsNo INTEGER, " +
                "type TEXT, " +
                "price REAL, " +
                "isAvailable INTEGER DEFAULT 1, " +
                "imageURL TEXT);"
                ;
        database.execSQL(query);
    }

    public void deleteTable(){
        database.execSQL("DROP TABLE IF EXISTS Properties");
    }

    public void insertDummyValues(){
        this.addProperty(new Property("Apartament cu 2 camere", "Cluj",2,"Apartament",400f, true,"https://empireimobiliare.com/oferte/170/big__spatiu-birou-18-camere-de-inchiriat-central-cluj-napoca_6408718b714cd6.jpg"));
        this.addProperty(new Property("Casa cu etaj","Baciu",7,"Casa/Vila",1200f,true, "https://img.staticmb.com/mbcontent//images/uploads/2022/12/Most-Beautiful-House-in-the-World.jpg"));
        this.addProperty(new Property("Apartament cu 3 camere","Floresti",3,"Apartament",600f,true,"https://www.bhg.com/thmb/dgy0b4w_W0oUJUxc7M4w3H4AyDo=/1866x0/filters:no_upscale():strip_icc()/living-room-gallery-shelves-l-shaped-couch-ELeyNpyyqpZ8hosOG3EG1X-b5a39646574544e8a75f2961332cd89a.jpg"));
    }
}
