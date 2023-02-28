package net.jt.pos.fragment;

import net.jt.pos.data.spinner.code.RequestCode;

public interface RequestMsgInterface {
    byte[] getRequestByteArr(RequestCode requestCode) throws ClassCastException;
}
