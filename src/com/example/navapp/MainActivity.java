package com.example.navapp;

import java.util.ArrayList;

import com.polites.android.GestureImageView;

import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Toast;

public class MainActivity extends ActionBarActivity implements ActionBar.OnNavigationListener,OnTouchListener {
	ActionBar ac;
	Position src,dest;
	boolean srcset=false,destset=false;
	String[] locations ;
	int hMultiplier,wMultiplier;
	// Title navigation Spinner data
		private ArrayList<SpinnerNavItem> navSpinner;

		// Navigation adapter
		private TitleNavigationAdapter adapter;
		GestureImageView g;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);			   
	    setContentView(R.layout.activity_main);
	    g=(GestureImageView)findViewById(R.id.image);
	  //  g.setClickable(true);		
	    ac = getSupportActionBar();
	   // ac.setDisplayShowTitleEnabled(false);
        ac.setTitle("Cvent Navigation");
		// Enabling Spinner dropdown navigation
		ac.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		// Spinner title navigation data
		navSpinner = new ArrayList<SpinnerNavItem>();
		//navSpinner.add(new SpinnerNavItem("Local", R.drawable.ic_location));
		navSpinner.add(new SpinnerNavItem("Find",R.drawable.ic_action_search));
		navSpinner
				.add(new SpinnerNavItem("My Places", R.drawable.ic_my_places));
		//navSpinner.add(new SpinnerNavItem("Checkins", R.drawable.ic_checkin));
		navSpinner.add(new SpinnerNavItem("People", R.drawable.ic_latitude));

		// title drop down adapter
		adapter = new TitleNavigationAdapter(getApplicationContext(),
				navSpinner);
		/*g.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new AlertDialog.Builder(MainActivity.this)
				.setMessage("Clicked!")
				.setTitle("Set Your Location")
				.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();												
					}
				})
				.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
					}
				})
				.create().show();
			}
		});*/
		// assigning the spinner navigation
		DisplayMetrics dsp = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dsp);
		hMultiplier= dsp.heightPixels;
		wMultiplier=dsp.widthPixels;		
		ac.setListNavigationCallbacks(adapter, this);
		  locationdb db= new locationdb(MainActivity.this);
	 	    db.open();
	 	    db.createentry("Anshu", new Position(50.0 * wMultiplier/480,50.0 * hMultiplier/854));
	 	    db.createentry("Vienna", new Position(200.0 * wMultiplier/480,200.0 * hMultiplier/854));
	 	    db.close(); 
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

	@Override
	public boolean onNavigationItemSelected(int arg0, long arg1) {
		// TODO Auto-generated method stub
		locationdb db = new locationdb(MainActivity.this);
		db.open();
		switch(arg0)
		{		
		case 1:
		{
			src=new Position(db.getlocation("Anshu").getPosX(),db.getlocation("Anshu").getPosY());
			srcset=true;
			dest=new Position(db.getlocation("Vienna").getPosX(),db.getlocation("Vienna").getPosY());
			destset=true;
			break;
		//	Toast.makeText(MainActivity.this, "" + dest.getPosX(), Toast.LENGTH_SHORT).show();
		}
		case 2:
		{				
		src=new Position(db.getlocation("Anshu").getPosX(),db.getlocation("Anshu").getPosY());
		srcset=true;
		destset=false;
		break;
	//	Toast.makeText(MainActivity.this, "" + src.getPosX(), Toast.LENGTH_SHORT).show();
		}
		}
		db.close();
		if(srcset && destset)
			g.redrawdest(src.getPosX(),src.getPosY(),dest.getPosX(),dest.getPosY());
		else if(srcset)
			g.redrawsource(src.getPosX(), src.getPosY());
			
		return false;
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
}
