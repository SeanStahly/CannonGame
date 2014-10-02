package com.example.Cannon_Game;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.Paint;
import android.util.AttributeSet;

import java.util.Map;


/**
 * Created by sean on 10/1/14.
 */
public class CannonView {
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

    private Map<Integer, Integer> soundMap; // maps IDs to SoundPool

    private Paint textPaint; // Paint used to draw text
    private Paint cannonballPaint; // Paint used to draw the cannonball
    private Paint cannonPaint; // Paint used to draw the cannon
    private Paint blockerPaint; // Paint used to draw the blocker
    private Paint targetPaint; // Paint used to draw the target
    private Paint backgroundPaint; // Paint used to clear the drawing area

    public CannonView(Context context, AttributeSet attrs) {

    }
}
