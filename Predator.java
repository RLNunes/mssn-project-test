/**
 * Predator agents that hunt prey, reproduce, and require energy to survive
 * Rendered as red circles
 */
public class Predator extends Agent {
    
    /**
     * Create a new predator agent
     * @param x Initial X position
     * @param y Initial Y position
     * @param world Reference to the world
     */
    public Predator(int x, int y, World world) {
        super(x, y, Constants.PREDATOR_INITIAL_ENERGY, world);
    }
    
    @Override
    public void update() {
        if (!isAlive()) {
            return;
        }
        
        // Try to hunt prey if available at current position or nearby
        if (!huntPrey()) {
            // If no prey to hunt, move towards nearest prey or randomly
            if (!moveTowardsPrey()) {
                moveRandomly();
            }
        }
        
        // Try to reproduce if energy is sufficient
        if (canReproduce()) {
            Agent offspring = reproduce();
            if (offspring != null && population != null) {
                population.addAgent(offspring);
            }
        }
        
        // Predators lose energy faster when not eating
        useEnergy(1); // Additional energy loss for being a predator
    }
    
    /**
     * Hunt for prey at current position or adjacent cells
     * @return True if prey was successfully hunted
     */
    private boolean huntPrey() {
        // First check current position
        Prey preyAtPosition = findPreyAtPosition(x, y);
        if (preyAtPosition != null) {
            eatPrey(preyAtPosition);
            return true;
        }
        
        // Check adjacent positions
        for (int[] neighbor : world.getNeighbors(x, y)) {
            Prey nearbyPrey = findPreyAtPosition(neighbor[0], neighbor[1]);
            if (nearbyPrey != null) {
                // Move to prey position and eat it
                if (moveTowards(neighbor[0], neighbor[1])) {
                    eatPrey(nearbyPrey);
                    return true;
                }
            }
        }
        
        return false;
    }
    
    /**
     * Find prey at a specific position
     * @param targetX X coordinate to check
     * @param targetY Y coordinate to check
     * @return Prey at the position, or null if none found
     */
    private Prey findPreyAtPosition(int targetX, int targetY) {
        if (population == null) return null;
        
        for (Agent agent : population.getAgents()) {
            if (agent instanceof Prey && agent.getX() == targetX && agent.getY() == targetY && agent.isAlive()) {
                return (Prey) agent;
            }
        }
        return null;
    }
    
    /**
     * Consume a prey agent
     * @param prey Prey to consume
     */
    private void eatPrey(Prey prey) {
        addEnergy(Constants.PREY_ENERGY_VALUE);
        if (population != null) {
            population.removeAgent(prey);
        }
    }
    
    /**
     * Move towards the nearest prey within hunting range
     * @return True if movement towards prey was successful
     */
    private boolean moveTowardsPrey() {
        int huntingRange = 8; // Predators can detect prey from further away
        Prey nearestPrey = (Prey) findNearestAgent(Prey.class, huntingRange);
        
        if (nearestPrey != null) {
            return moveTowards(nearestPrey.getX(), nearestPrey.getY());
        }
        return false;
    }
    
    @Override
    public boolean canReproduce() {
        return energy >= Constants.PREDATOR_REPRODUCTION_THRESHOLD;
    }
    
    @Override
    public Agent reproduce() {
        if (!canReproduce() || !useEnergy(Constants.REPRODUCTION_ENERGY_COST)) {
            return null;
        }
        
        // Find a suitable nearby location for offspring
        int[] birthLocation = findBirthLocation();
        if (birthLocation != null) {
            return new Predator(birthLocation[0], birthLocation[1], world);
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
        return Constants.COLOR_PREDATOR;
    }
    
    /**
     * Get energy as a percentage for visualization purposes
     * @return Energy level as percentage (0.0 to 1.0)
     */
    public double getEnergyPercentage() {
        return Math.min(1.0, energy / (double)Constants.PREDATOR_REPRODUCTION_THRESHOLD);
    }
    
    /**
     * Check if this predator is actively hunting (has sufficient energy)
     * @return True if predator is in hunting mode
     */
    public boolean isHunting() {
        return energy > Constants.PREDATOR_INITIAL_ENERGY * 0.3; // Hunt when above 30% of initial energy
    }
}