# Java Console Calculator

A beginner-friendly Java console calculator that supports the four basic arithmetic operations, handles invalid input gracefully, and loops until the user chooses to exit.

---

## Features

- ➕ Addition
- ➖ Subtraction
- ✖️ Multiplication
- ➗ Division (with divide-by-zero protection)
- 🔁 Loop — perform multiple calculations in one session
- 🛡️ Input validation — handles non-numeric input without crashing

---

## Project Structure

```
Calculator/
├── Calculator.java   ← Single-file source code
└── README.md
```

---

## How to Run

### Prerequisites
- Java JDK 8 or above installed
- Terminal / Command Prompt / VS Code / IntelliJ

### Steps

```bash
# 1. Compile
javac Calculator.java

# 2. Run
java Calculator
```

### Sample Session

```
╔══════════════════════════════════╗
║      Java Console Calculator     ║
╚══════════════════════════════════╝

----------------------------------
  Select an operation:
  1. Addition       (+)
  2. Subtraction    (-)
  3. Multiplication (*)
  4. Division       (/)
  5. Exit
----------------------------------
  Enter choice (1-5): 1
  Enter first number  : 12
  Enter second number : 8

  ✔  12.0000 + 8.00000 = 20
```

---

## Key Concepts Covered

| Concept | Where Used |
|---|---|
| Methods | `add()`, `subtract()`, `multiply()`, `divide()` |
| Scanner / Console I/O | `Scanner scanner = new Scanner(System.in)` |
| Loops | `while (running)` loop for multiple calculations |
| Conditionals | `switch` for operation selection, `if` for validation |
| Exception Handling | `ArithmeticException` for divide-by-zero |
| OOP | Single class with static methods |

---

## Author

Built as Task 1 of the Java learning series.
