import java.util.Arrays;
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
    private static final int MENU_ENTRIES = 2;

    /** Main menu display string. */
    private static final String MAIN_MENU = "\n1. Move\n2. Exit\n\nEnter 1 - 2:";

    /** Menu entry to select a move action. */
    public static final int MENU_MOVE = 1;

    /** Menu entry to terminate the program. */
    public static final int MENU_EXIT = 2;

    //print the board
    //todo optional fancy print
    /*@Deprecated
    public static <Char> void displayBoard(String[] players, int dimension) {
        //something is wired, lines of letters has length = dimension+4, but the other part has length = dimension+2
        int length = dimension+4;
        String[][] str = new String[length][length];

        //create default board layout, line by line.
        for (int i = 0; i< length; i++){

            //n is a parameter to switch between dimension>9 and dimension<=9
            int n = 2;
            if (dimension>9){
                n = 3;
            }

            //first and last line of letter index
            if (i == 0 || i == length-1){

                //leading and ending sapces
                for (int k = 0; k<n; k++){
                    str[i][k]=" ";
                    str[i][length-k-1]=" ";
                }

                //letters
                for (int j = n; j < dimension +n; j++){
                    char a = (char) ('A'+(j-n));
                    str[i][j]= Character.toString(a);
                }
            }

            //the blank line between letters and board
            else if(i == 1 || i == length-2){
                for (int j = 0; j< length; j++){
                    str[i][j]=" ";
                }
            }

            //board area
            else{

                // starting and ending numbers
                for (int x : new int[] {0,length-1}){
                    str[i][x]=Integer.toString(i-1);
                    if (str[i][x].length() ==1 && dimension>9){
                        str[i][x]=0+str[i][x];
                    }
                }

                //space between board and numbers
                str[i][length-1-1]=" ";
                str[i][1]=" ";

                //board in between
                for (int j = 2; j< dimension +2; j++) {
                    str[i][j] = ".";
                }
            }
        }

        //put players on the board one by one
        for (int k = 0; k < players.length; k++){
            char[] as = players[k].toCharArray();
            String stringNum = Character.toString(as[1]);

            //handling with 2 digit bumbers
            if ((dimension > 9) && (as.length == 3)){
                stringNum += Character.toString(as[2]);
                //System.out.println(as[2]);//testing if as[2] exist
            }

            //put Hounds on the board
            if (k < players.length-1){
                int j = as[0]-'A'+2;
                int i = Integer.parseInt(stringNum)+1;
                str[i][j] = "H";
                continue;
            }

            //put Fox on board
            int j = as[0]-'A'+2;
            int i = Integer.parseInt(stringNum)+1;
            str[i][j] = "F";
            }

        //print the game status
        String[] result = new String[length];
        for (int i = 0; i< length; i++){
            result[i]=str[i][0];
            for (int j = 1; j< length; j++){
                result[i]+=str[i][j];
            }
            System.out.println(result[i]);
        }
    }

     */
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

        //main while loop
        while(!validity){
            //print menu
            System.out.print("Provide origin and destination coordinates.\n");
            System.out.print("Enter two positions between A1-H8:\n");

            //read input
            result[0] = stdin.next();
            result[1] = stdin.next();

            //todo the upper case test
            //I handled it by accepting cases such as "a4 B3", but then using a toUpper() function.
            //check validity
            char[][] chars = new char[2][];
            chars[0] = result[0].toCharArray();
            chars[1] = result[1].toCharArray();
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

            if (!validity) {
                System.err.println("ERROR: Please enter valid coordinate pair separated by space. ");
                System.out.print("\n");
            }
        }
        return result;
    }

    //import saved status
    public static String fileQuery(Scanner stdin) {
        //todo file save
        System.out.print("Enter file path:\n");
        //todo is it ok to change Path type to String?
        String path = stdin.next();
        return path;
    }
}







