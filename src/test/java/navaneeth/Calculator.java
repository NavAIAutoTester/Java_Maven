package navaneeth;

/**
 * A simple calculator class demonstrating basic arithmetic operations.
 * This is a test script for the Maven project.
 */
public class Calculator {
    
    /**
     * Adds two numbers.
     * @param a First number
     * @param b Second number
     * @return Sum of a and b
     */
    public int add(int a, int b) {
        return a + b;
    }
    
    /**
     * Subtracts second number from first number.
     * @param a First number
     * @param b Second number
     * @return Difference of a and b
     */
    public int subtract(int a, int b) {
        return a - b;
    }
    
    /**
     * Multiplies two numbers.
     * @param a First number
     * @param b Second number
     * @return Product of a and b
     */
    public int multiply(int a, int b) {
        return a * b;
    }
    
    /**
     * Divides first number by second number.
     * @param a Dividend
     * @param b Divisor
     * @return Quotient of a divided by b
     * @throws ArithmeticException if b is zero
     */
    public double divide(int a, int b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero!");
        }
        return (double) a / b;
    }
    
    /**
     * Main method to demonstrate the Calculator class.
     * This is a test script for running calculator operations.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args) {
        Calculator calc = new Calculator();
        
        System.out.println("=== Simple Calculator Test Script ===");
        System.out.println();
        
        int num1 = 20;
        int num2 = 5;
        
        System.out.println("Number 1: " + num1);
        System.out.println("Number 2: " + num2);
        System.out.println();
        
        System.out.println("Addition: " + num1 + " + " + num2 + " = " + calc.add(num1, num2));
        System.out.println("Subtraction: " + num1 + " - " + num2 + " = " + calc.subtract(num1, num2));
        System.out.println("Multiplication: " + num1 + " * " + num2 + " = " + calc.multiply(num1, num2));
        System.out.println("Division: " + num1 + " / " + num2 + " = " + calc.divide(num1, num2));
    }
}

