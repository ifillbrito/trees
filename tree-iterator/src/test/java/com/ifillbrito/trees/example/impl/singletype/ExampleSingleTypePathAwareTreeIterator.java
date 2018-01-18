package com.ifillbrito.trees.example.impl.singletype;

import com.ifillbrito.trees.iterator.old.AbstractSingleTypePathAwareTreeIterator;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * Created by gjib on 07.01.18.
 */
public class ExampleSingleTypePathAwareTreeIterator extends AbstractSingleTypePathAwareTreeIterator<Node>
{

    public static final String SEPARATOR = "/";

    private ExampleSingleTypePathAwareTreeIterator(Node node)
    {
        super(node);
    }

    public static ExampleSingleTypePathAwareTreeIterator of(Node node)
    {
        return new ExampleSingleTypePathAwareTreeIterator(node);
    }

    @Override
    protected ListIterator getRootIterator(Node node)
    {
        return Arrays.asList(node).listIterator();
    }

    @Override
    protected void executeRecursionStep(Node object)
    {
        if ( object.isLeaf() )
        {
            // --- end of recursion
            return;
        }
        else
        {
            executeRecursive(object.getChildren().listIterator());
        }
    }

    @Override
    protected String getUpdatedPath(Node object, String currentPath)
    {
        return currentPath + SEPARATOR + object.getName();
    }
}