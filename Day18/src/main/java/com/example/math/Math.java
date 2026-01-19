package com.example.math;

public final class Math {

    private Math() {  }

    public interface MathEngine {
        int performOperation(int a, int b) throws Exception;
    }

    public static class MathOperationService {

        private final MathEngine engine;

        public MathOperationService(MathEngine engine) {
            this.engine = engine;
        }

        public int safeOperation(int a, int b) {
            try {
                return engine.performOperation(a, b);
            } catch (Exception e) {
                System.out.println("Math error: " + e.getMessage());
                return -1;
            }
        }
    }

    public static class RealMathEngine implements MathEngine {
        @Override
        public int performOperation(int a, int b) throws Exception {
            if (b == 0) throw new Exception("Division by zero");
            return a / b;
        }
    }

    public static void main(String[] args) throws Exception {
        MathEngine engine = new RealMathEngine();
        MathOperationService svc = new MathOperationService(engine);
        System.out.println("10 / 2 = " + svc.safeOperation(10, 2));
        System.out.println("10 / 0 = " + svc.safeOperation(10, 0));
    }
}
