package com.bradP.SG;

import java.util.ArrayList;

public class StateGraph
{
    ArrayList<ArrayList<Node>> nodes;

    int numNodes;
    int maxDepth;
    public StateGraph()
    {
        nodes = new ArrayList<ArrayList<Node>>();
        numNodes = 0;
        maxDepth = 0;
    }

    public void addNode(Node n)
    {
        if(numNodes == 0)
        {
            n.setDepth(0);
        }
        n.setId(numNodes++);
        nodes.add(new ArrayList<Node>());
        nodes.get(n.getId()).add(n);
    }

    public ArrayList<ArrayList<Node>> getAL()
    {
        return nodes;
    }

    public Node getByID(int id)
    {
        if(0 <= id && id < nodes.size())
        {
            return nodes.get(id).get(0);
        }
        return null;
    }

    public void addEdge(int i, int j)
    {
        addEdge(getByID(i), getByID(j));
    }

    public void addEdge(Node n1, Node n2)
    {
        if(n1 == null || n2 == null)
        {
            return;
        }

        if(getByID(n1.getId()) == null)
        {
            addNode(n1);
        }
        if(getByID(n2.getId()) == null)
        {
            addNode(n2);
            n2.setDepth(n1.getDepth()+1);
        }

        if(n1.getId() < nodes.size())
        {
            nodes.get(n1.getId()).add(n2);
        }

        if(n1.getDepth() > maxDepth)
        {
            maxDepth = n1.getDepth();
        }
        if(n2.getDepth() > maxDepth)
        {
            maxDepth = n2.getDepth();
        }
    }

    public Node copyNode(int id)
    {
        if(0 <= id && id < nodes.size())
        {
            return nodes.get(id).get(0).copy();
        }
        return null;
    }

    public void printGraph()
    {
        for(int i = 0; i < nodes.size(); i++)
        {
            System.out.print("Node: " + nodes.get(i).get(0).getId() + " connects to ");
            for(int j = 0; j < nodes.get(i).size(); j++)
            {
                System.out.print(nodes.get(i).get(j).getId() + ", ");
            }
            System.out.println("");
        }
    }

    public void printGraph(Class<? extends Copyable> c)
    {
        System.out.println("");
        for(int i = 0; i < nodes.size(); i++)
        {
            System.out.println("Board: " + nodes.get(i).get(0).getId());
            for(int j = 0; j < nodes.get(i).size(); j++)
            {
                System.out.println("|");
                System.out.println("V " + nodes.get(i).get(j).getId() + " D: " + nodes.get(i).get(j).getDepth());

                nodes.get(i).get(j).print(c);
            }
            System.out.println("");
        }
    }

    public int getMaxDepth()
    {
        return maxDepth;
    }

    public void printPath(Node n)
    {
        if(n == null || getByID(n.getId()) == null)
        {
            return;
        }

        ArrayList<Node> pNodes = new ArrayList<Node>();

        Node temp = n;
        pNodes.add(temp);
        while(true)
        {
            for(ArrayList<Node> aN : nodes)
            {
                if(aN.contains(temp) && aN.get(0).getDepth() < temp.getDepth())
                {
                    temp = aN.get(0).copy();
                    pNodes.add(temp);
                    break;
                }
            }

            if(temp.getDepth() == 0)
            {
                break;
            }
        }

        for(Node n1 : pNodes)
        {
            System.out.println("^" + n1.getDepth());
            System.out.println("|");
            n1.getObject(Board.class).print();
            System.out.println("");

        }
    }
}