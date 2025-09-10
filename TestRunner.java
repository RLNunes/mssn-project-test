/**
 * Simple test runner to verify that all classes compile correctly
 * This is NOT the main application - use MainApp.java with Processing
 */
public class TestRunner {
    public static void main(String[] args) {
        System.out.println("Testing ecosystem simulator classes...");
        
        try {
            // Test basic class instantiation
            World world = new World(10, 10);
            Population population = new Population(world);
            
            // Test agent creation
            Prey prey = new Prey(5, 5, world);
            prey.setPopulation(population);
            
            Predator predator = new Predator(3, 3, world);
            predator.setPopulation(population);
            
            // Test basic operations
            System.out.println("World created: " + world.getWidth() + "x" + world.getHeight());
            System.out.println("Population stats: " + population.getStatistics());
            System.out.println("Prey energy: " + prey.getEnergy());
            System.out.println("Predator energy: " + predator.getEnergy());
            
            // Test one simulation step
            world.update();
            prey.update();
            predator.update();
            population.update();
            
            System.out.println("After update - Population stats: " + population.getStatistics());
            System.out.println("All classes compiled and basic functionality works!");
            
        } catch (Exception e) {
            System.err.println("Error during testing: " + e.getMessage());
            e.printStackTrace();
        }
    }
}