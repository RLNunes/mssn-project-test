import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Manages all agents in the ecosystem
 * Handles agent lifecycle, updates, and population statistics
 */
public class Population {
    private List<Agent> agents;
    private World world;
    
    /**
     * Create a new population manager
     * @param world Reference to the world
     */
    public Population(World world) {
        this.world = world;
        this.agents = new ArrayList<>();
        initializePopulation();
    }
    
    /**
     * Initialize the population with starting agents
     */
    private void initializePopulation() {
        // Add initial prey
        for (int i = 0; i < Constants.INITIAL_PREY_COUNT; i++) {
            int[] position = findRandomEmptyPosition();
            if (position != null) {
                Prey prey = new Prey(position[0], position[1], world);
                prey.setPopulation(this);
                agents.add(prey);
            }
        }
        
        // Add initial predators
        for (int i = 0; i < Constants.INITIAL_PREDATOR_COUNT; i++) {
            int[] position = findRandomEmptyPosition();
            if (position != null) {
                Predator predator = new Predator(position[0], position[1], world);
                predator.setPopulation(this);
                agents.add(predator);
            }
        }
    }
    
    /**
     * Find a random empty position in the world
     * @return Coordinates [x, y] of empty position, or null if none found
     */
    private int[] findRandomEmptyPosition() {
        int attempts = 100; // Limit attempts to avoid infinite loops
        
        for (int i = 0; i < attempts; i++) {
            int x = (int)(Math.random() * world.getWidth());
            int y = (int)(Math.random() * world.getHeight());
            
            if (world.isPassable(x, y) && !isPositionOccupied(x, y)) {
                return new int[]{x, y};
            }
        }
        return null; // No empty position found
    }
    
    /**
     * Check if a position is occupied by an agent
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if position is occupied
     */
    private boolean isPositionOccupied(int x, int y) {
        for (Agent agent : agents) {
            if (agent.getX() == x && agent.getY() == y) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Update all agents and remove dead ones
     */
    public void update() {
        // Create a copy of the agents list to avoid concurrent modification
        List<Agent> agentsToUpdate = new ArrayList<>(agents);
        
        // Update all living agents
        for (Agent agent : agentsToUpdate) {
            if (agent.isAlive() && agents.contains(agent)) { // Check if agent still exists
                agent.update();
            }
        }
        
        // Remove dead agents
        removeDead();
    }
    
    /**
     * Remove all dead agents from the population
     */
    private void removeDead() {
        Iterator<Agent> iterator = agents.iterator();
        while (iterator.hasNext()) {
            Agent agent = iterator.next();
            if (!agent.isAlive()) {
                iterator.remove();
            }
        }
    }
    
    /**
     * Add a new agent to the population
     * @param agent Agent to add
     */
    public void addAgent(Agent agent) {
        if (agent != null) {
            agent.setPopulation(this);
            agents.add(agent);
        }
    }
    
    /**
     * Remove an agent from the population
     * @param agent Agent to remove
     */
    public void removeAgent(Agent agent) {
        agents.remove(agent);
    }
    
    /**
     * Get all agents in the population
     * @return List of all agents
     */
    public List<Agent> getAgents() {
        return new ArrayList<>(agents); // Return copy to prevent external modification
    }
    
    /**
     * Get all agents of a specific type
     * @param agentClass Class of agents to retrieve
     * @return List of agents of the specified type
     */
    public <T extends Agent> List<T> getAgentsOfType(Class<T> agentClass) {
        List<T> result = new ArrayList<>();
        for (Agent agent : agents) {
            if (agentClass.isInstance(agent)) {
                result.add(agentClass.cast(agent));
            }
        }
        return result;
    }
    
    /**
     * Get the total number of agents
     * @return Total agent count
     */
    public int getTotalCount() {
        return agents.size();
    }
    
    /**
     * Get the number of prey agents
     * @return Prey count
     */
    public int getPreyCount() {
        return getAgentsOfType(Prey.class).size();
    }
    
    /**
     * Get the number of predator agents
     * @return Predator count
     */
    public int getPredatorCount() {
        return getAgentsOfType(Predator.class).size();
    }
    
    /**
     * Check if the ecosystem is stable (has both prey and predators)
     * @return True if ecosystem is stable
     */
    public boolean isStable() {
        return getPreyCount() > 0 && getPredatorCount() > 0;
    }
    
    /**
     * Get population statistics as a formatted string
     * @return Statistics string
     */
    public String getStatistics() {
        return String.format("Total: %d | Prey: %d | Predators: %d", 
                           getTotalCount(), getPreyCount(), getPredatorCount());
    }
    
    /**
     * Get average energy of all agents
     * @return Average energy level
     */
    public double getAverageEnergy() {
        if (agents.isEmpty()) return 0.0;
        
        int totalEnergy = 0;
        for (Agent agent : agents) {
            totalEnergy += agent.getEnergy();
        }
        return (double) totalEnergy / agents.size();
    }
    
    /**
     * Get average energy for a specific agent type
     * @param agentClass Class of agents to calculate average for
     * @return Average energy for the specified agent type
     */
    public <T extends Agent> double getAverageEnergy(Class<T> agentClass) {
        List<T> agentsOfType = getAgentsOfType(agentClass);
        if (agentsOfType.isEmpty()) return 0.0;
        
        int totalEnergy = 0;
        for (T agent : agentsOfType) {
            totalEnergy += agent.getEnergy();
        }
        return (double) totalEnergy / agentsOfType.size();
    }
}