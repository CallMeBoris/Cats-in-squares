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

public class AboutTheAuthors extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_the_authors);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        Button button = findViewById(R.id.buttonExit);
        ImageView image = findViewById(R.id.backgroundAuthors);
        TextView text1 = findViewById(R.id.aboutFirst);
        TextView text2 = findViewById(R.id.aboutThird);
        TextView text3 = findViewById(R.id.aboutFourth);
        try{
            button.setText(getText(R.string.back));
            image.setImageBitmap(resized);
            text1.setText(getText(R.string.graph));
            text2.setText(getText(R.string.audio));
            text3.setText(getText(R.string.translate));
        }catch (Exception e){};
    }

    public void exitMenu(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this,newMenuActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
        startActivity(intent);
    }
}
