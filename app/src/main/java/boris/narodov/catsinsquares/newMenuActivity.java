package boris.narodov.catsinsquares;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapDrawableResource;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

public class newMenuActivity extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_BOOL2 = "saved_bool2";
    private final String SAVED_SCORE = "saved_score";
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_menu);
        loadCheck();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);


        ImageView image = findViewById(R.id.backgroundNewMenu);
        Button button1 = findViewById(R.id.classic);
        Button button2 = findViewById(R.id.puzzle);
        Button button3 = findViewById(R.id.language);
        Button button4 = findViewById(R.id.about);
        Button button5 = findViewById(R.id.exitNewMenu);
        OutlineTextView box = findViewById(R.id.soundNewMenu);
        try {
            image.setImageBitmap(resized);
            button1.setText(getText(R.string.classic));
            button2.setText(getText(R.string.puzzle));
            button3.setText(getText(R.string.language));
            button4.setText(getText(R.string.BtnAbout));
            button5.setText(getText(R.string.exit));
            box.setText(getText(R.string.sound_on));
        }catch (Exception e){}

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewActivityNewMenu);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void classic(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
            mp.start();}
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void puzzle(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
            mp.start();}
        savedCheck();
        Intent intent = new Intent(this, PuzzleActivity.class);
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

    public void about(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        if (isSound()){
            mp.start();}
        Intent intent = new Intent(this, AboutTheAuthors.class);
        startActivity(intent);
        finish();
    }

    public void exit(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat2);
        if (isSound()){
            mp.start();}
        finish();
    }

    private void savedCheck(){
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spref.edit();
        ed.putBoolean(SAVED_BOOL,isSound());
        ed.putBoolean(SAVED_BOOL2, true);
        if (spref.getInt(SAVED_SCORE,0)<1){
        ed.putInt(SAVED_SCORE,0);}
        ed.apply();
    }

    public boolean isSound() {
        CheckBox sound = findViewById(R.id.soundNewMenu);
        return sound.isChecked();
    }

    private void loadCheck(){
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        boolean savedBool = spref.getBoolean(SAVED_BOOL,true);
        CheckBox sound = findViewById(R.id.soundNewMenu);
        sound.setChecked(savedBool);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savedCheck();
    }
}
