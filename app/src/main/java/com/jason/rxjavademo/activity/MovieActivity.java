package com.jason.rxjavademo.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.jason.rxjavademo.R;
import com.jason.rxjavademo.adapter.CommonAdapter;
import com.jason.rxjavademo.adapter.ViewHolder;
import com.jason.rxjavademo.network.base.ConnectionBase;
import com.jason.rxjavademo.network.domain.BaseEntity;
import com.jason.rxjavademo.network.domain.MovieEntity;
import com.jason.rxjavademo.utils.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieActivity extends AppCompatActivity {


    @Bind(R.id.list)
    ListView listview;

    private BaseAdapter mAdapter;
    private ArrayList<MovieEntity> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        initViews();
    }

    private void initViews() {
        mData = new ArrayList<>();
        mAdapter = new CommonAdapter<MovieEntity>(this, mData, R.layout.item_movie) {
            @Override
            public void convert(ViewHolder helper, MovieEntity item, int position) {
                ImageLoader.load(getApplicationContext(), item.getImages().getSmall(), (ImageView) helper.getConvertView().findViewById(R.id.image));
                helper.setText(R.id.title, item.getTitle());
                helper.setText(R.id.year, item.getYear());
            }
        };
        listview.setAdapter(mAdapter);

        loadMovies();
    }

    private void loadMovies() {
        Call<BaseEntity> call = ConnectionBase.getApiService().getTopMovie(0, 20);
        call.enqueue(new Callback<BaseEntity>() {
            @Override
            public void onResponse(Call<BaseEntity> call, Response<BaseEntity> response) {
                if (response != null) {
                    BaseEntity baseEntity = response.body();
                    if (baseEntity != null) {
                        List<MovieEntity> movieEntities = baseEntity.getSubjects();
                        mData.addAll(movieEntities);
                        mAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<BaseEntity> call, Throwable t) {

            }
        });
    }

}
