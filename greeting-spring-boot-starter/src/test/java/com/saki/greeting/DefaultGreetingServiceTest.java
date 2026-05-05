package com.saki.greeting;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link DefaultGreetingService}.
 *
 * @author saki
 */
@DisplayName("DefaultGreetingService")
class DefaultGreetingServiceTest {

    private GreetingProperties properties;
    private DefaultGreetingService service;

    @BeforeEach
    void setUp() {
        properties = new GreetingProperties();
        service = new DefaultGreetingService(properties);
    }

    @Nested
    @DisplayName("Constructor")
    class ConstructorTest {

        @Test
        @DisplayName("should throw exception for null properties")
        void shouldThrowExceptionForNullProperties() {
            assertThatThrownBy(() -> new DefaultGreetingService(null))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessage("GreetingProperties must not be null");
        }
    }

    @Nested
    @DisplayName("greet() with default properties / mit Standard-Properties")
    class GreetWithDefaults {

        @Test
        @DisplayName("should return default greeting")
        void shouldReturnDefaultGreeting() {
            assertThat(service.greet("Karl")).isEqualTo("Hello, Karl!");
        }

        @Test
        @DisplayName("should trim whitespace in name")
        void shouldTrimWhitespaceInName() {
            assertThat(service.greet("  Franz  ")).isEqualTo("Hello, Franz!");
        }
    }

    @Nested
    @DisplayName("greet() with custom properties / mit eigenen Properties")
    class GreetWithCustomProperties {

        @Test
        @DisplayName("should use custom message")
        void shouldUseCustomMessage() {
            properties.setMessage("Guten Tag");
            assertThat(service.greet("Franz")).isEqualTo("Guten Tag, Franz!");
        }

        @Test
        @DisplayName("should prepend prefix")
        void shouldPrependPrefix() {
            properties.setPrefix("[VIP]");
            assertThat(service.greet("Franz")).isEqualTo("[VIP] Hello, Franz!");
        }

        @Test
        @DisplayName("should apply uppercase")
        void shouldApplyUppercase() {
            properties.setUppercase(true);
            assertThat(service.greet("Franz")).isEqualTo("HELLO, FRANZ!");
        }

        @Test
        @DisplayName("should add emoji")
        void shouldAddEmoji() {
            properties.getFormat().setEmoji("👋");
            assertThat(service.greet("Franz")).isEqualTo("👋 Hello, Franz! 👋");
        }

        @Test
        @DisplayName("should combine all options")
        void shouldCombineAllOptions() {
            properties.setMessage("Willkommen");
            properties.setPrefix("[PREMIUM]");
            properties.setUppercase(true);
            properties.getFormat().setEmoji("🎉");

            String result = service.greet("Franz");

            assertThat(result).isEqualTo("🎉 [PREMIUM] WILLKOMMEN, FRANZ! 🎉");
        }
    }

    @Nested
    @DisplayName("greet() edge cases / Grenzfälle")
    class GreetEdgeCases {

        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"   ", "\t", "\n"})
        @DisplayName("should throw exception for invalid names")
        void shouldThrowExceptionForInvalidNames(String invalidName) {
            assertThatThrownBy(() -> service.greet(invalidName))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("Name must not be null or empty");
        }

        @Test
        @DisplayName("should handle special characters in name")
        void shouldHandleSpecialCharactersInName() {
            assertThat(service.greet("O'Brien")).isEqualTo("Hello, O'Brien!");
            assertThat(service.greet("名前")).isEqualTo("Hello, 名前!");
        }
    }

}
