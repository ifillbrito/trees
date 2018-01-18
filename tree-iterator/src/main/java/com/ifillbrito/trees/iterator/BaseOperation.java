package com.ifillbrito.trees.iterator;

/**
 * Created by gjib on 17.01.18.
 */
public interface BaseOperation<R extends Condition>
{
    R skip();

    R ignore();
}
