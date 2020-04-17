package com.hailtosg;

import java.util.stream.IntStream;

/**
 * HashMap с открытой адресацией для ключей типа
 * int и значений типа long. Не сохраняет порядок
 * добавления элемента. Гарантирует уникальность
 * ключей в колллекции. Хэш-функция реализована
 * двойныи хэшированием.
 * <p>
 * В коллекции особо обрабатываются ключ 0. Для него
 * созданы переменные: zeroValue, которая принимает
 * value по ключу 0 и флаг isZeroEmpty, указывающий,
 * что ключ со значением 0 был использован. Это
 * сделано для того, чтобы считать 0-ячейки массива
 * keys пустыми.
 *
 * @author Egor Zaika
 */

public class OpenHashMap {
    private static final float LOAD_FACTOR = 0.67f;
    private static final int INITIAL_CAPACITY = 15;

    private int size = 0;

    private boolean isZeroEmpty;
    private long zeroValue;

    private int[] keys;
    private long[] values;

    public OpenHashMap() {
        keys = new int[INITIAL_CAPACITY];
        values = new long[INITIAL_CAPACITY];
        zeroValue = 0;
        isZeroEmpty = true;
    }

    /**
     * Возвращает количество добавленных пар
     */
    public int size() {
        return size;
    }

    /**
     * Признак того, что коллекция пуста
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * С помошью хэш-метода ищет элемент в массиве. Если
     * такого ключа еще нет, ключ и значение кладутся в
     * массивы (кроме пары с ключом 0, которая обрабатывается
     * отдельно), если есть - старое значение перезаписывается
     */

    public void put(int key, long value) {

        if (key == 0) {
            if (isZeroEmpty) {
                isZeroEmpty = false;
                size++;
            }
            zeroValue = value;
        } else {
            final int index = getIndex(key);
            values[index] = value;

            if (keys[index] == 0) {
                keys[index] = key;
                size++;
            }
        }

        if (size >= keys.length * LOAD_FACTOR) {
            increaseCapacityAndRehash();
        }
    }

    /**
     * Если элемент есть, возвращает его значение,
     * если элемент отсутствует, возвращает null.
     */

    public Long get(int key) {
        if (key == 0) {
            return isZeroEmpty ? null : zeroValue;
        }

        final int index = getIndex(key);
        return keys[index] == 0 ? null : values[index];
    }

    private int getIndex(int key) {
        int i = Math.abs(key % keys.length);
        int k = hashTwo(key);
        while (keys[i] != 0) {
            if (keys[i] == key) {
                return i;
            } else {
                k = hashTwo(k);
                i = (i + k) % keys.length;
            }
        }
        return i;
    }

    private int hashTwo(int key) {
        return Math.abs(key % 97) + 1;
    }

    private void increaseCapacityAndRehash() {
        int newSize = keys.length * 2 + 1;
        int[] oldKeys = keys;
        long[] oldValues = values;
        keys = new int[newSize];
        values = new long[newSize];

        IntStream.range(0, oldKeys.length)
                .filter(i -> oldKeys[i] != 0)
                .forEach(i -> {
                    final int newIndex = getIndex(oldKeys[i]);
                    keys[newIndex] = oldKeys[i];
                    values[newIndex] = oldValues[i];
                });
    }
}