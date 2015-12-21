package org.opencv.samples.colorblobdetect;

import java.util.ArrayList;
import java.util.List;

import org.opencv.samples.framework.FrameWork;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class Setting extends Activity{
Spinner rows,cols,speed;
	
	private void initRows(){
		List<String> list = new ArrayList<String>();
		final ArrayAdapter<String> adapter; 
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);    
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		rows.setAdapter(adapter);    
		rows.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				FrameWork.getInstance().setRow(Integer.valueOf(adapter.getItem(arg2)));
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	private void initCols(){
		List<String> list = new ArrayList<String>();
		final ArrayAdapter<String> adapter; 
		list.add("1");
		list.add("2");
		list.add("3");
		list.add("4");
		list.add("5");
		list.add("6");
		list.add("7");
		list.add("8");
		
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);    
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		cols.setAdapter(adapter);    
		cols.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				FrameWork.getInstance().setCol(Integer.valueOf(adapter.getItem(arg2)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}
	
	private void initSpeed(){
		List<String> list = new ArrayList<String>();
		final ArrayAdapter<String> adapter; 
		list.add("10");
		list.add("20");
		list.add("30");
		list.add("40");
		list.add("50");
		list.add("60");
		list.add("70");
		list.add("80");
		
		 adapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, list);    
		 adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
		 speed.setAdapter(adapter);    
		 speed.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				
				FrameWork.getInstance().setSpeed(Integer.valueOf(adapter.getItem(arg2)));
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
			
		});
		
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		rows=(Spinner)findViewById(R.id.rows);
		cols=(Spinner)findViewById(R.id.cols);
		speed=(Spinner)findViewById(R.id.speed);
		
		initRows();
		initCols();
		initSpeed();
	}
}
