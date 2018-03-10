package com.github.ifillbrito.tree.node;

/**
 * Created by gjib on 20.01.18.
 */
public class NodeWrapper<Type>
{
    private NodeWrapper<Type> parent;
    private Type node;

    /**
     * Only available if the method {@link com.github.ifillbrito.tree.iterator.AbstractTreeIterator#createPath(Object, String)}
     * has been overridden.
     */
    private String parentPath, currentPath;

    public NodeWrapper(Type node)
    {
        this.node = node;
    }

    public NodeWrapper(NodeWrapper<Type> other)
    {
        this.setParentPath(other.getParentPath());
        this.setParent(other.getParent());
        this.setNode(other.getNode());
    }

    public String getParentPath()
    {
        return parentPath;
    }

    public NodeWrapper<Type> setParentPath(String parentPath)
    {
        this.parentPath = parentPath;
        return this;
    }

    public String getCurrentPath()
    {
        return currentPath;
    }

    public NodeWrapper<Type> setCurrentPath(String currentPath)
    {
        this.currentPath = currentPath;
        return this;
    }

    public NodeWrapper<Type> getParent()
    {
        return parent;
    }

    public NodeWrapper<Type> setParent(NodeWrapper parent)
    {
        this.parent = parent;
        return this;
    }

    public Type getNode()
    {
        return node;
    }

    public NodeWrapper<Type> setNode(Type node)
    {
        this.node = node;
        return this;
    }
}
