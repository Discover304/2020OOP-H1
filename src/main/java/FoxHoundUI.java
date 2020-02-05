import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.Objects;

/**
 * A utility class for the fox hound program.
 *
 * It contains helper functions for all user interface related
 * functionality such as printing menus and displaying the game board.
 */
public class FoxHoundUI {

    /** Number of main menu entries. */
    private static final int MENU_ENTRIES = 4;

    /** Main menu display string. */
    private static final String MAIN_MENU = "\n1. Move\n2. Save Game\n3. Load Game\n4. Exit\nEnter 1 - 4:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;

    public static final int GAME_SAVE = 2;

    public static final int GAME_LOAD = 3;

    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 4;

    //print the board
    //todo optional fancy print
    public static <Char> void displayBoard(String[] players, int dim){
        //get letter labels
        if (dim<10) System.out.print("  ");
        else System.out.print("   ");
        char[] letters = new char[dim];
        for(int i = 0; i<dim; i++){
            letters[i] = (char) ('A'+i);
            System.out.print(letters[i]);
        }
        if (dim<10) System.out.print("  ");
        else System.out.print("   ");
        System.out.print("\n");
        System.out.print("\n");

        //get number labels
        String[] numbers = new String[dim];
        if(dim<9){
            for(int i = 0; i<dim; i++){
                numbers[i] = Integer.toString(i+1);
            }
        }
        else {
            for(int i = 0; i<dim; i++){
                if (i<9){
                    numbers[i] = "0" + (i + 1);
                }
                else numbers[i] = Integer.toString(i+1);
            }
        }

        //get board
        char[][] posi = new char[dim][dim];
        for (int i = 0; i<dim; i++){
            for (int j = 0; j<dim; j++) posi[i][j] = '.';
        }

        //place players on board
        for (int i = 0; i< players.length; i++){
            int[] temp = FoxHoundUtils.read(players[i]);
            if(i == players.length-1){
                posi [temp[0]-1][temp[1]-1] = FoxHoundUtils.FOX_FIELD;
                break;
            }
            posi [temp[0]-1][temp[1]-1] = FoxHoundUtils.HOUND_FIELD;
        }

        //print board+number labels
        for (int i = 0; i<dim; i++){
            System.out.print(numbers[i]+" ");
            for (int j = 0; j<dim; j++) System.out.print(posi[j][i]);
            System.out.print(" "+numbers[i]+"\n");
        }

        //print letter labels
        System.out.print("\n");
        if (dim<10) System.out.print("  ");
        else System.out.print("   ");
        for(int i = 0; i<dim; i++){
            System.out.print(letters[i]);
        }
        if (dim<10) System.out.print("  ");
        else System.out.print("   ");
        System.out.print("\n");
    }

    /**
     * Print the main menu and query the user for an entry selection.
     *
     * @param figureToMove the figure type that has the next move
     * @param stdin a Scanner object to read user input from
     * @return a number representing the menu entry selected by the user
     * @throws IllegalArgumentException if the given figure type is invalid
     * @throws NullPointerException if the given Scanner is null
     */
    //main menu interface
    public static int mainMenuQuery(char figureToMove, Scanner stdin) {
        Objects.requireNonNull(stdin, "Given Scanner must not be null");
        if (figureToMove != FoxHoundUtils.FOX_FIELD && figureToMove != FoxHoundUtils.HOUND_FIELD) {
            throw new IllegalArgumentException("Given figure field invalid: " + figureToMove);
        }

        String nextFigure =
            figureToMove == FoxHoundUtils.FOX_FIELD ? "Fox" : "Hounds";

        int input = -1;
        while (input == -1) {
            System.out.println(nextFigure + " to move");
            System.out.println(MAIN_MENU);

            boolean validInput = false;
            if (stdin.hasNextInt()) {
                input = stdin.nextInt();
                validInput = input > 0 && input <= MENU_ENTRIES;
            }

            if (!validInput) {
                System.out.println("Please enter valid number.");
                input = -1; // reset input variable
            }

            stdin.nextLine(); // throw away the rest of the line
        }

        return input;
    }

    // ask for next movement.
    public static String[] positionQuery(int dim, Scanner stdin){
        boolean validity = false;
        String[] result = new String[2];

        //check validity
        while(!validity){
            //print menu
            System.out.print("Provide origin and destination coordinates.\n");
            System.out.print("Enter two positions between A1-H8:\n");

            //read input
            result[0] = stdin.next();
            result[1] = stdin.next();

            //this is the main part
            char[][] chars = new char[2][];
            chars[0] = result[0].toUpperCase().toCharArray();
            chars[1] = result[1].toUpperCase().toCharArray();
            breakpoint:
            for (char[] x: chars){
                if (Character.isLetter(x[0])){
                    if (dim>9){
                        if (x.length==3&&Character.isDigit(x[1])&&Character.isDigit(x[2]));
                        else if (x.length==2&&Character.isDigit(x[1]));
                        else {
                            validity = false;
                            break breakpoint;
                        }
                    }
                    else if (dim<=9){
                        if (x.length==2&&Character.isDigit(x[1]));
                        else {
                            validity = false;
                            break breakpoint;
                        }
                    }
                }
                else {
                    validity = false;
                    break breakpoint;
                }
                validity = true;
            }

            try{
                if (!validity) {
                    throw new IllegalArgumentException();
                }
            }
            catch (IllegalArgumentException e){
                System.out.print("Please enter valid coordinate pair separated by space.");
            }
        }
        return result;
    }

    //import saved status
    public static Path fileQuery(Scanner stdin) {
        System.out.print("Enter file path:\n");

        //get the path
        Path path = Paths.get(stdin.nextLine());
        return path;
    }
}







