package com.jason.rxjavademo;

import android.test.InstrumentationTestCase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;

/**
 * Created by Chen Haitao on 2016/5/5.
 */
public class TestObservable extends InstrumentationTestCase {
    private static final String LOG_TAG = "test";

    public void test() throws Exception {
        Log.d(LOG_TAG, "test");
        List<Integer> items = new ArrayList<Integer>();
        items.add(1);
        items.add(10);
        items.add(100);
        items.add(200);
        Observable<Integer> observable = Observable.from(items);
        Subscription subscription = observable.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(LOG_TAG, "Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Integer item) {
                Log.d(LOG_TAG, "Item is " + item);
            }
        });
    }

    public void test2() throws Exception{
        Log.d(LOG_TAG, "test2");
        Observable<Integer> observableString = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> observer) {
                for (int i = 0; i < 5; i++) {
                    observer.onNext(i);
                }
                observer.onCompleted();
            }
        });

        Subscription subscriptionPrint = observableString.subscribe(new Observer<Integer>() {
            @Override
            public void onCompleted() {
                Log.d(LOG_TAG, "Observable completed");
            }

            @Override
            public void onError(Throwable e) {
                Log.d(LOG_TAG, "Oh,no! Something wrong happened！");
            }

            @Override
            public void onNext(Integer item) {
                Log.d(LOG_TAG, "Item is " + item);
            }
        });

    }

    public void test3() throws Exception {
        // 最多传入7个函数
        Observable<String> observable = Observable.just(helloworld1(), helloworld2());
        observable.subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });
    }

    private String helloworld1() {
        return "helloworld1";
    }

    private String helloworld2() {
        return "helloworld2";
    }
}
