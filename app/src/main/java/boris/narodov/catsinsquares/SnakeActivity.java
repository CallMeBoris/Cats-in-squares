package boris.narodov.catsinsquares;

import androidx.annotation.RequiresApi;
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
import android.view.Display;
import android.view.MotionEvent;
import android.widget.ImageView;

import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SnakeActivity extends AppCompatActivity implements OnSwipeTouchListener.SimpleGestureListener {
    private Mouse mouse;
    private boolean isGame = true;
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_MAX_SCORE = "saved_max_score_snake";
    private final String SAVED_SCORE = "saved_score_snake";
    private int score;
    private int maxScore;
    private Display display;
    private Point size;
    private OnSwipeTouchListener detector;
    private List<GameObject> snakeParts = new ArrayList<>();
    private Direction direction=Direction.LEFT;
    private AdView mAdView;
    private final int FIELD_SIZE = 15;
    private int[][] idField = {{R.id.snake1_1,R.id.snake1_2,R.id.snake1_3,R.id.snake1_4,R.id.snake1_5,R.id.snake1_6,R.id.snake1_7,R.id.snake1_8,R.id.snake1_9,R.id.snake1_10,R.id.snake1_11,R.id.snake1_12,R.id.snake1_13,R.id.snake1_14,R.id.snake1_15},
    {R.id.snake2_1,R.id.snake2_2,R.id.snake2_3,R.id.snake2_4,R.id.snake2_5,R.id.snake2_6,R.id.snake2_7,R.id.snake2_8,R.id.snake2_9,R.id.snake2_10,R.id.snake2_11,R.id.snake2_12,R.id.snake2_13,R.id.snake2_14,R.id.snake2_15},
    {R.id.snake3_1,R.id.snake3_2,R.id.snake3_3,R.id.snake3_4,R.id.snake3_5,R.id.snake3_6,R.id.snake3_7,R.id.snake3_8,R.id.snake3_9,R.id.snake3_10,R.id.snake3_11,R.id.snake3_12,R.id.snake3_13,R.id.snake3_14,R.id.snake3_15},
    {R.id.snake4_1,R.id.snake4_2,R.id.snake4_3,R.id.snake4_4,R.id.snake4_5,R.id.snake4_6,R.id.snake4_7,R.id.snake4_8,R.id.snake4_9,R.id.snake4_10,R.id.snake4_11,R.id.snake4_12,R.id.snake4_13,R.id.snake4_14,R.id.snake4_15},
    {R.id.snake5_1,R.id.snake5_2,R.id.snake5_3,R.id.snake5_4,R.id.snake5_5,R.id.snake5_6,R.id.snake5_7,R.id.snake5_8,R.id.snake5_9,R.id.snake5_10,R.id.snake5_11,R.id.snake5_12,R.id.snake5_13,R.id.snake5_14,R.id.snake5_15},
    {R.id.snake6_1,R.id.snake6_2,R.id.snake6_3,R.id.snake6_4,R.id.snake6_5,R.id.snake6_6,R.id.snake6_7,R.id.snake6_8,R.id.snake6_9,R.id.snake6_10,R.id.snake6_11,R.id.snake6_12,R.id.snake6_13,R.id.snake6_14,R.id.snake6_15},
    {R.id.snake7_1,R.id.snake7_2,R.id.snake7_3,R.id.snake7_4,R.id.snake7_5,R.id.snake7_6,R.id.snake7_7,R.id.snake7_8,R.id.snake7_9,R.id.snake7_10,R.id.snake7_11,R.id.snake7_12,R.id.snake7_13,R.id.snake7_14,R.id.snake7_15},
    {R.id.snake8_1,R.id.snake8_2,R.id.snake8_3,R.id.snake8_4,R.id.snake8_5,R.id.snake8_6,R.id.snake8_7,R.id.snake8_8,R.id.snake8_9,R.id.snake8_10,R.id.snake8_11,R.id.snake8_12,R.id.snake8_13,R.id.snake8_14,R.id.snake8_15},
    {R.id.snake9_1,R.id.snake9_2,R.id.snake9_3,R.id.snake9_4,R.id.snake9_5,R.id.snake9_6,R.id.snake9_7,R.id.snake9_8,R.id.snake9_9,R.id.snake9_10,R.id.snake9_11,R.id.snake9_12,R.id.snake9_13,R.id.snake9_14,R.id.snake9_15},
    {R.id.snake10_1,R.id.snake10_2,R.id.snake10_3,R.id.snake10_4,R.id.snake10_5,R.id.snake10_6,R.id.snake10_7,R.id.snake10_8,R.id.snake10_9,R.id.snake10_10,R.id.snake10_11,R.id.snake10_12,R.id.snake10_13,R.id.snake10_14,R.id.snake10_15},
    {R.id.snake11_1,R.id.snake11_2,R.id.snake11_3,R.id.snake11_4,R.id.snake11_5,R.id.snake11_6,R.id.snake11_7,R.id.snake11_8,R.id.snake11_9,R.id.snake11_10,R.id.snake11_11,R.id.snake11_12,R.id.snake11_13,R.id.snake11_14,R.id.snake11_15},
    {R.id.snake12_1,R.id.snake12_2,R.id.snake12_3,R.id.snake12_4,R.id.snake12_5,R.id.snake12_6,R.id.snake12_7,R.id.snake12_8,R.id.snake12_9,R.id.snake12_10,R.id.snake12_11,R.id.snake12_12,R.id.snake12_13,R.id.snake12_14,R.id.snake12_15},
    {R.id.snake13_1,R.id.snake13_2,R.id.snake13_3,R.id.snake13_4,R.id.snake13_5,R.id.snake13_6,R.id.snake13_7,R.id.snake13_8,R.id.snake13_9,R.id.snake13_10,R.id.snake13_11,R.id.snake13_12,R.id.snake13_13,R.id.snake13_14,R.id.snake13_15},
    {R.id.snake14_1,R.id.snake14_2,R.id.snake14_3,R.id.snake14_4,R.id.snake14_5,R.id.snake14_6,R.id.snake14_7,R.id.snake14_8,R.id.snake14_9,R.id.snake14_10,R.id.snake14_11,R.id.snake14_12,R.id.snake14_13,R.id.snake14_14,R.id.snake14_15},
    {R.id.snake15_1,R.id.snake15_2,R.id.snake15_3,R.id.snake15_4,R.id.snake15_5,R.id.snake15_6,R.id.snake15_7,R.id.snake15_8,R.id.snake15_9,R.id.snake15_10,R.id.snake15_11,R.id.snake15_12,R.id.snake15_13,R.id.snake15_14,R.id.snake15_15}};

    private int[][] field = new int[15][15];

    private MediaPlayer mediaPlayer;
    Runnable sound = new Runnable() {
        @Override
        public void run() {

            mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.snakemelody);
            mediaPlayer.setLooping(true);
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();}
        }
    };

    private int cc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snake);
        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        if (a==0||b==0){
            a=1200;
            b=1200;
        }
        cc =(int) ((b -94)/15);
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);

        TextView textScore = findViewById(R.id.scoreSnake);
        TextView textMaxScore = findViewById(R.id.maxScoreSnake);
        ImageView image1 = findViewById(R.id.backgroundSnake);
        TableLayout image2 = findViewById(R.id.tableSnake);
        Bitmap bitTable = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resizedTable = Bitmap.createScaledBitmap(bitTable,c,c,true);
        Drawable d = new BitmapDrawable(getResources(),resizedTable);
        try {
            textScore.setText(getText(R.string.score));
            textMaxScore.setText(getText(R.string.max_score));
            image1.setImageBitmap(resized);
            image2.setBackground(d);
        }catch (Exception e){}

        Thread forSound = new Thread(sound);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL, false)) {
            forSound.start();}

        snakeParts.add(new GameObject(12,5));
        snakeParts.add(new GameObject(13,5));
        snakeParts.add(new GameObject(14,5));
            emptyField();
            createMouse();

        detector = new OnSwipeTouchListener(this,this);

        game();

                MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewActivitySnake);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void emptyField(){
        for (int i = 0; i<FIELD_SIZE;i++){
            for (int j = 0; j<FIELD_SIZE;j++){
                field[i][j]=0;
            }
        }
    }

    public void vyvod(){
        display.getSize(size);
        Bitmap bitNothing = BitmapFactory.decodeResource(getResources(),R.drawable.nothing);
        Bitmap bitCat = BitmapFactory.decodeResource(getResources(),R.drawable.catpuzzle65536);
        Bitmap bitCatHead = BitmapFactory.decodeResource(getResources(),R.drawable.catpuzzle16384);
        Bitmap bitMouse = BitmapFactory.decodeResource(getResources(),R.drawable.mouse);
        Bitmap resizedNothing = Bitmap.createScaledBitmap(bitNothing,cc,cc,true);
        Bitmap resizedCat = Bitmap.createScaledBitmap(bitCat,cc,cc,true);
        Bitmap resizedCatHead = Bitmap.createScaledBitmap(bitCatHead,cc,cc,true);
        Bitmap resizedMouse = Bitmap.createScaledBitmap(bitMouse,cc,cc,true);
        for (int i =0; i<FIELD_SIZE;i++){
            for (int j =0; j<FIELD_SIZE;j++) {

                ImageView imageView = findViewById(idField[i][j]);
                if (field[i][j]==2){
                    imageView.setImageBitmap(resizedCat);
                }else if(field[i][j]==1){
                    imageView.setImageBitmap(resizedMouse);
                }else if(field[i][j]==3){
                    imageView.setImageBitmap(resizedCatHead);
                }else {
                imageView.setImageBitmap(resizedNothing);}

            }
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {

        switch (direction) {
            case OnSwipeTouchListener.SWIPE_RIGHT : setDirection(Direction.RIGHT);
                break;
            case OnSwipeTouchListener.SWIPE_LEFT :  setDirection(Direction.LEFT);
                break;
            case OnSwipeTouchListener.SWIPE_DOWN :  setDirection(Direction.DOWN);
                break;
            case OnSwipeTouchListener.SWIPE_UP :    setDirection(Direction.UP);
                break;
        }
    }

    @Override
    public  void onDoubleTap() {}

    public void setDirection(Direction direction){
        if (this.direction==Direction.LEFT&&!(direction==Direction.RIGHT)&&!(snakeParts.get(0).x==snakeParts.get(1).x)){
            this.direction=direction;}
        if (this.direction==Direction.RIGHT&&!(direction==Direction.LEFT)&&!(snakeParts.get(0).x==snakeParts.get(1).x)){
            this.direction=direction;}
        if (this.direction==Direction.UP&&!(direction==Direction.DOWN)&&!(snakeParts.get(0).y==snakeParts.get(1).y)){
            this.direction=direction;}
        if (this.direction==Direction.DOWN&&!(direction==Direction.UP)&&!(snakeParts.get(0).y==snakeParts.get(1).y)){
            this.direction=direction;}
    }

    private void drawSnake(){
        try{
            field[snakeParts.get(0).y][snakeParts.get(0).x]=3;
        for (int i = 1; i < snakeParts.size(); i++) {
                field[snakeParts.get(i).y][snakeParts.get(i).x]=2;
        }
        }catch (ArrayIndexOutOfBoundsException e){
            isGame = false;
            getNewGame();
        }
    }

    private void getNewGame(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        final AlertDialog.Builder builder = new AlertDialog.Builder(SnakeActivity.this, R.style.AlertDialogCustom);
        builder.setTitle(getString(R.string.youLose));
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
                        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        Thread thread = new Thread(){
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                });
            }
        };
        thread.start();

    }

    public void retry(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat1);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this,SnakeActivity.class);
        startActivity(intent);
        finish();
    }

    private GameObject createNewHead(){
        GameObject gameObject = new GameObject(0,0);
        if (direction==Direction.DOWN){
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y + 1);
        }
        else if (direction==Direction.LEFT){
            gameObject = new GameObject(snakeParts.get(0).x-1, snakeParts.get(0).y);
        }
        else if (direction==Direction.RIGHT){
            gameObject = new GameObject(snakeParts.get(0).x+1, snakeParts.get(0).y);
        }
        else if (direction==Direction.UP){
            gameObject = new GameObject(snakeParts.get(0).x, snakeParts.get(0).y - 1);
        }
        return gameObject;
    }

    private void removeTail(){
        field[snakeParts.get(snakeParts.size()-1).y][snakeParts.get(snakeParts.size()-1).x]=0;
        snakeParts.remove(snakeParts.size()-1);
    }

    Timer timer;
    TimerTask timerTask;
    int delay = 500;
    private void game(){
        GameObject head = createNewHead();
        if (checkCollision(head)){
            isGame=false;
            getNewGame();
        }

        snakeParts.add(0,head);
        if (!((snakeParts.get(0).y==mouse.x)&&(snakeParts.get(0).x==mouse.y))){
        removeTail();}else {delay = delay-7;
        score = score+5;
        createMouse();
            final MediaPlayer chpok = MediaPlayer.create(this, R.raw.chpoksnake);
            spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
            if (spref.getBoolean(SAVED_BOOL,false)){
                chpok.start();}}
        drawSnake();
        vyvod();
            timer = new Timer();
            timerTask = new MyTimerTask();
            timer.schedule(timerTask,delay);
    }

    class MyTimerTask extends TimerTask{
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            if (isGame){
            game();
                TextView textView = findViewById(R.id.scoreSnake);
                try{textView.setText(getString((R.string.score))+score);}catch (Exception e){}
                TextView textView2 = findViewById(R.id.maxScoreSnake);
                maxScore = spref.getInt(SAVED_MAX_SCORE, 0);
                if (maxScore<score){maxScore = score;
                    SharedPreferences.Editor ed = spref.edit();
                    ed.putInt(SAVED_MAX_SCORE,maxScore);
                    ed.apply();
                }
                try{textView2.setText(getString((R.string.max_score))+maxScore);}catch (Exception e){}}else {delay=6000000;}
        }
    }

    private void createMouse(){
        int x = (int) (Math.random()*15);
        int y = (int) (Math.random()*15);
        if (field[x][y]==0){
            field[x][y]=1;
            mouse = new Mouse(x,y);
        }else {createMouse();}

    }

    public boolean checkCollision(GameObject gameObject){
        for (GameObject obj : snakeParts) {
            if (gameObject.x == obj.x && gameObject.y == obj.y) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try{mediaPlayer.release();}catch (Exception e){}}

    @Override
    protected void onPause() {
        super.onPause();
        try{mediaPlayer.pause();}catch (Exception e){}}

    @Override
    protected void onResume() {
        super.onResume();
        try{mediaPlayer.start();}catch (Exception e){}}

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
        startActivity(intent);
    }
}