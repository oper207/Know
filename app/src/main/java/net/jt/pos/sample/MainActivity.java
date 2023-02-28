package net.jt.pos.sample;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import net.jt.pos.data.spinner.code.AbilityCode;
import net.jt.pos.data.spinner.code.ApprovalCode;
import net.jt.pos.data.spinner.code.RequestCode;
import net.jt.pos.fragment.AbilityFragment;
import net.jt.pos.fragment.ApprovalFragment;
import net.jt.pos.sdk.JTNetPosManager;
import net.jt.pos.sdk.JTNetRequestCode;
import net.jt.pos.utils.StringUtil;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends AppCompatActivity implements MainListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private RequestCode requestCode;
    private static final String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private static final int CODE_ALL_PERMISSION = 1000;
    private EditText etCardNum;
    private EditText etDeviceId;
    private TextView tvRequestMsg;
    private TextView tvResponseMsg;
    private TextView tvResponsetitle;
    private ScrollView svResponse;
    private ProgressBar pbResponse;

    private Button btnMainRequest;
    private JTNetPosManager.RequestCallback requestCallback;
    int mCount;

    private void setFullScreen(int viewId) {

        View view;
        view = findViewById(viewId);
        view.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE |
                View.SYSTEM_UI_FLAG_FULLSCREEN |
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY |
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setFullScreen(R.id.rl_head);

        setUpViews();
        mCount = 0;

        // todo 데몬 연동 객체 생성 및 서비스 연동 함수
        JTNetPosManager.init(getApplicationContext());
        requestCallback = new JTNetPosManager.RequestCallback() {
            @Override
            public void onResponse(Message msg) {
                pbResponse.setVisibility(View.INVISIBLE);
                tvResponseMsg.setVisibility(View.VISIBLE);
                byte[] response = msg.getData().getByteArray("RESPONSE_MSG");
                Log.e(TAG, "[응답2] : " + response.length + "||" + response[response.length - 1]);

                if (response != null) {
                    String strResData = StringUtil.byteArrayToString(response);
                    Log.e(TAG, "[응답] : " + response.length + "||" + strResData);
                    tvResponseMsg.setText(strResData + "|hello?");
                }
                btnMainRequest.setClickable(true);
            }
        };

        if (!hasPermissions(getApplicationContext(), PERMISSIONS)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(PERMISSIONS, CODE_ALL_PERMISSION);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void setUpViews() {
        etDeviceId = findViewById(R.id.et_main_device_id);
        etCardNum = findViewById(R.id.et_main_card_num);
        tvRequestMsg = findViewById(R.id.tv_request);
        tvResponseMsg = findViewById(R.id.tv_response);
        pbResponse = findViewById(R.id.pb_response);
        tvResponsetitle = findViewById(R.id.tv_response_title);
        svResponse = findViewById(R.id.sv_response);
        btnMainRequest = findViewById(R.id.btn_main_request);

        btnMainRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 중복 명령 방지
                //btnMainRequest.setClickable(false);
                requestTaskDaemon(requestCode);
            }
        });

        // todo 신용승인 로직
        findViewById(R.id.btn_auth).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 프래그먼트 객체생성을 하면서 프래그먼트 보이게하기
                changeFragment(ApprovalFragment.newInstance(), ApprovalFragment.TAG);
            }
        });

        findViewById(R.id.btn_function).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeFragment(AbilityFragment.newInstance(), AbilityFragment.TAG);
            }
        });

        replaceFragment(AbilityFragment.newInstance(), AbilityFragment.TAG);
    }

    /**
     * 기능, 승인 요청
     *
     * @param requestCode RequestCode
     */


    //todo 요청 받는 곳
    private void requestTaskDaemon(RequestCode requestCode) {

        byte[] requestArr = new byte[0];
        int requestCodeNum = 0;

        try {
            if (requestCode instanceof AbilityCode) {
                requestCodeNum = ((AbilityCode) requestCode).getRequestCode();
                requestArr = ((AbilityFragment) getCurrentFragment()).getRequestByteArr(requestCode);

            } else if (requestCode instanceof ApprovalCode) {
                // 요청 받으면 실행
                requestCodeNum = ((ApprovalCode) requestCode).getRequestCode();
                requestArr = ((ApprovalFragment) getCurrentFragment()).getRequestByteArr(requestCode);
            }
        } catch (ClassCastException e) {
            e.printStackTrace();
            pbResponse.setVisibility(View.INVISIBLE);
            tvResponseMsg.setVisibility(View.VISIBLE);
            tvResponseMsg.setText("포스앱 오류");
            return;
        }

        //tvRequestMsg.setText(StringUtil.byteArrayToString(requestArr) + "|");
        Log.e(TAG, "[요청 " + mCount + " ] : " + StringUtil.byteArrayToString(requestArr));

        // todo 데몬 연동 객체 리턴 함수
        JTNetPosManager.getInstance().jtdmProcess(requestCodeNum, requestArr, requestCallback);
        Log.e("리퀘스트 값1", " : " + requestCodeNum);
        Log.e("리퀘스트 값2", " : " + requestArr);
        Log.e("리퀘스트 값3", " : " + StringUtil.byteArrayToString(requestArr));
        Log.e("리퀘스트 값4", " : " + requestArr.length);
        Log.e("리퀘스트 값5", " : " + requestCallback);

    }

    /**
     * 프래그먼트 전환
     */
    private void replaceFragment(Fragment fragment, String tag) {
        getSupportFragmentManager().beginTransaction()
                .replace(
                        R.id.fragment_pane,
                        fragment,
                        tag
                ).commit();
    }


    /**
     * 키보드 숨김
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }

    /**
     * 현재 프래그먼트
     */
    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentById(R.id.fragment_pane);
    }

    /**
     * 현재와 다른 프래그먼트로 변경
     */
    private void changeFragment(Fragment fragment, String tag) {
        Fragment current = getCurrentFragment();
        Fragment tagFragment = getSupportFragmentManager().findFragmentByTag(tag);

        if (current != tagFragment) {
            replaceFragment(fragment, tag);
            hideKeyboard();
        }
    }

    @Override
    protected void onDestroy() {
        JTNetPosManager.getInstance().destroy(getApplicationContext());
        super.onDestroy();
    }

    @Override
    public void setRequestCode(RequestCode requestCode) {
        this.requestCode = requestCode;
    }

    @Override
    public String getDeviceId() {
        return etDeviceId.getText().toString().trim();
    }

    @Override
    public String getCardNumber() {
        return etCardNum.getText().toString().trim();
    }


    public boolean hasPermissions(Context _context, String[] _permissions) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && _context != null && _permissions != null) {
            for (String permission : _permissions) {
                if (ActivityCompat.checkSelfPermission(_context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case CODE_ALL_PERMISSION:
                if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        requestPermissions(PERMISSIONS, CODE_ALL_PERMISSION);
                    }
                }
                break;
        }
    }
}
