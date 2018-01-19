package com.ifillbrito.tree.node;

/**
 * Created by gjib on 20.01.18.
 */
public class NodeWrapper<Type, Parent>
{
    private String path;
    private Parent parent;
    private Type object;

    public String getPath()
    {
        return path;
    }

    public NodeWrapper<Type, Parent> setPath(String path)
    {
        this.path = path;
        return this;
    }

    public Parent getParent()
    {
        return parent;
    }

    public NodeWrapper<Type, Parent> setParent(Parent parent)
    {
        this.parent = parent;
        return this;
    }

    public Type getObject()
    {
        return object;
    }

    public NodeWrapper<Type, Parent> setObject(Type object)
    {
        this.object = object;
        return this;
    }
}
