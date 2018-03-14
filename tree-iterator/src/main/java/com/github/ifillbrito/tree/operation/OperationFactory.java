package com.github.ifillbrito.tree.operation;

import com.github.ifillbrito.tree.operation.impl.BaseOperationImpl;
import com.github.ifillbrito.tree.operation.impl.CollectOperationImpl;
import com.github.ifillbrito.tree.operation.impl.EditOperationImpl;
import com.github.ifillbrito.tree.operation.impl.IterateOperationImpl;

/**
 * Created by gjib on 10.03.18.
 */
public class OperationFactory
{
    public static BaseOperationImpl createOperation(OperationDataHolder arguments, OperationPrecondition precondition)
    {
        switch ( arguments.getOperationType() )
        {
            case EDIT:
                return new EditOperationImpl(arguments, precondition);
            case ITERATE:
                return new IterateOperationImpl(arguments, precondition);
            case COLLECT_AS_LIST:
            case COLLECT_AS_MAP:
            case GROUP:
                return new CollectOperationImpl(arguments, precondition);
            default:
                return null;
        }
    }
}
