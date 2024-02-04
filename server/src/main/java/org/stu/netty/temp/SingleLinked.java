package org.stu.netty.temp;

import lombok.Data;

import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author yupeng.zhang
 * @date 2024/1/12
 */
public class SingleLinked<Inner> {
    Node<Inner> head;
    AtomicInteger size;

    public SingleLinked(Inner inner) {
        size = new AtomicInteger();
        this.head = new Node<Inner>(inner);
    }

    @Data
    class Node<E> {
        Node<E> next;
        E e;

        public Node(E e) {
            this.e = e;
        }
    }

    private void addLast(Inner name) {
        Node<Inner> lastNode = new Node<>(name);
        Node<Inner> tail = head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        tail.setNext(lastNode);
        size.incrementAndGet();
    }

    private void addFirst(Inner inner) {
        Node<Inner> firstNode = new Node<>(inner);
        firstNode.setNext(head.getNext());
        head.setNext(firstNode);
        size.incrementAndGet();
    }

    private Inner getFirst() {
        if (head.getNext() == null) {
            throw new NoSuchElementException();
        }
        return head.getNext().getE();
    }

    private Inner getLast() {
        if (head.getNext() == null) {
            throw new NoSuchElementException();
        }
        Node<Inner> tail = head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
        }
        return tail.getE();
    }

    private boolean isEmpty() {
        return size.get() == 0;
    }

    private Inner removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Inner> prev = head;
        while (prev.getNext().getNext() != null) {
            prev = prev.getNext();
        }

        Node<Inner> lastNode = prev.getNext();
        prev.setNext(null);
        size.decrementAndGet();
        return lastNode.getE();
    }

    private Inner removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<Inner> first = head.getNext();
        head.setNext(first.getNext());
        size.decrementAndGet();
        return first.getE();
    }

    private boolean contains(Inner inner) {
        Node<Inner> next = head;
        while (next.getNext() != null) {
            next = next.getNext();
            if (next.getE().equals(inner)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }

    private int indexOf(Inner inner) {
        int index = 0;
        Node<Inner> search = this.head;
        while (search.getNext() != null) {
            search = search.getNext();
            if (search.getE().equals(inner)) {
                return index;
            }
            ++index;
        }
        return -1;
    }

    private Inner get(int index) {
        if (index < 0 || index > (size.get() + 1)) {
            throw new IndexOutOfBoundsException();
        }
        Node<Inner> indexNode = head;
        for (int i = 0; i <= index; i++) {
            indexNode = indexNode.getNext();
        }
        return indexNode.getE();
    }

    private Inner set(int index, Inner inner) {
        if (index < 0 || index > (size.get() + 1)) {
            throw new IndexOutOfBoundsException();
        }
        Node<Inner> posi = head.getNext();
        while (index > 0) {
            posi = posi.getNext();
            --index;
        }
        posi.setE(inner);
        return posi.getE();
    }

    private Inner remove(int index) {
        if (index < 0 || index > (size.get() + 1)) {
            throw new IndexOutOfBoundsException();
        }
        index -= 1;
        Node<Inner> prev = head.getNext();
        while (index > 0) {
            prev = prev.getNext();
            --index;
        }
        Node<Inner> nowNode = prev.getNext();
        prev.setNext(nowNode.getNext());
        size.decrementAndGet();
        return nowNode.getE();
    }

    private boolean remove(Inner inner) {
        int index = indexOf(inner);
        return index != -1 && remove(index).equals(inner);
    }

    private Inner insert(int index, Inner inner) {
        if (index < 0 || index > (size.get() + 1)) {
            throw new IndexOutOfBoundsException();
        }
        Node<Inner> prev = this.head;
        while (index > 0) {
            prev = prev.getNext();
            --index;
        }
        Node<Inner> insertNode = new Node<>(inner);
        insertNode.setNext(prev.getNext());
        prev.setNext(insertNode);
        size.incrementAndGet();
        return insertNode.getE();
    }

    private void clear() {
        Node<Inner> tmp = this.head;
        while (tmp.getNext() != null) {
            Node<Inner> next = tmp.getNext();
            tmp.setNext(null);
            tmp.setE(null);
            tmp = next;
            size.decrementAndGet();
        }
    }


    private void print() {
        Node<Inner> printNode = head;
        while (printNode.next != null) {
            printNode = printNode.next;
            System.out.println(printNode.e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        Node<Inner> tail = this.head;
        while (tail.getNext() != null) {
            tail = tail.getNext();
            sb.append(tail.getE());
            if (tail.getNext() != null) {
                sb.append(", ");
            }
        }
        sb.append("]");
        return sb.toString();
    }

    public static void main(String[] args) {
        SingleLinked<String> sl = new SingleLinked<>("奇点");
        for (int i = 0; i < 5; i++) {
            sl.addLast(i + "");
        }
        System.out.println(sl);
        System.out.println("size：" + sl.size);
        System.out.println("---");
        sl.addFirst("-1");
        sl.addLast("10");
        sl.addFirst("-2");
        sl.addLast("14");
        sl.addFirst("-4");
        System.out.println(sl);
        System.out.println("size：" + sl.size);

        System.out.println("remove first：" + sl.removeFirst());
        System.out.println("remove last：" + sl.removeLast());

        System.out.println(sl);
        System.out.println("size" + sl.size);

        System.out.println("remove first：" + sl.removeFirst());
        System.out.println("remove last：" + sl.removeLast());

        System.out.println(sl);
        System.out.println("size" + sl.size);

        System.out.println("contains: 4 |> " + sl.contains("4"));
        System.out.println("indexOf: 4 |> " + sl.indexOf("4"));
        System.out.println("get: 4 |> " + sl.get(4));
        System.out.println("set(3,2.3)" + sl.set(3, "2.3"));
        System.out.println(sl);

        System.out.println("remove(3)" + sl.remove(3));
        System.out.println(sl);
        System.out.println("size" + sl.size);

        System.out.println("remove(3)" + sl.remove("3"));
        System.out.println(sl);
        System.out.println("size" + sl.size);

        System.out.println("insert(2,0.5)" + sl.insert(2, "0.5"));
        System.out.println(sl);
        System.out.println("size" + sl.size);

        System.out.println("clear()");
        sl.clear();
        System.out.println(sl);
        System.out.println("size" + sl.size);


    }
}
