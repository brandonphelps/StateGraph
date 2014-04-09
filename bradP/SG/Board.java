package com.bradP.SG;

import java.util.Random;

public class Board implements Copyable, Conditional
{
    String[][] m_board;
    int width, height;
    public int e_x, e_y;
    public Board(int w, int h)
    {
        m_board = new String[w][h];
        e_x = 0;
        e_y = 0;
        width = w;
        height = h;
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                m_board[i][j] = "X";
            }
        }
    }

    public void createWalls(int maxWalls)
    {
        Random r = new Random();
        r.setSeed(40L);
        int r_x, r_y, numWalls = r.nextInt(maxWalls);
        for(int i = 0; i < numWalls; i++)
        {
            r_x = r.nextInt(width);
            r_y = r.nextInt(height);
            if(moveAble(r_x, r_y))
            {
                setPos(r_x, r_y, "O");
            }
        }
    }

    public boolean moveAble(int w, int h)
    {
        if(isValid(w, h) && m_board[w][h].equals("X"))
        {
            return true;
        }
        return false;
    }

    public void createWalls()
    {
        createWalls((width - (int)Math.ceil(Math.log(width))) * (height - (int)Math.ceil(Math.log(height))));
    }

    public boolean isValid(int x, int y)
    {
        if(x < 0 || x >= width || y < 0 || y >= height)
        {
            return false;
        }
        return true;
    }

    public String getPos(int w, int h)
    {
        if(isValid(w, h))
        {
            return m_board[w][h];
        }
        return "";
    }

    public void setPos(int w, int h, String s)
    {
        if(isValid(w, h))
        {
            m_board[w][h] = s;
            if(s.equals("e"))
            {
                e_x = w;
                e_y = h;
            }
        }
    }

    @Override
    public Copyable copy()
    {
        Board b = new Board(width, height);
        for(int i = 0; i < width; i++)
        {
            for(int j = 0; j < height; j++)
            {
                b.setPos(i, j, m_board[i][j]);
            }
        }
        b.e_x = this.e_x;
        b.e_y = this.e_y;
        return b;
    }

    @Override
    public void print()
    {
        for(int i = 0; i < width; i++)
        {
            for(int j = 0 ; j < height; j++)
            {
                System.out.print(m_board[i][j] + " ");
            }
            System.out.println("");
        }
    }

    @Override
    public boolean checkCondition()
    {
        if(m_board[width-1][height-1].equals("e"))
        {
            return true;
        }
        return false;
    }
}
