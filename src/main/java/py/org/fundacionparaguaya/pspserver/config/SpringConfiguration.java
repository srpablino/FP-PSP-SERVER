package py.org.fundacionparaguaya.pspserver.config;

import java.util.Properties;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class SpringConfiguration {

    private ApplicationProperties applicationProps;

    public SpringConfiguration(ApplicationProperties applicationProps) {
        this.applicationProps = applicationProps;
    }

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    @Bean
    public JavaMailSenderImpl javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(applicationProps.getMail().getHost());
        mailSender.setUsername(applicationProps.getMail().getUsername());
        mailSender.setPassword(applicationProps.getMail().getPassword());
        mailSender.setPort(Integer.parseInt(
                applicationProps.getMail().getSmtpSocketFactoryPort()));
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol",
                applicationProps.getMail().getTransportProtocol());
        props.put("mail.smtp.auth", applicationProps.getMail().getSmtpAuth());
        props.put("mail.properties.mail.smtp.socketFactory.fallback",
                applicationProps.getMail().getSmtpSocketFactoryFallback());
        props.put("mail.smtp.starttls.enable",
                applicationProps.getMail().getSmtpStarttlsEnable());
        props.put("mail.debug", applicationProps.getMail().getDebug());
        props.put("mail.smtp.socketFactory.class",
                applicationProps.getMail().getSmtpSocketFactoryClass());
        mailSender.setJavaMailProperties(props);

        return mailSender;
    }

}
