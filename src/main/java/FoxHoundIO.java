import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
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
        //is the dimension equals to default
        //todo may conflict with ADVANCED part
        if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is not match");

        //is the path good
        if(pathSave == null)
            throw new NullPointerException("no path given");

        //is path not exits
        if (pathSave.toFile().exists())
            throw new NullPointerException("file exists is not exists");

        //save part
        //start to write file
        FileWriter outFile;
        try {
            outFile = new FileWriter(String.valueOf(pathSave));
            outFile.write(turn +" ");
            for (String i : players) outFile.write(i+" ");
            outFile.close();
            return true;
        }
        catch (Exception e) {
            System.out.println("Saving file failed, please try again");
            return false;
        }
    }

    public static char loadGame(String[] players, Path pathLoad){
        //test part
        //testing things are at there initial position, for default dimension todo extend to all dimension
        if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is not match");

        //if path good
        if (pathLoad==null)
            throw new NullPointerException("path is empty");

        //is path exists
        if (!pathLoad.toFile().exists())
            return '#';

        //loading part
        //take the value from txt
        Scanner scan;
        try {
            scan = new Scanner(pathLoad);
        } catch (Exception e) {
            System.out.println("loading process has some error");
            return '#';
        }
        char[] as = scan.nextLine().toCharArray();
        scan.close();

        //todo input is in correct format(need higher dimension)
        //input is in correct format
        if (!(as[1] == ' ')){
            return '#';
        }

        //pass values
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
        char turn = str[0].toCharArray()[0];

        return turn;
    }
}
