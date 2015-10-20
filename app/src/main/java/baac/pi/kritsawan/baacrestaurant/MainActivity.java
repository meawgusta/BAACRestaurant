package baac.pi.kritsawan.baacrestaurant;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.SynchronousQueue;

public class MainActivity extends AppCompatActivity {
    //Explicit

    //step1
    private UserTABLE objUserTABLE;
    private FoodTABLE objFoodTABLE;//สีเทาไม่ได้ใช้งาน สีม่วงถูกเอาไปใช้งาน
    private EditText userEditText, passwordEditText;
    private String userString, passwordString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget ผูกตัวแปร
        bindWidget();


        //create & connected Database
        createAndConnect();

        //Tester Add New Values
        //testerAdd();

        //Delete all SQLLite
        deleteAllSQLite();

        //Synchronous JSON to SQLite
        synJSONtoSQLite();


    }// Main method

    private void bindWidget() {
        userEditText = (EditText) findViewById(R.id.editText);
        passwordEditText = (EditText) findViewById(R.id.editText2);
    }

    public void clickLogin(View view) {
        userString = userEditText.getText().toString().trim();
        passwordString = passwordEditText.getText().toString().trim();

        if (userString.equals("")  || passwordString.equals("")) {//เปรียบเทียบสตริงต้องใช้ .equal
            //Have space
            errorDialog("Have Space","Please fill all information");
        } else {
            // no Space กรอกทั้ง user and password
            checkUser();

        }

    }

    private void checkUser() {

        try {
            String[] strMyResult = objUserTABLE.searchUser(userString);

            Log.d("baac", "Password True ==>>>> " + strMyResult[2]);

            if (passwordString.equals(strMyResult[2])) {
                Toast.makeText(MainActivity.this, "Welcome " + strMyResult[3], Toast.LENGTH_LONG).show();

            } else {
                errorDialog("Password Fail","please try again password false");
            }


        } catch (Exception e) {
            errorDialog("Invalid User","No " + userString + " on DB");
        }
    }

    private void errorDialog(String strTitle, String strMessege) {
        AlertDialog.Builder objBuilder = new AlertDialog.Builder(this);
        objBuilder.setIcon(R.drawable.danger);//show picture
        objBuilder.setTitle(strTitle);
        objBuilder.setMessage(strMessege);
        objBuilder.setCancelable(false);
        objBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        objBuilder.show();
    }


    private void synJSONtoSQLite() {

        //step 0 change policy
        StrictMode.ThreadPolicy myPolicy = new StrictMode.ThreadPolicy.Builder().permitAll().build();//open all connection
        StrictMode.setThreadPolicy(myPolicy);

        int intTimes = 0; //ตามจำนวน Table
        while (intTimes <= 1) {

            InputStream objInputStream = null;
            String strJSON = null;
            String strUserURL = "http://swiftcodingthai.com/baac/php_get_data_master.php";//ตามจำนวน LINK TABLE
            String strFoodURL = "http://swiftcodingthai.com/baac/php_get_food.php";
            HttpPost objHttpPost;

            //step 1 create input stream

            try {
                HttpClient objHttpClient = new DefaultHttpClient();

                switch (intTimes) {
                    case 0:
                        objHttpPost = new HttpPost(strUserURL);
                        break;
                    case 1:
                        objHttpPost = new HttpPost(strFoodURL);
                        break;
                    default:
                        objHttpPost = new HttpPost(strUserURL);
                        break; //กรณีที่ไม่มีค่า ต้องมีค่า default
                }// switch

                HttpResponse objHttpResponse = objHttpClient.execute(objHttpPost);
                HttpEntity objHttpEntity = objHttpResponse.getEntity();
                objInputStream = objHttpEntity.getContent(); //load buffer done

            } catch (Exception e) {
                Log.d("baac","InputStream ==>" + e.toString());
            }

            //step 2 create JSON string
            //เปลี่ยนให้เป็นString1ตัว

            try {

                BufferedReader objBufferedReader = new BufferedReader(new InputStreamReader(objInputStream,"UTF-8"));//ตัด
                StringBuilder objStringBuilder = new StringBuilder();
                String strLine = null;

                while ((strLine = objBufferedReader.readLine()) != null) {
                    objStringBuilder.append(strLine);//ต่อเชื่อมสตริง

                }//while

                objInputStream.close();
                strJSON = objStringBuilder.toString();//ได้สตริงจากการต่อมาแล้ว

            } catch (Exception e) {
                Log.d("baac", "strJSON ==>" + e.toString());
            }

            //step 3 update SQLite

            try {
                JSONArray objJsonArray = new JSONArray(strJSON);

                for (int i = 0; i < objJsonArray.length(); i++) {
                    JSONObject object = objJsonArray.getJSONObject(i);
                    switch (intTimes) {
                        case 0:
                            //for UserTABLE
                            String strUser = object.getString("User");
                            String strPassword = object.getString("Password");
                            String strName = object.getString("Name");
                            objUserTABLE.addNewUser(strUser, strPassword, strName);


                            break;
                        case 1:
                            //for FoodTABLE
                            String strFood = object.getString("Food");
                            String strSaurce = object.getString("Source");
                            String strPrice = object.getString("Price");
                            objFoodTABLE.addNewFood(strFood, strSaurce, strPrice);

                            break;
                    }//switch

                }//ตัดดาต้าเข้า SQLLite

            } catch (Exception e) {
                Log.d("baac", "Update ==>" + e.toString());
            }

            intTimes += 1;



        }//while


    }//synJSONtoSQLite

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
