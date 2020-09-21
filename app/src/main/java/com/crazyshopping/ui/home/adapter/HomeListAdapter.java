package com.crazyshopping.ui.home.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.crazyshopping.R;
import com.crazyshopping.base.BaseAdapter;
import com.crazyshopping.bean.home.HomeBean;
import com.crazyshopping.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeBean.HomeListBean, BaseViewHolder> {

    private Context context;
    private String priceWord;
    private TopicAdapter topicAdapter;

    /**
     * Same as QuickAdapter#QuickAdapter(Context,int) but with
     * some initialization data.
     *
     * @param data A new list is created out of this one to avoid mutable list
     */
    public HomeListAdapter(List<HomeBean.HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        priceWord = "$元起";
        //做UI绑定
        addItemType(HomeBean.ITEM_TYPE_BANNER, R.layout.layout_home_banner);
        addItemType(HomeBean.ITEM_TYPE_TAB, R.layout.layout_home_tab);
        addItemType(HomeBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeBean.ITEM_TYPE_BRAND, R.layout.layout_home_brand);
        addItemType(HomeBean.ITEM_TYPE_TITLE, R.layout.layout_title);
        addItemType(HomeBean.ITEM_TYPE_NEW, R.layout.layout_home_newgood);
        addItemType(HomeBean.ITEM_TYPE_HOT, R.layout.layout_home_hot);
        addItemType(HomeBean.ITEM_TYPE_TOPIC, R.layout.layout_home_topiclist);
        addItemType(HomeBean.ITEM_TYPE_CATEGORY, R.layout.layout_home_cate);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void convert(BaseViewHolder helper, HomeBean.HomeListBean item) {
        switch (item.getItemType()) {
            case HomeBean.ITEM_TYPE_TITLE:
                updateTitle(helper, (String) item.data);
                break;
            case HomeBean.ITEM_TYPE_TITLETOP:
                updateTitle(helper, (String) item.data);
                break;
            case HomeBean.ITEM_TYPE_BANNER:
                updateBanner(helper, (List<HomeBean.DataBean.BannerBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_TAB:
                updateTab(helper, (List<HomeBean.DataBean.ChannelBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_BRAND:
                updateBrand(helper, (HomeBean.DataBean.BrandListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_NEW:
                updateNewGood(helper, (HomeBean.DataBean.NewGoodsListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_HOT:
                updateHot(helper, (HomeBean.DataBean.HotGoodsListBean) item.data);
                break;
            case HomeBean.ITEM_TYPE_TOPIC:
                updateTopic(helper, (List<HomeBean.DataBean.TopicListBean>) item.data);
                break;
            case HomeBean.ITEM_TYPE_CATEGORY:
                updateCate(helper,(HomeBean.DataBean.CategoryListBean.GoodsListBean)item.data);
                break;
        }
    }

    private void updateCate(BaseViewHolder helper,HomeBean.DataBean.CategoryListBean.GoodsListBean cate) {
        if (!TextUtils.isEmpty(cate.getList_pic_url())){
            Glide.with(context).load(cate.getList_pic_url()).into((ImageView) helper.getView(R.id.img_cate_home));
        }
        helper.setText(R.id.txt_cate_name_home,cate.getName());
        String price = priceWord.replace("$", String.valueOf(cate.getRetail_price()));
        helper.setText(R.id.txt_cate_price_home,price);

    }

    private void updateTopic(BaseViewHolder helper, List<HomeBean.DataBean.TopicListBean> topicGoods) {
        RecyclerView rv_topic = helper.getView(R.id.rv_topic_home);
        if (topicAdapter == null) {
            topicAdapter = new TopicAdapter(context, topicGoods);
            LinearLayoutManager manager = new LinearLayoutManager(context);
            manager.setOrientation(RecyclerView.HORIZONTAL);
            rv_topic.setLayoutManager(manager);
            rv_topic.setAdapter(topicAdapter);
        } else if (rv_topic.getAdapter() == null) {
            rv_topic.setAdapter(topicAdapter);
        }
    }

    private void updateHot(BaseViewHolder helper, HomeBean.DataBean.HotGoodsListBean hot) {
        if (!TextUtils.isEmpty(hot.getList_pic_url())) {
            Glide.with(context).load(hot.getList_pic_url()).into((ImageView) helper.getView(R.id.img_hot_home));
        }
        helper.setText(R.id.txt_hot_name_home, hot.getName());
        String price = priceWord.replace("$", String.valueOf(hot.getRetail_price()));
        helper.setText(R.id.txt_hot_price_home, price);
    }

    private void updateNewGood(BaseViewHolder helper, HomeBean.DataBean.NewGoodsListBean newGoodsListBean) {
        if (!TextUtils.isEmpty(newGoodsListBean.getList_pic_url())) {
            Glide.with(context).load(newGoodsListBean.getList_pic_url()).into((ImageView) helper.getView(R.id.img_newgood_home));
        }
        helper.setText(R.id.txt_newgood_price_home, newGoodsListBean.getName());
        String price = priceWord.replace("$", String.valueOf(newGoodsListBean.getRetail_price()));
        helper.setText(R.id.txt_newgood_price_home, price);
    }

    private void updateBrand(BaseViewHolder helper, HomeBean.DataBean.BrandListBean brands) {
        if (!TextUtils.isEmpty(brands.getNew_pic_url())) {
            Glide.with(context).load(brands.getNew_pic_url()).into((ImageView) helper.getView(R.id.img_brand_home));
        }
        helper.setText(R.id.txt_brand_name_home, brands.getName());
        String price = priceWord.replace("$", String.valueOf(brands.getFloor_price()));
        helper.setText(R.id.txt_brand_price_home, price);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateTab(BaseViewHolder helper, List<HomeBean.DataBean.ChannelBean> channels) {
        LinearLayout layoutChannels = helper.getView(R.id.layout_tab_home);
        //只让当前的布局内容只添加一次
        if (layoutChannels.getChildCount() == 0) {
            for (HomeBean.DataBean.ChannelBean item : channels) {
                String name = item.getName();
                TextView tab = new TextView(context);
                tab.setText(name);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1);
                tab.setLayoutParams(params);
                int size = SystemUtils.dp2px(context, 5);
                tab.setTextSize(size);
                tab.setGravity(Gravity.CENTER);
                Drawable icon = context.getDrawable(R.mipmap.ic_launcher);
                icon.setBounds(0,0,50,50);
                tab.setCompoundDrawables(null, icon, null, null);
                layoutChannels.addView(tab);
            }
        }
    }

    private void updateBanner(BaseViewHolder helper, List<HomeBean.DataBean.BannerBean> banners) {
        Banner banner = helper.getView(R.id.banner_home);
        if (banner.getTag() == null || (int) banner.getTag() == 0) {

            List<String> imgs = new ArrayList<>();
            for (HomeBean.DataBean.BannerBean item : banners) {
                imgs.add(item.getImage_url());
            }
            banner.setAdapter(new BannerImageAdapter<String>(imgs) {


                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }

            })
                    //.addBannerLifecycleObserver(this)
                    .setIndicator(new CircleIndicator(context));
            banner.setTag(1);
        }
    }

    private void updateTitle(BaseViewHolder helper, String title) {
        helper.setText(R.id.txt_title_home, title);
    }
}
