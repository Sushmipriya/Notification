package com.example.alarm;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity 
{

	PendingIntent pendingIntent;
	AlarmManager alarmManager;
	BroadcastReceiver mReceiver;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		RegisterAlarmBroadcast();
	}

	public void onClickSetAlarm(View v) 
	{
		alarmManager.set(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + 10000, pendingIntent);
	}

	private void RegisterAlarmBroadcast() 
	{
		Log.i("Alarm Example:RegisterAlarmBroadcast()",
				"Going to register Intent.RegisterAlramBroadcast");
		mReceiver = new BroadcastReceiver() 
		{
			private static final String TAG = "Alarm Example Receiver";

			@Override
			public void onReceive(Context context, Intent intent) 
			{
				Log.i(TAG,
						"BroadcastReceiver::OnReceive() >>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				Toast.makeText(context,
						"Congrats!. Your Alarm time has been reached",
						Toast.LENGTH_LONG).show();
			}
		};

		registerReceiver(mReceiver, new IntentFilter(
				"com.techblogon.alarmexample"));
		pendingIntent = PendingIntent.getBroadcast(this, 0, new Intent(
				"com.techblogon.alarmexample"), 0);
		alarmManager = (AlarmManager) (this
				.getSystemService(Context.ALARM_SERVICE));
	}

	private void UnregisterAlarmBroadcast() 
	{
		alarmManager.cancel(pendingIntent);
		getBaseContext().unregisterReceiver(mReceiver);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{

		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	protected void onDestroy() 
	{
		unregisterReceiver(mReceiver);
		super.onDestroy();
	}
}
