# Function to greet the user
def greet():
    print "Welcome to the Basic Arithmetic Calculator!"

# Function to get a number from the user
def get_number(prompt):
    print prompt
    input_str = raw_input()
    return float(input_str)

# Function to add two numbers
def add(a, b):
    return a + b

# Function to multiply two numbers
def multiply(a, b):
    return a * b

# Function to display the results
def display_results(sum, product):
    print "Sum:" + sum
    print "Product:" + product

# Main function to run the calculator
def main():
    greet()
    num1 = get_number("Enter the first number:")
    num2 = get_number("Enter the second number:")
    total = add(num1, num2)
    product = multiply(num1, num2)
    display_results(total, product)

# Start the program
main()
