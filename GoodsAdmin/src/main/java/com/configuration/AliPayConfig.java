package com.configuration;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Data
@Component
@ConfigurationProperties(prefix = "alipay")
public class AliPayConfig implements ApplicationRunner {
    //应用id
    @Value("${alipay.appId}")
    private String appId;

    //私钥
    @Value("${alipay.privateKey}")
    private String privateKey;

    //公钥
    @Value("${alipay.publicKey}")
    private String publicKey;

    //支付宝网关
    @Value("${alipay.gateway}")
    private String gateway;

    //支付成功后的接口回调地址
    @Value("${alipay.notifyUrl}")
    private String notifyUrl;

    /**
     *  项目初始化事件
     * */
    @Override
    public void run(ApplicationArguments args) throws Exception {
        //初始化支付宝SDK
        Factory.setOptions(getOptions());
        System.out.println("**********支付宝SDK初始化完成**********");
    }

    private Config getOptions() {
        //这里省略了一些不必要的配置，可参考文档的说明
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = this.gateway;
        config.signType = "RSA2";
        config.appId = this.appId;

        // 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
        config.merchantPrivateKey = this.privateKey;
        //赋值支付宝公钥字符串
        config.alipayPublicKey = this.publicKey;
        //接口回调地址
        config.notifyUrl = notifyUrl;

        return config;
    }

}