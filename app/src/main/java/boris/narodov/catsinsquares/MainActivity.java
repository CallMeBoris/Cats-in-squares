package boris.narodov.catsinsquares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class MainActivity extends AppCompatActivity {

    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_BOOL2 = "saved_bool2";

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                loadCheck();

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
        if(isSound()){
        mp.start();}
        Intent intent = new Intent(this, FullscreenActivityOnline.class);
        startActivity(intent);
        finish();
    }

    public void gameBot(final View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
        mp.start();}
        Intent intent = new Intent(this, BotMenu.class);
        startActivity(intent);
        finish();
    }

    public void language(final View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
        mp.start();}
        Intent intent = new Intent(this, Language.class);
        startActivity(intent);
        finish();
    }

    public void exit(final View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat2);
        if (isSound()){
        mp.start();}
        finish();
    }

public void rules(View view){
    final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
    if (isSound()){
    mp.start();}
    Intent intent = new Intent(this, Rules.class);
    startActivity(intent);
    finish();
}

    public void about(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
            mp.start();}
        Intent intent = new Intent(this, AboutTheAuthors.class);
        startActivity(intent);
        finish();
    }


    public boolean isSound() {
CheckBox sound = findViewById(R.id.sound);
        return sound.isChecked();
    }


private void savedCheck(){
        spref = getSharedPreferences("forsound",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spref.edit();
        ed.putBoolean(SAVED_BOOL,isSound());
        ed.putBoolean(SAVED_BOOL2, true);
        ed.apply();
}

private void loadCheck(){
    spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
   boolean savedBool = spref.getBoolean(SAVED_BOOL,true);
    CheckBox sound = findViewById(R.id.sound);
    sound.setChecked(savedBool);
}

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savedCheck();
    }
}