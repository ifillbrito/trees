package com.github.ifillbrito.tree.iterator.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Created by gjib on 09.03.18.
 */
public class Node
{
    private String name;
    private Integer value;
    private List<Node> children = new ArrayList<>();

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public Integer getValue()
    {
        return value;
    }

    public void setValue(Integer value)
    {
        this.value = value;
    }

    public void setValue(Function<Integer, Integer> function)
    {
        if ( value == null ) value = 0;
        setValue(function.apply(value));
    }

    public boolean isEven()
    {
        return value % 2 == 0;
    }

    public List<Node> getChildren()
    {
        return children;
    }

    public void setChildren(List<Node> children)
    {
        this.children = children;
    }
}
