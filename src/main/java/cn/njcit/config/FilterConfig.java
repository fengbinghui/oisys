package cn.njcit.config;

import cn.njcit.filter.AuthorityFilter;
import cn.njcit.filter.CORSFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig{
    @Value("${security.auth-protected-patterns:/studentManage/*,/classManage/*,/courseMan/*,/courseManage/*,/attendance/*,/attendClassSetting/*,/userLoginRecord/*,/userManage/*,/roleManage/*,/menuManage/*}")
    private String[] authProtectedPatterns;

    @Bean
    public FilterRegistrationBean<CORSFilter> corsFilter(){//配置跨域请求过滤器
        FilterRegistrationBean<CORSFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new CORSFilter());
        filterRegistrationBean.addUrlPatterns("*");
        filterRegistrationBean.setOrder(1);
        return filterRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean<AuthorityFilter> authFilter(){ //配置登录验证过滤器
        FilterRegistrationBean<AuthorityFilter> filterRegistrationBean = new FilterRegistrationBean<>();
        filterRegistrationBean.setFilter(new AuthorityFilter());
        filterRegistrationBean.addUrlPatterns(authProtectedPatterns);
        filterRegistrationBean.setOrder(2);
        return filterRegistrationBean;
    }
}