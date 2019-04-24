package android.project.handmedown;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends AppCompatActivity {
    TextView t;
    Button B;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);
        t=findViewById(R.id.textView);
        if(getIntent().getExtras()!=null) {
            String user = getIntent().getExtras().getString("user");
            t.setText(user);
        }
    B=findViewById(R.id.button1);
        B.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new User(FirstActivity.this).remove();
                Intent i = new Intent( FirstActivity.this,MainActivity.class);
                startActivity(i);
            }
        });

    }
}
