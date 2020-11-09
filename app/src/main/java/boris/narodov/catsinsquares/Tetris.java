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
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Tetris extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_MAX_SCORE = "saved_max_score_tetris";
    private int[][] idField = {{R.id.tetris1_1,R.id.tetris1_2,R.id.tetris1_3,R.id.tetris1_4,R.id.tetris1_5,R.id.tetris1_6,R.id.tetris1_7,R.id.tetris1_8,R.id.tetris1_9,R.id.tetris1_10,R.id.tetris1_11,R.id.tetris1_12},
            {R.id.tetris2_1,R.id.tetris2_2,R.id.tetris2_3,R.id.tetris2_4,R.id.tetris2_5,R.id.tetris2_6,R.id.tetris2_7,R.id.tetris2_8,R.id.tetris2_9,R.id.tetris2_10,R.id.tetris2_11,R.id.tetris2_12},
            {R.id.tetris3_1,R.id.tetris3_2,R.id.tetris3_3,R.id.tetris3_4,R.id.tetris3_5,R.id.tetris3_6,R.id.tetris3_7,R.id.tetris3_8,R.id.tetris3_9,R.id.tetris3_10,R.id.tetris3_11,R.id.tetris3_12},
            {R.id.tetris4_1,R.id.tetris4_2,R.id.tetris4_3,R.id.tetris4_4,R.id.tetris4_5,R.id.tetris4_6,R.id.tetris4_7,R.id.tetris4_8,R.id.tetris4_9,R.id.tetris4_10,R.id.tetris4_11,R.id.tetris4_12},
            {R.id.tetris5_1,R.id.tetris5_2,R.id.tetris5_3,R.id.tetris5_4,R.id.tetris5_5,R.id.tetris5_6,R.id.tetris5_7,R.id.tetris5_8,R.id.tetris5_9,R.id.tetris5_10,R.id.tetris5_11,R.id.tetris5_12},
            {R.id.tetris6_1,R.id.tetris6_2,R.id.tetris6_3,R.id.tetris6_4,R.id.tetris6_5,R.id.tetris6_6,R.id.tetris6_7,R.id.tetris6_8,R.id.tetris6_9,R.id.tetris6_10,R.id.tetris6_11,R.id.tetris6_12},
            {R.id.tetris7_1,R.id.tetris7_2,R.id.tetris7_3,R.id.tetris7_4,R.id.tetris7_5,R.id.tetris7_6,R.id.tetris7_7,R.id.tetris7_8,R.id.tetris7_9,R.id.tetris7_10,R.id.tetris7_11,R.id.tetris7_12},
            {R.id.tetris8_1,R.id.tetris8_2,R.id.tetris8_3,R.id.tetris8_4,R.id.tetris8_5,R.id.tetris8_6,R.id.tetris8_7,R.id.tetris8_8,R.id.tetris8_9,R.id.tetris8_10,R.id.tetris8_11,R.id.tetris8_12},
            {R.id.tetris9_1,R.id.tetris9_2,R.id.tetris9_3,R.id.tetris9_4,R.id.tetris9_5,R.id.tetris9_6,R.id.tetris9_7,R.id.tetris9_8,R.id.tetris9_9,R.id.tetris9_10,R.id.tetris9_11,R.id.tetris9_12},
            {R.id.tetris10_1,R.id.tetris10_2,R.id.tetris10_3,R.id.tetris10_4,R.id.tetris10_5,R.id.tetris10_6,R.id.tetris10_7,R.id.tetris10_8,R.id.tetris10_9,R.id.tetris10_10,R.id.tetris10_11,R.id.tetris10_12},
            {R.id.tetris11_1,R.id.tetris11_2,R.id.tetris11_3,R.id.tetris11_4,R.id.tetris11_5,R.id.tetris11_6,R.id.tetris11_7,R.id.tetris11_8,R.id.tetris11_9,R.id.tetris11_10,R.id.tetris11_11,R.id.tetris11_12},
            {R.id.tetris12_1,R.id.tetris12_2,R.id.tetris12_3,R.id.tetris12_4,R.id.tetris12_5,R.id.tetris12_6,R.id.tetris12_7,R.id.tetris12_8,R.id.tetris12_9,R.id.tetris12_10,R.id.tetris12_11,R.id.tetris12_12},
            {R.id.tetris13_1,R.id.tetris13_2,R.id.tetris13_3,R.id.tetris13_4,R.id.tetris13_5,R.id.tetris13_6,R.id.tetris13_7,R.id.tetris13_8,R.id.tetris13_9,R.id.tetris13_10,R.id.tetris13_11,R.id.tetris13_12},
            {R.id.tetris14_1,R.id.tetris14_2,R.id.tetris14_3,R.id.tetris14_4,R.id.tetris14_5,R.id.tetris14_6,R.id.tetris14_7,R.id.tetris14_8,R.id.tetris14_9,R.id.tetris14_10,R.id.tetris14_11,R.id.tetris14_12},
            {R.id.tetris15_1,R.id.tetris15_2,R.id.tetris15_3,R.id.tetris15_4,R.id.tetris15_5,R.id.tetris15_6,R.id.tetris15_7,R.id.tetris15_8,R.id.tetris15_9,R.id.tetris15_10,R.id.tetris15_11,R.id.tetris15_12}};
    private final int FIELD_HOR=17;
    private final int FIELD_VER=12;
    private int[][] field = new int[FIELD_HOR][FIELD_VER];
    private Display display;
    private Point size;
    private int cc=15;
    private int score=0;
    private int maxScore=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tetris);

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);

        display = getWindowManager().getDefaultDisplay();
        size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        cc =(int) ((b)*4/105);
        int btnSize = b/8;
        int c = Math.max(a, b);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        TextView textScore = findViewById(R.id.scoreTetris);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);
        ImageView backTetris = findViewById(R.id.backgroundTetris);
        ImageButton left = findViewById(R.id.tetrisLeft);
        ImageButton right = findViewById(R.id.tetrisRight);
        Bitmap bitBtn = BitmapFactory.decodeResource(getResources(),R.drawable.nothing);
        Bitmap resizedBtn = Bitmap.createScaledBitmap(bitBtn,btnSize,btnSize,true);

        try {
            backTetris.setImageBitmap(resized);
            textScore.setText(maxScore+"/"+score);
            left.setImageBitmap(resizedBtn);
            right.setImageBitmap(resizedBtn);
        }catch (Exception e){}


        game();
    }

    TetrisObjects objects = new TetrisObjects();
    ArrayList figures = new ArrayList();
boolean isGame=true;
    private void figureNext() {
        figures = objects.getFigure();
        List<GameObject> tetrisObject = figures;
        for (int j = 0; j < tetrisObject.size(); j++) {
            try{
            if ((tetrisObject.get(j).y == 16)||(field[tetrisObject.get(j).y+1][tetrisObject.get(j).x]==3)){
                isGame=false;
            }
            }catch (Exception e){}
        }
        if (isGame) {
            for (int j = 0; j < tetrisObject.size(); j++) {
                field[tetrisObject.get(j).y][tetrisObject.get(j).x] = 0;
            }
            for (int i = 0; i < tetrisObject.size(); i++) {
                tetrisObject.set(i, new GameObject(tetrisObject.get(i).x, tetrisObject.get(i).y + 1));
                field[tetrisObject.get(i).y][tetrisObject.get(i).x] = 2;
            }
        }else {
            for (int j = 0; j < tetrisObject.size(); j++) {
                field[tetrisObject.get(j).y][tetrisObject.get(j).x] = 3;
            }
            isGame = true;
            score=score+tetrisObject.size();
            objects=new TetrisObjects();
        }
    }

    public void vyvod(){
        display.getSize(size);
        Bitmap bitNothing = BitmapFactory.decodeResource(getResources(),R.drawable.nothing);
        Bitmap resizedNothing = Bitmap.createScaledBitmap(bitNothing,cc,cc,true);
        Bitmap bitCat = BitmapFactory.decodeResource(getResources(),getTileColor(tileColor[(int) (Math.random()*tileColor.length)]));
        Bitmap resizedCat = Bitmap.createScaledBitmap(bitCat,cc,cc,true);
        for (int i =0; i<15;i++){
            for (int j =0; j<12;j++) {
                ImageView imageView = findViewById(idField[i][j]);
                if (field[i+2][j]==0){
                    imageView.setImageBitmap(resizedNothing);}
                if (field[i+2][j]==2){
                    imageView.setImageBitmap(resizedCat);}
            }
        }
    }
    private int[] tileColor = {2,4,8,16,32,64,128,256,512,1024,2048,4096,8192,16384,32768,65536,131072};
    public int getTileColor(int value){

        switch (value){
            case (2):
                return R.drawable.catpuzzle2;
            case (4):
                return R.drawable.catpuzzle4;
            case (8):
                return R.drawable.catpuzzle8;
            case (16):
                return R.drawable.catpuzzle16;
            case (32):
                return R.drawable.catpuzzle32;
            case (64):
                return R.drawable.catpuzzle64;
            case (128):
                return R.drawable.catpuzzle128;
            case (256):
                return R.drawable.catpuzzle256;
            case (512):
                return R.drawable.catpuzzle512;
            case (1024):
                return R.drawable.catpuzzle1024;
            case (2048):
                return R.drawable.catpuzzle2048;
            case (4096):
                return R.drawable.catpuzzle4096;
            case (8192):
                return R.drawable.catpuzzle8192;
            case (16384):
                return R.drawable.catpuzzle16384;
            case (32768):
                return R.drawable.catpuzzle32768;
            case (65536):
                return R.drawable.catpuzzle65536;
        }
        return R.drawable.catpuzzle131072;
    }

    Timer timer;
    TimerTask timerTask;
    int delay = 500;
    private void game(){
        if (isNoLose()){
        figureNext();
        vyvod();
        remove();
        timer = new Timer();
        timerTask = new Tetris.MyTimerTask();
        timer.schedule(timerTask,delay);}
        else {getNewGame();}
    }

    private boolean isNoLose(){
        for (int i = 0;i<FIELD_VER;i++){
            if (field[2][i]==3){
                return false;
            }
        }
        return true;
    }

    private boolean isRemove(int i){
        for (int j = 0;j<FIELD_VER;j++){
            if (field[i][j]!=3){
                return false;
            }
        }
        return true;
    }

    private void remove(){
        for (int i = 0; i<FIELD_HOR; i++){
            if (isRemove(i)){
                score=score+12;
                delay=delay-10;
                delete(i);
                downTo(i);
                remove();
            }
        }
    }

    private void delete(int i){
        for (int j = 0; j<FIELD_VER;j++){
            field[i][j]=0;}
    }

    private void downTo(int i){
//        for (int k = i;k>1;k--){
//            for (int j = 0;j<FIELD_VER;j++){
//                field[k][j]=field[k-1][j];
//                field[k-1][j]=0;
//            }
//        }
    }

    class MyTimerTask extends TimerTask{
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    game();
                    TextView textView = findViewById(R.id.scoreTetris);
                    maxScore = spref.getInt(SAVED_MAX_SCORE, 0);
                    if (maxScore<score){maxScore = score;
                        SharedPreferences.Editor ed = spref.edit();
                        ed.putInt(SAVED_MAX_SCORE,maxScore);
                        ed.apply();
                    }
                    try{textView.setText(maxScore+"/"+score);}catch (Exception e){}
                }
            });
        }
    }

    public void left(View view){
        boolean isLeft = true;
        figures = objects.getFigure();
        List<GameObject> tetrisObject = figures;
        for (int j = 0; j < tetrisObject.size(); j++) {
            try{
                if ((!isGame)||(tetrisObject.get(j).x==0)||(field[tetrisObject.get(j).y][tetrisObject.get(j).x-1]==3)){
                    isLeft=false;
                }
            }catch (Exception e){}
        }
        if (isLeft) {
            for (int j = 0; j < tetrisObject.size(); j++) {
                field[tetrisObject.get(j).y][tetrisObject.get(j).x] = 0;
            }
            for (int i = 0; i < tetrisObject.size(); i++) {
                tetrisObject.set(i, new GameObject(tetrisObject.get(i).x-1, tetrisObject.get(i).y));
                field[tetrisObject.get(i).y][tetrisObject.get(i).x] = 2;
            }
            vyvod();
        }
    }
    public void right(View view){
        boolean isRight = true;
        figures = objects.getFigure();
        List<GameObject> tetrisObject = figures;
        for (int j = 0; j < tetrisObject.size(); j++) {
            try{
                if ((!isGame)||(tetrisObject.get(j).x==11)||(field[tetrisObject.get(j).y][tetrisObject.get(j).x+1]==3)){
                    isRight=false;
                }
            }catch (Exception e){}
        }
        if (isRight) {
            for (int j = 0; j < tetrisObject.size(); j++) {
                field[tetrisObject.get(j).y][tetrisObject.get(j).x] = 0;
            }
            for (int i = 0; i < tetrisObject.size(); i++) {
                tetrisObject.set(i, new GameObject(tetrisObject.get(i).x+1, tetrisObject.get(i).y));
                field[tetrisObject.get(i).y][tetrisObject.get(i).x] = 2;
            }
            vyvod();
        }
    }

    private void getNewGame(){
//        if (isAd&&mInterstitialAd.isLoaded()) {
//            mInterstitialAd.show();
//        }
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        final AlertDialog.Builder builder = new AlertDialog.Builder(Tetris.this, R.style.AlertDialogCustom);
        builder.setTitle(getString(R.string.youLose));
        builder.setMessage(getString(R.string.Playagain));
        try{builder.setIcon(R.drawable.logo);}catch (Exception e){}
        builder.setCancelable(false);
        builder.setNeutralButton(getString(R.string.yes),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        retry();
                    }
                });
//        if (!isAd){
//            builder.setNegativeButton(getString(R.string.continue_btn),new DialogInterface.OnClickListener() {
//                public void onClick(DialogInterface dialog, int id) {
//                    continueGame();
//                }
//            });}
        builder.setPositiveButton(getString(R.string.menu),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
//                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
//                        if (spref.getBoolean(SAVED_BOOL, false)) {
//                            mp.start();
//                        }
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
                        try{
                            alert.show();}catch (Exception e){}
                    }
                });
            }
        };
        thread.start();

    }

    public void retry(){
//        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
//        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
//        if (spref.getBoolean(SAVED_BOOL,false)){
//            mp.start();}
        Intent intent = new Intent(this,Tetris.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //try{mediaPlayer.release();}catch (Exception e){}
        }

    @Override
    protected void onPause() {
        super.onPause();
        //try{mediaPlayer.pause();}catch (Exception e){}
        }

    @Override
    protected void onResume() {
        super.onResume();
       // try{mediaPlayer.start();}catch (Exception e){}
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), newMenuActivity.class);
        startActivity(intent);
        finish();
    }
}