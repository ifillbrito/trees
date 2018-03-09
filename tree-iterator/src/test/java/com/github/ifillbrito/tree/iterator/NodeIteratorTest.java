package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.builder.NodeBuilder;
import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIteratorTest
{
    @Test
    public void modify()
    {
        //@formatter:off
        // -- given
        Node root = new NodeBuilder(new Node())
                .withName("root")
                .withValue(1)
                .addChild("1. child", 10)
                    .addChild( "1.1 child", 11).toParent()
                    .addChild( "1.2 child", 12).toParent()
                    .addChild( "1.3 child", 13).toParent()
                    .toParent()
                .addChild("2. child", 20)
                    .addChild( "2.1 child", 21).toParent()
                    .addChild( "2.2 child", 22).toParent()
                    .toParent()
                .build();

        // -- when
        new NodeIterator(root)
                .edit()
                    .forAll().apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals((Integer) 2, root.getValue());
        assertEquals((Integer) 20, root.getChildren().get(0).getValue());
        assertEquals((Integer) 22, root.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals((Integer) 24, root.getChildren().get(0).getChildren().get(1).getValue());
        assertEquals((Integer) 26, root.getChildren().get(0).getChildren().get(2).getValue());
        assertEquals((Integer) 40, root.getChildren().get(1).getValue());
        assertEquals((Integer) 42, root.getChildren().get(1).getChildren().get(0).getValue());
        assertEquals((Integer) 44, root.getChildren().get(1).getChildren().get(1).getValue());
    }

    @Test(expected = RuntimeException.class)
    public void nullNode()
    {
        new NodeIterator(null);
    }
}
