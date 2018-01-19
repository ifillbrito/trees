package com.ifillbrito.tree.example.impl.multipletype;

/**
 * Created by gjib on 05.01.18.
 */
public class Element
{
    private String name;

    public String getName()
    {
        return name;
    }

    public Element setName(String name)
    {
        this.name = name;
        return this;
    }

    @Override
    public String toString()
    {
        return "Element{" +
                "name='" + name + '\'' +
                '}';
    }
}
