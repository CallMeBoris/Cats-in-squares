package boris.narodov.catsinsquares;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;


public class BotMenu extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_BOOL_STEP = "saved_bool_step";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_menu);
        loadCheck();
    }

    public void backbot(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    public void easy(View view){
        savedCheck();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,FullscreenActivitybot.class);
        startActivity(intent);
        finish();
    }

    public void medium(View view){
        savedCheck();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,FullscreenActivityBotMedium.class);
        startActivity(intent);
        finish();
    }

    public void hard(View view){
        savedCheck();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this,FullscreenActivityBotHard.class);
        startActivity(intent);
        finish();
    }

    public boolean isBotFirst() {
        CheckBox sound = findViewById(R.id.botFirstStep);
        return sound.isChecked();
    }

    private void savedCheck(){
        spref = getSharedPreferences("forFirstStep",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed = spref.edit();
        ed.putBoolean(SAVED_BOOL_STEP,isBotFirst());
        ed.apply();
    }

    private void loadCheck(){
        spref = getSharedPreferences("forFirstStep", Context.MODE_PRIVATE);
        boolean savedBool = spref.getBoolean(SAVED_BOOL_STEP,false);
        CheckBox firstBotStep = findViewById(R.id.botFirstStep);
        firstBotStep.setChecked(savedBool);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        savedCheck();
    }
}

