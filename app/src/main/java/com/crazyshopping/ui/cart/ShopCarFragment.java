package com.crazyshopping.ui.cart;

import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.crazyshopping.R;
import com.crazyshopping.base.BaseFragment;
import com.crazyshopping.bean.cart.CartBean;
import com.crazyshopping.bean.cart.DeleteCartBean;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.presenter.cart.CartListPresenter;
import com.crazyshopping.presenter.cart.adapter.CartListAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class ShopCarFragment extends BaseFragment<ICart.ICartPresenter> implements ICart.ICartView {

    @BindView(R.id.layout_top)
    FrameLayout layoutTop;
    @BindView(R.id.recyclerview)
    RecyclerView recyclerview;
    @BindView(R.id.radio_select)
    CheckBox radioSelect;
    @BindView(R.id.txt_allPrice)
    TextView txtAllPrice;
    @BindView(R.id.txt_edit)
    TextView txtEdit;
    @BindView(R.id.txt_submit)
    TextView txtSubmit;
    @BindView(R.id.layout_bottom)
    RelativeLayout layoutBottom;
    @BindView(R.id.txt_selectAll)
    TextView txtSelectAll;


    List<CartBean.DataBean.CartListBean> list;

    private int allNums;
    private int allPrice;
    private CartListAdapter cartListAdapter;


    @Override
    protected void initData() {
        presenter.getCartList();
    }

    @Override
    protected ICart.ICartPresenter iitPresenter() {
        return new CartListPresenter();
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        cartListAdapter = new CartListAdapter(context, list);
        recyclerview.setLayoutManager(new LinearLayoutManager(context));
        recyclerview.setAdapter(cartListAdapter);
        cartListAdapter.setOnItemCheckBoxClickListener(new CartListAdapter.CheckBoxClick() {
            @Override
            public void checkChange() {
                //判断当前是否全选
                boolean bool = CheckSelectAll();
                txtSelectAll.setText("全选("+allNums+")");
                txtAllPrice.setText("￥"+allPrice);
                radioSelect.setChecked(bool);
                cartListAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_shop_car;
    }

    @Override
    public void getCartListReturn(CartBean result) {
        Log.d(TAG, "getCartListReturn: "+result.getData().getCartList());
        list.addAll(result.getData().getCartList());
        cartListAdapter.notifyDataSetChanged();
    }

    @OnClick({R.id.radio_select, R.id.txt_edit, R.id.txt_submit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.radio_select:
                selectAll();
                break;
            case R.id.txt_edit:
                clickEdit();
                break;
            case R.id.txt_submit:
                submitData();
                break;
        }
    }

    private void submitData() {
        if("下单".equals(txtSubmit.getText())){
            //提交数据

        }else if ("删除所选".equals(txtSubmit.getText())){
            StringBuilder sb = new StringBuilder();
            List<Integer> ids = getSelectProducts();
            for(Integer id:ids){
                sb.append(id);
                sb.append(",");
            }
            if(sb.length() > 0){
                sb.deleteCharAt(sb.length()-1);
                String productIds = sb.toString();
                presenter.deleteCartList(productIds);
            }else {
                Toast.makeText(context, "没有选中要删除的商品", Toast.LENGTH_SHORT).show();
            }
        }
    }
    /**
     * 获取当前选中的商品
     * @return
     */
    private List<Integer> getSelectProducts(){
        List<Integer> ids = new ArrayList<>();
        for(CartBean.DataBean.CartListBean item:list){
            if(item.select){
                ids.add(item.getProduct_id());
            }
        }
        return ids;
    }


    private void clickEdit() {
        //当前是默认的状态---编辑状态
        if("编辑".equals(txtEdit.getText())){
            cartListAdapter.isEditor = true;
            txtEdit.setText("完成");
            txtSubmit.setText("删除所选");
            txtAllPrice.setVisibility(View.GONE);
        }else if("完成".equals(txtEdit.getText())){   //编辑状态进入默认状态
            cartListAdapter.isEditor = false;
            txtEdit.setText("编辑");
            txtSubmit.setText("下单");
            txtAllPrice.setVisibility(View.VISIBLE);
            txtAllPrice.setText("￥0");
        }
        resetSelect(false);
        cartListAdapter.notifyDataSetChanged();
    }

    private void selectAll() {
        //设置当前是否是权限
        resetSelect(!radioSelect.isChecked());
        radioSelect.setSelected(!radioSelect.isChecked());
        txtSelectAll.setText("全选("+allNums+")");
        txtAllPrice.setText("￥"+allPrice);
        cartListAdapter.notifyDataSetChanged();
    }

    private void resetSelect(boolean bool) {
        allNums =0;
        allPrice =0;
        for (CartBean.DataBean.CartListBean item: list) {
            item.select = bool;
            if (bool){
                allNums += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
        }
        if (!bool){
            allPrice=0;
            allNums =0;
        }
    }

    @Override
    public void deleteCartListReturn(DeleteCartBean result) {

    }

    private static final String TAG = "ShopCarFragment";

    //判断当前数据是否选中
    private boolean CheckSelectAll(){
        boolean isSelectAll = true;
        for (CartBean.DataBean.CartListBean item :list) {
            if (item.select){
                allNums += item.getNumber();
                allPrice += item.getNumber() * item.getRetail_price();
            }
            if (isSelectAll&& !item.select){
                isSelectAll = false;
            }
        }
        return isSelectAll;
    }
}