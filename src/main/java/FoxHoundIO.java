import java.io.FileWriter;
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
        //testing things are at there initial position, for default dimension
        if(players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is not match");

        //if path good
        if (pathLoad==null)
            throw new NullPointerException("path is empty");

        //loading part
        //take the value from txt
        String[] scannedText;
        try {
            //is path exists
            if (!pathLoad.toFile().exists())
                return '#';

            //see if the second line has something
            Scanner testScanning = new Scanner(pathLoad);
            testScanning.nextLine();
            if (testScanning.hasNextLine()) return '#';
            testScanning.close();

            //read the data
            Scanner scan = new Scanner(pathLoad);
            String[] temp = new String[26];//number of players would not larger than this value
            int count = 0;
            for (int i = 0; scan.hasNext(); i++){
                temp[i] = scan.next();
                count+=1;
            }

            //pass value
            scannedText = new String[count];
            for (int i = 0; i<count; i++){
                scannedText[i] = temp[i];
            }
            scan.close();

            //test if the list is empty or not
            if (scannedText.length==0) {
                return '#';
            }

            //test input figure is legal
            if (!(scannedText[0].equals("F") || scannedText[0].equals("H"))){
                return '#';
            }

            //test the format of the loaded file
            for (int i = 1; i<scannedText.length;i++) {//start forom 1 is because the first one is turn

                //each coordinate has a letter axises
                char[] bs = scannedText[i].toCharArray();
                if (!Character.isAlphabetic(bs[0]))
                    return '#';
                int[] coordinate = FoxHoundUtils.read(scannedText[i]);

                //correct range of coordinate
                for (int j : coordinate) {
                    if (!(j >= 1 && j <= FoxHoundUtils.DEFAULT_DIM))//this may need to change to dim
                        return '#';
                }

                //test that the coordinate given is valid in the board, which is black
                if (((coordinate[0] + coordinate[1]) - 1) % 2 == 1)//the total length in a taxi-cub coordinate
                    return '#';
            }
        }
        catch (Exception e) {
            System.out.println("loading process has some error");
            return '#';
        }

        //give turn
        char turn = scannedText[0].charAt(0);

        //change values of players
        for (int i = 1; i<scannedText.length; i++){
            players[i-1] = scannedText[i];
        }

        return turn;
    }

    //todo this is saving for different dim
    public static boolean saveGameDim(String[] players, char turn, Path pathSave) {
        return false;
    }

    //todo this is the condition that loading game with a different dimension
    public static char loadGameDim(String[] players, Path pathLoad) {
        return '#';
    }

    //todo this is the function determine the loaded game dimension
    public static int dimGet(String[] players, char temp) {
        return 8;
    }
}
