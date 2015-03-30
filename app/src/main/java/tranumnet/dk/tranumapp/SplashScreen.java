package tranumnet.dk.tranumapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;



/**
 * Created by Benjamin on 30-03-2015.
 */
public class SplashScreen extends Activity
{
    //timer for Splash Screen
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startscreen);

        new Handler().postDelayed(new Runnable()
        {
            @Override
        public void run()
            {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                finish();
            }
        }, SPLASH_TIME_OUT);
    }



}
