package au.com.lukesleeman.hothot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import au.com.lukesleeman.hothot.domain.Order;

public class SelectLunch extends Activity {
	
	private Order order;
	private SeekBar numPeopleSeek, numDrinkingSeek;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.selectlunch);
        
        // Construct our order objects
        order = new Order(0.75, 1000);
        order.addMenuItem("Fish slices & vegitables in hot & spicy soup", 2680);
        order.addMenuItem("Fish flavoured Eggplant", 1680);
        order.addMenuItem("Spicy cumin ribs", 2380);
        order.addMenuItem("Kung Pao prawns", 2380);
        order.addMenuItem("Stirfried beef skewers", 2180);
        order.addMenuItem("Ants climbing trees", 1680);
        order.addMenuItem("Pork threads with tea tree mushrooms", 2080);
        order.addMenuItem("Dry Stirfied beans with pork mince", 1680);
        
        // Setup our seek bars
        numPeopleSeek = (SeekBar)findViewById(R.id.numberOfPeople);
        numDrinkingSeek = (SeekBar)findViewById(R.id.numberDrinking);
        numDrinkingSeek.setMax(20);
        numPeopleSeek.setMax(20);
        
        OnSeekBarChangeListener l = new OnSeekBarChangeListener() {
        	
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateNumberLabels();
			}
		};
		numDrinkingSeek.setOnSeekBarChangeListener(l);
		numPeopleSeek.setOnSeekBarChangeListener(l);
		numPeopleSeek.setProgress(5);
		
		// Setup button click
		Button calculate = (Button)findViewById(R.id.calculate);
		calculate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				calculate();
			}
		});
		
		updateNumberLabels();
    }
    
    private void updateNumberLabels(){
    	// Make sure we dont have more people drinking than are attending!
    	if(numDrinkingSeek.getProgress() > numPeopleSeek.getProgress()){
    		numDrinkingSeek.setProgress(numPeopleSeek.getProgress());
    	}
    	if(numPeopleSeek.getProgress() == 0){
    		numPeopleSeek.setProgress(1);
    	}
    	
    	int numDrinking = numDrinkingSeek.getProgress();
    	int numPeople = numPeopleSeek.getProgress();
    	
    	TextView numPeopleLabel = (TextView)findViewById(R.id.numberOfPeopleLabel);
    	numPeopleLabel.setText(getString(R.string.number_of_people) + " " + numPeople);
    	
    	TextView numDrinkingLabel = (TextView)findViewById(R.id.numberDrinkingLabel);
    	numDrinkingLabel.setText(getString(R.string.number_drinking) + " " + numDrinking);
    }
    
    private void calculate(){
    	// Get our values and push back into the order object
    	order.setNumPeople(numPeopleSeek.getProgress());
    	order.setNumPeopleDrinking(numDrinkingSeek.getProgress());
    	
    	CheckBox hungryCheck = (CheckBox)findViewById(R.id.hungry);
    	order.setHungry(hungryCheck.isChecked());
    	
    	// Calculate the order
    	order.calculateOrder();
    	
    	// Now display it
    	Intent intent = new Intent(this, ViewLunch.class);
    	intent.putExtra("au.com.hothot.domain.Order", order);
    	startActivity(intent);
    }
    
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.selectlunchmenu, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.about:
            Intent intent = new Intent(this, About.class);
        	startActivity(intent);
            return true;
        case R.id.share:
        	Intent shareIntent=new Intent(android.content.Intent.ACTION_SEND);

        	shareIntent.setType("text/plain");
        	shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.share);
        	shareIntent.putExtra(Intent.EXTRA_TEXT, order.toString());
        	
        	startActivity(shareIntent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
    
    @Override
    public void onAttachedToWindow() {
    	super.onAttachedToWindow();
    	Window window = getWindow();
    	window.setFormat(PixelFormat.RGBA_8888);
    }

}