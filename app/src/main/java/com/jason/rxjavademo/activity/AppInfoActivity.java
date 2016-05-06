package com.jason.rxjavademo.activity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jason.rxjavademo.R;
import com.jason.rxjavademo.adapter.CommonAdapter;
import com.jason.rxjavademo.adapter.ViewHolder;
import com.jason.rxjavademo.domian.AppInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class AppInfoActivity extends AppCompatActivity {


    @Bind(R.id.list)
    ListView listview;

    private BaseAdapter mAdapter;
    private ArrayList<AppInfo> mData;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_info);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    @OnClick({
            R.id.fab
    })
    public void onClick(View view) {
        ArrayList<AppInfo> copyOfData = new ArrayList<>();
        copyOfData.addAll(mData);
        mData.clear();
        mAdapter.notifyDataSetChanged();
        Observable.from(copyOfData)
                .subscribe(new Observer<AppInfo>() {
                    @Override
                    public void onCompleted() {

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
        refreshList();
    }

    private void refreshList() {
        getApps().toSortedList()
                .subscribe(new Observer<List<AppInfo>>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(AppInfoActivity.this, "load finished", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toast.makeText(AppInfoActivity.this, "load error", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(List<AppInfo> appInfos) {
                        Toast.makeText(AppInfoActivity.this, "onNext", Toast.LENGTH_SHORT).show();
                        mData.addAll(appInfos);
                        mAdapter.notifyDataSetChanged();
                    }
                });
    }

    private Observable<AppInfo> getApps() {
        return Observable.create(new Observable.OnSubscribe<AppInfo>() {
            @Override
            public void call(Subscriber<? super AppInfo> subscriber) {

                PackageManager pm = getPackageManager();
                List<PackageInfo> packageinfos = pm.getInstalledPackages(0);

                List<AppInfo> appInfos = new ArrayList<AppInfo>();

                for (PackageInfo pak : packageinfos) {
                    AppInfo appInfo = new AppInfo();
                    appInfo.setName((String) pm.getApplicationLabel(pak.applicationInfo));
                    appInfo.setIconDra(pm.getApplicationIcon(pak.applicationInfo));
                    appInfos.add(appInfo);
                }

                // emit
                for (AppInfo info : appInfos) {
                    if (subscriber.isUnsubscribed()){
                        return;
                    }

                    subscriber.onNext(new AppInfo(info.getName(), info.getIconDra()));
                }

                if (!subscriber.isUnsubscribed()){
                    subscriber.onCompleted();
                }
            }
        });
    }

}
