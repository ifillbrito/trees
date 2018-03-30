package com.github.ifillbrito.tree.operation;

import com.github.ifillbrito.common.function.TriPredicate;
import com.github.ifillbrito.tree.node.NodeWrapper;

import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public class OperationDataHolder
{
    // scope data
    private String scope;
    private ExecutionMode executionMode;

    // operation data
    private OperationType operationType;
    private Operation operation;

    // modify operation data
    private Consumer consumer;

    // replace operation data
    private Function replaceFunction;

    // take operation data
    private Integer takeCounter;
    private Integer takeOccurrenceFrom;
    private Integer takeOccurrenceTo;

    // precondition data
    private Class classType;
    private boolean parentResolutionEnabledForPrecondition = false;
    private boolean parentResolutionEnabledForOperation = false;
    private OperationPreconditionType preconditionType;
    private String pathRegex;
    private Predicate<String> pathPredicate;
    private Predicate nodePredicate;
    private BiPredicate nodeAndPathPredicate;
    private TriPredicate parentAndNodeAndPathPredicate;
    private boolean negatePredicates = false;

    public OperationDataHolder(OperationDataHolder other)
    {
        this.scope = other.scope;
        this.executionMode = other.executionMode;
        this.operationType = other.operationType;
        this.operation = other.operation;
        this.consumer = other.consumer;
        this.replaceFunction = other.replaceFunction;
        this.takeCounter = other.takeCounter;
        this.takeOccurrenceFrom = other.takeOccurrenceFrom;
        this.takeOccurrenceTo = other.takeOccurrenceTo;
        this.classType = other.classType;
        this.parentResolutionEnabledForPrecondition = other.parentResolutionEnabledForPrecondition;
        this.parentResolutionEnabledForOperation = other.parentResolutionEnabledForOperation;
        this.preconditionType = other.preconditionType;
        this.pathRegex = other.pathRegex;
        this.pathPredicate = other.pathPredicate;
        this.nodePredicate = other.nodePredicate;
        this.nodeAndPathPredicate = other.nodeAndPathPredicate;
        this.parentAndNodeAndPathPredicate = other.parentAndNodeAndPathPredicate;
        this.negatePredicates = other.negatePredicates;
    }

    public OperationDataHolder(OperationType operationType, int scopeCounter, Class classType)
    {
        String scope = operationType.getScopePrefix() + scopeCounter;
        this.setOperationType(operationType);
        this.setScope(scope);
        this.setClassType(classType);
    }

    public <Target> Target getByScopeFromObjectOrMapOtherwiseDefault(Function<OperationDataHolder, Target> getter, Map<String, Target> targetPropertyMap, Target defaultValue)
    {
        String scope = this.getScope();
        Target targetProperty = getter.apply(this);
        if ( targetProperty == null )
        {
            Target targetPropertyInScope = targetPropertyMap.get(scope);
            if ( targetPropertyInScope == null )
            {
                targetProperty = defaultValue;
            }
            else
            {
                targetProperty = targetPropertyInScope;
            }
        }
        else
        {
            targetPropertyMap.put(scope, targetProperty);
        }
        return targetProperty;
    }

    public String getScope()
    {
        return scope;
    }

    public void setScope(String scope)
    {
        this.scope = scope;
    }

    public OperationType getOperationType()
    {
        return operationType;
    }

    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
    }

    public void setPreconditionType(OperationPreconditionType preconditionType)
    {
        this.preconditionType = preconditionType;
    }

    public void setNodePredicate(Predicate nodePredicate)
    {
        this.nodePredicate = nodePredicate;
    }

    public Consumer getConsumer()
    {
        return consumer;
    }

    public void setConsumer(Consumer consumer)
    {
        this.consumer = consumer;
    }

    public Function getReplaceFunction()
    {
        return replaceFunction;
    }

    public void setReplaceFunction(Function replaceFunction)
    {
        this.replaceFunction = replaceFunction;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }

    public void setClassType(Class classType)
    {
        this.classType = classType;
    }

    public void setPathRegex(String pathRegex)
    {
        this.pathRegex = pathRegex;
        this.pathPredicate = path -> path.matches(pathRegex);
    }

    public void setPathPredicate(Predicate<String> pathPredicate)
    {
        this.pathPredicate = pathPredicate;
    }

    public void setNodeAndPathPredicate(BiPredicate nodeAndPathPredicate)
    {
        this.nodeAndPathPredicate = nodeAndPathPredicate;
    }

    public void setParentAndNodeAndPathPredicate(TriPredicate parentAndNodeAndPathPredicate)
    {
        this.parentAndNodeAndPathPredicate = parentAndNodeAndPathPredicate;
    }

    public void setNegatePredicates(boolean negatePredicates)
    {
        this.negatePredicates = negatePredicates;
    }

    public void enableParentResolution()
    {
        this.parentResolutionEnabledForPrecondition = true;
        this.classType = NodeWrapper.class;
    }

    public void initializeTakeCounter()
    {
        this.takeCounter = 0;
        this.takeOccurrenceFrom = 0;
        this.takeOccurrenceTo = 0;
    }

    public void increaseTakeCounter()
    {
        if ( takeCounter != null )
        {
            this.takeCounter++;
        }
    }

    public boolean isParentResolutionEnabledForPrecondition()
    {
        return parentResolutionEnabledForPrecondition;
    }

    public void enableParentResolutionForOperation()
    {
        this.parentResolutionEnabledForOperation = true;
    }

    public boolean isParentResolutionEnabledForOperation()
    {
        return parentResolutionEnabledForOperation;
    }

    public ExecutionMode getExecutionMode()
    {
        return executionMode;
    }

    public void setExecutionMode(ExecutionMode executionMode)
    {
        this.executionMode = executionMode;
    }

    public void setTakeOccurrences(Integer takeOccurrenceFrom, Integer takeOccurrenceTo)
    {
        this.takeOccurrenceFrom = takeOccurrenceFrom;
        this.takeOccurrenceTo = takeOccurrenceTo;
    }

    public Predicate<String> getPathPredicate()
    {
        if ( negatePredicates ) return pathPredicate.negate();
        return pathPredicate;
    }

    public Predicate getNodePredicate()
    {
        if ( negatePredicates ) return nodePredicate.negate();
        return nodePredicate;
    }

    public BiPredicate getNodeAndPathPredicate()
    {
        if ( negatePredicates ) return nodeAndPathPredicate.negate();
        return nodeAndPathPredicate;
    }

    public TriPredicate getParentAndNodeAndPathPredicate()
    {
        if ( negatePredicates ) return parentAndNodeAndPathPredicate.negate();
        return parentAndNodeAndPathPredicate;
    }

    public boolean testPrecondition(Object parent, Object object, String path)
    {
        if ( !isTargetClass(object) || !isTargetObject() ) return false;

        switch ( preconditionType )
        {
            case FOR_ALL:
                return getNodePredicate().test(object);
            case FOR_ALL_BI_PREDICATE:
                return getNodeAndPathPredicate().test(object, path);
            case FOR_ALL_TRI_PREDICATE:
                return isTargetClass(parent) && getParentAndNodeAndPathPredicate().test(parent, object, path);
            case FOR_ALL_PATH_REGEX:
            case FOR_ALL_PATH_PREDICATE:
                return getPathPredicate().test(path);
            default:
                return false;
        }
    }

    private boolean isTargetClass(Object object)
    {
        return object != null && this.getClassType().isAssignableFrom(object.getClass());
    }

    private boolean isTargetObject()
    {
        if ( takeCounter != null )
        {
            return takeOccurrenceFrom <= takeCounter && takeCounter <= takeOccurrenceTo;
        }
        // no counter defined -> take all
        return true;
    }

    private Class getClassType()
    {
        return classType;
    }
}
