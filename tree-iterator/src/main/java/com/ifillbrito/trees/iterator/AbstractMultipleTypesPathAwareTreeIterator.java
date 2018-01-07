package com.ifillbrito.trees.iterator;

/**
 * Created by gjib on 07.01.18.
 */
public abstract class AbstractMultipleTypesPathAwareTreeIterator<Root> extends AbstractMultipleTypesTreeIterator<Root>
{
    public AbstractMultipleTypesPathAwareTreeIterator(Root root)
    {
        super(root);
    }

    @Override
    protected abstract String getUpdatedPath(Object object, String currentPath);
}
