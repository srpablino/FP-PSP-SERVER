package py.org.fundacionparaguaya.pspserver.config;

import java.util.Locale;

import javax.validation.MessageInterpolator;

import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
/**
 *
 * @author mgonzalez
 *
 */
public class LocaleAwareMessageInterpolator implements MessageInterpolator {

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
