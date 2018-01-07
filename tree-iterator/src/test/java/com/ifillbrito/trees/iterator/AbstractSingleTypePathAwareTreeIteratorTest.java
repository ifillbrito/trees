package com.ifillbrito.trees.iterator;

import com.ifillbrito.trees.example.impl.singletype.ExampleSingleTypePathAwareTreeIterator;
import com.ifillbrito.trees.example.impl.singletype.Node;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

/**
 * Created by gjib on 07.01.18.
 */
public class AbstractSingleTypePathAwareTreeIteratorTest
{
    @Test
    public void collect_returnListOfNodes()
    {
        // given
        Node inputRoot = createTree();

        // when
        List<Node> list = ExampleSingleTypePathAwareTreeIterator.of(inputRoot)
                .forall()
                .collect(ArrayList::new);

        // then
        assertEquals(10, list.size());
    }

    @Test
    public void collect_returnMapOfNodesByName()
    {
        // given
        Node inputRoot = createTree();

        // when
        Map<String, Node> map = ExampleSingleTypePathAwareTreeIterator.of(inputRoot)
                .forall()
                .collect(Node::getName, HashMap::new);

        // then
        assertEquals(10, map.size());
    }

    @Test
    public void group_returnMapOfNodesByColor()
    {
        // given
        Node inputRoot = createTree();

        // when
        Map<Node.Color, List<Node>> map = ExampleSingleTypePathAwareTreeIterator.of(inputRoot)
                .forall()
                .group(Node::getColor, ArrayList::new, HashMap::new);

        // then
        assertEquals(2, map.get(Node.Color.YELLOW).size());
        assertEquals(5, map.get(Node.Color.RED).size());
        assertEquals(3, map.get(Node.Color.GREEN).size());

        assertEquals(Optional.of(27), getTotal(map, Node.Color.YELLOW));
        assertEquals(Optional.of(208), getTotal(map, Node.Color.RED));
        assertEquals(Optional.of(24), getTotal(map, Node.Color.GREEN));
    }

    @Test
    public void multipleOperations()
    {
        // given
        Node inputRoot = createTree();

        // when
        ExampleSingleTypePathAwareTreeIterator.of(inputRoot)
                .forall(node -> node.isRed() && node.isValueEven())
                .modify(node -> node.setValue(n -> n * 2))
                .forall(node -> node.isYellow() && node.getValue() > 15)
                .modify(node -> node.setColor(Node.Color.GREEN))
                .forall("/a/b/.*")
                .modify(node -> node.setColor(Node.Color.YELLOW))
                .execute();

        // then
        Map<Node.Color, List<Node>> map = ExampleSingleTypePathAwareTreeIterator.of(inputRoot)
                .forall()
                .group(Node::getColor, ArrayList::new, HashMap::new);

        assertEquals(3, map.get(Node.Color.YELLOW).size());
        assertEquals(4, map.get(Node.Color.RED).size());
        assertEquals(3, map.get(Node.Color.GREEN).size());

        assertEquals(Optional.of(100), getTotal(map, Node.Color.YELLOW));
        assertEquals(Optional.of(231), getTotal(map, Node.Color.RED));
        assertEquals(Optional.of(36), getTotal(map, Node.Color.GREEN));
    }


    private Node createTree()
    {
        return Node.create("a", 10, Node.Color.YELLOW)
                .addChild(Node.create("b", 02, Node.Color.RED)
                        .addChild(Node.create("e", 85, Node.Color.RED))
                        .addChild(Node.create("f", 05, Node.Color.GREEN))
                )
                .addChild(
                        Node.create("c", 88, Node.Color.RED)
                                .addChild(Node.create("g", 15, Node.Color.RED))
                                .addChild(Node.create("h", 07, Node.Color.GREEN))
                                .addChild(Node.create("i", 17, Node.Color.YELLOW))
                )
                .addChild(
                        Node.create("d", 12, Node.Color.GREEN)
                                .addChild(Node.create("j", 18, Node.Color.RED))
                );
    }

    private Optional<Integer> getTotal(Map<Node.Color, List<Node>> map, Node.Color color)
    {
        return map.get(color)
                .stream()
                .map(Node::getValue)
                .reduce((x, y) -> x + y);
    }
}