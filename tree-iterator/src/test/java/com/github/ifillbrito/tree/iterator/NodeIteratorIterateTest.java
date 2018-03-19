package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIteratorIterateTest extends AbstractNodeIteratorTest
{
    @Test
    public void iterate_filter()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .iterate()
                    .forAll(Node::isEven)
                    .filter()
                    .end()
                .edit()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10 * 4, 11, 12 * 4, 13, 20 * 4, 21, 22 * 4);
    }

    @Test
    public void iterate_ignore()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .iterate()
                    .forAll(node -> node.getValue().equals(10))
                    .ignore()
                    .forAll(node -> node.getValue().equals(20))
                    .ignore()
                    .end()
                .edit()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11 * 4, 12 * 4, 13 * 4, 20, 21 * 4, 22 * 4);
    }

    @Test
    public void iterate_skip()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .iterate()
                    .forAll(node -> node.getValue().equals(10))
                    .skip()
                    .forAll(node -> node.getValue().equals(20))
                    .ignore()
                    .end()
                .edit()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11, 12, 13, 20, 21 * 4, 22 * 4);
    }

    @Test
    public void iterate_topDownExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .iterate()
                    .topDownExecution()
                    .end()
                .edit()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12, 13, 20 * 2 * 2, 21, 22);
    }

    @Test
    public void iterate_bottomUpExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .iterate()
                    .bottomUpExecution()
                    .end()
                .edit()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 2, 10, 11, 12, 13, 20 * 2 * 2, 21, 22);
    }
}
