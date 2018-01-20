package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.example.impl.singletype.Node;
import org.junit.Test;

import java.util.*;

/**
 * Created by gjib on 18.01.18.
 */
public class TreeIteratorTest
{
    @Test
    public void api_example_only()
    {
        //@formatter:off
        Node inputRoot = createNode();
        List<Node> collection = new ArrayList<>();
        Map<String, Node> leafsMap = new HashMap<>();
        Map<String, Node.Color> colorMap = new HashMap<>();
        Map<Node.Color, Set<Node>> nodesByColorMap = new HashMap<>();
        Map<Node.Color, Set<String>> nodeNamesByColorMap = new HashMap<>();

        MyTreeIterator.of(inputRoot)
                // configure the iteration:
                // execution order; filtered, ignored and/or skipped nodes
                .iterate()
                    .topDownExecution()
                    .forAll(Node::isLeaf)
                    .filter(4)
                    .resolveParents()
                    .forAll(node -> node.getParent() == null)
                    .ignore()
                    .end()

                // modify (or just do something with the nodes), replace them, or remove them
                .edit()
                    .forAll(node -> node.isRed() && node.isValueEven())
                    .apply(node -> node.setValue(n -> n * 2))
                    .forAll(node -> node.isYellow() && node.getValue() > 15)
                    .replace(node -> Node.create("z", 20, Node.Color.RED))
                    .forPath("/a/b/.*")
                    .remove()
                    .end()

                // list of nodes
                .collect(collection)
                    .forPath("/a/b/[a-z]^")
                    .skip()
                    .end()

                // node map by name
                .collect(leafsMap, Node::getName)
                    .forAll(Node::isLeaf)
                    .take(3)
                    .bottomUpExecution()
                    .filter()
                    .end()

                // node color map by name
                .collect(colorMap, Node::getName, Node::getColor)
                    .forAll(Node::isLeaf)
                    .filter()
                    .end()

                // group nodes by color
                .group(nodesByColorMap, Node::getColor, HashSet::new)
                    .forAll()
                    .take(2,4)
                    .filter()
                    .end()

                // group nodes by color
                .group(nodeNamesByColorMap, Node::getColor, Node::getName, HashSet::new)
                    // node-path condition
                    .forAll((node, path) -> true)
                    .takeOccurrence(4)
                    .filter()
                    // node-parent-path condition
                    .forAll((node, parent, path) -> true)
                    .ignore()
                    .end()

                // Wrappers: Useful if a reference to the parent, grandparent, etc is needed.
                .edit()
                    // work with a node wrapper in the next statement
                    // the wrapper contains: object, parent, path
                    .resolveParents()
                    .forAll(node -> node.getParent() == null)
                    .resolveParents()
                    .apply(node -> node.getParent().setNode(node))
                    .end()

                // work with a node wrapper for all coming operations
                // the wrapper contains: object, parent, path
                .resolveParents()
                .edit()
                    .forAll(node -> node.getParent() == null || node.getPath() == null)
                    .apply(node -> node.getParent().getParent().getParent().getNode())
                    .end()

                // define the class to be used for the next operations
                // it can be used to disable wrapper, or to avoid
                // "xyz instance of SomeObject" conditions
                .use(Node.class)
                .edit()
                    .forAll(Node::isRed)
                    .remove()
                    .end()
                .execute();
        //@formatter:on
    }

    private Node createNode()
    {
        return Node.create("a", 10, Node.Color.YELLOW);
    }
}