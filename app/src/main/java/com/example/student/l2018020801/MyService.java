package com.example.student.l2018020801;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    String idLove="LOVE";
    NotificationChannel channelLove;
    NotificationManager nm;
    public int NotificationId=321321;
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("msg","this is onCreate");
        nm=(NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT>=26)
        {
            regchannel1();
        }
    }

    @TargetApi(Build.VERSION_CODES.O)
    public  void regchannel1()
    {
        channelLove = new NotificationChannel(
                idLove,
                "Channel Love",
                NotificationManager.IMPORTANCE_HIGH);

        channelLove.setDescription("重要");
        channelLove.enableLights(true);
        channelLove.enableVibration(true);
        nm.createNotificationChannel(channelLove);
    }

    @TargetApi(Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("msg","this is onStartCommand");
        new Thread(){
            @Override
            public void run() {
                super.run();
                try {
                    for(int i=0;i<=20;i++) //關掉程式會重新再跑一次
                    {
                        Thread.sleep(1000);
                        Log.d("msg","Delay:i="+i);
                    }

                    //當迴圈跑完執行Notification
                    Notification.Builder builder;
                    if(Build.VERSION.SDK_INT>=26)
                    {
                        builder =new Notification.Builder(MyService.this,idLove);
                    }
                    else
                    {
                        builder =new Notification.Builder(MyService.this);
                    }

                    Intent it =new Intent(MyService.this,MainActivity.class);
                    it.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP |
                            Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    PendingIntent pi=PendingIntent.getActivity(MyService.this,123,it,
                            PendingIntent.FLAG_UPDATE_CURRENT);//requestCode for cancel

                    builder.setContentTitle("測試");
                    builder.setContentText("Content");
                    if(Build.VERSION.SDK_INT >= 26)
                    {
                        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
                    }
                    else
                    {
                        builder.setSmallIcon(R.mipmap.ic_launcher);
                    }

                    builder.setAutoCancel(true);
                    builder.setContentIntent(pi);


                    Notification notify = builder.build();
                    nm.notify(NotificationId,notify);



                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();




        /* 如果只寫這樣沒有多重執行緒，在程式中還是可以跑，但是一離開就不行了
        try {
            for(int i=0;i<=20;i++)
                    {
                        Thread.sleep(1000);
                        Log.d("msg","Delay:i="+i);
                    }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
