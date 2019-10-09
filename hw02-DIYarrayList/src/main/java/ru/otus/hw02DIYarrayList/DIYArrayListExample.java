package ru.otus.hw02DIYarrayList;

import java.util.Collections;
import java.util.Comparator;

public class DIYArrayListExample {

    public static void main(String[] args) {
        //Написать свою реализацию ArrayList на основе массива.
        //Проверить, что на ней работают методы из java.util.Collections:
        DIYarrayList<String> destList = new DIYarrayList<>();

        //Collections.addAll(Collection<? super T> c, T... elements)
        Collections.addAll(destList, "1", null, "3", "4", "5", "6", null, "8", "9", "10", "11", "12", null, "14", "15", "16", "17", "18", "19", "20", "21", "22", "23");
        System.out.println(destList);
        Collections.addAll(destList, "24", null, "26", "27", "28");
        System.out.println(destList);

        System.out.println("Element at index " + 0 + " = \"" + destList.get(0) + "\"");
        System.out.println("Element at index " + 12 + " = \"" + destList.get(12) + "\"");
        System.out.println("Element at index " + 27 + " = \"" + destList.get(27) + "\"");

        System.out.println("list.set(1, \"2\") = " + destList.set(1, "2") + ", new value at index 1 = \"" + destList.get(1) + "\"");
        System.out.println("list.set(15, \"166\") = " + destList.set(15, "166") + ", new value at index 15 = \"" + destList.get(15) + "\"");
        System.out.println("list.set(26, \"277\") = " + destList.set(26, "277") + ", new value at index 26 = \"" + destList.get(26) + "\"");

        //Collections.static <T> void copy(List<? super T> dest, List<? extends T> src)
        DIYarrayList<String> srcList = new DIYarrayList<>();
        Collections.addAll(srcList, "01", null, "03", "04", "05", "06", null, "08", "09", "010", "011", "012", null, "014", "015", "016", "017", "018", "019", "020", "021", "022", "023");
        Collections.copy(destList, srcList);
        System.out.println("\n" + destList);

        //Collections.static <T> void sort(List<T> list, Comparator<? super T> c)
        Collections.sort(destList, (o1, o2) -> o1 == null ? -1 : o2 == null ? 1 : o1.compareTo(o2));
        System.out.println("\n" + destList);
    }

}
