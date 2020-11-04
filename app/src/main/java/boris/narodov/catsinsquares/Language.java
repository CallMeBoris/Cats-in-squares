package boris.narodov.catsinsquares;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

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

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        ImageView image = findViewById(R.id.backgroundLanguage);
        Button button1 = findViewById(R.id.lanEng);
        Button button2 = findViewById(R.id.lanDeu);
        Button button3 = findViewById(R.id.lanRus);
        Button button4 = findViewById(R.id.lanUkr);
        Button button5 = findViewById(R.id.lanExit);
        try {
            image.setImageBitmap(resized);
            button1.setText(getText(R.string.english));
            button2.setText(getText(R.string.deutsch));
            button3.setText(getText(R.string.russian));
            button4.setText(getText(R.string.ukranian));
            button5.setText(getText(R.string.back));
        }catch (Exception e){}
    }

    public void back(final View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,newMenuActivity.class);
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
        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
        startActivity(intent);
    }
}
