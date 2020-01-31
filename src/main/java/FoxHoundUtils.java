/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions to check the state of the game
 * board and validate board coordinates and figure positions.
 */
public class FoxHoundUtils {

    /** Default dimension of the game board in case none is specified. */
    public static final int DEFAULT_DIM = 8;
    /** Minimum possible dimension of the game board. */
    public static final int MIN_DIM = 4;
    /** Maximum possible dimension of the game board. */
    public static final int MAX_DIM = 26;

    /** Symbol to represent a hound figure. */
    public static final char HOUND_FIELD = 'H';
    /** Symbol to represent the fox figure. */
    public static final char FOX_FIELD = 'F';

    //initialisePositions when start the program
    public static String[] initialisePositions(int dimension) {
        int numPlayers = dimension/2+1;
        String[] str = new String[numPlayers];

        //position of Hound
        for (int i = 0; i < numPlayers-1; i++){
            str[i] = Character.toString((char) (66 + 2*i)) + 1;
        }

        //position of Fox
        if ((dimension/2)%2==0)
            str[numPlayers-1] = Character.toString((char) (64+(dimension+1)/2+1)) + dimension;
        else
            str[numPlayers-1] = Character.toString((char) (64+dimension/2+1)) + dimension;

        //System.out.println(str[numPlayers-1]);//this is a test to see if fox is at the correct position
        //String[] hello = {"B1","D1","D4","H1","C8"};//this is a test input
        return str;
    }

    //see if the entred movememtn is correct
    public static boolean isValidMove(int dim, String[] players, char figure, String origin, String destination){
        boolean result = true;

        /*
          the only difference between H and F is the direction they can move
          so, without switch we can assign a value to a variable to validate some movement or not
         */
        int key = -1;//set default value of Fox backward movement validation
        if (figure == 'H'){
            key = 0;//invalidate backward movememnt of Hound
        }

        //this is the main part to see validity, useing while is because any of the following false the relust is false
        while(true){
            //todo correct origin compare players and figure and origin
            if (result == false) break;
            //todo correct range of destination, take destination and dim
            if (result == false) break;
            //todo correct destination, no player at destination
            if (result == false) break;
            //todo correct move generate position function and see if the value of destination from origin of the figure is valid
            if (result == false) break;
            break;
        }

        return result;
    }

    public static boolean isFoxWin(String position){
        //the y direction == 1
        return false;//test
    }

    public static boolean isHoundWin(String[] positions, int dim){
        return false;//test
    }
}
