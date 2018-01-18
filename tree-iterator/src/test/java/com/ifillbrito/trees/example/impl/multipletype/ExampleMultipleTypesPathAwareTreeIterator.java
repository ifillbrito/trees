package com.ifillbrito.trees.example.impl.multipletype;

import com.ifillbrito.trees.iterator.old.AbstractMultipleTypesPathAwareTreeIterator;

import java.util.Arrays;
import java.util.ListIterator;

/**
 * Created by gjib on 05.01.18.
 */
public class ExampleMultipleTypesPathAwareTreeIterator extends AbstractMultipleTypesPathAwareTreeIterator<Root>
{

    public static final String DELIMITER = "/";

    private ExampleMultipleTypesPathAwareTreeIterator(Root root)
    {
        super(root);
    }

    public static ExampleMultipleTypesPathAwareTreeIterator of (Root root)
    {
        return new ExampleMultipleTypesPathAwareTreeIterator(root);
    }

    @Override
    protected ListIterator getRootIterator(Root root)
    {
        return Arrays.asList(root).listIterator();
    }

    @Override
    protected void executeRecursionStep(Object object)
    {
        if ( object instanceof Child )
        {
            // recursive anchor
            return;
        }
        else if ( object instanceof Container )
        {
            executeRecursive(((Container) object).getChildren().listIterator());
        }
        else if ( object instanceof Root )
        {
            executeRecursive(((Root) object).getChildren().listIterator());
        }
    }

    @Override
    protected String getUpdatedPath(Object object, String currentPath)
    {
        if ( object instanceof Child )
        {
            return String.join(DELIMITER, currentPath, ((Child) object).getName());
        }
        else if ( object instanceof Container )
        {
            return String.join(DELIMITER, currentPath, ((Container) object).getName());
        }
        else if ( object instanceof Root )
        {
            return String.join(DELIMITER, currentPath, ((Root) object).getName());
        }
        return currentPath;
    }
}
