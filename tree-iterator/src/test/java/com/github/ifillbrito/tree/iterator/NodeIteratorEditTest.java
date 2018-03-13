package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.builder.NodeBuilder;
import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import com.github.ifillbrito.tree.operation.ExecutionMode;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIteratorEditTest extends AbstractNodeIteratorTest
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
    public void replace()
    {
        // -- given
        Node root = createTree();
        Node newNode = new NodeBuilder()
                .withName("new node")
                .withValue(25)
                .build();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(node -> node.getValue().equals(22))
                    .replace(node -> newNode)
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12, 13, 20, 21, 25);
        assertEquals("new node", root.getChildren().get(1).getChildren().get(1).getName());
    }

    @Test
    public void remove()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(node -> node.getValue().equals(10))
                    .remove()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals((Integer)1, root.getValue());
        assertEquals((Integer)20, root.getChildren().get(0).getValue());
        assertEquals((Integer)21, root.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals((Integer)22, root.getChildren().get(0).getChildren().get(1).getValue());
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
