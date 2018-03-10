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
    private Node createTree()
    {
        //@formatter:off
        return new NodeBuilder(new Node())
                .withName("root")
                .withValue(1)
                .addChild("1. child (a)", 10)
                    .addChild( "1.1 child (a1)", 11).toParent()
                    .addChild( "1.2 child (a2)", 12).toParent()
                    .addChild( "1.3 child (a3)", 13).toParent()
                    .toParent()
                .addChild("2. child (b)", 20)
                    .addChild( "2.1 child (b1)", 21).toParent()
                    .addChild( "2.2 child (b2)", 22).toParent()
                    .toParent()
                .build();
        //@formatter:on
    }

    @Test
    public void modify_all()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 2, 20, 22, 24, 26, 40, 42, 44);
    }

    @Test
    public void modify_byPredicate()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(Node::isEven)
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 20, 11, 24, 13, 40, 21, 44);
    }

    @Test
    public void modify_byPath()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forPath("/root/1\\. child \\(a\\)/.*")
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 22, 24, 26, 20, 21, 22);
    }

    @Test(expected = RuntimeException.class)
    public void nullNode()
    {
        new NodeIterator(null);
    }

    private void assertValues(Node root, Integer r, Integer a, Integer a1, Integer a2, Integer a3, Integer b, Integer b1, Integer b2)
    {
        assertEquals(r, root.getValue());
        assertEquals(a, root.getChildren().get(0).getValue());
        assertEquals(a1, root.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals(a2, root.getChildren().get(0).getChildren().get(1).getValue());
        assertEquals(a3, root.getChildren().get(0).getChildren().get(2).getValue());
        assertEquals(b, root.getChildren().get(1).getValue());
        assertEquals(b1, root.getChildren().get(1).getChildren().get(0).getValue());
        assertEquals(b2, root.getChildren().get(1).getChildren().get(1).getValue());
    }
}
