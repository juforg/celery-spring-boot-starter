package vip.appcity.celery.starter.config;

import com.geneea.celery.Celery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vip.appcity.celery.starter.properties.CeleryProperties;

/**
 * @author songjie
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(value = CeleryProperties.class)
public class CeleryAutoConfiguration {

    @Bean
    public Celery getCelery(CeleryProperties celeryProperties){
        log.info("init celery");
        Celery client = Celery.builder()
                .brokerUri(celeryProperties.getBroker())
                .backendUri(celeryProperties.getBackend())
                .queue(celeryProperties.getQueue())
                .build();
        return client;
    }
}
