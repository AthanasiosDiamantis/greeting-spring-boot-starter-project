package com.saki.greeting;

/**
 * Service interface for greetings.
 * Defines the contract for all greeting implementations.
 *
 * <p>Usage example:</p>
 * <pre>{@code
 * @Autowired
 * private GreetingService greetingService;
 *
 * String message = greetingService.greet("World");
 * // Output: "Hello, World!"
 * }</pre>
 *
 * @author saki
 * @see DefaultGreetingService
 * @see GreetingProperties
 */
public interface GreetingService {

    /**
     * Creates a greeting for the given name.
     * Erstellt eine Begrüßung für den angegebenen Namen.
     *
     * @param name the name to greet (must not be null or empty)
     *             Der zu begrüßende Name (darf nicht null oder leer sein)
     * @return the formatted greeting, never null
     *         Die formatierte Begrüßung, niemals null
     * @throws IllegalArgumentException if name is null or empty
     *                                  wenn name null oder leer ist
     */
    String greet(String name);
}
