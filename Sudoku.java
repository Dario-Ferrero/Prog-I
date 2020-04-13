import java.util.Scanner;

public class Sudoku {

	private static final int GRID_SIDE = 9;		
	private static final int EMPTY_CELL = 0;	// Value representative of an empty cell

	private static final String ROW_BEGIN_POINT = "                  ";
	private static final String GRID_TOP_BORDER = "\r\n" + ROW_BEGIN_POINT + " _____________________________"  +
												  "\r\n" + ROW_BEGIN_POINT + "|         |         |         |" + "\r\n";

	private static final String GRID_MID_BORDER = "\r\n" + ROW_BEGIN_POINT + "|_________|_________|_________|" + "\r\n"
														 + ROW_BEGIN_POINT + "|         |         |         |" + "\r\n";

	private static final String GRID_LOW_BORDER = ROW_BEGIN_POINT + "|_________|_________|_________|" + "\r\n";
	private static final String GRID_COLUMN_DIVISOR = " | ";


    private int[][] numberGrid;
    private boolean[][] isInitialValue;	// isInitialValue[i][[j] == True IFF numberGrid[i][j] is an initial grid value

	/*
	 * Creates a new empty 9x9 Sudoku object (with 3x3 subregions)
	 */
    private Sudoku()
	{
        numberGrid = new int[GRID_SIDE][GRID_SIDE];
        isInitialValue = new boolean[GRID_SIDE][GRID_SIDE];
    }

	/*
	 * Creates a new 9x9 Sudoku object (with 3x3 subregions)
	 * from the int[][] argument (dimensions must match)
     */ 
    private Sudoku(int[][] schema)
	{
		assert(checkDimensions(schema));

        numberGrid = schema;
        isInitialValue = new boolean[GRID_SIDE][GRID_SIDE];
        for (int row = 0; row < isInitialValue.length; row++)
            for (int column = 0; column < isInitialValue.length; column++)
                isInitialValue[row][column] = numberGrid[row][column] != 0;
    }

    /*
	 * Terminal output grid
	 */
    public String toString()
	{
		String grid = GRID_TOP_BORDER + ROW_BEGIN_POINT + "| ";
        int squareHeigth = 1;
		for (int row = 0; row < numberGrid.length; row++, squareHeigth++) {
			int squareLength = 1;
			for (int column = 0; column < numberGrid.length; column++, squareLength++) {
				grid += Integer.toString(numberGrid[row][column]);
				grid += (squareLength % 3 != 0)? "  " : GRID_COLUMN_DIVISOR;
			}
			grid += (squareHeigth % 3 == 0 && squareHeigth != GRID_SIDE)? GRID_MID_BORDER : "\r\n";
			if (squareHeigth != GRID_SIDE) grid += ROW_BEGIN_POINT + "| ";
		}
		grid += GRID_LOW_BORDER;

		return grid.replace('0', '*');
    }

	/*
	 * Adds a new value (to the initial ones) at the specified position
	 *
	 * Assetion vs Exception ?
	 * - do I want the game to stop on a wrong input?
     */
    private void addToInitials(int row, int column, int value) throws ArrayIndexOutOfBoundsException, IllegalArgumentException
	{
        if (row < 0 || row > 8 || column < 0 || column > 8)
            throw new ArrayIndexOutOfBoundsException("Indici non validi, riprovare: ");
        else if (value < 1 || value > GRID_SIDE)      
            throw new IllegalArgumentException("value non ammissibile, reinserire value: ");    

        numberGrid[row][column] = value;
        isInitialValue[row][column] = true;              
    }
    
	/*
	 * Adds a new value (player's move) at the specified position
	 */
    private void addMove(int row, int column, int value) throws ArrayIndexOutOfBoundsException, IllegalArgumentException
	{
        if (row < 1 || row > GRID_SIDE || column < 1 || column > GRID_SIDE)
            throw new ArrayIndexOutOfBoundsException("Indici non validi, riprovare: ");
        else if (value < 0 || value > GRID_SIDE)
            throw new IllegalArgumentException("value non ammissibile, riprovare: ");    
        else{   
            if (!isInitialValue[row-1][column-1])
                numberGrid[row-1][column-1] = value;   
            else
                System.out.println("Non si puo' modificare un value iniziale");
        }
    }

    private boolean verifyGame()
	{        
        boolean regular = true;
        for (int i = 0; i < numberGrid.length && regular; i++)
            for (int j = 0; j < numberGrid.length && regular; j++){
                boolean[] possible = getValidValues(i, j);
                regular = possible[numberGrid[i][j]];                
            }

        return regular;
    }
    
	/**
	 * Returns the value at a specified position
	 */
    private int getValue(int row, int column) throws ArrayIndexOutOfBoundsException
	{
        if (1 <= row && row <= GRID_SIDE && 1 <= column && column <= GRID_SIDE)
            return numberGrid[row-1][column-1];
        else
            throw new ArrayIndexOutOfBoundsException("Indici non validi, riprovare: ");
    }

	/**
	 * Given a certain position returns a boolean[] indicating the validity,
	 * for that cell, of the value used as the array index
     */
    private boolean[] getValidValues(int row, int column) throws IllegalArgumentException
	{
        boolean[] isValid = new boolean[10];

		for (int i = 0; i < numberGrid.length; i++) {
			int inLineValue = numberGrid[row][i];
			if (i != column) isValid[inLineValue] = inLineValue == EMPTY_CELL;
			
			inLineValue = numberGrid[i][column];
			if (i != row) isValid[inLineValue] = inLineValue == EMPTY_CELL;
		}

        int rowInit, rowEnd, columnInit, columnEnd;

		switch (row) {
			case 0:
			case 1:
			case 2: rowInit = 0;
					rowEnd = 2;
					break;
			case 3:
			case 4:
			case 5: rowInit = 3;
					rowEnd = 5;
					break;
			case 6:
			case 7:
			case 8: rowInit = 6;
					rowEnd = 8;
					break;
			default:
					throw new IllegalArgumentException("Invalid row value. Try again: ");
		}
		
		switch (column) {
			case 0:
			case 1:
			case 2: columnInit = 0;
					columnEnd = 2;
					break;
			case 3:
			case 4:
			case 5: columnInit = 3;
					columnEnd = 5;
					break;
			case 6:
			case 7:
			case 8: columnInit = 6;
					columnEnd = 8;
					break;
			default:
					throw new IllegalArgumentException("Invalid column value. Try again: ");
		}

        for (int i = rowInit; i <= rowEnd; i++)                        	//Per ogni elemento della matrice interna
            for (int j = columnInit; j <= columnEnd; j++) {
                int inLineValue = numberGrid[i][j];                             	//Tale elemento
                if (i != row && j != column)							//(Esclusa la posizione di partenza)
					isValid[inLineValue] = inLineValue == 0;
            }                                                         	//Non è inseribile nella posizione attuale (se diverso da 0)

        return isValid;                                               	//Restituisco l'array di valori validi
    }

	/*
	 * Verifies if numberGrid has no more empty cells
	 */
    private boolean isGridFull()
	{
        boolean isFull = true;
        for (int row = 0; row < numberGrid.length && isFull; row++)
            for (int column = 0; column < numberGrid.length && isFull; column++)
                isFull = numberGrid[row][column] != 0;

        return isFull;     
    }

	/*
	 * Resets the grid to its initial values
     */
    private void reset()
	{
        for (int row = 0; row < numberGrid.length; row++)
            for (int column = 0; column < numberGrid.length; column++)
                if (!isInitialValue[row][column])   numberGrid[row][column] = 0;
    }

	private boolean checkDimensions(int[][] grid)
	{
		boolean dimensionsValid = grid.length == GRID_SIDE;
		for (int i = 0; i < grid.length && dimensionsValid; i++)
			dimensionsValid = grid[i].length == GRID_SIDE;
		
		return dimensionsValid;
	}

    public static void main(String[] args)
	{
		// Credits by "La Settimana Enigmistica"
        int[][] easy = {{3, 0, 9, 6, 0, 0, 5, 0, 0}, 
                        {0, 2, 0, 0, 0, 5, 9, 4, 0},
                        {0, 0, 1, 2, 4, 0, 0, 0, 0},
                        {0, 0, 5, 0, 6, 0, 0, 3, 4},
                        {0, 6, 0, 3, 0, 0, 0, 5, 8},
                        {4, 0, 7, 0, 0, 8, 0, 0, 0},
                        {2, 0, 0, 1, 0, 0, 8, 0, 0},
                        {6, 1, 0, 0, 0, 0, 2, 0, 0},
                        {0, 5, 0, 0, 2, 6, 0, 0, 9}};

        //int[][] almostFull = {{},{},{},{},{},{},{},{},{}};

        Sudoku s = new Sudoku(easy);   
        Scanner keyboard = new Scanner(System.in);
        do {
            System.out.println(s);
            System.out.print("Insert \"help\" for the guide.\r\n"
						   + "Insert next move's row column and value to be inserted (space separated): ");
            s.addMove(keyboard.nextInt(), keyboard.nextInt(), keyboard.nextInt());
			if (s.isGridFull() && !s.verifyGame())
				System.out.println("\r\nThe input moves present one or more errors. Game (still) on\r\n");
        } while(!(s.isGridFull() && s.verifyGame()));

        System.out.println("\r\n\r\nGame ended successfully!!!");                                  
    }

}