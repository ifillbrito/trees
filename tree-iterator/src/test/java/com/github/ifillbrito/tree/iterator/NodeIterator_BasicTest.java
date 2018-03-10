package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import com.github.ifillbrito.tree.operation.ExecutionMode;
import org.junit.Test;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIterator_BasicTest extends AbstractNodeIteratorTest
{
    @Test
    public void modify()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 4, 40, 44, 48, 52, 80, 84, 88);
    }

    @Test
    public void topDownExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                /*
                    Operation: select the nodes which value is higher than 20 and
                    multiply the parent by a factor of 2.
                    Result of top down execution: only the direct parent is affected.
                 */
                .setExecution(ExecutionMode.TOP_DOWN)
                .edit()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12, 13, 80, 21, 22);
    }

    @Test
    public void bottomUpExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                /*
                    Operation: select the nodes which value is higher than 20 and
                    multiply the parent by a factor of 2.
                    Result of buttom up execution: all ascendants are affected.
                 */
                .setExecution(ExecutionMode.BOTTOM_UP)
                .edit()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 2, 10, 11, 12, 13, 80, 21, 22);
    }

    @Test(expected = RuntimeException.class)
    public void nullNode()
    {
        new NodeIterator(null);
    }
}
