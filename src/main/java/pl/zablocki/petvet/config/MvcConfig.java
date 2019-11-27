package pl.zablocki.petvet.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.context.support.ResourceBundleMessageSource;

public class MvcConfig {


    @Bean(name = "messageSource")
    public MessageSource reloadableResourceBundleMessageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/error-messages");
        messageSource.setDefaultEncoding("UTF-8");
        return messageSource;
    }
}
