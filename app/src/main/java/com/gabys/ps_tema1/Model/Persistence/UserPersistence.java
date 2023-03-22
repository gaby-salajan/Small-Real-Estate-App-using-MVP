package com.gabys.ps_tema1.Model.Persistence;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.gabys.ps_tema1.Model.DB.DBConnection;
import com.gabys.ps_tema1.Model.User;

import java.util.ArrayList;

public class UserPersistence {
    private SQLiteDatabase database;

    public UserPersistence(Context context) {
        this.database = DBConnection.getInstance(context).getConnection();
    }

    public ArrayList<User> getAuthUsers() {
        ArrayList<User> userList = new ArrayList<>();
        //ia doar autentificati
        String whereArgs = "role != 0";
        Cursor cursor = database.query("Users", null, whereArgs, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                int role = cursor.getInt(cursor.getColumnIndexOrThrow("role"));

                userList.add(new User(id, username, password, role));

                cursor.moveToNext();
            }
        }

        cursor.close();
        return userList;
    }

    public User getLoginUser(String username){
        User result = null;
        //ia doar cel ce se autentifica
        String whereArgs = "username = " +"'"+username+"'";
        Cursor cursor = database.query("Users", null, whereArgs, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                int role = cursor.getInt(cursor.getColumnIndexOrThrow("role"));

                result  = new User(id, username, password, role);

                cursor.moveToNext();
            }
        }
        cursor.close();

        return result;
    }

    public User getUser(int id) {
        User user = null;
        Cursor cursor = database.query("Users", null, "id = " + id, null, null, null, null);

        if (cursor != null){
            user = new User();
            cursor.moveToFirst();
        }

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                String username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
                String password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
                int role = cursor.getInt(cursor.getColumnIndexOrThrow("role"));

                user = new User(username, password, role);

                cursor.moveToNext();
            }
        }

        cursor.close();
        return user;
    }

    public long addUser(User user) {
        ContentValues values = new ContentValues();

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        return database.insert("Users", null, values);
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();

        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        values.put("role", user.getRole());

        String[] whereArgs = {String.valueOf(user.getId())};
        database.update("Users", values, "id = ?", whereArgs);
    }
    
    public int deleteUser(int id) {
        String[] whereArgs = {String.valueOf(id)};
        return database.delete("Users", "id = ?", whereArgs);
    }

    public void createUserTable() {
        String query = "CREATE TABLE IF NOT EXISTS Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "username TEXT, "+
                "password TEXT," +
                "role INTEGER DEFAULT 0);"
                ;
        database.execSQL(query);
    }

    public void deleteTable(){
        database.execSQL("DROP TABLE IF EXISTS Users");
    }

    public void insertDummyValues(){
        this.addUser(new User("john", "1234", 1));
        this.addUser(new User("alex", "1234", 2));

    }
}
