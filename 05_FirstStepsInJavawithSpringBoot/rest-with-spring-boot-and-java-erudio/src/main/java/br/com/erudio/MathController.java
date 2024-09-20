package br.com.erudio;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

@RestController
public class MathController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    // Mapear uma requisição para um metodo.
    @RequestMapping(value = "/sum/{numberOne}/{numberTwo}",     // Adicione esta anotação para mapear a URL
    method=RequestMethod.GET)
    public Double sum(
    @PathVariable(value = "name", defaultValue = "World")
    String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }
}
