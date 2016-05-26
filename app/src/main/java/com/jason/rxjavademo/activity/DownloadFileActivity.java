package com.jason.rxjavademo.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.jason.rxjavademo.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

public class DownloadFileActivity extends AppCompatActivity {

    @Bind(R.id.circle_progress)
    CircleProgress mCircleProgress;
    @Bind(R.id.btn_download)
    Button mButton;

    private PublishSubject<Integer> mDownloadProgress = PublishSubject.create();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_file);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

    }

    @OnClick(R.id.btn_download)
    void download() {
        mButton.setText(getString(R.string.downloading));
        mButton.setClickable(false);
        mDownloadProgress.distinct()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Integer>() {

                    @Override
                    public void onCompleted() {
                        Log.d("test", "Completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("test", e.toString());
                    }

                    @Override
                    public void onNext(Integer progress) {
                        mCircleProgress.setProgress(progress);
                    }
                });

        final String destination = Environment.getExternalStorageDirectory() + "/rxjava/sdcardsoftboy.avi";
        obserbableDownload("http://archive.blender.org/fileadmin/movies/softboy.avi", destination)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onCompleted() {
                        resetDownloadButton();
                        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
                        File file = new File(destination);
                        intent.setDataAndType(Uri.fromFile(file),"video/avi");
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(getApplicationContext(), "Something went south", Toast.LENGTH_SHORT).show();
                        resetDownloadButton();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {

                    }
                });
    }

    private void resetDownloadButton() {
        mButton.setText(getString(R.string.finished));
    }

    private boolean downloadFile(String source, String destination) {
        boolean result = false;
        InputStream input = null;
        OutputStream output = null;
        HttpURLConnection connection = null;
        try {
            URL url = new URL(source);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();
            if (connection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return false;
            }
            int fileLength = connection.getContentLength();
            input = connection.getInputStream();
            output = new FileOutputStream(destination);
            byte data[] = new byte[4096];
            long total = 0;
            int count;
            while ((count = input.read(data)) != -1) {
                total += count;
                if (fileLength >0) {
                    int percentage = (int) (total * 100 / fileLength);
                    mDownloadProgress.onNext(percentage);
                }
                output.write(data, 0, count);
            }
            mDownloadProgress.onCompleted();
            result = true;
        } catch (Exception e) {
            mDownloadProgress.onError(e);
        } finally {
            try {
                if (output != null) {
                    output.close();
                }
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                mDownloadProgress.onError(e);
            }
            if (connection != null) {
                connection.disconnect();
                mDownloadProgress.onCompleted();
            }
        }
        return result;
    }

    private Observable<Boolean> obserbableDownload(final String source, final String destination) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    boolean result = downloadFile(source, destination);
                    if (result) {
                        subscriber.onNext(true);
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new Throwable("Download failed."));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
