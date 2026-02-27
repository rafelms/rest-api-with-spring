package br.com.rafelms.rest_with_spring.controllers;

import br.com.rafelms.rest_with_spring.exception.UnsupportedMathOperationException;
import br.com.rafelms.rest_with_spring.operations.NumberConvert;
import br.com.rafelms.rest_with_spring.operations.SimpleMath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static br.com.rafelms.rest_with_spring.operations.NumberConvert.convertToDouble;
import static br.com.rafelms.rest_with_spring.operations.NumberConvert.isNumeric;


@RestController
@RequestMapping("/math")
public class MathController {

    @Autowired // Injeção de dependência do serviço
    private SimpleMath math;

    @GetMapping("/sum/{numberOne}/{numberTwo}")
    public Double sum(@PathVariable String numberOne, @PathVariable String numberTwo) {
        validateInput(numberOne, numberTwo);
        return math.sum(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/sub/{numberOne}/{numberTwo}")
    public Double sub(@PathVariable String numberOne, @PathVariable String numberTwo) {
        validateInput(numberOne, numberTwo);
        return math.sub(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/mult/{numberOne}/{numberTwo}")
    public Double mult(@PathVariable String numberOne, @PathVariable String numberTwo) {
        validateInput(numberOne, numberTwo);
        return math.mult(convertToDouble(numberOne), convertToDouble(numberTwo));
    }

    @GetMapping("/div/{numberOne}/{numberTwo}")
    public Double div(@PathVariable String numberOne, @PathVariable String numberTwo) {
        validateInput(numberOne, numberTwo);

        Double n1 = convertToDouble(numberOne);
        Double n2 = convertToDouble(numberTwo);

        if (n2 == 0) throw new UnsupportedMathOperationException("Division by zero is not possible.");

        return math.div(n1, n2);
    }

    @GetMapping("/rad/{number}")
    public Double rad(@PathVariable(value = "number") String number) {

        // 1. Validação (usando o método que você já tem no Controller ou na Utils)
        if (!isNumeric(number)) {
            throw new UnsupportedMathOperationException("Please set a numeric value!");
        }

        // 2. Conversão (ajustando o nome que deu erro na imagem)
        Double convertedNumber = convertToDouble(number);

        // 3. Regra de Negócio (Raiz quadrada de negativo não existe nos Reais)
        if (convertedNumber < 0) {
            throw new UnsupportedMathOperationException("Cannot calculate square root of a negative number!");
        }

        // 4. Execução delegada para a classe de operações (math)
        return math.rad(convertedNumber);
    }

    // Método privado para evitar repetir os "IFs" de validação em cada endpoint
    private void validateInput(String... numbers) {
        for (String n : numbers) {
            if (!isNumeric(n)) {
                throw new UnsupportedMathOperationException("Please set a numeric value!");
            }
        }
    }
}