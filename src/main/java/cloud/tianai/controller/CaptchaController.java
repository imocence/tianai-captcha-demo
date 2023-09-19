package cloud.tianai.controller;


import cloud.tianai.captcha.common.constant.CaptchaTypeConstant;
import cloud.tianai.captcha.common.response.ApiResponse;
import cloud.tianai.captcha.common.response.CaptchaResponse;
import cloud.tianai.captcha.generator.ImageCaptchaGenerator;
import cloud.tianai.captcha.generator.common.model.dto.ImageCaptchaInfo;
import cloud.tianai.captcha.generator.impl.MultiImageCaptchaGenerator;
import cloud.tianai.captcha.resource.ImageCaptchaResourceManager;
import cloud.tianai.captcha.resource.ResourceStore;
import cloud.tianai.captcha.resource.common.model.dto.Resource;
import cloud.tianai.captcha.resource.impl.DefaultImageCaptchaResourceManager;
import cloud.tianai.captcha.resource.impl.provider.ClassPathResourceProvider;
import cloud.tianai.captcha.validator.ImageCaptchaValidator;
import cloud.tianai.captcha.validator.common.model.dto.ImageCaptchaTrack;
import cloud.tianai.captcha.validator.impl.BasicCaptchaTrackValidator;
import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Controller
public class CaptchaController {

    @GetMapping("/tac")
    public String tac() {
        return "tac";
    }

    @GetMapping({"", "/", "/index"})
    public String index() {
        return "index";
    }

    @GetMapping("/slider")
    public String slider() {
        return "slider";
    }

    @GetMapping("/rotate")
    public String rotate() {
        return "rotate";
    }

    @GetMapping("/concat")
    public String concat() {
        return "concat";
    }

    @GetMapping("/word-click")
    public String wordClick() {
        return "word-click";
    }

    @GetMapping("/bt-image")
    public String btImage() {
        return "bt-image";
    }

    @RequestMapping("/gen")
    @ResponseBody
    public CaptchaResponse<ImageCaptchaInfo> genCaptcha(HttpSession session, @RequestParam(value = "type", required = false) String type) {

        ImageCaptchaResourceManager imageCaptchaResourceManager = new DefaultImageCaptchaResourceManager();
        ImageCaptchaGenerator imageCaptchaGenerator = new MultiImageCaptchaGenerator(imageCaptchaResourceManager).init(true);

//        添加自定义图片资源
        ResourceStore resourceStore = imageCaptchaResourceManager.getResourceStore();
        // 添加classpath目录下的 aa.jpg 图片
        resourceStore.addResource(CaptchaTypeConstant.SLIDER, new Resource(ClassPathResourceProvider.NAME, "bgimages/a.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.CONCAT, new Resource(ClassPathResourceProvider.NAME, "bgimages/b.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.SLIDER, new Resource(ClassPathResourceProvider.NAME, "bgimages/c.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.ROTATE, new Resource(ClassPathResourceProvider.NAME, "bgimages/d.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.SLIDER, new Resource(ClassPathResourceProvider.NAME, "bgimages/e.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(ClassPathResourceProvider.NAME, "bgimages/g.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.CONCAT, new Resource(ClassPathResourceProvider.NAME, "bgimages/h.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.ROTATE, new Resource(ClassPathResourceProvider.NAME, "bgimages/i.jpg"));
        resourceStore.addResource(CaptchaTypeConstant.WORD_IMAGE_CLICK, new Resource(ClassPathResourceProvider.NAME, "bgimages/j.jpg"));

        if (!StringUtils.hasText(type)) {
            type = CaptchaTypeConstant.SLIDER;
        }
        if ("RANDOM".equals(type)) {
            int i = ThreadLocalRandom.current().nextInt(0, 4);
            if (i == 0) {
                type = CaptchaTypeConstant.SLIDER;
            } else if (i == 1) {
                type = CaptchaTypeConstant.CONCAT;
            } else if (i == 2) {
                type = CaptchaTypeConstant.ROTATE;
            } else {
                type = CaptchaTypeConstant.WORD_IMAGE_CLICK;
            }
        }
        // 生成滑块图片
        ImageCaptchaInfo imageCaptchaInfo = imageCaptchaGenerator.generateCaptchaImage(type);
        CaptchaResponse<ImageCaptchaInfo> captchaInfoMap = new CaptchaResponse<>();
        String UID = UUID.randomUUID().toString().replace("-", "");
        captchaInfoMap.setId(UID);
        captchaInfoMap.setCaptcha(imageCaptchaInfo);
        session.setAttribute(UID, imageCaptchaInfo);
        return captchaInfoMap;
    }

    @PostMapping("/check")
    @ResponseBody
    public ApiResponse<?> checkCaptcha(HttpSession session, @RequestBody String data) {
        JSONObject json = JSONObject.parseObject(data);
        ImageCaptchaTrack track = JSONObject.parseObject(json.getString("data"), ImageCaptchaTrack.class);
        ImageCaptchaValidator imageCaptchaValidator = new BasicCaptchaTrackValidator();
        ImageCaptchaInfo info = (ImageCaptchaInfo) session.getAttribute(json.getString("id"));
        // 这个map数据应该存到缓存中，校验的时候需要用到该数据
        Map<String, Object> map = imageCaptchaValidator.generateImageCaptchaValidData(info);
        ImageCaptchaValidator sliderCaptchaValidator = new BasicCaptchaTrackValidator();
        return sliderCaptchaValidator.valid(track, map);
    }
}
