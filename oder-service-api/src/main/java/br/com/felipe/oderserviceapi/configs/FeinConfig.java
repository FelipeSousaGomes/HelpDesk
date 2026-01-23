package br.com.felipe.oderserviceapi.configs;

import br.com.felipe.oderserviceapi.decoders.RetrieveMessageErroDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeinConfig {

    @Bean
    public ErrorDecoder errorDecoder(){
        return new RetrieveMessageErroDecoder();
    }

}
