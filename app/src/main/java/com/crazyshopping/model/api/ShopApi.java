package com.crazyshopping.model.api;


import com.crazyshopping.bean.cart.AddCartInfoBean;
import com.crazyshopping.bean.cart.AddressBean;
import com.crazyshopping.bean.cart.CartBean;
import com.crazyshopping.bean.cart.DeleteCartBean;
import com.crazyshopping.bean.home.BrandDetailBean;
import com.crazyshopping.bean.home.GoodDetailBean;
import com.crazyshopping.bean.home.HomeBean;
import com.crazyshopping.bean.own.LoginBean;
import com.crazyshopping.bean.special.SpecialBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShopApi {
    //首页content
    @GET("index")
    Flowable<HomeBean> getHomeContent();

    //品牌购买页详情
    @GET("brand/detail")
    Flowable<BrandDetailBean> getBrandDetail(@Query("id") int id);

    //商品购买页详情
    @GET("goods/detail")
    Flowable<GoodDetailBean> getGoodDetail(@Query("id") int id);

    //登录
    @POST("auth/login")
    @FormUrlEncoded
    Flowable<LoginBean> getLogin(@Field("username") String username,@Field("password") String password);

    //添加到购物车
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddCartInfoBean> addCart(@Field("goodsId") int goodsId,@Field("number") int number,@Field("productId") int productId);

    //购物车详情
    @GET("cart/index")
    Flowable<CartBean> getCartList();

    //删除购物车
    @POST("cart/delete")
    @FormUrlEncoded
    Flowable<DeleteCartBean> cartDelete(@Field("productIds") String productIds);

    //下单
    @GET("region/list")
    Flowable<AddressBean> getAddressById(@Query("parentId") int parentId);

    //专题
    @GET("topic/list")
    Flowable<SpecialBean> getSpecialList();
}
