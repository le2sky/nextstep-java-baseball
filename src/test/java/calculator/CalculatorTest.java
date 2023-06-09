package calculator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

class CalculatorTest {

    private static Calculator calculator;

    private static void assertCalculateWithGivenExpression(String expression, int expected) {
        assertThat(calculator.calculate(expression)).isEqualTo(expected);
    }

    @BeforeAll
    static void beforeAll() {
        calculator = new Calculator(new ExpressionParser(new OperationFactory()));
    }

    @ParameterizedTest
    @CsvSource(value = {"1 + 1:2", "1 + 1 + 1:3", "2 + 3 + 4 + 5:14"}, delimiter = ':')
    void calculate_더하기(String source, int expected) {
        assertCalculateWithGivenExpression(source, expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1 - 1:0", "3 - 2 - 2:-1", "10 - 3 - 4 - 1:2"}, delimiter = ':')
    void calculate_빼기(String source, int expected) {
        assertCalculateWithGivenExpression(source, expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1 * 1:1", "3 * 2 * 2:12", "10 * 3 * 4:120"}, delimiter = ':')
    void calculate_곱하기(String source, int expected) {
        assertCalculateWithGivenExpression(source, expected);
    }

    @ParameterizedTest
    @CsvSource(value = {"1 / 1:1", "6 / 2:3", "12 / 3:4"}, delimiter = ':')
    void calculate_피연산자_두개_나누기(String source, int expected) {
        assertCalculateWithGivenExpression(source, expected);
    }

    @Test
    void calculate_더하기_빼기_복합식() {
        assertCalculateWithGivenExpression("9 + 3 - 7", 5);
    }

    @Test
    void calculate_더하기_빼기_곱하기_복합식() {
        assertCalculateWithGivenExpression("9 + 3 * 2 * 3 - 1", 71);
    }

    @Test
    void calculate_더하기_빼기_곱하기_나누기_복합식() {
        assertCalculateWithGivenExpression("9 + 3 * 2 / 2", 12);
    }

    @ParameterizedTest
    @ValueSource(strings = {"+ ", "-", "*", "/", " +", "* -", "+ "})
    void calculate_expression_에_피연산자가_없다면_예외를_발생한다(String source) {
        assertThatThrownBy(() -> {
            assertCalculateWithGivenExpression(source, 12);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @ValueSource(strings = {"1 1", "2 3", "1 2", "1 2 3"})
    void calculate_expression_에_연산자가_없다면_예외를_발생한다(String source) {
        assertThatThrownBy(() -> {
            assertCalculateWithGivenExpression(source, 12);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void calculate_expression_에_null이_들어오면_예외를_발생한다() {
        assertThatThrownBy(() -> {
            assertCalculateWithGivenExpression(null, 12);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void calculate_expression_에_빈문자열이_들어오면_예외를_발생한다() {
        assertThatThrownBy(() -> {
            assertCalculateWithGivenExpression("", 12);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void calculate_expression에_공백을_생략해도_정상적으로_계산된다() {
        assertCalculateWithGivenExpression("1 + 4 -2 *3", 9);
    }

    @Test
    void calculate_expression에_숫자_연산자가_아닌값이_포함되어있으면_예외를_발생한다() {
        assertThatThrownBy(() -> {
            assertCalculateWithGivenExpression("a + b + 1 * 2", 12);
        }).isInstanceOf(IllegalArgumentException.class);
    }
}
