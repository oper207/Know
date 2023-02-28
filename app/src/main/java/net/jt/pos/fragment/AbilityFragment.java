package net.jt.pos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import net.jt.pos.data.spinner.code.AbilityCode;
import net.jt.pos.data.spinner.code.RequestCode;
import net.jt.pos.data.ability.BaseAbility;
import net.jt.pos.data.ability.PlainNumAbility;
import net.jt.pos.data.ability.SignAbility;
import net.jt.pos.sample.MainListener;
import net.jt.pos.sample.R;
import net.jt.pos.utils.StringUtil;

/**
 * 기능요청
 */
public class AbilityFragment extends Fragment implements RequestMsgInterface {

    public final static String TAG = AbilityFragment.class.getSimpleName();

    private Spinner spFunctionKind;
    private final String[] functionArr = AbilityCode.getFunctionArr();

    private EditText etAmount;

    private LinearLayout llAmount;
    private MainListener mainListener;

    public static AbilityFragment newInstance() {
        return new AbilityFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            this.mainListener = ((MainListener) context);
        } catch (ClassCastException e) {
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ability, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            setUpViews(getView());
        }
    }

    private void setUpViews(View view) {
        llAmount = view.findViewById(R.id.ll_function_amount);
        etAmount = view.findViewById(R.id.et_approval_amount); // 거래금액

        if (getActivity() != null) {
            spFunctionKind = view.findViewById(R.id.sp_approval_kind); // 기능요청구분
            ArrayAdapter adapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, AbilityCode.getFunctionArr());
            spFunctionKind.setAdapter(adapter);

            spFunctionKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    AbilityCode abilityCode = AbilityCode.getFunction(functionArr[position]);

                    // 선택시 mainActivity 에 set
                    if (mainListener != null) {
                        mainListener.setRequestCode(abilityCode);
                    }

                    // 사인요청일 때만 거래금액 표시
                    if (abilityCode == AbilityCode.SIGN) {
                        llAmount.setVisibility(View.VISIBLE);
                    } else {
                        llAmount.setVisibility(View.INVISIBLE);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }
    }

    @Override
    public byte[] getRequestByteArr(RequestCode requestCode) throws ClassCastException {
        byte[] requestArr = new byte[0];

        AbilityCode abilityCode = ((AbilityCode) requestCode);

        byte[] codeBytes = abilityCode.getCode().getBytes();

        switch (abilityCode) {
            case POS_NUM:
                requestArr = new PlainNumAbility(codeBytes).create();
                break;
            case KEY_EXCHANGE:
            case PRETRANSACTION:
            case CARD_CANCEL:
            case IC_CHECK:
                requestArr = new BaseAbility(codeBytes).create();
                break;
            case SIGN:
                byte[] amount = StringUtil.getLPadZero(9, etAmount.getText().toString()).getBytes();
                requestArr = new SignAbility(codeBytes, amount).create();
                break;
        }

        return requestArr;
    }
}
