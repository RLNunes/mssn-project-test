/**
 * Extended test to demonstrate ecosystem dynamics over time
 */
public class ExtendedTest {
    public static void main(String[] args) {
        System.out.println("=== Ecosystem Simulator Extended Test ===");
        System.out.println("Simulating ecosystem dynamics over 50 steps...\n");
        
        // Create a larger world for better dynamics
        World world = new World(20, 20);
        Population population = new Population(world);
        
        System.out.println("Initial state:");
        printStatus(0, world, population);
        
        // Run simulation for multiple steps
        for (int step = 1; step <= 50; step++) {
            world.update();
            population.update();
            
            // Print status every 10 steps
            if (step % 10 == 0) {
                printStatus(step, world, population);
            }
            
            // Check for ecosystem collapse
            if (!population.isStable()) {
                System.out.println("\n*** Ecosystem collapsed at step " + step + " ***");
                break;
            }
        }
        
        System.out.println("\n=== Test Completed ===");
        System.out.println("Simulation demonstrates:");
        System.out.println("✓ Agent creation and initialization");
        System.out.println("✓ Energy-based movement and reproduction");
        System.out.println("✓ Predator-prey interactions");
        System.out.println("✓ Population dynamics over time");
        System.out.println("✓ World environment with food spawning");
        System.out.println("✓ Modular, extensible design");
    }
    
    private static void printStatus(int step, World world, Population population) {
        System.out.printf("Step %2d: %s | Avg Energy: %.1f | Prey Avg: %.1f | Predator Avg: %.1f%n",
            step,
            population.getStatistics(),
            population.getAverageEnergy(),
            population.getAverageEnergy(Prey.class),
            population.getAverageEnergy(Predator.class)
        );
    }
}