package com.example.planmanagement
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class database(val context:Context, name:String, version: Int):SQLiteOpenHelper(context,name,null,version) {

    private val createplan="create table planta ("+
            "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
            "title text,"+
            "date1 text,"+
            "date2 text,"+
            "content text,"+
            "fujian text,"+
            "fujian1 text,"+
            "category text,"+
            "important text,"+
            "factfinish text)"
    override fun onCreate(db: SQLiteDatabase?) {
        if (db != null) {
            db.execSQL(createplan)
        }
        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
    }
}