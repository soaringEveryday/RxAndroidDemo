package com.jason.rxjavademo.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jason.rxjavademo.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func3;

public class RaceActivity extends AppCompatActivity {

    @Bind(R.id.editText)
    EditText mEditText;
    @Bind(R.id.editText2)
    EditText mEditText2;
    @Bind(R.id.editText4)
    EditText mEditText4;
    @Bind(R.id.button)
    Button mButton;

    Observable<String> race1;
    Observable<String> race2;
    Observable<String> race3;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    Log.d("rx", "1");
                    race1 = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (!TextUtils.isEmpty(mEditText.getText().toString())) {
                                subscriber.onNext(mEditText.getText().toString());
                            }
                        }
                    });
                    break;
                case 2:
                    Log.d("rx", "2");
                    race2 = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (!TextUtils.isEmpty(mEditText2.getText().toString())) {
                                subscriber.onNext(mEditText2.getText().toString());
                            }
                        }
                    });
                    break;
                case 3:
                    Log.d("rx", "3");
                    race3 = Observable.create(new Observable.OnSubscribe<String>() {
                        @Override
                        public void call(Subscriber<? super String> subscriber) {
                            if (!TextUtils.isEmpty(mEditText4.getText().toString())) {
                                subscriber.onNext(mEditText4.getText().toString());
                            }
                        }
                    });
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_race);
        ButterKnife.bind(this);
        
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message msg1 = Message.obtain();
                msg1.what = 1;
                Message msg2 = Message.obtain();
                msg2.what = 2;
                Message msg3 = Message.obtain();
                msg3.what = 3;
                mHandler.sendMessageDelayed(msg1, 1000);
                mHandler.sendMessageDelayed(msg2, 2000);
                mHandler.sendMessageDelayed(msg3, 4000);
            }
        });
        
        Observable.zip(race1, race2, race3, new Func3<String, String, String, String>() {
            @Override
            public String call(String s, String s2, String s3) {
                return s;
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Toast.makeText(RaceActivity.this, s, Toast.LENGTH_SHORT).show();
            }
        });


    }

}
