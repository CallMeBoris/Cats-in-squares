package boris.narodov.catsinsquares;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class Language extends AppCompatActivity {

    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
    }

    public void back(final View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void deu(View view){
        Resources res = view.getResources(); //Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("de".toLowerCase()));
        res.updateConfiguration(conf, dm);
        back(view);// API 17+ only. // Use conf.locale = new Locale(...) if targeting lower versions
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void eng(View view){
        Resources res = view.getResources(); //Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("values".toLowerCase()));
        res.updateConfiguration(conf, dm);
        back(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void ru(View view){
        Resources res = view.getResources(); //Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("ru".toLowerCase()));
        res.updateConfiguration(conf, dm);
        back(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void ukr(View view){
        Resources res = view.getResources(); //Change locale settings in the app.
        DisplayMetrics dm = res.getDisplayMetrics();
        android.content.res.Configuration conf = res.getConfiguration();
        conf.setLocale(new Locale("uk".toLowerCase()));
        res.updateConfiguration(conf, dm);
        back(view);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
