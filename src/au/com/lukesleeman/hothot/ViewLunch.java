package au.com.lukesleeman.hothot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.widget.TextView;
import au.com.lukesleeman.hothot.domain.Order;

/**
 * Activity which displays the order to the user.
 * 
 * @author lukesleeman
 *
 */
public class ViewLunch extends Activity {

	private Order order;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlunch);

     // Animate in
        overridePendingTransition(R.anim.pull_in_from_left, R.anim.hold);
        

        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        // Try and pull an order out of our intent
        order = (Order)getIntent().getExtras().get("au.com.hothot.domain.Order");
        
        TextView price = (TextView)findViewById(R.id.price);
        price.setText(order.getPriceWithoutBeer());
        
        TextView beerPrice = (TextView)findViewById(R.id.beerPrice);
        beerPrice.setText(order.getPriceWithBeer());
        
        TextView orderTextView = (TextView)findViewById(R.id.orderTextView);
        orderTextView.setText(order.toString());
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.viewlunchmenu, menu);
        return true;
    }
    
    @Override
    protected void onPause() {
    	// Animate out
        overridePendingTransition(R.anim.hold, R.anim.push_out_to_left);
        super.onPause();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
            return true;
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
