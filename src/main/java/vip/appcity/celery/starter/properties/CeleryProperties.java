package vip.appcity.celery.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author songjie
 */
@Data
@ConfigurationProperties(prefix = "celery")
public class CeleryProperties {
    private String queue = "celery";
    private Integer concurrency = 4;
    private String broker = "amqp://guest:guest@localhost/";
    private String backend = "";

}
