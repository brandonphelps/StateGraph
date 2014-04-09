package com.bradP.SG;

import java.util.ArrayList;

public class Node implements Comparable<Node>
{
    static int nextId = 0;
    private int id;
    private int depth;
    ArrayList<Copyable> holder;

    NodeComp comparitor;

    public Node(Copyable... copies)
    {
        //id = nextId++;
        depth = 0;
        id = -1;
        holder = new ArrayList<Copyable>();
        for(int i = 0; i < copies.length; i++)
        {
            holder.add(copies[i]);
        }
        comparitor = null;
    }

    public Node(NodeComp c, Copyable... copies)
    {
        this(copies);

        comparitor = c;
    }

    public void setId(int i)
    {
        id = i;
    }

    public int getId()
    {
        return id;
    }

    public void addObject(Copyable o)
    {
        holder.add(o);
    }

    public Copyable getObject(Class<? extends Copyable> c)
    {
        for(int i = 0; i < holder.size(); i++)
        {
            if(c.isInstance(holder.get(i)))
            {
                return holder.get(i);
            }
        }
        return null;
    }

    public Node copy()
    {
        Node n = new Node(comparitor);
        for(Copyable c : holder)
        {
            n.addObject(c);
        }
        n.setDepth(getDepth());
        n.setId(getId());
        return n;
    }

    public void setDepth(int i)
    {
        depth = i;
    }

    public int getDepth()
    {
        return depth;
    }

    public NodeComp getComp()
    {
        return comparitor;
    }

    public void print(Class<? extends Copyable> c)
    {
        getObject(c).print();
    }

    @Override
    public int compareTo(Node o)
    {
        if(comparitor == null)
        {
            if(o != null)
            {
                if(this.getId() == o.getId())
                {
                    return 0;
                }
                return this.getId() < o.getId() ? -1 : 1;
            }
            else
            {
                return -1;
            }
        }
        else
        {
            return comparitor.compareTo(this, o);
        }
    }

    @Override
    public boolean equals(Object n1)
    {
        if(n1 == null)
        {
            return false;
        }
        if(n1 instanceof Node)
        {
            Node n = (Node) n1;
            if(n.getId() == getId() && n.getDepth() == getDepth())
            {
                return true;
            }
        }
        return false;
    }
}
