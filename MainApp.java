/**
 * Main Processing application for the ecosystem simulator
 * 
 * This is a Processing sketch that simulates a simple ecosystem with prey and predators.
 * - Blue circles represent prey that eat green food
 * - Red circles represent predators that hunt prey  
 * - Green squares represent food
 * - Brown squares represent obstacles
 * 
 * Controls:
 * - Press 'r' to restart the simulation
 * - Press 'p' to pause/unpause
 * - Press 's' to show/hide statistics
 * 
 * The simulation runs automatically and displays population statistics.
 * 
 * NOTE: For Processing, rename this file to match your sketch name (e.g., EcosystemSimulator.pde)
 * and remove the .java extension. Processing will handle the compilation automatically.
 */

// Global variables for the ecosystem
World world;
Population population;
boolean isPaused = false;
boolean showStats = true;
int simulationFrame = 0;

/**
 * Processing setup function - called once at startup
 */
void setup() {
    // Set up the display
    size(Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    background(0);
    
    // Initialize the ecosystem
    initializeEcosystem();
    
    println("Ecosystem Simulator Started");
    println("Controls: 'r' = restart, 'p' = pause/unpause, 's' = toggle stats");
}

/**
 * Processing draw function - called every frame
 */
void draw() {
    // Clear the screen
    background(Constants.COLOR_EMPTY);
    
    // Update the simulation if not paused
    if (!isPaused) {
        updateSimulation();
    }
    
    // Render the world and agents
    renderWorld();
    renderAgents();
    
    // Display statistics and controls
    if (showStats) {
        displayStatistics();
    }
    
    displayControls();
    
    simulationFrame++;
}

/**
 * Initialize or reset the ecosystem
 */
void initializeEcosystem() {
    world = new World(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
    population = new Population(world);
    simulationFrame = 0;
    println("Ecosystem initialized with " + population.getStatistics());
}

/**
 * Update the simulation by one step
 */
void updateSimulation() {
    // Update world (spawn food, etc.)
    world.update();
    
    // Update all agents
    population.update();
    
    // Check if ecosystem has collapsed
    if (!population.isStable() && simulationFrame > 300) { // Give some time for initial stabilization
        println("Ecosystem collapsed! Restarting...");
        initializeEcosystem();
    }
}

/**
 * Render the world grid (food, obstacles, empty cells)
 */
void renderWorld() {
    for (int x = 0; x < world.getWidth(); x++) {
        for (int y = 0; y < world.getHeight(); y++) {
            Cell cell = world.getCell(x, y);
            if (cell != null) {
                fill(cell.getColor());
                noStroke();
                
                // Draw different shapes for different cell types
                if (cell.getType() == Cell.CellType.FOOD) {
                    // Draw food as small green squares
                    rect(x * Constants.CELL_SIZE + 2, y * Constants.CELL_SIZE + 2, 
                         Constants.CELL_SIZE - 4, Constants.CELL_SIZE - 4);
                } else if (cell.getType() == Cell.CellType.OBSTACLE) {
                    // Draw obstacles as brown squares
                    rect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE, 
                         Constants.CELL_SIZE, Constants.CELL_SIZE);
                }
                // Empty cells use the background color
            }
        }
    }
}

/**
 * Render all agents (prey and predators)
 */
void renderAgents() {
    for (Agent agent : population.getAgents()) {
        if (agent.isAlive()) {
            renderAgent(agent);
        }
    }
}

/**
 * Render a single agent
 * @param agent Agent to render
 */
void renderAgent(Agent agent) {
    int screenX = agent.getX() * Constants.CELL_SIZE + Constants.CELL_SIZE / 2;
    int screenY = agent.getY() * Constants.CELL_SIZE + Constants.CELL_SIZE / 2;
    
    fill(agent.getColor());
    stroke(255); // White border
    strokeWeight(1);
    
    // Draw agent as a circle
    int size = Constants.CELL_SIZE - 2;
    
    // Adjust size based on energy for visual feedback
    if (agent instanceof Prey) {
        Prey prey = (Prey) agent;
        size = (int)(size * (0.5 + 0.5 * prey.getEnergyPercentage()));
    } else if (agent instanceof Predator) {
        Predator predator = (Predator) agent;
        size = (int)(size * (0.6 + 0.4 * predator.getEnergyPercentage()));
    }
    
    ellipse(screenX, screenY, size, size);
}

/**
 * Display simulation statistics
 */
void displayStatistics() {
    // Set up text properties
    fill(255); // White text
    textSize(14);
    textAlign(LEFT);
    
    int yPos = 20;
    int lineHeight = 18;
    
    // Background for text
    fill(0, 150); // Semi-transparent black background
    noStroke();
    rect(5, 5, 300, 120);
    
    // Statistics text
    fill(255);
    text("Frame: " + simulationFrame, 10, yPos);
    yPos += lineHeight;
    
    text(population.getStatistics(), 10, yPos);
    yPos += lineHeight;
    
    text("Avg Energy: " + String.format("%.1f", population.getAverageEnergy()), 10, yPos);
    yPos += lineHeight;
    
    text("Prey Avg Energy: " + String.format("%.1f", population.getAverageEnergy(Prey.class)), 10, yPos);
    yPos += lineHeight;
    
    text("Predator Avg Energy: " + String.format("%.1f", population.getAverageEnergy(Predator.class)), 10, yPos);
    yPos += lineHeight;
    
    text("Status: " + (isPaused ? "PAUSED" : "RUNNING"), 10, yPos);
}

/**
 * Display control instructions
 */
void displayControls() {
    fill(255);
    textSize(12);
    textAlign(RIGHT);
    
    int yPos = height - 60;
    int lineHeight = 15;
    
    // Background for controls
    fill(0, 150);
    noStroke();
    rect(width - 200, height - 70, 195, 65);
    
    fill(255);
    text("Controls:", width - 10, yPos);
    yPos += lineHeight;
    text("'r' - Restart simulation", width - 10, yPos);
    yPos += lineHeight;
    text("'p' - Pause/Unpause", width - 10, yPos);
    yPos += lineHeight;
    text("'s' - Toggle statistics", width - 10, yPos);
}

/**
 * Handle keyboard input
 */
void keyPressed() {
    switch (key) {
        case 'r':
        case 'R':
            println("Restarting simulation...");
            initializeEcosystem();
            break;
            
        case 'p':
        case 'P':
            isPaused = !isPaused;
            println("Simulation " + (isPaused ? "paused" : "resumed"));
            break;
            
        case 's':
        case 'S':
            showStats = !showStats;
            println("Statistics " + (showStats ? "shown" : "hidden"));
            break;
            
        default:
            // Other keys do nothing
            break;
    }
}

/**
 * Handle mouse clicks (optional future extension)
 */
void mousePressed() {
    // Future: Could add functionality to add/remove agents or food at mouse position
    println("Mouse clicked at: " + (mouseX / Constants.CELL_SIZE) + ", " + (mouseY / Constants.CELL_SIZE));
}