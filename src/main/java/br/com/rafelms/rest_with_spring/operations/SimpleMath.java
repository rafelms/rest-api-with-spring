package br.com.rafelms.rest_with_spring.operations;

import org.springframework.stereotype.Service;

@Service // Permite que o Spring gerencie esta classe
public class SimpleMath {
    public Double sum(Double n1, Double n2) { return n1 + n2; }
    public Double sub(Double n1, Double n2) { return n1 - n2; }
    public Double mult(Double n1, Double n2) { return n1 * n2; }
    public Double div(Double n1, Double n2) { return n1 / n2; }
    public Double rad(Double n) { return Math.sqrt(n); }
}
