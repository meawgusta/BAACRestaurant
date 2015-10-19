package baac.pi.kritsawan.baacrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by BAAC on 19/10/2558.
 */
public class MyOpenHelper extends SQLiteOpenHelper{

    //Explicit ประกาศตัวแปรเพื่อให้โทรศัพท์คำนวนเมมถ้าพอให้รันได้เลย
    private static final String DATABASE_NAME ="BAAC.db";//เปลี่ยนแปลงไม่ได้
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_USER_TABLE = "create table userTable (_id integer primary key, user text, Password text, Name text);";
    private static final String CREATE_FOOD_TABLE = "create table foodTable (_id integer primary key, Food text, Source text, Price text);";

    public MyOpenHelper(Context context) {
        super(context , DATABASE_NAME, null, DATABASE_VERSION);//context first
    }// Constructor

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_FOOD_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}//Main Class
