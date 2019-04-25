package android.project.handmedown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button Goto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Goto = (Button) findViewById(R.id.save);
        Goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent redirect = new Intent(v.getContext(), Feedback.class);
                startActivityForResult(redirect, 0);
            }
        });
    }
}
