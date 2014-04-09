package com.bradP.SG;

import java.util.ArrayList;
import java.util.PriorityQueue;

public class Driver
{
    StateGraph s;
    Callable funcCaller;
    Node FirstN;
    int step;
    PriorityQueue<Node> pq;
    Conditional c;
    boolean finished;
    Node CompErr;
    public Driver(Node firstN, Callable func)
    {
        this(firstN, func, null);
    }

    public Driver(Node firstN, Callable func, Conditional c)
    {
        s = new StateGraph();
        funcCaller = func;
        FirstN = firstN;
        step = 0;
        pq = new PriorityQueue<Node>();
        this.c = c;
        finished = false;
        CompErr = null;
    }

    public void run()
    {
        run(this.c);
    }

    public void run(Conditional c)
    {
        if(finished)
        {
            return;
        }

        Node n = FirstN, t;
        pq.add(n);

        if(c == null)
        {
            System.out.println("Will run forever");
            throw new RuntimeException();
        }
        Node temp = null;
        while(c.checkCondition())
        {
            t = pq.poll();
            if(t == null)
            {
                break;
            }
            ArrayList<Node> nodes = funcCaller.generateNodes(t);

            for(int j = 0; j < nodes.size(); j++)
            {
                temp = nodes.get(j);
                s.addEdge(t, nodes.get(j));

                pq.add(nodes.get(j));
            }
            //s.printGraph(Board.class);
        }
        if(!c.checkCondition())
        {
            finished = true;
            CompErr = temp;
        }
    }

    public Node getFinishingNode()
    {
        if(c == null)
        {
            System.out.println("Why you do this what were u expecting");
        }
        return CompErr;
    }

    public int getMaxDepth()
    {
        return s.getMaxDepth();
    }

    public PriorityQueue<Node> getPQ()
    {
        return pq;
    }

    public void step()
    {
        if(c != null && !c.checkCondition())
        {
            return;
        }

        Node t;
        if(step == 0)
        {
            pq.add(FirstN);
        }

        t = pq.poll();
        ArrayList<Node> nodes = funcCaller.generateNodes(t);
        for(int j = 0; j < nodes.size(); j++)
        {
            s.addEdge(t, nodes.get(j));
            pq.add(nodes.get(j));
        }
        step++;
    }

    public StateGraph getSG()
    {
        return s;
    }
}
