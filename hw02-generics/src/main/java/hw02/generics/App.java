package hw02.generics;

import java.util.*;

public class App {
    private static String[] text;

    public static void main(String[] args) {
        testWithIntegers();
        System.out.println("############################################");
        testWithUUIDs();
        System.out.println("############################################");
        testWithStrings();
    }

    private static void testWithIntegers() {
        List<TestClass<Integer>> firstSource = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstSource.add(new TestClass<>(i));
        }
        System.out.println(String.format("First source = %s", firstSource.toString()));
        List<TestClass<Integer>> secondSource = new ArrayList<>();
        for (int i = 10; i < 25; i++) {
            secondSource.add(new TestClass<>(i));
        }
        System.out.println(String.format("Second source = %s", secondSource.toString()));
        List<TestClass<Integer>> target = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 30; i++) {
            target.add(new TestClass<>(30 + random.nextInt(70)));
        }
        System.out.println(String.format("Target before copying of the first source = %s", target.toString()));
        Collections.copy(target, firstSource);
        System.out.println(String.format("Target before copying of the second source = %s", target.toString()));
        Collections.copy(target, secondSource);
        System.out.println(String.format("Target after copying = %s", target.toString()));
        Collections.addAll(target,
                new TestClass<>(110), new TestClass<>(111), new TestClass<>(112), new TestClass<>(113));
        System.out.println(String.format("Target after copying and adding of additional elements = %s", target.toString()));
        Collections.sort(target, new TestClassComparator<>());
        System.out.println(String.format("Target after sorting = %s", target.toString()));
    }

    private static void testWithUUIDs() {
        List<TestClass<UUID>> firstSource = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstSource.add(new TestClass<>(UUID.randomUUID()));
        }
        System.out.println(String.format("First source = %s", firstSource.toString()));
        List<TestClass<UUID>> secondSource = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            secondSource.add(new TestClass<>(UUID.randomUUID()));
        }
        System.out.println(String.format("Second source = %s", secondSource.toString()));
        List<TestClass<UUID>> target = new ArrayList<>();
        for (int i = 0; i < 30; i++) {
            target.add(new TestClass<>(UUID.randomUUID()));
        }
        System.out.println(String.format("Target before copying of the first source = %s", target.toString()));
        Collections.copy(target, firstSource);
        System.out.println(String.format("Target before copying of the second source = %s", target.toString()));
        Collections.copy(target, secondSource);
        System.out.println(String.format("Target after copying = %s", target.toString()));
        Collections.addAll(target,
                new TestClass<>(UUID.randomUUID()), new TestClass<>(UUID.randomUUID()),
                new TestClass<>(UUID.randomUUID()), new TestClass<>(UUID.randomUUID()));
        System.out.println(String.format("Target after copying and adding of additional elements = %s", target.toString()));
        Collections.sort(target, new TestClassComparator<>());
        System.out.println(String.format("Target after sorting = %s", target.toString()));
    }

    private static void testWithStrings() {
        List<TestClass<String>> firstSource = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            firstSource.add(new TestClass<>(getText()[i]));
        }
        System.out.println(String.format("First source = %s", firstSource.toString()));
        List<TestClass<String>> secondSource = new ArrayList<>();
        for (int i = 5; i < 20; i++) {
            secondSource.add(new TestClass<>(getText()[i]));
        }
        System.out.println(String.format("Second source = %s", secondSource.toString()));
        List<TestClass<String>> target = new ArrayList<>();
        for (int i = 20; i < 40; i++) {
            target.add(new TestClass<>(getText()[i]));
        }
        System.out.println(String.format("Target before copying of the first source = %s", target.toString()));
        Collections.copy(target, firstSource);
        System.out.println(String.format("Target before copying of the second source = %s", target.toString()));
        Collections.copy(target, secondSource);
        System.out.println(String.format("Target after copying = %s", target.toString()));
        Collections.addAll(target, new TestClass<>("some"), new TestClass<>("additional"), new TestClass<>("text"));
        System.out.println(String.format("Target after copying and adding of additional elements = %s", target.toString()));
        Collections.sort(target, new TestClassComparator<>());
        System.out.println(String.format("Target after sorting = %s", target.toString()));
    }

    private static String[] getText() {
        if (text == null) {
            String rawText = "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Alias assumenda aut " +
                    "blanditiis commodi corporis cupiditate eius esse excepturi fugiat id in incidunt molestias " +
                    "neque nesciunt nihil obcaecati odit officia pariatur provident quam qui, quos sapiente sint " +
                    "ullam velit vitae voluptas.";
            text = rawText.split(" ");
        }
        return text;
    }
}
