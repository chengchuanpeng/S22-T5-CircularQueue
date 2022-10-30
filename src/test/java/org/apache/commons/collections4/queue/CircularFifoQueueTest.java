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
import java.util.*;

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
    // case ID: A1
    @Test
    public void testInitWithSizeEqualsToThree() {
        int size = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(size);
        assertEquals(size, fifo.maxSize());
    }

    // case ID: A2
    @Test
    public void testInitWithSizeEqualToZeroShouldThrowException() {
        int size = 0;
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<Integer>(size));
    }

    // case ID: A3
    @Test
    public void testInitWithSizeEqualsToOne() {
        int size = 1;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(size);
        assertEquals(size, fifo.maxSize());
    }

    // case ID: A4
    // Init with maxSize should pass, but will potentially consume too much memory so we comment it out.
    @Test
    public void testInitWithSizeEqualsToMaxInt() {
    //    int maxSize = Integer.MAX_VALUE;
    //    final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
    //    assertEquals(maxSize, fifo.maxSize());
    }

    // case ID: A5
    @Test
    public void testInitWithSizeExceedMaxIntShouldThrowException() {
        int offset = 10;
        int maxSize = Integer.MAX_VALUE + offset;
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<Integer>(maxSize));
    }

    // case ID: A6
    @Test
    public void testInitWithNullElementShouldThrowException() {
        assertThrows(NullPointerException.class, () -> new CircularFifoQueue<Integer>(null));
    }

    // // case ID: A7
    @Test
    public void testInitWithPrimitiveTypeShouldHaveSyntaxError() {
        // Initialize CircularFifoQueue with primitive type should have syntax error
        // final CircularFifoQueue<int> fifo = new CircularFifoQueue<int>(maxSize);
    }

    // case ID: A8
    @Test
    public void testInitArrayListCircularFifoQueue() {
        try {
            final CircularFifoQueue<ArrayList<Integer>> queue = new CircularFifoQueue<ArrayList<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A9
    @Test
    public void testInitHashSetCircularFifoQueue() {
        try {
            final CircularFifoQueue<HashSet<Integer>> queue = new CircularFifoQueue<HashSet<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A10
    @Test
    public void testInitTreeSetCircularFifoQueue() {
        try {
            final CircularFifoQueue<TreeSet<Integer>> queue = new CircularFifoQueue<TreeSet<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A11
    @Test
    public void testInitLinkedListCircularFifoQueue() {
        try {
            final CircularFifoQueue<LinkedList<Integer>> queue = new CircularFifoQueue<LinkedList<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A12
    @Test
    public void testInitVectorCircularFifoQueue() {
        try {
            final CircularFifoQueue<Vector<Integer>> queue = new CircularFifoQueue<Vector<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A13
    @Test
    public void testInitPriorityQueueCircularFifoQueue() {
        try {
            final CircularFifoQueue<PriorityQueue<Integer>> queue = new CircularFifoQueue<PriorityQueue<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A14
    @Test
    public void testInitStackCircularFifoQueue() {
        try {
            final CircularFifoQueue<Stack<Integer>> queue = new CircularFifoQueue<Stack<Integer>>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A15
    @Test
    public void testInitWithStringShouldBeValid() {
        try {
            final CircularFifoQueue<String> fifo = new CircularFifoQueue<String>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A16
    @Test
    public void testInitWithByteShouldBeValid() {
        try {
            final CircularFifoQueue<Byte> fifo = new CircularFifoQueue<Byte>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A17
    @Test
    public void testInitWithShortShouldBeValid() {
        try {
            final CircularFifoQueue<Short> fifo = new CircularFifoQueue<Short>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A18
    @Test
    public void testInitWithLongShouldBeValid() {
        try {
            final CircularFifoQueue<Long> fifo = new CircularFifoQueue<Long>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A19
    @Test
    public void testInitWithFloatShouldBeValid() {
        try {
            final CircularFifoQueue<Float> fifo = new CircularFifoQueue<Float>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A20
    @Test
    public void testInitWithDoubleShouldBeValid() {
        try {
            final CircularFifoQueue<Double> fifo = new CircularFifoQueue<Double>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A21
    @Test
    public void testInitWithBooleanShouldBeValid() {
        try {
            final CircularFifoQueue<Boolean> fifo = new CircularFifoQueue<Boolean>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: A22
    @Test
    public void testInitWithCharacterShouldBeValid() {
        try {
            final CircularFifoQueue<Character> fifo = new CircularFifoQueue<Character>();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: C1, C2
    @Test
    public void testIsFirstInFirstOut() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(5);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("1", queue.poll());
        assertEquals("2", queue.poll());
        assertEquals("3", queue.poll());
    }

    // case ID: D1, J1
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


    // case ID: D2
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

    // case ID: E1
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

    // case ID: E2
    @Test
    public void testAddVaryElementTypeWhenSizeIsValidShouldBuildFailed() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        // queue.add("2"); Build failed
    }

    // case ID: F1
    @Test
    @SuppressWarnings("unchecked")
    public void testGetWhenInputIndexIsLowerBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertEquals("1", queue.get(0));
    }

    // case ID: F2
    @Test
    @SuppressWarnings("unchecked")
    public void testGetWhenInputIndexIsBelowLowerBoundaryShouldBeInvalid() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertThrows(NoSuchElementException.class, () -> queue.get(-1));
    }

    // case ID: F3
    @Test
    @SuppressWarnings("unchecked")
    public void testGetWhenInputIndexIsValidAndNotBoundaryValueShouldReturnCorrectResult() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("2", queue.get(1));
    }

    // case ID: F4
    @Test
    @SuppressWarnings("unchecked")
    public void testGetWhenInputIndexIsUpperBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("3", queue.get(2));
    }

    // case ID: F5
    @Test
    @SuppressWarnings("unchecked")
    public void testGetWhenInputIsOverUpperBoundary() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertThrows(NoSuchElementException.class, () -> queue.get(4));
    }

    // case ID: G1, O1
    @Test
    public void testConstructorDefaultSize() {
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>();
        assertEquals(32, queue.maxSize());
    }

    // case ID: J2-1
    @Test
    public void testAddIntegerIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        try {
            // act
            fifo.add(3);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-2
    @Test
    public void testAddArrayListIsValid() {
        // arrange
        final CircularFifoQueue<ArrayList<Integer>> fifo = new CircularFifoQueue<ArrayList<Integer>>();
        ArrayList<Integer> arr = new ArrayList<>();
        try {
            // act
            fifo.add(arr);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-3
    @Test
    public void testAddHashSetIsValid() {
        // arrange
        final CircularFifoQueue<HashSet<Integer>> fifo = new CircularFifoQueue<HashSet<Integer>>();
        HashSet<Integer> set = new HashSet<>();
        try {
            // act
            fifo.add(set);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-4
    @Test
    public void testAddTreeSetIsValid() {
        // arrange
        final CircularFifoQueue<TreeSet<Integer>> fifo = new CircularFifoQueue<TreeSet<Integer>>();
        TreeSet<Integer> set = new TreeSet<>();
        try {
            // act
            fifo.add(set);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-5
    @Test
    public void testAddLinkedListIsValid() {
        // arrange
        final CircularFifoQueue<LinkedList<Integer>> fifo = new CircularFifoQueue<LinkedList<Integer>>();
        LinkedList<Integer> list = new LinkedList<>();
        try {
            // act
            fifo.add(list);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-6
    @Test
    public void testAddVectorIsValid() {
        // arrange
        final CircularFifoQueue<Vector<Integer>> fifo = new CircularFifoQueue<Vector<Integer>>();
        Vector<Integer> v = new Vector<>();
        try {
            // act
            fifo.add(v);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-7
    @Test
    public void testAddPriorityQueueIsValid() {
        // arrange
        final CircularFifoQueue<PriorityQueue<Integer>> fifo = new CircularFifoQueue<PriorityQueue<Integer>>();
        PriorityQueue<Integer> heap = new PriorityQueue<>();
        try {
            // act
            fifo.add(heap);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-8
    @Test
    public void testAddStackIsValid() {
        // arrange
        final CircularFifoQueue<Stack<Integer>> fifo = new CircularFifoQueue<Stack<Integer>>();
        Stack<Integer> stack = new Stack<>();
        try {
            // act
            fifo.add(stack);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-9
    @Test
    public void testAddStringIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<String> fifo = new CircularFifoQueue<>(maxSize);
        String str = "test string";
        try {
            // act
            fifo.add(str);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-10
    @Test
    public void testAddByteIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Byte> fifo = new CircularFifoQueue<Byte>(maxSize);
        Byte b = 2;
        try {
            // act
            fifo.add(b);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-11
    @Test
    public void testAddShortIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Short> fifo = new CircularFifoQueue<Short>(maxSize);
        Short sh = 2;
        try {
            // act
            fifo.add(sh);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-12
    @Test
    public void testAddLongIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Long> fifo = new CircularFifoQueue<Long>(maxSize);
        long lng = 5;
        try {
            // act
            fifo.add(lng);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-13
    @Test
    public void testAddFloatIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Float> fifo = new CircularFifoQueue<Float>(maxSize);
        float f = 0.05f;
        try {
            // act
            fifo.add(f);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-14
    @Test
    public void testAddDoubleIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Double> fifo = new CircularFifoQueue<Double>(maxSize);
        Double d = 0.05;
        try {
            // act
            fifo.add(d);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-15
    @Test
    public void testAddBooleanIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Boolean> fifo = new CircularFifoQueue<Boolean>(maxSize);
        Boolean b = true;
        try {
            // act
            fifo.add(b);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-16
    @Test
    public void testAddCharacterIsValid() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Character> fifo = new CircularFifoQueue<Character>(maxSize);
        Character ch = 'a';
        try {
            // act
            fifo.add(ch);
        } catch (Exception e) {
            // assert
            fail("should not have thrown error");
        }
    }

    // case ID: J2-17
    @Test
    public void testAddNullShouldThrowException() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<Integer>(maxSize);
        // act & assert
        assertThrows(NullPointerException.class, () -> fifo.add(null));
    }
    // case ID: k1, R2
    @Test
    public void testSizeOfEmptyCircularFifoQueueShouldBeZero() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        int expectedSize = 0;
        assertEquals(expectedSize, fifo.size());
    }

    // case ID: K2
    @Test
    public void testSizeFnUpperBoundary() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        assertEquals(maxSize, fifo.size());
    }


    // case ID: L1. S2
    @Test
    public void testisEmptyFnWhenSizeIsNotZeroShouldReturnFalse() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        fifo.add(1);
        int expectedSize = 1;
        assertEquals(expectedSize, fifo.size());
        assertFalse(fifo.isEmpty());
    }

    // case ID: L2, S1
    // isEmpty()
    @Test
    public void testShouldBeEmptyIfSizeIsZero() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        int expectedSize = 0;
        assertEquals(expectedSize, fifo.size());
        assertTrue(fifo.isEmpty());
    }

    // case ID: M1
    // isFull()
    @Test
    public void testShouldNotBeFullIfSizeNotReachMaxSize() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        assertFalse(fifo.isFull());
    }

    // case ID: M2
    // isFull() -> should fail, potential mutant
    @Test
    public void testShouldBeFullIfSizeReachMaxSize() {
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
        assertEquals(maxSize, fifo.size());
        assertTrue(fifo.isAtFullCapacity());
    }


    // case ID: N1
    @Test
    public void testisAtFullCapacityShouldBeFalseWhenNotReachMaxSize() {
        // arrange
        int maxSize = 3;
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(maxSize);

        // assert
        assertFalse(fifo.isAtFullCapacity());
    }

    // case ID: N2
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

    // case ID: P1
    @Test
    public void testQueueIsNotEmptyWhenThereAreElemsAndNotClear() {
        // arrange
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);

        // assert
        assertFalse(fifo.isEmpty());
    }
    // case ID: P2
    // Clear()
    @Test
    public void testSizeShouldBeZeroAfterClear() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.clear();
        assertTrue(fifo.isEmpty());
    }

    // case ID: Q1
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoHashSetSize() {
        HashSet<Integer> set = new HashSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(set);
        assertEquals(set.size(), queue.size());
    }

    // case ID: Q2
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoArrayListSize() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // case ID: Q3
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoTreeSetSize() {
        TreeSet<Integer> set = new TreeSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(set);
        assertEquals(set.size(), queue.size());
    }

    // case ID: Q4
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoLinkedListSize() {
        LinkedList<Integer> list = new LinkedList<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // case ID: Q5
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoVectorSize() {
        Vector<Integer> list = new Vector<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(list);
        assertEquals(list.size(), queue.size());
    }

    // case ID: Q6
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoPriorityQueueSize() {
        PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
        pq.add(1);
        pq.add(2);
        pq.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(pq);
        assertEquals(pq.size(), queue.size());
    }

    // case ID: Q7
    @Test
    public void testQueueSizeSize_ShouldBeEqualtoStackSize() {
        Stack<Integer> stack = new Stack<Integer>();
        stack.add(1);
        stack.add(2);
        stack.add(3);
        CircularFifoQueue<Integer> queue = new CircularFifoQueue<Integer>(stack);
        assertEquals(stack.size(), queue.size());
    }

    // case ID: Q8
    @Test
    public void testInitWithNullShouldBeInvalid() {
        assertThrows(NullPointerException.class, () -> new CircularFifoQueue<Integer>(null));
    }

    // ---------- White Box Testing -----------
    /**
     * MC/DC test for iterator.hasNext().
     */
    // | Test case | isFirst   | index != end|
    // |-----------|-----------|-------------|
    // | 1 | F | F |
    // | 2 | T | F |
    // | 3 | F | T |
    // case ID: T1
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

    // case ID: T2
    @Test
    public void testHasNoNextElementIfEmpty() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertFalse(itr.hasNext());
    }

    // case ID: T3
    @Test
    public void testNoNextElementIfIteratorReachEnd() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        fifo.add(1);
        itr.next();
        assertFalse(itr.hasNext());
    }

    // Test Iterator.next() fn by all branch coverage
    // case ID: U1
    @Test
    public void testNoNextElementShouldThrowException() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertThrows(NoSuchElementException.class, () -> itr.next());
    }

    // case ID: U2
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

    /**
     * MC/DC test for {@link #get(int)}.
     */
    // | Test case | index < 0 | index >= sz|
    // |-----------|-----------|------------|
    // | 1 | T | F |
    // | 2 | F | F |
    // | 3 | F | T |

    // case ID: V1
    @Test
    public void testGetElementWhenIndexLessThanZeroThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertThrows(NoSuchElementException.class, () -> queue.get(-1));
    }

    // case ID: V2
    @Test
    public void testGetElementWhenIndexGreaterThanZeroAndLessThanSize() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        queue.add((E) "2");
        queue.add((E) "3");
        assertEquals("2", queue.get(1));
    }

    // case ID: V3
    @Test
    public void testGetElementWhenIndexGreaterThanSizeThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        assertThrows(NoSuchElementException.class, () -> queue.get(3));
    }

    // test remove using CFG & base path strategy
    // case ID: W1
    @Test
    public void testRemoveWhenSizeIsZeroThrowError() {
        final CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(3);
        assertThrows(NoSuchElementException.class, () -> queue.remove());
    }

    // case ID: W2
    @Test
    public void testRemoveWhenSizeIsOne() {
        final CircularFifoQueue<E> queue = new CircularFifoQueue<>(3);
        queue.add((E) "1");
        assertEquals("1", queue.remove());
    }

    // case ID: W3
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

    // ----------------

    // case ID: X1
    // Test peek() fn by all branch coverage
    @Test
    public void testPeekShouldReturnNullIfEmpty() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.peek();
        assertTrue(fifo.isEmpty());
        assertNull(fifo.peek());
    }

    // case ID: X2
    @Test
    public void testPeekShouldReturnOldestElement() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.add(2);
        int expectedElement = 1;
        assertEquals(expectedElement, (E) fifo.peek());
    }

    // case ID: Y1
    // Iterator.remove()
    @Test
    public void testIteratorRemoveInvalidIndex() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>();
        Iterator<Integer> itr = fifo.iterator();
        assertThrows(IllegalStateException.class, itr::remove);
    }

    // case ID: Y2
    @Test
    public void testIteratorRemoveFirstIndex() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        Iterator<Integer> itr = fifo.iterator();
        itr.next();
        try {
            itr.remove();
        } catch (Exception e) {
            fail("should not have thrown error");
        }
    }

    // case ID: Y3
    @Test
    public void testIteratorRemoveAtIndexBetweenStartAndEnd() {
        final CircularFifoQueue<Integer> fifo = new CircularFifoQueue<>(3);
        fifo.add(1);
        fifo.add(2);
        fifo.add(3);
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

    // case ID: Y4
    @Test
    public void testIteratorRemoveAtEndPos() {
        final CircularFifoQueue<E> fifo = new CircularFifoQueue<>(3);
        fifo.add((E) "1");
        fifo.add((E) "2");
        fifo.add((E) "3");
        int expectedSize = 2;
        Iterator<E> itr = fifo.iterator();
        itr.next();
        itr.next();
        itr.next();
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
