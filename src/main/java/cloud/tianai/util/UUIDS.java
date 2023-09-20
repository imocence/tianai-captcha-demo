package cloud.tianai.util;

import java.util.UUID;

/**
 * @className: UUIDS
 * @description:
 * @author: Aim
 * @date: 2023/9/20
 **/
public class UUIDS {

    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
