package com.crazyshopping.ui.cart;

import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.crazyshopping.R;
import com.crazyshopping.base.BaseActivity;
import com.crazyshopping.bean.cart.AddressBean;
import com.crazyshopping.interfaces.cart.ICart;
import com.crazyshopping.presenter.cart.adapter.AddressPresenter;
import com.crazyshopping.utils.SystemUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;

public class AddressActivity extends BaseActivity<ICart.IAddressPresenter> implements ICart.IAddressView {
    @BindView(R.id.layout_adress_select)
    LinearLayout layoutAdressSelect;

    @BindView(R.id.layout_adress)
    LinearLayout layoutAdress;
    @BindView(R.id.txt_adress)
    TextView txtAdress;


    private PopupWindow mPopWindow;
    private Map<Integer, List<AddressBean.DataBean>> addressMap;

    private LoopView province, city, area;
    private TextView txtProvince, txtCity, txtArea,txtSubmit;

    private int curProvinceId, curCityId, curAreaId; //当前省市区的ID

    @Override
    protected int getLayoutId() {
        return R.layout.activity_adress_add;
    }

    @Override
    protected void initView() {
        addressMap = new HashMap<>();
    }

    @Override
    protected ICart.IAddressPresenter initPresenter() {
        return new AddressPresenter();
    }

    @Override
    protected void initData() {
        //加载省份数据
        mPresenter.getAddressById(1);
    }


    @OnClick({R.id.layout_adress_select})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.layout_adress_select:
                openAdressSelect();
                break;
        }
    }

    private void openAdressSelect() {
        if (mPopWindow != null && mPopWindow.isShowing()) {

        } else {
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_adress, null);
            int height = SystemUtils.dp2px(this, 250);
            mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT, height);
            mPopWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopWindow.setFocusable(false);
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.setContentView(contentView);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            province = contentView.findViewById(R.id.adress_province);
            city = contentView.findViewById(R.id.adress_city);
            area = contentView.findViewById(R.id.adress_area);
            txtProvince = contentView.findViewById(R.id.txt_province);
            txtCity = contentView.findViewById(R.id.txt_city);
            txtArea = contentView.findViewById(R.id.txt_area);
            txtSubmit = contentView.findViewById(R.id.txt_submit);


            mPopWindow.showAtLocation(layoutAdress, Gravity.BOTTOM, 0, 0);

            //省份
            province.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                    AddressBean.DataBean dataBean = proviceList.get(index);
                    curProvinceId = dataBean.getId();
                    mPresenter.getAddressById(curProvinceId);
                    List<String> items = new ArrayList<>();
                    items.add("请选择");
                    city.setItems(items);
                    txtProvince.setText(dataBean.getName());
                    txtCity.setText("请选择城市");
                    txtArea.setText("请选中区域");
                }
            });

            city.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                    AddressBean.DataBean dataBean = cityList.get(index);
                    curCityId = dataBean.getId();
                    mPresenter.getAddressById(curCityId);
                    area.setItems(new ArrayList<>());
                    txtCity.setText(dataBean.getName());
                    txtArea.setText("请选中区域");
                }
            });

            area.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                    AddressBean.DataBean dataBean = areaList.get(index);
                    curAreaId = dataBean.getId();
                    txtArea.setText(dataBean.getName());
                }
            });

            //初始化省份的数据
            List<AddressBean.DataBean> pList = addressMap.get(1);
            if (pList == null) return;
            List<String> adresses = getAdressStrings(pList);
            if (pList == null || adresses.size() == 0) {
                mPresenter.getAddressById(1);
            } else {
                province.setItems(adresses);
                curProvinceId = pList.get(0).getId();
                txtProvince.setText(adresses.get(0));
            }

            txtSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                    String s2 = txtProvince.getText().toString();
                    String s = txtCity.getText().toString();
                    String s1 = txtArea.getText().toString();
                    txtAdress.setText(s2+s+s1);
                }
            });
        }
    }

    /**
     * 请求数据回来的方法
     *
     * @param result
     */
    @Override
    public void getAddressByIdReturn(AddressBean result) {
        List<AddressBean.DataBean> list = null;
        int type = 0;
        for (AddressBean.DataBean item : result.getData()) {
            int key = item.getParent_id();
            list = addressMap.get(key);
            if (list == null) {
                list = new ArrayList<>();
                addressMap.put(key, list);
            }
            boolean bool = hasList(item.getId(), list);
            if (!bool) list.add(item);
            if (type == 0) {
                type = item.getType();
            }
        }
        if (list == null) return;
        List<String> adresses = getAdressStrings(list);
        if (type == 1) {
            //刷新省的数据
            if (province != null) {
                curProvinceId = list.get(0).getId();
                txtProvince.setText(list.get(0).getName());
                province.setItems(adresses);
            }
        } else if (type == 2) {
            //刷新市的数据
            if (city != null) {
                curCityId = list.get(0).getId();
                txtCity.setText(list.get(0).getName());
                city.setItems(adresses);
            }
        } else {
            //区
            if (area != null) {
                curAreaId = list.get(0).getId();
                txtArea.setText(list.get(0).getName());
                area.setItems(adresses);
            }
        }
    }

    /**
     * 判断当前的地址列表中是否有这个地址
     *
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AddressBean.DataBean> list) {
        boolean bool = false;
        for (AddressBean.DataBean item : list) {
            if (item.getId() == id) {
                bool = true;
                break;
            }
        }
        return bool;
    }

    /**
     * 提取省市区的名字
     *
     * @param list
     * @return
     */
    private List<String> getAdressStrings(List<AddressBean.DataBean> list) {
        List<String> adresses = new ArrayList<>();
        for (AddressBean.DataBean item : list) {
            adresses.add(item.getName());
        }
        return adresses;
    }

    /**
     * 通过id获取当前数据中的对象
     *
     * @param id
     * @param list
     * @return
     */
    private AddressBean.DataBean getDataBeanById(int id, List<AddressBean.DataBean> list) {
        for (AddressBean.DataBean item : list) {
            if (item.getId() == id) return item;
        }
        return null;
    }
}
