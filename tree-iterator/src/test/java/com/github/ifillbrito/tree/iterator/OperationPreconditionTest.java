package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

/**
 * Created by gjib on 09.03.18.
 */
public class OperationPreconditionTest extends AbstractNodeIteratorTest
{
    @Test
    public void forAll()
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
        assertValues(root, 1 * 2, 10 * 2, 11 * 2, 12 * 2, 13 * 2, 20 * 2, 21 * 2, 22 * 2);
    }

    @Test
    public void forAll_predicate()
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
        assertValues(root, 1, 10 * 2, 11, 12 * 2, 13, 20 * 2, 21, 22 * 2);
    }

    @Test
    public void forAll_pathRegex()
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
        assertValues(root, 1, 10, 11 * 2, 12 * 2, 13 * 2, 20, 21, 22);
    }

    @Test
    public void forAll_pathPredicate()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forPath(path -> path.matches("/root/1\\. child \\(a\\)/.*"))
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11 * 2, 12 * 2, 13 * 2, 20, 21, 22);
    }

    @Test
    public void forAll_nodeAndPathPredicate()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll((node, path) ->
                                path.matches("/root/1\\. child \\(a\\)/.*") &&
                                !node.isEven()
                    )
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11 * 2, 12, 13 * 2, 20, 21, 22);
    }

    @Test
    public void forAll_parentAndNodeAndPathPredicate()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .forAll((parent, node, path) ->
                                parent != null && parent.getValue().equals(10) &&
                                path.matches("/root/.*") &&
                                node.isEven()
                    )
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11, 12 * 2, 13, 20, 21, 22);
    }

    @Test
    public void resolveParents()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .resolveParents()
                    .forAll(wrapper -> wrapper.getParent() != null && wrapper.getParent().getNode().isEven())
                    .apply(node -> node.setValue( x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1, 10, 11 * 2, 12 * 2, 13 * 2, 20, 21 * 2, 22 * 2);
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
                    .topDownExecution()
                    /*
                        Operation: select the nodes which value is higher than 20 and
                        multiply the parent by a factor of 2.
                        Result of top down execution: only the direct parent is affected.
                     */
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
                    .bottomUpExecution()
                    /*
                        Operation: select the nodes which value is higher than 20 and
                        multiply the parent by a factor of 2.
                        Result of buttom up execution: all ascendants are affected.
                     */
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertValues(root, 1 * 2, 10, 11, 12, 13, 20 * 2 * 2, 21, 22);
    }

    @Test
    public void executionModeScope()
    {
        // -- given
        Node root = createTree();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .edit()
                    .bottomUpExecution()
                    .forAll(node -> node.getValue() > 20)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x * 2))
                    .end()
                .edit()
                    .topDownExecution()
                    .forAll(node -> node.getValue() < 20 && node.getValue() > 10)
                    .resolveParents()
                    .apply(wrapper -> wrapper.getParent().getNode().setValue(x -> x + 2))
                    .end()
                .execute();
        //@formatter:on

        // -- then
        // Remember that the top down operations are executed first than the bottom up ones.
        /*
        TD: top down; BU: bottom up

            1 ->            TD: do nothing                              BU: do nothing
                10 ->       TD: do nothing                              BU: do nothing
                    11 ->   TD: parent = parent+2 (parent.value = 12)   BU: do nothing
                    12 ->   TD: parent = parent+2 (parent.value = 14)   BU: do nothing
                    13 ->   TD: parent = parent+2 (parent.value = 16)   BU: do nothing
                20 ->       TD: do nothing                              BU: parent = parent*2 (parent.value = 2)
                    21 ->   TD: do nothing                              BU: parent = parent*2 (parent.value = 80)
                    22 ->   TD: do nothing                              BU: parent = parent*2 (parent.value = 40)
         */
        assertValues(root, 1 * 2, 10 + 2 + 2 + 2, 11, 12, 13, 20 * 2 * 2, 21, 22);
    }
}
