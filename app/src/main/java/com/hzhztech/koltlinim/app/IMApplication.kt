package com.hzhztech.koltlinim.app

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import android.support.v4.app.NotificationCompat
import cn.bmob.v3.Bmob
import com.hyphenate.chat.EMClient
import com.hyphenate.chat.EMMessage
import com.hyphenate.chat.EMOptions
import com.hyphenate.chat.EMTextMessageBody
import com.hzhztech.koltlinim.BuildConfig
import com.hzhztech.koltlinim.R
import com.hzhztech.koltlinim.adapter.EMMessageListenerAdapter
import com.hzhztech.koltlinim.ui.activity.ChatActivity
import com.hzhztech.koltlinim.ui.activity.MainActivity

class IMApplication:Application() {

    companion object {
        //lateinit 表示后面才完成初始化(onCreate的时候完成)
        lateinit var instance: IMApplication
    }

    val soundPool = SoundPool(2,AudioManager.STREAM_MUSIC,0)
    val duan by lazy {
        soundPool.load(instance, R.raw.duan,0)
    }
    val yulu by lazy {
        soundPool.load(instance,R.raw.yulu,0)
    }

    private val messageListener = object :EMMessageListenerAdapter() {
        override fun onMessageReceived(p0: MutableList<EMMessage>?) {
            //收到消息之后 如果是在前台则播放短的提示音
            //收到消息之后 如果是在后台则播放长的声音
            if(isForeground()) {
                soundPool.play(duan,1f,1f,0,0,1f)
            }else {
                soundPool.play(yulu,1f,1f,0,0,1f)
                //在后台 收到消息后 弹出通知栏
                showNotification(p0)
            }
        }

    }

    private fun showNotification(p0: MutableList<EMMessage>?) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        p0?.forEach {
            var contentText = getString(R.string.no_text_message)
            if(it.type == EMMessage.Type.TXT) {
                contentText = (it.body as EMTextMessageBody).message
            }
            val intent = Intent(this,ChatActivity::class.java)
            intent.putExtra("username", it.conversationId())
            val pendingInt = PendingIntent.getActivity(this,0,intent, PendingIntent.FLAG_UPDATE_CURRENT)
            if(Build.VERSION.SDK_INT >= 26) {
                //当sdk版本大于26
                var id = "channel_1"
                var description = "143"
                var importance = NotificationManager.IMPORTANCE_LOW
                var channel = NotificationChannel(id, description, importance);
                notificationManager.createNotificationChannel(channel);
                var notification = Notification.Builder(instance, id)
                    .setCategory(Notification.CATEGORY_MESSAGE)
                    .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                    .setSmallIcon(R.mipmap.ic_contact)
                    .setContentTitle(getString(R.string.receive_new_message))
                    .setContentText(contentText)
                    .setContentIntent(pendingInt)
                    .setAutoCancel(true)
                    .build();
                notificationManager.notify(1, notification);
            } else {
                //当sdk版本小于26
                var notification = NotificationCompat.Builder(instance)
                    .setLargeIcon(BitmapFactory.decodeResource(resources,R.mipmap.avatar1))
                    .setSmallIcon(R.mipmap.ic_contact)
                    .setContentTitle(getString(R.string.receive_new_message))
                    .setContentText(contentText)
                    .setContentIntent(pendingInt)
                    .setAutoCancel(true)
                    .build()
                notificationManager.notify(1,notification);
            }

        }
    }




    override fun onCreate() {
        super.onCreate()
        instance = this
        //初始化
        EMClient.getInstance().init(applicationContext, EMOptions());
        //在做打包混淆时，关闭debug模式，避免消耗不必要的资源
        EMClient.getInstance().setDebugMode(BuildConfig.DEBUG);
        //Bmob
        Bmob.initialize(this, "5fc797898ece21e9b0aaa6b8ee4056c3");

        EMClient.getInstance().chatManager().addMessageListener(messageListener)
    }

    //判断App是否在前台
    private fun isForeground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        for(runningAppProgress in activityManager.runningAppProcesses) {
            if(runningAppProgress.processName == packageName) {
                //找到了app的进程
                return runningAppProgress.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND
            }
        }
        return false
    }
}