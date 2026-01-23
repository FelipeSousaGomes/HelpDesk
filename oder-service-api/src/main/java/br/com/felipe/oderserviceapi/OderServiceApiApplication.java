package br.com.felipe.oderserviceapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class OderServiceApiApplication {



    public static void main(String[] args) {
        SpringApplication.run(OderServiceApiApplication.class, args);
    }

}
