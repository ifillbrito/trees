package com.ifillbrito.tree.example.impl.multipletype;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gjib on 05.01.18.
 */
public class Root extends Element
{
    private List<Object> children = new ArrayList<>();

    public List<Object> getChildren()
    {
        return children;
    }

    public Root addChild(Object child)
    {
        this.children.add(child);
        return this;
    }

    public Root setChildren(List<Object> children)
    {
        this.children = children;
        return this;
    }

    @Override
    public String toString()
    {
        return "Root{" +
                "name='" + getName() + '\'' +
                ", children=" + children +
                '}';
    }
}
