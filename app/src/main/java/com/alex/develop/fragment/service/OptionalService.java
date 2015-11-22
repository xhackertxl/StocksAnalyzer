package com.alex.develop.fragment.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.Vibrator;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;

public class OptionalService extends Service {
    /**
     * 用于Handler里的消息类型
     */
    public static final int MSG_SAY_HELLO = 1;
    /**
     * 这个Messenger可以关联到Service里的Handler，Activity用这个对象发送Message给Service，Service通过Handler进行处理。
     */
    final Messenger mMessenger = new Messenger(new IncomingHandler());

    private MediaPlayer mMediaPlayer = null;
    private Vibrator vibrator;
    //private Target target; // 关注对象的信息

    // 状态栏提示要用的
    private NotificationManager m_Manager;
    private PendingIntent m_PendingIntent;
    private Notification m_Notification;


    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();


    }

    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub

        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            // 要释放资源，不然会打开很多个MediaPlayer
            mMediaPlayer.release();
        }
        if (vibrator != null) {
            vibrator.cancel();
        }
        super.onDestroy();
    }

    /**
     * 一启动就响铃，震动提醒
     */
    @SuppressLint("NewApi")
    @SuppressWarnings("deprecation")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        // TODO Auto-generated method stub
        Context mContext = getApplicationContext();
        {
            vibrator = (Vibrator) mContext.getSystemService(mContext.VIBRATOR_SERVICE);
            // 等待3秒，震动3秒，从第0个索引开始，一直循环
            //0一直循环  -1 不循环
            vibrator.vibrate(new long[]{100,100}, -1);
        }

        return super.onStartCommand(intent, flags, startId);
    }


    /**
     * 在Service处理Activity传过来消息的Handler
     */
    class IncomingHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_SAY_HELLO:
                    Toast.makeText(getApplicationContext(), "hello!", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    super.handleMessage(msg);
            }
        }
    }


    public void PlaySound(final Context context) {

//            {
//                vibrator = (Vibrator)mContext.getSystemService(mContext.VIBRATOR_SERVICE);
//                // 等待3秒，震动3秒，从第0个索引开始，一直循环
//                //0一直循环  -1 不循环
//                vibrator.vibrate(new long[]{3000, 3000}, -1);
//            }

//            {
//                AudioManager audioManager = (AudioManager)mContext.getSystemService(Context.AUDIO_SERVICE);
//                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
//                PlaySound(mContext);
//            }

        Log.e("ee", "正在响铃");
        // 使用来电铃声的铃声路径
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        // 如果为空，才构造，不为空，说明之前有构造过
        if (mMediaPlayer == null)
            mMediaPlayer = new MediaPlayer();
        try {
            mMediaPlayer.setDataSource(context, uri);
            mMediaPlayer.setLooping(true); //循环播放
            mMediaPlayer.prepare();
            mMediaPlayer.start();
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public class MsgBinder extends Binder {
        /**
         * 获取当前Service的实例
         *
         * @return
         */
        public OptionalService getService() {
            return OptionalService.this;
        }
    }

    /**
     * 当Activity绑定Service的时候，通过这个方法返回一个IBinder，Activity用这个IBinder创建出的Messenger，就可以与Service的Handler进行通信了
     */
    @Override
    public IBinder onBind(Intent intent) {
        //Toast.makeText(getApplicationContext(), "binding", Toast.LENGTH_SHORT).show();
       // mMessenger.getBinder();
        return new LocalBinder();
    }


    public class LocalBinder extends Binder {
        OptionalService getService() {
            // Return this instance of LocalService so clients can call public methods
            return OptionalService.this;
        }
    }
}
