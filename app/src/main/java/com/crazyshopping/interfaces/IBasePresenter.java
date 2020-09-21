package com.crazyshopping.interfaces;

public interface IBasePresenter<T extends IBaseView> {

    //V层接口的关联
    void attachView(T view);
    //取消和V层的关联
    void detachView();
}
