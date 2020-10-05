package boris.narodov.catsinsquares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        ImageView image = findViewById(R.id.backgroundClassic);
        Button button1 = findViewById(R.id.gameBot);
        Button button2 = findViewById(R.id.game);
        Button button3 = findViewById(R.id.rules);
        Button button5 = findViewById(R.id.classicExit);
        try {
            image.setImageBitmap(resized);
            button1.setText(getText(R.string.play));
            button2.setText(getText(R.string.two_players));
            button3.setText(getText(R.string.rules));
            button5.setText(getText(R.string.back));
        }catch (Exception e){}


        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewActivityMain);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void game(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this, FullscreenActivity.class);
        startActivity(intent);
        finish();
    }

    public void gameBot(final View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this, BotMenu.class);
        startActivity(intent);
        finish();
    }

    public void exit(final View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this, newMenuActivity.class);
        startActivity(intent);
        finish();
    }

public void rules(View view){
    final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
    spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
    if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
    Intent intent = new Intent(this, Rules.class);
    startActivity(intent);
    finish();
}

}