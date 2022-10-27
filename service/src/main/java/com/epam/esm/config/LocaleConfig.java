package com.epam.esm.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

@Configuration
@ComponentScan(basePackages = "com.epam.esm.config")
public class LocaleConfig implements WebMvcConfigurer {

    @Bean(name = "messageSource")
    public MessageSource getMessageSource() {
        ReloadableResourceBundleMessageSource ret = new ReloadableResourceBundleMessageSource();
        ret.setBasename("classpath:message");
        ret.setCacheSeconds(1);
        ret.setUseCodeAsDefaultMessage(true);
        ret.setDefaultEncoding("utf-8");
        return ret;
    }

    @Bean(name = "localeResolver")
    public AcceptHeaderLocaleResolver localeResolver() {
        final AcceptHeaderLocaleResolver resolver = new AcceptHeaderLocaleResolver();
        resolver.setSupportedLocales(Arrays.asList(new Locale("en_US"), new Locale("ru_RU")));
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
}
