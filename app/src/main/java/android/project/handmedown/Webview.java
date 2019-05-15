package android.project.handmedown;

import android.content.Context;
import android.project.handmedown.Navigationdrawer.NavigationDrawerActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebView;


public class Webview extends NavigationDrawerActivity {

    WebView home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*setContentView(R.layout.activity_web_view);*/
        LayoutInflater inflater = (LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_web_view,null, false);
        drawer.addView(view,0);
        home  = (WebView) findViewById(R.id.homeweb);
        Intent intent = getIntent();
        switch (intent.getStringExtra("id")){
            case "faqs":
            home.loadUrl("file:///android_asset/faq.html");
                break;
            case "contactus":
                home.loadUrl("file:///android_asset/contact_us.html");
                break;
            case "aboutus":
                home.loadUrl("file:///android_asset/About_us.html");
                break;
            case "tnc":
                home.loadUrl("file:///android_asset/Terms_and_condition.html");
                break;
                default:
        }
        //home.setWebChromeClient(new WebChromeClient());
        //home.clearCache(true);
        // home.getSettings().setAppCacheEnabled(false);
        /*BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_recents:
                        Intent main = new Intent(getApplicationContext(), MainActivity.class);
                        startActivityForResult(main, 0);
                        Toast.makeText(Webview.this, "This is Home Page.", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_add:
                        Intent donate = new Intent(Webview.this, Donate.class);
                        startActivityForResult(donate, 0);
                        break;
                    case R.id.action_account:
                        Intent redirect = new Intent(Webview.this, Feedback.class);
                        startActivityForResult(redirect, 0);
                        break;

                }
                return true;
            }
        });


    }*/}
}
