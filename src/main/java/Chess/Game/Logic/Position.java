package Chess.Game.Logic;

/**
 * @author Fitor Avdiji
 * <p>
 * class Represents a position on the Game Field from a1 - h8
 */
public class Position {

    /**
     * column of the current Position (i-value)
     **/
    private final char row;
    /**
     * row of the current Position (j - value)
     **/
    private final int column;

    /**
     * Constructor initializes {@link #row}
     * and {@link #column}, in this order.
     *
     * @param column column of the position
     * @param row row of the position
     * @throws IllegalArgumentException if column is not a alphanumerical,
     *                                  lowercase letter from a - h, or row is not a number from 1 - 8
     */
    public Position(final char row, final int column) {
        if (row < 'a' || row > 'h')
            throw new IllegalArgumentException("The column must be between a - h inclusive!");
        else if (column < 1 || column > 8)
            throw new IllegalArgumentException("The row must be between 1 - 8 inclusive!");

        this.row = row;
        this.column = column;
    }

    /**
     * Getter for {@link #row}
     *
     * @return column
     */
    public char getRow() {
        return row;
    }

    /**
     * Getter for {@link #column}
     *
     * @return row
     */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString(){
        return String.format("(Row: %c, Column: %d)", row, column);
    }

}
