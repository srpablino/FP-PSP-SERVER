package py.org.fundacionparaguaya.pspserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.MessageInterpolator;

@SpringBootApplication
public class PspServerApplication extends SpringBootServletInitializer {

    @Override
    protected SpringApplicationBuilder configure(
            SpringApplicationBuilder application) {
        return application.sources(PspServerApplication.class);
    }

    @Bean
    public LocaleResolver localeResolver() {
        SessionLocaleResolver slr = new SessionLocaleResolver();
        slr.setDefaultLocale(Locale.forLanguageTag("es"));
        return slr;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor() {
            @Override
            public boolean preHandle(HttpServletRequest request,
                    HttpServletResponse response, Object handler)
                    throws ServletException {

                String newLocale = request.getHeader("X-Language");
                if (newLocale != null) {
                    LocaleResolver localeResolver = RequestContextUtils
                            .getLocaleResolver(request);
                    if (localeResolver == null) {
                        throw new IllegalStateException(
                                "No LocaleResolver found: not in a DispatcherServlet request?");
                    }
                    try {
                        localeResolver.setLocale(request, response,
                                parseLocaleValue(newLocale));
                    } catch (IllegalArgumentException ex) {
                        if (isIgnoreInvalidLocale()) {
                            logger.debug("Ignoring invalid locale value ["
                                    + newLocale + "]: " + ex.getMessage());
                        } else {
                            throw ex;
                        }
                    }
                }
                return true;
            }
        };
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
        public String interpolate(final String messageTemplate,
                final Context context) {
            return messageSource.getMessage(messageTemplate, null,
                    LocaleContextHolder.getLocale());
        }

        @Override
        public String interpolate(final String messageTemplate,
                final Context context, final Locale locale) {
            return messageSource.getMessage(messageTemplate, null, locale);
        }
    }

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasenames("i18n/messages");
        messageSource.setFallbackToSystemLocale(false);

        return messageSource;
    }

    @Configuration
    class WebMvcConfig extends WebMvcConfigurerAdapter {

        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(localeChangeInterceptor());
        }

        @Bean
        public LocalValidatorFactoryBean validator() {
            LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
            factory.setMessageInterpolator(
                    (MessageInterpolator) new LocaleAwareMessageInterpolator(
                            messageSource()));
            factory.setValidationMessageSource(messageSource());

            return factory;
        }
    }

}