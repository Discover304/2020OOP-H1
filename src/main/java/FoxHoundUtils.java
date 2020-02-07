import javax.swing.plaf.metal.MetalBorders;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.ErrorManager;

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
    public static String[] initialisePositions(int dim) {
        if (dim <= 0) throw new IllegalArgumentException("Negative dimension");
        int numPlayers = dim/2+1;
        String[] str = new String[numPlayers];

        //position of Hound
        for (int i = 0; i < numPlayers-1; i++){
            str[i] = Character.toString((char) (66 + 2*i)) + 1;
        }

        //position of Fox
        if ((dim/2)%2==0)
            str[numPlayers-1] = Character.toString((char) (64+(dim+1)/2+1)) + dim;
        else
            str[numPlayers-1] = Character.toString((char) (64+dim/2+1)) + dim;

        return str;
    }

    //see if the entred movememtn is correct
    public static boolean isValidMove(int dim, String[] players, char figure, String origin, String destination){
        //test dimension
        if (dim <= 0) throw new IllegalArgumentException("Negative dimension");

        //read the position variable from input
        int[] num = new int[2];
        int[] letters = new int[2];
        int[] temp = read(origin);
        letters[0] = temp[0];
        num[0] = temp[1];
        temp = read(destination);
        letters[1] = temp[0];
        num[1] = temp[1];

        //this is the main part to see validity
        boolean result = true;
        //useing while is because any of the following false the result is false
        while(true){
            //correct origin compare players and figure and origin
            breakpoint:
            switch (figure){
                case 'H':{
                    for (int i = 0; i<players.length-2;i++){
                        if (players[i].equals(origin)) {
                            break breakpoint;
                        }
                    }
                    result = false;
                    break;//breakpoint
                }
                case 'F':{
                    if (players[players.length-1].equals(origin)) {
                        break;//breakpoint
                    }
                    result = false;
                }
            }
            if (result == false) break;//while

            //correct range of destination, take destination and dim
            for (int i:letters){
                if (i > dim){
                    result = false;
                }
            }
            for (int i:num){
                if (i > dim){
                    result=false;
                }
            }
            if (result == false) break;//while

            //correct destination, no player at destination
            for (String i:players){
                if (i.equals(destination)){
                    result = false;
                    break;
                }
            }
            if (result == false) break;//while

            //correct move generate position function and see if the value of destination from origin of the figure is valid
            //origin position get
            int[] os = read(origin);
            // destination position get
            int[] ds = read(destination);
            switch (figure){
                case 'H':{
                    result = ds[1] == os[1]+1 && (ds[0] == os[0]+1 || ds[0] == os[0]-1);
                    break;
                }
                case 'F':{
                    result = (ds[1] == os[1]+1 || ds[1] == os[1]-1) && (ds[0] == os[0]+1 || ds[0] == os[0]-1);
                }
            }

            break;//while
        }
        if (!result){
            System.err.println("ERROR: Invalid move. Try again!");
        }
        return result;
    }

    //read the position value
    public static int[] read(String str){
        //origin position get
        int[] result = new int[2];
        char[] bs = str.toCharArray();
        result[0] = bs[0] - 64;
        //handling with 2 digit bumbers
        String stringNum = Character.toString(bs[1]);
        if (bs.length == 3){
            stringNum += Character.toString(bs[2]);
            //System.out.println(as[2]);//testing if as[2] exist
        }
        result[1] = Integer.parseInt(stringNum);
        return result;
    }

    //winning condition
    public static boolean isFoxWin(String position){
        int[] as = read(position);
        return (as[1] == 1);//test
    }

    //winning condition
    public static boolean isHoundWin(String[] positions, int dim){
        int[] foxMove = FoxHoundUtils.read(positions[positions.length-1]);
        //fox can move?
        int[][] availablePosition = new int[4][2];
        availablePosition[0] = new int[] {foxMove[0]+1,foxMove[1]+1};
        availablePosition[0] = new int[] {foxMove[0]-1,foxMove[1]+1};
        availablePosition[0] = new int[] {foxMove[0]+1,foxMove[1]-1};
        availablePosition[0] = new int[] {foxMove[0]-1,foxMove[1]-1};

        String[] converted = new String[4];
        boolean result = true;
        for (int i = 0; i<4; i++){
            converted[i] = Character.toString(availablePosition[i][0]+64) + availablePosition[i][1];
            if (isValidMove(dim, positions, 'F', positions[positions.length-1], converted[i])){
                result = false;
                break;
            }
        }

        return result;//test
    }
}
