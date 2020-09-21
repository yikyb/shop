package com.crazyshopping.presenter.cart;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.home.BrandDetailBean;
import com.crazyshopping.bean.home.GoodDetailBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class BrandPresenter extends BasePresenter<ICart.BrandView> implements ICart.BrandPresenter {
    @Override
    public void getBrandDetail(int id) {
        addSubscribe(HttpManager.getInstance().getShopApi().getBrandDetail(id)
                .compose(RxUtils.<BrandDetailBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandDetailBean>(mView) {
                    @Override
                    public void onNext(BrandDetailBean brandDetailBean) {
                        mView.getBrandDetailReturn(brandDetailBean);
                    }
                }));
    }
}
