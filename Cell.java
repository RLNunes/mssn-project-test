/**
 * Represents a cell in the world grid
 * Can be empty, contain food, or be an obstacle
 */
public class Cell {
    /**
     * Enumeration of possible cell types
     */
    public enum CellType {
        EMPTY,      // Empty cell that agents can move through
        FOOD,       // Cell containing food
        OBSTACLE    // Impassable obstacle
    }
    
    private CellType type;
    private int x, y;
    
    /**
     * Create a new cell
     * @param x X coordinate in the grid
     * @param y Y coordinate in the grid
     * @param type Type of the cell
     */
    public Cell(int x, int y, CellType type) {
        this.x = x;
        this.y = y;
        this.type = type;
    }
    
    /**
     * Create an empty cell
     * @param x X coordinate in the grid
     * @param y Y coordinate in the grid
     */
    public Cell(int x, int y) {
        this(x, y, CellType.EMPTY);
    }
    
    /**
     * Get the cell type
     * @return Current cell type
     */
    public CellType getType() {
        return type;
    }
    
    /**
     * Set the cell type
     * @param type New cell type
     */
    public void setType(CellType type) {
        this.type = type;
    }
    
    /**
     * Check if the cell is passable (not an obstacle)
     * @return True if agents can move through this cell
     */
    public boolean isPassable() {
        return type != CellType.OBSTACLE;
    }
    
    /**
     * Check if the cell contains food
     * @return True if cell contains food
     */
    public boolean hasFood() {
        return type == CellType.FOOD;
    }
    
    /**
     * Consume food from this cell
     * @return Energy value of consumed food, or 0 if no food
     */
    public int consumeFood() {
        if (hasFood()) {
            type = CellType.EMPTY;
            return Constants.FOOD_ENERGY_VALUE;
        }
        return 0;
    }
    
    /**
     * Get the X coordinate
     * @return X coordinate
     */
    public int getX() {
        return x;
    }
    
    /**
     * Get the Y coordinate
     * @return Y coordinate
     */
    public int getY() {
        return y;
    }
    
    /**
     * Get the color for rendering this cell
     * @return Color value compatible with Processing
     */
    public int getColor() {
        switch (type) {
            case FOOD:
                return Constants.COLOR_FOOD;
            case OBSTACLE:
                return Constants.COLOR_OBSTACLE;
            case EMPTY:
            default:
                return Constants.COLOR_EMPTY;
        }
    }
}