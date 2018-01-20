package com.ifillbrito.tree.iterator;

import com.ifillbrito.tree.example.impl.singletype.Node;
import org.junit.Test;

import java.util.*;

/**
 * Created by gjib on 18.01.18.
 */
public class SingleTypeTreeIteratorTest
{
    @Test
    public void test()
    {
        //@formatter:off
        Node inputRoot = Node.create("a", 10, Node.Color.YELLOW);
        List<Node> collection = new ArrayList<>();
        Map<String, Node> leafsMap = new HashMap<>();
        Map<String, Node.Color> colorMap = new HashMap<>();
        Map<Node.Color, Set<Node>> nodesByColorMap = new HashMap<>();
        Map<Node.Color, Set<String>> nodeNamesByColorMap = new HashMap<>();

        MyTypeTreeIterator.of(inputRoot)
                // declare globally the nodes that must be considered
                .traverse()
                    .forAll(Node::isLeaf)
                    .filter(4)
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
                // collect in list
                .collect(collection)
                    .forPath("/a/b")
                    .skip()
                    .end()
                // node map by name
                .collect(leafsMap, Node::getName)
                    .forAll(Node::isLeaf)
                    .take(3)
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
                    .forAll((node, path) -> true) // some condition
                    .takeOccurrence(4)
                    .filter()
                    .end()
                // work with a wrapper that contains parent and path
                .resolveMetaData()
                .edit()
                    .forAll(node -> node.getParent() == null || node.getPath() == null)
                    .apply(node -> node.getParent().getParent().getParent().getObject())
                    .end()
                .edit()
                    .resolveMetaData()
                    .forAll(node -> node.getParent() == null)
                    .remove()
                    .end()
                .execute();
        //@formatter:on
    }
}