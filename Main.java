package minesweeper;

import java.util.*;

public class Main
{
    public static void initializeBoards(String[][] innerBoard, String[][] userBoard, int minesCount)
    {
        while (minesCount > 0)
        {
            Random random = new Random();
            int i = random.nextInt(9);
            int j = random.nextInt(9);

            if (!innerBoard[i][j].equals("X"))
            {
                innerBoard[i][j] = "X";
                minesCount--;
            }
        }

        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++)
            {
                if (innerBoard[i][j].equals("."))
                {
                    int checkMine = 0;
                    if (i != 0 && innerBoard[i-1][j].equals("X"))
                        checkMine++;
                    if (j != 0 && innerBoard[i][j-1].equals("X"))
                        checkMine++;
                    if (i != 0 && j != 0 && innerBoard[i-1][j-1].equals("X"))
                        checkMine++;
                    if (i != 0 && j != 8 && innerBoard[i-1][j+1].equals("X"))
                        checkMine++;
                    if (i != 8 && innerBoard[i+1][j].equals("X"))
                        checkMine++;
                    if (i != 8 && j != 0 && innerBoard[i+1][j-1].equals("X"))
                        checkMine++;
                    if (j != 8 && innerBoard[i][j+1].equals("X"))
                        checkMine++;
                    if (i != 8 && j != 8 && innerBoard[i+1][j+1].equals("X"))
                        checkMine++;

                    if (checkMine != 0)
                        innerBoard[i][j] = Integer.toString(checkMine);
                }
            }
        }

        for (String[] x : userBoard)
            Arrays.fill(x, ".");
    }

    public static void display(String[][] userBoard)
    {
        int i = 1;

        System.out.println(" |123456789|\n-|---------|");
        for (String[] it1 : userBoard)
        {
            System.out.print(i++ + "|");
            for (String it2 : it1)
            {
                System.out.print(it2);
            }

            System.out.println("|");
        }

        System.out.println("-|---------|");
    }

    public static void revealPositions(int x, int y, String[][] userBoard, String[][] innerBoard, boolean[][] visited)
    {
        userBoard[x][y] = innerBoard[x][y];
        if (!innerBoard[x][y].equals("."))
            return;

        userBoard[x][y] = "/";
        visited[x][y] = true;

        if (x != 0 && !visited[x-1][y])
            revealPositions(x-1, y, userBoard, innerBoard, visited);
        if (y != 0 && !visited[x][y-1])
            revealPositions(x, y-1, userBoard, innerBoard, visited);
        if (x != 0 && y != 0 && !visited[x-1][y-1])
            revealPositions(x-1, y-1, userBoard, innerBoard, visited);
        if (x != 0 && y != 8 && !visited[x-1][y+1])
            revealPositions(x-1, y+1, userBoard, innerBoard, visited);
        if (x != 8 && y != 0 && !visited[x+1][y-1])
            revealPositions(x+1, y-1, userBoard, innerBoard, visited);
        if (x != 8 && !visited[x+1][y])
            revealPositions(x+1, y, userBoard, innerBoard, visited);
        if (y != 8 && !visited[x][y+1])
            revealPositions(x, y+1, userBoard, innerBoard, visited);
        if (x != 8 && y != 8 && !visited[x+1][y+1])
            revealPositions(x+1, y+1, userBoard, innerBoard, visited);
    }

    public static boolean safeMarkWin(String[][] userBoard, String[][] innerBoard)
    {
        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++)
            {
                if (userBoard[i][j].equals(".") && !innerBoard[i][j].equals("X"))
                    return false;
            }
        }

        return true;
    }

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        String[][] innerBoard = new String[9][9];
        String[][] userBoard = new String[9][9];
        for (String[] x : innerBoard)
            Arrays.fill(x, ".");

        System.out.println("How many mines do you want on the field?");
        int minesCount = scanner.nextInt();

        initializeBoards(innerBoard, userBoard, minesCount);

        boolean gameOn = true;
        int x, y, mineMark = 0;
        String claimCommand;

        while (gameOn)
        {
            display(userBoard);

            while (true)
            {
                System.out.println("Set/unset mine marks or claim a cell as free:");
                y = scanner.nextInt()-1;
                x = scanner.nextInt()-1;
                claimCommand = scanner.next();

                try
                {
                    if (userBoard[x][y].equals("/"))
                    {
                        System.out.println("Space already explored!");
                        continue;
                    }

                    Integer.parseInt(userBoard[x][y]);
                    System.out.println("Space already explored!");
                }
                catch (NumberFormatException ne)
                {
                    if (claimCommand.equals("free"))
                    {
                        if (innerBoard[x][y].equals("X"))
                        {
                            display(userBoard);
                            System.out.println("You stepped on a mine and failed!");
                            gameOn = false;
                        }
                        else
                        {
                            boolean[][] visited = new boolean[9][9];
                            for (boolean[] it1 : visited)
                                Arrays.fill(it1, false);

                            revealPositions(x, y, userBoard, innerBoard, visited);
                        }
                    }
                    else
                    {
                        if (userBoard[x][y].equals("*"))
                        {
                            if (innerBoard[x][y].equals("X"))
                                mineMark--;

                            userBoard[x][y] = ".";
                        }
                        else
                        {
                            if (innerBoard[x][y].equals("X"))
                                mineMark++;

                            userBoard[x][y] = "*";
                        }
                    }

                    break;
                }
            }

            if (mineMark == minesCount || safeMarkWin(userBoard, innerBoard))
            {
                display(userBoard);
                System.out.println("Congratulations! You found all the mines!");
                gameOn = false;
            }
        }
    }
}
