package boris.narodov.catsinsquares;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import boris.narodov.catsinsquares.R;

import boris.google.android.ads.nativetemplates.NativeTemplateStyle;
import boris.google.android.ads.nativetemplates.TemplateView;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

public class MainActivity extends AppCompatActivity {

    SharedPreferences spref;
    final String SAVED_BOOL = "saved_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
                loadCheck();

        MobileAds.initialize(this, "\n" + "ca-app-pub-5586713183085646~5422503179");
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();
                        TemplateView template = findViewById(R.id.my_template);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);
                    }
                })
                .build();
        adLoader.loadAd(new AdRequest.Builder().build());
    }



    public void game(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if(isSound()){
        mp.start();}
        Intent intent = new Intent(this, Real_online.class);
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


    public boolean isSound() {
CheckBox sound = findViewById(R.id.sound);
        return sound.isChecked();
    }


private void savedCheck(){
        spref = getSharedPreferences("forsound",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spref.edit();
        ed.putBoolean(SAVED_BOOL,isSound());
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