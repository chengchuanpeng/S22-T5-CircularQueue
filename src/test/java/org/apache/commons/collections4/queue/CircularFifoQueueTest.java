/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.commons.collections4.queue;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Stack;
import java.util.PriorityQueue;
import java.util.NoSuchElementException;
import java.util.Queue;

import org.junit.Ignore;
import org.junit.jupiter.api.Test;

/**
 * Test cases for CircularFifoQueue.
 *
 * @since 4.0
 */
public class CircularFifoQueueTest<E> extends AbstractQueueTest<E> {

    public CircularFifoQueueTest() {
        super(CircularFifoQueueTest.class.getSimpleName());
    }

    /**
     * Runs through the regular verifications, but also verifies that
     * the buffer contains the same elements in the same sequence as the
     * list.
     */
    @Override
    public void verify() {
        super.verify();
        final Iterator<E> iterator1 = getCollection().iterator();
        for (final E e : getConfirmed()) {
            assertTrue(iterator1.hasNext());
            final Object o1 = iterator1.next();
            final Object o2 = e;
            assertEquals(o1, o2);
        }
    }

    /**
     * Overridden because CircularFifoQueue doesn't allow null elements.
     * 
     * @return false
     */
    @Override
    public boolean isNullSupported() {
        return false;
    }

    /**
     * Overridden because CircularFifoQueue isn't fail fast.
     * 
     * @return false
     */
    @Override
    public boolean isFailFastSupported() {
        return false;
    }

    /**
     * Returns an empty ArrayList.
     *
     * @return an empty ArrayList
     */
    @Override
    public Collection<E> makeConfirmedCollection() {
        return new ArrayList<>();
    }

    /**
     * Returns a full ArrayList.
     *
     * @return a full ArrayList
     */
    @Override
    public Collection<E> makeConfirmedFullCollection() {
        final Collection<E> c = makeConfirmedCollection();
        c.addAll(java.util.Arrays.asList(getFullElements()));
        return c;
    }

    /**
     * Returns an empty CircularFifoQueue that won't overflow.
     *
     * @return an empty CircularFifoQueue
     */
    @Override
    public Queue<E> makeObject() {
        return new CircularFifoQueue<>(100);
    }

    /**
     ************************** SVT Test case ***************************
     */

    // --- Shang-Yi ---
    /**
     * Step B
     * Test relationship between queue size and current index.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testIndexIsLowerBoundWhenSizeIsValid() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        try {
            // act
            fifo.add((E) "1");
            fifo.add((E) "2");
            fifo.add((E) "3");
            fifo.get(0);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }
    @Test
    public void testIndexBelowLowerBoundWhenSizeThrowError() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        // act
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");

        // assert
        assertThrows(NoSuchElementException.class, () -> fifo.get(-1));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testValidIndexWhichIsNotBoundaryWhenSizeIsValid() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        try {
            // act
            fifo.add((E) "1");
            fifo.add((E) "2");
            fifo.add((E) "3");
            fifo.get(1);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIndexIsUpperBoundWhenSizeIsValid() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        try {
            // act
            fifo.add((E) "1");
            fifo.add((E) "2");
            fifo.add((E) "3");
            fifo.get(2);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testIndexAboveUpperBoundWhenSizeThrowError() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        // act
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");

        // assert
        assertThrows(NoSuchElementException.class, () -> fifo.get(3));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testAddOfferWhenIsAtFullCapacityIsFalseShouldAddWithoutReplacingOldElem() {
        // arrange
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);

        // act
        fifo.add((E) "1");
        fifo.add((E) "2");

        // assert
        assertTrue(fifo.contains("1"));
    }

    /**
     * Step N - Test Case Specs - isAtFullCapacity()
     */
    @Test
    public void testisAtFullCapacityShouldBeTrueWhenReachMaxSize() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);

        // assert
        assertTrue(fifo.isAtFullCapacity());
    }

    @Test
    public void testisAtFullCapacityShouldBeFalseWhenNotReachMaxSize() {
        // arrange
        int maxSize = 3;
        int expectedSize = 2;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);

        // assert
        assertFalse(fifo.isAtFullCapacity());
    }

    /**
     * Step P - Test Case Specs - clear()
     */
    @Test
    public void testQueueIsNotEmptyWhenThereAreElemsAndNotClear() {
        // arrange
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);

        // assert
        assertFalse(fifo.isEmpty());
    }

    /**
     * Step K - Test Case Specs - public int size()
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testSizeFnUpperBoundary() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        assertEquals(maxSize, fifo.size());
    }

    // --- 成全 ---
    // D1
    @Test
    public void testCircularFifoQueueCircular_WhenQueueSizeIsMaxPopTheOldestElement_AndAddTwoElement_ShouldNotContainEarliestElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(1);
        queue.add(1);
        queue.add(2);
        int queueValue = queue.peek();
        assertEquals(2, queueValue);
        // 1 is oldest element should be replaced
        assertFalse(queue.contains(1));
    }

    // D2
    @Test
    public void testCircularFifoQueueCircular_WhenQueueSizeIsMaxPopTheOldestElement_AndAddElevenElement_ShouldNotContainEarliestElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.add(i);
        int queueValue = queue.peek();
        // make sure queue element is oldest
        assertEquals(2, queueValue);
        // first element is not in queue
        assertFalse(queue.contains(1));
    }

    // D3
    @Test
    public void testCircularFifoQueueCircular_WhenQueueSizeIsMaxPopTheOldestElement_AndOfferOneElement_ShouldNotContainEarliestElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(1);
        queue.offer(1);
        queue.offer(2);
        int queueValue = queue.peek();
        // make sure queue element is oldest
        assertEquals(2, queueValue);
        // first element is not in queue
        assertFalse(queue.contains(1));
    }

    // D4
    @Test
    public void testCircularFifoQueueCircular_WhenQueueSizeIsMaxPopTheOldestElement_AndQueueSizeIsTenOfferlevenElement_ShouldNotContainEarliestElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.offer(i);
        int queueValue = queue.peek();
        // make sure queue element is oldest
        assertEquals(2, queueValue);
        // first element is not in queue
        assertFalse(queue.contains(1));
    }

    // G1
    @Test
    public void testConstructorDefaultSize() {
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>();
        assertEquals(32, queue.maxSize());
    }

    // Q1
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoHashSetSize() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(set);
        assertEquals(set.size(), queue.size());
    }

    // Q2
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoArrayListSize() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // Q3
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoTreeSetSize() {
        TreeSet<Integer> set = new TreeSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(set);
        assertEquals(set.size(), queue.size());
    }

    // Q4
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoLinkedListSize() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // Q5
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoVectorSize() {
        Vector<Integer> list = new Vector<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // Q6
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoPriorityQueueSize() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(pq);
        assertEquals(pq.size(), queue.size());
    }

    // Q7
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoStackSize() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(stack);
        assertEquals(stack.size(), queue.size());
    }

    // Q8
    @Test
    public void testInitWithNullShouldBeInValid() {
        assertThrows(NullPointerException.class, () -> new CircularFifoQueue<Integer>(null));
    }

    // ----- 千珊 -----
    @Test
    public void testIsFirstInFirstOut() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(5);

        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals(queue.poll(), "1");
    }

    @Test
    public void testAddSameElementTypeWhenSizeIsValid() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        try {
            queue.add(1);
            queue.add(2);
            queue.add(3);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    @Ignore
    @Test
    public void testAddVaryElementTypeWhenSizeIsValidShouldBuildFailed() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        // queue.add("2"); Build failed
    }

    /**
     * MC/DC test for {@link #get(int)}.
     */
    // | Test case | index < 0 | index >= sz|
    // |-----------|-----------|------------|
    // | 1 | T | F |
    // | 2 | F | F |
    // | 3 | F | T |

    @Test
    public void testGetElementWhenIndexLessThanZeroThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertThrows(NoSuchElementException.class, () -> queue.get(-1));
    }

    @Test
    public void testGetElementWhenIndexGreaterThanZeroAndLessThanSize() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("2", queue.get(1));
    }

    @Test
    public void testGetElementWhenIndexGreaterThanSizeThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertThrows(NoSuchElementException.class, () -> queue.get(3));
    }
    /**
     * test remove using CFG & base path strategy
     * 3 test cases
     */
    @Test
    public void testRemoveWhenSizeIsZeroThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    @Test
    public void testRemoveWhenSizeIsOne() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertEquals("1", queue.remove());
    }

    @Test
    public void testRemoveWhenStartEqualsMaxElements() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        queue.remove();
        queue.remove();
        assertEquals("3", queue.remove());
    }

    /**
     * Step: A
     */
    @Test
    public void testAddHashSetToCircularFifoQueue() {
        try {
            final CircularFifoQueue<HashSet<Integer>> queue = new CircularFifoQueue<HashSet<Integer>>();
            queue.add(new HashSet<Integer>());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddTreeSetToCircularFifoQueue() {
        try {
            final CircularFifoQueue<TreeSet<Integer>> queue = new CircularFifoQueue<TreeSet<Integer>>();
            queue.add(new TreeSet<Integer>());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddLinkedListToCircularFifoQueue() {
        try {
            final CircularFifoQueue<LinkedList<Integer>> queue = new CircularFifoQueue<LinkedList<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddPriorityQueueToCircularFifoQueue() {
        try {
            final CircularFifoQueue<PriorityQueue<Integer>> queue = new CircularFifoQueue<PriorityQueue<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddVectorToCircularFifoQueue() {
        try {
            final CircularFifoQueue<Vector<Integer>> queue = new CircularFifoQueue<Vector<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddStackToCircularFifoQueue() {
        try {
            final CircularFifoQueue<Stack<Integer>> queue = new CircularFifoQueue<Stack<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    /**
     * Step. F
     */
    @Test
    public void testGetWhenInputIndexIsLowerBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertEquals("1", queue.get(0));
    }

    @Test
    public void testGetWhenInputIndexIsInvalid() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertThrows(NoSuchElementException.class, () -> queue.get(-1));
    }

    @Test
    public void testGetWhenInputIndexIsValidAndNotBoundaryValue() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("2", queue.get(1));
    }

    @Test
    public void testGetWhenInputIndexIsUpperBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("3", queue.get(2));
    }

    @Test
    public void testGetWhenInputIsOverBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertThrows(NoSuchElementException.class, () -> queue.get(4));
    }

    // ----- John -----
    /**
     ************************** SVT Test case ***************************
     */
    // Step A - Test Case Specs
    @Test
    public void testInitWithSizeEqualsToThree() {
        int size = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(size);
        assertEquals(size, fifo.maxSize());
    }

    @Test
    public void testInitWithSizeEqualsToOne() {
        int size = 1;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(size);
        assertEquals(size, fifo.maxSize());
    }

    @Test
    public void testInitWithSizeEqualToZeroShouldThrowException() {
        int size = 0;
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<Integer>(size));
    }


    @Test
    public void testInitWithSizeExceedMaxIntShouldThrowException() {
        int offset = 10;
        int maxSize = Integer.MAX_VALUE + offset;
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<Integer>(maxSize));
    }


    @Test
    public void testInitWithSizeEqualsToMaxInt() {
        int maxSize = Integer.MAX_VALUE - 2;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
        assertEquals(maxSize, fifo.maxSize());
    }

    @Test
    public void testInitWithNullElementShouldThrowException() {
        assertThrows(NullPointerException.class, () -> new CircularFifoQueue<Integer>(null));
    }

    @Test
    public void testInitWithPrimitiveTypeShouldHaveSyntaxError() {
        // Initialize CircularFifoQueue with primitive type should have syntax error
        // final CircularFifoQueue<int> fifo = new CircularFifoQueue<int>(maxSize);
    }

    @Test
    public void testInitWithStringShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<String> fifo = new CircularFifoQueue<String>();
        String str = "test string";
        try {
            fifo.add(str);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithDoubleShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Double> fifo = new CircularFifoQueue<Double>();
        Double d = 0.05;
        try {
            fifo.add(d);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithByteShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Byte> fifo = new CircularFifoQueue<Byte>();
        Byte b = 2;
        try {
            fifo.add(b);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithShortShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Short> fifo = new CircularFifoQueue<Short>();
        Short sh = 2;
        try {
            fifo.add(sh);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithLongShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Long> fifo = new CircularFifoQueue<Long>();
        long lng = 5;
        try {
            fifo.add(Long.valueOf(lng));
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithBooleanShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Boolean> fifo = new CircularFifoQueue<Boolean>();
        Boolean flag = true;
        try {
            fifo.add(flag);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithCharShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Character> fifo = new CircularFifoQueue<Character>();
        Character ch = 'a';
        try {
            fifo.add(ch);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testInitWithFloatShouldBeValid() {
        int expectedSize = 1;
        final CircularFifoQueue<Float> fifo = new CircularFifoQueue<Float>();
        float f = 0.05f;
        try {
            fifo.add(f);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }


    @Test
    public void testAddNullShouldThrowException() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
        assertThrows(NullPointerException.class, () -> fifo.add(null));
    }

    @Test
    public void testAddIntergerToCircularFifoQueue() {
        int expectedSize = 1;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>();
        try {
            fifo.add(1);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testAddCollectionToCircularFifoQueue() {
        int expectedSize = 1;
        final CircularFifoQueue<ArrayList<Integer>> fifo = new CircularFifoQueue<ArrayList<Integer>>();
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            fifo.add(arr);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testOfferNullShouldThrowException() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
        assertThrows(NullPointerException.class, () -> fifo.offer(null));
    }

    @Test
    public void testOfferIntergerToCircularFifoQueue() {
        int maxSize = 3;
        int expectedSize = 1;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
        try {
            fifo.offer(1);
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    @Test
    public void testOfferCollectionToCircularFifoQueue() {
        int maxSize = 3;
        int expectedSize = 1;
        final CircularFifoQueue<ArrayList<Integer>> fifo = new CircularFifoQueue<ArrayList<Integer>>(maxSize);
        ArrayList<Integer> arr = new ArrayList<>();
        fifo.offer(arr);
        assertEquals(maxSize, fifo.maxSize());
        assertEquals(expectedSize, fifo.size());
    }

    /**
     ************************* Structural Test (whitebox) *************************
     */

    // Size()
    @Test
    public void testSizeOfEmptyCircularFifoQueueShouldBeZero() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        int expectedSize = 0;
        assertEquals(expectedSize, fifo.size());
    }

    @Test
    public void testSizeShouldBeThreeAfterAddThreeElement() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        int expectedSize = 3;
        assertEquals(expectedSize, fifo.size());
    }

    @Test
    public void testSizeShouldBeMaxSizeAfterAddMoreElement() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        fifo.add(4);
        fifo.add(5);
        int expectedSize = 3;
        assertEquals(expectedSize, fifo.size());
    }

    // isEmpty()
    @Test
    public void testShouldBeEmptyIfSizeIsZero() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        int expectedSize = 0;
        assertEquals(expectedSize, fifo.size());
        assertTrue(fifo.isEmpty());
    }

    @Test
    public void testNotShouldBeEmptyIfSizeIsNotZero() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        int expectedSize = 1;
        assertEquals(expectedSize, fifo.size());
        assertFalse(fifo.isEmpty());
    }

    // isFull() -> should fail, potential mutant
    @Test
    public void testShouldBeFullIfSizeReachMaxSize() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        assertEquals(maxSize, fifo.size());
        assertFalse(fifo.isFull());
    }

    // isFull()
    @Test
    public void testShouldNotBeFullIfSizeNotReachMaxSize() {
        int maxSize = 3;
        int expectedSize = 2;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        assertEquals(expectedSize, fifo.size());
        assertFalse(fifo.isFull());
    }

    // Clear()
    @Test
    public void testSizeShouldBeZeroAfterClear() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.clear();
        assertTrue(fifo.isEmpty());
    }

    // peek()
    @Test
    public void testPeekShouldReturnNullIfEmpty() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.peek();
        assertTrue(fifo.isEmpty());
        assertNull(fifo.peek());
    }

    // peek()
    @Test
    public void testPeekShouldReturnOldestElement() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.add(2);
        int expectedElement = 1;
        assertEquals(expectedElement, (E) fifo.peek());
    }

    // Test iterator hasNext() with F F
    @Test
    public void testInitialIteratorHasNextElementIfNotEmpty() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        Iterator<Integer> itr = fifo.iterator();
        itr.next();
        itr.next();
        itr.next();
        assertFalse(itr.hasNext());
    }

    // Test iterator hasNext() with T F
    @Test
    public void testHasNoNextElementIfEmpty() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertFalse(itr.hasNext());
    }

    // Test iterator hasNext() with F T
    @Test
    public void testNoNextementIfIteratorReachEnd() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        fifo.add(1);
        itr.next();
        assertFalse(itr.hasNext());
    }

    @Test
    public void testNoNextElementShouldThrowException() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertThrows(NoSuchElementException.class, () -> itr.next());
    }

    @Test
    public void testShouldReturnNextElement() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        Iterator<Integer> itr = fifo.iterator();
        try {
            itr.next();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // Iterator.remove()
    @Test
    public void testIteratorRemove1() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertThrows(IllegalStateException.class, () -> itr.remove());
    }

    @Test
    public void testIteratorRemove2() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        Iterator<Integer> itr = fifo.iterator();
        itr.next();
        try {
            itr.remove();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // if (start <= lastReturnedIndex && pos <= end)
    // T T
    @Test
    public void testIteratorRemove3() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        fifo.add(4);
        fifo.add(5);
        Iterator<Integer> itr = fifo.iterator();
        itr.next();
        itr.next();
        int expectedSize = 4;
        try {
            itr.remove();
            assertEquals(expectedSize, fifo.size());
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    /**
     ************************* Native Test case *************************
     */
    /**
     * Tests that the removal operation actually removes the first element.
     */
    @Test
    @SuppressWarnings("unchecked")
    public void testCircularFifoQueueCircular() {
        final List<E> list = new ArrayList<>();
        list.add((E) "A");
        list.add((E) "B");
        list.add((E) "C");
        final Queue<E> queue = new CircularFifoQueue<>(list);

        assertTrue(queue.contains("A"));
        assertTrue(queue.contains("B"));
        assertTrue(queue.contains("C"));

        queue.add((E) "D");

        assertFalse(queue.contains("A"));
        assertTrue(queue.contains("B"));
        assertTrue(queue.contains("C"));
        assertTrue(queue.contains("D"));

        assertEquals("B", queue.peek());
        assertEquals("B", queue.remove());
        assertEquals("C", queue.remove());
        assertEquals("D", queue.remove());
    }

    /**
     * Tests that the removal operation actually removes the first element.
     */
    @Test
    public void testCircularFifoQueueRemove() {
        resetFull();
        final int size = getConfirmed().size();
        for (int i = 0; i < size; i++) {
            final Object o1 = getCollection().remove();
            final Object o2 = ((List<?>) getConfirmed()).remove(0);
            assertEquals("Removed objects should be equal", o1, o2);
            verify();
        }

        assertThrows(NoSuchElementException.class, () -> getCollection().remove(),
                "Empty queue should raise Underflow.");
    }

    /**
     * Tests that the constructor correctly throws an exception.
     */
    @Test
    public void testConstructorException1() {
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<E>(0));
    }

    /**
     * Tests that the constructor correctly throws an exception.
     */
    @Test
    public void testConstructorException2() {
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<E>(-20));
    }

    /**
     * Tests that the constructor correctly throws an exception.
     */
    @Test
    public void testConstructorException3() {
        assertThrows(NullPointerException.class, () -> new CircularFifoQueue<E>(null));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError1() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5");

        assertEquals("[1, 2, 3, 4, 5]", fifo.toString());

        fifo.remove("3");
        assertEquals("[1, 2, 4, 5]", fifo.toString());

        fifo.remove("4");
        assertEquals("[1, 2, 5]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError2() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5");
        fifo.add((E) "6");

        assertEquals(5, fifo.size());
        assertEquals("[2, 3, 4, 5, 6]", fifo.toString());

        fifo.remove("3");
        assertEquals("[2, 4, 5, 6]", fifo.toString());

        fifo.remove("4");
        assertEquals("[2, 5, 6]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError3() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5");

        assertEquals("[1, 2, 3, 4, 5]", fifo.toString());

        fifo.remove("3");
        assertEquals("[1, 2, 4, 5]", fifo.toString());

        fifo.add((E) "6");
        fifo.add((E) "7");
        assertEquals("[2, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("4");
        assertEquals("[2, 5, 6, 7]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError4() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("4"); // remove element in middle of array, after start
        assertEquals("[3, 5, 6, 7]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError5() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("5"); // remove element at last pos in array
        assertEquals("[3, 4, 6, 7]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError6() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("6"); // remove element at position zero in array
        assertEquals("[3, 4, 5, 7]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError7() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("7"); // remove element at position one in array
        assertEquals("[3, 4, 5, 6]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError8() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2
        fifo.add((E) "8"); // end=3

        assertEquals("[4, 5, 6, 7, 8]", fifo.toString());

        fifo.remove("7"); // remove element at position one in array, need to shift 8
        assertEquals("[4, 5, 6, 8]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRemoveError9() throws Exception {
        // based on bug 33071
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(5);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5"); // end=0
        fifo.add((E) "6"); // end=1
        fifo.add((E) "7"); // end=2
        fifo.add((E) "8"); // end=3

        assertEquals("[4, 5, 6, 7, 8]", fifo.toString());

        fifo.remove("8"); // remove element at position two in array
        assertEquals("[4, 5, 6, 7]", fifo.toString());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testRepeatedSerialization() throws Exception {
        // bug 31433
        final CircularFifoQueue<E> b = new CircularFifoQueue<>(2);
        b.add((E) "a");
        assertEquals(1, b.size());
        assertTrue(b.contains("a"));

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        new ObjectOutputStream(bos).writeObject(b);

        final CircularFifoQueue<E> b2 = (CircularFifoQueue<E>) new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray())).readObject();

        assertEquals(1, b2.size());
        assertTrue(b2.contains("a"));
        b2.add((E) "b");
        assertEquals(2, b2.size());
        assertTrue(b2.contains("a"));
        assertTrue(b2.contains("b"));

        bos = new ByteArrayOutputStream();
        new ObjectOutputStream(bos).writeObject(b2);

        final CircularFifoQueue<E> b3 = (CircularFifoQueue<E>) new ObjectInputStream(
                new ByteArrayInputStream(bos.toByteArray())).readObject();

        assertEquals(2, b3.size());
        assertTrue(b3.contains("a"));
        assertTrue(b3.contains("b"));
        b3.add((E) "c");
        assertEquals(2, b3.size());
        assertTrue(b3.contains("b"));
        assertTrue(b3.contains("c"));
    }

    @Test
    public void testGetIndex() {
        resetFull();

        final CircularFifoQueue<E> queue = getCollection();
        final List<E> confirmed = (List<E>) getConfirmed();
        for (int i = 0; i < confirmed.size(); i++) {
            assertEquals(confirmed.get(i), queue.get(i));
        }

        // remove the first two elements and check again
        queue.remove();
        queue.remove();

        for (int i = 0; i < queue.size(); i++) {
            assertEquals(confirmed.get(i + 2), queue.get(i));
        }
    }

    @Test
    public void testAddNull() {
        final CircularFifoQueue<E> b = new CircularFifoQueue<>(2);
        assertThrows(NullPointerException.class, () -> b.add(null));
    }

    @Test
    public void testDefaultSizeAndGetError1() {
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>();
        assertEquals(32, fifo.maxSize());
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5");
        assertEquals(5, fifo.size());
        assertThrows(NoSuchElementException.class, () -> fifo.get(5));
    }

    @Test
    public void testDefaultSizeAndGetError2() {
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>();
        assertEquals(32, fifo.maxSize());
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        fifo.add((E) "4");
        fifo.add((E) "5");
        assertEquals(5, fifo.size());
        assertThrows(NoSuchElementException.class, () -> fifo.get(-2));
    }

    @Override
    public String getCompatibilityVersion() {
        return "4";
    }

    // public void testCreate() throws Exception {
    // resetEmpty();
    // writeExternalFormToDisk((java.io.Serializable) getCollection(),
    // "src/test/resources/data/test/CircularFifoQueue.emptyCollection.version4.obj");
    // resetFull();
    // writeExternalFormToDisk((java.io.Serializable) getCollection(),
    // "src/test/resources/data/test/CircularFifoQueue.fullCollection.version4.obj");
    // }

    /**
     * {@inheritDoc}
     */
    @Override
    public CircularFifoQueue<E> getCollection() {
        return (CircularFifoQueue<E>) super.getCollection();
    }

}
