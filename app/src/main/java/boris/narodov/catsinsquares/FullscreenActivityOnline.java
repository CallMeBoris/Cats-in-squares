package boris.narodov.catsinsquares;

import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivityOnline extends AppCompatActivity {
    private SharedPreferences spref;
    private final String SAVED_BOOL = "saved_bool";
    private Animation anim;
    private static int SIGN_IN_CODE = 1;
    private MatrixWithEverything matrixWithEverything = new MatrixWithEverything();
    private int[][] images = matrixWithEverything.getImages();
    private int[][] matrix = matrixWithEverything.getMatrix();
    private int[][] buttons ={{R.id.firstFirstOnline,R.id.firstSecondOnline,R.id.firstThirdOnline,R.id.firstFourthOnline},
            {R.id.secondFirstOnline,R.id.secondSecondOnline,R.id.secondThirdOnline,R.id.secondFourthOnline},
            {R.id.thirdFirstOnline,R.id.thirdSecondOnline,R.id.thirdThirdOnline,R.id.thirdFourthOnline},
            {R.id.fourthFirstOnline,R.id.fourthSecondOnline,R.id.fourthThirdOnline,R.id.fourthFourthOnline}};
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private String namePlayer;
    private DatabaseReference player = database.getReference("playerName");
    private DatabaseReference image = database.getReference("OnlineGameImage");
    private String firstPlayer;
    private String secondPlayer;
    private List<String> players = new ArrayList<>();
    private HashMap<Integer,Integer> matrixImage = new HashMap<>();
    private DatabaseReference myRef = database.getReference("OnlineGame");
    private String matrixtoString="";
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen_online);

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
        //findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
        if(FirebaseAuth.getInstance().getCurrentUser()==null){

            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(), SIGN_IN_CODE);
            makeGame();
        } else {makeGame();}
        Thread forSound = new Thread(sound);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            forSound.start();}
        anim = AnimationUtils.loadAnimation(this, R.anim.anim);

    }

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
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
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

    public void talk(View view){
        final MediaPlayer mp = MediaPlayer.create(this, R.raw.cat3);
        spref = getSharedPreferences("forsound", Context.MODE_PRIVATE);
        if (spref.getBoolean(SAVED_BOOL,false)){
            mp.start();}

        //for test
        try{view.startAnimation(anim);}catch(Exception e){}
    }

    public void startGame(View view){}

    private void vyvod(){
        for (int i =0; i<4;i++){
            for (int j =0; j<4;j++) {
                ImageButton imageButton = findViewById(buttons[i][j]);
                imageButton.setImageResource(images[i][j]);
            }
        }
    }

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

    public void read(){
        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String msg = dataSnapshot.getValue(String.class);
                Log.i("msg",msg);
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
    ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            try{HashMap data = dataSnapshot.getValue(HashMap.class);
                players.clear();
                assert data != null;
                String[] s = (String[]) data.values().toArray();
                players.addAll(Arrays.asList(s));
            }catch (Exception e){}
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    };

    public void makeGame(){
        try{
            player.addValueEventListener(postListener);
            namePlayer = FirebaseAuth.getInstance().getCurrentUser().getEmail();
            player.push().setValue(namePlayer);
            } catch (Exception e){}
        try{
            firstPlayer=players.get(0);
            Log.i("players",players.get(0)+" first");}catch(Exception e){}
        try{
            secondPlayer=players.get(1);
            Log.i("players",players.get(1)+" second");} catch(Exception e){}
        put();
        try{
            if (namePlayer.equals(firstPlayer)){
                //isMove = true;
                //allButtons(isMove);
                matrixWithEverything.random();
                vyvod();
//                findViewById(R.id.secondSecondRealOnline).setEnabled(false);
//                findViewById(R.id.secondThirdRealOnline).setEnabled(false);
//                findViewById(R.id.thirdSecondRealOnline).setEnabled(false);
//                findViewById(R.id.thirdThirdRealOnline).setEnabled(false);
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
                //isMove = false;
                //allButtons(isMove);
                read();
            }
        }catch (Exception e){}
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (namePlayer.equals(firstPlayer)||true){
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
        if (namePlayer.equals(firstPlayer)||true){
            myRef.removeValue();
            image.removeValue();
            player.removeValue();}
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}