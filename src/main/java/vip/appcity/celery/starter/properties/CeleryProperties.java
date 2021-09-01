package vip.appcity.celery.starter.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author songjie
 */
@Data
@ConfigurationProperties(prefix = "celery")
public class CeleryProperties {
    private String queue ;
    private Integer concurrency ;
    private String broker ;
    private String backend ;

}
