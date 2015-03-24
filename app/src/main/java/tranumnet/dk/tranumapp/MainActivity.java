package tranumnet.dk.tranumapp;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity
{

    private WebView mWebView;
    private String myToast = "Der er ingen internet forbindelse i øjeblikket, venlist prøv igen senere.";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //gør fullscreen på app
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Viser layout activity_main når app starter
        mWebView = (WebView) findViewById(R.id.activity_main_webview);


        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);


        // Enable Javascript
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSaveFormData(true);

        //bruger denne url til at starte i webview
        mWebView.loadUrl("http://www.tranumnet.dk");

        // Stop local links and redirects from opening in browser instead of WebView
        mWebView.setWebViewClient(new MyAppWebViewClient());



        if ( isNetworkAvailable())
        {
            //gør synlig når webview virker
            mWebView.setVisibility(View.VISIBLE);

        }
        else
        {
            //toast ved ingen forbindelse 2 gange for længere tid vist (ca. 3,5 sek pr stk. i alt 7 sek).
            Toast.makeText(this, myToast, Toast.LENGTH_LONG).show();
            Toast.makeText(this, myToast,Toast.LENGTH_LONG).show();
        }



    }

    @Override
    public boolean onKeyDown (int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    void Showtoast(String message)
    {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //opdater knap i menu vil opdatere.
        switch (item.getItemId())
        {
            case R.id.action_update:
                mWebView.reload();
                break;
        }
        //afslut app knap
        switch (item.getItemId())
        {
            case R.id.action_quit:
                finish();
                break;
        }

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_update)
        {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public static class PlaceholderFragment extends Fragment
    {

        public PlaceholderFragment()
        {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState)
        {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }


    //bruges til at skrive om enhed er online hvis den ikke er.
    private boolean isNetworkAvailable()
    {
        ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected())
        {
            isAvailable = true;
        }


        //kode til at gemme i lokal cache
        mWebView.getSettings().setAppCacheMaxSize(1024*1024*8);
        mWebView.getSettings().setAppCachePath(("/data/data/"+ getPackageName() +"/cache"));
        mWebView.getSettings().setAppCacheEnabled(true);
        mWebView.getSettings().setDomStorageEnabled(true);
        mWebView.getSettings().setAllowFileAccess(true);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

        return isAvailable;
    }
}

