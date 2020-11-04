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
import android.widget.TextView;

public class Rules extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rules);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        ImageView image = findViewById(R.id.backgroundRules);
        ImageView image2 = findViewById(R.id.imageRulesFirst);
        ImageView image3 = findViewById(R.id.imageRulesSecond);
        ImageView image4 = findViewById(R.id.imageRulesThird);
        ImageView image5 = findViewById(R.id.imageRulesFourth);
        ImageView image6 = findViewById(R.id.imageRulesFifth);
        TextView text1 = findViewById(R.id.rulesFirst);
        TextView text2 = findViewById(R.id.rulesSecond);
        TextView text3 = findViewById(R.id.rulesThird);
        Button button = findViewById(R.id.button5);
        try{
            image.setImageBitmap(resized);
            image2.setImageResource(R.drawable.rules1);
            image3.setImageResource(R.drawable.rules2);
            image4.setImageResource(R.drawable.rules3);
            image5.setImageResource(R.drawable.rules4);
            image6.setImageResource(R.drawable.rules5);
            text1.setText(getText(R.string.rulesFirst));
            text2.setText(getText(R.string.rulesSecond));
            text3.setText(getText(R.string.rulesThird));
        button.setText(getText(R.string.back));}catch (Exception e){};
    }

    public void exitMenu(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}