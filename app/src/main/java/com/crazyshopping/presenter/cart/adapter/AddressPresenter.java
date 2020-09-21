package com.crazyshopping.presenter.cart.adapter;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.cart.AddressBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class AddressPresenter extends BasePresenter<ICart.IAddressView> implements ICart.IAddressPresenter {
    @Override
    public void getAddressById(int parentId) {
        addSubscribe(HttpManager.getInstance().getShopApi().getAddressById(parentId)
        .compose(RxUtils.<AddressBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<AddressBean>(mView) {
            @Override
            public void onNext(AddressBean addressBean) {
                mView.getAddressByIdReturn(addressBean);
            }
        }));
    }
}
