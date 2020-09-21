package com.crazyshopping.presenter.own;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.own.LoginBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.own.ILogin;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class LoginPresenter extends BasePresenter<ILogin.LoginView> implements ILogin.LoginPresenter {

    @Override
    public void getLoginBean(String username, String password) {
        addSubscribe(HttpManager.getInstance().getShopApi().getLogin(username, password)
                .compose(RxUtils.rxScheduler())
                .subscribeWith(new CommonSubscriber<LoginBean>(mView) {
                    @Override
                    public void onNext(LoginBean loginBean) {
                        mView.getLoginReturn(loginBean);
                    }
                }));
    }
}
