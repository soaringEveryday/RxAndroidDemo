package com.jason.rxjavademo.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jason.rxjavademo.R;
import com.jason.rxjavademo.adapter.CommonAdapter;
import com.jason.rxjavademo.adapter.ViewHolder;
import com.jason.rxjavademo.domian.AppInfo;
import com.jason.rxjavademo.utils.SPUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SchedulersActivity extends AppCompatActivity {

    @Bind(R.id.list)
    ListView listview;

    private BaseAdapter mAdapter;
    private ArrayList<AppInfo> mData;
    private List<AppInfo> appInfos = new ArrayList<AppInfo>();
    private Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedulers);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        gson = new Gson();

        getApplicationListAndStore();
        initViews();

    }

    @OnClick({
            R.id.fab
    })
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fab:
                restoreApp();
                break;
        }
    }

    private void restoreApp() {

        Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {
                String appStr = (String) SPUtils.get(getApplicationContext(), "app", "");
                if (appStr != null && !TextUtils.isEmpty(appStr)) {
                    appInfos.clear();
                    appInfos = gson.fromJson(appStr, new TypeToken<List<AppInfo>>() {
                    }.getType());
                } else {
                    Toast.makeText(SchedulersActivity.this, "get app str error!", Toast.LENGTH_SHORT).show();
                }

                for (AppInfo appInfo : appInfos) {
                    if (appInfo != null) {
                        subscriber.onNext(appInfo);
                    }
                }

                subscriber.onCompleted();
            }
        }).onBackpressureBuffer()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(SchedulersActivity.this, "restore finished", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(AppInfo appInfo) {
                        mData.add(appInfo);
                        mAdapter.notifyDataSetChanged();
                    }
                });


    }

    private void initViews() {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<AppInfo>(this, mData, R.layout.item_app_info) {
            @Override
            public void convert(ViewHolder helper, AppInfo item, int position) {
                helper.setText(R.id.app_name, item.getName());
                helper.setImageDrawable(R.id.app_icon, item.getIconDra());
            }
        };
        listview.setAdapter(mAdapter);
    }

    private void getApplicationListAndStore() {
        PackageManager pm = getPackageManager();
        List<PackageInfo> packageinfos = pm.getInstalledPackages(0);

        // prevent stack overflow
        int count = 0;
        for (PackageInfo pak : packageinfos) {
            AppInfo appInfo = new AppInfo();
            appInfo.setName((String) pm.getApplicationLabel(pak.applicationInfo));
            appInfo.setIconDra(pm.getApplicationIcon(pak.applicationInfo));
            appInfos.add(appInfo);
            if (++count > 10) {
                break;
            }
        }

        int length = appInfos.size();
        Toast.makeText(SchedulersActivity.this, "start store apps " + length, Toast.LENGTH_SHORT).show();

        new Thread(new Runnable() {
            @Override
            public void run() {
                final String appStr = gson.toJson(appInfos);
                SPUtils.put(getApplicationContext(), "app", appStr);
                Toast.makeText(SchedulersActivity.this, "apps info stored to share preferences", Toast.LENGTH_SHORT).show();
            }
        }).run();

//        Schedulers.io().createWorker().schedule(new Action0() {
//            @Override
//            public void call() {
//                SPUtils.put(getApplicationContext(), "app", appStr);
//                Toast.makeText(SchedulersActivity.this, "apps info stored to share preferences", Toast.LENGTH_SHORT).show();
//            }
//        });

    }

}
