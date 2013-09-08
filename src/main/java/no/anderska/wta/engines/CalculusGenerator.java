package no.anderska.wta.engines;

import java.util.Random;

public class CalculusGenerator {
    private final static int stopWhenNodes = 12;
    private final static int maxNum = 43;

    private Random random = new Random();
    private int numberOfNumbers=0;
    private int xpick;

    private int leftpicks = stopWhenNodes;


    private static enum Operator {
        ADD("+"),SUBSTRACT("-"),MULTIPLY("*");

        private Operator(String sign) {
            this.sign = sign;
        }
        public final String sign;
    }


    private static class Part {
        Operator operator;
        Integer value;
        Part left;
        Part right;
        boolean isX = false;
    }


    private Part generate(boolean forcenum) {
        Part part = new Part();
        int type = random.nextInt(4);
        if (forcenum || leftpicks <= 0 || type==0) {
            this.numberOfNumbers=this.numberOfNumbers+1;
            part.value = random.nextInt(maxNum)+1;
            return part;
        }
        switch (type) {
            case 1:
                part.operator = Operator.ADD;
                part.left = generate(false);
                part.right = generate(false);
                part.value = part.left.value+part.right.value;
                return part;
            case 2:
                part.operator = Operator.SUBSTRACT;
                part.left = generate(false);
                part.right = generate(false);
                part.value = part.left.value-part.right.value;
                return part;
            case 3:
                part.operator = Operator.MULTIPLY;
                part.left = generate(true);
                part.right = generate(true);
                part.value = part.left.value*part.right.value;
                return part;
        }
        throw new RuntimeException("Should not be here");
    }

    private void partToStr(StringBuilder res,Part part) {
        if (part.operator == null) {
            res.append(part.isX ? "X" : part.value);
            return;
        }
        partToStr(res, part.left);
        res.append(part.operator.sign);
        partToStr(res, part.right);
    }

    public String[] generateComputation() {
        Part part = generate(false);
        StringBuilder res = new StringBuilder();
        partToStr(res,part);

        String [] resarr = new String[2];
        resarr[0] = res.toString();
        resarr[1] = "" + part.value;
        return resarr;
    }


    public String[] generateEquation() {
        Part part = generate(false);

        xpick = random.nextInt(numberOfNumbers);

        Part xp = setX(part);

        StringBuilder res = new StringBuilder();
        partToStr(res,part);

        String [] resarr = new String[2];
        resarr[0] = res.toString() + "=" + part.value;
        resarr[1] = "" + xp.value;
        return resarr;
    }

    private Part setX(Part part) {
        if (part.operator == null) {
            xpick--;
            if (xpick >= 0) {
                return null;
            }
            part.isX = true;
            return part;
        }
        Part res = setX(part.left);
        if (res == null) {
            res=setX(part.right);
        }
        return res;
    }


}
