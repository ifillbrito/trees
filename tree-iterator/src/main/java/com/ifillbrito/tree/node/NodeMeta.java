package com.ifillbrito.tree.node;

/**
 * Created by gjib on 20.01.18.
 */
public class NodeMeta<Type>
{
    private String path;
    private NodeMeta parent;
    private Type node;

    public NodeMeta(Type node)
    {
        this.node = node;
    }

    public static <T> NodeMeta<T> of (T object)
    {
        return new NodeMeta<>(object);
    }

    public String getPath()
    {
        return path;
    }

    public NodeMeta<Type> setPath(String path)
    {
        this.path = path;
        return this;
    }

    public NodeMeta getParent()
    {
        return parent;
    }

    public NodeMeta<Type> setParent(NodeMeta parent)
    {
        this.parent = parent;
        return this;
    }

    public Type getNode()
    {
        return node;
    }

    public NodeMeta<Type> setNode(Type node)
    {
        this.node = node;
        return this;
    }
}
