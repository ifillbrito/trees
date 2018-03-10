package com.github.ifillbrito.tree.operation;

import com.github.ifillbrito.common.function.TriPredicate;

import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public class OperationArguments<Node>
{
    private String scope;
    private OperationType operationType;
    private OperationPreconditionType preconditionType;
    private Predicate<Node> nodePredicate;
    private Consumer<Node> consumer;
    private Function<Node, ?> function;
    private Operation operation;
    private Class<Node> classType;
    private String pathRegex;
    private Predicate<String> pathPredicate;
    private BiPredicate<Node, String> nodeAndPathPredicate;
    private TriPredicate<Node, Node, String> parentAndNodeAndPathPredicate;

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

    public Predicate<Node> getNodePredicate()
    {
        return nodePredicate;
    }

    public void setNodePredicate(Predicate<Node> nodePredicate)
    {
        this.nodePredicate = nodePredicate;
    }

    public Consumer<Node> getConsumer()
    {
        return consumer;
    }

    public void setConsumer(Consumer<Node> consumer)
    {
        this.consumer = consumer;
    }

    public Function<Node, ?> getFunction()
    {
        return function;
    }

    public void setFunction(Function<Node, ?> function)
    {
        this.function = function;
    }

    public Operation getOperation()
    {
        return operation;
    }

    public void setOperation(Operation operation)
    {
        this.operation = operation;
    }

    public void setClassType(Class<Node> classType)
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

    public BiPredicate<Node, String> getNodeAndPathPredicate()
    {
        return nodeAndPathPredicate;
    }

    public void setNodeAndPathPredicate(BiPredicate<Node, String> nodeAndPathPredicate)
    {
        this.nodeAndPathPredicate = nodeAndPathPredicate;
    }

    public TriPredicate<Node, Node, String> getParentAndNodeAndPathPredicate()
    {
        return parentAndNodeAndPathPredicate;
    }

    public void setParentAndNodeAndPathPredicate(TriPredicate<Node, Node, String> parentAndNodeAndPathPredicate)
    {
        this.parentAndNodeAndPathPredicate = parentAndNodeAndPathPredicate;
    }

    public boolean testPrecondition(Node parent, Node object, String path)
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

    private boolean testNodePredicate(Node object)
    {
        return isTargetClass(object) && this.getNodePredicate().test(object);
    }

    private boolean testPathRegex(Node object, String path)
    {
        return isTargetClass(object) && path.matches(pathRegex);
    }

    private boolean testPathPredicate(Node object, String path)
    {
        return isTargetClass(object) && pathPredicate.test(path);
    }

    private boolean testNodeAndPathPredicate(Node object, String path)
    {
        return isTargetClass(object) && nodeAndPathPredicate.test(object, path);
    }

    private boolean testParentAndNodeAndPathPredicate(Node parent, Node object, String path)
    {
        return isTargetClass(object) && parentAndNodeAndPathPredicate.test(parent, object, path);
    }

    private boolean isTargetClass(Node object)
    {
        return this.getClassType().isAssignableFrom(object.getClass());
    }

    private Class<Node> getClassType()
    {
        return classType;
    }
}
