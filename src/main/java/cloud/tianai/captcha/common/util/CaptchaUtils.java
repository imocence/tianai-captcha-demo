package cloud.tianai.captcha.common.util;

import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;

/**
 * @Author: 天爱有情
 * @date 2022/4/28 17:03
 * @Description 验证码工具包
 */
public class CaptchaUtils {

    /**
     * 是否是滑动验证码
     *
     * @param type 类型
     * @return boolean
     */
    public static boolean isSliderCaptcha(String type) {
        return CaptchaTypeConstant.SLIDER.equals(type)
                || CaptchaTypeConstant.ROTATE.equals(type)
                || CaptchaTypeConstant.CONCAT.equals(type);
    }

    /**
     * 是否是点击验证码
     *
     * @param type type
     * @return boolean
     */
    public static boolean isClickCaptcha(String type) {
        return CaptchaTypeConstant.WORD_IMAGE_CLICK.equals(type) || CaptchaTypeConstant.IMAGE_CLICK.equals(type);
    }

    /**
     * 是否是拼图类验证码, 校验的值是 数字排列顺序
     *
     * @param type 类型
     * @return boolean
     */
    public static boolean isJigsawCaptcha(String type) {
        return CaptchaTypeConstant.JIGSAW.equals(type);
    }
}
