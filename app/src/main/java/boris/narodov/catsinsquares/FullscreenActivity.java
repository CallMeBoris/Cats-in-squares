package boris.narodov.catsinsquares;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.Objects;
import java.util.Random;

import boris.google.android.ads.nativetemplates.NativeTemplateStyle;
import boris.google.android.ads.nativetemplates.TemplateView;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    SharedPreferences spref;
    final String SAVED_BOOL = "saved_bool";
    public int idimage=0;
    Animation anim;

    public int[][] images ={{R.drawable.a11,R.drawable.a12,R.drawable.a13,R.drawable.a14},
            {R.drawable.a21,R.drawable.a22,R.drawable.a23,R.drawable.a24},
            {R.drawable.a31,R.drawable.a32,R.drawable.a33,R.drawable.a34},
            {R.drawable.a41,R.drawable.a42,R.drawable.a43,R.drawable.a44}};

    public int[][] matrix={{11,12,13,14},{21,22,23,24},{31,32,33,34},{41,42,43,44}};

    public int[][] buttons ={{R.id.firstFirst,R.id.firstSecond,R.id.firstThird,R.id.firstFourth},
            {R.id.secondFirst,R.id.secondSecond,R.id.secondThird,R.id.secondFourth},
            {R.id.thirdFirst,R.id.thirdSecond,R.id.thirdThird,R.id.thirdFourth},
            {R.id.fourthFirst,R.id.fourthSecond,R.id.fourthThird,R.id.fourthFourth}};

    MediaPlayer mediaPlayer;
    Runnable sound = new Runnable() {
        @Override
        public void run() {

            mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.longsecond);
            mediaPlayer.setLooping(true);
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();}
        }
    };

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen);

        findViewById(R.id.secondSecond).setEnabled(false);
        findViewById(R.id.secondThird).setEnabled(false);
        findViewById(R.id.thirdSecond).setEnabled(false);
        findViewById(R.id.thirdThird).setEnabled(false);

        TextView textView =(TextView) findViewById(R.id.textView3);
        try {
            textView.setText(getText(R.string.firstStep));}catch (Exception e){}

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Thread forSound = new Thread(sound);

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            forSound.start();}

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        // findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        random();
        vyvod();

        anim = AnimationUtils.loadAnimation(this, R.anim.anim);

        MobileAds.initialize(this, "ca-app-pub-5586713183085646~5422503179");
        AdLoader adLoader = new AdLoader.Builder(this, "ca-app-pub-3940256099942544/2247696110")
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                        NativeTemplateStyle styles = new
                                NativeTemplateStyle.Builder().build();

                        TemplateView template = findViewById(R.id.my_templateGame);
                        template.setStyles(styles);
                        template.setNativeAd(unifiedNativeAd);

                    }
                })
                .build();

        adLoader.loadAd(new AdRequest.Builder().build());
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        //if (mVisible) {
            hide();
        //} else {
            //show();
            //hide();
       // }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    public void vyvod(){
        for (int i =0; i<4;i++){
            for (int j =0; j<4;j++) {
                ImageButton imageButton = findViewById(buttons[i][j]);
                imageButton.setImageResource(images[i][j]);
            }
        }
    }

    public  void random(){
        Random random = new Random();
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                int index1 = random.nextInt(i+1);
                int index2 = random.nextInt(j+1);
                int a=images[index1][index2];
                int b=matrix[index1][index2];
                images[index1][index2]=images[i][j];
                matrix[index1][index2]=matrix[i][j];
                images[i][j]=a;
                matrix[i][j]=b;
            }
        }
    }

    public boolean usloviyeWin(int[][] matr){
        try{
            for (int j=0; j<4;j++){
                if(matr[0][j]==matr[1][j]&&matr[2][j] == matr[3][j]&&matr[1][j]==matr[3][j]){
                    return true;
                }
                else if(matr[j][0]==matr[j][1]&&matr[j][2] == matr[j][3]&&matr[j][1]==matr[j][3]){
                    return true;
                }
                else if(matr[0][j]==matr[1][j]&&matr[0][j+1] == matr[1][j+1]&&matr[1][j]==matr[1][j+1]){
                    return true;
                }
                else if(matr[1][j]==matr[2][j]&&matr[1][j+1] == matr[2][j+1]&&matr[2][j]==matr[2][j+1]){
                    return true;
                }
                else if(matr[2][j]==matr[3][j]&&matr[2][j+1] == matr[3][j+1]&&matr[3][j]==matr[3][j+1]){
                    return true;
                }
            }
        }catch (Exception ignored){}
        return false;
    }
String setText=null;
    private  int count=1;


    public synchronized void talk(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}

        int a;

        if (count==1){
            findViewById(R.id.secondSecond).setEnabled(true);
            findViewById(R.id.secondThird).setEnabled(true);
            findViewById(R.id.thirdSecond).setEnabled(true);
            findViewById(R.id.thirdThird).setEnabled(true);}

        boolean bool=usloviyeWin(matrix);
        if (!bool) {
            a = proverka(view.getId());
            if (idimage == 0) {
                idimage = a;
            }
            if (uslovieZam(a, idimage)) {
                step(a, count);
                count++;
                idimage = a;//комментить когда бот
            } else {
                Toast.makeText(this, getString(R.string.condition), Toast.LENGTH_SHORT).show();
            }
        }
        okno();
    }


    public boolean uslovieZam(int a, int b){
        return a / 10 == b / 10 || a % 10 == b % 10;
    }

    public int counter(int count){
        if (count%2==0){
            return 2;
        }else {return 1;}
    }

    public int proverka(int id){
        for (int i =0;i<4;i++){
            for (int j=0;j<4;j++){
                if (buttons[i][j]==id){
                    return matrix[i][j];
                }
            }
        }
        return 0;}


    public synchronized void step(int a, int n) {
        for (int i =0; i<4;i++){
            for (int j =0; j<4;j++){
                if (matrix[i][j]==a&&n%2==0&&matrix[i][j]!=1&&matrix[i][j]!=0){
                    matrix[i][j]=0;
                    int b = images[i][j];
                    TextView textView = findViewById(R.id.textView3);
                    ImageButton enabled =findViewById(buttons[i][j]);
                    ImageButton imageButtonExample = findViewById(R.id.imageButton29);
                    try{imageButtonExample.startAnimation(anim);
                    imageButtonExample.setImageResource(b);}catch(Exception e){}
                    images[i][j]=R.drawable.secondplayer;

                    try{enabled.startAnimation(anim);}catch(Exception e){}
                    enabled.setEnabled(false);
                    try{textView.setText(getString(R.string.redGo));}catch (Exception e){}
                    vyvod();
                    if (usloviyeWin(matrix)) {
                        if (counter(count) == 1) {
                            try{textView.setText(getString(R.string.redWin));}catch (Exception e){}
                        } else if (counter(count) == 2) {
                            try{textView.setText(R.string.blueWin);}catch (Exception e){}
                        }
                    }
                }
                else if (matrix[i][j]==a&&n%2!=0&&matrix[i][j]!=1&&matrix[i][j]!=0){
                    matrix[i][j]=1;
                    int b = images[i][j];
                    TextView textView = findViewById(R.id.textView3);
                    ImageButton imageButtonExample = findViewById(R.id.imageButton29);
                    try{imageButtonExample.startAnimation(anim);
                    imageButtonExample.setImageResource(b);}catch(Exception e){}
                    try{textView.setText(getString(R.string.blueGo));}catch (Exception e){}
                    images[i][j]=R.drawable.firstplayer;
                    ImageButton enabled =findViewById(buttons[i][j]);
                    try{enabled.startAnimation(anim);}catch(Exception e){}

                    enabled.setEnabled(false);
                    vyvod();
                    if (usloviyeWin(matrix)) {
                        if (counter(count) == 1) {
                            try{textView.setText(getString(R.string.redWin));}catch (Exception e){}
                        } else if (counter(count) == 2) {
                            try{textView.setText(getString(R.string.blueWin));}catch (Exception e){}
                        }
                    }
                }
            }
        }

    }


    public void retry(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,FullscreenActivity.class);
        startActivity(intent);
        finish();
    }


    public void okno(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        boolean bool;
        int k=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (matrix[i][j]/10!=idimage/10&&matrix[i][j]%10!=idimage%10||matrix[i][j]==1||matrix[i][j]==0){
                    k++;
                }
            }
        }
        bool=usloviyeWin(matrix);
        if (k==16){
            bool=true;
        }
    if (bool){
        if (counter(count-1)==1){
            TextView textView = findViewById(R.id.textView3);
            textView.setText(getString(R.string.redWin));
            setText=getString(R.string.redWin);

        }else if (counter(count-1)==2){
            TextView textView = findViewById(R.id.textView3);
            textView.setText(getString(R.string.blueWin));
            setText=getString(R.string.blueWin);
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(FullscreenActivity.this,R.style.AlertDialogCustom);

        builder.setTitle(setText);
        builder.setMessage(getString(R.string.Playagain));
        builder.setIcon(R.drawable.logo);
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        retry();
                    }
                });
        builder.setPositiveButton(getString(R.string.menu),

                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL,false)){
                            mp.start();}
                        Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                        startActivity(intent);
                        finish();

                    }});
        AlertDialog alert = builder.create();
        alert.show();}
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{
            mediaPlayer.release();}catch (Exception e){}
    }

    @Override
    protected void onPause() {
        super.onPause();
        try{
            mediaPlayer.pause();}catch (Exception e){}
    }

    @Override
    protected void onResume() {
        super.onResume();
        try{
            mediaPlayer.start();}catch (Exception e){}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
