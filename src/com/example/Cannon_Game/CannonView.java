package com.example.Cannon_Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by sean on 10/1/14.
 */
public class CannonView extends SurfaceView implements SurfaceHolder.Callback{
    private CannonThread cannonThread;
    private Activity activity;
    private boolean dialogIsDisplayed = false;

    public static final int TARGET_PIECES = 7;
    public static final int MISS_PENALTY = 2;
    public static final int HIT_REWARD = 3;

    private boolean gameOver; // is the game over?
    private double timeLeft; // the amount of time left in seconds
    private int shotsFired; // the number of shots the user has fired
    private double totalTimeElapsed; // the number of seconds elapsed

    private Line blocker; // start and end points of the blocker
    private int blockerDistance; // blocker distance from left
    private int blockerBeginning; // blocker distance from top
    private int blockerEnd; // blocker bottom edge distance from top
    private int initialBlockerVelocity; // initial blocker speed multiplier
    private float blockerVelocity; // blocker speed multiplier during game

    private Line target; // start and end points of the target
    private int targetDistance; // target distance from left
    private int targetBeginning; // target distance from top
    private double pieceLength; // length of a target piece
    private int targetEnd; // target bottom's distance from top
    private int initialTargetVelocity; // initial target speed multiplier
    private float targetVelocity; // target speed multiplier during game

    private int lineWidth; // width of the target and blocker
    private boolean[] hitStates; // is each target piece hit?
    private int targetPiecesHit; // number of target pieces hit (out of 7)

    private Point cannonball; // cannonball image's upper-left corner
    private int cannonballVelocityX; // cannonball's x velocity
    private int cannonballVelocityY; // cannonball's y velocity
    private boolean cannonballOnScreen; // is the cannonball on the screen
    private int cannonballRadius; // cannonball radius
    private int cannonballSpeed; // cannonball speed
    private int cannonBaseRadius; // cannon base radius
    private int cannonLength; // cannon barrel length
    private Point barrelEnd; // the endpoint of the cannon's barrel
    private int screenWidth; // width of the screen
    private int screenHeight; // height of the screen

    private static final int TARGET_SOUND_ID = 0;
    private static final int CANNON_SOUND_ID = 1;
    private static final int BLOCKER_SOUND_ID = 2;
    private SoundPool soundPool;
    private Map<Integer, Integer> soundMap; // maps IDs to SoundPool

    private Paint textPaint; // Paint used to draw text
    private Paint cannonballPaint; // Paint used to draw the cannonball
    private Paint cannonPaint; // Paint used to draw the cannon
    private Paint blockerPaint; // Paint used to draw the blocker
    private Paint targetPaint; // Paint used to draw the target
    private Paint backgroundPaint; // Paint used to clear the drawing area

    public CannonView(Context context, AttributeSet attrs) {
        super(context, attrs);
        activity = (Activity) context;

        getHolder().addCallback(this);

        blocker = new Line();
        target = new Line();
        cannonball = new Point();

        hitStates = new boolean[TARGET_PIECES];

        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);

        soundMap = new HashMap<Integer, Integer>();
        soundMap.put(TARGET_SOUND_ID, soundPool.load(context, R.raw.target_hit, 1));
        soundMap.put(CANNON_SOUND_ID, soundPool.load(context, R.raw.cannon_fire, 1));
        soundMap.put(BLOCKER_SOUND_ID, soundPool.load(context, R.raw.blocker_hit, 1));

        textPaint = new Paint();
        cannonPaint = new Paint();
        cannonballPaint = new Paint();
        blockerPaint = new Paint();
        targetPaint = new Paint();
        backgroundPaint = new Paint();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        screenWidth = w;
        screenHeight = h;

        cannonBaseRadius = h/18;
        cannonLength = w/8;

        cannonballRadius = w/36;
        cannonballSpeed = w * 3 /2;

        lineWidth = w /24;

        blockerDistance = w*5/8;
        blockerBeginning = h /8;
        blockerEnd = h * 3/8;
        initialBlockerVelocity = h/2;
        blocker.start = new Point(blockerDistance, blockerBeginning);
        blocker.end = new Point(blockerDistance, blockerEnd);

        targetDistance = w *7/8;
        targetBeginning = h/8;
        targetEnd = h * 7/8;
        pieceLength = (targetEnd - targetBeginning) / TARGET_PIECES;
        initialTargetVelocity = -h / 4;
        target.start = new Point(targetDistance, targetBeginning);
        target.end = new Point(targetDistance, targetEnd);

        barrelEnd = new Point(cannonLength, h/2);

        textPaint.setTextSize(w/20);
        textPaint.setAntiAlias(true);
        cannonPaint.setStrokeWidth(lineWidth * 1.5f);
        blockerPaint.setStrokeWidth(lineWidth);
        targetPaint.setStrokeWidth(lineWidth);
        backgroundPaint.setColor(Color.WHITE);

        newGame();
    }

    public void newGame() {
        for (int i =0; i < TARGET_PIECES; ++i)
            hitStates[i] = false;

        targetPiecesHit =0;
        blockerVelocity = initialBlockerVelocity;
        targetVelocity = initialTargetVelocity;
        timeLeft = 10;
        cannonballOnScreen = false;
        shotsFired =0;
        totalTimeElapsed = 0.0;
        blocker.start.set(blockerDistance, blockerBeginning);
        blocker.end.set(blockerDistance, blockerEnd);
        target.start.set(targetDistance, targetBeginning);
        target.end.set(targetDistance, targetEnd);

        if (gameOver) {
            gameOver = false;
            cannonThread = new CannonThread(getHolder());
            cannonThread.start();
        }
    }
}
