package com.github.ifillbrito.tree.iterator.impl;

import com.github.ifillbrito.tree.iterator.AbstractTreeIterator;
import com.github.ifillbrito.tree.iterator.domain.Node;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIterator extends AbstractTreeIterator<Node>
{

    public NodeIterator(Node node)
    {
        super(node);
    }

    @Override
    protected void executeRecursionStep(Node node)
    {
        if ( node.getChildren() != null && !node.getChildren().isEmpty() )
        {
            executeRecursive(node, node.getChildren().iterator());
        }
    }

    @Override
    protected String createPath(Node node, String path)
    {
        return path + PATH_SEPARATOR + node.getName();
    }
}
