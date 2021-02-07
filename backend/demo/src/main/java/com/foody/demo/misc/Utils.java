package com.foody.demo.misc;

import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class Utils {

    public static Iterable<Long> convert(long[] array) {
        return () -> Arrays.stream(array).iterator();
    }

    public static long[] convert(Integer[] array) {
        return Arrays.stream(array).mapToLong(i -> i).toArray();
    }

    public static <T> Stream<T> collectionStream(Collection<T> collection) {
        return collection == null || collection.isEmpty() ? Stream.empty() : collection.stream();
    }

}
