package com.github.ifillbrito.tree.iterator.builder;

import com.github.ifillbrito.builder.BaseBuilder;
import com.github.ifillbrito.tree.iterator.domain.Node;

/**
 * Created by gjib on 09.03.18.
 */
public class NodeBuilder extends BaseBuilder<Node, NodeBuilder>
{
    public NodeBuilder(Node object)
    {
        super(object);
    }

    public NodeBuilder()
    {
        super(new Node());
    }

    public NodeBuilder withName(String name)
    {
        return this.set(Node::setName, name);
    }

    public NodeBuilder withValue(Integer value)
    {
        return this.set(Node::setValue, value);
    }

    public NodeBuilder addChild(String childName, Integer value)
    {
        return this.addWithBuilder(Node::getChildren, new NodeBuilder(new Node()))
                .set(Node::setName, childName)
                .set(Node::setValue, value);
    }


}
