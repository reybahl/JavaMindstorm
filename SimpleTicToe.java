
import org.xml.sax.ErrorHandler;

import java.util.*; //imports all classes in the java.util category

public class SimpleTicToe { //new class
    private char[][] ticTacToeArray = new char[3][3]; //new two dimensional array(char)

    public static void main(String[] args) throws InterruptedException { //main method
        SimpleTicToe simpleTicToe = new SimpleTicToe(); //object of SimpleTicToe
        simpleTicToe.startPlayVsComputer();
    }

    public void startPlayVsComputer() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        int r = 0;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                ticTacToeArray[i][j] = '_';
            }
        }

        printArray();

        while (r < 9) {
            System.out.println("Player 1 : You are X");
            System.out.println("Enter which row you want to put it in");
            int x = scanner.nextInt();
            if (!(x < 4)) {
                System.out.println("Sorry, there are three rows:you have to enter a number that is 1-3");
                Thread.sleep(1000);
                System.out.println("Exiting");
                System.exit(1);

            }
            System.out.println("Enter which column you want to put it in");
            int y = scanner.nextInt();
            if (!(y < 4)) {
                System.out.println("Sorry, there are three columns:you have to enter a number that is 1-3");
                Thread.sleep(1000);
                System.out.println("Exiting");
                System.exit(1);
            }

            ticTacToeArray[x - 1][y - 1] = 'X';
            r++;
            printArray();
            r++;
            if (r == 10) {
                System.out.println("End of game...it's a tie");
                break;
            }
            System.out.println("Now it's the computers turn");

            boolean amIWinning = AmIWinningV2();

            if (amIWinning) {
                printArray();
                System.out.println("Computer wins");
                System.exit(0);
            }


            boolean defense = Defense();

            if (!amIWinning && defense) {
                printArray();
                continue;
            }

            if (!amIWinning && !defense) {
                int HowManySpaces = 0;
                int t = 0;
                int tt = 0;
                //while (t < 3) {
                    if (ticTacToeArray[1][1] == '_') {
                        ticTacToeArray[1][1] = 'O';
                        printArray();
                        continue;
                    }

                    if (ticTacToeArray[1][1] == 'X') {
                        if (ticTacToeArray[0][0] == '_') {
                            ticTacToeArray[0][0] = 'O';
                            printArray();
                            continue;
                        }
                    }

                    if (ticTacToeArray[1][1] == 'O') {
                        if (
                                (ticTacToeArray[0][0] == 'X' && ticTacToeArray[2][2] == 'X')
                                        || (ticTacToeArray[0][0] == 'X' && ticTacToeArray[2][1] == 'X')
                                        || (ticTacToeArray[2][2] == 'X' && ticTacToeArray[0][1] == 'X')
                                        || (ticTacToeArray[2][0] == 'X' && ticTacToeArray[0][2] == 'X')
                                        || (ticTacToeArray[2][0] == 'X' && ticTacToeArray[0][1] == 'X')
                                        || (ticTacToeArray[0][2] == 'X' && ticTacToeArray[2][1] == 'X')
                        ) {
                            if(ticTacToeArray[1][0] == '_') {
                                ticTacToeArray[1][0] = 'O';
                            }
                            else {
                                putValSomeWhereElse();
                            }
                            printArray();
                        }
                        /*else if (ticTacToeArray[0][0] == 'X' && ticTacToeArray[2][2] == 'X')
                        {
                            ticTacToeArray[0][0] = 'O';
                        }
                        else if (ticTacToeArray[0][2] == 'X' && ticTacToeArray[2][2] == 'X')
                        {
                            ticTacToeArray[0][2] = 'O';
                        }*/

                    }



                //}

            }

        }


    }

    private void putValSomeWhereElse() {
        List<Integer> givenList = new ArrayList<>();
        for (Integer xx = 0; xx < 3; xx++) {
            for (Integer yy = 0; yy < 3; yy++) {
                if (ticTacToeArray[xx][yy] == '_') {
                    givenList.add(Integer.parseInt(xx.toString() + yy.toString()));
                }
            }
        }

        Random rand = new Random();
        Integer randomElement = givenList.get(rand.nextInt(givenList.size()));
        int xCord = 0;
        int yCord = 0;
        if (randomElement < 10) {
            xCord = 0;
            yCord = randomElement;
        } else {
            xCord = Integer.parseInt(String.valueOf(randomElement.toString().charAt(0)));
            yCord = Integer.parseInt(String.valueOf(randomElement.toString().charAt(1)));
        }
        ticTacToeArray[xCord][yCord] = 'O';

        //printArray();
    }

    private void printArray() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int iPos = i + 1;
                int jPos = j + 1;
                if (ticTacToeArray[i][j] == '_') {
                    System.out.print("|" + iPos + "," + jPos + "|");
                } else {
                    System.out.print("|" + " " + ticTacToeArray[i][j] + " " + "|");
                }

            }
            System.out.println();
        }
    }

    public boolean Defense() {
        // Check rows direction first.
        for (int i = 0; i < 3; i++) {
            int j = 0;
            if (ticTacToeArray[i][j] == 'X' && ticTacToeArray[i][(j + 1) % 3] == 'X') {
                // yes, I am winning...if the last space is empty
                if (ticTacToeArray[i][(j + 2) % 3] == '_') {
                    ticTacToeArray[i][(j + 2) % 3] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][j] == 'X' && ticTacToeArray[i][(j + 2) % 3] == 'X') {
                // yes I am winning...maybe
                if (ticTacToeArray[i][(j + 1) % 3] == '_') {
                    ticTacToeArray[i][(j + 1) % 3] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][(j + 1) % 3] == 'X' && ticTacToeArray[i][(j + 2) % 3] == 'X') {
                // yes I am winning...hopefully
                if (ticTacToeArray[i][j] == '_') {
                    ticTacToeArray[i][j] = 'O';
                    return true;
                }
            }
        }

        // Check column direction
        for (int j = 0; j < 3; j++) {
            int i = 0;
            if (ticTacToeArray[i][j] == 'X' && ticTacToeArray[(i + 1) % 3][j] == 'X') {
                // yes, I am winning
                if (ticTacToeArray[(i + 2) % 3][j] == '_') {
                    ticTacToeArray[(i + 2) % 3][j] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][j] == 'X' && ticTacToeArray[(i + 2) % 3][j] == 'X') {
                // yes I am winning
                if (ticTacToeArray[(i + 1) % 3][j] == '_') {
                    ticTacToeArray[(i + 1) % 3][j] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[(i + 1) % 3][j] == 'X' && ticTacToeArray[(i + 2) % 3][j] == 'X') {
                // yes I am winning
                if (ticTacToeArray[i][j] == '_') {
                    ticTacToeArray[i][j] = 'O';
                    return true;
                }
            }
        }


        // check diagnols now
        if ((ticTacToeArray[0][0] == 'X' && ticTacToeArray[1][1] == 'X')) {
            ticTacToeArray[2][2] = 'O';
            return true;
        }
        if ((ticTacToeArray[0][0] == 'X' && ticTacToeArray[2][2] == 'X')) {
            if (ticTacToeArray[1][1] == '_') {
                ticTacToeArray[1][1] = 'O';
                return true;
            }
        }

        if ((ticTacToeArray[1][1] == 'X' && ticTacToeArray[2][2] == 'X')) {
            if (ticTacToeArray[0][0] == '_') {
                ticTacToeArray[0][0] = 'O';
                return true;
            }
        }

        // check diagonals now
        if ((ticTacToeArray[0][2] == 'X' && ticTacToeArray[1][1] == 'X')) {
            if (ticTacToeArray[2][0] == '_') {
                ticTacToeArray[2][0] = 'O';
                return true;
            }
        }
        if (ticTacToeArray[0][2] == 'X' && ticTacToeArray[2][0] == 'X') {
            if (ticTacToeArray[1][1] == '_') {
                ticTacToeArray[1][1] = 'O';
                return true;
            }
        }
        if (ticTacToeArray[1][1] == 'X' && ticTacToeArray[2][0] == 'X') {
            if (ticTacToeArray[0][2] == '_') {
                ticTacToeArray[0][2] = 'O';
                return true;
            }
        }


        return false;
    }

    public boolean AmIWinningV2() {

        // Check rows direction first.
        for (int i = 0; i < 3; i++) {
            int j = 0;
            if (ticTacToeArray[i][j] == 'O' && ticTacToeArray[i][(j + 1) % 3] == 'O') {
                // yes, I am winning...if the last space is empty
                if (ticTacToeArray[i][(j + 2) % 3] == '_') {
                    ticTacToeArray[i][(j + 2) % 3] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][j] == 'O' && ticTacToeArray[i][(j + 2) % 3] == 'O') {
                // yes I am winning...maybe
                if (ticTacToeArray[i][(j + 1) % 3] == '_') {
                    ticTacToeArray[i][(j + 1) % 3] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][(j + 1) % 3] == 'O' && ticTacToeArray[i][(j + 2) % 3] == 'O') {
                // yes I am winning...hopefully
                if (ticTacToeArray[i][j] == '_') {
                    ticTacToeArray[i][j] = 'O';
                    return true;
                }
            }
        }

        // Check column direction
        for (int j = 0; j < 3; j++) {
            int i = 0;
            if (ticTacToeArray[i][j] == 'O' && ticTacToeArray[(i + 1) % 3][j] == 'O') {
                // yes, I am winning
                if (ticTacToeArray[(i + 2) % 3][j] == '_') {
                    ticTacToeArray[(i + 2) % 3][j] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[i][j] == 'O' && ticTacToeArray[(i + 2) % 3][j] == 'O') {
                // yes I am winning
                if (ticTacToeArray[(i + 1) % 3][j] == '_') {
                    ticTacToeArray[(i + 1) % 3][j] = 'O';
                    return true;
                }
            } else if (ticTacToeArray[(i + 1) % 3][j] == 'O' && ticTacToeArray[(i + 2) % 3][j] == 'O') {
                // yes I am winning
                if (ticTacToeArray[i][j] == '_') {
                    ticTacToeArray[i][j] = 'O';
                    return true;
                }
            }
        }


        // check diagnols now
        if ((ticTacToeArray[0][0] == 'O' && ticTacToeArray[1][1] == 'O')) {
            ticTacToeArray[2][2] = 'O';
            return true;
        }
        if ((ticTacToeArray[0][0] == 'O' && ticTacToeArray[2][2] == 'O')) {
            if (ticTacToeArray[1][1] == '_') {
                ticTacToeArray[1][1] = 'O';
                return true;
            }
        }

        if ((ticTacToeArray[1][1] == 'O' && ticTacToeArray[2][2] == 'O')) {
            if (ticTacToeArray[0][0] == '_') {
                ticTacToeArray[0][0] = 'O';
                return true;
            }
        }

        // check diagonals now
        if ((ticTacToeArray[0][2] == 'O' && ticTacToeArray[1][1] == 'O')) {
            if (ticTacToeArray[2][0] == '_') {
                ticTacToeArray[2][0] = 'O';
                return true;
            }
        }
        if (ticTacToeArray[0][2] == 'O' && ticTacToeArray[2][0] == 'O') {
            if (ticTacToeArray[1][1] == '_') {
                ticTacToeArray[1][1] = 'O';
                return true;
            }
        }
        if (ticTacToeArray[1][1] == 'O' && ticTacToeArray[2][0] == 'O') {
            if (ticTacToeArray[0][2] == '_') {
                ticTacToeArray[0][2] = 'O';
                return true;
            }
        }


        return false;
    }

}