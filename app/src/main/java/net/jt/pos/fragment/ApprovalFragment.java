package net.jt.pos.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import net.jt.pos.common.HexCode;
import net.jt.pos.data.approval.CashApproval;
import net.jt.pos.data.approval.CashIcApproval;
import net.jt.pos.data.approval.CashIcInquiryApproval;
import net.jt.pos.data.approval.CheckApproval;
import net.jt.pos.data.approval.CreditApproval;
import net.jt.pos.data.approval.KakaoInqApproval;
import net.jt.pos.data.approval.MembershipApproval;
import net.jt.pos.data.approval.PointApproval;
import net.jt.pos.data.approval.QRApproval;
import net.jt.pos.data.approval.StoreDownApproval;
import net.jt.pos.data.approval.ZeroPayApproval;

import net.jt.pos.data.spinner.AdditionalInfo;
import net.jt.pos.data.spinner.DealGubun;
import net.jt.pos.data.spinner.PointKind;
import net.jt.pos.data.spinner.PointSaveGubun;
import net.jt.pos.data.spinner.QRTransactionType;
import net.jt.pos.data.spinner.Signature;
import net.jt.pos.data.spinner.TradeGubun;
import net.jt.pos.data.spinner.Wcc;
import net.jt.pos.data.spinner.ZeroPayGubun;
import net.jt.pos.data.spinner.ZeroPayWCC;
import net.jt.pos.data.spinner.code.ApprovalCode;
import net.jt.pos.data.spinner.QRWcc;
import net.jt.pos.data.spinner.code.RequestCode;
import net.jt.pos.sample.MainListener;
import net.jt.pos.sample.R;
import net.jt.pos.sdk.JTNetRequestCode;
import net.jt.pos.utils.StringUtil;

import java.util.Arrays;

/**
 * 승인요청
 */
public class ApprovalFragment extends Fragment implements RequestMsgInterface {

    public final static String TAG = ApprovalFragment.class.getSimpleName();

    // todo 전문종료
    private final String[] approvalCodes = ApprovalCode.getApprovalArr();
    private final String[] wccs = Wcc.getWccArr();
    private final String[] ZeroPaywccs = ZeroPayWCC.getZeroPayWCCArr();
    private final String[] signatures = Signature.getSignatureArr();
    private final String[] additionalInfos = AdditionalInfo.getAdditionalInfoArr();
    private final String[] dealGubuns = DealGubun.getDealGubunArr();
    private final String[] tradeGubuns = TradeGubun.getDealGubunArr();
    private final String[] pointSaveGubuns = PointSaveGubun.getPointSveGubunArr();
    private final String[] pointKindGubuns = PointKind.getPointKindGubunArr();
    private final String[] zeroPayGubuns = ZeroPayGubun.getZeroPayGubunArr();
    private final String[] zeroPayWCC = ZeroPayWCC.getZeroPayWCCArr();
    private final String[] qrTransactionType = QRTransactionType.getQRTransactionTypeArr();
    private final String[] qrWCC = QRWcc.getWccArr();

    // todo 스피너 변수
    private Spinner spApprovalKind;
    private Spinner spApprovalWcc;
    private Spinner spApprovalSignature;
    private Spinner spApprovalAdditional;
    private Spinner spApprovalDealGubun;
    private Spinner spApprovalTradeGubun;
    private Spinner sp_approval_point_save_gubun; // 적립구분
    private Spinner sp_approval_point_kind;
    private Spinner sp_approval_zero_wcc;

    private Spinner sp_approval_QR_kind;
    private Spinner sp_approval_QR_wcc;



    private EditText etApprovalDealAmount;
    private EditText etApprovalTax;
    private EditText etApprovalSvcCharge;
    private EditText etApprovalOrgDealDt;
    private EditText etApprovalOrgApprovalNo;
    private EditText etApprovalOrgUniqueNo;
    private EditText etApprovalIMonths;
    private EditText etApprovalBusinessNo;
    private EditText et_approval_password_info; // 비밀번호
    private EditText et_approval_point_amount; // 사용요청포인트
    private EditText et_approval_check_no; // 수표번호
    private EditText et_approval_check_amount; // 수표금액
    private EditText et_approval_check_publish_dt; // 발행일자
    private EditText et_approval_check_bank_code; // 단위농협코드
    private EditText et_approval_barcode;
    private EditText et_approval_random;
    private EditText et_approval_QRBarcode;


    private LinearLayout llApprovalAdditional;
    private LinearLayout llApprovalAmount;
    private LinearLayout llApprovalDealGubun;
    private LinearLayout llApprovalIMonths;
    private LinearLayout llApprovalOrgApprovalNo;
    private LinearLayout llApprovalRandom;
    private LinearLayout llApprovalOrgDealDt;
    private LinearLayout llApprovalOrgUniqueNo;
    private LinearLayout llApprovalSignature;
    private LinearLayout llApprovalSvcCharge;
    private LinearLayout llApprovalTax;
    private LinearLayout llApprovalWcc;
    private LinearLayout llApprovalBusinessInfo;
    private LinearLayout llApprovalTradeGubun;
    private LinearLayout ll_approval_password_info; // 비밀번호
    private LinearLayout ll_approval_point_save_gubun; // 적립구분
    private LinearLayout ll_approval_point_kind;        //포인트 종류
    private LinearLayout ll_approval_point_amount; // 사용요청포인트
    private LinearLayout ll_approval_check_no; // 수표번호
    private LinearLayout ll_approval_check_amount; // 수표금액
    private LinearLayout ll_approval_check_publish_dt; // 발행일자
    private LinearLayout ll_approval_check_bank_code; // 단위농협코드
    private LinearLayout ll_approval_zero_auth_kind;

    private LinearLayout ll_approval_QR;
    private MainListener mainListener;

    private TextView tv_approval_random;

    public static ApprovalFragment newInstance() {
        return new ApprovalFragment();
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_approval, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (getView() != null) {
            setUpViews(getView());
        }
    }

    private void setUpViews(View view) {
        llApprovalWcc = view.findViewById(R.id.ll_approval_wcc);
        llApprovalSignature = view.findViewById(R.id.ll_approval_signature);
        llApprovalAdditional = view.findViewById(R.id.ll_approval_additional);
        llApprovalIMonths = view.findViewById(R.id.ll_approval_i_months);
        llApprovalAmount = view.findViewById(R.id.ll_approval_amount);
        llApprovalTax = view.findViewById(R.id.ll_approval_tax);
        llApprovalSvcCharge = view.findViewById(R.id.ll_approval_svc_charge);
        llApprovalOrgDealDt = view.findViewById(R.id.ll_approval_org_deal_dt);
        llApprovalOrgApprovalNo = view.findViewById(R.id.ll_approval_org_approval_no);
        llApprovalOrgUniqueNo = view.findViewById(R.id.ll_approval_org_unique_no);
        llApprovalDealGubun = view.findViewById(R.id.ll_approval_deal_gubun);
        llApprovalBusinessInfo = view.findViewById(R.id.ll_approval_business_info);
        llApprovalTradeGubun = view.findViewById(R.id.ll_approval_trade_gubun);
        ll_approval_password_info = view.findViewById(R.id.ll_approval_password_info);
        ll_approval_point_save_gubun = view.findViewById(R.id.ll_approval_point_save_gubun);
        ll_approval_point_kind = view.findViewById(R.id.ll_approval_point_kind);
        ll_approval_point_amount = view.findViewById(R.id.ll_approval_point_amount);
        ll_approval_check_no = view.findViewById(R.id.ll_approval_check_no);
        ll_approval_check_amount = view.findViewById(R.id.ll_approval_check_amount);
        ll_approval_check_publish_dt = view.findViewById(R.id.ll_approval_check_publish_dt);
        ll_approval_check_bank_code = view.findViewById(R.id.ll_approval_check_bank_code);
        ll_approval_zero_auth_kind = view.findViewById(R.id.ll_approval_zero_auth_kind);

        ll_approval_QR = view.findViewById(R.id.ll_approval_qr);
        llApprovalRandom = view.findViewById(R.id.ll_approval_random);
        etApprovalDealAmount = view.findViewById(R.id.et_approval_amount);                  // 거래금액
        etApprovalTax = view.findViewById(R.id.et_approval_tax);                             // 세금
        etApprovalSvcCharge = view.findViewById(R.id.et_approval_svc_charge);               // 봉사료
        etApprovalOrgDealDt = view.findViewById(R.id.et_approval_org_deal_dt);              // 승인일자
        etApprovalOrgApprovalNo = view.findViewById(R.id.et_approval_org_approval_no);      // 승인번호
        etApprovalOrgUniqueNo = view.findViewById(R.id.et_approval_org_unique_no);          // 고유번호
        etApprovalIMonths = view.findViewById(R.id.et_approval_i_months);                   // 할부			(1 ~ 40 개월)
        etApprovalBusinessNo = view.findViewById(R.id.et_approval_business_no);             // 사업자번호

        et_approval_password_info = view.findViewById(R.id.et_approval_password_info);             // 비밀번호
        et_approval_point_amount = view.findViewById(R.id.et_approval_point_amount);             // 사용요청포인트
        et_approval_check_no = view.findViewById(R.id.et_approval_check_no);             // 수표번호
        et_approval_check_amount = view.findViewById(R.id.et_approval_check_amount);             // 수표금액
        et_approval_check_publish_dt = view.findViewById(R.id.et_approval_check_publish_dt);             // 발행일자
        et_approval_check_bank_code = view.findViewById(R.id.et_approval_check_bank_code);             // 단위농협코드
        et_approval_barcode = view.findViewById(R.id.et_approval_barcode);
        et_approval_random = view.findViewById(R.id.et_approval_random);

        et_approval_QRBarcode = view.findViewById(R.id.et_approval_qrbarcode);
        tv_approval_random = view.findViewById(R.id.tv_approval_random);

        // todo 레이아웃
        spApprovalKind = view.findViewById(R.id.sp_approval_kind);                          // 전문종류
        spApprovalWcc = view.findViewById(R.id.sp_approval_wcc);                            // 카드입력방식	("Key-In", "앱카드(수기)", "리더기")
        spApprovalSignature = view.findViewById(R.id.sp_approval_signature);                // 서명처리구분	("사용", "미사용", "재사용")
        spApprovalAdditional = view.findViewById(R.id.sp_approval_additional);               // 부가정보		("없음", "알리페이", "해외은련카드")
        spApprovalDealGubun = view.findViewById(R.id.sp_approval_deal_gubun);                       // 거래구분자
        spApprovalTradeGubun = view.findViewById(R.id.sp_approval_trade_gubun);                       // 거래구분자
        sp_approval_point_save_gubun = view.findViewById(R.id.sp_approval_point_save_gubun);                       // 적립구분
        sp_approval_point_kind = view.findViewById(R.id.sp_approval_point_kind);
        sp_approval_zero_wcc = view.findViewById(R.id.sp_approval_zero_wcc);
        sp_approval_QR_kind = view.findViewById(R.id.sp_approval_qrtype);
        sp_approval_QR_wcc = view.findViewById(R.id.sp_approval_qrwcc);

        if (getActivity() != null) {
            // todo 전문 종류 시피너 리스트(approvalCodes) 닫는 어댑터
            ArrayAdapter approvalCodesAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, approvalCodes);
            ArrayAdapter wccsAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, wccs);
            ArrayAdapter signaturesAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, signatures);
            ArrayAdapter additionalInfosAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, additionalInfos);
            ArrayAdapter dealGubunsAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, dealGubuns);
            ArrayAdapter tradeGubunsAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, tradeGubuns);
            ArrayAdapter pointSaveGubunsAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, pointSaveGubuns);
            ArrayAdapter pointSaveKindAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, pointKindGubuns);
            ArrayAdapter zeropayGubnsAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, zeroPayGubuns);
            ArrayAdapter zeropayWCCAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, zeroPayWCC);
            ArrayAdapter qrTransactionTypeAdapter = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, qrTransactionType);
            ArrayAdapter qrWCCAdapter  = new ArrayAdapter<>(getActivity(), R.layout.custom_dropdown_item, qrWCC);

            // todo 전문 종류 스피너
            spApprovalKind.setAdapter(approvalCodesAdapter);
            spApprovalWcc.setAdapter(wccsAdapter);
            spApprovalSignature.setAdapter(signaturesAdapter);
            spApprovalAdditional.setAdapter(additionalInfosAdapter);
            spApprovalDealGubun.setAdapter(dealGubunsAdapter);
            spApprovalTradeGubun.setAdapter(tradeGubunsAdapter);
            sp_approval_point_save_gubun.setAdapter(pointSaveGubunsAdapter);
            sp_approval_point_kind.setAdapter(pointSaveKindAdapter);
            sp_approval_zero_wcc.setAdapter(zeropayWCCAdapter);

            sp_approval_QR_wcc.setAdapter(qrWCCAdapter);
            sp_approval_QR_kind.setAdapter(qrTransactionTypeAdapter);

            spApprovalKind.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ApprovalCode approvalCode = ApprovalCode.getApproval(approvalCodes[position]);

                    spApprovalWcc.setSelection(1);
                    spApprovalSignature.setSelection(3);

                    // 선택시 mainActivity 에 set
                    if (mainListener != null) {
                        mainListener.setRequestCode(approvalCode);
                    }
                    if (approvalCode != null) {
                        changeLayout(approvalCode);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }


    }

    /**
     * 전부 숨긴 후 필요 레이아웃만 표시
     */
    private void changeLayout(ApprovalCode approvalCode) {  // approvalCode 리스트 선택

        Log.e(TAG, "changeLayout approvalCode: " + approvalCode);

        llApprovalWcc.setVisibility(View.GONE);
        llApprovalSignature.setVisibility(View.GONE);
        llApprovalAdditional.setVisibility(View.GONE);
        llApprovalIMonths.setVisibility(View.GONE);
        llApprovalAmount.setVisibility(View.GONE);
        llApprovalTax.setVisibility(View.GONE);
        llApprovalSvcCharge.setVisibility(View.GONE);
        llApprovalOrgDealDt.setVisibility(View.GONE);
        llApprovalOrgApprovalNo.setVisibility(View.GONE);
        llApprovalOrgUniqueNo.setVisibility(View.GONE);
        llApprovalDealGubun.setVisibility(View.GONE);
        llApprovalBusinessInfo.setVisibility(View.GONE);
        llApprovalTradeGubun.setVisibility(View.GONE);
        ll_approval_password_info.setVisibility(View.GONE);
        ll_approval_point_save_gubun.setVisibility(View.GONE);
        ll_approval_point_kind.setVisibility(View.GONE);

        ll_approval_point_amount.setVisibility(View.GONE);
        ll_approval_check_no.setVisibility(View.GONE);
        ll_approval_check_amount.setVisibility(View.GONE);
        ll_approval_check_publish_dt.setVisibility(View.GONE);
        ll_approval_check_bank_code.setVisibility(View.GONE);
        ll_approval_zero_auth_kind.setVisibility(View.GONE);
        ll_approval_QR.setVisibility(View.GONE);
        llApprovalRandom.setVisibility(View.GONE);
        etApprovalDealAmount.setEnabled(true);

        switch (approvalCode) {
            case CREDIT_BL:
                etApprovalDealAmount.setText("8");
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalSignature.setVisibility(View.VISIBLE);
                llApprovalAdditional.setVisibility(View.VISIBLE);
                llApprovalIMonths.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);

                break;
            case CREDIT_CANCEL:
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                llApprovalOrgUniqueNo.setVisibility(View.VISIBLE);

                // todo 신용승인 요청 선택 시
            case CREDIT_START:
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalSignature.setVisibility(View.VISIBLE);
                llApprovalAdditional.setVisibility(View.VISIBLE);
                llApprovalIMonths.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);

                break;
            case CASH_CANCEL:
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);

            case CASH_START:
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalDealGubun.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);

                break;
            case MEMBERSHIP_START:
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                etApprovalDealAmount.setEnabled(true);

                break;
            case MEMBERSHIP_CANCEL:
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                etApprovalDealAmount.setEnabled(true);

                break;
            case MEMBERSHIP_INQUIRY:
                llApprovalWcc.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                etApprovalDealAmount.setText("0");
                etApprovalDealAmount.setEnabled(false);
                ll_approval_zero_auth_kind.setVisibility(View.GONE);

                break;
            case CASH_IC_START:
                llApprovalWcc.setVisibility(View.GONE);
                llApprovalSignature.setVisibility(View.GONE);
                llApprovalAdditional.setVisibility(View.GONE);
                llApprovalIMonths.setVisibility(View.GONE);
                llApprovalTradeGubun.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                llApprovalTradeGubun.setVisibility(View.VISIBLE);
                llApprovalBusinessInfo.setVisibility(View.GONE);

                break;
            case CASH_IC_CANCEL:
                llApprovalWcc.setVisibility(View.GONE);
                llApprovalSignature.setVisibility(View.GONE);
                llApprovalAdditional.setVisibility(View.GONE);
                llApprovalIMonths.setVisibility(View.GONE);
                llApprovalTradeGubun.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                llApprovalTradeGubun.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                llApprovalBusinessInfo.setVisibility(View.GONE);

                break;
            case CASH_IC_INQUIRY:
                llApprovalWcc.setVisibility(View.GONE);
                llApprovalSignature.setVisibility(View.GONE);
                llApprovalAdditional.setVisibility(View.GONE);
                llApprovalIMonths.setVisibility(View.GONE);
                llApprovalTradeGubun.setVisibility(View.GONE);
                llApprovalAmount.setVisibility(View.GONE);
                llApprovalTax.setVisibility(View.GONE);
                llApprovalSvcCharge.setVisibility(View.GONE);
                llApprovalTradeGubun.setVisibility(View.GONE);
                llApprovalOrgDealDt.setVisibility(View.GONE);
                llApprovalOrgApprovalNo.setVisibility(View.GONE);
                llApprovalBusinessInfo.setVisibility(View.GONE);
                ll_approval_zero_auth_kind.setVisibility(View.GONE);

                break;
            case POINT_SAVE:
            case POINT_SAVE_CANCEL:
            case POINT_USE:
            case POINT_USE_CANCEL:
            case POINT_INQUIRY:
                llApprovalWcc.setVisibility(View.VISIBLE);
                ll_approval_point_save_gubun.setVisibility(View.VISIBLE);
                ll_approval_point_kind.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                ll_approval_point_amount.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                ll_approval_password_info.setVisibility(View.VISIBLE);

                break;
            case CHECK_INQUIRY:
                ll_approval_check_no.setVisibility(View.VISIBLE);
                ll_approval_check_amount.setVisibility(View.VISIBLE);
                ll_approval_check_publish_dt.setVisibility(View.VISIBLE);
                ll_approval_check_bank_code.setVisibility(View.VISIBLE);

                break;
            case STORE_DOWN:
                llApprovalBusinessInfo.setVisibility(View.VISIBLE);

                break;
            case ZEROPAY_AUTH:
                et_approval_barcode.setText("");
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                ll_approval_zero_auth_kind.setVisibility(View.VISIBLE);
                et_approval_random.setText("[TEST]제로페이 구매 요청");
                tv_approval_random.setText("임의 데이터");
                llApprovalRandom.setVisibility(View.VISIBLE);

                break;
            case ZEROPAY_CANCEL:
            case ZEROPAY_CHECK:
                et_approval_barcode.setText("");
                llApprovalWcc.setVisibility(View.GONE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                ll_approval_zero_auth_kind.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                et_approval_random.setText("[TEST]제로페이 환불/조회 요청");
                tv_approval_random.setText("임의 데이터");
                llApprovalRandom.setVisibility(View.VISIBLE);

                break;
            case QRCODE_AUTH:
                //WCC , 거래구분, 바코드
                ll_approval_QR.setVisibility(View.VISIBLE);
                llApprovalIMonths.setVisibility(View.VISIBLE);
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);

            break;

            case KAKAO_AUTH_INQUIRY:
                et_approval_barcode.setText("281006020000000000367403");
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                ll_approval_zero_auth_kind.setVisibility(View.VISIBLE);
                llApprovalIMonths.setVisibility(View.VISIBLE);
                break;

            case KAKAO_CANCEL_INQUIRY:
                et_approval_barcode.setText("281006020000000000367403");
                llApprovalAmount.setVisibility(View.VISIBLE);
                llApprovalTax.setVisibility(View.VISIBLE);
                llApprovalSvcCharge.setVisibility(View.VISIBLE);
                ll_approval_zero_auth_kind.setVisibility(View.VISIBLE);
                llApprovalOrgDealDt.setVisibility(View.VISIBLE);
                llApprovalOrgApprovalNo.setVisibility(View.VISIBLE);
                llApprovalIMonths.setVisibility(View.VISIBLE);

                break;

        }
    }

    // todo 명령 처리 버튼 클릭 시 작동 됨
    @Override
    public byte[] getRequestByteArr(RequestCode requestCode) throws ClassCastException { // requestCode: 리스트 값
        byte[] requestArr = new byte[0];

        ApprovalCode approvalCode = ((ApprovalCode) requestCode);


        switch (approvalCode) {  // approvalCode: 리스트 값
            // todo 신용승인 선택 시 여기는 무엇이고?
            case CREDIT_START:
            case CREDIT_BL:
            case CREDIT_CANCEL:
                requestArr = createCreditArr(approvalCode);
                break;
            case CASH_START:
            case CASH_CANCEL:
                requestArr = createCashArr(approvalCode);
                break;
            case MEMBERSHIP_START:
            case MEMBERSHIP_CANCEL:
            case MEMBERSHIP_INQUIRY:
                requestArr = createMembershipArr(approvalCode);
                break;
            case CASH_IC_START:
            case CASH_IC_CANCEL:
                requestArr = createCashIcArr(approvalCode);
                break;
            case CASH_IC_INQUIRY:
                requestArr = createCashIcInquiryArr(approvalCode);
                break;
            case POINT_USE:
            case POINT_USE_CANCEL:
            case POINT_INQUIRY:
            case POINT_SAVE:
            case POINT_SAVE_CANCEL:
                requestArr = createPointArr(approvalCode);
                break;
            case CHECK_INQUIRY:
                requestArr = createCheckInquiryArr(approvalCode);
                break;
            case STORE_DOWN:
                requestArr = createStoreDownArr(approvalCode);
                break;

            case ZEROPAY_AUTH:
            case ZEROPAY_CANCEL:
            case ZEROPAY_CHECK:
                requestArr = createZeroPayArr(approvalCode);
                break;
            case QRCODE_AUTH:
                requestArr = createQRAuthArrArr(approvalCode);
                break;
            case KAKAO_AUTH_INQUIRY:
            case KAKAO_CANCEL_INQUIRY:
                requestArr = createKakaopayArr(approvalCode);
                break;
        }
        Log.e(TAG+"프래그먼트", "[요청] : " + StringUtil.byteArrayToString(requestArr));
        return requestArr;
    }

    /**
     * 신용 전문
     */
    private byte[] createCreditArr(ApprovalCode approvalCode) {

        byte[] code = approvalCode.getCode().getBytes();
        // 신용취소면 isCancel는 true 아니면 false
        boolean isCancel = approvalCode == ApprovalCode.CREDIT_CANCEL;

        // 은련이면 은련코드 || 신용취소면 isCancel는 true 아니면 false
        if (isSelectUnionPay()) {
            code[3] = HexCode.UNION; // 뭐하는역할을 할까?
        }

        return new CreditApproval(
                code,
                getDeviceId(),
                getWCC(),
                getTrack2(isSelectKeyIn()),
                getIMonths(),
                getDealAmount(),
                getTax(),
                getSvcCharge(),
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel),
                getOrgUniqueNo(isCancel),
                getSignature()
        ).create();

    }

    /**
     * 현금영수증
     */
    private byte[] createCashArr(ApprovalCode approvalCode) {

        byte[] code = approvalCode.getCode().getBytes();
        boolean isCancel = approvalCode == ApprovalCode.CASH_CANCEL;

        return new CashApproval(
                code,
                getDeviceId(),
                getWCC(),
                getTrack2(isSelectKeyIn()),
                getDealAmount(),
                getTax(),
                getSvcCharge(),
                getDealGubun(),
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel)
        ).create();
    }

    /**
     * 현금 IC
     */
    private byte[] createCashIcArr(ApprovalCode approvalCode) {

        // todo 구현 되면 조건 수정 필요
        boolean isCancel = approvalCode == ApprovalCode.CASH_IC_CANCEL;

        return new CashIcApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId(),
                getDealAmount(),
                getTax(),
                getSvcCharge(),
                approvalCode == ApprovalCode.CASH_IC_CANCEL || approvalCode == ApprovalCode.CASH_IC_START || approvalCode == ApprovalCode.CASH_IC_INQUIRY ? getTradeGubun() : getDealGubun(),
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel)
        ).create();
    }

    /**
     * 현금 IC 체크
     */
    private byte[] createCashIcInquiryArr(ApprovalCode approvalCode) {
        return new CashIcInquiryApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId()
        ).create();
    }

    /**
     * 멤버쉽
     */
    private byte[] createMembershipArr(ApprovalCode approvalCode) {

        // todo 구현 되면 조건 수정 필요
        boolean isMembershipInquiry = approvalCode == ApprovalCode.MEMBERSHIP_INQUIRY;
        boolean isCancel = approvalCode == ApprovalCode.MEMBERSHIP_CANCEL;

        byte[] amount = !isMembershipInquiry ? getDealAmount() : StringUtil.getLPadZero(9, "").getBytes();


        return new MembershipApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId(),
                getWCC(),
                getTrack2(isSelectKeyIn()),
                amount,
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel)
        ).create();
    }


    /**
     * 수표조회
     */
    private byte[] createCheckInquiryArr(ApprovalCode approvalCode) {

        return new CheckApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId(),
                getCheckNo(),
                getCheckAmount(),
                getCheckPublishDt(),
                getBankCode()
        ).create();
    }

    /**
     * 포인트
     */
    private byte[] createPointArr(ApprovalCode approvalCode) {

        return new PointApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId(),
                getWCC(),
                getTrack2(isSelectKeyIn()),
                getPontSaveGubun(),
                getDealAmount(),
                getPointAmount(),
                getPontKindGubun(),
                getOrgDealDt(approvalCode == ApprovalCode.POINT_SAVE_CANCEL || approvalCode == ApprovalCode.POINT_USE_CANCEL ? true : false),
                getOrgApprovalNo(approvalCode == ApprovalCode.POINT_SAVE_CANCEL || approvalCode == ApprovalCode.POINT_USE_CANCEL ? true : false),
                getPasswordInfo()
        ).create();
    }

    /**
     * 가맹점 다운
     */
    private byte[] createStoreDownArr(ApprovalCode approvalCode) {
        return new StoreDownApproval(
                approvalCode.getCode().getBytes(),
                getDeviceId(),
                getBusinessNo()
        ).create();
    }
    /**
     * 제로페이
     */

    private byte[] createZeroPayArr(ApprovalCode approvalCode) {

        byte[] code = approvalCode.getCode().getBytes();
        boolean isCancel = approvalCode == ApprovalCode.ZEROPAY_CANCEL || approvalCode == ApprovalCode.ZEROPAY_CHECK;

        return new ZeroPayApproval(
                code,
                getDeviceId(),
                getZeroPayWCC(),
                getZeroPayData(),
                getZeroPayGubun(),
                StringUtil.getLPadZero(12, etApprovalDealAmount.getText().toString().trim()).getBytes(),
                StringUtil.getLPadZero(12, etApprovalTax.getText().toString().trim()).getBytes(),
                StringUtil.getLPadZero(12, etApprovalSvcCharge.getText().toString().trim()).getBytes(),
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel),
                getZeroPayRandomData()
        ).create();
    }

    private byte[] createKakaopayArr(ApprovalCode approvalCode) {

        byte[] code = approvalCode.getCode().getBytes();
        boolean isCancel = approvalCode == ApprovalCode.KAKAO_CANCEL_INQUIRY;

        return new KakaoInqApproval(
                code,
                getDeviceId(),
                getKakaoPayWCC(),
                getKakaoPayData(),
                StringUtil.getLPadZero(12, etApprovalDealAmount.getText().toString().trim()).getBytes(),
                StringUtil.getLPadZero(12, etApprovalTax.getText().toString().trim()).getBytes(),
                StringUtil.getLPadZero(12, etApprovalSvcCharge.getText().toString().trim()).getBytes(),
                getKakaoAuthDate(isCancel),
                getKakaoAuthNo(isCancel),
                getKakaoCancelReason(isCancel),
                getKakaoPayPosData()
        ).create();
    }

    /**
     * 제로페이
     */

    private byte[] createQRAuthArrArr(ApprovalCode approvalCode) {

        byte[] code = approvalCode.getCode().getBytes();
        boolean isCancel;

        if(sp_approval_QR_kind.getSelectedItemPosition() == 0){
            isCancel = false;
        }
        else
            isCancel = true;
        return new QRApproval(
                code,
                getDeviceId(),
                getQRWCC(),
                getQRTransactionType(),
                getQRData(),
                getIMonths(),
                getDealAmount(),
                getTax(),
                getSvcCharge(),
                getOrgDealDt(isCancel),
                getOrgApprovalNo(isCancel),
                getOrgUniqueNo(isCancel)
        ).create();
    }
    /**
     * 카드 입력 방식
     */
    private byte[] getWCC() {
        Wcc wcc = Wcc.getWcc(wccs[spApprovalWcc.getSelectedItemPosition()]);  // wcc: READER
        if (wcc != null) {
            return wcc.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();
    }

    /**
     * 거래구분자
     */
    private byte[] getDealGubun() {
        DealGubun dealGubun = DealGubun.getDealGubun(dealGubuns[spApprovalDealGubun.getSelectedItemPosition()]);
        if (dealGubun != null) {
            return dealGubun.getCode().getBytes();
        }
        return "0".getBytes();
    }

    /**
     * 거래구분
     */
    private byte[] getTradeGubun() {
        TradeGubun tradeGubun = TradeGubun.getDealGubun(tradeGubuns[spApprovalTradeGubun.getSelectedItemPosition()]);
        if (tradeGubun != null) {
            return tradeGubun.getCode().getBytes();
        }
        return "00".getBytes();
    }

    /**
     * 적립구분
     */
    private byte[] getPontSaveGubun() {
        PointSaveGubun pointSaveGubun = PointSaveGubun.getPointSaveGubun(pointSaveGubuns[sp_approval_point_save_gubun.getSelectedItemPosition()]);
        if (pointSaveGubun != null) {
            return pointSaveGubun.getCode().getBytes();
        }
        return "00".getBytes();
    }
    /**
     * 포인트 종류
     */
    private byte[] getPontKindGubun() {
        PointKind pointKindGubun = PointKind.getPointKindGubun(pointKindGubuns[sp_approval_point_kind.getSelectedItemPosition()]);
        if (pointKindGubun != null) {
            return pointKindGubun.getCode().getBytes();
        }
        return "  ".getBytes();
    }
    /**
     * 할부
     */
    private byte[] getIMonths() {
        return StringUtil.getLPadZero(2, etApprovalIMonths.getText().toString().trim()).getBytes();
    }

    /**
     * 거래금액
     */
    private byte[] getDealAmount() {
        return StringUtil.getLPadZero(9, etApprovalDealAmount.getText().toString().trim()).getBytes();
    }

    /**
     * 사용요청포인트
     */
    private byte[] getPointAmount() {
        return StringUtil.getLPadZero(9, et_approval_point_amount.getText().toString().trim()).getBytes();
    }

    /**
     * 세금
     */
    private byte[] getTax() {
        return StringUtil.getLPadZero(9, etApprovalTax.getText().toString().trim()).getBytes();
    }

    /**
     * 봉사료
     */
    private byte[] getSvcCharge() {
        return StringUtil.getLPadZero(9, etApprovalSvcCharge.getText().toString().trim()).getBytes();
    }

    /**
     * 비밀번호
     */
    private byte[] getPasswordInfo() {
        return StringUtil.getRPadSpace(18, et_approval_password_info.getText().toString().trim()).getBytes();
    }

    /**
     * 수표번호
     */
    private byte[] getCheckNo() {
        return StringUtil.getRPadSpace(16, et_approval_check_no.getText().toString().trim()).getBytes();
    }

    /**
     * 수표금액
     */
    private byte[] getCheckAmount() {
        return StringUtil.getLPadZero(9, et_approval_check_amount.getText().toString().trim()).getBytes();
    }

    /**
     * 발행일자
     */
    private byte[] getCheckPublishDt() {
        return StringUtil.getRPadSpace(6, et_approval_check_publish_dt.getText().toString().trim()).getBytes();
    }

    /**
     * 단위농협코드
     */
    private byte[] getBankCode() {
        return StringUtil.getRPadSpace(6, et_approval_check_bank_code.getText().toString().trim()).getBytes();
    }


    /**
     * 원거래일자 (취소시)
     */
    private byte[] getOrgDealDt(boolean isCancel) {
        return StringUtil.getRPadSpace(6,
                isCancel ? etApprovalOrgDealDt.getText().toString().trim() : ""
        ).getBytes();
    }

    /**
     * 원승인번호 (취소시)
     */
    private byte[] getOrgApprovalNo(boolean isCancel) {
        return StringUtil.getRPadSpace(12,
                isCancel ? etApprovalOrgApprovalNo.getText().toString().trim() : ""
        ).getBytes();
    }

    /**
     * 원거래고유번호 (취소시)
     */
    private byte[] getOrgUniqueNo(boolean isCancel) {
        return StringUtil.getRPadSpace(12,
                isCancel ? etApprovalOrgUniqueNo.getText().toString().trim() : ""
        ).getBytes();
    }

    /**
     * 서명처리구분
     */
    private byte[] getSignature() {
        Signature signature = Signature.getSignature(signatures[spApprovalSignature.getSelectedItemPosition()]);
        if (signature != null) {
            return signature.getCode().getBytes();
        }
        return Signature.NONE.getCode().getBytes();
    }

    /**
     * 사업자번호
     */
    private byte[] getBusinessNo() {
        return StringUtil.getRPadSpace(10, etApprovalBusinessNo.getText().toString().trim()).getBytes();
    }

    /**
     * 키인 체크
     */
    private boolean isSelectKeyIn() {
        return wccs[spApprovalWcc.getSelectedItemPosition()].equals(Wcc.KEYIN.getMsg());
    }

    /**
     * 은련 체크
     */
    private boolean isSelectUnionPay() {
        return additionalInfos[spApprovalAdditional.getSelectedItemPosition()].equals(AdditionalInfo.UNION_PAY.getMsg());
    }

    /**
     * 단말기 ID
     */
    private byte[] getDeviceId() {
        if (mainListener != null) {
            return StringUtil.getRPadSpace(10, mainListener.getDeviceId()).getBytes(); // mainListener.getDeviceId()).getBytes(): 단말기 번호
        }
        return StringUtil.getRPadSpace(10, "").getBytes();
    }

    /**
     * 카드번호
     */  // isKeyIn: false 카드번호가 제대로 안받아와 진다?
    private byte[] getTrack2(boolean isKeyIn) {
        Log.e("카드번호1", " : " + isKeyIn);
        Log.e("카드번호2", " : " + mainListener );
        if (mainListener != null && isKeyIn) {
            String cardNumber = mainListener.getCardNumber();
            int length = cardNumber.length() + 1;
            String track2 = length + cardNumber + "=";
            Log.e("카드번호3", " : " + cardNumber);
            Log.e("카드번호4", " : " + mainListener.getCardNumber());
            Log.e("카드번호5", " : " + length);
            Log.e("카드번호6", " : " + track2);
            return StringUtil.getRPadSpace(100, track2).getBytes();
        }
        return StringUtil.getRPadSpace(100, "").getBytes();
    }

    /**
     * 제로페이 입력 방식
     */
    private byte[] getZeroPayWCC() {
        ZeroPayWCC wcc = ZeroPayWCC.getZeroPayWCC(ZeroPaywccs[sp_approval_zero_wcc.getSelectedItemPosition()]);
        if (wcc != null) {
            return wcc.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();
    }
    /**
     * 제로페이 Track 2 Data
     */
    private byte[] getZeroPayData() {
        return StringUtil.getRPadSpace(100, et_approval_barcode .getText().toString().trim() + "=").getBytes();
    }


    /**
     * 제로페이 거래 방식
     */
    private byte[] getZeroPayGubun() {
        return "CPM".getBytes();
        /*
        ZeroPayGubun wcc = ZeroPayGubun.getZeroPayGubun(zeroPayGubuns[sp_approval_zero_kind.getSelectedItemPosition()]);
        if (wcc != null) {
            return wcc.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();

         */
    }
    /**
     * 제로페이 가맹점 임의 데이터
     */
    private byte[] getZeroPayRandomData() {
        try {
            return StringUtil.getRPadSpace(50, et_approval_random.getText().toString().trim()).getBytes("euc-kr");
        }
        catch(Exception e){
            return StringUtil.getRPadSpace(50, et_approval_random.getText().toString().trim()).getBytes();
        }
    }

    private byte[] getKakaoPayWCC() {
        ZeroPayWCC wcc = ZeroPayWCC.getZeroPayWCC(ZeroPaywccs[sp_approval_zero_wcc.getSelectedItemPosition()]);
        if (wcc != null) {
            return wcc.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();
    }

    private byte[] getKakaoPayData() {
        return StringUtil.getRPadSpace(24, et_approval_barcode .getText().toString().trim()).getBytes();
    }
    private byte[] getKakaoCancelReason(boolean isCancel) {
        return isCancel ? "01".getBytes() : "  ".getBytes();
    }

    private byte[] getKakaoPayPosData() {
        //etApprovalIMonths;

        byte[] pPosData = new byte[12];
        Arrays.fill(pPosData,(byte)0x20);

        try {
            System.arraycopy(etApprovalIMonths.getText().toString().trim().getBytes(), 0, pPosData, 6, 2);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        return pPosData;
    }

    private byte[] getKakaoAuthDate(boolean isCancel) {
        return StringUtil.getRPadSpace(8,
                isCancel ? etApprovalOrgDealDt.getText().toString().trim() : ""
        ).getBytes();
    }

    /**
     * 원승인번호 (취소시)
     */
    private byte[] getKakaoAuthNo(boolean isCancel) {
        return StringUtil.getRPadSpace(10,
                isCancel ? etApprovalOrgApprovalNo.getText().toString().trim() : ""
        ).getBytes();
    }

    private byte[] getQRTransactionType(){
        QRTransactionType mgetQRTransactionType = QRTransactionType.getQRTransactionType(qrTransactionType[sp_approval_QR_kind.getSelectedItemPosition()]);

        if(mgetQRTransactionType !=null){
            return mgetQRTransactionType.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();
    }
    private byte[] getQRWCC(){
        QRWcc qrWcc = QRWcc.getWcc(qrWCC[sp_approval_QR_wcc.getSelectedItemPosition()]);

        if(qrWcc !=null){
            return qrWcc.getCode().getBytes();
        }
        return StringUtil.getRPadSpace(1, "").getBytes();

    }
    private byte[] getQRData(){
        return StringUtil.getRPadSpace(1024, et_approval_QRBarcode.getText().toString().trim()).getBytes();
    }
}
