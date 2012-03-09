package app.serenity;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.widget.Toast;

public class serenityService extends Service {

	//this boolean is used only to simulate data from sensors
	private static boolean mflag = false;

	private static final int notifyStress = 1337;
	private final IBinder binder = new LocalBinder();
	public class LocalBinder extends Binder {
		serenityService getService() {
			return serenityService.this;
		}}

	@Override
	public IBinder onBind(Intent intent) { 
		// TODO Auto-generated method stub
		return binder;
	}
	@Override

	public void onCreate() {
		super.onCreate();

//		Toast.makeText(this, "Serenity Service Start", Toast.LENGTH_LONG).show();


  
		Thread simulateDataThread = new Thread(null, simulateData, "Wait 10 seconds");
		simulateDataThread.start();



	}

	Runnable  simulateData = new Runnable() {

		public synchronized void run() {
			try {
				wait(20000);
				mflag = true;
				String ticker = "Serenity";
				String title = "Hi Gadi";
				String text = "Click me!";
				sendNotification(ticker, title, text);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} 

	};

	private void sendNotification(String ticker, String title, String text) {
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		//		Instantiate the Notification:
		int icon = R.drawable.icon;
		CharSequence tickerText = ticker;
		long when = System.currentTimeMillis();
		Notification notification = new Notification(icon, tickerText, when);
		//		Define the notification's message and PendingIntent:
		Context context = getApplicationContext();
		CharSequence contentTitle = title;
		CharSequence contentText = text;
		Intent notificationIntent = new Intent(this, RunSerenity.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
		//		Pass the Notification to the NotificationManager:
		mNotificationManager.notify(notifyStress, notification);
	} 


	public boolean getNotificationNeeded(){
		//TODO: add sensors data and logic here.
		return mflag;
	}
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		stopSelf();
	}


}

