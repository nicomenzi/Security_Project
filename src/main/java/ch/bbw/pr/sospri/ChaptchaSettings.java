package ch.bbw.pr.sospri;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.recaptcha.key")
public class ChaptchaSettings {

        private String site;
        private String secret;

        public ChaptchaSettings() {
        }

        public ChaptchaSettings(String site, String secret) {
            this.site = site;
            this.secret = secret;
        }

        public String getSite() {
            return site;
        }

        public String getSecret() {
            return secret;
        }

        public void setSite(String site) {
            this.site = site;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
}
