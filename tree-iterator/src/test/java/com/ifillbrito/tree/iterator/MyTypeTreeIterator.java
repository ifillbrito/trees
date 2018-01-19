package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.example.impl.singletype.Node;

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
