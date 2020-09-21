package com.crazyshopping.presenter.home;


import com.crazyshopping.base.BasePresenter;
import com.crazyshopping.bean.home.HomeBean;
import com.crazyshopping.common.CommonSubscriber;
import com.crazyshopping.interfaces.home.IHomeContent;
import com.crazyshopping.model.HttpManager;
import com.crazyshopping.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;


public class HomeContentPresenter extends BasePresenter<IHomeContent.HomeContentView> implements IHomeContent.HomeContentPresenter {

    @Override
    public void getHomeContentBean() {
        addSubscribe(HttpManager.getInstance().getShopApi().getHomeContent()
        .compose(RxUtils.<HomeBean>rxScheduler())
        .map(new Function<HomeBean, List<HomeBean.HomeListBean>>() {
            @Override
            public List<HomeBean.HomeListBean> apply(HomeBean homeBean) throws Exception {
                List<HomeBean.HomeListBean> list = new ArrayList<>();
                //第一个对象封装 banner
                HomeBean.HomeListBean banner = new HomeBean.HomeListBean();
                banner.currenType = HomeBean.ITEM_TYPE_BANNER;
                banner.data = homeBean.getData().getBanner();
                list.add(banner);

                //导航的封装
                HomeBean.HomeListBean tab = new HomeBean.HomeListBean();
                tab.currenType = HomeBean.ITEM_TYPE_TAB;
                tab.data = homeBean.getData().getChannel();
                list.add(tab);

                //封装带top边距的标题
                HomeBean.HomeListBean title1 = new HomeBean.HomeListBean();
                title1.currenType = HomeBean.ITEM_TYPE_TITLETOP;
                title1.data = "品牌制造商直供";
                list.add(title1);

                //封装品牌制造商直供的列表数据
                for (int i = 0; i < homeBean.getData().getBrandList().size(); i++) {
                    HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                    brand.currenType = HomeBean.ITEM_TYPE_BRAND;
                    brand.data = homeBean.getData().getBrandList().get(i);
                    list.add(brand);
                }

                //新品首发标题
                HomeBean.HomeListBean title2 = new HomeBean.HomeListBean();
                title2.currenType = HomeBean.ITEM_TYPE_TITLE;
                title2.data = "周一周四·新品首发";
                list.add(title2);


                //新品首发数据封装
                for (int i = 0; i < homeBean.getData().getNewGoodsList().size(); i++) {
                    HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                    brand.currenType = HomeBean.ITEM_TYPE_NEW;
                    brand.data = homeBean.getData().getNewGoodsList().get(i);
                    list.add(brand);
                }

                //人气推荐
                HomeBean.HomeListBean title3 = new HomeBean.HomeListBean();
                title3.currenType = HomeBean.ITEM_TYPE_TITLETOP;
                title3.data = "人气推荐";
                list.add(title3);

                //人气推荐数据
                for (int i = 0; i < homeBean.getData().getHotGoodsList().size(); i++) {
                    HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                    brand.currenType = homeBean.ITEM_TYPE_HOT;
                    brand.data = homeBean.getData().getHotGoodsList().get(i);
                    list.add(brand);
                }

                //专题精选
                HomeBean.HomeListBean title4 = new HomeBean.HomeListBean();
                title4.currenType = HomeBean.ITEM_TYPE_TITLETOP;
                title4.data = "专题精选";
                list.add(title4);

                //专题精选数据
                HomeBean.HomeListBean brand = new HomeBean.HomeListBean();
                brand.currenType = HomeBean.ITEM_TYPE_TOPIC;
                brand.data = homeBean.getData().getTopicList();
                list.add(brand);

                //居家
                for (int i = 0; i < homeBean.getData().getCategoryList().size(); i++) {
                    HomeBean.HomeListBean title5 = new HomeBean.HomeListBean();
                    title5.currenType = HomeBean.ITEM_TYPE_TITLETOP;
                    title5.data = homeBean.getData().getCategoryList().get(i).getName();
                    list.add(title5);
                    for (int j =0; j<homeBean.getData().getCategoryList().get(i).getGoodsList().size() ;j++){
                        HomeBean.HomeListBean cate = new HomeBean.HomeListBean();
                        cate.currenType = HomeBean.ITEM_TYPE_CATEGORY;
                        cate.data = homeBean.getData().getCategoryList().get(i).getGoodsList().get(j);
                        list.add(cate);
                    }
                }






                return list;
            }
        })
        .subscribeWith(new CommonSubscriber<List<HomeBean.HomeListBean>>(mView) {
            @Override
            public void onNext(List<HomeBean.HomeListBean> homeListBeans) {
                mView.getHomeContentReturn(homeListBeans);
            }
        }));
    }
}
