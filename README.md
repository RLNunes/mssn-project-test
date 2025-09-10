# Ecosystem Simulator - Java + Processing

A simple ecosystem simulator built with Java and Processing that demonstrates predator-prey dynamics in a virtual environment.

## Overview

This simulation features:
- **Blue prey** that move around, eat green food, and reproduce
- **Red predators** that hunt prey and reproduce when well-fed
- **Green food** that spawns randomly throughout the environment
- **Brown obstacles** that block movement
- Dynamic population statistics and energy management

## Features

### Agents
- **Prey (Blue circles)**: Herbivores that consume food and reproduce when energy levels are sufficient
- **Predators (Red circles)**: Carnivores that hunt prey and require successful hunts to maintain energy

### Environment
- **Grid-based world** with configurable dimensions
- **Food sources** that spawn randomly and provide energy to prey
- **Obstacles** that create environmental complexity
- **Energy-based ecosystem** where all actions consume energy

### Visualization
- Real-time population statistics
- Energy-based size scaling for agents
- Color-coded environment elements
- Interactive controls for simulation management

## Setup Instructions

### Prerequisites
1. **Download Processing IDE**
   - Visit [https://processing.org/download/](https://processing.org/download/)
   - Download the appropriate version for your operating system
   - Extract and install Processing

### Running the Simulation

#### Option 1: Processing IDE (Recommended)
1. Open Processing IDE
2. Create a new sketch (`File > New`)
3. Replace the default code with the contents of `MainApp.java`
4. Copy all other `.java` files into your sketch folder (next to the main `.pde` file)
5. Click the "Run" button (▶️) in Processing IDE

#### Option 2: Command Line with Processing
1. Install Processing and add it to your PATH
2. Navigate to the project directory
3. Create a `.pde` file with MainApp.java contents
4. Run: `processing-java --sketch=/path/to/project --run`

#### Option 3: Test Core Functionality (Java only)
To verify the simulation logic without Processing visualization:
```bash
javac Constants.java AgentInterfaces.java Cell.java World.java Agent.java Prey.java Predator.java Population.java TestRunner.java
java TestRunner
```

### File Structure
```
mssn-project-test/
├── MainApp.java          # Main Processing application
├── World.java            # Grid world management
├── Cell.java             # Individual cell types
├── Agent.java            # Abstract base class for agents
├── Prey.java             # Prey agent implementation
├── Predator.java         # Predator agent implementation
├── Population.java       # Population management
├── Constants.java        # Configuration parameters
├── AgentInterfaces.java  # Extensibility interfaces
├── README.md             # This file
└── .gitignore           # Git ignore rules
```

## Controls

- **`r`** - Restart the simulation with a new random ecosystem
- **`p`** - Pause/unpause the simulation
- **`s`** - Toggle statistics display on/off
- **Mouse click** - Display grid coordinates (for debugging)

## Configuration

Modify values in `Constants.java` to customize the simulation:

### World Settings
- `WORLD_WIDTH` / `WORLD_HEIGHT` - Grid dimensions
- `CELL_SIZE` - Pixel size of each grid cell
- `OBSTACLE_DENSITY` - Percentage of world covered by obstacles

### Population Settings
- `INITIAL_PREY_COUNT` - Starting number of prey
- `INITIAL_PREDATOR_COUNT` - Starting number of predators

### Energy Settings
- `PREY_INITIAL_ENERGY` - Starting energy for prey
- `PREDATOR_INITIAL_ENERGY` - Starting energy for predators
- `PREY_REPRODUCTION_THRESHOLD` - Energy needed for prey reproduction
- `PREDATOR_REPRODUCTION_THRESHOLD` - Energy needed for predator reproduction

### Behavior Settings
- `FOOD_SPAWN_PROBABILITY` - Chance of food spawning each frame
- `MOVEMENT_ENERGY_COST` - Energy consumed per movement
- `REPRODUCTION_ENERGY_COST` - Energy consumed during reproduction

## Architecture

The simulation follows a modular, object-oriented design:

### Core Classes
- **`MainApp`**: Processing sketch handling setup, draw loop, and user interaction
- **`World`**: Manages the grid-based environment and cell states
- **`Cell`**: Represents individual grid cells (empty, food, obstacle)
- **`Agent`**: Abstract base class implementing common agent behaviors
- **`Prey`**: Herbivore agents that eat food and avoid predators
- **`Predator`**: Carnivore agents that hunt prey
- **`Population`**: Manages agent lifecycle and population statistics

### Extensibility
The system is designed for easy extension through:
- **Interfaces**: `Updatable`, `Movable`, `EnergyBased`, `Reproductive`
- **Constants**: Centralized configuration in `Constants.java`
- **Inheritance**: New agent types can extend `Agent` class
- **Modular design**: Each component is independently testable

### Design Patterns
- **Strategy Pattern**: Different behaviors through interfaces
- **Observer Pattern**: Population management of agents
- **Template Method**: Agent base class with customizable behaviors

## Adding New Features

### New Agent Types
1. Create a new class extending `Agent`
2. Implement required abstract methods
3. Add any specific behaviors
4. Update `Population` class if needed

### New Cell Types
1. Add new type to `Cell.CellType` enum
2. Update `Cell.getColor()` method
3. Add rendering logic in `MainApp`

### New Behaviors
1. Define new interfaces in `AgentInterfaces.java`
2. Implement in relevant agent classes
3. Add constants for configuration

## Troubleshooting

### Common Issues
1. **Sketch won't run**: Ensure all `.java` files are in the sketch folder
2. **Performance issues**: Reduce world size or population in `Constants.java`
3. **Ecosystem collapse**: Adjust energy parameters to balance predator-prey dynamics

### Performance Tips
- Lower `WORLD_WIDTH` and `WORLD_HEIGHT` for better performance
- Reduce initial population sizes
- Adjust `FOOD_SPAWN_PROBABILITY` to maintain ecosystem balance

## License

This project is open source. See `LICENSE` file for details.

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes following the existing code style
4. Test your changes
5. Submit a pull request

## Educational Use

This simulator is ideal for:
- Computer science education (OOP, algorithms, simulation)
- Biology education (predator-prey dynamics, ecosystem modeling)
- Game development learning (agent-based systems)
- Interactive system design