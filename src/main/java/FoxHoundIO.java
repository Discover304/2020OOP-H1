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
        //is the path good
        try{
            if(pathSave == null)
                throw new NullPointerException();
        }
        catch (Exception e){
            System.out.println("no path given");
            return false;
        }

        //is path not exits
        try{
            if (Files.exists(pathSave))
                throw new IllegalArgumentException();
        }
        catch (Exception e){
            System.out.println("file exists is wrong");
            return false;
        }

        //is the dimension equals to default
        //todo may conflict with ADVANCED part
        try {
            if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            System.out.println("dimension is not default");
            return false;
        }

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
            System.out.println("saving process has some error");
            return false;
        }
    }

    public static char loadGame(String[] players, Path pathLoad){
        //test part
        //if path good
        try{
          if (pathLoad.toString().isEmpty()) {
              throw new NullPointerException();
          }
        }
        catch (NullPointerException e){
            System.out.println("path is empty");
            return '#';
        }

        //is path exists
        try{
            if (!Files.exists(pathLoad))
                throw new IllegalArgumentException();;
        }
        catch (Exception e){
            System.out.println("file is not exists");
            return '#';
        }

        //testing things are at there initial position, for default dimension todo extend to all dimension
        try{
            if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
                throw new IllegalArgumentException();
        }
        catch (IllegalArgumentException e){
            System.out.println("dimension is not match");
            return '#';
        }

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
