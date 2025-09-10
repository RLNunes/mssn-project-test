/**
 * Text-based visualization demo of the ecosystem
 * Shows a simple ASCII representation of the simulation
 */
public class VisualizationDemo {
    public static void main(String[] args) {
        System.out.println("=== Ecosystem Simulator - Visualization Demo ===");
        System.out.println("Legend: P = Prey, X = Predator, + = Food, # = Obstacle, . = Empty\n");
        
        // Create a smaller world for visualization
        World world = new World(20, 15);
        Population population = new Population(world);
        
        // Show initial state
        System.out.println("Initial State:");
        printWorld(world, population);
        System.out.println("Population: " + population.getStatistics());
        
        // Run a few simulation steps
        for (int step = 1; step <= 3; step++) {
            System.out.println("\n" + "=".repeat(50));
            System.out.println("After Step " + step + ":");
            
            world.update();
            population.update();
            
            printWorld(world, population);
            System.out.println("Population: " + population.getStatistics());
            
            if (!population.isStable()) {
                System.out.println("Ecosystem collapsed!");
                break;
            }
        }
        
        System.out.println("\n=== Demo Complete ===");
        System.out.println("In the Processing visualization:");
        System.out.println("- Prey appear as BLUE circles");
        System.out.println("- Predators appear as RED circles");
        System.out.println("- Food appears as bright GREEN squares");
        System.out.println("- Obstacles appear as BROWN squares");
        System.out.println("- Background is forest GREEN");
    }
    
    private static void printWorld(World world, Population population) {
        char[][] display = new char[world.getWidth()][world.getHeight()];
        
        // Initialize with world state
        for (int x = 0; x < world.getWidth(); x++) {
            for (int y = 0; y < world.getHeight(); y++) {
                Cell cell = world.getCell(x, y);
                if (cell != null) {
                    switch (cell.getType()) {
                        case FOOD:
                            display[x][y] = '+';
                            break;
                        case OBSTACLE:
                            display[x][y] = '#';
                            break;
                        default:
                            display[x][y] = '.';
                            break;
                    }
                }
            }
        }
        
        // Add agents (agents override world tiles)
        for (Agent agent : population.getAgents()) {
            int x = agent.getX();
            int y = agent.getY();
            if (x >= 0 && x < world.getWidth() && y >= 0 && y < world.getHeight()) {
                if (agent instanceof Prey) {
                    display[x][y] = 'P';
                } else if (agent instanceof Predator) {
                    display[x][y] = 'X';
                }
            }
        }
        
        // Print the display
        for (int y = 0; y < world.getHeight(); y++) {
            for (int x = 0; x < world.getWidth(); x++) {
                System.out.print(display[x][y] + " ");
            }
            System.out.println();
        }
    }
}