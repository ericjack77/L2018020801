package com.example.student.l2018020801;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("msg","this is onCreate");
    }

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
