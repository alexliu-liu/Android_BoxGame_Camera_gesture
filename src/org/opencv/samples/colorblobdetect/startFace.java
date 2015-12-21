package org.opencv.samples.colorblobdetect;

import java.util.ArrayList;
import java.util.List;

import org.opencv.samples.framework.FrameWork;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

public class startFace extends Activity implements OnClickListener,OnTouchListener{
	Button start,setting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.startgame);
		start=(Button)findViewById(R.id.start);
		setting=(Button)findViewById(R.id.set);

		start.setOnClickListener(this);
		start.setOnTouchListener(this);
		
		setting.setOnClickListener(this);
		setting.setOnTouchListener(this);
		
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
			Intent intent=new Intent(this,ColorBlobDetectionActivity.class);
			this.startActivity(intent);
			break;
		case R.id.set:
			Intent intent2=new Intent(this,Setting.class);
			this.startActivity(intent2);
			break;
		default:
			break;
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent e) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.start:
			if(e.getAction()==MotionEvent.ACTION_DOWN){
				start.setTextSize(40);
				
			}else if(e.getAction()==MotionEvent.ACTION_UP){
				start.setTextSize(30);
				
			}
			break;
		case R.id.set:
			if(e.getAction()==MotionEvent.ACTION_DOWN){
				setting.setTextSize(40);
				
			}else if(e.getAction()==MotionEvent.ACTION_UP){
				setting.setTextSize(30);
				
			}
			break;
		default:
			break;
		}
		return false;
	}
}
