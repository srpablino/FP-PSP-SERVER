package py.org.fundacionparaguaya.pspserver;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import javax.validation.MessageInterpolator;

@SpringBootApplication
public class PspServerApplication extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(PspServerApplication.class);
	}
	
	@Bean
	public LocaleResolver localeResolver() {
	    SessionLocaleResolver slr = new SessionLocaleResolver();
	    slr.setDefaultLocale(Locale.FRENCH);
	    return slr;
	}
	
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
	    LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
	    lci.setParamName("lang");
	    return lci;
	}
	
	
	
	public static void main(String[] args) {
	 
		SpringApplication.run(PspServerApplication.class, args);
	}
	
	  class LocaleAwareMessageInterpolator implements MessageInterpolator {

	        private MessageSource messageSource;

	        public LocaleAwareMessageInterpolator(MessageSource messageSource) {
	            this.messageSource = messageSource;
	        }

	        @Override
	        public String interpolate(final String messageTemplate, final Context context) {
	            //log.info("LocaleAwareMessageInterpolator.interpolate1()");
	            //System.out.println("locale "+LocaleContextHolder.getLocale());
	            return messageSource.getMessage(messageTemplate, null, LocaleContextHolder.getLocale());
	        }

	        @Override
	        public String interpolate(final String messageTemplate, final Context context, final Locale locale) {
	            //log.info("LocaleAwareMessageInterpolator.interpolate2() - {}", locale);
	            return messageSource.getMessage(messageTemplate, null, locale);
	        }
	    }
	
	 @Configuration
	    class WebMvcConfig extends WebMvcConfigurerAdapter {

	        @Autowired
	        private MessageSource messageSource;

	        @Override
	        public Validator getValidator() {
	            return validator();
	        }

	        @Override
	        public void addInterceptors(InterceptorRegistry registry) {
	            registry.addInterceptor(localeChangeInterceptor());
	        }


	        @Bean
	        public LocalValidatorFactoryBean validator() {
	            LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
	            factory.setMessageInterpolator(new LocaleAwareMessageInterpolator(messageSource));
	            factory.setValidationMessageSource(messageSource);

	            return factory;
	        }
	    }

}