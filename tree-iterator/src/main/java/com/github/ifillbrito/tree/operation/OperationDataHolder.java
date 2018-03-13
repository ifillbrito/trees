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

    public OperationPreconditionType getPreconditionType()
    {
        return preconditionType;
    }

    public void setPreconditionType(OperationPreconditionType preconditionType)
    {
        this.preconditionType = preconditionType;
    }

    public Predicate getNodePredicate()
    {
        return nodePredicate;
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

    public String getPathRegex()
    {
        return pathRegex;
    }

    public void setPathRegex(String pathRegex)
    {
        this.pathRegex = pathRegex;
    }

    public Predicate<String> getPathPredicate()
    {
        return pathPredicate;
    }

    public void setPathPredicate(Predicate<String> pathPredicate)
    {
        this.pathPredicate = pathPredicate;
    }

    public BiPredicate getNodeAndPathPredicate()
    {
        return nodeAndPathPredicate;
    }

    public void setNodeAndPathPredicate(BiPredicate nodeAndPathPredicate)
    {
        this.nodeAndPathPredicate = nodeAndPathPredicate;
    }

    public TriPredicate getParentAndNodeAndPathPredicate()
    {
        return parentAndNodeAndPathPredicate;
    }

    public void setParentAndNodeAndPathPredicate(TriPredicate parentAndNodeAndPathPredicate)
    {
        this.parentAndNodeAndPathPredicate = parentAndNodeAndPathPredicate;
    }

    public void enableParentResolution()
    {
        this.parentResolutionEnabledForPrecondition = true;
        this.classType = NodeWrapper.class;
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

    public boolean testPrecondition(NodeWrapper wrapper)
    {
        return testPrecondition(wrapper.getParent(), wrapper, wrapper.getCurrentPath());
    }

    public boolean testPrecondition(Object parent, Object object, String path)
    {
        switch ( preconditionType )
        {
            case FOR_ALL:
                return testNodePredicate(object);
            case FOR_ALL_BI_PREDICATE:
                return testNodeAndPathPredicate(object, path);
            case FOR_ALL_TRI_PREDICATE:
                return testParentAndNodeAndPathPredicate(parent, object, path);
            case FOR_ALL_PATH_REGEX:
                return testPathRegex(object, path);
            default: //case FOR_ALL_PATH_PREDICATE:
                return testPathPredicate(object, path);
        }
    }

    private boolean testNodePredicate(Object object)
    {
        return isTargetClass(object) && this.getNodePredicate().test(object);
    }

    private boolean testPathRegex(Object object, String path)
    {
        return isTargetClass(object) && path.matches(pathRegex);
    }

    private boolean testPathPredicate(Object object, String path)
    {
        return isTargetClass(object) && pathPredicate.test(path);
    }

    private boolean testNodeAndPathPredicate(Object object, String path)
    {
        return isTargetClass(object) && nodeAndPathPredicate.test(object, path);
    }

    private boolean testParentAndNodeAndPathPredicate(Object parent, Object object, String path)
    {
        return isTargetClass(object) && isTargetClass(parent)
                && parentAndNodeAndPathPredicate.test(parent, object, path);
    }

    private boolean isTargetClass(Object object)
    {
        if (object == null) return false;
        return this.getClassType().isAssignableFrom(object.getClass());
    }

    private Class getClassType()
    {
        return classType;
    }

    public <Target> Target getByScope(Function<OperationDataHolder, Target> getter, Target defaultValue, Map<String, Target> targetPropertyMap )
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
}
