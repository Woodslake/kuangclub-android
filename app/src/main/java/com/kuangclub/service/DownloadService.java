package com.kuangclub.service;

import android.app.DownloadManager;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.webkit.MimeTypeMap;

import com.kuangclub.BuildConfig;
import com.kuangclub.util.Logger;

import java.io.File;

/**
 * Created by Woodslake on 2018/8/10.
 */
public class DownloadService extends Service {
    private final String TAG = getClass().getSimpleName();

    //下载器
    private DownloadManager downloadManager;
    //下载地址
    private String downloadPath;
    //任务id
    private long taskId;
    //是否正在下载
    private boolean isDownloading;

    //广播接受者，接收下载状态
    private BroadcastReceiver receiver;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Logger.log(TAG, "onBind");
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.log(TAG, "onCreate");
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                checkDownloadStatus();//检查下载状态
            }
        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.log(TAG, "onStartCommand");
        String url = intent.getStringExtra("url");
        String name = intent.getStringExtra("name");
        download(url, name);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.log(TAG, "onDestroy");
        unregisterReceiver(receiver);
    }

    private void download(String url, String name){
        if(isDownloading){
            return;
        }
        isDownloading = true;
        Logger.log(TAG, "download");
        //创建下载任务
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        //移动网络情况下是否允许漫游
        request.setAllowedOverRoaming(false);
        //设置文件类型，可以在下载结束后自动打开该文件
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        String mimeString = mimeTypeMap.getMimeTypeFromExtension(MimeTypeMap.getFileExtensionFromUrl(url));
        request.setMimeType(mimeString);
        //在通知栏中显示，默认就是显示的
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setVisibleInDownloadsUi(true);
        //设置下载的路径
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name);
        downloadPath =  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath() + File.separator + name;
        //获取DownloadManager
        downloadManager = (DownloadManager)getSystemService(Context.DOWNLOAD_SERVICE);
        //将下载请求加入下载队列，加入下载队列后会给该任务返回一个long型的id，通过该id可以取消任务，重启任务、获取下载的文件等等
        taskId =  downloadManager.enqueue(request);
        //注册广播接收者，监听下载状态
        registerReceiver(receiver, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    //检查下载状态
    private void checkDownloadStatus() {
        DownloadManager.Query query = new DownloadManager.Query();
        query.setFilterById(taskId);//筛选下载任务，传入任务ID，可变参数
        Cursor c = downloadManager.query(query);
        if (c.moveToFirst()) {
            int status = c.getInt(c.getColumnIndex(DownloadManager.COLUMN_STATUS));
            switch (status) {
                case DownloadManager.STATUS_PAUSED:
                    Logger.log(TAG, "STATUS_PAUSED");
                    break;
                case DownloadManager.STATUS_PENDING:
                    Logger.log(TAG, "STATUS_PENDING");
                    break;
                case DownloadManager.STATUS_RUNNING:
                    Logger.log(TAG, "STATUS_RUNNING");
                    break;
                case DownloadManager.STATUS_SUCCESSFUL:
                    Logger.log(TAG, "STATUS_SUCCESSFUL");
                    isDownloading = false;
                    //下载完成安装APK
                    installApk(new File(downloadPath));
                    stopSelf();
                    break;
                case DownloadManager.STATUS_FAILED:
                    Logger.log(TAG, "STATUS_FAILED");
                    isDownloading = false;
                    stopSelf();
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 下载到本地后执行安装
     */
    private void installApk(File file) {
        if (!file.exists()) return;
        String fileName = file.getName();
        if(fileName.endsWith(".apk")){
            Intent intent = new Intent(Intent.ACTION_VIEW);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
                Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".FileProvider", file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            }else{
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/vnd.android.package-archive");
            }
            //在服务中开启activity必须设置flag
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }

}
