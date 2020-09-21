package com.crazyshopping.interfaces.cart;

import com.crazyshopping.bean.cart.AddCartInfoBean;
import com.crazyshopping.bean.cart.AddressBean;
import com.crazyshopping.bean.cart.CartBean;
import com.crazyshopping.bean.cart.DeleteCartBean;
import com.crazyshopping.bean.home.BrandDetailBean;
import com.crazyshopping.bean.home.GoodDetailBean;
import com.crazyshopping.interfaces.IBasePresenter;
import com.crazyshopping.interfaces.IBaseView;

import okhttp3.Address;

public interface ICart {
    interface CartView extends IBaseView{
        void getGoodDetailReturn(GoodDetailBean result);
        //添加商品信息返回
        void addCartInfoReturn(AddCartInfoBean result);
    }
    interface CartPresenter extends IBasePresenter<CartView>{
        void getGoodDetail(int id);
        void addCart(int goodsId,int number,int productId);
    }

    //品牌
    interface BrandView extends IBaseView{
        void getBrandDetailReturn(BrandDetailBean result);
    }
    interface BrandPresenter extends IBasePresenter<BrandView>{
        void getBrandDetail(int id);
    }

    //购物车接口
    interface ICartView extends IBaseView{
        void getCartListReturn(CartBean result);
        void deleteCartListReturn(DeleteCartBean result);
    }
    interface ICartPresenter extends IBasePresenter<ICartView>{
        void getCartList();
        void deleteCartList(String productId);
    }

    //下单
    interface IAddressView extends IBaseView{
        void getAddressByIdReturn(AddressBean result);
    }
    interface IAddressPresenter extends IBasePresenter<IAddressView>{
        void getAddressById(int parentId);
    }

}
