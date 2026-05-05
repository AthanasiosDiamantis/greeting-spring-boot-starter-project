package com.saki.greeting;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-Configuration for the Greeting Spring Boot Starter.
 *
 * <p>This class is automatically loaded by Spring Boot when:</p>
 * <ul>
 *   <li>The starter is on the classpath/li>
 *   <li>{@link GreetingService} class is available</li>
 *   <li>{@code greeting.enabled} is not explicitly set to false</li>
 * </ul>
 *
 * @author saki
 * @see GreetingService
 * @see GreetingProperties
 */
@AutoConfiguration
@ConditionalOnClass(GreetingService.class)
@EnableConfigurationProperties(GreetingProperties.class)
public class GreetingAutoConfiguration {

    /**
     * Creates the {@link GreetingService} Bean.
     *
     * <p><b>@ConditionalOnMissingBean:</b> Only created if the user has not
     * provided a custom implementation.</p>
     *
     * <p><b>@ConditionalOnProperty:</b> Only created if not explicitly disabled.
     * {@code matchIfMissing = true} means: enabled by default!</p>
     */
    @Bean
    @ConditionalOnMissingBean(GreetingService.class)
    @ConditionalOnProperty(
            prefix = "greeting",
            name = "enabled",
            havingValue = "true",
            matchIfMissing = true
    )
    public GreetingService greetingService(GreetingProperties properties) {
        return new DefaultGreetingService(properties);
    }
}