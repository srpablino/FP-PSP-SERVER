package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

    private Client client = new Client();

    private Templates templates = new Templates();

    private Mail mail = new Mail();

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Templates getTemplates() {
        return templates;
    }

    public void setTemplates(Templates templates) {
        this.templates = templates;
    }

    public Mail getMail() {
        return mail;
    }

    public void setMail(Mail mail) {
        this.mail = mail;
    }

    public static class Client {
        private String loginUrl;

        public String getLoginUrl() {
            return loginUrl;
        }

        public void setLoginUrl(String loginUrl) {
            this.loginUrl = loginUrl;
        }

    }

    public static class Templates {
        private String resetMail;

        public String getResetMail() {
            return resetMail;
        }

        public void setResetMail(String resetMail) {
            this.resetMail = resetMail;
        }

    }

    public static class Mail {
        private String host;
        private String username;
        private String password;
        private String smtpSocketFactoryPort;
        private String smtpSocketFactoryFallback;
        private String smtpSocketFactoryClass;
        private String smtpAuth;
        private String smtpSslEnable;
        private String smtpStarttlsEnable;
        private String transportProtocol;
        private String debug;

        public String getHost() {
            return host;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getSmtpSocketFactoryPort() {
            return smtpSocketFactoryPort;
        }

        public void setSmtpSocketFactoryPort(String smtpSocketFactoryPort) {
            this.smtpSocketFactoryPort = smtpSocketFactoryPort;
        }

        public String getSmtpSocketFactoryFallback() {
            return smtpSocketFactoryFallback;
        }

        public void setSmtpSocketFactoryFallback(
                String smtpSocketFactoryFallback) {
            this.smtpSocketFactoryFallback = smtpSocketFactoryFallback;
        }

        public String getSmtpSocketFactoryClass() {
            return smtpSocketFactoryClass;
        }

        public void setSmtpSocketFactoryClass(String smtpSocketFactoryClass) {
            this.smtpSocketFactoryClass = smtpSocketFactoryClass;
        }

        public String getSmtpAuth() {
            return smtpAuth;
        }

        public void setSmtpAuth(String smtpAuth) {
            this.smtpAuth = smtpAuth;
        }

        public String getSmtpSslEnable() {
            return smtpSslEnable;
        }

        public void setSmtpSslEnable(String smtpSslEnable) {
            this.smtpSslEnable = smtpSslEnable;
        }

        public String getSmtpStarttlsEnable() {
            return smtpStarttlsEnable;
        }

        public void setSmtpStarttlsEnable(String smtpStarttlsEnable) {
            this.smtpStarttlsEnable = smtpStarttlsEnable;
        }

        public String getTransportProtocol() {
            return transportProtocol;
        }

        public void setTransportProtocol(String transportProtocol) {
            this.transportProtocol = transportProtocol;
        }

        public String getDebug() {
            return debug;
        }

        public void setDebug(String debug) {
            this.debug = debug;
        }

    }

}
