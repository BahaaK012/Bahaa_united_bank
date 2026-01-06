🏦 Bahaa United Bank (BUB)

"We are not alone nor the best, but we are your only choice."

Bahaa United Bank is a Java-based banking system designed for secure (and strictly managed) financial operations. It features a dual-portal system for Users and Employees, persistent file storage, and a unique "menacing" input protection system.

🚀 Key Features

👤 User Services
•Account Variety: Supports Savings and Checking accounts with different rules.
•Savings Limits: Withdrawals are capped at $2,000 to encourage saving.
•Checking Fees: A 5% "small transaction fee" is applied to withdrawals under $50.
•Loans: Apply for loans directly through the app.
•Transfers: Instant transfers between internal accounts.

👔 Employee Management
•Loan Review: Employees can approve or reject pending loans.
•Risk Detection: Automatic warnings if a user asks for a loan exceeding 50% of their annual income.
•Automatic Payouts: Approved loans are instantly deposited into the user's account.

🛡️ System Robustness (The "Scary" Strike System)
•Crash Protection: The system uses Scanner validation to prevent crashes if a user enters text where a number is expected.
•Behavior Tracking: If a user enters invalid text 3 times, the bank issues a warning:
[!] You think you are funny huh? sleep with your eyes open today.
•Flexible Login: Usernames are case-insensitive, making it easier for users to log in (BAHAA vs bahaa).


🛠️ Technical Implementation

The project is built using Object-Oriented Programming (OOP) principles:
•Inheritance: SavingsAccount and CheckingAccount inherit from a base Account class.
•Polymorphism: Overridden withdraw methods to apply specific business rules.
•Encapsulation: Private data members with secure getters/setters.
•File I/O: All data is saved to and loaded from .txt files (users.txt, accounts.txt, loans.txt).


💾 Project Files
•Main.java: The interactive UI and input validation logic.
•Bank.java: The core engine handling data and file management.
•Account.java: The abstract base for financial data.
•BankTest.java: JUnit tests for balance, fees, and the strike system.


📖 How to Run
1.Ensure the .txt data files are in the root directory.
2.Compile the package: `bash javac bahaa/*.java Run the application:

Bash

java bahaa.Main Created by Bahaa - 2026
