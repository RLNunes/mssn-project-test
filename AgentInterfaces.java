/**
 * Interface for agents that can be updated each simulation step
 */
interface Updatable {
    /**
     * Update the agent's state for one simulation step
     */
    void update();
}

/**
 * Interface for entities that can be rendered
 */
interface Renderable {
    /**
     * Render the entity at the specified position
     * @param x X coordinate in pixels
     * @param y Y coordinate in pixels
     */
    void render(int x, int y);
}

/**
 * Interface for agents that can move
 */
interface Movable {
    /**
     * Move the agent to a new position
     * @param newX New X coordinate
     * @param newY New Y coordinate
     */
    void moveTo(int newX, int newY);
    
    /**
     * Get the current X position
     * @return Current X coordinate
     */
    int getX();
    
    /**
     * Get the current Y position
     * @return Current Y coordinate
     */
    int getY();
}

/**
 * Interface for agents with energy
 */
interface EnergyBased {
    /**
     * Get current energy level
     * @return Current energy
     */
    int getEnergy();
    
    /**
     * Add energy to the agent
     * @param amount Amount of energy to add
     */
    void addEnergy(int amount);
    
    /**
     * Use energy from the agent
     * @param amount Amount of energy to consume
     * @return True if energy was available and consumed, false otherwise
     */
    boolean useEnergy(int amount);
    
    /**
     * Check if the agent is alive (has energy > 0)
     * @return True if alive, false if dead
     */
    boolean isAlive();
}

/**
 * Interface for agents that can reproduce
 */
interface Reproductive {
    /**
     * Check if the agent can reproduce
     * @return True if reproduction is possible, false otherwise
     */
    boolean canReproduce();
    
    /**
     * Attempt to reproduce
     * @return New offspring agent, or null if reproduction failed
     */
    Agent reproduce();
}