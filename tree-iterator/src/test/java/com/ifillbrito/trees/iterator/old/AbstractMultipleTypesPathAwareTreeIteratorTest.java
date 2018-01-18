package com.ifillbrito.trees.iterator.old;

import com.ifillbrito.trees.example.impl.multipletype.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by gjib on 05.01.18.
 */
public class AbstractMultipleTypesPathAwareTreeIteratorTest
{
    @Test
    public void collect_returnListOfChildren()
    {
        // given
        Root root = createTree();

        // when
        List<Child> children = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class)
                .collect(ArrayList::new);

        // then
        assertEquals(7, children.size());
    }

    @Test
    public void collect_returnMapOfChildrenById()
    {
        // given
        Root root = createTree();

        // when
        Map<String, Child> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class)
                .collect(Child::getName, HashMap::new);

        // then
        List<String> childrenIds = Arrays.asList("name", "lastName", "home", "mobile", "work", "city", "email");

        assertEquals(7, map.size());
        map.forEach((key, value) -> {
            assertTrue(childrenIds.contains(key));
            assertNotNull(value);
        });
    }

    @Test
    public void group_returnMapOfListByClass()
    {
        // given
        Root root = createTree();

        // when
        Map<Class<? extends Element>, List<Object>> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Object.class)
                .group(this::getClass, ArrayList::new, HashMap::new);

        // then
        assertEquals(3, map.size());
        assertEquals(1, map.get(Root.class).size());
        assertEquals(7, map.get(Child.class).size());
        assertEquals(2, map.get(Container.class).size());
    }

    private Class<? extends Element> getClass(Object object)
    {
        if ( object instanceof Root ) return Root.class;
        if ( object instanceof Child ) return Child.class;
        return Container.class;
    }

    @Test
    public void modify_childrenToMandatoryFalse()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, Child::isMandatory)
                .modify(child -> child.setMandatory(false))
                .execute();

        // then
        ArrayList<Child> children = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class)
                .collect(ArrayList::new);

        children.forEach(child -> assertFalse(child.isMandatory()));
    }

    @Test
    public void replace_childWithContainer()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, child -> child.getName().equals("name"))
                .replace(child ->
                        new Container()
                                .addChild(child)
                                .setName("newContainer")
                )
                .execute();

        // then
        Map<Class<? extends Element>, List<Object>> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Object.class)
                .group(this::getClass, ArrayList::new, HashMap::new);

        // check number of roots children and containers
        assertEquals(3, map.size());
        assertEquals(1, map.get(Root.class).size());
        assertEquals(7, map.get(Child.class).size());
        assertEquals(3, map.get(Container.class).size());

        // check that the container exists and that the name is in it
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Container.class, container -> "newContainer".equals(container.getName()))
                .collect(ArrayList::new)
                .stream()
                .map(container -> (Child) container.getChildren().get(0))
                .forEach(child -> Assert.assertEquals("name", child.getName()));
    }

    @Test
    public void remove_children()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, Child::isMandatory)
                .remove()
                .execute();

        // then
        Map<Class<? extends Element>, List<Object>> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Object.class)
                .group(this::getClass, ArrayList::new, HashMap::new);

        // check number of roots children and containers
        assertEquals(3, map.size());
        assertEquals(1, map.get(Root.class).size());
        assertEquals(5, map.get(Child.class).size());
        assertEquals(2, map.get(Container.class).size());

        List<Object> children = map.get(Child.class);
        children.stream()
                .map(child -> (Child) child)
                .forEach(child -> assertFalse(child.isMandatory()));
    }

    @Test
    public void ignore_children()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, child -> child.getName().matches("(name|lastName)"))
                .ignore()
                .when(Child.class)
                .remove()
                .execute();

        // then
        Map<Class<? extends Element>, List<Object>> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Object.class)
                .group(this::getClass, ArrayList::new, HashMap::new);

        assertEquals(2, map.get(Child.class).size());
    }

    @Test
    public void skip_container()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Container.class, child -> child.getName().equals("contactInformation"))
                .skip()
                .when(Child.class)
                .remove()
                .execute();

        // then
        Map<Class<? extends Element>, List<Object>> map = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Object.class)
                .group(this::getClass, ArrayList::new, HashMap::new);

        assertEquals(5, map.get(Child.class).size());
    }

    @Test
    public void modify_childrenByPath()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, "/person/contactInformation/phone.*")
                .modify(child -> child.setMandatory(true))
                .execute();

        // then
        List<Child> children = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, "/person/contactInformation/phone.*")
                .collect(ArrayList::new);

        assertEquals(3, children.size());
        children.forEach(child -> assertTrue(child.isMandatory()));
    }

    @Test
    public void modify_childrenByPreconditionAndPath()
    {
        // given
        Root root = createTree();

        // when
        ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class,
                        child -> "111-1111".equals(child.getValue()),
                        "/person/contactInformation/phone.*")
                .modify(child -> child.setMandatory(true))
                .execute();

        // then
        List<Child> children = ExampleMultipleTypesPathAwareTreeIterator.of(root)
                .when(Child.class, "/person/contactInformation/phone.*")
                .collect(ArrayList::new);

        assertEquals(3, children.size());
        children.forEach(child -> {
            if ( child.getValue().equals("111-1111") )
            {
                assertTrue(child.isMandatory());
            }
            else
            {
                assertFalse(child.isMandatory());
            }
        });
    }

    private Root createTree()
    {
        /*
            <root name="person">
                <child name="name" mandatory="true" value="Bob"/>
                <child name="lastName" mandatory="true" value="Smith"/>
                <container name="contactInformation">
                    <container name="phone-numbers">
                        <child name="home" value="111-1111"/>
                        <child name="mobile" value="222-2222"/>
                        <child name="work" value="333-3333"/>
                    </container>
                    <child name="city" value="Frankfurt"/>
                    <child name="email" value="email@email.com"/>
                </container>
            </root>
         */
        return (Root) new Root()
                .addChild(
                        new Child()
                                .setValue("Bob")
                                .setMandatory(true)
                                .setName("name")
                )
                .addChild(
                        new Child()
                                .setValue("Smith")
                                .setMandatory(true)
                                .setName("lastName")
                )
                .addChild(
                        new Container()
                                .addChild(
                                        new Container()
                                                .addChild(
                                                        new Child()
                                                                .setValue("111-1111")
                                                                .setName("home")
                                                )
                                                .addChild(
                                                        new Child()
                                                                .setValue("222-2222")
                                                                .setName("mobile")
                                                )
                                                .addChild(
                                                        new Child()
                                                                .setValue("333-3333")
                                                                .setName("work")
                                                )
                                                .setName("phone-numbers")
                                )
                                .addChild(
                                        new Child()
                                                .setValue("Frankfurt")
                                                .setName("city")
                                )
                                .addChild(
                                        new Child()
                                                .setValue("email@email.com")
                                                .setName("email")
                                )
                                .setName("contactInformation")
                )
                .setName("person");
    }
}