package br.com.erudio.service;

import br.com.erudio.exceptions.UnsupportedMathOperationException;
import org.springframework.stereotype.Service;

@Service
public class MathService {

    public Double sum(String numberOne, String numberTwo, String numberThree, String numberFour) {
        return convertToDouble(numberOne) + convertToDouble(numberTwo) +
            convertToDouble(numberThree) + convertToDouble(numberFour);
    }

    public Double subtract(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) - convertToDouble(numberTwo);
    }

    public Double multiply(String numberOne, String numberTwo) {
        return convertToDouble(numberOne) * convertToDouble(numberTwo);
    }

    public Double divide(String numberOne, String numberTwo) {
        if (convertToDouble(numberTwo) == 0) {
            throw new UnsupportedMathOperationException("Cannot divide by zero");
        }
        return convertToDouble(numberOne) / convertToDouble(numberTwo);
    }

    public Double average(String numberOne, String numberTwo, String numberThree, String numberFour) {
        return sum(numberOne, numberTwo, numberThree, numberFour) / 4;
    }

    public Double sqrt(String number) {
        return Math.sqrt(convertToDouble(number));
    }

    private Double convertToDouble(String strNumber) {
        try {
            return Double.parseDouble(strNumber);
        } catch (NumberFormatException e) {
            throw new UnsupportedMathOperationException("Please provide a numeric value");
        }
    }
}
