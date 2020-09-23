package boris.narodov.catsinsquares;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class Real_online extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private final String SAVED_BOOL2 = "saved_bool2";
    private int idimage=0;
    private Animation anim;
    private String firstPlayer;
    private String secondPlayer;
    private ArrayList<String> players = new ArrayList<>();
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference("OnlineGame");
    private String matrixtoString="";
    private DatabaseReference image = database.getReference("OnlineGameImage");
    private DatabaseReference player = database.getReference("playerName");
    private boolean isMove;
    private HashMap<Integer,Integer> matrixImage = new HashMap<>();
    private MatrixWithEverything matrixWithEverything = new MatrixWithEverything();
    private int[][] matrix=matrixWithEverything.getMatrix();
    private int[][] buttons ={{R.id.firstFirstRealOnline,R.id.firstSecondRealOnline,R.id.firstThirdRealOnline,R.id.firstFourthRealOnline},
            {R.id.secondFirstRealOnline,R.id.secondSecondRealOnline,R.id.secondThirdRealOnline,R.id.secondFourthRealOnline},
            {R.id.thirdFirstRealOnline,R.id.thirdSecondRealOnline,R.id.thirdThirdRealOnline,R.id.thirdFourthRealOnline},
            {R.id.fourthFirstRealOnline,R.id.fourthSecondRealOnline,R.id.fourthThirdRealOnline,R.id.fourthFourthRealOnline}};
    private MediaPlayer mediaPlayer;
    private static int SIGN_IN_CODE = 1;
    private String namePlayer;

    public void put(){
        matrixImage.put(11,R.drawable.a11);
        matrixImage.put(12,R.drawable.a12);
        matrixImage.put(13,R.drawable.a13);
        matrixImage.put(14,R.drawable.a14);
        matrixImage.put(21,R.drawable.a21);
        matrixImage.put(22,R.drawable.a22);
        matrixImage.put(23,R.drawable.a23);
        matrixImage.put(24,R.drawable.a24);
        matrixImage.put(31,R.drawable.a31);
        matrixImage.put(32,R.drawable.a32);
        matrixImage.put(33,R.drawable.a33);
        matrixImage.put(34,R.drawable.a34);
        matrixImage.put(41,R.drawable.a41);
        matrixImage.put(42,R.drawable.a42);
        matrixImage.put(43,R.drawable.a43);
        matrixImage.put(44,R.drawable.a44);
        matrixImage.put(77,R.drawable.firstplayer);
        matrixImage.put(88,R.drawable.secondplayer);
    }

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


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==SIGN_IN_CODE){
            if (resultCode==RESULT_OK){
               makeGame();
            }else{
                finish();
            }
        }
    }


    @Override
    protected synchronized void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_real_online);

        idimage=0;
        //allButtons(false);

        TextView textView =(TextView) findViewById(R.id.textViewRealOnline);
        try {
            textView.setText(getText(R.string.firstStep));}catch (Exception e){}

        Thread forSound = new Thread(sound);

        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            forSound.start();}

        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
            makeGame();
        }else {
            makeGame();
        }

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);

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

        anim = AnimationUtils.loadAnimation(this, R.anim.anim);

//D:\ProgramFiles\Nox\bin\nox_adb.exe connect 127.0.0.1:62001
    }

private void makeGame(){
    try{
namePlayer = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        player.push().setValue(namePlayer);
        checkUser();} catch (Exception e){}
        try{
        firstPlayer=players.get(0);
            Log.i("players",players.get(0)+" first");}catch(Exception e){}
  try{
        secondPlayer=players.get(1);
      Log.i("players",players.get(1)+" second");} catch(Exception e){}
    put();
try{
    if (namePlayer.equals(firstPlayer)){
        isMove = true;
        //allButtons(isMove);
        matrixWithEverything.random();
        vyvod();
        findViewById(R.id.secondSecondRealOnline).setEnabled(false);
        findViewById(R.id.secondThirdRealOnline).setEnabled(false);
        findViewById(R.id.thirdSecondRealOnline).setEnabled(false);
        findViewById(R.id.thirdThirdRealOnline).setEnabled(false);
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                matrixtoString = matrixtoString + matrix[i][j];
            }
        }
        myRef.push().setValue(matrixtoString);
        matrixtoString="";
    }}catch (Exception e){}
try {
    if (namePlayer.equals(secondPlayer)) {
        isMove = false;
        //allButtons(isMove);
        read();
    }
}catch (Exception e){}

}

public void checkUser(){
    player.addChildEventListener(new ChildEventListener() {
        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            String gamer = dataSnapshot.getValue(String.class);
            if (!players.contains(gamer)){
            players.add(gamer);}
        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });
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
                imageButton.setImageResource(matrixImage.get(matrix[i][j]));
            }
        }
    }

    String setText=null;
    private  int count=1;


    public synchronized void talk(View view) {
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}

        int a;

//        if (count==1){
//            findViewById(R.id.secondSecondRealOnline).setEnabled(true);
//            findViewById(R.id.secondThirdRealOnline).setEnabled(true);
//            findViewById(R.id.thirdSecondRealOnline).setEnabled(true);
//            findViewById(R.id.thirdThirdRealOnline).setEnabled(true);}

        boolean bool=matrixWithEverything.usloviyeWin(matrix);
        if (!bool) {
            a = proverka(view.getId());
            if (idimage == 0) {
                idimage = a;
            }
            if (matrixWithEverything.uslovieZam(a, idimage)) {
                step(a, count);
                count++;
                idimage = a;//комментить когда бот
            } else {
                Toast.makeText(this, getString(R.string.condition), Toast.LENGTH_SHORT).show();
            }
        }
        okno();

read();
    }

    public void read(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);

                StringBuffer buffer = new StringBuffer(msg);
                matrix[0][0]=Integer.parseInt(buffer.substring(0,2));
                matrix[0][1]=Integer.parseInt(buffer.substring(2,4));
                matrix[0][2]=Integer.parseInt(buffer.substring(4,6));
                matrix[0][3]=Integer.parseInt(buffer.substring(6,8));
                matrix[1][0]=Integer.parseInt(buffer.substring(8,10));
                matrix[1][1]=Integer.parseInt(buffer.substring(10,12));
                matrix[1][2]=Integer.parseInt(buffer.substring(12,14));
                matrix[1][3]=Integer.parseInt(buffer.substring(14,16));
                matrix[2][0]=Integer.parseInt(buffer.substring(16,18));
                matrix[2][1]=Integer.parseInt(buffer.substring(18,20));
                matrix[2][2]=Integer.parseInt(buffer.substring(20,22));
                matrix[2][3]=Integer.parseInt(buffer.substring(22,24));
                matrix[3][0]=Integer.parseInt(buffer.substring(24,26));
                matrix[3][1]=Integer.parseInt(buffer.substring(26,28));
                matrix[3][2]=Integer.parseInt(buffer.substring(28,30));
                matrix[3][3]=Integer.parseInt(buffer.substring(30));
                vyvod();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    public void read2(){
        image.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                isMove = !isMove;
                //allButtons(isMove);
                idimage = dataSnapshot.getValue(Integer.class);
                ImageButton imageButtonExample = findViewById(R.id.imageButton29RealOnline);
                try{imageButtonExample.startAnimation(anim);
                    imageButtonExample.setImageResource(matrixImage.get(idimage));}catch(Exception e){}
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
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
                if (matrix[i][j]==a&&matrix[i][j]!=77&&matrix[i][j]!=88&&namePlayer.equals(secondPlayer)){//n%2==0&&
                    int prom = matrix[i][j];
                    matrix[i][j]=88;
                    image.push().setValue(prom);
                    //int b = numberForOnline;//matrixImage.get(prom);
                    TextView textView = findViewById(R.id.textViewRealOnline);
                    ImageButton enabled =findViewById(buttons[i][j]);
                        read2();
                    try{enabled.startAnimation(anim);}catch(Exception e){}
                    //enabled.setEnabled(false);
                    try{textView.setText(getString(R.string.redGo));}catch (Exception e){}
                    vyvod();
                    for (int i1=0;i1<4;i1++){
                        for (int j1=0;j1<4;j1++){
                            matrixtoString = matrixtoString + matrix[i1][j1];
                        }
                    }
                    myRef.push().setValue(matrixtoString);
                    matrixtoString="";
                    if (matrixWithEverything.usloviyeWin(matrix)) {
                        if (matrixWithEverything.counter(count) == 1) {
                            try{textView.setText(getString(R.string.redWin));}catch (Exception e){}
                        } else if (matrixWithEverything.counter(count) == 2) {
                            try{textView.setText(R.string.blueWin);}catch (Exception e){}
                        }
                    }
                }
                else if (matrix[i][j]==a&&matrix[i][j]!=77&&matrix[i][j]!=88&&namePlayer.equals(firstPlayer)){//&&n%2!=0
                    int prom = matrix[i][j];
                    matrix[i][j]=77;
                    image.push().setValue(prom);
                    //int b = numberForOnline;
                    //int b = matrixImage.get(prom);
                    TextView textView = findViewById(R.id.textViewRealOnline);
                            read2();
                    try{textView.setText(getString(R.string.blueGo));}catch (Exception e){}
                    ImageButton enabled =findViewById(buttons[i][j]);
                    try{enabled.startAnimation(anim);}catch(Exception e){}

                    //enabled.setEnabled(false);
                    vyvod();
                    for (int i1=0;i1<4;i1++){
                        for (int j1=0;j1<4;j1++){
                            matrixtoString = matrixtoString + matrix[i1][j1];
                        }
                    }
                    myRef.push().setValue(matrixtoString);
                    matrixtoString="";
                    if (matrixWithEverything.usloviyeWin(matrix)) {
                        if (matrixWithEverything.counter(count) == 1) {
                            try{textView.setText(getString(R.string.redWin));}catch (Exception e){}
                        } else if (matrixWithEverything.counter(count) == 2) {
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
        //finish();
        Intent intent = new Intent(getApplicationContext(),Real_online.class);
        startActivity(intent);
        finish();
    }

    public void okno(){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        boolean bool;
        int k=0;
        for (int i=0;i<4;i++){
            for (int j=0;j<4;j++){
                if (matrix[i][j]/10!=idimage/10&&matrix[i][j]%10!=idimage%10||matrix[i][j]==77||matrix[i][j]==88){
                    k++;
                }
            }
        }
        bool=matrixWithEverything.usloviyeWin(matrix);
        if (k==16){
            bool=true;
        }
        if (bool){
            if (matrixWithEverything.counter(count-1)==1){
                TextView textView = findViewById(R.id.textView3);
                try{
                textView.setText(getString(R.string.redWin));}catch (Exception e){}
                setText=getString(R.string.redWin);

            }else if (matrixWithEverything.counter(count-1)==2){
                TextView textView = findViewById(R.id.textView3);
                try{
                textView.setText(getString(R.string.blueWin));}catch (Exception e){}
                setText=getString(R.string.blueWin);
            }


            AlertDialog.Builder builder = new AlertDialog.Builder(Real_online.this,R.style.AlertDialogCustom);

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
        if (namePlayer.equals(firstPlayer)){
        myRef.removeValue();
        image.removeValue();
        player.removeValue();}
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

    public void startGame(View view){
        if(players.size()!=2||players.get(0).equals(players.get(1))||spref.getBoolean(SAVED_BOOL2,false)){
            Log.i(TAG, "zashel");
            spref = getSharedPreferences("forsound",Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = spref.edit();
            ed.putBoolean(SAVED_BOOL2, false);
            ed.apply();
            retry();
        }
        else {
            makeGame();
            findViewById(R.id.imageButton29RealOnline).setEnabled(false);
            //allButtons(true);
            for (int i = 0; i < players.size(); i++) {
                Log.i(TAG, players.get(i));
            }
       }
    }
    private static final String TAG = "LOLKEK";

//    public void allButtons(boolean bool){
//        findViewById(R.id.firstFirstRealOnline).setEnabled(bool);
//        findViewById(R.id.firstSecondRealOnline).setEnabled(bool);
//        findViewById(R.id.firstThirdRealOnline).setEnabled(bool);
//        findViewById(R.id.firstFourthRealOnline).setEnabled(bool);
//        findViewById(R.id.secondFirstRealOnline).setEnabled(bool);
//        findViewById(R.id.secondSecondRealOnline).setEnabled(bool);
//        findViewById(R.id.secondThirdRealOnline).setEnabled(bool);
//        findViewById(R.id.secondFourthRealOnline).setEnabled(bool);
//        findViewById(R.id.thirdFirstRealOnline).setEnabled(bool);
//        findViewById(R.id.thirdSecondRealOnline).setEnabled(bool);
//        findViewById(R.id.thirdThirdRealOnline).setEnabled(bool);
//        findViewById(R.id.thirdFourthRealOnline).setEnabled(bool);
//        findViewById(R.id.fourthFirstRealOnline).setEnabled(bool);
//        findViewById(R.id.fourthSecondRealOnline).setEnabled(bool);
//        findViewById(R.id.fourthThirdRealOnline).setEnabled(bool);
//        findViewById(R.id.fourthFourthRealOnline).setEnabled(bool);
//    }
}
