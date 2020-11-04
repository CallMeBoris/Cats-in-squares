package boris.narodov.catsinsquares;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.Random;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivitybot extends AppCompatActivity {
    private boolean firstStep = false; //for bot first step
    private SharedPreferences spref;
    private final String SAVED_BOOL_STEP = "saved_bool_step";
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_INT_LEVEL = "saved_int_level";
    private Animation anim;
    private int idimage=0;
    private int step=0;
    private int[] firstNumFromYourStep = {0,1,2,3,4,5,6,7};
    private int[] secondNumFromYourStep={0,1,2,3,4,5,6,7};

    private MatrixWithEverything matrixWithEverything = new MatrixWithEverything();
    private int[][] images = matrixWithEverything.getImages();
    private int[][] matrix = matrixWithEverything.getMatrix();
    private int[][] buttons ={{R.id.firstFirstBot,R.id.firstSecondBot,R.id.firstThirdBot,R.id.firstFourthBot},
           {R.id.secondFirstBot,R.id.secondSecondBot,R.id.secondThirdBot,R.id.secondFourthBot},
            {R.id.thirdFirstBot,R.id.thirdSecondBot,R.id.thirdThirdBot,R.id.thirdFourthBot},
           {R.id.fourthFirstBot,R.id.fourthSecondBot,R.id.fourthThirdBot,R.id.fourthFourthBot}};

    private int count=1;
    private String setText=null;
    private int a;
    private boolean forStepBot2=true;
    private MediaPlayer mediaPlayer;
    private  Runnable sound = new Runnable() {
        @Override
        public void run() {
            mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.longfirst);
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


    private AdView mAdView;
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_activitybot);

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        ImageView image1 = findViewById(R.id.backgroundGameEasy);
        ImageButton image3 = findViewById(R.id.imageButtonEasy);
        TableLayout image2 = findViewById(R.id.tableEasy);

        Bitmap bitTable = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resizedTable = Bitmap.createScaledBitmap(bitTable,c,c,true);
        Drawable d = new BitmapDrawable(getResources(),resizedTable);
        try {
            image1.setImageBitmap(resized);
            image2.setBackground(d);
            image3.setImageResource(R.drawable.nothing);
        }catch (Exception e){}



        Thread forSound = new Thread(sound);

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            forSound.start();
        }

        setFourButtons(false);

        TextView textView =(TextView) findViewById(R.id.textView3Bot); //вывод текста, сообщающего, что следующим будет ходить бот
        try {
            textView.setText(getText(R.string.firstStep));}catch (Exception e){}


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
        matrixWithEverything.random();
        vyvod();
        spref = getSharedPreferences("forFirstStep", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL_STEP,false)){
            firstStep=true;//for bot first step
            stepBot2();// for bot first step
            firstStep=false;// for bot first step
            count=1;
            setFourButtons(true);
        }

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adViewActivityBotEasy);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
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
       // } else {
            //show();
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

    public synchronized void vyvod(){
        for (int i =0; i<4;i++){
            for (int j =0; j<4;j++) {
                ImageButton imageButton = findViewById(buttons[i][j]);
                try{imageButton.setImageResource(images[i][j]);}catch (Exception e){}
            }
        }
    }

     Runnable runnable = new Runnable() {
        @Override
        public void run() {
            try{
            stepBot(a);
            enabled(false);
            long ctime = System.currentTimeMillis();
            while (System.currentTimeMillis() - ctime < 700);
            if (forStepBot2){
            stepBot2();}
            enabled(true);
                }catch (Exception e){}
        }
    };

    public synchronized void talkBot(final View view) {
        if (count==1){setFourButtons(true);}
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        boolean bool=matrixWithEverything.usloviyeWin(matrix);

        if (!bool){
            a=proverka(view.getId());
            if (idimage==0){ idimage=a;}
            if (matrixWithEverything.uslovieZam(a,idimage)){
                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
                    Thread thread = new Thread(runnable);
                    thread.start();}
                else {
                    stepBot(a);
                    enabled(false);
                    long ctime = System.currentTimeMillis();
                    while (System.currentTimeMillis() - ctime < 200) ;
                    if (forStepBot2) {
                        stepBot2();
                    }
                    enabled(true);
                }
                count++;
            }else {
                Toast.makeText(this, getString(R.string.condition), Toast.LENGTH_SHORT).show();}
            }
        uslovieokno();
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

    public int botEasy() {
        int[] aa = {0, 1, 2, 3};
        int[] bb = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index1 = random.nextInt(i +1);
            int index2 = random.nextInt(i +1);
            int a2 = aa[index1];
            int b2 = bb[index2];
            aa[index1]=aa[i];
            bb[index2]=bb[i];
            aa[i]=a2;
            bb[i]=b2;
        }

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int a = matrix[aa[i]][bb[j]];
                if (firstStep){
                    if ((a!=11)&&(a!=12)&&(a!=21)&&(a!=22)){
                        return a;
                    }else {botEasy();}
                }// for bot first step
                if (matrixWithEverything.uslovieZam(a, idimage)&&a!=88&&a!=77) {
                    return a;
                }
            }
        }
        return 0;
    }

    public int botMedium() {
        int[] aa = {0, 1, 2, 3};
        int[] bb = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index1 = random.nextInt(i +1);
            int index2 = random.nextInt(i +1);
            int a2 = aa[index1];
            int b2 = bb[index2];
            aa[index1]=aa[i];
            bb[index2]=bb[i];
            aa[i]=a2;
            bb[i]=b2;
        }
        int[] aa2 = {1, 2};
        int[] bb2 = {1, 2};
        for (int i = 0; i < 2; i++) {
            int index1 = random.nextInt(i +1);
            int index2 = random.nextInt(i +1);
            int a2 = aa2[index1];
            int b2 = bb2[index2];
            aa2[index1]=aa2[i];
            bb2[index2]=bb2[i];
            aa2[i]=a2;
            bb2[i]=b2;
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                int a = matrix[aa2[i]][bb2[j]];
                if (matrixWithEverything.uslovieZam(a, idimage)&&a!=0&&a!=1) {
                    return a;
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int a = matrix[aa[i]][bb[j]];
                if (firstStep){
                    if ((a!=11)&&(a!=12)&&(a!=21)&&(a!=22)){
                        return a;
                    }else {botMedium();}
                }
                if (matrixWithEverything.uslovieZam(a, idimage)&&a!=88&&a!=77) {
                    return a;
                }
            }
        }
        return 0;
    }

    public int botHard() {
        int[] aa = {0, 1, 2, 3};
        int[] bb = {0, 1, 2, 3};
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int index1 = random.nextInt(i +1);
            int index2 = random.nextInt(i +1);
            int a2 = aa[index1];
            int b2 = bb[index2];
            aa[index1]=aa[i];
            bb[index2]=bb[i];
            aa[i]=a2;
            bb[i]=b2;
        }

        for (int i = 0; i < step; i++) {
            int index1 = random.nextInt(i +1);
            int index2 = random.nextInt(i +1);
            int a2 = firstNumFromYourStep[index1];
            int b2 = secondNumFromYourStep[index2];
            firstNumFromYourStep[index1]=firstNumFromYourStep[i];
            secondNumFromYourStep[index2]=secondNumFromYourStep[i];
            firstNumFromYourStep[i]=a2;
            secondNumFromYourStep[i]=b2;
        }

        for (int i = 0; i < step; i++) {
            int a;
            int b;
            int c;
            int d;
            try {
                a = matrix[firstNumFromYourStep[i]-1][secondNumFromYourStep[i]];
                if (matrixWithEverything.uslovieZam(a, idimage)) {
                    return a;}
            }catch (Exception e){}
            try {
                b = matrix[firstNumFromYourStep[i]+1][secondNumFromYourStep[i]];
                if (matrixWithEverything.uslovieZam(b, idimage)) {
                    return b; }
            }catch (Exception e){}
            try {
                c = matrix[firstNumFromYourStep[i]][secondNumFromYourStep[i]-1];
                if (matrixWithEverything.uslovieZam(c, idimage)) {
                    return c;}
            }catch (Exception e){}
            try {
                d = matrix[firstNumFromYourStep[i]][secondNumFromYourStep[i]+1];
                if (matrixWithEverything.uslovieZam(d, idimage)) {
                    return d;}
            }catch (Exception e){}
        }


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                int a = matrix[aa[i]][bb[j]];
                if (firstStep){return a;}// for bot first step
                if (matrixWithEverything.uslovieZam(a, idimage)&&a!=77&&a!=88) {
                    return a;
                }
            }
        }
        return 0;
    }


    public synchronized void stepBot(int a) {
        final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.cat3);
        try {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    if (matrix[i][j] == a && matrix[i][j] != 88 && matrix[i][j] != 77) {
                        firstNumFromYourStep[step]=i;
                        secondNumFromYourStep[step]=j;
                        step++;
                        matrix[i][j] = 88;
                        int b = images[i][j];
                        ImageButton imageButtonExample = findViewById(R.id.imageButtonEasy);
                        try{imageButtonExample.startAnimation(anim);}catch(Exception e){}
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL,false)){
                            mp.start();}
                        try{imageButtonExample.setImageResource(b);}catch (Exception e){}
                        TextView textView = findViewById(R.id.textView3Bot);
                        try{textView.setText(getString(R.string.blueGo));}catch (Exception e){}
                        try{images[i][j] = R.drawable.firstplayer;}catch (Exception e){}
                        ImageButton enabled = findViewById(buttons[i][j]);
                        try{enabled.startAnimation(anim);}catch(Exception e){}
                        idimage = a;
                        vyvod();
                        oknoThread();
                        return;
                    }
                }
            }
        }catch (Exception e){}
    }

    public synchronized void stepBot2(){
        final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.cat3);
        int bota;
        boolean bool = matrixWithEverything.usloviyeWin(matrix);
        if (!bool) {
            bota = bot();
            for (int i1 = 0; i1 < 4; i1++) {
                for (int j1 = 0; j1 < 4; j1++) {
                    if (matrix[i1][j1] == bota && matrix[i1][j1] != 88 && matrix[i1][j1] != 77) {
                        matrix[i1][j1] = 77;
                        int b5 = images[i1][j1];
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL,false)){
                            mp.start();}
                        ImageButton imageButtonExample = findViewById(R.id.imageButtonEasy);
                        try{imageButtonExample.setImageResource(b5);
                        imageButtonExample.startAnimation(anim);}catch(Exception e){}
                        try{images[i1][j1] = R.drawable.secondplayer;}catch (Exception e){}
                        ImageButton enabled2 = findViewById(buttons[i1][j1]);
                        try{enabled2.startAnimation(anim);}catch(Exception e){vyvod();}
                        TextView textView = findViewById(R.id.textView3Bot);
                        try{textView.setText(getString(R.string.redGo));}catch (Exception e){}
                        idimage = bota;
                        vyvod();
                        count++;
                        oknoThread();
                        return;
                    }
                }
            }
        }
    }

    public void oknoThread(){
        boolean bool;
        int k=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (matrix[i][j]/10!=idimage/10&&matrix[i][j]%10!=idimage%10||matrix[i][j]==88||matrix[i][j]==77){
                    k++;
                }
            }
        }
        bool=matrixWithEverything.usloviyeWin(matrix);
        if (k==16){
            bool=true;
        }
        if (bool) {
            if (matrixWithEverything.counter(count - 1) == 1) {
                TextView textView = findViewById(R.id.textView3Bot);
                try{textView.setText(getString(R.string.youWinbot));}catch (Exception e){}
                forStepBot2=false;
            } else if (matrixWithEverything.counter(count - 1) == 2) {
                TextView textView = findViewById(R.id.textView3Bot);
                try{textView.setText(getString(R.string.youLosebot));}catch (Exception e){}
            }
        }
    }


    public void retry(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,FullscreenActivitybot.class);
        startActivity(intent);
        finish();
    }

    public void uslovieokno(){
        boolean bool;
        int k=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (matrix[i][j]/10!=idimage/10&&matrix[i][j]%10!=idimage%10||matrix[i][j]==88||matrix[i][j]==77){
                    k++;
                }
            }
        }
        bool=matrixWithEverything.usloviyeWin(matrix);
        if (k==16){
            bool=true;
        }
        if (bool) {
            if (matrixWithEverything.counter(count - 1) == 1) {
                TextView textView = findViewById(R.id.textView3Bot);
                try {
                    textView.setText(getString(R.string.youWin));
                }catch (Exception e){}
                setText = getString(R.string.youWin);
            } else if (matrixWithEverything.counter(count - 1) == 2) {
                TextView textView = findViewById(R.id.textView3Bot);
                try{textView.setText(getString(R.string.youLose));}catch (Exception e){}
                setText = getString(R.string.youLose);
            }

            final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);

            AlertDialog.Builder builder = new AlertDialog.Builder(FullscreenActivitybot.this, R.style.AlertDialogCustom);
            builder.setTitle(setText);
            builder.setMessage(getString(R.string.Playagain));
            try{builder.setIcon(R.drawable.logo);}catch (Exception e){}
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
                            if (spref.getBoolean(SAVED_BOOL, false)) {
                                mp.start();
                            }
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();

                        }
                    });
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    private void setFourButtons(boolean bool){
        findViewById(R.id.secondSecondBot).setEnabled(bool);
        findViewById(R.id.secondThirdBot).setEnabled(bool);
        findViewById(R.id.thirdSecondBot).setEnabled(bool);
        findViewById(R.id.thirdThirdBot).setEnabled(bool);
    }

    private int bot(){
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getInt(SAVED_INT_LEVEL,0)==1){
            return botEasy();}else
        if (spref.getInt(SAVED_INT_LEVEL,0)==2){
            return botMedium();}
        else return botHard();

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

    public void enabled(boolean enabled){
        ImageButton button;
        for (int i = 0;i<4;i++){
            for (int j = 0; j<4; j++){
                button=findViewById(buttons[i][j]);
                try{
                    button.setEnabled(enabled);}catch (Exception e){}
            }
        }
    }
}