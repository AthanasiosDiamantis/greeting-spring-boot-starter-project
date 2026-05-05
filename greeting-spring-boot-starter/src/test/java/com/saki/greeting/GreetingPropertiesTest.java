package com.saki.greeting;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link GreetingProperties}.
 *
 * @author saki
 */
@DisplayName("GreetingProperties")
public class GreetingPropertiesTest {

    @Nested
    @DisplayName("Default Values")
    class DefaultValues {

        @Test
        @DisplayName("message should default to 'Hello'")
        void messageShouldDefaultToHello() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getMessage()).isEqualTo("Hello");
        }

        @Test
        @DisplayName("uppercase should default to false")
        void uppercaseShouldDefaultToFalse() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.isUppercase()).isFalse();
        }

        @Test
        @DisplayName("prefix should default to empty")
        void prefixShouldDefaultToEmpty() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getPrefix()).isEmpty();
        }

        @Test
        @DisplayName("enabled should default to true")
        void enabledShouldDefaultToTrue() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.isEnabled()).isTrue();
        }

        @Test
        @DisplayName("format should not be null")
        void formatShouldNotBeNull() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getFormat()).isNotNull();
        }

    }

    @Nested
    @DisplayName("Format Default Values")
    class FormatDefaultValues {

        @Test
        @DisplayName("emoji should default to empty")
        void emojiShouldDefaultToEmpty() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getFormat().getEmoji()).isEmpty();
        }

        @Test
        @DisplayName("suffix should default to 'comma and space'")
        void separatorShouldDefaultToCommaSpace() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getFormat().getSeparator()).isEqualTo(", ");
        }

        @Test
        @DisplayName("suffix should default to '!'")
        void suffixShouldDefaultToExclamationPoint() {
            GreetingProperties properties = new GreetingProperties();
            assertThat(properties.getFormat().getSuffix()).isEqualTo("!");
        }

    }

    @Nested
    @DisplayName("Setters and Getters")
    class SettersAndGetters {

        @Test
        @DisplayName("setMessage should store value")
        void setMessageShouldStoreValue() {
            GreetingProperties props = new GreetingProperties();
            props.setMessage("Guten Tag");
            assertThat(props.getMessage()).isEqualTo("Guten Tag");
        }

        @Test
        @DisplayName("format setters should store values")
        void formatSettersShouldStoreValues() {
            GreetingProperties.Format format = new GreetingProperties.Format();
            format.setEmoji("👋");
            format.setSeparator(" -> ");

            assertThat(format.getEmoji()).isEqualTo("👋");
            assertThat(format.getSeparator()).isEqualTo(" -> ");
        }
    }

}
