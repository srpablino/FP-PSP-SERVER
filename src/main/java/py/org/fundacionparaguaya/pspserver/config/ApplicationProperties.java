package py.org.fundacionparaguaya.pspserver.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("application")
public class ApplicationProperties {

    private Client client = new Client();

    private Templates templates = new Templates();

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

}
