package com.hailtosg;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OpenHashMapTest {
    private OpenHashMap map;

    @BeforeEach
    void setUp() {
        this.map = new OpenHashMap();
    }

    @Test
    @DisplayName("isEmpty() и size() возвращает true для пустой map")
    public void isEmptyForNewMap() {
        assertTrue(map.isEmpty());
        assertEquals(0, map.size());
    }

    @Test
    @DisplayName("isEmpty() возвращает false, если map не пуста")
    public void isEmptyFalseWhenElementsExist() {
        map.put(1, 5L);
        assertFalse(map.isEmpty());
    }

    @Test
    @DisplayName("size увеличивается при добавлении новых элементов")
    public void sizeIncrementsWhenAddingElements() {
        map.put(2, 5L);
        assertEquals(1, map.size());

        map.put(3, 6L);
        assertEquals(2, map.size());
    }

    @Test
    @DisplayName("size не увеличивается при добавлении с уже существующим ключом")
    public void keepsSameSizeWithDuplicateKey() {
        map.put(1, 5L);
        assertEquals(1, map.size());

        map.put(1, 6L);
        assertNotEquals(2, map.size());
    }

    @Test
    @DisplayName("map возвращает корректные значения по ключам")
    public void getReturnsCorrectValues() {
        map.put(0, 5L);
        map.put(1, 6L);
        map.put(0, 245L);
        map.put(615, 234L);
        map.put(15, -400L);
        map.put(232, 0L);
        map.put(22323, Long.MAX_VALUE);
        map.put(Integer.MAX_VALUE, Long.MAX_VALUE);
        map.put(Integer.MIN_VALUE, Long.MIN_VALUE);

        assertEquals(245L, map.get(0));
        assertEquals(6L, map.get(1));
        assertEquals(234L, map.get(615));
        assertEquals(-400L, map.get(15));
        assertEquals(0L, map.get(232));
        assertEquals(Long.MAX_VALUE, map.get(22323));
        assertEquals(Long.MAX_VALUE, map.get(Integer.MAX_VALUE));
        assertEquals(Long.MIN_VALUE, map.get(Integer.MIN_VALUE));

        for (int i = 1; i <=10000000 ; i = -(i*2)){
            map.put(i, i*2);
        }

        for (int i = 1; i <=10000000 ; i = -(i*2)){
            assertEquals(i*2, map.get(i));
        }
    }

    @Test
    @DisplayName("возвращает null, если ключ не найден")
    public void returnsNullIfKeyDoesNotExist() {
        assertNull(map.get(-321));
        map.put(-321, 123L);
        assertNotNull(map.get(-321));
    }

    @Test
    @DisplayName("заменяет значение, если положить тот же самый ключ с другим значением")
    public void replacesValueWithSameKey() {
        map.put(100, 5L);
        map.put(100, 6L);
        assertNotEquals(5L, map.get(100));
        assertEquals(6L, map.get(100));
    }

    @Test
    @DisplayName("Принимает отрицательные и положительные ключи корректно")
    public void positiveAndNegativeKeysAreDifferent() {
        map.put(-100, 5);
        map.put(100, 6);
        assertNotEquals(map.get(100), map.get(-100));
    }

    @Test
    @DisplayName("может принимать 0 в качестве ключа и возвращать по нему значение")
    public void zeroAsKey() {
        map.put(0, -245);
        map.put(0, 245);
        assertEquals(245, map.get(0));
    }
}

