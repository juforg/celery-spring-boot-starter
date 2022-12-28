# celery-spring-boot-starter

Springboot starter for celery using celery-java,
which is Java implementation of [Celery](https://docs.celeryproject.org/en/v5.1.2/) client and worker. Quoting from the project website:

> Celery is an asynchronous task queue/job queue based on distributed message passing. It is focused on real-time operation, but supports scheduling as well.

>  The execution units, called tasks, are executed concurrently on a single or more worker servers using multiprocessing, Eventlet, or gevent. Tasks can execute asynchronously (in the background) or synchronously (wait until ready).

>  Celery is used in production systems to process millions of tasks a day.

For more info, [celery-java](https://github.com/crabhi/celery-java)

The aim is to be compatible with existing [Python Celery implementation][celery]. That means you should be able
to run a Java client with a Python worker or vice-versa. Tested with Python Celery 5.1.


## Maven dependency

Releases are available from Maven Central. Latest version: [![Maven
Central](https://maven-badges.herokuapp.com/maven-central/vip.appcity/celery-spring-boot-starter/badge.svg)](http://search.maven.org/#search%7Cga%7C1%7Cg%3A%22vip.appcity%22%20AND%20a%3A%celery-spring-boot-starter%22)

```xml
<dependency>
    <groupId>vip.appcity</groupId>
    <artifactId>celery-spring-boot-starter</artifactId>
    <version>...</version>
</dependency>
```

Snapshots are available from [Sonatype OSRH](https://s01.oss.sonatype.org//content/groups/public):

```xml
<repository>
    <id>sonatype</id>
    <url>https://oss.sonatype.org/content/groups/public</url>
    <snapshots>
        <enabled>true</enabled>
        <updatePolicy>always</updatePolicy>
    </snapshots>
</repository>
```


## init config int application.yml
```yaml
celery:
  queue: "demo:celery"
  broker: amqp://guest:guest@localhost:5672/vhost
#  backend:
```

## Calling Python task from Java

1. Start a celery worker as described in [First Steps with Celery][celery-py-start].
2. define a worker task with python
    ```Python
    @celery.task(name='test.dummy_task')
    def dummy_task(num):
        print(num)
        return "finished"
    ```
3. Call the task by name in java
    ```java
    
        @Autowired
        private Celery celery;
        celery.submit("test.dummy_task", new Object[]{1});
    ```


## Relase notes


* 1.0 - Initial release. enable to call  Python task from Java without result.


[celery-py-start]: http://docs.celeryproject.org/en/latest/getting-started/first-steps-with-celery.html
[celery]: http://www.celeryproject.org/
