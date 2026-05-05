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
     *
     * @param name the name to greet (must not be null or empty)
     * @return the formatted greeting, never null
     * @throws IllegalArgumentException if the name is null or empty
     */
    String greet(String name);
}
