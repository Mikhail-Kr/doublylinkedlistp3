import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.ListIterator;
import list.DoublyLinkedList;

public class Main {
  /**
   * Проверяет работу классов, созданных
   * в рамках выполнения задания.
   *
   * @param args аргументы командной строки.
   */
  public static void main(String[] args) {
    DoublyLinkedList<String> list = new DoublyLinkedList<>();
    list.add("a1");
    list.add("b2");
    list.add("c3");
    list.add(1, "d4");
    list.add("e5");
    list.set(4, "a6");
    list.add("f5");
    list.add(3, "t1000");

    //Проверка позитивного использования DoublyLinkedList.size()
    if (list.size() != 7) {
      System.err.println("Некорректная работа позитивного использования DoublyLinkedList.size()");
    }

    //Проверка негативного использования DoublyLinkedList.size()
    boolean thrown = false;
    try {
      if (list.size() != 8) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.out.println("Некорректная работа метода size()");
    }

    //Проверка позитивного использования DoublyLinkedList.add()
    if ((!list.get(0).equals("a1"))
        || (!list.get(1).equals("b2"))
        || (!list.get(2).equals("d4"))
        || (!list.get(6).equals("f5"))) {
      System.err.println("Некорректная работа позитивного использования DoublyLinkedList.add()");
    }


    //Проверка негативного использования DoublyLinkedList.add()
    thrown = false;
    try {
      if ((!list.get(0).equals("b2"))
          || (!list.get(1).equals("c3"))
          || (!list.get(2).equals("f5"))
          || (!list.get(6).equals("t1000"))) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода add");
    }

    //Позитивный работы метода clone.
    DoublyLinkedList<String> list2;
    try {
      list2 = (DoublyLinkedList<String>) list.clone();
      if (!list2.equals(list)) {
        System.out.println("Некорректная работа метода clone");
      }
    } catch (CloneNotSupportedException e) {
      e.printStackTrace();
    }

    //Позитивная проверка работы метода listIterator.
    thrown = false;
    ListIterator<String> listIt;
    try {
      listIt = list.listIterator(0);
    } catch (IndexOutOfBoundsException ex) {
      thrown = true;
    }
    if (thrown) {
      System.out.println("Некорректная работа метода listIterator");
    }

    //Негативная проверка работы метода listIterator.
    thrown = false;
    try {
      listIt = list.listIterator(8);
    } catch (IndexOutOfBoundsException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.out.println("Некорректная работа метода listIterator");
    }

    //Позитивная проверка сериализации/десериализации через GSON
    DoublyLinkedList<String> testGson = new DoublyLinkedList<>();
    testGson.add("a1");
    testGson.add("b2");
    testGson.add("c3");

    try {
      ObjectOutputStream objectOutputStream =
          new ObjectOutputStream(new FileOutputStream("test.json"));
      objectOutputStream.writeObject(testGson.listToJson());
    } catch (IOException e) {
      e.printStackTrace();
    }

    DoublyLinkedList<String> readList = null;
    try {
      ObjectInputStream objectInputStream =
          new ObjectInputStream(new FileInputStream("test.json"));
      readList =
          testGson.listFromJson((String) objectInputStream.readObject());
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    if (!testGson.equals(readList)) {
      System.err.println("Некорректная работа сериализации/десериализации через GSON");
    }

    //Позитивная проверка сериализации/десериализации через Externalize
    try {
      ObjectOutputStream oos =
          new ObjectOutputStream(new FileOutputStream("test.txt"));
      oos.writeObject(testGson);
    } catch (IOException e) {
      e.printStackTrace();
    }

    DoublyLinkedList<String> readList2 = null;
    try {
      ObjectInputStream ois =
          new ObjectInputStream(new FileInputStream("test.txt"));
      readList2 =
          (DoublyLinkedList<String>) ois.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    if (!testGson.equals(readList2)) {
      System.err.println("Некорректная работа сериализации/десериализации через Externalize");
    }

    //Позитивный случай метода addFirst
    list.addFirst("0");
    if (!list.get(0).equals("0")) {
      System.err.println("Некорректная работа метода addFirst");
    }

    //Негативный случай метода addFirst
    try {
      if (!list.get(0).equals("a1")) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода addFirst");
    }

    //Позитивный случай метода addLast
    list.addLast("tlast");
    if (!list.get(list.size() - 1).equals("tlast")) {
      System.err.println("Некорректная работа метода addLast");
    }

    //Негативный случай метода addLast
    thrown = false;
    try {
      if (!list.get(list.size() - 1).equals("f5")) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода addLast");
    }

    //Позитивный случай метода offerFirst
    list.offerFirst("a0");
    if (!list.get(0).equals("a0")) {
      System.err.println("Некорректная работа метода offerFirst");
    }

    //Негативный случай метода offerFirst
    thrown = false;
    try {
      if (!list.get(0).equals("0")) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода offerFirst");
    }

    //Позитивный случай метода offerLast
    list.offerLast("offerlast");
    if (!list.get(list.size() - 1).equals("offerlast")) {
      System.err.println("Некорректная работа метода offerLast");
    }

    //Негативный случай метода offerLast
    thrown = false;
    try {
      if (!list.get(list.size() - 1).equals("tlast")) {
        throw new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода offerLast");
    }

    //Позитивный случай метода removeFirst
    if (!list.removeFirst().equals("a0")) {
      System.err.println("Некорректная работа метода removeFirst");
    }

    //Негативный случай метода removeFirst
    thrown = false;
    try {
      if (!list.removeFirst().equals("a1")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода removeFirst");
    }

    //Позитивный случай метода removeLast
    if (!list.removeLast().equals("offerlast")) {
      System.err.println("Некорректная работа метода removeLast");
    }

    //Негативный случай метода removeLast
    thrown = false;
    try {
      if (!list.removeFirst().equals("tlast")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода removeLast");
    }

    //Позитивный случай метода pollFirst
    if (list.pollFirst() == null) {
      System.err.println("Некорректная работа метода pollFirst");
    }

    //Позитивный случай метода pollFirst
    list.addFirst("2");
    if (!list.pollFirst().equals("2")) {
      System.err.println("Некорректная работа метода pollFirst");
    }

    //Негативный случай метода pollFirst
    thrown = false;
    try {
      if (list.pollFirst() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода pollFirst");
    }

    //Позитивный случай метода pollLast
    list.addLast("pollLast");
    if (!list.pollLast().equals("pollLast")) {
      System.err.println("Некорректная работа метода pollLast");
    }

    //Позитивный случай метода pollLast
    if (list.pollLast() == null) {
      System.err.println("Некорректная работа метода pollLast");
    }

    //Негативный случай метода pollLast
    list.addLast("pollLast");
    thrown = false;
    try {
      if (list.pollLast() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода pollLast");
    }

    //Позитивный случай метода getFirst
    list.addFirst("getFirst");
    if (!list.getFirst().equals("getFirst")) {
      System.err.println("Некорректная работа метода getFirst");
    }

    //Негативный случай метода getFirst
    thrown = false;
    try {
      if (list.getFirst() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода getFirst");
    }

    //Позитивный случай метода getLast
    list.add("getLast");
    if (list.getLast() != "getLast") {
      System.err.println("Некорректная работа метода getLast");
    }

    //Негативный случай метода getLast
    thrown = false;
    try {
      if (list.getLast() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода getLast");
    }

    //Позитивный случай метода peekFirst
    list.addFirst("peekF");
    if (!list.peekFirst().equals("peekF")) {
      System.err.println("Некорректная работа метода peekFirst");
    }

    //Негативный случай метода peekFirst
    thrown = false;
    try {
      if (list.peekFirst() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода peekFirst");
    }

    //Позитивный случай метода peekFirst
    int count = list.size();
    for (int j = 0; j < count; j++) {
      list.remove();
    }
    if (list.peekFirst() != null) {
      System.err.println("Некорректная работа метода peekFirst");
    }

    //Позитивный случай метода peekLast
    DoublyLinkedList<String> list3 = new DoublyLinkedList<>();
    list3.add("pl");
    list3.add("pl2");
    if (list3.peekLast() != "pl2") {
      System.err.println("Некорректная работа метода peekLast");
    }

    //Позитивный случай метода peekLast
    list3.removeFirst();
    list3.removeFirst();
    if (list3.peekLast() != null) {
      System.err.println("Некорректная работа метода peekLast");
    }

    //Негативный случай метода peekLast
    thrown = false;
    try {
      if (list3.peekLast() == null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода peekLast");
    }

    //Позитивный случай метода removeFirstOccurrence
    list3.addFirst("a1");
    list3.addFirst("b2");
    list3.addFirst("c3");
    list3.addFirst("c4");
    if (!list3.removeFirstOccurrence("c3")) {
      System.err.println("Некорректная работа метода removeFirstOccurrence");
    }

    //Негативный случай метода removeFirstOccurrence
    thrown = false;
    try {
      if (!list3.removeFirstOccurrence("d5")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода removeFirstOccurrence");
    }

    //Позитивный случай метода removeLastOccurrence
    list3.addFirst("t1000");
    if (!list3.removeLastOccurrence("t1000")) {
      System.err.println("Некорректная работа метода removeLastOccurrence");
    }

    //Негативный случай метода removeLastOccurrence
    thrown = false;
    try {
      if (!list3.removeLastOccurrence("t1000")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода removeLastOccurrence");
    }

    //Позитивный случай метода offer
    DoublyLinkedList<String> list4 = new DoublyLinkedList<>();
    list4.offer("first");
    list4.offer("second");
    if (!list4.get(list4.size() - 1).equals("second")) {
      System.err.println("Некорректная работа метода offer");
    }

    //Негативный случай метода offer
    thrown = false;
    try {
      if (!list4.get(list4.size() - 1).equals("first")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода offer");
    }

    //Позитивный случай метода remove
    if (!list4.remove().equals("first")) {
      System.err.println("Некорректная работа метода remove");
    }

    //Негативный случай метода remove
    thrown = false;
    try {
      if (list4.remove() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода remove");
    }

    //Позитивный случай метода poll
    list4.addFirst("a");
    list4.addFirst("b");
    list4.addFirst("c");
    list4.addFirst("d");
    while (list4.size() > 0) {
      list4.remove();
    }

    if (list4.poll() != null) {
      System.err.println("Некорректная работа метода poll");
    }

    //Негативный случай метода poll
    thrown = false;
    list4.addFirst("1");
    list4.addFirst("2");
    list4.addFirst("3");
    try {
      if (!list4.poll().equals("1")) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода poll");
    }

    //Позитивный случай метода element
    if (!list4.element().equals("2")) {
      System.err.println("Некорректная работа метода element");
    }

    //Негативный случай метода element
    thrown = false;
    try {
      if (list4.element() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода element");
    }

    //Позитивный случай метода peek
    if (!list4.peek().equals("2")) {
      System.err.println("Некорректная работа метода peek");
    }

    //Позитивный случай метода peek
    list4.removeFirst();
    list4.removeFirst();
    if (list4.peek() != null) {
      System.err.println("Некорректная работа метода peek");
    }

    //Негативный случай метода peek
    thrown = false;
    try {
      if (list4.peek() == null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода peek");
    }

    //Позитивный случай метода push
    list4.push("0");
    if (!list4.get(0).equals("0")) {
      System.err.println("Некорректная работа метода push");
    }

    //Негативный случай метода push
    thrown = false;
    try {
      if (list4.get(0) != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода push");
    }

    //Позитивный случай метода pop
    list4.add("1");
    list4.add("2");
    if (!list4.pop().equals("0")) {
      System.err.println("Некорректная работа метода pop");
    }

    //Негативный случай метода pop
    thrown = false;
    try {
      if (list4.pop() != null) {
        throw  new IllegalArgumentException();
      }
    } catch (IllegalArgumentException ex) {
      thrown = true;
    }
    if (!thrown) {
      System.err.println("Некорректная работа метода pop");
    }

    // Проверка позитивного использования метода descendingIterator
    Iterator iterator;
    thrown = false;
    try {
      iterator = list4.descendingIterator();
    } catch (IndexOutOfBoundsException e) {
      thrown = true;
    }
    if (thrown) {
      System.err.println("Некорректная работа позитивного использования метода descendingIterator");
    }
  }
}
