package com.bradP.SG;

import java.util.ArrayList;
import java.util.Random;

public class Main
{
    private static class nodeGenerator implements Callable
    {
        @Override
        public ArrayList<Node> generateNodes(Node t)
        {
            if(t == null)
            {
                return new ArrayList<Node>();
            }
            ArrayList<Node> nodes = new ArrayList<Node>();

            int[][] neighbors = {{1,0},{-1,0},{0,1},{0,-1}};

            Board b = (Board) t.getObject(Board.class);

            for(int i = 0; i < 4; i++)
            {
                if(!b.moveAble(b.e_x + neighbors[i][0], b.e_y + neighbors[i][1]))
                {
                    continue;
                }
                Board cb = (Board) b.copy();
                if(!cb.getPos(cb.e_x + neighbors[i][0], cb.e_y + neighbors[i][1]).equals("e"))
                {
                    cb.setPos(cb.e_x + neighbors[i][0], cb.e_y + neighbors[i][1], "e");
                    nodes.add(new Node(t.getComp(), cb));
                }
            }
            return nodes;
        }
    }

    public static int manHat(int x1, int y1, int x2, int y2)
    {
        return (int)Math.abs(x1 - x2) + Math.abs(y1 - y2);
    }

    private static class ManHat implements NodeComp
    {
        @Override
        public int compareTo(Node n1, Node n2)
        {
            if(n1 == null || n2 == null)
            {
                return -1;
            }
            else
            {
                Board tempB = (Board) n1.getObject(Board.class);
                int b1 = manHat(tempB.e_x, tempB.e_y, tempB.width-1, tempB.height-1);
                tempB = (Board) n2.getObject(Board.class);
                int b2 = manHat(tempB.e_x, tempB.e_y, tempB.width-1, tempB.height-1);
                int t;
                if(b1 == b2)
                {
                    //return 0;
                    t = n1.getDepth() < n2.getDepth() ? -1 : 1;
                }
                else
                {
                    t = b1 < b2 ? -1 : 1;
                }
                return t;
            }
        }
    }

    public static void main(String[] args)
    {
        Driver d;

        Board b = new Board(8, 39);
        b.setPos(0, 0, "e");
        b.createWalls(47);

        BoardCondit bc = new BoardCondit();
        d = new Driver(new Node(new ManHat(), b), new nodeGenerator(), bc);
        bc.setDriver(d);
        d.run();

        d.getSG().printGraph(Board.class);
        d.getSG().printPath(d.getFinishingNode());
    }

    public static ArrayList<Node> generateNodes(Node n)
    {
        ArrayList<Node> nodes = new ArrayList<Node>();
        Random r = new Random();

        int max = r.nextInt(10);
        for(int i = 0; i < max; i++)
        {
            Node t = n.copy();
            Board b = (Board) t.getObject(Board.class);


            int x, y;
            x = r.nextInt(b.width);
            y = r.nextInt(b.height);

            String s = String.valueOf(Character.toChars(r.nextInt(128)));

            b.setPos(x, y, s);

            nodes.add(t);
        }

        return nodes;
    }
}
