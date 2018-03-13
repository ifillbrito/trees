package com.github.ifillbrito.tree.iterator.collections;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;

/**
 * Created by gjib on 13.03.18.
 */
public class CollectionTests
{
    @Test
    public void replaceFromList()
    {
        // given
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

        // when
        ListIterator<Integer> iterator = numbers.listIterator();
        while ( iterator.hasNext() )
        {
            Integer number = iterator.next();
            if ( number.equals(3) ) iterator.set(6);
        }

        // then
        assertEquals(5, numbers.size());
        assertFalse(numbers.contains(3));
        assertTrue(numbers.contains(6));
        assertEquals((Integer) 6, numbers.get(2));
    }

    @Test
    public void replaceFromSet()
    {
        // given
        Set<Integer> numbers = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
        ArrayList<Integer> tmpNumbers = new ArrayList<>(numbers);

        // when
        ListIterator<Integer> iterator = tmpNumbers.listIterator();
        while ( iterator.hasNext() )
        {
            Integer number = iterator.next();
            if ( number.equals(3) ) iterator.set(6);
        }

        numbers.clear();
        numbers.addAll(tmpNumbers);

        // then
        assertEquals(5, numbers.size());
        assertFalse(numbers.contains(3));
        assertTrue(numbers.contains(6));
    }
}
