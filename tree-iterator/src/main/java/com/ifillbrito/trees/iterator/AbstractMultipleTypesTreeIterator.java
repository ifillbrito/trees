package com.ifillbrito.trees.iterator;

import com.ifillbrito.common.operation.OperationArguments;

import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public abstract class AbstractMultipleTypesTreeIterator<Root>
        extends AbstractTreeIterator<Root, Object, MultipleTypesTreeIterator<Root>>
        implements MultipleTypesTreeIterator<Root>
{
    public AbstractMultipleTypesTreeIterator(Root root)
    {
        super(root);
    }

    @Override
    public <Type> TreeOperation<Type, MultipleTypesTreeIterator<Type>> when(Class<Type> classType)
    {
        return this.when(classType, x -> true);
    }

    @Override
    public <Type> TreeOperation<Type, MultipleTypesTreeIterator<Type>> when(Class<Type> classType, Predicate<Type> precondition)
    {
        operationArguments.add(OperationArguments.create(classType, precondition));
        return new TreeOperation<>((MultipleTypesTreeIterator<Type>) this, operationArguments);
    }

    @Override
    public <Type> TreeOperation<Type, MultipleTypesTreeIterator<Type>> when(Class<Type> classType, String pathRegex)
    {
        operationArguments.add(OperationArguments.create(classType, pathRegex));
        return new TreeOperation<>((MultipleTypesTreeIterator<Type>) this, operationArguments);
    }

    @Override
    public <Type> TreeOperation<Type, MultipleTypesTreeIterator<Type>> when(Class<Type> classType, Predicate<Type> precondition, String pathRegex)
    {
        operationArguments.add(OperationArguments.create(classType, precondition, pathRegex));
        return new TreeOperation<>((MultipleTypesTreeIterator<Type>) this, operationArguments);
    }
}
