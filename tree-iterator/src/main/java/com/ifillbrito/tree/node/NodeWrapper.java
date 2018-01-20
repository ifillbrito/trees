package com.ifillbrito.tree.node;

/**
 * Created by gjib on 20.01.18.
 */
public class NodeWrapper<Type>
{
    private String path;
    private NodeWrapper parent;
    private Type object;

    public NodeWrapper(Type object)
    {
        this.object = object;
    }

    public static <T> NodeWrapper<T> of (T object)
    {
        return new NodeWrapper<>(object);
    }

    public String getPath()
    {
        return path;
    }

    public NodeWrapper<Type> setPath(String path)
    {
        this.path = path;
        return this;
    }

    public NodeWrapper getParent()
    {
        return parent;
    }

    public NodeWrapper<Type> setParent(NodeWrapper parent)
    {
        this.parent = parent;
        return this;
    }

    public Type getObject()
    {
        return object;
    }

    public NodeWrapper<Type> setObject(Type object)
    {
        this.object = object;
        return this;
    }
}
