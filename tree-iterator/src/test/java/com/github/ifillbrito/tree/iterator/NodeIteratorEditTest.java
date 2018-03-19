package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.builder.NodeBuilder;
import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
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
        assertValues(root, 1 * 4, 10 * 4, 11 * 4, 12 * 4, 13 * 4, 20 * 4, 21 * 4, 22 * 4);
    }

    @Test
    public void modify_multipleOperations()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(Node::isEven)
                    .apply(node -> node.setValue( x -> x * 4))
                    .forAll(node -> !node.isEven())
                    .apply(node -> node.setValue( x -> x + 1 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 + 1, 10 * 4, 11 + 1, 12 * 4, 13 + 1, 20 * 4, 21 + 1, 22 * 4);
    }

    @Test
    public void filter()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(Node::isEven)
                    .filter()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10 * 4, 11, 12 * 4, 13, 20 * 4, 21, 22 * 4);
    }

    @Test
    public void filter_resolvingParents()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .resolveParents()
                    .forAll(wrapper -> wrapper.getNode().isEven())
                    .filter()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10 * 4, 11, 12 * 4, 13, 20 * 4, 21, 22 * 4);
    }

    @Test
    public void ignore()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(node -> node.getValue().equals(10))
                    .ignore()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11 * 4, 12 * 4, 13 * 4, 20 * 4, 21 * 4, 22 * 4);
    }

    @Test
    public void ignore_resolvingParents()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .resolveParents()
                    .forAll(wrapper -> wrapper.getNode().getValue().equals(10))
                    .ignore()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11 * 4, 12 * 4, 13 * 4, 20 * 4, 21 * 4, 22 * 4);
    }

    @Test
    public void skip()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll(node -> node.getValue().equals(10))
                    .skip()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11, 12, 13, 20 * 4, 21 * 4, 22 * 4);
    }

    @Test
    public void skip_resolvingParents()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .resolveParents()
                    .forAll(wrapper -> wrapper.getNode().getValue().equals(10))
                    .skip()
                    .forAll()
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10, 11, 12, 13, 20 * 4, 21 * 4, 22 * 4);
    }

    @Test
    public void take_maxCount()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll()
                    .take(4)
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 4, 10 * 4, 11 * 4, 12 * 4, 13, 20, 21, 22);
    }

    @Test
    public void take_occurrenceFromTo()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll()
                    .take(4, 6)
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12 * 4, 13 * 4, 20 * 4, 21, 22);
    }

    @Test
    public void takeOccurrence()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll()
                    .takeOccurrence(4)
                    .apply(node -> node.setValue( x -> x * 4 ))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12 * 4, 13, 20, 21, 22);
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
        assertEquals((Integer) 1, root.getValue());
        assertEquals((Integer) 20, root.getChildren().get(0).getValue());
        assertEquals((Integer) 21, root.getChildren().get(0).getChildren().get(0).getValue());
        assertEquals((Integer) 22, root.getChildren().get(0).getChildren().get(1).getValue());
    }

    @Test
    public void topDownExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    /*
                        Operation: select the nodes which value is higher than 20 and
                        multiply the parent by a factor of 2.
                        Result of top down execution: only the direct parent is affected.
                     */
                    .topDownExecution()
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
    public void bottomUpExecution()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    /*
                        Operation: select the nodes which value is higher than 20 and
                        multiply the parent by a factor of 2.
                        Result of bottom up execution: all ascendants are affected.
                     */
                    .bottomUpExecution()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 2, 10, 11, 12, 13, 20 * 2 * 2, 21, 22);
    }

    @Test(expected = RuntimeException.class)
    public void nullNode()
    {
        new NodeIterator(null);
    }
}
