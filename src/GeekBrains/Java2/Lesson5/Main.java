package GeekBrains.Java2.Lesson5;

public class Main {
    private final static int size = 10000000;
    private final static int h = size / 2;
    private final static float array[] = new float[size];

    public static void main(String[] args) {
        fillArrayWithOnes();
        refillWithCalculated();
        twoThreadsCalculate();
    }

    public static void fillArrayWithOnes() {
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }
    }

    public static void refillWithCalculated() {
        float array1[] = new float[size];
        System.arraycopy(array, 0, array1, 0, size);
        long startTime = System.currentTimeMillis();
        calculateNewValue(array1);
        System.out.println("elapsed time in sec: " + (((System.currentTimeMillis() - startTime) / 1000F)));

    }


    public static void twoThreadsCalculate() {
        float[] resultArray = new float[size];
        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        long startTime = System.currentTimeMillis();
        System.arraycopy(array, 0, arr1, 0, h);
        System.arraycopy(array, h, arr2, 0, h);

        try {
            Thread t = new Thread(() -> calculateNewValue(arr1), "part1");
            t.start();
            Thread t2 = new Thread(() -> calculateNewValue(arr2), "part2");
            t2.start();

            t.join();
            t2.join();
        } catch (InterruptedException ex) {
            System.out.println("Thread interrupted " + ex);
        }
        System.arraycopy(arr1, 0, resultArray, 0, h);
        System.arraycopy(arr2, 0, resultArray, h, h);

        System.out.println("elapsed for concurrent method in sec: " + (System.currentTimeMillis() - startTime) / 1000F );


    }

    public static void calculateNewValue(float[] arr) {
        System.out.println(Thread.currentThread().getName() +" starts");
        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        System.out.println(Thread.currentThread().getName() + " ends");
    }

}
