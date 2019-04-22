package android.project.handmedown;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(getApplicationContext(),"Mounil", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Shyam", Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"Dipesh", Toast.LENGTH_LONG).show();
    }
}
