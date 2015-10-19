package baac.pi.kritsawan.baacrestaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by BAAC on 19/10/2558.
 */
public class UserTABLE {

    //Explicit
    private MyOpenHelper objMyOpenHelper;
    private SQLiteDatabase WriteSqLiteDatabase, readSqLiteDatabase;

    public UserTABLE(Context context) {

        objMyOpenHelper = new MyOpenHelper(context);
        WriteSqLiteDatabase = objMyOpenHelper.getWritableDatabase();
        readSqLiteDatabase = objMyOpenHelper.getReadableDatabase();

    }//constructor
}
