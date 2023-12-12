import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.TestFactory;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Stream;

public class AbstractMultipleSampleDayTest<DAY extends AbstractMultiStepDay<STEP1, STEP2>, STEP1, STEP2> {

    Map<String, DAY> daysForFiles = new HashMap<>();
    private final Function<String, DAY> daySupplier;
    private final List<DayTestParam<STEP1, STEP2>> paramList;

    @SafeVarargs
    public AbstractMultipleSampleDayTest(Function<String, DAY> supplier,
                                         DayTestParam<STEP1, STEP2>... params) {
        this.daySupplier = supplier;
        this.paramList = Arrays.asList(params);
    }

    private DAY initDay(String filename) {
        DAY d = daySupplier.apply(filename);
        try {
            d.readFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return d;
    }

    private void step1Test(String fileName, STEP1 expected) {
        DAY day = daysForFiles.computeIfAbsent(fileName, this::initDay);
        STEP1 result = day.resultStep1();
        Assertions.assertEquals(expected, result);
    }


    private void step2Test(String fileName, STEP2 expected) {
        DAY day = daysForFiles.computeIfAbsent(fileName, this::initDay);
        STEP2 result = day.resultStep2();
        Assertions.assertEquals(expected, result);
    }

    @TestFactory
    @DisplayName("Running tests")
    Stream<DynamicTest> dynamicDay() {
        return
                Stream.concat(
                        paramList.stream()
                                .filter(param -> param.expected1().isPresent())
                                .map(param -> DynamicTest.dynamicTest("Step1 " + param.filename(), () -> step1Test(param.filename(), param.expected1().get())))
                        ,
                        paramList.stream()
                                .filter(param -> param.expected2().isPresent())
                                .map(param -> DynamicTest.dynamicTest("Step2 " + param.filename(), () -> step2Test(param.filename(), param.expected2().get())))
                );
    }

}
