package com.bradP.SG;


import java.util.PriorityQueue;

public class BoardCondit implements Conditional
{
    Driver d;
    public BoardCondit()
    {
        this(null);
    }

    public BoardCondit(Driver d)
    {
        this.d = d;
    }

    public void setDriver(Driver d)
    {
        this.d = d;
    }

    @Override
    public boolean checkCondition()
    {
        if(this.d == null)
        {
            return false;
        }
        PriorityQueue<Node> n = d.getPQ();
        Node t_n = n.peek();
        if(t_n != null)
        {
            Board b = (Board) t_n.getObject(Board.class);

            if(b.getPos(b.width-1, b.height-1).equals("e"))
            {
                return false;
            }
        }

        return true;
    }
}
