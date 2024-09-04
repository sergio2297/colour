package es.sfernandez.colour.model.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static es.sfernandez.colour.model.utils.NumUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class NumUtilsTest {

    private record DenormalizeTestCase(int min, int max, int number) {}

    private static Stream<DenormalizeTestCase> denormalizeTestCases() {
        Random rnd = new Random();
        return Stream
                .generate(() -> rnd.nextInt(-100, 100))
                .limit(100)
                .map(min -> {
                    int max = rnd.nextInt(min + 1, 102);
                    int number = rnd.nextInt(min, max + 1);
                    return new DenormalizeTestCase(min, max, number);
                });
    }

    @ParameterizedTest(name = "{0}")
    @MethodSource(value = "denormalizeTestCases")
    void denormalize_numberPreviouslyNormalized_returnsOriginalNumberTest(DenormalizeTestCase testCase) {
        assertThat(
                denormalize(testCase.min, testCase.max,
                    normalize(testCase.min, testCase.max, testCase.number)
                )
        ).isEqualTo(testCase.number);
    }

    @Test
    void normalize_withSameMinAndMax_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> normalize(10, 10, 10));
    }

    @Test
    void denormalize_withSameMinAndMax_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> denormalize(10, 10, 10f));
    }

    @Test
    void denormalize_valueOutOfMinAndMaxRange_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, () -> denormalize(10, 20, 200f));
    }

    @Test
    void min_ofNullArray_throwsIllegalArgumentExceptionTest() {
        float[] numbers = null;
        assertThrows(IllegalArgumentException.class, () -> min(numbers));
    }

    @Test
    void min_ofEmptyArray_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, NumUtils::min);
    }

    @Test
    void max_ofNullArray_throwsIllegalArgumentExceptionTest() {
        assertThrows(IllegalArgumentException.class, NumUtils::max);
    }

}