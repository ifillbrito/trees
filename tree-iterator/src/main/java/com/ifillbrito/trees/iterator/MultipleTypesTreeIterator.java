package com.ifillbrito.trees.iterator;

import com.ifillbrito.common.operation.Operation;

import java.util.function.Predicate;

/**
 * Created by gjib on 07.01.18.
 */
public interface MultipleTypesTreeIterator<Root> extends TreeIterator<Root>
{
    <Type> Operation<Type, MultipleTypesTreeIterator<Type>> forall(Class<Type> classType);

    <Type> Operation<Type, MultipleTypesTreeIterator<Type>> forall(Class<Type> classType, Predicate<Type> precondition);

    <Type> Operation<Type, MultipleTypesTreeIterator<Type>> forall(Class<Type> classType, String pathRegex);

    <Type> Operation<Type, MultipleTypesTreeIterator<Type>> forall(Class<Type> classType, Predicate<Type> precondition, String pathRegex);
}
