package com.ifillbrito.tree.example.impl.multipletype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjib on 05.01.18.
 */
public class Container extends Element
{
    private List<Object> children = new ArrayList<>();

    public List<Object> getChildren()
    {
        return children;
    }

    public Container addChild(Object object)
    {
        this.children.add(object);
        return this;
    }

    @Override
    public String toString()
    {
        return "Container{" +
                "name='" + getName() + '\'' +
                ", children=" + children +
                '}';
    }
}
