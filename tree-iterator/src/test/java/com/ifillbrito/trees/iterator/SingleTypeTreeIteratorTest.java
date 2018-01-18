package com.ifillbrito.trees.iterator;

import com.ifillbrito.trees.example.impl.singletype.Node;
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
                // modify (or just do something with the nodes), replace them, or remove them
                .edit()
                    .forall(node -> node.isRed() && node.isValueEven())
                    .apply(node -> node.setValue(n -> n * 2))
                    .forall(node -> node.isYellow() && node.getValue() > 15)
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
                    .forall(Node::isLeaf)
                    .add()
                .end()
                // node color map by name
                .collect(colorMap, Node::getName, Node::getColor)
                    .forall(Node::isLeaf)
                    .add()
                .end()
                // group nodes by color
                .group(nodesByColorMap, Node::getColor, HashSet::new)
                    .forall()
                    .add()
                .end()
                // group nodes by color
                .group(nodeNamesByColorMap, Node::getColor, Node::getName, HashSet::new)
                    .forall((node, path) -> true) // some condition
                    .add()
                .end()
                .execute();
        //@formatter:on
    }
}