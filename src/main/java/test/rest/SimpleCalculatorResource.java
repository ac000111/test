package test.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import test.PrimeCalculator;

import javax.inject.Inject;
import javax.inject.Named;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@RestController
public class SimpleCalculatorResource {
    private PrimeCalculator calculator;

    @Inject
    @Named("simple")
    public SimpleCalculatorResource(PrimeCalculator calculator) {
        this.calculator = calculator;
    }

    @Produces(MediaType.APPLICATION_JSON)
    @RequestMapping(value="/prime2", method= RequestMethod.GET)
    public int[] primes(
            @QueryParam("from") int from,
            @QueryParam("to") int to
    ) throws Exception {
        return calculator.calculate(from, to);
    }
}
