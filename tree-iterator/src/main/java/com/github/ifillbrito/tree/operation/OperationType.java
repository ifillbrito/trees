package com.github.ifillbrito.tree.operation;

/**
 * Created by gjib on 10.03.18.
 */
public enum OperationType
{
    EDIT("edit"),
    ITERATE("iterate"),
    COLLECT_AS_LIST("collectAsList"),
    COLLECT_AS_MAP("collectAsMap"),
    GROUP("group");

    private String scopePrefix;

    OperationType(String scopePrefix)
    {
        this.scopePrefix = scopePrefix;
    }

    public String getScopePrefix()
    {
        return scopePrefix;
    }
}
