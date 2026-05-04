package com.saki.greeting;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Auto-Configuration for the Greeting Spring Boot Starter.
 * Auto-Konfiguration für den Greeting Spring Boot Starter.
 *
 * <p>This class is automatically loaded by Spring Boot when:</p>
 * <p>Diese Klasse wird automatisch von Spring Boot geladen wenn:</p>
 * <ul>
 *   <li>The starter is on the classpath / Der Starter im Classpath ist</li>
 *   <li>{@link GreetingService} class is available / Die Klasse verfügbar ist</li>
 *   <li>{@code greeting.enabled} is not explicitly set to false /
 *       nicht explizit auf false gesetzt ist</li>
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
     * Erstellt den {@link GreetingService} Bean.
     *
     * <p><b>@ConditionalOnMissingBean:</b> Only created if the user has not
     * provided a custom implementation.</p>
     * <p>Nur erstellen, wenn der User nicht bereits eine eigene Implementierung
     * bereitgestellt hat.</p>
     *
     * <p><b>@ConditionalOnProperty:</b> Only created if not explicitly disabled.
     * {@code matchIfMissing = true} means: enabled by default!</p>
     * <p>Nur erstellen, wenn nicht explizit deaktiviert.
     * {@code matchIfMissing = true} bedeutet: Standard ist aktiviert!</p>
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