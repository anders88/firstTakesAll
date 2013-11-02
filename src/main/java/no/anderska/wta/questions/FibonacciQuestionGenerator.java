package no.anderska.wta.questions;

import no.anderska.wta.game.Question;

class FibonacciQuestionGenerator extends  AbstractQuestionGenerator {

    public FibonacciQuestionGenerator() {
        super(20, "Compute the Fibonacci number. Fib(n)=Fib(n-1)+Fib(n-2), where Fib(0) = 0 and Fib(1)=1. E.g. '4'=>'3'");
    }

    long fibonacci(int num) {
        if (num == 0) return 0;
        if (num == 1) return 1;

        long prevPrev = 0;
        long prev = 1;
        long current = 1;

        for (int i=2;i<=num;i++) {
            current=prevPrev+prev;
            prevPrev = prev;
            prev = current;
        }

        return current;
    }

    @Override
    protected Question createQuestion() {
        int fibNo = random.nextInt(51);
        long fib = fibonacci(fibNo);

        return new Question("" + fibNo,"" + fib);
    }
}
