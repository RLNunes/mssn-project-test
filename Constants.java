/**
 * Constants for the ecosystem simulator
 * Contains configuration parameters that can be easily modified
 */
public class Constants {
    // World dimensions
    public static final int WORLD_WIDTH = 80;
    public static final int WORLD_HEIGHT = 60;
    public static final int CELL_SIZE = 10;
    
    // Screen dimensions
    public static final int SCREEN_WIDTH = WORLD_WIDTH * CELL_SIZE;
    public static final int SCREEN_HEIGHT = WORLD_HEIGHT * CELL_SIZE;
    
    // Population settings
    public static final int INITIAL_PREY_COUNT = 150; // Increased
    public static final int INITIAL_PREDATOR_COUNT = 5; // Further reduced
    
    // Energy settings
    public static final int PREY_INITIAL_ENERGY = 35; // Increased
    public static final int PREDATOR_INITIAL_ENERGY = 40; // Reduced
    public static final int PREY_REPRODUCTION_THRESHOLD = 40;
    public static final int PREDATOR_REPRODUCTION_THRESHOLD = 100; // Further increased
    public static final int FOOD_ENERGY_VALUE = 20; // Increased
    public static final int PREY_ENERGY_VALUE = 25; // Reduced
    
    // Movement costs
    public static final int MOVEMENT_ENERGY_COST = 1;
    public static final int REPRODUCTION_ENERGY_COST = 15; // Reduced
    
    // Food settings
    public static final double FOOD_SPAWN_PROBABILITY = 0.03; // Further increased
    public static final int MAX_FOOD_PER_CELL = 1;
    
    // Obstacle settings
    public static final double OBSTACLE_DENSITY = 0.05;
    
    // Colors (Processing compatible)
    public static final int COLOR_EMPTY = 0xFF228B22;     // Forest green background
    public static final int COLOR_FOOD = 0xFF00FF00;      // Bright green food
    public static final int COLOR_OBSTACLE = 0xFF654321;  // Brown obstacles
    public static final int COLOR_PREY = 0xFF0000FF;      // Blue prey
    public static final int COLOR_PREDATOR = 0xFFFF0000;  // Red predators
}