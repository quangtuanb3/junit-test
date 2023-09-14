package com.example.demojunittesting;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


class DemoJUnitTestingApplicationTests {
    Calculator testCalculator = new Calculator();

    @Test
    void canAddTwoNumbers() {
        //given
        int num1 = 5;
        int num2 = 10;

        //when
       int result =  testCalculator.add(num1, num2);

       //assert
        int expected = 15;
        assertThat(result).isEqualTo(expected);

    }

    static class Calculator {
        int add(int a, int b) {
            return a - b;
        }
    }

}
