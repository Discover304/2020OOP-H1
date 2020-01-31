import java.util.Scanner;

public class test {
    public static void main(String[] args) {
        //int i = Integer.parseInt(Character.toString('1'))+1;
        Scanner str = new Scanner("N45");
        String a = str.next();
        System.out.println(a);
    }

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
}

