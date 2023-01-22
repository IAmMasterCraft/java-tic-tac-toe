public class Pieces extends Player {
    private final int row;
    private final int col;
    public Pieces(char name, int row, int col) {
        super(name);

        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return this.row;
    }
    public int getCol() {
        return this.col;
    }
    @Override
    public String toString(){
        // Pieces: Player Y [1, 2]
        return "Pieces: Player " + getName() + " [" + (getRow() + 1) + ", " + (getCol() + 1) + "]";
    }
}
