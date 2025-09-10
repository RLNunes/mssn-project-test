import java.util.List;

/**
 * Abstract base class for all agents in the ecosystem
 * Implements common functionality for energy, movement, and basic behaviors
 */
public abstract class Agent implements Updatable, Movable, EnergyBased, Reproductive {
    protected int x, y;           // Position in the world grid
    protected int energy;         // Current energy level
    protected World world;        // Reference to the world
    protected Population population; // Reference to the population manager
    
    /**
     * Create a new agent
     * @param x Initial X position
     * @param y Initial Y position
     * @param initialEnergy Initial energy level
     * @param world Reference to the world
     */
    public Agent(int x, int y, int initialEnergy, World world) {
        this.x = x;
        this.y = y;
        this.energy = initialEnergy;
        this.world = world;
    }
    
    /**
     * Set the population reference (needed for reproduction)
     * @param population Population manager
     */
    public void setPopulation(Population population) {
        this.population = population;
    }
    
    @Override
    public int getX() {
        return x;
    }
    
    @Override
    public int getY() {
        return y;
    }
    
    @Override
    public void moveTo(int newX, int newY) {
        if (world.isPassable(newX, newY)) {
            this.x = newX;
            this.y = newY;
        }
    }
    
    @Override
    public int getEnergy() {
        return energy;
    }
    
    @Override
    public void addEnergy(int amount) {
        this.energy += amount;
    }
    
    @Override
    public boolean useEnergy(int amount) {
        if (energy >= amount) {
            energy -= amount;
            return true;
        }
        return false;
    }
    
    @Override
    public boolean isAlive() {
        return energy > 0;
    }
    
    /**
     * Move the agent randomly to an adjacent passable cell
     * @return True if movement was successful
     */
    protected boolean moveRandomly() {
        List<int[]> passableNeighbors = world.getPassableNeighbors(x, y);
        
        if (!passableNeighbors.isEmpty() && useEnergy(Constants.MOVEMENT_ENERGY_COST)) {
            int[] newPos = passableNeighbors.get((int)(Math.random() * passableNeighbors.size()));
            moveTo(newPos[0], newPos[1]);
            return true;
        }
        return false;
    }
    
    /**
     * Move towards a target position
     * @param targetX Target X coordinate
     * @param targetY Target Y coordinate
     * @return True if movement was successful
     */
    protected boolean moveTowards(int targetX, int targetY) {
        if (!useEnergy(Constants.MOVEMENT_ENERGY_COST)) {
            return false;
        }
        
        List<int[]> passableNeighbors = world.getPassableNeighbors(x, y);
        if (passableNeighbors.isEmpty()) {
            return false;
        }
        
        // Find the neighbor closest to the target
        int[] bestMove = null;
        double bestDistance = Double.MAX_VALUE;
        
        for (int[] neighbor : passableNeighbors) {
            double distance = Math.sqrt(Math.pow(neighbor[0] - targetX, 2) + Math.pow(neighbor[1] - targetY, 2));
            if (distance < bestDistance) {
                bestDistance = distance;
                bestMove = neighbor;
            }
        }
        
        if (bestMove != null) {
            moveTo(bestMove[0], bestMove[1]);
            return true;
        }
        return false;
    }
    
    /**
     * Find the nearest agent of a specific type within a given range
     * @param agentClass Class of agent to search for
     * @param maxDistance Maximum search distance
     * @return Nearest agent of the specified type, or null if none found
     */
    protected Agent findNearestAgent(Class<? extends Agent> agentClass, int maxDistance) {
        if (population == null) return null;
        
        Agent nearest = null;
        double nearestDistance = Double.MAX_VALUE;
        
        for (Agent agent : population.getAgents()) {
            if (agentClass.isInstance(agent) && agent != this) {
                double distance = Math.sqrt(Math.pow(agent.getX() - x, 2) + Math.pow(agent.getY() - y, 2));
                if (distance <= maxDistance && distance < nearestDistance) {
                    nearestDistance = distance;
                    nearest = agent;
                }
            }
        }
        
        return nearest;
    }
    
    /**
     * Abstract method for updating agent behavior
     * Must be implemented by subclasses
     */
    @Override
    public abstract void update();
    
    /**
     * Abstract method for rendering the agent
     * @param screenX X coordinate in pixels for rendering
     * @param screenY Y coordinate in pixels for rendering
     */
    public abstract void render(int screenX, int screenY);
    
    /**
     * Get the color for rendering this agent
     * @return Color value compatible with Processing
     */
    public abstract int getColor();
}