package per.cby.frame.web.config;

import java.util.List;

import javax.servlet.ServletException;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mock.web.MockFilterConfig;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import per.cby.frame.web.filter.BodyReaderFilter;
import per.cby.frame.web.filter.RestFilter;
import per.cby.frame.web.filter.XssFilter;
import per.cby.frame.web.resolver.MultiRequestBodyResolver;
import per.cby.frame.web.resolver.RequestJsonResolver;
import per.cby.frame.web.resolver.RequestModelResolver;

/**
 * MVC配置类
 * 
 * @author chenboyang
 *
 */
@ConditionalOnWebApplication
@Configuration("__MVC_CONFIG__")
@ConditionalOnClass(name = "org.springframework.web.servlet.config.annotation.WebMvcConfigurer")
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 请求体读取过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @ConditionalOnMissingBean(name = "bodyReaderFilter")
    public FilterRegistrationBean<BodyReaderFilter> bodyReaderFilter() {
        FilterRegistrationBean<BodyReaderFilter> filterRegistrator = new FilterRegistrationBean<BodyReaderFilter>();
        filterRegistrator.setFilter(new BodyReaderFilter());
        filterRegistrator.addUrlPatterns("/*");
        return filterRegistrator;
    }

    /**
     * REST请求过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @ConditionalOnMissingBean(name = "restFilter")
    public FilterRegistrationBean<RestFilter> restFilter() {
        FilterRegistrationBean<RestFilter> filterRegistrator = new FilterRegistrationBean<RestFilter>();
        filterRegistrator.setFilter(new RestFilter());
        filterRegistrator.addUrlPatterns("/*");
        return filterRegistrator;
    }

    /**
     * XSS漏洞防御过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @ConditionalOnClass(name = "org.owasp.validator.html.AntiSamy")
    public FilterRegistrationBean<XssFilter> xssFilter() {
        FilterRegistrationBean<XssFilter> filterRegistrator = new FilterRegistrationBean<XssFilter>();
        filterRegistrator.setFilter(new XssFilter());
        filterRegistrator.addUrlPatterns("/*");
        return filterRegistrator;
    }

    /**
     * Druid服务器信息过滤器
     * 
     * @return 过滤器
     */
    @Bean
    @ConditionalOnClass(name = "com.alibaba.druid.support.http.WebStatFilter")
    public FilterRegistrationBean<WebStatFilter> druidFilter() {
        FilterRegistrationBean<WebStatFilter> filterRegistrator = new FilterRegistrationBean<WebStatFilter>();
        try {
            WebStatFilter webStatFilter = new WebStatFilter();
            MockFilterConfig mockFilterConfig = new MockFilterConfig();
            mockFilterConfig.addInitParameter(WebStatFilter.PARAM_NAME_EXCLUSIONS,
                    "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
            webStatFilter.init(mockFilterConfig);
            filterRegistrator.setFilter(webStatFilter);
            filterRegistrator.addUrlPatterns("/*");
        } catch (ServletException e) {
            e.printStackTrace();
        }
        return filterRegistrator;
    }

    /**
     * Druid服务器信息Servlet访问接口
     * 
     * @return Servlet访问接口
     */
    @Bean
    @ConditionalOnClass(name = "com.alibaba.druid.support.http.StatViewServlet")
    public ServletRegistrationBean<StatViewServlet> druidServlet() {
        return new ServletRegistrationBean<StatViewServlet>(new StatViewServlet(), "/druid/*");
    }

    /**
     * 添加参数解析器
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new RequestModelResolver());
        argumentResolvers.add(new RequestJsonResolver());
        argumentResolvers.add(new MultiRequestBodyResolver());
    }

}
