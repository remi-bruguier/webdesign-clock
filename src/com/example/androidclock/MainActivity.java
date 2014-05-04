package com.example.androidclock;

import java.util.List;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DigitalClock;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidclock.ShakeDetector.OnShakeListener;

public class MainActivity extends Activity {
	
	// init AndroidClockObject
	private WebDesignClock mWebDesignClock = new WebDesignClock();
	private List<Object> mCurrentSettings = mWebDesignClock.currentSettings;
	
	// shake
	private SensorManager mSensorManager;
	private Sensor mAccelerometer;
	private ShakeDetector mShakeDetector;
	
	// layout elements
	private RelativeLayout mainLayout;
	private DigitalClock mDigitalClock;
	private TextView mSettingsTextView;
	
	// sound is playing, dont start again
	private boolean soundIsPlaying = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
		mSettingsTextView = (TextView) findViewById(R.id.bgcolor_infos);
		mDigitalClock = (DigitalClock) findViewById(R.id.digitalClock1);
		

		mainLayout.setOnTouchListener(new OnSwipeTouchListener(this) {
		    @Override
		    public void onSwipeLeft() {
		    	UpdateLayout(true,false);
		    }
		    @Override
		    public void onSwipeRight() {
		    	UpdateLayout(false,true);
		    }
		});
		
		mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
    	mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
    	mShakeDetector = new ShakeDetector(new OnShakeListener() {
			@Override
			public void onShake() {
				
				// if !already in the default state
				if(mWebDesignClock.mCurrentColor>1){
					
					// resets the current settings
					mWebDesignClock.mCurrentColor = -1;
					mWebDesignClock.mCurrentFont = -1;
			 		playSound(R.raw.reset);
					UpdateLayout(true,true);
					
				}

				
			}
		});
		
		
		UpdateLayout(true,true);
		
	}
	
	 /*
	  * Updates the layout background color and infos textview
	  */
	 private void UpdateLayout(Boolean updateBgColor, Boolean updateFont){
		 
		 	// default background
		 	BgColor defaultColor = (BgColor) mCurrentSettings.get(0);
		
		 	// gets the new settings
		 	List<Object> settings 	= mWebDesignClock.UpdateLayout(updateBgColor,updateFont);
		 	BgColor newBgColor 		= (BgColor) settings.get(0);
		 	Font newFont 			= (Font) settings.get(1);

		 	// animates the background color change + sound
		 	if(updateBgColor){
		 		
		 		
			 	Integer colorFrom 	= Color.parseColor(defaultColor.color);
			 	Integer colorTo 	= Color.parseColor(newBgColor.color);
			 	
			 	ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
			 	colorAnimation.addUpdateListener(new AnimatorUpdateListener() {

			 	    @Override
			 	    public void onAnimationUpdate(ValueAnimator animator) {
			 	    	mainLayout.setBackgroundColor((Integer)animator.getAnimatedValue());
			 	    }

			 	});
			 	colorAnimation.setDuration(1000);
			 	colorAnimation.start();
			 	
		 	}
		 	
		 	if(updateFont){
		 		
			 	// creates the new typeface and sets it 
			 	Typeface tf = Typeface.createFromAsset(getAssets(), newFont.file);
			 	mSettingsTextView.setTypeface(tf);
			 	mDigitalClock.setTypeface(tf);
			 	
		 	}
		 	
		 	// updates the text
			mSettingsTextView.setText(newFont.name+" - "+newBgColor.name);
		 	
		 	// updates currentSettings
		 	mCurrentSettings.clear();
		 	mCurrentSettings.add(newBgColor);
		 	

		 
	 }
	 
	 private void playSound(int effect){
		 
		 if(!soundIsPlaying){
			 MediaPlayer player = MediaPlayer.create(this, effect);
			 player.start();
			 soundIsPlaying = true;
			 player.setOnCompletionListener(new OnCompletionListener() {
					@Override
					public void onCompletion(MediaPlayer mp) {
						mp.release();
						soundIsPlaying = false;
					}
			 });
		 }
		 

		
		 
	 }
	 
    @Override
    public void onResume(){
      	super.onResume();
    	mSensorManager.registerListener(mShakeDetector, mAccelerometer, 
    			SensorManager.SENSOR_DELAY_UI);
    }
    
    @Override
    public void onPause(){
    	super.onPause();
    	mSensorManager.unregisterListener(mShakeDetector);
    }
	 

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}


}
