package com.tryking.trykingcitychoose;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.choose_address)
    TextView chooseAddress;
    @Bind(R.id.detail_address)
    TextView detailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.choose_address)
    void click(View v) {
        switch (v.getId()) {
            case R.id.choose_address:
                AddressDialog addressDialog = new AddressDialog(this, mHandler);
                addressDialog.show();
                break;
            default:
                break;
        }
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    SsoMemberAddress address = new SsoMemberAddress();
                    address = (SsoMemberAddress) msg.obj;
                    detailAddress.setText(address.getAddressContent());
                    break;
                default:
                    break;
            }
        }
    };
}
