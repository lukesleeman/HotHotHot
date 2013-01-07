package au.com.lukesleeman.hothot;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.ImageButton;

/**
 * Give some details about the app and allows the user to call the restaurant
 * 
 * @author lukesleeman
 */
public class About extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about);


        getActionBar().setDisplayHomeAsUpEnabled(true);
        
        ImageButton call = (ImageButton)findViewById(R.id.call_button);
        call.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String uri = "tel:03 9650 8589";
				 Intent intent = new Intent(Intent.ACTION_CALL);
				 intent.setData(Uri.parse(uri));
				 startActivity(intent);
			}
		});
      
    }
    
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
        case android.R.id.home:
            onBackPressed();
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
