import java.io.FileWriter;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * A utility class for the fox hound program.
 * <p>
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {

    //save game with default dimension
    public static boolean saveGame(final String[] players, final char turn, final Path pathSave) {
        //basic test
        //is the dimension equals to default
        if (players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is not match");

        return saveGameDim(players, turn, pathSave);
    }

    //this is saving for different dim
    public static boolean saveGameDim(final String[] players, final char turn, final Path pathSave) {
        //is the path good
        if (pathSave == null)
            throw new NullPointerException("no path given");

        //is path not exits
        if (pathSave.toFile().exists())
            throw new NullPointerException("file exists is not exists");

        //save part
        //start to write file
        FileWriter outFile;
        try {
            outFile = new FileWriter(String.valueOf(pathSave));
            outFile.write(turn + " ");
            for (final String i : players) outFile.write(i + " ");
            outFile.close();
            return true;
        } catch (final Exception e) {
            System.out.println("Saving file failed, please try again");
            return false;
        }
    }

    //onlyLoad the game with default dimension
    public static char loadGame(final String[] players, final Path pathLoad) {
        //test part
        //if path good
        if (pathLoad == null||players == null)
            throw new NullPointerException("path is empty");

        //testing things are at there initial position, for default dimension
        if (players.length != FoxHoundUtils.initialisePositions(FoxHoundUtils.DEFAULT_DIM).length)
            throw new IllegalArgumentException("dimension is not match");

        //loading part
        //take the value from txt
        String[] scannedText;
        try {
            //is path exists
            if (!pathLoad.toFile().exists())
                return '#';

            //see if the second line has something
            final Scanner testScanning = new Scanner(pathLoad);
            testScanning.nextLine();
            if (testScanning.hasNextLine()) return '#';
            testScanning.close();

            //read the data
            final Scanner scan = new Scanner(pathLoad);
            final String[] temp = new String[26];//number of players would not larger than this value
            int count = 0;
            for (int i = 0; scan.hasNext(); i++) {
                temp[i] = scan.next();
                count += 1;
            }

            //pass value
            scannedText = new String[count];
            System.arraycopy(temp, 0, scannedText, 0, count);
            scan.close();

            //test if the list is empty or not
            if (scannedText.length == 0) {
                return '#';
            }

            //test input figure is legal
            if (!(scannedText[0].equals("F") || scannedText[0].equals("H"))) {
                return '#';
            }

            //test the format of the loaded file
            for (int i = 1; i < scannedText.length; i++) {//start forom 1 is because the first one is turn

                //each coordinate has a letter axises
                final char[] bs = scannedText[i].toCharArray();
                if (!Character.isAlphabetic(bs[0]))
                    return '#';
                final int[] coordinate = FoxHoundUtils.read(scannedText[i]);

                //correct range of coordinate
                for (final int j : coordinate) {
                    if (!(j >= 1 && j <= FoxHoundUtils.DEFAULT_DIM))//this may need to change to dim
                        return '#';
                }

                //test that the coordinate given is valid in the board, which is black
                if (((coordinate[0] + coordinate[1]) - 1) % 2 == 1)//the total length in a taxi-cub coordinate
                    return '#';
            }
        } catch (final Exception e) {
            System.out.println("loading process has some error");
            return '#';
        }

        //give turn
        final char turn = scannedText[0].charAt(0);

        //change values of players
        System.arraycopy(scannedText, 1, players, 0, scannedText.length - 1);

        return turn;
    }

    //this is the condition that loading game with a different dimension
    //if this is used the position where this is called need to add another function in order to update the dimension.
    public static char loadGameDim(String[] players, final Path pathLoad) {
        //test part
        //testing things are at there initial position, for default dimension
        if (players.length != FoxHoundUtils.initialisePositions(dimGet(players, FoxHoundUtils.FOX_FIELD)).length)
            throw new IllegalArgumentException("dimension is not match");

        //if path good
        if (pathLoad == null)
            throw new NullPointerException("path is empty");

        //loading part
        //take the value from txt
        String[] scannedText;
        try {
            //is path exists
            if (!pathLoad.toFile().exists())
                return '#';

            //see if the second line has something
            final Scanner testScanning = new Scanner(pathLoad);
            testScanning.nextLine();
            if (testScanning.hasNextLine()) return '#';
            testScanning.close();

            //read the data
            final Scanner scan = new Scanner(pathLoad);
            final String[] temp = new String[26];//number of players would not larger than this value
            int count = 0;
            for (int i = 0; scan.hasNext(); i++) {
                temp[i] = scan.next();
                count += 1;
            }

            //pass value
            scannedText = new String[count];
            System.arraycopy(temp, 0, scannedText, 0, count);
            scan.close();

            //test if the list is empty or not
            if (scannedText.length == 0) {
                return '#';
            }

            //test input figure is legal
            if (!(scannedText[0].equals("F") || scannedText[0].equals("H"))) {
                return '#';
            }

            //test the format of the loaded file
            for (int i = 1; i < scannedText.length; i++) {//start forom 1 is because the first one is turn

                //each coordinate has a letter axises
                final char[] bs = scannedText[i].toCharArray();
                if (!Character.isAlphabetic(bs[0]))
                    return '#';
                final int[] coordinate = FoxHoundUtils.read(scannedText[i]);

                //see what is the dimension
                final String[] tempPlayers = new String[scannedText.length - 1];
                System.arraycopy(scannedText, 1, tempPlayers, 0, scannedText.length - 1);
                final int dim = dimGet(tempPlayers, scannedText[0].charAt(0));

                //correct range of coordinate
                for (final int j : coordinate) {
                    if (!(j >= 1 && j <= dim))//this may need to change to dim
                        return '#';
                }

                //test that the coordinate given is valid in the board, which is black
                if (((coordinate[0] + coordinate[1]) - 1) % 2 == 1)//the total length in a taxi-cub coordinate
                    return '#';
            }
        } catch (final Exception e) {
            System.out.println("loading process has some error");
            return '#';
        }

        //give turn
        final char turn = scannedText[0].charAt(0);

        players = new String[scannedText.length - 1];

        //change values of players
        System.arraycopy(scannedText, 1, players, 0, scannedText.length - 1);

        return turn;
    }

    //this is the function determine the loaded game dimension
    public static int dimGet(final String[] players, final char turn) {
        final int[][] coordinates = new int[players.length][];

        //read fox coordinate
        coordinates[players.length - 1] = FoxHoundUtils.read(players[players.length - 1]);

        //read Hound coodinate + find total move
        int totalMove = 0;
        for (int i = 0; i < players.length - 1; i++) {
            coordinates[i] = FoxHoundUtils.read(players[i]);
            totalMove += coordinates[i][1];
        }

        //find the fox move
        if (turn == FoxHoundUtils.HOUND_FIELD) {
            totalMove += 1;
        }

        //number of found determines the dimension
        final int[] tempDim = {(players.length - 1) * 2, (players.length - 1) * 2 + 1};

        int dimension;

        //find which temp_dim is true
        if (totalMove % 2 == 0) {
            if (coordinates[coordinates.length - 1][1] % 2 == 0) {
                dimension = tempDim[0];
            } else dimension = tempDim[1];
        } else {
            if (coordinates[coordinates.length - 1][1] % 2 == 1) {
                dimension = tempDim[0];
            } else dimension = tempDim[1];
        }

        //see if the fox have the correct move numbers
        if (totalMove - coordinates[players.length - 1][1] < 0) {//this means the fox has move so many steps that exceed the total
            System.err.println("impossible board coordinate");
            return 8;
        }

        return dimension;
    }
}
