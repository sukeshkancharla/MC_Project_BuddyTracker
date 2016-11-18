package vinodhkumar.sample2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * Created by VinodhKumar on 17/11/16.
 */

public class RecieverNotificationActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toast.makeText(getApplicationContext(),"Request Accepted",Toast.LENGTH_SHORT);
    }
}
