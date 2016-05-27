package com.jason.rxjavademo.network.base;

import com.jason.rxjavademo.network.domain.BaseEntity;
import com.jason.rxjavademo.network.exception.ApiException;

import rx.functions.Func1;

/**
 * Created by Chen Haitao on 2016/5/27.
 */
public class HttpResultFunc<T> implements Func1<BaseEntity<T>, T> {

    @Override
    public T call(BaseEntity<T> httpResult) {
        if (httpResult.getResultCode() != 0) {
            try {
                throw new ApiException(httpResult.getResultCode(), httpResult.getResultMessage());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
        return httpResult.getSubjects();
    }
}