package com.saki.greeting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.AutoConfigurations;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests for {@link GreetingAutoConfiguration}.
 * Verwendet {@link ApplicationContextRunner} - den empfohlenen Weg
 * um Spring Boot Auto-Configuration zu testen.
 *
 * @author saki
 */
@DisplayName("GreetingAutoConfiguration")
class GreetingAutoConfigurationTest {

    /**
     * ApplicationContextRunner with our Auto-Configuration.
     * ApplicationContextRunner mit unserer Auto-Configuration.
     * Used as the base for all tests / Wird als Basis für alle Tests verwendet.
     */
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner()
            .withConfiguration(AutoConfigurations.of(GreetingAutoConfiguration.class));

    @Nested
    @DisplayName("Bean creation / Bean-Erstellung")
    class BeanCreation {

        @Test
        @DisplayName("should create GreetingService bean")
        void shouldCreateGreetingServiceBean() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(GreetingService.class);
                assertThat(context).hasSingleBean(DefaultGreetingService.class);
            });
        }

        @Test
        @DisplayName("should create GreetingProperties bean")
        void shouldCreateGreetingPropertiesBean() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(GreetingProperties.class);
            });
        }

        @Test
        @DisplayName("GreetingService should work correctly")
        void greetingServiceShouldWork() {
            contextRunner.run(context -> {
                GreetingService service = context.getBean(GreetingService.class);
                assertThat(service.greet("Test")).isEqualTo("Hello, Test!");
            });
        }
    }

    @Nested
    @DisplayName("Property binding / Property-Bindung")
    class PropertyBinding {

        @Test
        @DisplayName("should bind greeting.message")
        void shouldBindMessage() {
            contextRunner
                    .withPropertyValues("greeting.message=Guten Tag")
                    .run(context -> {
                        GreetingService service = context.getBean(GreetingService.class);
                        assertThat(service.greet("Franz")).isEqualTo("Guten Tag, Franz!");
                    });
        }

        @Test
        @DisplayName("should bind greeting.prefix")
        void shouldBindPrefix() {
            contextRunner
                    .withPropertyValues("greeting.prefix=[VIP]")
                    .run(context -> {
                        GreetingService service = context.getBean(GreetingService.class);
                        assertThat(service.greet("Franz")).isEqualTo("[VIP] Hello, Franz!");
                    });
        }

        @Test
        @DisplayName("should bind greeting.uppercase")
        void shouldBindUppercase() {
            contextRunner
                    .withPropertyValues("greeting.uppercase=true")
                    .run(context -> {
                        GreetingService service = context.getBean(GreetingService.class);
                        assertThat(service.greet("Franz")).isEqualTo("HELLO, FRANZ!");
                    });
        }

        @Test
        @DisplayName("should bind nested greeting.format.* properties")
        void shouldBindNestedFormatProperties() {
            contextRunner
                    .withPropertyValues(
                            "greeting.format.emoji=👋",
                            "greeting.format.separator= -> ",
                            "greeting.format.suffix=???"
                    )
                    .run(context -> {
                        GreetingService service = context.getBean(GreetingService.class);
                        assertThat(service.greet("Franz")).isEqualTo("👋 Hello->Franz??? 👋");
                    });
        }
    }

    @Nested
    @DisplayName("@ConditionalOnProperty (greeting.enabled)")
    class ConditionalOnProperty {

        @Test
        @DisplayName("should create bean when enabled is not set")
        void shouldCreateBeanWhenEnabledNotSet() {
            contextRunner.run(context -> {
                assertThat(context).hasSingleBean(GreetingService.class);
            });
        }

        @Test
        @DisplayName("should create bean when enabled=true")
        void shouldCreateBeanWhenEnabledTrue() {
            contextRunner
                    .withPropertyValues("greeting.enabled=true")
                    .run(context -> {
                        assertThat(context).hasSingleBean(GreetingService.class);
                    });
        }

        @Test
        @DisplayName("should NOT create bean when enabled=false")
        void shouldNotCreateBeanWhenEnabledFalse() {
            contextRunner
                    .withPropertyValues("greeting.enabled=false")
                    .run(context -> {
                        assertThat(context).doesNotHaveBean(GreetingService.class);
                        assertThat(context).doesNotHaveBean(DefaultGreetingService.class);
                    });
        }
    }

    @Nested
    @DisplayName("@ConditionalOnMissingBean (overridability)")
    class ConditionalOnMissingBean {

        @Test
        @DisplayName("should prefer custom implementation over default")
        void shouldPreferCustomImplementation() {
            contextRunner
                    .withUserConfiguration(CustomGreetingServiceConfig.class)
                    .run(context -> {
                        // There is a GreetingService... / Es gibt einen GreetingService...
                        assertThat(context).hasSingleBean(GreetingService.class);
                        // ...but NOT our default! / ...aber NICHT unseren Default!
                        assertThat(context).doesNotHaveBean(DefaultGreetingService.class);

                        // The custom implementation is used / Die Custom-Implementierung wird genutzt
                        GreetingService service = context.getBean(GreetingService.class);
                        assertThat(service.greet("Test")).isEqualTo("CUSTOM: Test");
                    });
        }

        /**
         * Test configuration with custom GreetingService.
         * Test-Konfiguration mit eigenem GreetingService.
         */
        @Configuration(proxyBeanMethods = false)
        static class CustomGreetingServiceConfig {

            @Bean
            GreetingService greetingService() {
                // Lambda as implementation / Lambda als Implementierung
                return name -> "CUSTOM: " + name;
            }
        }
    }

    @Nested
    @DisplayName("Context lifecycle / Context-Lebenszyklus")
    class ContextLifecycle {

        @Test
        @DisplayName("should start context without errors")
        void shouldStartContextCleanly() {
            contextRunner.run(context -> {
                assertThat(context).hasNotFailed();
            });
        }
    }
}