package android.project.handmedown;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

public class WebViewFAQ extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_faq);
        //String url=;
        WebView home  = (WebView) this.findViewById(R.id.homeweb);
        home.loadUrl("file:///android_asset/Terms_and_condition.html");
        //home.setWebChromeClient(new WebChromeClient());
        //home.clearCache(true);
       // home.getSettings().setAppCacheEnabled(false);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Intent main = new Intent(WebViewFAQ.this, MainActivity.class);
                        startActivityForResult(main, 0);
                        Toast.makeText(WebViewFAQ.this, "This is Home Page.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add:
                        Intent donate = new Intent(WebViewFAQ.this, Donate.class);
                        startActivityForResult(donate, 0);
                        break;
                    case R.id.action_account:
                        Intent redirect = new Intent(WebViewFAQ.this, Feedback.class);
                        startActivityForResult(redirect, 0);
                        break;

                }
                return true;
            }
        });

    }
}
