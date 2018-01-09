package com.ifillbrito.common.operation;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by gjib on 05.01.18.
 */
public class OperationArguments<Type>
{
    private Predicate<Type> precondition;
    private Consumer<Type> consumer;
    private Function<Type, ?> function;
    private OperationType operationType;
    private Class<Type> classType;
    private String pathRegex;

    private OperationArguments(Class<Type> classType, Predicate<Type> precondition)
    {
        this.precondition = precondition;
        this.classType = classType;
    }

    private OperationArguments(Class<Type> classType, String pathRegex)
    {
        this.pathRegex = pathRegex;
        this.classType = classType;
    }

    private OperationArguments(Class<Type> classType, Predicate<Type> precondition, String pathRegex)
    {
        this.precondition = precondition;
        this.pathRegex = pathRegex;
        this.classType = classType;
    }

    public static <Type> OperationArguments create(Class<Type> classType, Predicate<Type> precondition)
    {
        return new OperationArguments<>(classType, precondition);
    }

    public static <Type> OperationArguments create(Class<Type> classType, String pathRegex)
    {
        return new OperationArguments<>(classType, pathRegex);
    }

    public static <Type> OperationArguments create(Class<Type> classType, Predicate<Type> precondition, String pathRegex)
    {
        return new OperationArguments<>(classType, precondition, pathRegex);
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

    public OperationType getOperationType()
    {
        return operationType;
    }

    public void setOperationType(OperationType operationType)
    {
        this.operationType = operationType;
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
