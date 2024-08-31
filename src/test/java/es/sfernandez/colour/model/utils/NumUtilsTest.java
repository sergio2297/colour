package es.sfernandez.colour.model.utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.stream.Stream;

import static es.sfernandez.colour.model.utils.NumUtils.denormalize;
import static es.sfernandez.colour.model.utils.NumUtils.normalize;
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

}