package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIteratorTest
{
    @Test(expected = RuntimeException.class)
    public void nullNode()
    {
        new NodeIterator(null);
    }
}
