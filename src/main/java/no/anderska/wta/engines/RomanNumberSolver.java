package no.anderska.wta.engines;

public class RomanNumberSolver {
    private static class Number {
        private int num;
        StringBuilder res = new StringBuilder();

        private Number(int num) {
            this.num = num;
        }

        public boolean match(int val, String sign) {
            if (num < val) {
                return false;
            }
            res.append(sign);
            num-=val;
            return true;
        }

        private StringBuilder getRes() {
            return res;
        }
    }

    public String romanNumber(int num) {
        Number n = new Number(num);
        while (
                n.match(1000, "M") ||
                        n.match(900, "CM") ||
                        n.match(500, "D") ||
                        n.match(400, "CD") ||
                        n.match(100, "C") ||
                        n.match(90, "XC") ||
                        n.match(50, "L") ||
                        n.match(40, "XL") ||
                        n.match(10, "X") ||
                        n.match(9, "IX") ||
                        n.match(5, "V") ||
                        n.match(4, "IV") ||
                        n.match(1, "I")
                ) {}
        return n.getRes().toString();
    }
}
