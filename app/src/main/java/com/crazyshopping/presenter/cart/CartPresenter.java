package com.crazyshopping.presenter.cart;

import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.cart.AddCartInfoBean;
import com.crazyshopping.bean.home.GoodDetailBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

public class CartPresenter extends BasePresenter<ICart.CartView> implements ICart.CartPresenter {
    @Override
    public void getGoodDetail(int id) {
        addSubscribe(HttpManager.getInstance().getShopApi().getGoodDetail(id)
        .compose(RxUtils.<GoodDetailBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<GoodDetailBean>(mView) {
            @Override
            public void onNext(GoodDetailBean goodDetailBean) {
                mView.getGoodDetailReturn(goodDetailBean);
            }
        }));
    }

    @Override
    public void addCart(int goodsId, int number, int productId) {
        addSubscribe(HttpManager.getInstance().getShopApi().addCart(goodsId,number,productId)
        .compose(RxUtils.<AddCartInfoBean>rxScheduler())
        .subscribeWith(new CommonSubscriber<AddCartInfoBean>(mView) {
            @Override
            public void onNext(AddCartInfoBean addCartInfoBean) {
                mView.addCartInfoReturn(addCartInfoBean);
            }
        }));
    }
}
