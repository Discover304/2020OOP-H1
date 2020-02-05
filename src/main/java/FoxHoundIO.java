import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {
    public static boolean saveGame(String[] players, char turn, Path pathSave){
        //basic test
        //todo give a better way to make this part better
        if(pathSave == null) throw new NullPointerException("no path");
        if (Files.exists(pathSave)) return false;
        if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is wrong");


        FileWriter outFile;
        try {
            //write file
            outFile = new FileWriter(String.valueOf(pathSave));
            outFile.write(turn +" ");
            for (String i : players) outFile.write(i+" ");
            outFile.close();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static char loadGame(String[] players, Path pathLoad){
        if (pathLoad == null) throw new NullPointerException("path is empty");
        if (!Files.exists(pathLoad)) return '#';

        //testing same dimension
        if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("Something is wrong");

        //is modified board
        label:
        for (int i = 0;i<players.length-1;i++) {
            for (int j = 0;j<players.length-1;j++) {
                if (players[j].equals(FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM)[j])){
                    continue label;
                }
            }
            return '#';
        }
        if (!players[players.length-1].equals(FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM)[players.length-1]))
            return '#';



        //take the value from txt
        Scanner scan;
        try {
            scan = new Scanner(pathLoad);
        } catch (Exception e) {
            return '#';
        }
        char[] as = scan.nextLine().toCharArray();
        scan.close();

        String[] str = new String[(as.length-2+1)/3+1];
        str[0]=Character.toString(as[0]);
        for (int i = 2; i<as.length; i+=3){
            str[(i+1)/3]=""+as[i]+as[i+1];
        }



        //update board
        for (int i = 1; i<str.length; i++){
            players[i-1] = str[i];
            //System.out.print(players[i-1]+"\n");//players[i-1] do changed
        }

        //if (players.equals(FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM))) {
        //    return '#';
        //}  //may be helpful

        char turn = str[0].toCharArray()[0];
        return turn;
    }
}
