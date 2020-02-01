import java.util.Scanner;

/**
 * The Main class of the fox hound program.
 * 
 * It contains the main game loop where main menu interactions
 * are processed and handler functions are called.
  */
public class FoxHoundGame {

    /**
     * This scanner can be used by the program to read from
     * the standard input. 
     * 
     * Every scanner should be closed after its use, however, if you do
     * that for StdIn, it will close the underlying input stream as well
     * which makes it difficult to read from StdIn again later during the
     * program.
     * 
     * Therefore, it is advisable to create only one Scanner for StdIn 
     * over the course of a program and only close it when the program
     * exits. Additionally, it reduces complexity. 
     */
    private static final Scanner STDIN_SCAN = new Scanner(System.in);

    /**
     * Swap between fox and hounds to determine the next
     * figure to move.
     * 
     * @param currentTurn last figure to be moved
     * @return next figure to be moved
     */
    private static char swapPlayers(char currentTurn) {
        if (currentTurn == FoxHoundUtils.FOX_FIELD) {
            return FoxHoundUtils.HOUND_FIELD;
        } else {
            return FoxHoundUtils.FOX_FIELD;
        }
    }

    /**
     * The main loop of the game. Interactions with the main
     * menu are interpreted and executed here.
     * 
     * @param dim the dimension of the game board
     * @param players current position of all figures on the board in board coordinates
     */
    private static void gameLoop(int dim, String[] players) {

        // start this game with Fox
        char turn = FoxHoundUtils.FOX_FIELD;

        //then give a while loop for the game processing
        boolean exit = false;
        while(!exit) {

            //show the board
            System.out.println("\n#################################");

            //show the status of game
            FoxHoundUI.displayBoard(players, dim);

            //see who is win
            if (FoxHoundUtils.isFoxWin(players[players.length-1])){
                System.out.println("The Fox wins!");
                break;
            }
            if (FoxHoundUtils.isHoundWin(players, dim)){
                System.out.println("The Hounds wins!");
                break;
            }

            //only require the commend line input as choice
            int choice = FoxHoundUI.mainMenuQuery(turn, STDIN_SCAN);
            
            // handle menu choices
            switch(choice) {
                case FoxHoundUI.MENU_MOVE:
                    while (true){
                        String[] newPosition = FoxHoundUI.positionQuery(FoxHoundUtils.DEFAULT_DIM, STDIN_SCAN);
                        if (FoxHoundUtils.isValidMove(FoxHoundUtils.DEFAULT_DIM, players, turn, newPosition[0], newPosition[1])){
                            for (int i = 0; i<players.length; i++){
                                if (players[i].equals(newPosition[0])){
                                    players[i] = newPosition[1];
                                    break;
                                }
                            }
                            break;
                        }
                        System.out.println("Invalid move. Try again!");
                    }
                    turn = swapPlayers(turn);
                    break;
                case FoxHoundUI.MENU_EXIT:
                    exit = true;//break the while loop
                    break;
                default:
                    System.err.println("ERROR: invalid menu choice: " + choice);
            }
        }
    }

    /**
     * Entry method for the Fox and Hound game. 
     * 
     * The dimensions of the game board can be passed in as
     * optional command line argument.
     * 
     * If no argument is passed, a default dimension of 
     * {@value FoxHoundUtils#DEFAULT_DIM} is used. 
     * 
     * Dimensions must be between {@value FoxHoundUtils#MIN_DIM} and 
     * {@value FoxHoundUtils#MAX_DIM}.
     * 
     * @param args contain the command line arguments where the first can be
     * board dimensions.
     */
    public static void main(String[] args) {

        //pass constant
        int dimension = FoxHoundUtils.DEFAULT_DIM;

        //initialise the positions of all players
        String[] players = FoxHoundUtils.initialisePositions(dimension);

        //this players is initial value, so, changing the value of players is in gameloop
        gameLoop(dimension, players);

        // Close the scanner reading the standard input stream
        STDIN_SCAN.close();
    }
}
