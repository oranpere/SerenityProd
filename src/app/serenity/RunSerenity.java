package app.serenity;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.os.IBinder;
import android.widget.Toast;
import app.serenity.serenityService.LocalBinder;

public class RunSerenity extends Activity {

	// Field:
	private static final String HELLO = "Good morining Gadi.\nI see that you are feeling uncomfortable.\n" +
	"Please take three deep breathes, count to ten and relax ;)";
	private static serenityService mService;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		Intent intent=new Intent(this,serenityService.class);
        // AUTO CREATE: creates the service and gives it an importance so that it won't be killed
        // unless any process bound to it (our activity in this case) is killed to
    	startService(intent);
		bindService(intent, serviceConn, Context.BIND_AUTO_CREATE);
	}
	  
	@Override
	protected void onResume(){
		super.onResume();
		
		
//		String s = (mService == null)?"null":"not null";
//		Toast.makeText(this, "on resume mService is: " + s , Toast.LENGTH_LONG).show();
		
		if(mService != null && mService.getNotificationNeeded()){
			callMessageBox();
//			Toast.makeText(this, "inside Notification If", Toast.LENGTH_LONG).show();
//			Toast.makeText(this, String.valueOf(mService.getNotificationNeeded()), Toast.LENGTH_LONG).show();
			}
		
	}
	
	 @Override
	    protected void onDestroy() {
	    	super.onDestroy();
	    	//unbind the service whena ctivity is destroyed
	    	unbindService(serviceConn);
	    }
	
	
	 ServiceConnection serviceConn=new ServiceConnection() {

         /**
         * service unbound, release from memory
         **/
	public void onServiceDisconnected(ComponentName name) {
		//mService=null;
	}

         /**
         * service is bound, start it's work
         **/
	public void onServiceConnected(ComponentName name, IBinder service) {
		mService=((LocalBinder)service).getService();

	}
};



	private void callMessageBox() {
		AlertDialog stressAlert = new AlertDialog.Builder(this).create(); 
		stressAlert.setTitle("Serenity");
		stressAlert.setMessage(HELLO);
		stressAlert.setButton("O.K", new OnClickListener(){

			public void onClick(DialogInterface dialog, int which) {               
				//...
			}
		});

		stressAlert.show();
	}
}

