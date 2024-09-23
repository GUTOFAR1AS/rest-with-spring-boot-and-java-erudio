package br.com.erudio.controller;

import br.com.erudio.service.MathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/math")
public class MathController {

    @Autowired
    private MathService mathService;

    @GetMapping("/somar/{numberOne}/{numberTwo}/{numberThree}/{numberFour}")
    public Double sum(
        @PathVariable String numberOne,
        @PathVariable String numberTwo,
        @PathVariable String numberThree,
        @PathVariable String numberFour) throws Exception {
        return mathService.sum(numberOne, numberTwo, numberThree, numberFour);
    }

    @GetMapping("/subtrair/{numberOne}/{numberTwo}")
    public Double subtract(
        @PathVariable String numberOne,
        @PathVariable String numberTwo) throws Exception {
        return mathService.subtract(numberOne, numberTwo);
    }

    @GetMapping("/multiplicar/{numberOne}/{numberTwo}")
    public Double multiply(
        @PathVariable String numberOne,
        @PathVariable String numberTwo) throws Exception {
        return mathService.multiply(numberOne, numberTwo);
    }

    @GetMapping("/dividir/{numberOne}/{numberTwo}")
    public Double divide(
        @PathVariable String numberOne,
        @PathVariable String numberTwo) throws Exception {
        return mathService.divide(numberOne, numberTwo);
    }

    @GetMapping("/media/{numberOne}/{numberTwo}/{numberThree}/{numberFour}")
    public Double average(
        @PathVariable String numberOne,
        @PathVariable String numberTwo,
        @PathVariable String numberThree,
        @PathVariable String numberFour) throws Exception {
        return mathService.average(numberOne, numberTwo, numberThree, numberFour);
    }

    @GetMapping("/raiz/{number}")
    public Double sqrt(@PathVariable String number) throws Exception {
        return mathService.sqrt(number);
    }
}
