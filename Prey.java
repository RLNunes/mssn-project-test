/**
 * Prey agents that move around, eat food, reproduce, and can be hunted by predators
 * Rendered as blue circles
 */
public class Prey extends Agent {
    
    /**
     * Create a new prey agent
     * @param x Initial X position
     * @param y Initial Y position
     * @param world Reference to the world
     */
    public Prey(int x, int y, World world) {
        super(x, y, Constants.PREY_INITIAL_ENERGY, world);
    }
    
    @Override
    public void update() {
        if (!isAlive()) {
            return;
        }
        
        // Try to eat food if available at current position
        eatFood();
        
        // Try to reproduce if energy is sufficient
        if (canReproduce()) {
            Agent offspring = reproduce();
            if (offspring != null && population != null) {
                population.addAgent(offspring);
            }
        }
        
        // Move towards food or randomly
        if (!moveTowardsFood()) {
            moveRandomly();
        }
    }
    
    /**
     * Eat food from the current cell if available
     * @return True if food was consumed
     */
    private boolean eatFood() {
        Cell currentCell = world.getCell(x, y);
        if (currentCell != null && currentCell.hasFood()) {
            int foodEnergy = currentCell.consumeFood();
            addEnergy(foodEnergy);
            return true;
        }
        return false;
    }
    
    /**
     * Move towards the nearest food within a reasonable search radius
     * @return True if movement towards food was successful
     */
    private boolean moveTowardsFood() {
        int searchRadius = 5;
        int[] nearestFood = findNearestFood(searchRadius);
        
        if (nearestFood != null) {
            return moveTowards(nearestFood[0], nearestFood[1]);
        }
        return false;
    }
    
    /**
     * Find the nearest food within the specified radius
     * @param radius Search radius
     * @return Coordinates of nearest food [x, y], or null if none found
     */
    private int[] findNearestFood(int radius) {
        int[] nearestFood = null;
        double nearestDistance = Double.MAX_VALUE;
        
        int minX = Math.max(0, x - radius);
        int maxX = Math.min(world.getWidth() - 1, x + radius);
        int minY = Math.max(0, y - radius);
        int maxY = Math.min(world.getHeight() - 1, y + radius);
        
        for (int fx = minX; fx <= maxX; fx++) {
            for (int fy = minY; fy <= maxY; fy++) {
                Cell cell = world.getCell(fx, fy);
                if (cell != null && cell.hasFood()) {
                    double distance = Math.sqrt(Math.pow(fx - x, 2) + Math.pow(fy - y, 2));
                    if (distance < nearestDistance) {
                        nearestDistance = distance;
                        nearestFood = new int[]{fx, fy};
                    }
                }
            }
        }
        
        return nearestFood;
    }
    
    @Override
    public boolean canReproduce() {
        return energy >= Constants.PREY_REPRODUCTION_THRESHOLD;
    }
    
    @Override
    public Agent reproduce() {
        if (!canReproduce() || !useEnergy(Constants.REPRODUCTION_ENERGY_COST)) {
            return null;
        }
        
        // Find a suitable nearby location for offspring
        int[] birthLocation = findBirthLocation();
        if (birthLocation != null) {
            return new Prey(birthLocation[0], birthLocation[1], world);
        }
        return null;
    }
    
    /**
     * Find a suitable location near the parent for offspring
     * @return Coordinates for offspring [x, y], or null if no suitable location
     */
    private int[] findBirthLocation() {
        // Try to place offspring in an adjacent empty cell
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                
                int newX = x + dx;
                int newY = y + dy;
                
                if (world.isPassable(newX, newY)) {
                    // Check if position is not occupied by another agent
                    boolean occupied = false;
                    if (population != null) {
                        for (Agent agent : population.getAgents()) {
                            if (agent.getX() == newX && agent.getY() == newY) {
                                occupied = true;
                                break;
                            }
                        }
                    }
                    
                    if (!occupied) {
                        return new int[]{newX, newY};
                    }
                }
            }
        }
        return null;
    }
    
    @Override
    public void render(int screenX, int screenY) {
        // This method signature is for compatibility with Processing
        // The actual rendering will be handled by the main application
    }
    
    @Override
    public int getColor() {
        return Constants.COLOR_PREY;
    }
    
    /**
     * Get energy as a percentage for visualization purposes
     * @return Energy level as percentage (0.0 to 1.0)
     */
    public double getEnergyPercentage() {
        return Math.min(1.0, energy / (double)Constants.PREY_REPRODUCTION_THRESHOLD);
    }
}