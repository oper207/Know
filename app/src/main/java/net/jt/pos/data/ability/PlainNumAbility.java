package net.jt.pos.data.ability;

import net.jt.pos.utils.ByteUtil;
import net.jt.pos.utils.StringUtil;

/**
 * 암호화하지 않은 번호
 */
public class PlainNumAbility extends BaseAbility {

    private final byte[] amount = StringUtil.getLPadZero(9, "1004").getBytes();
    private final byte[] msg = StringUtil.getRPadSpace(16, "").getBytes();

    public PlainNumAbility(byte[] requestCode) {
        super(requestCode);
    }

    @Override
    public byte[] create() {
        return ByteUtil.mergeArrays(
                requestCode,
                amount,
                msg,
                cr
        );
    }
}
