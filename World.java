import java.util.ArrayList;
import java.util.List;

/**
 * Represents the world grid containing cells and managing the environment
 */
public class World {
    private Cell[][] grid;
    private int width, height;
    
    /**
     * Create a new world with specified dimensions
     * @param width Width of the world in cells
     * @param height Height of the world in cells
     */
    public World(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new Cell[width][height];
        initializeWorld();
    }
    
    /**
     * Initialize the world with empty cells, obstacles, and initial food
     */
    private void initializeWorld() {
        // Create all cells as empty initially
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                grid[x][y] = new Cell(x, y, Cell.CellType.EMPTY);
            }
        }
        
        // Add obstacles
        addObstacles();
        
        // Add initial food
        addInitialFood();
    }
    
    /**
     * Add obstacles randomly throughout the world
     */
    private void addObstacles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (Math.random() < Constants.OBSTACLE_DENSITY) {
                    grid[x][y].setType(Cell.CellType.OBSTACLE);
                }
            }
        }
    }
    
    /**
     * Add initial food distribution
     */
    private void addInitialFood() {
        int initialFoodCount = (int)(width * height * 0.1); // 10% of cells start with food
        for (int i = 0; i < initialFoodCount; i++) {
            int x = (int)(Math.random() * width);
            int y = (int)(Math.random() * height);
            if (grid[x][y].getType() == Cell.CellType.EMPTY) {
                grid[x][y].setType(Cell.CellType.FOOD);
            }
        }
    }
    
    /**
     * Update the world (spawn new food)
     */
    public void update() {
        spawnFood();
    }
    
    /**
     * Randomly spawn new food in empty cells
     */
    private void spawnFood() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y].getType() == Cell.CellType.EMPTY && 
                    Math.random() < Constants.FOOD_SPAWN_PROBABILITY) {
                    grid[x][y].setType(Cell.CellType.FOOD);
                }
            }
        }
    }
    
    /**
     * Get the cell at specified coordinates
     * @param x X coordinate
     * @param y Y coordinate
     * @return Cell at the specified position, or null if out of bounds
     */
    public Cell getCell(int x, int y) {
        if (isValidPosition(x, y)) {
            return grid[x][y];
        }
        return null;
    }
    
    /**
     * Check if the specified position is within world bounds
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if position is valid
     */
    public boolean isValidPosition(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
    
    /**
     * Check if the specified position is passable (not an obstacle and within bounds)
     * @param x X coordinate
     * @param y Y coordinate
     * @return True if position is passable
     */
    public boolean isPassable(int x, int y) {
        Cell cell = getCell(x, y);
        return cell != null && cell.isPassable();
    }
    
    /**
     * Get all neighboring positions (8-directional)
     * @param x Center X coordinate
     * @param y Center Y coordinate
     * @return List of valid neighboring positions
     */
    public List<int[]> getNeighbors(int x, int y) {
        List<int[]> neighbors = new ArrayList<>();
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip center position
                int newX = x + dx;
                int newY = y + dy;
                if (isValidPosition(newX, newY)) {
                    neighbors.add(new int[]{newX, newY});
                }
            }
        }
        return neighbors;
    }
    
    /**
     * Get all passable neighboring positions
     * @param x Center X coordinate
     * @param y Center Y coordinate
     * @return List of passable neighboring positions
     */
    public List<int[]> getPassableNeighbors(int x, int y) {
        List<int[]> passableNeighbors = new ArrayList<>();
        for (int[] neighbor : getNeighbors(x, y)) {
            if (isPassable(neighbor[0], neighbor[1])) {
                passableNeighbors.add(neighbor);
            }
        }
        return passableNeighbors;
    }
    
    /**
     * Get world width
     * @return Width in cells
     */
    public int getWidth() {
        return width;
    }
    
    /**
     * Get world height
     * @return Height in cells
     */
    public int getHeight() {
        return height;
    }
}