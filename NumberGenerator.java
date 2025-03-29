import java.util.Random;

public class NumberGenerator {

    private static Random random = new Random();

    /**
     * Generate a string only with digits of the specified length
     * number
     * 
     * @param length this the length of the number
     * @return String of digits
     */
    public static String getNumberWithLengthOf(int length) {
        String generatedNumber = "";

        for (int i = 0; i < length; i++) {
            int newRandomNumber = NumberGenerator.random.nextInt(10);
            generatedNumber += newRandomNumber;
        }
        return generatedNumber;
    }
}
