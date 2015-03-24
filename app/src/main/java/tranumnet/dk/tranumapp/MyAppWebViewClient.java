package tranumnet.dk.tranumapp;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

/**
 * Created by Benjamin on 28-02-2015.
 */
public class MyAppWebViewClient extends WebViewClient
{

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url)
    {
        //parser url tranumnet.dk og viser i webview men kun url's som er under tranumnet.dk
        if(Uri.parse(url).getHost().endsWith("tranumnet.dk"))
        {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        view.getContext().startActivity(intent);


        return true;

    }


}