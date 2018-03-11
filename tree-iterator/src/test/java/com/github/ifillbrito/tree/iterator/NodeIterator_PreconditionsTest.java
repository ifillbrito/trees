package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIterator_PreconditionsTest extends AbstractNodeIteratorTest
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
        assertValues(root, 2, 20, 22, 24, 26, 40, 42, 44);
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
        assertValues(root, 1, 20, 11, 24, 13, 40, 21, 44);
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
        assertValues(root, 1, 10, 22, 24, 26, 20, 21, 22);
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
        assertValues(root, 1, 10, 22, 24, 26, 20, 21, 22);
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
        assertValues(root, 1, 10, 22, 12, 26, 20, 21, 22);
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
        assertValues(root, 1, 10, 11, 24, 13, 20, 21, 22);
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
        assertValues(root, 1, 10, 22, 24, 26, 20, 42, 44);
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
        assertValues(root, 2, 10, 11, 12, 13, 80, 21, 22);
    }
}
