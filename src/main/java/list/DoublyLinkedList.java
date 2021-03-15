package list;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Serializable;
import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.Deque;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Класс двусвязного списка.
 *
 * @param <T> тип элементов в списке.
 */
public class DoublyLinkedList<T> extends AbstractSequentialList<T>
    implements Cloneable, Externalizable, Deque<T> {
  private int size = 0;
  private Node<T> first;
  private Node<T> last;

  /**
   * Пустой конструктор.
   */
  public DoublyLinkedList() {
  }

  /**
   * Добавляет элемент в начало коллекции.
   *
   * @param element элемент.
   */
  @Override
  public void addFirst(T element) {
    final Node<T> currentFirst = first;
    final Node<T> newNode = new Node<>(element, null, currentFirst);
    first = newNode;
    if (currentFirst == null) {
      last = newNode;
    } else {
      currentFirst.prev = newNode;
    }
    size++;
    modCount++;
  }

  /**
   * Добавляет элемент в конец коллекции.
   *
   * @param element элемент.
   */
  @Override
  public void addLast(T element) {
    final Node<T> currentLast = last;
    final Node<T> newNode = new Node<>(element, currentLast, null);
    last = newNode;
    if (currentLast == null) {
      first = newNode;
    } else {
      currentLast.next = newNode;
    }
    size++;
    modCount++;
  }

  /**
   * Вставляет элемент в начало коллекции.
   *
   * @param element элемент.
   * @return true.
   */
  @Override
  public boolean offerFirst(T element) {
    addFirst(element);
    return true;
  }

  /**
   * Вставляет элемент в конец списка.
   *
   * @param element элемент.
   * @return true.
   */
  @Override
  public boolean offerLast(T element) {
    addLast(element);
    return true;
  }

  /**
   * Удаляет и возвращает первый элемент коллекции.
   *
   * @return первый элемент списка.
   */
  @Override
  public T removeFirst() {
    final Node<T> element = first;
    if (element == null) {
      throw new NoSuchElementException();
    }
    return unlinkFirst(element);
  }

  /**
   * Отменяет связь ненулевого первого узла.
   *
   * @param item ненулевой первый узел.
   * @return содержимое узла.
   */
  private T unlinkFirst(Node<T> item) {
    final T element = item.item;
    final Node<T> next = item.next;
    item.item = null;
    item.next = null;
    first = next;
    if (next == null) {
      last = null;
    } else {
      next.prev = null;
    }
    size--;
    modCount++;
    return element;
  }

  /**
   * Удаляет и возвращает последний элемент коллекции.
   *
   * @return последний элемент списка.
   */
  @Override
  public T removeLast() {
    final Node<T> element = last;
    if (element == null) {
      throw new NoSuchElementException();
    }
    return unlinkLast(element);

  }

  /**
   * Отменяет связь ненулевого последнего узла.
   *
   * @param item ненулевой последний узел.
   * @return содержимое узла.
   */
  private T unlinkLast(Node<T> item) {
    final T element = item.item;
    final Node<T> prev = item.prev;
    item.item = null;
    item.prev = null;
    last = prev;
    if (prev == null) {
      first = null;
    } else {
      prev.next = null;
    }
    size--;
    modCount++;
    return element;
  }

  /**
   * Отменяет связь с ненулевым узлом.
   *
   * @param x ненулевой узел.
   * @return содержимое узла.
   */
  T unlink(Node<T> x) {
    final T element = x.item;
    final Node<T> next = x.next;
    final Node<T> prev = x.prev;

    if (prev == null) {
      first = next;
    } else {
      prev.next = next;
      x.prev = null;
    }

    if (next == null) {
      last = prev;
    } else {
      next.prev = prev;
      x.next = null;
    }

    x.item = null;
    size--;
    modCount++;
    return element;
  }

  /**
   * Извлекает и удаляет первый элемент коллекции,
   * возвращает null, если коллекция пуста.
   *
   * @return первый элемент коллекции или null.
   */
  @Override
  public T pollFirst() {
    final Node<T> element = first;
    return (element == null) ? null : unlinkFirst(element);
  }

  /**
   * Извлекает и удаляет последний элемент коллекции,
   * возвращает null, если коллекция пуста.
   *
   * @return последний элемент коллекции или null.
   */
  @Override
  public T pollLast() {
    final Node<T> element = last;
    return (element == null) ? null : unlinkLast(element);
  }

  /**
   * Возвращает первый элемент коллекции.
   *
   * @return первый элемент коллекции.
   */
  @Override
  public T getFirst() {
    final Node<T> element = first;
    if (element == null) {
      throw new NoSuchElementException();
    }
    return element.item;

  }

  /**
   * Возвращает последний элемент коллекции.
   *
   * @return первый элемент коллекции.
   */
  @Override
  public T getLast() {
    final Node<T> element = last;
    if (element == null) {
      throw new NoSuchElementException();
    }
    return element.item;
  }

  /**
   * Извлекает первый элемент коллекции
   * возращает null если коллекция пуста.
   *
   * @return первый элемент списка или null.
   */
  @Override
  public T peekFirst() {
    final Node<T> element = first;
    return (element == null) ? null : element.item;
  }

  /**
   * Извлекает последний элемент коллекции
   * возращает null если коллекция пуста.
   *
   * @return первый элемент списка или null.
   */
  @Override
  public T peekLast() {
    final Node<T> element = last;
    return (element == null) ? null : element.item;
  }

  /**
   * Удаляет первое вхождение указанного элемента в коллекции.
   *
   * @param o элемент.
   * @return true, если коллекция содержала элемент
   */
  @Override
  public boolean removeFirstOccurrence(Object o) {
    return remove(o);
  }

  /**
   * Удаляет последнее вхождение указанного элемента в коллекции.
   *
   * @param o элемент.
   * @return true, если коллекция содержала элемент
   */
  @Override
  public boolean removeLastOccurrence(Object o) {
    if (o == null) {
      for (Node<T> x = last; x != null; x = x.prev) {
        if (x.item == null) {
          unlink(x);
          return true;
        }
      }
    } else {
      for (Node<T> x = last; x != null; x = x.prev) {
        if (o.equals(x.item)) {
          unlink(x);
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Добавляет элемент в конец коллекции.
   *
   * @param element элемент.
   * @return true.
   */
  public boolean add(T element) {
    addLast(element);
    return true;
  }

  /**
   * Добавляет элемент в конец коллекции.
   *
   * @param element элемент.
   * @return true.
   */
  @Override
  public boolean offer(T element) {
    return add(element);
  }

  /**
   * Извлекает и удаляет первый элемент коллекции.
   *
   * @return удаленный элемент.
   */
  @Override
  public T remove() {
    final Node<T> element = first;
    if (element == null) {
      throw new NoSuchElementException();
    }
    return unlinkFirst(element);

  }

  /**
   * Извлекает и удаляет первый элемент коллекции.
   *
   * @return удаленный элемент.
   */
  @Override
  public T poll() {
    final Node<T> f = first;
    return (f == null) ? null : unlinkFirst(f);
  }

  /**
   * Возвращает, но не удаляет первый элемент коллекции.
   *
   * @return первый элемент коллекции.
   */
  @Override
  public T element() {
    return getFirst();
  }

  /**
   * Возвращает, но не удаляет первый элемент коллекции.
   *
   * @return первый элемент коллекции или null, если коллекция пуста.
   */
  @Override
  public T peek() {
    final Node<T> f = first;
    return (f == null) ? null : f.item;
  }

  /**
   * Добавляет элемент в начало коллекции.
   *
   * @param element элемент.
   */
  @Override
  public void push(T element) {
    addFirst(element);
  }

  /**
   * Удаляет и возвращает первый элемент коллекции.
   *
   * @return первый элемент коллекции.
   */
  @Override
  public T pop() {
    return removeFirst();
  }

  /**
   * Возвращает количество элементов в двусвязном списке.
   *
   * @return количество элементов в двусвязном списке.
   */
  @Override
  public int size() {
    return size;
  }

  @Override
  public Object[] toArray(Object[] a) {
    return new Object[0];
  }

  @Override
  public Iterator descendingIterator() {
    return new DescendingIterator();
  }

  /**
   * Адаптер для предоставления нисходящих итераторов через ListItr.previous.
   */
  private class DescendingIterator implements Iterator<T> {
    private final ListItr itr = new ListItr(size());

    public boolean hasNext() {
      return itr.hasPrevious();
    }

    public T next() {
      return itr.previous();
    }

    public void remove() {
      itr.remove();
    }
  }

  /**
   * Возвращает ListIterator элементов в коллекции.
   *
   * @param i индекс первого элемента итератора.
   * @return ListIterator элементов в коллекции начиная с указанной позиции в списке.
   */
  @Override
  public ListIterator<T> listIterator(int i) {
    if (!(i >= 0 && i <= size)) {
      throw new IndexOutOfBoundsException("Index: " + i + ", Size: " + size);
    }
    return new ListItr(i);
  }

  /**
   * Превращает строковое представление объекта
   * в виде GSON в объект класса DoublyLinkedList.
   *
   * @param readJson строковое представление объекта в виде JSON.
   * @return объект класса DoublyLinkedList.
   */
  public DoublyLinkedList<T> listFromJson(String readJson) {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.fromJson(readJson, DoublyLinkedList.class);
  }


  /**
   * Класс, описывающий итератор по двусвязному списку.
   */
  private class ListItr implements ListIterator<T> {
    private Node<T> lastReturned;
    private Node<T> next;
    private int nextIndex;
    private int expectedModCount = modCount;

    ListItr(int i) {
      next = (i == size) ? null : node(i);
      nextIndex = i;
    }

    @Override
    public boolean hasNext() {
      return nextIndex < size;
    }

    @Override
    public T next() {
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (!hasNext()) {
        throw new NoSuchElementException();
      }

      lastReturned = next;
      next = next.next;
      nextIndex++;
      return lastReturned.item;
    }


    @Override
    public boolean hasPrevious() {
      return nextIndex > 0;
    }

    @Override
    public T previous() {
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (!hasPrevious()) {
        throw new NoSuchElementException();
      }

      lastReturned = next = (next == null) ? last : next.prev;
      nextIndex--;
      return lastReturned.item;
    }

    @Override
    public int nextIndex() {
      return nextIndex;
    }

    @Override
    public int previousIndex() {
      return nextIndex - 1;
    }

    @Override
    public void remove() {
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      if (lastReturned == null) {
        throw new IllegalStateException();
      }

      Node<T> lastNext = lastReturned.next;
      final Node<T> n = lastReturned.next;
      final Node<T> p = lastReturned.prev;

      if (p == null) {
        first = n;
      } else {
        p.next = n;
        lastReturned.prev = null;
      }

      if (n == null) {
        last = p;
      } else {
        n.prev = p;
        lastReturned.next = null;
      }

      lastReturned.item = null;
      size--;
      modCount++;
      if (next == lastReturned) {
        next = lastNext;
      } else {
        nextIndex--;
        lastReturned = null;
        expectedModCount++;
      }
    }

    @Override
    public void set(T element) {
      if (lastReturned == null) {
        throw new IllegalStateException();
      }
      if (modCount != expectedModCount) {
        throw new ConcurrentModificationException();
      }
      lastReturned.item = element;
    }

    @Override
    public void add(T element) {
      if (modCount != expectedModCount) {
        throw new ArrayIndexOutOfBoundsException();
      } else if (size == 0) {
        //Добавление нулевого элемента
        Node<T> newNode = new Node<>(element, null, null);
        first = newNode;
        last = newNode;
      } else if (first != null & nextIndex == size & size != 0 & size != 1) {
        //Добавление элемента в конец коллекции
        Node<T> newNode = new Node<>(element, last, null);
        last.prev = last;
        last = newNode;
        newNode.prev.next = newNode;
      } else if (size == 1) {
        //Добавление второго элемента list[1]
        Node<T> newNode = new Node<>(element, first, null);
        first.next = newNode;
        last = newNode;
      } else if (nextIndex > 0 && nextIndex < size) {
        //Добавление элемента в центр коллекции
        Node<T> newNode = new Node<>(element, null, null);
        Node<T> x = first;
        for (int j = 0; j < nextIndex; j++) {
          x = x.next;
        }
        newNode.next = x.next;
        newNode.prev = x.prev;
        x.next = newNode;
      }
      modCount++;
      size++;
    }
  }

  /**
   * Возвращает ненулевой узел по указанному индексу элемента.
   *
   * @param index индекс элемента.
   * @return ненулевой узел.
   */
  Node<T> node(int index) {
    Node<T> x;
    if (index < (size >> 1)) {
      x = first;
      for (int i = 0; i < index; i++) {
        x = x.next;
      }
    } else {
      x = last;
      for (int i = size - 1; i > index; i--) {
        x = x.prev;
      }
    }
    return x;
  }

  /**
   * Класс узла списка.
   *
   * @param <T> тип элемента списка.
   */
  public static class Node<T> implements Serializable {
    T item;
    Node<T> next;
    Node<T> prev;

    Node(T item, Node<T> prev, Node<T> next) {
      this.item = item;
      this.next = next;
      this.prev = prev;
    }
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(size);
    out.writeObject(first);
    out.writeObject(last);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    size = (int) in.readObject();
    first = (Node<T>) in.readObject();
    last = (Node<T>) in.readObject();
  }

  /**
   * Возвращает строковое представление объекта
   * в виде GSON.
   *
   * @return строковое представление объекта.
   */
  public String listToJson() {
    Gson gson = new GsonBuilder()
        .setPrettyPrinting()
        .create();
    return gson.toJson(this);
  }

  /**
   * Клонирует объект.
   *
   * @return склонированный объект.
   * @throws CloneNotSupportedException исключение при клонировании.
   */
  @Override
  public Object clone() throws CloneNotSupportedException {
    DoublyLinkedList<T> clone = (DoublyLinkedList<T>) super.clone();
    clone.first = null;
    clone.last = null;
    clone.size = 0;
    clone.modCount = 0;
    for (Node<T> x = first; x != null; x = x.next) {
      clone.add(x.item);
    }
    return clone;
  }
}
