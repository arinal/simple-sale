package com.salestock.common;

import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class IterableCommon {
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        return StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterable.iterator(), Spliterator.ORDERED), false);
    }
}
