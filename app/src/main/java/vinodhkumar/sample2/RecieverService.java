package vinodhkumar.sample2;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class RecieverService extends Service {

    public static final long RECIEVER_INTERVAL = 20 * 1000; // 20 seconds
    public static int temp = 1;
    //NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);

    // run on another Thread to avoid crash
    private Handler mHandler = new Handler();
    // timer handling
    private Timer mTimer = null;

    public RecieverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // Let it continue running until it is stopped.
        //Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        Log.d("RecieverService: ","onStartCommand()");
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(this, "Service Destroyed", Toast.LENGTH_LONG).show();
        Log.d("RecieverService: ","onDestroy()");
    }

    @Override
    public void onCreate() {
        // cancel if already existed
        if(mTimer != null) {
            mTimer.cancel();
        } else {
            // recreate new
            mTimer = new Timer();
        }
        //Dummy log creation method to explain services.
        mTimer. scheduleAtFixedRate(new TimeDisplayTimerTask(), 0, RECIEVER_INTERVAL);
    }

    class TimeDisplayTimerTask extends TimerTask {

        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {

                   /*
                   The following function call must be inside appropriate logic
                    */
                    if(true)
                    displayNotification("Buddy tracker notification msg!");
                }

            });
        }

        private void displayNotification(String msg){
                //Accept button in notification
                Intent acceptIntent = new Intent(getApplicationContext(), AcceptButtonListener.class);
                PendingIntent pendingAcceptIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, acceptIntent, 0);
                NotificationCompat.Action acceptAction = new NotificationCompat.Action.Builder(0, "Accept", pendingAcceptIntent).build();

                //Reject button in notification
                Intent rejectIntent = new Intent(getApplicationContext(), RejectButtonListener.class);
                PendingIntent pendingRejectIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, rejectIntent, 0);
                NotificationCompat.Action rejectAction = new NotificationCompat.Action.Builder(0, "Reject", pendingRejectIntent).build();


            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                Notification notification = new NotificationCompat.Builder(getApplicationContext())
                        .setContentTitle("BuddyTracker")
                        .setContentText(msg)
                        .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
                        .addAction(acceptAction)
                        .addAction(rejectAction)
                        .setAutoCancel(true).build();

                notification.flags |= Notification.FLAG_AUTO_CANCEL;

                int unique_number = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
                //rejectIntent.putExtra("ACCEPT",unique_number);
                //acceptIntent.putExtra("ACCEPT",unique_number);
                notificationManager.notify(unique_number, notification);

                Log.d("RECIEVER TASK : ", getDateTime());

        }

//        private void displayNotification1(){
//
//            Intent acceptIntent = new Intent(getApplicationContext(),AcceptButtonListener.class);
//            PendingIntent pendingAcceptIntent = PendingIntent.getBroadcast(getApplicationContext(),0,acceptIntent,0);
//
//            NotificationManager notif=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
//            Notification notify=new Notification.Builder(getApplicationContext())
//                    .setContentTitle("BuddyTracker")
//                    .setContentText("BuddyTracker push notification!")
//                    .setSmallIcon(R.drawable.cast_ic_notification_small_icon)
//                    .build();
//            temp++;
//            notify.flags |= Notification.FLAG_AUTO_CANCEL;
//            //notify.flags |= Notification.FLAG_NO_CLEAR;
//            int unique_number = (int) ((new Date().getTime() / 1000L) % Integer.MAX_VALUE);
//            notif.notify(unique_number, notify);
//
//
//
//            // display toast
//            //Toast.makeText(getApplicationContext(), getDateTime(),Toast.LENGTH_SHORT).show();
//            Log.d("RECIEVER TASK : ",getDateTime());
//        }
        private String getDateTime() {
            // get date time in custom format
            SimpleDateFormat sdf = new SimpleDateFormat("[yyyy/MM/dd - HH:mm:ss]");
            return sdf.format(new Date());
        }


    }

    public static class AcceptButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            /*
            Logic for Accept button of push notification
             */

            Toast.makeText(context, "Request Accepted", Toast.LENGTH_SHORT).show();
        }

    }

    public static class RejectButtonListener extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {

            /*
            Logic for Reject button of push notification
             */

            Toast.makeText(context, "Request rejected", Toast.LENGTH_SHORT).show();
        }

    }


}

