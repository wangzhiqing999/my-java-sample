package cn.wzq.studyspringnacos.pojo;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;

@Component
@NacosConfigurationProperties(prefix = "order", dataId = "${nacos.config.data-id}", type=ConfigType.YAML, autoRefreshed = true)
// @ConfigurationProperties(prefix = "order")
public class OrderProperties {

    /**
     * 订单支付超时时长，单位：秒。
     */
    private Integer payTimeoutSeconds;

    /**
     * 订单创建频率，单位：秒
     */
    private Integer createFrequencySeconds;

//    /**
//     * 配置描述
//     */
//    private String desc;

    public Integer getPayTimeoutSeconds() {
        return payTimeoutSeconds;
    }

    public OrderProperties setPayTimeoutSeconds(Integer payTimeoutSeconds) {
        this.payTimeoutSeconds = payTimeoutSeconds;
        return this;
    }

    public Integer getCreateFrequencySeconds() {
        return createFrequencySeconds;
    }

    public OrderProperties setCreateFrequencySeconds(Integer createFrequencySeconds) {
        this.createFrequencySeconds = createFrequencySeconds;
        return this;
    }

//    public String getDesc() {
//        return desc;
//    }
//
//    public OrderProperties setDesc(String desc) {
//        this.desc = desc;
//        return this;
//    }

}
