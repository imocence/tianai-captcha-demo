package cloud.tianai.captcha.common.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @className: CaptchaResponse
 * @description:
 * @author: Aim
 * @date: 2023/9/18
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CaptchaResponse<T> implements Serializable {
    private String id;
    private T captcha;

    public static <T> CaptchaResponse<T> of(String id, T data) {
        return new CaptchaResponse<T>(id, data);
    }
}
