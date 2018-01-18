package com.ifillbrito.trees.iterator.old;

/**
 * Created by gjib on 07.01.18.
 */
public abstract class AbstractSingleTypePathAwareTreeIterator<Node> extends AbstractSingleTypeTreeIterator<Node>
{
    public AbstractSingleTypePathAwareTreeIterator(Node node)
    {
        super(node);
    }

    @Override
    protected abstract String getUpdatedPath(Node object, String currentPath);
}
