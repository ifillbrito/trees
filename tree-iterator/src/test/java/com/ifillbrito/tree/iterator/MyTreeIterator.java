package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.example.impl.singletype.Node;

/**
 * Created by gjib on 18.01.18.
 */
public class MyTreeIterator extends AbstractTreeIterator<Node>
{
    private MyTreeIterator(Node root)
    {
        super(root);
    }

    public static MyTreeIterator of(Node root)
    {
        return new MyTreeIterator(root);
    }
}
