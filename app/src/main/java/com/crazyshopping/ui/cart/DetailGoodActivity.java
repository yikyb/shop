package com.crazyshopping.ui.cart;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.crazyshopping.R;
import com.crazyshopping.base.BaseActivity;
import com.crazyshopping.bean.cart.AddCartInfoBean;
import com.crazyshopping.bean.home.GoodDetailBean;
import com.crazyshopping.common.CartCustomView;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.presenter.cart.CartPresenter;
import com.crazyshopping.utils.SpUtils;
import com.crazyshopping.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailGoodActivity extends BaseActivity<ICart.CartPresenter> implements ICart.CartView, View.OnClickListener {
    @BindView(R.id.layout_back)
    RelativeLayout layoutBack;
    @BindView(R.id.txt_title)
    TextView txtTitle;
    @BindView(R.id.banner_detail)
    Banner bannerDetail;
    @BindView(R.id.txt_name)
    TextView txtName;
    @BindView(R.id.txt_des)
    TextView txtDes;
    @BindView(R.id.txt_price)
    TextView txtPrice;
    @BindView(R.id.layout_norms)
    FrameLayout layoutNorms;
    @BindView(R.id.layout_comment)
    FrameLayout layoutComment;
    @BindView(R.id.txt_date)
    TextView txtDate;
    @BindView(R.id.txt_recommend)
    TextView txtRecommend;
    @BindView(R.id.layout_imgs)
    LinearLayout layoutImgs;
    @BindView(R.id.webview)
    WebView webview;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.layout_collect)
    RelativeLayout layoutCollect;
    @BindView(R.id.img_cart)
    ImageView imgCart;
    @BindView(R.id.layout_parameter)
    LinearLayout layoutParameter;
    @BindView(R.id.layout_problem)
    LinearLayout layoutProblem;
    @BindView(R.id.txt_problem)
    TextView txtProblem;
    @BindView(R.id.layout_bottom)
    LinearLayout layoutBottom;
    @BindView(R.id.txt_add_cart)
    TextView txtAddCart;
    @BindView(R.id.txt_count)
    TextView txtCount;
    private String html = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                    img{\n" +
            "                        width:100%;\n" +
            "                        height:auto;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";


    private PopupWindow mPopWindow;
    private GoodDetailBean goodDetailBean;
    private int currentNum = 1;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_good_detail;
    }


    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        mPresenter.getGoodDetail(id);
        layoutCollect.setOnClickListener(this);
        txtAddCart.setOnClickListener(this);
    }


    @Override
    protected ICart.CartPresenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    protected void initView() {
        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        layoutNorms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPop();
            }
        });
    }

    private void showPop() {
        if (mPopWindow != null && mPopWindow.isShowing()) {

        } else {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_good, null);
            int height = SystemUtils.dp2px(this, 250);
            mPopWindow = new PopupWindow(view, LinearLayout.LayoutParams.MATCH_PARENT, height);
            mPopWindow.setFocusable(true);
            mPopWindow.setOutsideTouchable(true);
            mPopWindow.setContentView(view);
            setBackGroundAlaph(0.3f);
            mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    setBackGroundAlaph(1f);
                }
            });
            view.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            CartCustomView cartCustomView = view.findViewById(R.id.layout_cartwindow);
            ImageView img_close = view.findViewById(R.id.img_close_good);
            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
            });
            int[] pt = new int[2];
            layoutBottom.getLocationInWindow(pt);
            mPopWindow.showAtLocation(layoutBottom, Gravity.NO_GRAVITY, 0, pt[1] - height);
            cartCustomView.initView();
            cartCustomView.setOnClickListener(new CartCustomView.IClick() {
                @Override
                public void clickCB(int value) {

                }
            });
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_collect:
                break;
            case R.id.txt_add_cart:
                addCart();
                break;
            case R.id.layout_cart_detail:
                setResult(1000);
                finish();
                break;
        }
    }

    private void addCart() {
        //boolean isLogin = SpUtils.getInstance().getBoolean("token");
        String token = SpUtils.getInstance().getString("token");
        if (!TextUtils.isEmpty(token)) {
            //判断当前的规格弹框是否打开
            if (mPopWindow != null && mPopWindow.isShowing()) {
                //添加到购物车的操作
                if (goodDetailBean.getData().getProductList().size() > 0) {
                    int goodsId = goodDetailBean.getData().getProductList().get(0).getGoods_id();
                    int productId = goodDetailBean.getData().getProductList().get(0).getId();
                    mPresenter.addCart(goodsId,currentNum,productId);
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }else {
                    Toast.makeText(this, "没有产品数据", Toast.LENGTH_SHORT).show();
                }
            }else {
                showPop();
            }
        }else {
            Toast.makeText(this, "未登录", Toast.LENGTH_SHORT).show();
        }
    }

    private void setBackGroundAlaph(float v) {
//        Window window = getWindow();
//        WindowManager.LayoutParams attributes = window.getAttributes();
//        attributes.alpha = v;
//        window.setAttributes(attributes);
        Window window = getWindow();
        window.findViewById(R.id.nest).setAlpha(v);
    }


    @Override
    public void addCartInfoReturn(AddCartInfoBean result) {
        int count = result.getData().getCartTotal().getGoodsCount();
        txtCount.setText(String.valueOf(count));
    }

    @Override
    public void getGoodDetailReturn(GoodDetailBean result) {
        goodDetailBean = result;
        //banner刷新
        updateBanner(result.getData().getGallery());
        //评论
        if (result.getData().getComment().getCount() > 0) {
            layoutComment.setVisibility(View.VISIBLE);
            updateComment(result.getData().getComment());
        } else {
            layoutComment.setVisibility(View.GONE);
        }
        //设置参数
        updateParameter(result.getData().getAttribute());
        //详情信息的展示
        updateDetailInfo(result.getData().getInfo());
        //常见问题
        updateProblem(result.getData().getIssue());
        //品牌
        updateBrand(result.getData().getBrand());
    }



    private void updateBrand(GoodDetailBean.DataBeanX.BrandBean brand) {
        txtName.setText(brand.getName());
        txtDes.setText(brand.getSimple_desc());
        txtPrice.setText("￥" + brand.getFloor_price());
    }

    private void updateProblem(List<GoodDetailBean.DataBeanX.IssueBean> issue) {
        layoutProblem.removeAllViews();
        if (issue.size() > 0) {
            txtProblem.setVisibility(View.VISIBLE);
            layoutProblem.setVisibility(View.VISIBLE);
            for (GoodDetailBean.DataBeanX.IssueBean item : issue) {
                View view = LayoutInflater.from(this).inflate(R.layout.layout_problem, null);
                TextView name = view.findViewById(R.id.txt_title);
                TextView value = view.findViewById(R.id.txt_context);
                name.setText(item.getQuestion());
                value.setText(item.getAnswer());
                layoutProblem.addView(view);
            }
        } else {
            layoutProblem.setVisibility(View.GONE);
            layoutProblem.setVisibility(View.GONE);
        }
    }

    private void updateDetailInfo(GoodDetailBean.DataBeanX.InfoBean info) {
        if (!TextUtils.isEmpty(info.getGoods_desc())) {
            String h5 = info.getGoods_desc();
            html = html.replace("$", h5);
            webview.loadDataWithBaseURL("about:blank", html, "text/html", "utf-8", null);
        }
    }

    private void updateParameter(List<GoodDetailBean.DataBeanX.AttributeBean> attribute) {
        layoutParameter.removeAllViews();//qingkong
        for (GoodDetailBean.DataBeanX.AttributeBean item : attribute) {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_parameter, null);
            TextView name = view.findViewById(R.id.txt_parameter_name);
            TextView value = view.findViewById(R.id.txt_parameter_value);
            name.setText(item.getName());
            value.setText(item.getValue());
            layoutParameter.addView(view);
        }
    }

    private void updateComment(GoodDetailBean.DataBeanX.CommentBean comment) {
        txtDate.setText(comment.getData().getAdd_time());
        txtRecommend.setText(comment.getData().getContent());
        layoutImgs.removeAllViews();
        for (int i = 0; i < comment.getData().getPic_list().size(); i++) {
            ImageView imageView = new ImageView(this);
            Glide.with(this).load(comment.getData().getPic_list().get(i).getPic_url()).into(imageView);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200, 200);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setPadding(5, 5, 5, 5);
            imageView.setLayoutParams(layoutParams);
            layoutImgs.addView(imageView);
        }

    }

    private void updateBanner(List<GoodDetailBean.DataBeanX.GalleryBean> gallery) {
        if (bannerDetail.getTag() == null || (int) bannerDetail.getTag() == 0) {
            List<String> imgs = new ArrayList<>();
            for (GoodDetailBean.DataBeanX.GalleryBean item : gallery) {
                imgs.add(item.getImg_url());
            }
            bannerDetail.setTag(1);
            bannerDetail.setAdapter(new BannerImageAdapter<String>(imgs) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }

            })
                    //.addBannerLifecycleObserver(this)
                    .setIndicator(new CircleIndicator(this));
        }
    }



}
