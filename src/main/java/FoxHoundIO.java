import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * A utility class for the fox hound program.
 * 
 * It contains helper functions for all file input / output
 * related operations such as saving and loading a game.
 */
public class FoxHoundIO {//todo IO may work
    public static void SaveGame(String[] players, char turn, Path filePath) throws IOException {
        FileWriter outFile = new FileWriter(String.valueOf(filePath));
        PrintWriter out = new PrintWriter(outFile);
        out.print(turn +" ");
        for (String i : players) out.print(i+" ");
        out.close();
    }

    public static String[] LoadGame(Path filePath) throws Exception{
        Scanner scan = new Scanner(filePath);
        char[] as = scan.nextLine().toCharArray();
        scan.close();

        String[] str = new String[(as.length-2+1)/3+1];
        str[0]=Character.toString(as[0]);
        for (int i = 2; i<as.length; i+=3){
            str[(i+1)/3]=""+as[i]+as[i+1];
        }
        return str;
    }
}
