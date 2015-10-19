package baac.pi.kritsawan.baacrestaurant;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private UserTABLE objUserTABLE;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create & connected Database
        createAndConnect();

    }// Main method

    private void createAndConnect() {

        objUserTABLE = new UserTABLE(this);


    }
}//Main class
