package boris.narodov.catsinsquares;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class AboutTheAuthors extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_RATE = "saved_rate";

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

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_RATE, true)){
        getRate();}
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

    public void rateApp()
    {
        try
        {
            Intent rateIntent = rateIntentForUrl("market://details");
            startActivity(rateIntent);
        }
        catch (ActivityNotFoundException e)
        {
            Intent rateIntent = rateIntentForUrl("https://play.google.com/store/apps/details");
            startActivity(rateIntent);
        }
    }

    private Intent rateIntentForUrl(String url)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(String.format("%s?id=%s", url, getPackageName())));
        int flags = Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_MULTIPLE_TASK;
        if (Build.VERSION.SDK_INT >= 21)
        {
            flags |= Intent.FLAG_ACTIVITY_NEW_DOCUMENT;
        }
        else
        {
            //noinspection deprecation
            flags |= Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET;
        }
        intent.addFlags(flags);
        return intent;
    }

    private void getRate(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        AlertDialog.Builder builder = new AlertDialog.Builder(AboutTheAuthors.this, R.style.AlertDialogCustom);
        builder.setTitle(getString(R.string.rate));
        try{builder.setIcon(R.drawable.logo);}catch (Exception e){}
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        rateApp();
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL, false)) {
                            mp.start();
                        }
                        SharedPreferences.Editor ed = spref.edit();
                        ed.putBoolean(SAVED_RATE,false);
                        ed.apply();
                    }
                });
        builder.setPositiveButton(getString(R.string.no),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL, false)) {
                            mp.start();
                        }
                        SharedPreferences.Editor ed = spref.edit();
                        ed.putBoolean(SAVED_RATE,false);
                        ed.apply();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
        startActivity(intent);
    }
}
