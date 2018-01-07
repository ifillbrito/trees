package com.ifillbrito.trees.example.impl.multipletype;

/**
 * Created by gjib on 05.01.18.
 */
public class Child extends Element
{
    private String value;
    private boolean mandatory;

    public String getValue()
    {
        return value;
    }

    public Child setValue(String value)
    {
        this.value = value;
        return this;
    }

    public boolean isMandatory()
    {
        return mandatory;
    }

    public Child setMandatory(boolean mandatory)
    {
        this.mandatory = mandatory;
        return this;
    }

    @Override
    public String toString()
    {
        return "Child{" +
                "name='" + getName() + '\'' +
                "value='" + value + '\'' +
                ", mandatory=" + mandatory +
                '}';
    }
}
