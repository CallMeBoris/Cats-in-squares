package boris.narodov.catsinsquares;

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
import android.widget.CheckBox;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;



public class BotMenu extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_BOOL_STEP = "saved_bool_step";
    private final String SAVED_INT_LEVEL = "saved_int_level";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bot_menu);
        loadCheck();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        ImageView image = findViewById(R.id.backgroundBotMenu);
        Button button1 = findViewById(R.id.easy);
        Button button2 = findViewById(R.id.medium);
        Button button3 = findViewById(R.id.hard);
        Button button4 = findViewById(R.id.buttonBackBotMenu);
        OutlineTextView box = findViewById(R.id.botMenuBox);
        try{
            image.setImageBitmap(resized);
            button1.setText(getText(R.string.easy));
            button2.setText(getText(R.string.medium));
            button3.setText(getText(R.string.hard));
            button4.setText(getText(R.string.back));
            box.setText(getText(R.string.bot_first));
        }
        catch (Exception e){}
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
        SharedPreferences.Editor ed = spref.edit();
        ed.putInt(SAVED_INT_LEVEL,1);
        ed.apply();
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
        SharedPreferences.Editor ed = spref.edit();
        ed.putInt(SAVED_INT_LEVEL,2);
        ed.apply();
        Intent intent = new Intent(this,FullscreenActivitybot.class);
        startActivity(intent);
        finish();
    }

    public void hard(View view){
        savedCheck();
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        SharedPreferences.Editor ed = spref.edit();
        ed.putInt(SAVED_INT_LEVEL,3);
        ed.apply();
        Intent intent = new Intent(this,FullscreenActivitybot.class);
        startActivity(intent);
        finish();
    }

    public boolean isBotFirst() {
        CheckBox sound = findViewById(R.id.botMenuBox);
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
        CheckBox firstBotStep = findViewById(R.id.botMenuBox);
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

