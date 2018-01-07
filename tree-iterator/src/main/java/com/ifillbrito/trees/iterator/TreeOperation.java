package com.ifillbrito.trees.iterator;

import com.ifillbrito.common.function.OneArgSupplier;
import com.ifillbrito.common.operation.Operation;
import com.ifillbrito.common.operation.OperationArguments;
import com.ifillbrito.common.operation.OperationType;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Created by gjib on 07.01.18.
 */
public class TreeOperation<Argument, Iterator extends TreeIterator> implements Operation<Argument, Iterator>
{
    private AbstractTreeIterator treeIterator;
    private LinkedList<OperationArguments> operationArguments;

    public TreeOperation(Iterator treeIterator, LinkedList<OperationArguments> operationArguments)
    {
        this.treeIterator = (AbstractTreeIterator) treeIterator;
        this.operationArguments = operationArguments;
    }

    @Override
    public Iterator modify(Consumer<Argument> consumer)
    {
        operationArguments.getLast().setConsumer(consumer);
        operationArguments.getLast().setOperationType(OperationType.MODIFY);
        return (Iterator) treeIterator;
    }

    @Override
    public Iterator replace(OneArgSupplier<Argument, ?> supplier)
    {
        operationArguments.getLast().setSupplier(supplier);
        operationArguments.getLast().setOperationType(OperationType.REPLACE);
        return (Iterator) treeIterator;
    }

    @Override
    public Iterator remove()
    {
        operationArguments.getLast().setOperationType(OperationType.REMOVE);
        return (Iterator) treeIterator;
    }

    @Override
    public Iterator skipAll()
    {
        operationArguments.getLast().setOperationType(OperationType.SKIP_TREE);
        return (Iterator) treeIterator;
    }

    @Override
    public Iterator skipOne()
    {
        operationArguments.getLast().setOperationType(OperationType.SKIP_ITEM);
        return (Iterator) treeIterator;
    }

    @Override
    public <ListOrSet extends Collection<Argument>> ListOrSet collect(Supplier<ListOrSet> collectionSupplier)
    {
        operationArguments.getLast().setOperationType(OperationType.COLLECT_AS_LIST);
        treeIterator.setCollection(collectionSupplier.get());
        treeIterator.execute();
        ListOrSet result = collectionSupplier.get();
        result.addAll(treeIterator.getCollection());
        treeIterator.resetDataHolders();
        return result;
    }

    @Override
    public <Key, Value extends Argument> Map<Key, Value> collect(
            OneArgSupplier<Argument, Key> keySupplier,
            Supplier<Map<Key, Value>> mapSupplier)
    {
        operationArguments.getLast().setOperationType(OperationType.COLLECT_AS_MAP);
        treeIterator.setMap(mapSupplier.get());
        treeIterator.setMapKeySupplier(keySupplier);
        treeIterator.execute();
        Map<Key, Value> result = mapSupplier.get();
        result.putAll(treeIterator.getMap());
        treeIterator.setMap(null);
        treeIterator.resetDataHolders();
        return result;
    }

    @Override
    public <Key, ListOrSet extends Collection<Argument>> Map<Key, ListOrSet> group(
            OneArgSupplier<Argument, Key> keySupplier,
            Supplier<ListOrSet> collectionSupplier,
            Supplier<Map<Key, ListOrSet>> mapSupplier)
    {
        operationArguments.getLast().setOperationType(OperationType.GROUP);
        treeIterator.setCollectionSupplier( collectionSupplier);
        treeIterator.setMap(mapSupplier.get());
        treeIterator.setMapKeySupplier(keySupplier);
        treeIterator.execute();
        Map<Key, ListOrSet> result = mapSupplier.get();
        result.putAll(treeIterator.getMap());
        treeIterator.resetDataHolders();
        return result;
    }
}
