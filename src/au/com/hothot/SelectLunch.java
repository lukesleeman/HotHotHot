package au.com.hothot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.SeekBar.OnSeekBarChangeListener;
import au.com.hothot.domain.Order;

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
        order.addMenuItem("Hot and spicy fish pot", 2400);
        order.addMenuItem("Eggplant", 1100);
        order.addMenuItem("Spicy cumin ribs", 1700);
        order.addMenuItem("Kung Pao prawns", 1200);
        order.addMenuItem("Beef toothpics", 1200);
        order.addMenuItem("Ants", 1200);
        order.addMenuItem("Tea flavoured mushrooms", 1200);
        order.addMenuItem("Beans", 1200);
        
        // Setup our seek bars
        numPeopleSeek = (SeekBar)findViewById(R.id.numberOfPeople);
        numDrinkingSeek = (SeekBar)findViewById(R.id.numberDrinking);
        numDrinkingSeek.setMax(20);
        numPeopleSeek.setMax(20);
        
        OnSeekBarChangeListener l = new OnSeekBarChangeListener() {
        	
//			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {}
			
//			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {}
			
//			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
				updateNumberLabels();
			}
		};
		numDrinkingSeek.setOnSeekBarChangeListener(l);
		numPeopleSeek.setOnSeekBarChangeListener(l);
		
		// Setup button click
		Button calculate = (Button)findViewById(R.id.calculate);
		calculate.setOnClickListener(new OnClickListener() {
			
//			@Override
			public void onClick(View v) {
				calculate();
			}
		});
    }
    
    private void updateNumberLabels(){
    	// Make sure we dont have more people drinking than are attending!
    	if(numDrinkingSeek.getProgress() > numPeopleSeek.getProgress()){
    		numDrinkingSeek.setProgress(numPeopleSeek.getProgress());
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

}