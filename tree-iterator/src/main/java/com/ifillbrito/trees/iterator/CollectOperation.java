package com.ifillbrito.trees.iterator;

/**
 * Created by gjib on 17.01.18.
 */
public interface CollectOperation<Arg, R extends Condition> extends BaseOperation<R>
{
    R add();
}
