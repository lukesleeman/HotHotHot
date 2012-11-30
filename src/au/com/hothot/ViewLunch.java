package au.com.hothot;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import au.com.hothot.domain.Order;

public class ViewLunch extends Activity {

	private Order order;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlunch);


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
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case R.id.about:
            Toast.makeText(getApplicationContext(), "HotHot - By Luke Sleeman", Toast.LENGTH_LONG).show();
            System.out.println("Tett");
            return true;
        case R.id.share:
        	Intent shareIntent=new Intent(android.content.Intent.ACTION_SEND);

        	shareIntent.setType("text/plain");
        	shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.share_title);
        	shareIntent.putExtra(Intent.EXTRA_TEXT, order.toString());
        	
        	startActivity(shareIntent);
            return true;
        default:
            return super.onOptionsItemSelected(item);
        }
    }
}
