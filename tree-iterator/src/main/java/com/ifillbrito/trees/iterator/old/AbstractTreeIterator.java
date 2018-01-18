package com.ifillbrito.trees.iterator.old;

import com.ifillbrito.common.operation.OperationArguments;
import com.ifillbrito.common.operation.OperationType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by gjib on 05.01.18.
 */
public abstract class AbstractTreeIterator<Root, RecursionStepObject, Iterator extends TreeIterator<Root>> implements TreeIterator<Root>
{
    private static final String EMPTY_PATH = "";
    protected Root root;
    protected LinkedList<OperationArguments> operationArguments = new LinkedList<>();

    public AbstractTreeIterator(Root root)
    {
        this.root = root;
    }

    // data holders
    private Collection collection;
    private Map map;
    private Function mapKeyFunction;
    private Supplier collectionSupplier;
    private String currentPath = "";

    public Collection getCollection()
    {
        return collection;
    }

    public AbstractTreeIterator setCollection(Collection collection)
    {
        this.collection = collection;
        return this;
    }

    public Map getMap()
    {
        return map;
    }

    public AbstractTreeIterator setMap(Map map)
    {
        this.map = map;
        return this;
    }

    public AbstractTreeIterator setMapKeyFunction(Function mapKeyFunction)
    {
        this.mapKeyFunction = mapKeyFunction;
        return this;
    }

    public AbstractTreeIterator setCollectionSupplier(Supplier collectionSupplier)
    {
        this.collectionSupplier = collectionSupplier;
        return this;
    }

    public void resetDataHolders()
    {
        this.map = null;
        this.mapKeyFunction = null;
        this.collectionSupplier = null;
    }

    @Override
    public Iterator execute()
    {
        ListIterator listIterator = getRootIterator(root);
        executeRecursive(listIterator);
        operationArguments.clear();
        return (Iterator) this;
    }

    protected abstract ListIterator getRootIterator(Root root);

    protected abstract void executeRecursionStep(RecursionStepObject object);

    protected String getUpdatedPath(RecursionStepObject object, String currentPath)
    {
        return EMPTY_PATH;
    }

    protected void executeRecursive(ListIterator listIterator)
    {
        while ( listIterator.hasNext() )
        {
            Object object = listIterator.next();
            if ( object == null ) continue;
            String tmpPath = currentPath;
            currentPath = getUpdatedPath((RecursionStepObject) object, currentPath);

            boolean skipTree = false;
            for ( OperationArguments operationArguments : this.operationArguments )
            {
                if ( isTreeSkipped(object, operationArguments) )
                {
                    skipTree = true;
                    break;
                }

                if ( operationArguments.testPrecondition(object, currentPath) &&
                        !isItemSkipped(object, operationArguments) )
                {
                    switch ( operationArguments.getOperationType() )
                    {
                        case MODIFY:
                            operationArguments.getConsumer().accept(object);
                            break;
                        case REPLACE:
                            listIterator.set(operationArguments.getFunction().apply(object));
                            break;
                        case REMOVE:
                            listIterator.remove();
                            break;
                        case COLLECT_AS_LIST:
                            collection.add(object);
                            break;
                        case COLLECT_AS_MAP:
                            map.put(mapKeyFunction.apply(object), object);
                            break;
                        case GROUP:
                            map.putIfAbsent(mapKeyFunction.apply(object), collectionSupplier.get());
                            Collection collection = (Collection) map.get(mapKeyFunction.apply(object));
                            collection.add(object);
                            break;
                    }
                }
            }

            if ( !skipTree )
            {
                executeRecursionStep((RecursionStepObject) object);
                currentPath = tmpPath;
            }
        }
    }

    private boolean isTreeSkipped(Object object, OperationArguments currentOperationArguments)
    {
        return isRegisteredBeforeCurrentOperation(OperationType.SKIP, object, currentOperationArguments);
    }

    private boolean isItemSkipped(Object object, OperationArguments currentOperationArguments)
    {
        return isRegisteredBeforeCurrentOperation(OperationType.IGNORE, object, currentOperationArguments);
    }

    private boolean isRegisteredBeforeCurrentOperation(OperationType operationType, Object object, OperationArguments currentOperationArguments)
    {
        for ( int i = operationArguments.indexOf(currentOperationArguments); i >= 0; i-- )
        {
            OperationArguments operationArguments = this.operationArguments.get(i);
            if ( operationArguments.getOperationType().equals(operationType) &&
                    operationArguments.testPrecondition(object, currentPath) )
            {
                return true;
            }
        }
        return false;
    }
}
