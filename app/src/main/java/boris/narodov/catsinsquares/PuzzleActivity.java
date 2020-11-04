package boris.narodov.catsinsquares;

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
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;


public class PuzzleActivity extends AppCompatActivity implements OnSwipeTouchListener.SimpleGestureListener {
    private InterstitialAd mInterstitialAd;
    private OnSwipeTouchListener detector;
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_MAX_SCORE = "saved_max_score";
    private final String SAVED_SCORE = "saved_score";
    private final String SAVED_MATRIX = "saved_matrix";

    private MatrixWithEverything matrixWithEverything = new MatrixWithEverything();
    private int[][] images = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    private int[][] imagesRes = {{0,0,0,0},{0,0,0,0},{0,0,0,0},{0,0,0,0}};
    private int scoreRes=0;
    private int[][] imagesBackground = matrixWithEverything.getImages();
    private  int[][] idImages ={{R.id.firstFirstPuzzle,R.id.firstSecondPuzzle,R.id.firstThirdPuzzle,R.id.firstFourthPuzzle},
            {R.id.secondFirstPuzzle,R.id.secondSecondPuzzle,R.id.secondThirdPuzzle,R.id.secondFourthPuzzle},
            {R.id.thirdFirstPuzzle,R.id.thirdSecondPuzzle,R.id.thirdThirdPuzzle,R.id.thirdFourthPuzzle},
            {R.id.fourthFirstPuzzle,R.id.fourthSecondPuzzle,R.id.fourthThirdPuzzle,R.id.fourthFourthPuzzle}};

    private MediaPlayer mediaPlayer;
    Runnable sound = new Runnable() {
        @Override
        public void run() {

            mediaPlayer = MediaPlayer.create(getBaseContext(),R.raw.puzzlemelody);
            mediaPlayer.setLooping(true);
            if (!mediaPlayer.isPlaying()){
                mediaPlayer.start();}
        }
    };

    private AdView mAdView;
    int score, maxTile;
    int maxScore;
    private int sizeBtn=100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_puzzle);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int a = size.x;
        int b = size.y;
        if (a==0||b==0){
            a=1200;
            b=1200;
        }
        int c = Math.max(a, b);
        sizeBtn = (int)(a/8);
        Bitmap bit = BitmapFactory.decodeResource(getResources(),R.drawable.backgr1);
        Bitmap resized = Bitmap.createScaledBitmap(bit,c,c,true);
        Bitmap bitBtn = BitmapFactory.decodeResource(getResources(),R.drawable.graybutton);
        Bitmap resizedBtn = Bitmap.createScaledBitmap(bitBtn,sizeBtn,sizeBtn,true);

        TextView textScore = findViewById(R.id.score);
        TextView textMaxScore = findViewById(R.id.maxScore);
        ImageView image1 = findViewById(R.id.backgroundPuzzle);
        TableLayout image2 = findViewById(R.id.tablepuzzle);
        ImageButton buttonBack = findViewById(R.id.backStep);
        buttonBack.setEnabled(false);
        Bitmap bitTable = BitmapFactory.decodeResource(getResources(),R.drawable.wooden2);
        Bitmap resizedTable = Bitmap.createScaledBitmap(bitTable,c,c,true);
        Drawable d = new BitmapDrawable(getResources(),resizedTable);
        try {
            textScore.setText(getText(R.string.score));
            textMaxScore.setText(getText(R.string.max_score));
            image1.setImageBitmap(resized);
            image2.setBackground(d);
            buttonBack.setImageBitmap(resizedBtn);
        }catch (Exception e){}

        Thread forSound = new Thread(sound);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL, false)) {
            forSound.start();
        }
        if (spref.getInt(SAVED_SCORE,0)>10){
            getNewGame();
            score=spref.getInt(SAVED_SCORE,0);
            for (int i = 0; i<4;i++){
                for (int j = 0; j<4; j++){
                    images[i][j]= spref.getInt(SAVED_MATRIX+i+j,0);
                }
            }
        }
        else{
        images[(int) (Math.random()*4)][(int) (Math.random()*4)]=(Math.random() < 0.9 ? 2 : 4);
            images[(int) (Math.random()*4)][(int) (Math.random()*4)]=(Math.random() < 0.9 ? 2 : 4);}
        vyvod();
        detector = new OnSwipeTouchListener(this,this);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        mAdView = findViewById(R.id.adViewActivityPuzzle);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-5586713183085646/2290757899");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
    }

    public synchronized void vyvod(){
        for (int i =0; i<4;i++){
            for (int j =0; j<4;j++) {
                ImageView imageView = findViewById(idImages[i][j]);
                if (images[i][j]==0){
                    imageView.setImageResource(imagesBackground[i][j]);
                }else{
                imageView.setImageResource(getTileColor(images[i][j]));}
            }
        }
        SharedPreferences.Editor ed = spref.edit();
        for (int i = 0; i<4;i++){
            for (int j = 0; j<4; j++){
                ed.putInt(SAVED_MATRIX+i+j,images[i][j]);
                ed.apply();
            }
        }
        ed.putInt(SAVED_SCORE,score);
        ed.apply();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent me){
        // Call onTouchEvent of SimpleGestureFilter class
        this.detector.onTouchEvent(me);
        return super.dispatchTouchEvent(me);
    }
    @Override
    public void onSwipe(int direction) {
        ImageButton buttonBack = findViewById(R.id.backStep);
        buttonBack.setEnabled(true);
        scoreRes=score;
        for (int i =0; i<images.length;i++){
            for (int j=0; j<images[i].length;j++){
                imagesRes[i][j] = images[i][j];}}
        switch (direction) {
            case OnSwipeTouchListener.SWIPE_RIGHT : right();
                break;
            case OnSwipeTouchListener.SWIPE_LEFT :  left();
                break;
            case OnSwipeTouchListener.SWIPE_DOWN :  down();
                break;
            case OnSwipeTouchListener.SWIPE_UP :    up();
                break;
        }
    }

    @Override
    public  void onDoubleTap() {}

    public void left() {
        boolean isChanged;
        boolean arrayBeChanged = false;
        final MediaPlayer chpok = MediaPlayer.create(this, R.raw.chpok);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            chpok.start();}
        for (int i =0; i<images.length;i++){
            for (int j=1; j<images[i].length;j++){
                int left = images[i][j - 1];
                int right = images[i][j];

                if (left==0 || right==0) continue;
                if (left != right) continue;

                images[i][j-1] = left + right;
                images[i][j]=0;
                score = score + left;
                maxTile = Math.max(maxTile, left);
                arrayBeChanged = true;
            }
            isChanged = true;
            while (isChanged){
                isChanged = false;
                for (int i1 =0; i1<images.length;i1++){
                for (int j1 = images[i].length - 1; j1 > 0; j1--) {
                    int curr = images[i1][j1];
                    if (curr==0) continue;
                    if (images[i1][j1 - 1]==0) {
                        int swap = images[i1][j1-1];
                        images[i1][j1-1] = images[i1][j1];
                        images[i1][j1] = swap;
                        isChanged = true;
                        arrayBeChanged = true;
                        break;
                    }
                }
            }
            }
        }
        if (arrayBeChanged) {
            Bitmap bitBtn = BitmapFactory.decodeResource(getResources(),R.drawable.greenbutton);
            Bitmap resizedBtn = Bitmap.createScaledBitmap(bitBtn,sizeBtn,sizeBtn,true);
            ImageButton buttonBack = findViewById(R.id.backStep);
            try{buttonBack.setImageBitmap(resizedBtn);}catch (Exception e){}
            isChanged = false;
            for (int i = 0; i<4; i++){
                for (int j = 0; j<4; j++){
                    if (isChanged){break;}
                    int[] a = {1,3,2,0};

                    if (images[a[i]][j] == 0){
                        images[a[i]][j] = Math.random() < 0.9 ? 2:4;
                        isChanged = true;
                    }
                }
            }
        }
vyvod();
        TextView textView = findViewById(R.id.score);
        try{textView.setText(getString((R.string.score))+score);}catch (Exception e){}
        TextView textView2 = findViewById(R.id.maxScore);
        maxScore = spref.getInt(SAVED_MAX_SCORE, 0);
        if (maxScore<score){maxScore = score;
            SharedPreferences.Editor ed = spref.edit();
            ed.putInt(SAVED_MAX_SCORE,maxScore);
            ed.apply();
        }
        try{textView2.setText(getString((R.string.max_score))+maxScore);}catch (Exception e){}

        if (!canMove()){
            if (mInterstitialAd.isLoaded()) {
                mInterstitialAd.show();
            }
            final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
            AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this, R.style.AlertDialogCustom);
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
            AlertDialog alert = builder.create();
            alert.show();
        }
    }

    public void right(){
        rotateClockwise();
        rotateClockwise();
        left();
        rotateClockwise();
        rotateClockwise();
        vyvod();
    }

    public void down(){
        for (int i = 0; i < 3; i++) {rotateClockwise();}
        left();
        rotateClockwise();
        vyvod();
    }

    public void up(){
        rotateClockwise();
        left();
        for (int i = 0; i < 3; i++) {rotateClockwise();}
        vyvod();
    }

    private boolean compressTiles(int[] tiles){
        boolean isChanged = true;
        boolean arrayBeChanged = false;
        while (isChanged){
            isChanged = false;
            for (int i = tiles.length - 1; i > 0; i--) {
                int curr = tiles[i];
                if (curr==0) continue;
                if (tiles[i - 1]==0) {
                    isChanged = true;
                    arrayBeChanged = true;
                    break;
                }
            }
        }
        return arrayBeChanged;
    }

    private boolean mergeTiles(int[] tiles){
        boolean arrayBeChanged = false;

        for (int i = 1; i < tiles.length; i++) {

            int left = tiles[i - 1];
            int right = tiles[i];

            if (left==0 || right==0) continue;
            if (left != right) continue;
            arrayBeChanged = true;
        }
        arrayBeChanged = arrayBeChanged || compressTiles(tiles);
        compressTiles(tiles);
        return arrayBeChanged;
    }

    private synchronized void rotateClockwise() {
        int[][] tmpMatrix = new int[images.length][images.length];
        for (int row = 0; row < images.length; row++) {
            for (int col = 0; col < images[0].length; col++) {
                tmpMatrix[row][col] = images[col][images.length - row - 1];
            }
        }
        for (int row = 0; row < images.length; row++) {
            for (int col = 0; col < images[0].length; col++) {
                images[row][col] = tmpMatrix[row][col];
            }
        }
    }

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

    public void retry(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}
        Intent intent = new Intent(this,PuzzleActivity.class);
        startActivity(intent);
        finish();
    }

    public boolean canMove(){
        for(int i = 0; i < images.length; i++) {
            if(compressTiles(images[i])) return true;
            if(mergeTiles(images[i])) return true;
        }

        int[] tiles = new int[images.length];
        for(int i = 0; i < images[0].length; i++) {
            for (int j = 0; j < images.length; j++) {
                if(images[j][i]==0)return true;
                tiles[j] = images[j][i];
            }
            if(compressTiles(tiles)) return true;
            if(mergeTiles(tiles)) return true;

        }
        return false;
    }

    private void getNewGame(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        AlertDialog.Builder builder = new AlertDialog.Builder(PuzzleActivity.this, R.style.AlertDialogCustom);
        builder.setTitle(getString(R.string.continue_game));
        //builder.setMessage("Download the latest game?");
        try{builder.setIcon(R.drawable.logo);}catch (Exception e){}
        builder.setCancelable(false);
        builder.setNegativeButton(getString(R.string.new_game),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        SharedPreferences.Editor ed = spref.edit();
                        ed.putInt(SAVED_SCORE,0);
                        ed.apply();
                        retry();
                    }
                });
        builder.setPositiveButton(getString(R.string.continue_btn),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int id) {
                        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
                        if (spref.getBoolean(SAVED_BOOL, false)) {
                            mp.start();
                        }
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void backStep(View view){
        Bitmap bitBtn = BitmapFactory.decodeResource(getResources(),R.drawable.graybutton);
        Bitmap resizedBtn = Bitmap.createScaledBitmap(bitBtn,sizeBtn,sizeBtn,true);
        ImageButton buttonBack = findViewById(R.id.backStep);
        try{buttonBack.setImageBitmap(resizedBtn);}catch (Exception e){}
        for (int i =0; i<images.length;i++){
            for (int j=0; j<images[i].length;j++){
                images[i][j] = imagesRes[i][j];}}
        score=scoreRes;
        TextView textView = findViewById(R.id.score);
        try{textView.setText(getString((R.string.score))+score);}catch (Exception e){}
        vyvod();
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