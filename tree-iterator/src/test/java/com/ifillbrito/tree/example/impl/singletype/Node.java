package com.ifillbrito.tree.example.impl.singletype;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by gjib on 07.01.18.
 */
public class Node
{
    private String name;
    private int value;
    private Color color;
    private List<Node> children;

    public List<Node> getChildren()
    {
        return children;
    }

    public String getName()
    {
        return name;
    }

    public int getValue()
    {
        return value;
    }

    public Node setValue(int value)
    {
        this.value = value;
        return this;
    }

    public Node setValue(Function<Integer, Integer> function)
    {
        this.value = function.apply(value);
        return this;
    }

    public Color getColor()
    {
        return color;
    }

    public Node setColor(Color color)
    {
        this.color = color;
        return this;
    }

    public enum Color
    {
        YELLOW,RED,GREEN;
    }

    private Node(String name, int value, Color color)
    {
        this.name = name;
        this.value = value;
        this.color = color;
    }

    public static Node create(String name, int value, Color color)
    {
        return new Node(name, value, color);
    }

    public Node addChild(Node node)
    {
        if (children == null)
        {
            children = new ArrayList<>();
        }
        children.add(node);
        return this;
    }

    public boolean isLeaf()
    {
        return children == null || children.isEmpty();
    }

    public boolean isRed()
    {
        return Color.RED.equals(this.getColor());
    }
    public boolean isYellow()
    {
        return Color.YELLOW.equals(this.getColor());
    }
    public boolean isGreen()
    {
        return Color.GREEN.equals(this.getColor());
    }

    public boolean isValueEven()
    {
        return this.value % 2 == 0;
    }
}
