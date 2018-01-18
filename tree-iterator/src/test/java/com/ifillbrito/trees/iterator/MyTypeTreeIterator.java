package com.ifillbrito.trees.iterator;

import com.ifillbrito.trees.example.impl.singletype.Node;

/**
 * Created by gjib on 18.01.18.
 */
public class MyTypeTreeIterator extends AbstractTreeIterator<Node>
{
    private MyTypeTreeIterator(Node root)
    {
        super(root);
    }

    public static MyTypeTreeIterator of(Node root)
    {
        return new MyTypeTreeIterator(root);
    }
}
