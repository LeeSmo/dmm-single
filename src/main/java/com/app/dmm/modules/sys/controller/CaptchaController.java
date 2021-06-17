package com.app.dmm.modules.sys.controller;

import cn.hutool.core.util.RandomUtil;
import com.app.dmm.core.ApiResult;
import com.app.dmm.core.utils.MD5Util;
import com.app.dmm.core.utils.RandImageUtil;
import com.app.dmm.core.utils.RedisUtil;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

@RestController
@Api(tags = "系统验证码")
public class CaptchaController {

    @Autowired
    private Producer producer;

    @Autowired
    private RedisUtil redisUtil;

    private static final String BASE_CHECK_CODES = "qwertyuiplkjhgfdsazxcvbnmQWERTYUPLKJHGFDSAZXCVBNM1234567890";

    @ApiOperation(value = "获取验证码")
    @GetMapping("/captcha.jpg")
    public void captcha(HttpServletResponse response, HttpServletRequest request) throws IOException {

        response.setHeader("Cache-Control","no-store, no-cache");
        response.setContentType("image/jpeg");
        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存验证码到session
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY,text);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image,"jpg",out);
        IOUtils.closeQuietly(out);
    }
    /**
     * 后台生成图形验证码 ：有效
     * @param response
     * @param key 时间戳
     */
    @ApiOperation("获取验证码")
    @GetMapping(value = "/randomImage/{key}")
    public ApiResult<Object> randomImage(HttpServletResponse response, @PathVariable String key){
        //ApiResult<String> res = new Result<String>();
        try {
            String code = RandomUtil.randomString(BASE_CHECK_CODES,4);
            String lowerCaseCode = code.toLowerCase();
            String realKey = MD5Util.MD5Encode(lowerCaseCode+key, "utf-8");
            redisUtil.set(realKey, lowerCaseCode, 6000);
            String base64 = RandImageUtil.generate(code);
            return ApiResult.OK(base64);
        } catch (Exception e) {
            return ApiResult.error("获取验证码出错"+e.getMessage());
        }
    }
}
