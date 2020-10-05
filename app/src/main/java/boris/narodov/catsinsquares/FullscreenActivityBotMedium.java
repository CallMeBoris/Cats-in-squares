package boris.narodov.catsinsquares;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
public class FullscreenActivityBotMedium extends AppCompatActivity {
    private boolean firstStep = false; //for bot first step
    private Animation anim;
    private final String SAVED_BOOL_STEP = "saved_bool_step";
    private int idimage=0;
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private int count=1;
    private String setText=null;
    private int a;
    private boolean forStepBot2=true;

    private MatrixWithEverything matrixWithEverything = new MatrixWithEverything();
    private int[][] images = matrixWithEverything.getImages();
    private int[][] matrix = matrixWithEverything.getMatrix();

    private int[][] buttons ={{R.id.firstFirstBotMedium,R.id.firstSecondBotMedium,R.id.firstThirdBotMedium,R.id.firstFourthBotMedium},
            {R.id.secondFirstBotMedium,R.id.secondSecondBotMedium,R.id.secondThirdBotMedium,R.id.secondFourthBotMedium},
            {R.id.thirdFirstBotMedium,R.id.thirdSecondBotMedium,R.id.thirdThirdBotMedium,R.id.thirdFourthBotMedium},
            {R.id.fourthFirstBotMedium,R.id.fourthSecondBotMedium,R.id.fourthThirdBotMedium,R.id.fourthFourthBotMedium}};

    private MediaPlayer mediaPlayer;
    Runnable sound = new Runnable() {
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
Bundle bundle;


    private AdView mAdView;
    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
bundle=savedInstanceState;
        setContentView(R.layout.activity_fullscreen_bot_medium);

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

        ImageView image1 = findViewById(R.id.backgroundGameMedium);
        ImageButton image3 = findViewById(R.id.imageButton29BotMedium);
        TableLayout image2 = findViewById(R.id.tableMedium);
        Bitmap bitTable = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resizedTable = Bitmap.createScaledBitmap(bitTable,c,c,true);
        Drawable d = new BitmapDrawable(getResources(),resizedTable);

        try {
            image1.setImageBitmap(resized);
            image2.setBackground(d);
            image3.setImageResource(R.drawable.nothing);
        }catch (Exception e){}

        setFourButtons(false);

        Thread forSound = new Thread(sound);

        TextView textView =(TextView) findViewById(R.id.textView3BotMedium); //вывод текста, сообщающего, что следующим будет ходить бот
        try {
            textView.setText(getText(R.string.firstStep));}catch (Exception e){}

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
if (spref.getBoolean(SAVED_BOOL,false)){
        forSound.start();}

        //requestWindowFeature(Window.FEATURE_NO_TITLE);
       // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
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

        mAdView = findViewById(R.id.adViewActivityBotMedium);
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
        //} else {
           // show();
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
                        imageButton.setImageResource(images[i][j]);
                    }
                }
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            stepBot(a);
            enabled(false);
            long ctime = System.currentTimeMillis();
            while (System.currentTimeMillis() - ctime < 700);
            if(forStepBot2){
            stepBot2();}
            enabled(true);
        }
    };

    public synchronized void talkBot(final View view) {
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);
        if (count==1){setFourButtons(true);}

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
        okno();
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

    public int bot() {
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
                    }else {bot();}
                }
                if (matrixWithEverything.uslovieZam(a, idimage)&&a!=88&&a!=77) {
                    return a;
                }
            }
        }
        return 0;
    }

    public synchronized void stepBot(final int a) { //Метод, в котором происходит шаг пользователя и бота
                MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
                mp.start();}
                for (int i = 0; i < 4; i++) { //циклы перебора матрицы игрового поля
                    for (int j = 0; j < 4; j++) { //циклы перебора матрицы игрового поля
                        if (matrix[i][j] == a && matrix[i][j] != 88 && matrix[i][j] != 77) { //проверка того, ту ли ячейку при переборе матрицы мы выбрали
                            matrix[i][j] = 88; //запись в матрицу хода пользователя
                            int b = images[i][j]; //запись id картинки выбранной карточки в переменную
                            ImageButton imageButtonExample = findViewById(R.id.imageButton29BotMedium); //запись в переменную кнопки с картинкой предыдущего хода
                            try{imageButtonExample.startAnimation(anim);}catch(Exception e){} //запуск анимации
                            imageButtonExample.setImageResource(b); //замена картинки предыдущего хода на картинку хода пользователя
                            TextView textView = findViewById(R.id.textView3BotMedium); //вывод текста, сообщающего, что следующим будет ходить бот
                            try{textView.setText(getString(R.string.blueGo));}catch (Exception e){} //вывод текста, сообщающего, что следующим будет ходить бот
                            images[i][j] = R.drawable.firstplayer; //замена картинки в игровом поле на картинку кота
                            ImageButton enabled = findViewById(buttons[i][j]); //запись кнопки, на которую нажимал пользователь
                            try{enabled.startAnimation(anim);}catch(Exception e){} //запуск анимации
                            //enabled.setEnabled(false); //делаем кнопку неактивной
                            idimage = a; //сохраняем id картинки, которую выбирал пользователь
                            vyvod(); //обновление главной матрицы, в которую записываются ходы
                            oknoThread();
                            return;
                        }//конец if, где проверяется правильность выбора ячейки пользователем
                    }// конец циклов перебора матрицы игрового поля у пользователя
                }// конец циклов перебора матрицы игрового поля у пользователя
    } //конец метода


    public void retry(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
        mp.start();}
        Intent intent = new Intent(this,FullscreenActivityBotMedium.class);
        startActivity(intent);
        finish();
    }

    public synchronized void stepBot2(){
        final MediaPlayer mp = MediaPlayer.create(getBaseContext(), R.raw.cat3); //создание звука хода бота
        int bota; //переменная в которую запишется ход бота
        boolean bool = matrixWithEverything.usloviyeWin(matrix); //запись в переменную проверки условия победы

        if (!bool) {
            bota = bot(); //записываем ход бота
            for (int i1 = 0; i1 < 4; i1++) { //циклы перебора матрицы игрового поля
                for (int j1 = 0; j1 < 4; j1++) { //циклы перебора матрицы игрового поля
                    if (matrix[i1][j1] == bota && matrix[i1][j1] != 88 && matrix[i1][j1] != 77) { //проверка того, ту ли ячейку при переборе матрицы мы выбрали
                        matrix[i1][j1] = 77; //запись в матрицу хода бота
                        int b5 = images[i1][j1]; //запись id картинки выбранной карточки в переменную
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL,false)){
                        mp.start();} //проигрыш звука

                        ImageButton
                                imageButtonExample = findViewById(R.id.imageButton29BotMedium); //запись в переменную кнопки с картинкой предыдущего хода
                        TextView
                                textView = findViewById(R.id.textView3BotMedium); //вывод текста, сообщающего, что следующим будет ходить бот
                        try{imageButtonExample.startAnimation(anim);}catch(Exception e){} //запуск анимации
                        imageButtonExample.setImageResource(b5); //замена картинки предыдущего хода на картинку хода бота
                        images[i1][j1] = R.drawable.secondplayer; //замена картинки в игровом поле на картинку кота
                        ImageButton enabled2 = findViewById(buttons[i1][j1]); //запись кнопки, на которую нажимал пользователь
                        try{enabled2.startAnimation(anim);}catch(Exception e){} //запуск анимации
                        //enabled2.setEnabled(false); //делаем кнопку неактивной
                        try{textView.setText(getString(R.string.redGo));}catch (Exception e){} //вывод текста, сообщающего, что следующим будет ходить бот
                        idimage = bota; //сохраняем id картинки, которую выбирал бот
                        vyvod();  //обновление главной матрицы, в которую записываются ходы
                        count++; //счётчик, по которому определяем в других методах, какую именно инфу выводить на экран
                        oknoThread();
                        return;
                    }//конец if, где проверяется правильность выбора ячейки ботом
                }// конец циклов перебора матрицы игрового поля у бота
            }// конец циклов перебора матрицы игрового поля у бота
        }//конец if, где проверяем условие победы
    }

    public void okno(){
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
                TextView textView = findViewById(R.id.textView3BotMedium);
                try{textView.setText(getString(R.string.youWin));}catch (Exception e){}
                setText = getString(R.string.youWin);
            } else if (matrixWithEverything.counter(count - 1) == 2) {
                TextView textView = findViewById(R.id.textView3BotMedium);
                try{textView.setText(getString(R.string.youLose));}catch (Exception e){}
                setText = getString(R.string.youLose);
            }

            final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);

            AlertDialog.Builder builder = new AlertDialog.Builder(FullscreenActivityBotMedium.this, R.style.AlertDialogCustom);
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
            TextView textView = findViewById(R.id.textView3BotMedium);
            if (matrixWithEverything.counter(count - 1) == 1) {
                try{textView.setText(getText(R.string.youWinbot));}catch (Exception e){}
                forStepBot2=false;
            }else if (matrixWithEverything.counter(count - 1) == 2) {
               try{ textView.setText(getText(R.string.youLosebot));}catch (Exception e){}
            }
        }
    }

    private void setFourButtons(boolean bool){
        findViewById(R.id.secondSecondBotMedium).setEnabled(bool);
        findViewById(R.id.secondThirdBotMedium).setEnabled(bool);
        findViewById(R.id.thirdSecondBotMedium).setEnabled(bool);
        findViewById(R.id.thirdThirdBotMedium).setEnabled(bool);
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