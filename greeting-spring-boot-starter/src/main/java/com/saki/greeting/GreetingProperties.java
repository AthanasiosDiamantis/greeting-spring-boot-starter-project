package com.saki.greeting;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Configuration properties for the Greeting Starter.
 * Konfigurationseigenschaften für den Greeting Starter.
 *
 * <p>All properties with the prefix {@code greeting} are automatically
 * bound to this class.</p>
 * <p>Alle Properties mit dem Prefix {@code greeting} werden automatisch
 * an diese Klasse gebunden.</p>
 *
 * <h2>Example application.properties:</h2>
 * <pre>
 * greeting.message=Hello
 * greeting.uppercase=true
 * greeting.prefix=[VIP]
 * greeting.enabled=true
 * greeting.format.emoji=👋
 * greeting.format.separator=,
 * </pre>
 *
 * @author saki
 * @see GeetingService
 * @see GreetingAutoConfiguration
 */
@ConfigurationProperties(prefix = "geeting")
public class GreetingProperties {

    /**
     * Defautlt grreing message / Standard-Begrüßungsnachricht
     */
    private String message = "Hello";

    /**
     * Output in uppercase letters /Ausgabe in Großbuchstaben
     */
    private boolean uppercase = false;

    /**
     * Optional prefix before the message / Optionaler Prefix vor der Message
     */
    private String prefix = "";

    /**
     * Enables or disables the greeting service / Aktiviert oder deaktiviert den Service
     */
    private boolean enabled = true;

    /**
     * Format options for extended customization / Format-Optionen für erweiterte Anpassung
     */
    private Format format = new Format();

    // ==================== GETTERS & SETTERS ====================

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isUppercase() {
        return uppercase;
    }

    public void setUppercase(boolean uppercase) {
        this.uppercase = uppercase;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Format getFormat() {
        return format;
    }

    public void setFormat(Format format) {
        this.format = format;
    }


    // ==================== NESTED CLASS ====================

    /**
     * Nested configuration for format options.
     * Konfigurierbar über {@code greeting.format.*}
     */
    public static class Format {

        /**
         * Optional emoji added to the greeting / Optionales Emoji zur Begrüßung
         */
        private String emoji = "";

        /**
         * Separator between message and name / Separator zwischen Message und Name
         */
        private String separator = ", ";

        /**
         * Suffix after the name / Suffix nach dem Namen
         */
        private String suffix = "!";

        public String getEmoji() {
            return emoji;
        }

        public void setEmoji(String emoji) {
            this.emoji = emoji;
        }

        public String getSeparator() {
            return separator;
        }

        public void setSeparator(String separator) {
            this.separator = separator;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }
    }


}
