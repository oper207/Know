package net.jt.pos.sample;

import net.jt.pos.data.spinner.code.RequestCode;

/**
 * MainActivity 에서 Fragment 로 데이터 전달
 */
public interface MainListener {
    void setRequestCode(RequestCode requestCode);

    String getDeviceId();

    String getCardNumber();
}
