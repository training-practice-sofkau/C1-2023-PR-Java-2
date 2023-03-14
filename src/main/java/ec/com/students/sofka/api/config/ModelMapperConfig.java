package ec.com.students.sofka.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {
        @Bean
        public ModelMapperConfig modelMapper(){
            return new ModelMapperConfig();

    }
}
