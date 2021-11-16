package com.company.minesweeper;

import java.util.*;

public class Minesweeper
{
    String[][] innerBoard, userBoard;
    int mineMark, safeMark;

    public void initializeBoards(String[][] innerBoard, String[][] userBoard, int minesCount)
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

    public void revealPositions(int x, int y, String[][] userBoard, String[][] innerBoard, boolean[][] visited)
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

    public boolean safeMarkWin(String[][] userBoard, String[][] innerBoard)
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

    Minesweeper()
    {
        mineMark = safeMark = 0;
        innerBoard = new String[9][9];
        userBoard = new String[9][9];
        for (String[] x : innerBoard)
            Arrays.fill(x, ".");

        initializeBoards(innerBoard, userBoard, 10);
    }

    public boolean makeMove(int x, int y, String claimCommand)
    {
        if (claimCommand.equals("free"))
        {
            if (innerBoard[x][y].equals("X"))
            {
                return true;
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
                else
                    safeMark--;

                userBoard[x][y] = ".";
            }
            else
            {
                if (innerBoard[x][y].equals("X"))
                    mineMark++;
                else
                    safeMark++;

                userBoard[x][y] = "*";
            }
        }

        return false;
    }

    public boolean winCond()
    {
        return (mineMark == 10 && safeMark == 0)|| safeMarkWin(userBoard, innerBoard);
    }

    public String[][] getUserBoard()
    {
        return userBoard;
    }
}