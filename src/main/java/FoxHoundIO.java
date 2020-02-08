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

        //is path exists
        if (!pathLoad.toFile().exists())
            return '#';

        //loading part
        //take the value from txt
        String[] scannedText;
        try {
            Scanner testScanning = new Scanner(pathLoad);
            testScanning.nextLine();
            if (testScanning.hasNextLine()) return '#';
            testScanning.close();
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
        } catch (Exception e) {
            System.out.println("loading process has some error");
            return '#';
        }

        //test if teh list is empty or not
        if (scannedText.length==0) {
            return '#';
        }

        //test input is legal
        if (!(scannedText[0].equals("F") || scannedText[0].equals("H"))){
            return '#';
        }

        //give turn
        char turn = scannedText[0].charAt(0);

        //test the format of the given things
        for (int i = 1; i<scannedText.length;i++){//start forom 1 is because the first one is turn
            try{
                int[] coordinate = FoxHoundUtils.read(scannedText[i]);
                for (int j:coordinate){
                    if (!(j>=1 && j<=FoxHoundUtils.DEFAULT_DIM))
                        return '#';//this may need to change to dim
                }
                //test that the coordinate given is valid
                if (((coordinate[0]+coordinate[1])-1)%2==1)
                    return '#';//the total length in a taxi-cub coordinate

                char[] bs = scannedText[i].toCharArray();
                if (!Character.isAlphabetic(bs[0]))
                    return '#';
            }
            catch (Exception e){
                return '#';
            }
        }

        //waiting for test
        String[] tempPlayers = new String[scannedText.length-1];
        for (int i = 1; i<scannedText.length; i++){
            tempPlayers[i-1] = scannedText[i];
        }

        //todo get dimension
        //int dim = dimOfLoadedGame(tempPlayers, turn);

        //update board
        for (int i = 0; i<tempPlayers.length; i++){
            players[i] = tempPlayers[i];
        }

        return turn;
    }

    public static int dimOfLoadedGame(String[] players, char turn){
        //todo this should be a mathematic question
        return FoxHoundUtils.DEFAULT_DIM;
    }
}
