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

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Test cases for CircularFifoQueue.
 *
 * @since 4.0
 */
public class IsCircularFifoQueueTest<E> extends AbstractQueueTest<E> {
    public IsCircularFifoQueueTest() {
        super(IsCircularFifoQueueTest.class.getSimpleName());
    }

    /**
     *  Runs through the regular verifications, but also verifies that
     *  the buffer contains the same elements in the same sequence as the
     *  list.
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
     * @return false
     */
    @Override
    public boolean isNullSupported() {
        return false;
    }

    /**
     * Overridden because CircularFifoQueue isn't fail fast.
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
     * Tests that the removal operation actually removes the first element.
     */
    /* @Test
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
    }*/

    /**
     * Tests that the removal operation actually removes the first element.
     */
    /*@Test
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
    }*/

    /**
     * Tests that the constructor correctly throws an exception.
     */
    /*@Test
    public void testConstructorException1() {
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<E>(0));
    }
    */
    /**
     * Tests that the constructor correctly throws an exception.
     */
    /*@Test
    public void testConstructorException2() {
        assertThrows(IllegalArgumentException.class, () -> new CircularFifoQueue<E>(-20));
    }
    */
    /**
     * Tests that the constructor correctly throws an exception.
     */
    /*@Test
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("4");  // remove element in middle of array, after start
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("5");  // remove element at last pos in array
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("6");  // remove element at position zero in array
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2

        assertEquals("[3, 4, 5, 6, 7]", fifo.toString());

        fifo.remove("7");  // remove element at position one in array
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2
        fifo.add((E) "8");  // end=3

        assertEquals("[4, 5, 6, 7, 8]", fifo.toString());

        fifo.remove("7");  // remove element at position one in array, need to shift 8
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
        fifo.add((E) "5");  // end=0
        fifo.add((E) "6");  // end=1
        fifo.add((E) "7");  // end=2
        fifo.add((E) "8");  // end=3

        assertEquals("[4, 5, 6, 7, 8]", fifo.toString());

        fifo.remove("8");  // remove element at position two in array
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
    }*/

    @Override
    public String getCompatibilityVersion() {
        return "4";
    }

//    public void testCreate() throws Exception {
//        resetEmpty();
//        writeExternalFormToDisk((java.io.Serializable) getCollection(), "src/test/resources/data/test/CircularFifoQueue.emptyCollection.version4.obj");
//        resetFull();
//        writeExternalFormToDisk((java.io.Serializable) getCollection(), "src/test/resources/data/test/CircularFifoQueue.fullCollection.version4.obj");
//    }

    /**
     * {@inheritDoc}
     */
    @Override
    public CircularFifoQueue<E> getCollection() {
        return (CircularFifoQueue<E>) super.getCollection();
    }
    // D1
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(1);
        queue.add(1);
        queue.add(2);
        int queueValue = queue.get(0);
        assertEquals(2, queueValue);
        assertFalse(queue.contains(1));
    }

    // D2
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.add(i);
        int queueValue = queue.get(0);
        assertEquals(2, queueValue);
        assertFalse(queue.contains(1));
    }

    // D3
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(1);
        queue.offer(1);
        queue.offer(2);
        int queueValue = queue.get(0);
        assertEquals(2, queueValue);
        assertFalse(queue.contains(1));
    }

    // D4
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.offer(i);
        int queueValue = queue.get(0);
        assertEquals(2, queueValue);
        assertFalse(queue.contains(1));
    }

    // D5
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(2);
        queue.add(1);
        queue.add(2);
        queue.add(3);
        int queueValue = queue.get(1);
        assertEquals(3, queueValue);
        assertFalse(queue.contains(1));
    }

    // D6
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.add(i);
        int queueValue = queue.get(9);
        assertEquals(13, queueValue);
        assertFalse(queue.contains(1));
    }

    // D7
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(2);
        queue.offer(1);
        queue.offer(2);
        queue.offer(3);
        int queueValue = queue.get(1);
        assertEquals(3, queueValue);
        assertFalse(queue.contains(1));
    }

    // D8
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElement() {

        CircularFifoQueue<Integer> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.offer(i);
        int queueValue = queue.get(9);
        assertEquals(13, queueValue);
        assertFalse(queue.contains(1));
    }

    // Double
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(1);
        queue.add(1.1);
        queue.add(2.1);
        double queueValue = queue.get(0);
        assertEquals(2.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.add(Double.valueOf(i)+0.1);
        double queueValue = queue.get(0);
        assertEquals(2.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(1);
        queue.offer(1.1);
        queue.offer(2.1);
        double queueValue = queue.get(0);
        assertEquals(2.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.offer(Double.valueOf(i)+0.1);
        double queueValue = queue.get(0);
        assertEquals(2.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(2);
        queue.add(1.1);
        queue.add(2.1);
        queue.add(3.1);
        double queueValue = queue.get(1);
        assertEquals(3.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.add(Double.valueOf(i)+0.1);
        double queueValue = queue.get(9);
        assertEquals(13.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(2);
        queue.offer(1.1);
        queue.offer(2.1);
        queue.offer(3.1);
        double queueValue = queue.get(1);
        assertEquals(3.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeDouble() {

        CircularFifoQueue<Double> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.offer(Double.valueOf(i)+0.1);
        double queueValue = queue.get(9);
        assertEquals(13.1, queueValue);
        assertFalse(queue.contains(1.1));
    }

    // Float

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(1);
        queue.add(1.1f);
        queue.add(2.1f);
        float queueValue = queue.get(0);
        assertEquals(2.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.add(Float.valueOf(i)+0.1f);
        float queueValue = queue.get(0);
        assertEquals(2.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(1);
        queue.offer(1.1f);
        queue.offer(2.1f);
        float queueValue = queue.get(0);
        assertEquals(2.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.offer(Float.valueOf(i)+0.1f);
        float queueValue = queue.get(0);
        assertEquals(2.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(2);
        queue.add(1.1f);
        queue.add(2.1f);
        queue.add(3.1f);
        float queueValue = queue.get(1);
        assertEquals(3.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.add(Float.valueOf(i)+0.1f);
        float queueValue = queue.get(9);
        assertEquals(13.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(2);
        queue.offer(1.1f);
        queue.offer(2.1f);
        queue.offer(3.1f);
        float queueValue = queue.get(1);
        assertEquals(3.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeFloat() {

        CircularFifoQueue<Float> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.offer(Float.valueOf(i)+0.1f);
        float queueValue = queue.get(9);
        assertEquals(13.1f, queueValue);
        assertFalse(queue.contains(1.1f));
    }

    // String
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(1);
        queue.add("1");
        queue.add("2");
        String queueValue = queue.get(0);
        assertEquals("2", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.add(String.valueOf(i));
        String queueValue = queue.get(0);
        assertEquals("2", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(1);
        queue.offer("1");
        queue.offer("2");
        String queueValue = queue.get(0);
        assertEquals("2", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++) queue.offer(String.valueOf(i));
        String queueValue = queue.get(0);
        assertEquals("2", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(2);
        queue.add("1");
        queue.add("2");
        queue.add("3");
        String queueValue = queue.get(1);
        assertEquals("3", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.add(String.valueOf(i));
        String queueValue = queue.get(9);
        assertEquals("13", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(2);
        queue.offer("1");
        queue.offer("2");
        queue.offer("3");
        String queueValue = queue.get(1);
        assertEquals("3", queueValue);
        assertFalse(queue.contains("1"));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeString() {

        CircularFifoQueue<String> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++) queue.offer(String.valueOf(i));
        String queueValue = queue.get(9);
        assertEquals("13", queueValue);
        assertFalse(queue.contains("1"));
    }

    // char
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(1);
        queue.add('1');
        queue.add('2');
        char queueValue = queue.get(0);
        assertEquals('2', queueValue);
        assertFalse(queue.contains('1'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(10);
        for(int i=65;i<=75;i++) queue.add((char)i);
        char queueValue = queue.get(0);
        assertEquals('B', queueValue);
        assertFalse(queue.contains('A'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(1);
        queue.offer('1');
        queue.offer('2');
        char queueValue = queue.get(0);
        assertEquals('2', queueValue);
        assertFalse(queue.contains('1'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(10);
        for(int i=65;i<=75;i++) queue.offer((char)i);
        char queueValue = queue.get(0);
        assertEquals('B', queueValue);
        assertFalse(queue.contains('A'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(2);
        queue.add('1');
        queue.add('2');
        queue.add('3');
        char queueValue = queue.get(1);
        assertEquals('3', queueValue);
        assertFalse(queue.contains('1'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(10);
        for(int i=65;i<=77;i++) queue.add((char)i);
        char queueValue = queue.get(9);
        assertEquals('M', queueValue);
        assertFalse(queue.contains('A'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(2);
        queue.offer('1');
        queue.offer('2');
        queue.offer('3');
        char queueValue = queue.get(1);
        assertEquals('3', queueValue);
        assertFalse(queue.contains('1'));
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeCharacter() {

        CircularFifoQueue<Character> queue = new CircularFifoQueue<>(10);
        for(int i=65;i<=77;i++) queue.offer((char)i);
        char queueValue = queue.get(9);
        assertEquals('M', queueValue);
        assertFalse(queue.contains('A'));
    }


    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneAddOneElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(1);
        queue.add(true);
        queue.add(false);
        boolean queueValue = queue.get(0);
        assertEquals(false, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddElevenElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++)
        {
            if (i%2 ==1) queue.add(true);
            else queue.add(false);
        }
        boolean queueValue = queue.get(0);
        assertEquals(false, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsOneOfferOneElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(1);
        queue.offer(true);
        queue.offer(false);
        boolean queueValue = queue.get(0);
        assertEquals(false, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferlevenElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=11;i++)
        {
            if (i%2 ==1) queue.add(true);
            else queue.add(false);
        }
        boolean queueValue = queue.get(0);
        assertEquals(false, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoAddThreeElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(2);
        queue.add(true);
        queue.add(false);
        queue.add(true);
        boolean queueValue = queue.get(1);
        assertEquals(true, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenAddThirteenElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++)
        {
            if (i%2 ==1) queue.add(true);
            else queue.add(false);
        }
        boolean queueValue = queue.get(9);
        assertEquals(true, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTwoOfferThreeElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(2);
        queue.offer(true);
        queue.offer(false);
        queue.offer(true);
        boolean queueValue = queue.get(1);
        assertEquals(true, queueValue);
    }

    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeBoolean() {

        CircularFifoQueue<Boolean> queue = new CircularFifoQueue<>(10);
        for(int i=1;i<=13;i++)
        {
            if (i%2 ==1) queue.add(true);
            else queue.add(false);
        }
        boolean queueValue = queue.get(9);
        assertEquals(true, queueValue);
    }




    // null EXCEPTION
    @Test
    public void testCircularFifoQueueCircularWhenQueueSizeIsTenOfferThirteenElementTypeNull() {
        CircularFifoQueue<E> queue = new CircularFifoQueue<>(10);
        Throwable exception = assertThrows(NullPointerException.class, () -> {queue.offer(null);});

    }
}
