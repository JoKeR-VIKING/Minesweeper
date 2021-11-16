package com.company.minesweeper;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MinesweeperBoard implements MouseListener
{
    JFrame frame;
    JPanel titlePanel, buttonPanel;
    JButton[][] guiBoard = new JButton[9][9];
    Color c1;
    Minesweeper minesweeper = new Minesweeper();

    MinesweeperBoard()
    {
        frame = new JFrame();
        titlePanel = new JPanel();
        buttonPanel = new JPanel();
        c1 = new Color(194, 186, 165);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        frame.getContentPane().setBackground(new Color(50, 50, 50));
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());

        JTextField titleName = new JTextField();
        titleName.setFont(new Font("Sans-Serif", Font.BOLD, 30));
        titleName.setOpaque(true);
        titleName.setText("Welcome to Minesweeper!");
        titleName.setBackground(new Color(50, 50, 50));
        titleName.setForeground(new Color(0, 128,0));
        titleName.setHorizontalAlignment(JLabel.CENTER);

        titlePanel.setBounds(0, 0, 500, 100);
        titlePanel.setLayout(new BorderLayout());
        titlePanel.add(titleName);

        buttonPanel.setLayout(new GridLayout(9, 9));

        for (int i=0;i<9;i++)
        {
            for (int j=0;j<9;j++)
            {
                guiBoard[i][j] = new JButton(" ");
                buttonPanel.add(guiBoard[i][j]);
                guiBoard[i][j].setFocusable(false);
                guiBoard[i][j].addMouseListener(this);
                guiBoard[i][j].setBackground(c1);
            }
        }

        frame.add(titlePanel, BorderLayout.NORTH);
        frame.add(buttonPanel);
        frame.setVisible(true);
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int i, j = 0;

        for (i=0;i<9;i++)
        {
            for (j=0;j<9;j++)
            {
                if (e.getSource() == guiBoard[i][j])
                    break;
            }

            if (j != 9)
                break;
        }

        if (guiBoard[i][j].getBackground() != c1 && !guiBoard[i][j].getText().equals(" "))
        {
            JOptionPane.showMessageDialog(frame, "The area is already marked unsafe!");
            return;
        }

        boolean loseState;

        if (e.getButton() == MouseEvent.BUTTON1)
            loseState = minesweeper.makeMove(i, j, "free");
        else
            loseState = minesweeper.makeMove(i, j, "mark");

        if (loseState)
        {
            JOptionPane.showMessageDialog(frame, "You stepped on a mine!");
            frame.dispose();
            new MinesweeperBoard();
        }
        else if (minesweeper.winCond())
        {
            JOptionPane.showMessageDialog(frame, "Congratulations! You found all the mines");
            frame.dispose();
            new MinesweeperBoard();
        }

        String[][] userBoard = minesweeper.userBoard;

        for (i=0;i<9;i++)
        {
            for (j=0;j<9;j++)
            {
                if (userBoard[i][j].equals("/"))
                    guiBoard[i][j].setBackground(new Color(0, 128, 0));
                else if (userBoard[i][j].equals("*"))
                    guiBoard[i][j].setBackground(new Color(255, 255, 0));
                else if (!userBoard[i][j].equals("."))
                {
                    guiBoard[i][j].setText(userBoard[i][j]);
                    guiBoard[i][j].setBackground(c1);
                }
                else
                    guiBoard[i][j].setBackground(c1);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e)
    {}

    @Override
    public void mouseExited(MouseEvent e)
    {}

    @Override
    public void mousePressed(MouseEvent e)
    {}

    @Override
    public void mouseReleased(MouseEvent e)
    {}
}