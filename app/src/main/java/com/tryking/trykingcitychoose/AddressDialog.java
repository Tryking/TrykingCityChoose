package com.tryking.trykingcitychoose;

import android.app.Dialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Tryking on 2016/4/19.
 */
public class AddressDialog extends Dialog {
    @Bind(R.id.address_dialog_close)
    Button addressDialogClose;
    @Bind(R.id.title_province)
    TextView titleProvince;
    @Bind(R.id.title_city)
    TextView titleCity;
    @Bind(R.id.title_district)
    TextView titleDistrict;
    @Bind(R.id.title_street)
    TextView titleStreet;
    @Bind(R.id.address_list)
    ListView addressList;

    private final View mDialogView;
    private Context mContext;
    private final List<CityItemBean> cityList;
    private CityDBManager manager;
    private SQLiteDatabase database;
    private AddressCityListAdapter adapter;
    private int currentLevel = 1;
    private Handler mHandler;
    private CityItemBean provinceItem;
    private CityItemBean cityItem;
    private CityItemBean districtItem;
    private CityItemBean streetItem;

    public AddressDialog(Context context, Handler handler) {
        super(context, R.style.style_address_dialog);
        setCancelable(true);

        mContext = context;
        mHandler = handler;

        WindowManager.LayoutParams params = getWindow().getAttributes();
        // getDimension和getDimensionPixelOffset的功能差不多,都是获取某个dimen的值,
        // 如果是dp或sp的单位,将其乘以density,如果是px,则不乘;两个函数的区别是一个返回float,一个返回int.
        params.height = context.getResources().getDimensionPixelOffset(R.dimen.h_40);
        params.width = context.getResources().getDimensionPixelOffset(R.dimen.w_80);
        getWindow().setAttributes(params);
        getWindow().setGravity(Gravity.BOTTOM);
        mDialogView = LayoutInflater.from(context).inflate(R.layout.dialog_address, null);
        ButterKnife.bind(this, mDialogView);
        setContentView(mDialogView);
        cityList = new ArrayList<CityItemBean>();

        initList1();

        addressList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (currentLevel) {
                    case 1:
                        provinceItem = cityList.get(position);
                        titleProvince.setText(provinceItem.getName());
                        titleProvince.setVisibility(View.VISIBLE);

                        initList2(provinceItem.getCode());
                        currentLevel++;
                        break;
                    case 2:
                        cityItem = cityList.get(position);
                        titleCity.setText(cityItem.getName());
                        titleCity.setVisibility(View.VISIBLE);

                        initList3(cityItem.getCode());
                        currentLevel++;
                        break;
                    case 3:
                        districtItem = cityList.get(position);
                        titleDistrict.setText(districtItem.getName());
                        titleDistrict.setVisibility(View.VISIBLE);

                        initList4(districtItem.getCode());
                        currentLevel++;
                        break;
                    case 4:
                        streetItem = cityList.get(position);
                        titleStreet.setText(streetItem.getName());
                        titleStreet.setVisibility(View.VISIBLE);

                        printAddress();
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @OnClick(R.id.address_dialog_close)
    void click(View v) {
        switch (v.getId()) {
            case R.id.address_dialog_close:
                dismiss();
                break;
            default:
                break;
        }
    }

    private void initList1() {
        manager = new CityDBManager(mContext);
        manager.openDatabase();
        database = manager.getDatabase();
        cityList.clear();
        try {
            String sql = "select code,name from t_prov_city_area_street where level=1";
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                CityItemBean cityItemBean = new CityItemBean();
                cityItemBean.setName(name);
                cityItemBean.setCode(code);
                cityList.add(cityItemBean);
            }
            adapter = new AddressCityListAdapter(mContext, cityList);
            addressList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        manager.closeDatabase();
        adapter.notifyDataSetChanged();
    }

    private void initList2(String pCode) {
        manager = new CityDBManager(mContext);
        manager.openDatabase();
        database = manager.getDatabase();
        cityList.clear();

        try {
            String sql = "select code,name from t_prov_city_area_street where level=2 and parentId='" + pCode + "'";
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                CityItemBean cityItemBean = new CityItemBean();
                cityItemBean.setName(name);
                cityItemBean.setCode(code);
                cityList.add(cityItemBean);
            }
            adapter = new AddressCityListAdapter(mContext, cityList);
            addressList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        manager.closeDatabase();
        adapter.notifyDataSetChanged();
    }

    private void initList3(String pCode) {
        manager = new CityDBManager(mContext);
        manager.openDatabase();
        database = manager.getDatabase();
        cityList.clear();

        try {
            String sql = "select code,name from t_prov_city_area_street where level=3 and parentId='" + pCode + "'";
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                CityItemBean cityItemBean = new CityItemBean();
                cityItemBean.setName(name);
                cityItemBean.setCode(code);
                cityList.add(cityItemBean);
            }
            adapter = new AddressCityListAdapter(mContext, cityList);
            addressList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        manager.closeDatabase();
        adapter.notifyDataSetChanged();
    }

    private void initList4(String pCode) {
        manager = new CityDBManager(mContext);
        manager.openDatabase();
        database = manager.getDatabase();
        cityList.clear();

        try {
            String sql = "select code,name from t_prov_city_area_street where level=4 and parentId='" + pCode + "'";
            Cursor cursor = database.rawQuery(sql, null);
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String code = cursor.getString(cursor.getColumnIndex("code"));
                String name = cursor.getString(cursor.getColumnIndex("name"));
                CityItemBean cityItemBean = new CityItemBean();
                cityItemBean.setName(name);
                cityItemBean.setCode(code);
                cityList.add(cityItemBean);
            }
            adapter = new AddressCityListAdapter(mContext, cityList);
            addressList.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
        database.close();
        manager.closeDatabase();
        adapter.notifyDataSetChanged();
        if (cityList.size() == 0) {
            printAddress();
            return;
        }
    }

    private void printAddress() {
        SsoMemberAddress address = new SsoMemberAddress();

        address.setProvince(provinceItem.getName());
        address.setCity(cityItem == null ? "" : cityItem.getName());
        address.setArea(districtItem == null ? "" : districtItem.getName());
        address.setAddressName(streetItem == null ? "" : streetItem.getName());

        Message msg = new Message();
        msg.what = 1;
        msg.obj = address;

        mHandler.sendMessage(msg);
        dismiss();
    }
}
