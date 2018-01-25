package py.org.fundacionparaguaya.pspserver.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

}
