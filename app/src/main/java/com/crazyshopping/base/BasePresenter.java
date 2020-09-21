package com.crazyshopping.base;


import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.interfaces.IBaseView;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<V extends IBaseView> implements IBasePresenter<V> {
    //P层关联V层
    protected V mView;
    //弱引用 解决activity或者fragment使用MVP的内存泄漏问题
    WeakReference<V> weakReference;

    //RXJava2背压网络请求处理
    CompositeDisposable compositeDisposable;

    @Override
    public void attachView(V view) {
        weakReference = new WeakReference<>(view);
        mView = weakReference.get();
    }

    @Override
    public void detachView() {
        mView = null;
        clearSubscribe();
    }

    public void addSubscribe(Disposable disposable){
        if (compositeDisposable == null)compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(disposable);
    }

    private void clearSubscribe() {
        if (compositeDisposable!=null){
            compositeDisposable.clear();
        }
    }
}
