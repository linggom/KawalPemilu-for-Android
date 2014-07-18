package com.antares.kawalpemilu.client;

import java.text.DecimalFormat;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import com.antares.kawalpemilu.MainActivity;
import com.antares.kawalpemilu.PemiluApplication;
import com.antares.kawalpemilu.R;
import com.antares.kawalpemilu.model.CompileResult;

import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.util.Log;

public class AlarmReceiver extends WakefulBroadcastReceiver {

	private AlarmManager alarmMgr;
	private PendingIntent alarmIntent;


	@Override
	public void onReceive(Context arg0, Intent arg1) {
		Log.e(getClass().getSimpleName(), "Alarm Wake");
		//notify to user
		VoteService mService = RestClient.getVoteService();
		mService.getHie("0", new Callback<String[][]>() {
			
			@Override
			public void success(String[][] r, Response arg1) {
				if (r == null ){
				}
				else if (r.length == 0){
				}
				else{

					String [] a  = new String[]{"0","Total Rekapitulasi","0","0","0","0","0","0","0","0"};
					for (int i = 0; i < r.length; i++) {
						int x = CompileResult.toInt(r[i][2]);
						a[0] = "-29081990";
						a[1] = "Total Rekapitulasi";
						a[2]= ""+(CompileResult.toInt(a[2]) + x) ;



						x = CompileResult.toInt(r[i][3]);
						a[3]= ""+ (CompileResult.toInt(a[3]) + x) ;;

						x = CompileResult.toInt(r[i][4]);
						a[4]= ""+ (CompileResult.toInt(a[4]) + x) ;;

						x = CompileResult.toInt(r[i][5]);
						a[5]= ""+ (CompileResult.toInt(a[5]) + x) ;;

						x = CompileResult.toInt(r[i][6]);
						a[6]= ""+ (CompileResult.toInt(a[6]) + x) ;;

						x = CompileResult.toInt(r[i][7]);
						a[7]= ""+ (CompileResult.toInt(a[7]) + x) ;;

						x = CompileResult.toInt(r[i][8]);
						a[8]= ""+ (CompileResult.toInt(a[8]) + x) ;;

						x = CompileResult.toInt(r[i][9]);
						a[9]= ""+ (CompileResult.toInt(a[9]) + x) ;;
					}
					CompileResult res = new CompileResult(a);
					SharedPreferences sp = PemiluApplication.getApplication().getSharedPreferences("PEMILU", Context.MODE_PRIVATE);
					int p = sp.getInt("prabowo", -1);
					int j = sp.getInt("jokowi", -1);
					
					if (p != res.getResultCandidate1() || j != res.getResultCandidate2()){
						showNotification(1234567, "Update", "Hasil Total Rekapitulasi" , res);
						sp.edit().putInt("prabowo", res.getResultCandidate1()).commit();
						sp.edit().putInt("jokowi", res.getResultCandidate2()).commit();
						
					}
					
					


				}
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});


	}
	private static NotificationCompat.Builder mNotificationBuilder;
	private static Uri alarmSound;
	public static void showNotification(int notificationId, String title, String message,  CompileResult res) {

		if (mNotificationBuilder == null) {
			mNotificationBuilder = new NotificationCompat.Builder(PemiluApplication.getApplication());
		}

		mNotificationBuilder.setSmallIcon(R.drawable.ic_launcher);
		mNotificationBuilder.setContentTitle(title);
		mNotificationBuilder.setContentText(message);
		mNotificationBuilder.setOngoing(false);
		mNotificationBuilder.setAutoCancel(true);
		NotificationCompat.InboxStyle inboxStyle =
		        new NotificationCompat.InboxStyle();
		String[] events = new String[6];
		// Sets a title for the Inbox style big view
		inboxStyle.setBigContentTitle("Update Rekapitulasi Pemilu");
		// Moves events into the big view
		inboxStyle.addLine("Prabowo-Hatta : " + String.format("%,d", res.getResultCandidate1()));
		inboxStyle.addLine("Jokowi-JK : " + String.format("%,d", res.getResultCandidate2()));
		double result = ((double)res.getPending() / res.getTotal())*100;
		DecimalFormat df = new DecimalFormat("#.00");
		String angleFormated = df.format(result);
		inboxStyle.addLine("Total Suara Masuk : " + ""+String.format("%,d", res.getPending()) +"/" + String.format("%,d", res.getTotal()) + "("+angleFormated+"%)");		
		// Moves the big view style object into the notification object.
		mNotificationBuilder.setStyle(inboxStyle);

		if (alarmSound == null) {
			alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		}
		mNotificationBuilder.setSound(alarmSound);

		Intent chatIntent = new Intent(PemiluApplication.getApplication(), MainActivity.class);
//		chatIntent.putExtra(ContentConstants.KEY_JID, roomJid);
//		chatIntent.putExtra(ContentConstants.KEY_REALNAME, title);
//		chatIntent.putExtra(ContentConstants.KEY_MESSAGE_TYPE, messageType);

		TaskStackBuilder stackBuilder = TaskStackBuilder.create(PemiluApplication.getApplication());
		stackBuilder.addParentStack(MainActivity.class);
		stackBuilder.addNextIntent(chatIntent);
		PendingIntent resultPendingIntent =
				stackBuilder.getPendingIntent(
						0,
						PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT
						);
		mNotificationBuilder.setContentIntent(resultPendingIntent);

		((NotificationManager)PemiluApplication.getApplication().getSystemService(Context.NOTIFICATION_SERVICE)).notify(notificationId, mNotificationBuilder.build());

	}


	public void setAlarm(Context context){
		alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
		Intent intent = new Intent(context, AlarmReceiver.class);
		alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);


		alarmMgr.setInexactRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP,  
				60*1000, 3*60*60*1000, alarmIntent); //every 3 hour update data 
	}
}
