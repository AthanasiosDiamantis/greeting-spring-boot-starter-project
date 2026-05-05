package com.saki.greeting;

/**
 * Default implementation of {@link GreetingService}.
 *
 * <p>This class is automatically created as a Spring Bean by
 * {@link GreetingAutoConfiguration}, unless another implementation exists.</p>
 *
 * @author saki
 * @see GreetingService
 * @see GreetingProperties
 */
public class DefaultGreetingService implements GreetingService {

    private final GreetingProperties properties;

    /**
     * Constructor injection - best practice in Spring!
     *
     * @param properties the configuration from application.properties
     * @throws IllegalArgumentException if properties is null
     */
    public DefaultGreetingService(GreetingProperties properties) {
        if (properties == null) {
            throw new IllegalArgumentException("GreetingProperties must not be null");
        }
        this.properties = properties;
    }

    @Override
    public String greet(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name must not be null or empty");
        }

        StringBuilder greeting = new StringBuilder();

        // 1. Add prefix if configured / Prefix hinzufügen falls konfiguriert
        String prefix = properties.getPrefix();
        if (prefix != null && !prefix.trim().isEmpty()) {
            greeting.append(prefix).append(" ");
        }

        // 2. Add message / Message hinzufügen
        greeting.append(properties.getMessage());

        // 3. Add separator and name / Separator und Name hinzufügen
        greeting.append(properties.getFormat().getSeparator());
        greeting.append(name.trim());

        // 4. Add suffix / Suffix hinzufügen
        greeting.append(properties.getFormat().getSuffix());

        // 5. Apply uppercase before emoji / Uppercase vor Emoji anwenden
        String result = greeting.toString();
        if (properties.isUppercase()) {
            result = result.toUpperCase();
        }

        // 6. Add emoji if configured / Emoji hinzufügen falls konfiguriert
        String emoji = properties.getFormat().getEmoji();
        if (emoji != null && !emoji.trim().isEmpty()) {
            result = emoji + " " + result + " " + emoji;
        }

        return result;
    }

    /**
     * Returns the current properties (for debugging).
     * Gibt die aktuellen Properties zurück (für Debugging-Zwecke).
     */
    public GreetingProperties getProperties() {
        return properties;
    }
}
