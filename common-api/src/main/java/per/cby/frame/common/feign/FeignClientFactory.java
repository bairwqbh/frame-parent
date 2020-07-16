package per.cby.frame.common.feign;

import org.springframework.beans.factory.FactoryBean;

import per.cby.frame.common.util.ReflectUtil;

import feign.Feign;
import feign.Feign.Builder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;

/**
 * Feign客户端构建工厂
 * 
 * @author chenboyang
 *
 * @param <T> 客户端类型
 */
@SuppressWarnings("unchecked")
public abstract class FeignClientFactory<T> implements FactoryBean<T> {

    @Override
    public T getObject() throws Exception {
        return build();
    }

    @Override
    public Class<?> getObjectType() {
        return clientClass();
    }

    /**
     * 构建客户端实例
     * 
     * @return 客户端实例
     */
    protected T build() {
        return builder().target(clientClass(), url());
    }

    /**
     * feign构造器
     * 
     * @return 构造器
     */
    protected Builder builder() {
        return Feign.builder().encoder(encoder()).decoder(decoder());
    }

    /**
     * 编码器
     * 
     * @return 编码器
     */
    protected Encoder encoder() {
        return new JacksonEncoder();
    }

    /**
     * 解码器
     * 
     * @return 解码器
     */
    protected Decoder decoder() {
        return new JacksonDecoder();
    }

    /**
     * 访问地址
     * 
     * @return 地址
     */
    protected abstract String url();

    /**
     * 客户端类型
     * 
     * @return 类型
     */
    protected Class<T> clientClass() {
        return (Class<T>) ReflectUtil.getParameterizedType(getClass());
    }

}
