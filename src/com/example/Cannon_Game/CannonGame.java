package com.example.Cannon_Game;

import android.app.Activity;
import android.os.Bundle;
import android.media.AudioManager;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.GestureDetector.SimpleOnGestureListener;

public class CannonGame extends Activity {
    private GestureDetector gestureDetector;
    private CannonView cannonView;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        cannonView = (CannonView) findViewById(R.id.cannonView);

        gestureDetector = new GestureDetector(this, gestureListener);

        setVolumeControlStream(AudioManager.STREAM_MUSIC);
    }

    @Override
    public void onPause(){
        super.onPause();
        cannonView.stopGame();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        cannonView.releaseResources();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        if (action == MotionEvent.ACTION_DOWN || action == MotionEvent.ACTION_MOVE) {
            cannonView.alignCannon(event);
        }

        return gestureDetector.onTouchEvent(event);
    }

    SimpleOnGestureListener gestureListener = new SimpleOnGestureListener() {
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            cannonView.fireCannonball(e);
            return true;
        }
    };
}
