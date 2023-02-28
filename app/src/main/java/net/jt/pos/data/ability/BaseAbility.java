package net.jt.pos.data.ability;

import net.jt.pos.common.HexCode;
import net.jt.pos.utils.ByteUtil;

public class BaseAbility {
    protected byte[] requestCode;
    protected final byte[] cr = new byte[]{HexCode.CR};

    public BaseAbility(byte[] requestCode) {
        this.requestCode = requestCode;
    }

    public byte[] getRequestCode() {
        return requestCode;
    }

    public void setRequestCode(byte[] requestCode) {
        this.requestCode = requestCode;
    }

    public byte[] create() {
        return ByteUtil.mergeArrays(
                requestCode,
                cr
        );
    }
}
