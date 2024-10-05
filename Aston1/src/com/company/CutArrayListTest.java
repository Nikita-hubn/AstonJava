package com.company;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.platform.commons.util.StringUtils;

import java.util.Comparator;

class CutArrayListTest {
    private CutArrayList<String> testStringList;
    private CutArrayList<Integer> testIntegerList;

    @BeforeEach
    void setUp() {
        testStringList = new CutArrayList<>();
        testIntegerList = new CutArrayList<>();
    }

    @AfterEach
    void tearDown() {
        testStringList = null;
        testIntegerList = null;
    }

    @DisplayName("Add elements to List")
    @ParameterizedTest
    @CsvSource(value = {
            "3; '[1, 2, 3]'; '1,2,3'",
            "4; '[4, 3, 2, 1]'; '4,3,2,1'"
    }, delimiter = ';'
    )
    void add(int expectedCount, String expectedToString, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
            testIntegerList.add(Integer.parseInt(value));
        }
        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());

        Assertions.assertEquals(expectedCount, testIntegerList.size());
        Assertions.assertEquals(expectedToString, testIntegerList.toString());
    }



    @DisplayName("Insert with index elements to List")
    @ParameterizedTest
    @CsvSource(value = {
            "4; '[0, 1, 2, 3]';    0; 0; '1,2,3'", // at start
            "4; '[1, 2, 0, 3]';    2; 0; '1,2,3'", // into middle
            "5; '[4, 3, 2, 1, 0]'; 4; 0; '4,3,2,1'" // into end
    }, delimiter = ';'
    )
    void insert(int expectedCount, String expectedToString, int index, String element, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
            testIntegerList.add(Integer.parseInt(value));
        }
        testStringList.addToIndex(index, element);
        testIntegerList.addToIndex(index, Integer.parseInt(element));

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());

        Assertions.assertEquals(expectedCount, testIntegerList.size());
        Assertions.assertEquals(expectedToString, testIntegerList.toString());
    }


    @DisplayName("Set elements in List")
    @ParameterizedTest
    @CsvSource(value = {
            "3; '[1, -1, 3]'; 1; -1; '1,2,3'",
            "4; '[4, 3, -1, 1]'; 2; -1; '4,3,2,1'"
    }, delimiter = ';'
    )
    void set(int expectedCount, String expectedToString, int index, String element, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        testStringList.set(index, element);

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());
    }

    @DisplayName("Remove elements from List by index")
    @ParameterizedTest
    @CsvSource(value = {
            "2; 2; '[1, 3]'; 1; '1,2,3'",
            "3; 3; '[4, 2, 1]'; 1; '4,3,2,1'"
    }, delimiter = ';'
    )
    void removeByIndex(int expectedCount, String expectedElement, String expectedToString, int index, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        String removedElement = testStringList.remove(index);

        Assertions.assertEquals(expectedCount, testStringList.size());
        Assertions.assertEquals(expectedToString, testStringList.toString());
        Assertions.assertEquals(expectedElement, removedElement);
    }


    @DisplayName("Get elements from List by index")
    @ParameterizedTest
    @CsvSource(value = {
            "0; 1; '1,2,3'",
            "2; 2; '4,3,2,1'",
            "3; 4; '1,2,3,4'"
    }, delimiter = ';'
    )
    void get(int index, String expectedValue, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        for (int i = 0; i < 3; i++) {
            String element = testStringList.get(index);
            Assertions.assertEquals(expectedValue, element);
        }
    }

    @DisplayName("Clear list")
    @ParameterizedTest
    @CsvSource(value = {
            "0; 0; ''",
            "3; 0; '1,2,3'",
            "4; 0; '4,3,2,1'"
    }, delimiter = ';'
    )
    void clear(int expectedSizeBefore, int expectedSizeAfter, String values) {
        for (int i = 0; i < 2; i++) {
            if (StringUtils.isNotBlank(values)) {
                for (String value : values.split(",")) {
                    testStringList.add(value);
                }
            }
            Assertions.assertEquals(expectedSizeBefore, testStringList.size());
            testStringList.clear();
            Assertions.assertEquals(expectedSizeAfter, testStringList.size());
        }
    }

    @DisplayName("Size of list in action")
    @ParameterizedTest
    @CsvSource(value = {
            "1; 0; 1; 1; 2; '1'",
            "3; 2; 3; 3; 4; '1,2,3'"
    }, delimiter = ';'
    )
    void size(int expectedAtStart, int expectedAfterRemove, int expectedAfterAdd, int expectedAfterSet, int expectedAfterInsert, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }
        Assertions.assertEquals(expectedAtStart, testStringList.size());

        testStringList.remove(0);
        Assertions.assertEquals(expectedAfterRemove, testStringList.size());

        testStringList.add("new Element");
        Assertions.assertEquals(expectedAfterAdd, testStringList.size());

        testStringList.set(0, "update Element");
        Assertions.assertEquals(expectedAfterSet, testStringList.size());

        testStringList.addToIndex(0, "inserted Element");
        Assertions.assertEquals(expectedAfterInsert, testStringList.size());
    }

    @DisplayName("Sort string list")
    @ParameterizedTest
    @CsvSource(value = {
            "'[0, 1, 2, 3, 4, 5]'; '[5, 4, 3, 2, 1, 0]'; '1,0,4,5,3,2'",
            "'[e, ee, qe, qq, ww]'; '[ww, qq, qe, ee, e]'; 'ee,qe,qq,ww,e'",
            "'[1, 10, 2, 20, 3, 30]'; '[30, 3, 20, 2, 10, 1]'; '3,20,1,2,30,10'"
    }, delimiter = ';'
    )
    void sortString(String expectedNaturalOrderToString, String expectedReverseOrderToString, String values) {
        for (String value : values.split(",")) {
            testStringList.add(value);
        }

        testStringList.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expectedNaturalOrderToString, testStringList.toString());

        testStringList.sort(Comparator.reverseOrder());
        Assertions.assertEquals(expectedReverseOrderToString, testStringList.toString());
    }

    @DisplayName("Sort integer list")
    @ParameterizedTest
    @CsvSource(value = {
            "'[0, 1, 2, 3, 4, 5]'; '[5, 4, 3, 2, 1, 0]'; '1,0,4,5,3,2'",
            "'[1, 2, 3, 10, 20, 30]'; '[30, 20, 10, 3, 2, 1]'; '3,20,1,2,30,10'"
    }, delimiter = ';'
    )
    void sortInteger(String expectedNaturalOrderToString, String expectedReverseOrderToString, String values) {
        for (String value : values.split(",")) {
            testIntegerList.add(Integer.parseInt(value));
        }

        testIntegerList.sort(Comparator.naturalOrder());
        Assertions.assertEquals(expectedNaturalOrderToString, testIntegerList.toString());

        testIntegerList.sort(Comparator.reverseOrder());
        Assertions.assertEquals(expectedReverseOrderToString, testIntegerList.toString());
    }

    @DisplayName("IndexOutOfBoundsException then set wrong index")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenSet(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.set(index, "exception");
                },
                "IndexOutOfBoundsException was expected");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());

    }

    @DisplayName("IndexOutOfBoundsException then insert at the wrong index")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenInsert(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.addToIndex(index, "exception");
                },
                "IndexOutOfBoundsException was expected");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());
    }

    @DisplayName("IndexOutOfBoundsException then remove at the wrong index")
    @ParameterizedTest
    @CsvSource(value = {
            "-1; Index: -1, Size: 0",
            "500; Index: 500, Size: 0"
    }, delimiter = ';'
    )
    void testIndexOutOfBoundsExceptionThenRemove(int index, String exceptedMessage) {
        IndexOutOfBoundsException exception = Assertions.assertThrows(
                IndexOutOfBoundsException.class,
                () -> {
                    testStringList.addToIndex(index, "exception");
                },
                "IndexOutOfBoundsException was expected");

        Assertions.assertEquals(exceptedMessage, exception.getMessage());
    }

}