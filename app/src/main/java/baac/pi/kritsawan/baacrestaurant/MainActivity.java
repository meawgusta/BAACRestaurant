package baac.pi.kritsawan.baacrestaurant;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Explicit

    //step1
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;//สีเทาไม่ได้ใช้งาน สีม่วงถูกเอาไปใช้งาน


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create & connected Database
        createAndConnect();

        //Tester Add New Values
        //testerAdd();

        //Delete all SQLLite
        deleteAllSQLite();

    }// Main method

    private void deleteAllSQLite() {

        SQLiteDatabase objSqLiteDatabase = openOrCreateDatabase("BAAC.db",MODE_PRIVATE, null);
        objSqLiteDatabase.delete("userTABLE",null,null);
        objSqLiteDatabase.delete("foodTABLE", null, null);

    }

    private void testerAdd() {
        //step2
        objUserTABLE.addNewUser("testUser", "testPass", "ทดสอบไทย");
        objFoodTABLE.addNewFood("ชื่ออาหาร","testSource","123");
    }

    private void createAndConnect() {
        //step3
        objUserTABLE = new UserTABLE(this);
        objFoodTABLE = new FoodTABLE(this);


    }
}//Main class
