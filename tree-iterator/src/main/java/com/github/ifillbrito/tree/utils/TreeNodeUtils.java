package com.github.ifillbrito.tree.utils;

/**
 * Created by gjib on 10.03.18.
 */
public class TreeNodeUtils
{
    public static <T> void verifyNotNull(T object)
    {
        if ( object == null ) throw new RuntimeException("The object cannot be null");
    }
}
