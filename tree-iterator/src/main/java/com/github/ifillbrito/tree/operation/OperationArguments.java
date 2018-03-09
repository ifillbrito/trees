package com.github.ifillbrito.tree.operation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public class OperationArguments<Type>
{
    private String scope;
    private OperationType operationType;
    private Predicate<Type> precondition;
    private Consumer<Type> consumer;
    private Function<Type, ?> function;
    private Operation operation;
    private Class<Type> classType;
    private String pathRegex;

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

    public Predicate<Type> getPrecondition()
    {
        return precondition;
    }

    public void setPrecondition(Predicate<Type> precondition)
    {
        this.precondition = precondition;
    }

    public Consumer<Type> getConsumer()
    {
        return consumer;
    }

    public void setConsumer(Consumer<Type> consumer)
    {
        this.consumer = consumer;
    }

    public Function<Type, ?> getFunction()
    {
        return function;
    }

    public void setFunction(Function<Type, ?> function)
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

    public void setClassType(Class<Type> classType)
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

    public boolean testPrecondition(Type object, String path)
    {
        if ( pathRegex != null && precondition != null )
        {
            return isValidPrecondition(object) && isValidPath(object, path);
        }
        else if ( pathRegex == null && precondition != null )
        {
            return isValidPrecondition(object);
        }
        else if ( pathRegex != null ) // precondition is null
        {
            return isValidPath(object, path);
        }
        else
        {
            return false;
        }
    }

    private boolean isValidPath(Type object, String path)
    {
        return isTargetClass(object) && path.matches(pathRegex);
    }

    private boolean isValidPrecondition(Type object)
    {
        return isTargetClass(object) && this.getPrecondition().test(object);
    }

    private boolean isTargetClass(Type object)
    {
        return this.getClassType().isAssignableFrom(object.getClass());
    }

    private Class<Type> getClassType()
    {
        return classType;
    }
}
