package vip.appcity.celery.starter.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.util.StringUtils;
import vip.appcity.celery.Celery;
import vip.appcity.celery.CeleryTaskProducer;
import vip.appcity.celery.starter.properties.CeleryProperties;

/**
 * @author songjie
 */
@Slf4j
@EnableConfigurationProperties(value = CeleryProperties.class)
@Configuration
@ConditionalOnProperty(value = "celery.enabled", havingValue = "true", matchIfMissing = false)
public class CeleryAutoConfiguration {
    public static final String QUEUE = "celery";
    public static final Integer CONCURRENCY = 4;
    public static final String BROKER = "amqp://guest:guest@localhost/";

    @Bean
    public Celery getCelery(CeleryProperties celeryProperties){

        Celery.CeleryBuilder builder = Celery.builder().brokerUri(celeryProperties.getBroker());
        if(!StringUtils.isEmpty(celeryProperties.getBroker())) {
            builder.brokerUri(celeryProperties.getBroker());
            log.info("[celery] use broker: {}", celeryProperties.getBroker());
        }else{
            builder.brokerUri(BROKER);
            log.debug("[celery] use default broker:{}", BROKER);
        }

        if(!StringUtils.isEmpty(celeryProperties.getBackend())) {
            builder.backendUri(celeryProperties.getBackend());
            log.info("[celery] use backend: {}", celeryProperties.getBackend());
        }else{
            log.debug("[celery] dont use backend");
        }

        if(!StringUtils.isEmpty(celeryProperties.getQueue())) {
            builder.queue(celeryProperties.getQueue());
            log.info("[celery] use queue: {}", celeryProperties.getQueue());
        }else{
            builder.queue(QUEUE);
            log.warn("[celery] use default queue: {}", QUEUE);
        }
        log.info("[celery] init finished");
        return builder.build();
    }

    @Bean
    @DependsOn({"celery"})
    @ConditionalOnProperty(value = "snp.enableMultiQueue", havingValue = "true", matchIfMissing = false)
    public CeleryTaskProducer getCeleryTaskProducer(CeleryProperties celeryProperties, Celery defaultCelery){
        log.info("[celery] enable multi queue");
        CeleryTaskProducer celeryTaskProducer = new CeleryTaskProducer();
        celeryTaskProducer.setTaskQueueMaps(celeryProperties.getTaskQueueMaps());
        celeryTaskProducer.setBroker(celeryProperties.getBroker());
        celeryTaskProducer.setBackend(celeryProperties.getBackend());
        celeryTaskProducer.setDefaultQueueName(celeryProperties.getQueue());
        celeryTaskProducer.addQueueClient(celeryProperties.getQueue(), defaultCelery);
        return celeryTaskProducer;
    }
}
