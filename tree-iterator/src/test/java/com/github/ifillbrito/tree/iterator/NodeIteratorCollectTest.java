package com.github.ifillbrito.tree.iterator;

import com.github.ifillbrito.tree.iterator.domain.Node;
import com.github.ifillbrito.tree.iterator.impl.NodeIterator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeIteratorCollectTest extends AbstractNodeIteratorTest
{
    @Test
    public void collectAsList()
    {
        // -- given
        Node root = createTree();
        List<Node> evenNodes = new ArrayList<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .collect(evenNodes)
                    .forAll(Node::isEven)
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, evenNodes.size());
        assertEquals((Integer) 10, evenNodes.get(0).getValue());
        assertEquals((Integer) 12, evenNodes.get(1).getValue());
        assertEquals((Integer) 20, evenNodes.get(2).getValue());
        assertEquals((Integer) 22, evenNodes.get(3).getValue());
    }

    @Test
    public void collectAsList_transformer()
    {
        // -- given
        Node root = createTree();
        List<Integer> evenNodes = new ArrayList<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .collect(evenNodes, Node::getValue)
                    .forAll(Node::isEven)
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, evenNodes.size());
        assertEquals((Integer) 10, evenNodes.get(0));
        assertEquals((Integer) 12, evenNodes.get(1));
        assertEquals((Integer) 20, evenNodes.get(2));
        assertEquals((Integer) 22, evenNodes.get(3));
    }

    @Test
    public void collectAsMap()
    {
        // -- given
        Node root = createTree();
        Map<String, Node> nodesByName = new HashMap<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .collect(nodesByName, Node::getName)
                    .forAll(Node::isEven)
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, nodesByName.size());
        assertEquals((Integer) 10, nodesByName.get("1. child (a)").getValue());
        assertEquals((Integer) 12, nodesByName.get("1.2 child (a2)").getValue());
        assertEquals((Integer) 20, nodesByName.get("2. child (b)").getValue());
        assertEquals((Integer) 22, nodesByName.get("2.2 child (b2)").getValue());
    }

    @Test
    public void collectAsMap_transformer()
    {
        // -- given
        Node root = createTree();
        Map<String, Integer> nodesByName = new HashMap<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .collect(nodesByName, Node::getName, Node::getValue)
                    .forAll(Node::isEven)
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, nodesByName.size());
        assertEquals((Integer) 10, nodesByName.get("1. child (a)"));
        assertEquals((Integer) 12, nodesByName.get("1.2 child (a2)"));
        assertEquals((Integer) 20, nodesByName.get("2. child (b)"));
        assertEquals((Integer) 22, nodesByName.get("2.2 child (b2)"));
    }

    @Test
    public void group()
    {
        // -- given
        Node root = createTree();
        Map<String, List<Node>> groupedNodes = new HashMap<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .group(groupedNodes, node -> node.isEven() ? "even" : "odd", ArrayList::new)
                    .forAll()
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, groupedNodes.get("even").size());
        assertEquals(4, groupedNodes.get("odd").size());
    }

    @Test
    public void group_transformer()
    {
        // -- given
        Node root = createTree();
        Map<String, List<Integer>> groupedNodes = new HashMap<>();

        // -- when
        //@formatter:off
        new NodeIterator(root)
                .group(groupedNodes, node -> node.isEven() ? "even" : "odd", Node::getValue, ArrayList::new)
                    .forAll()
                    .filter()
                    .end()
                .execute();
        //@formatter:on

        // -- then
        assertEquals(4, groupedNodes.get("even").size());
        assertEquals(4, groupedNodes.get("odd").size());

        assertEquals((Integer) 10, groupedNodes.get("even").get(0));
        assertEquals((Integer) 12, groupedNodes.get("even").get(1));
        assertEquals((Integer) 20, groupedNodes.get("even").get(2));
        assertEquals((Integer) 22, groupedNodes.get("even").get(3));

        assertEquals((Integer) 1, groupedNodes.get("odd").get(0));
        assertEquals((Integer) 11, groupedNodes.get("odd").get(1));
        assertEquals((Integer) 13, groupedNodes.get("odd").get(2));
        assertEquals((Integer) 21, groupedNodes.get("odd").get(3));
    }
}
