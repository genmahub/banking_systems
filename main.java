package org.example;
import java.util.Scanner;

public class Main {  //public class in netbeans

    //customer info in arrays
    static String[] customer_name = new String[1000];
    static String[] customer_email = new String[100];
    static int[] customer_ids = new int[1000];
    static int total_customer = 0;

    //account info in arrays
    static int[] acc_number = new int[1000];
    static String[] acc_type = new String[1000];
    static double[] acc_balances = new double[1000];
    static boolean[] acc_open = new boolean[1000];
    static int[] acc_owner_index = new int[1000];/*which customer owns this account*/
    static int total_acc = 0;

    //id
    static int next_customer_id = 1;
    static int next_acc_number = 1001;

    static int choice;
    static int customer_index = -1;
    static Scanner s = new Scanner(System.in);

    static void main() {   //it public static void main in NetBeans

        System.out.println("==============================");
        System.out.println("Welcome to Grace's Bank App");
        System.out.println("================================");

        boolean running = true;
        //while loop
        while (running) {
            System.out.println("=============");
            System.out.println("_____Main menu____");
            System.out.println("1. Register new customer");
            System.out.println("2.Create new account");
            System.out.println("3. View account");
            System.out.println("4. Update customer info");
            System.out.println("5. Deposit money");
            System.out.println("6. Withdraw money");
            System.out.println("7. Transfer money");
            System.out.println("8. Close account");
            System.out.println("9. View customer info");
            System.out.println("10. Exit");
            System.out.println("please, pick an option");

            String input = s.nextLine();
            //make sure input is a number
            //for loop
            boolean is_number = true;
            for (int k = 0; k < input.length(); k++) {
                if (!Character.isDigit(input.charAt(k))) {
                    is_number = false;
                }
            }
            if (!is_number || input.equals(" ")) {
                System.out.println("Error, please type a number");
                continue;
            }

            choice = Integer.parseInt(input);

            String type;
            switch (choice) {

                case 1:
                    //register customer
                    System.out.println("==Register A New Customer");
                    System.out.print("Enter your name: ");
                    String name = s.nextLine();

                    if (name.isEmpty()) {
                        System.out.println("name can not be empty");
                        break;
                    }
                    System.out.print("Enter the email: ");
                    String email = s.nextLine();

                    //check email has @
                    boolean hasAt = false;
                    for (int a = 0; a < email.length(); a++)
                        if (email.charAt(a) == '@') {
                            hasAt = true;
                            break;
                        }
                    if (!hasAt) {
                        System.out.println("there's an error! Email must have @");
                        break;
                    }

                    //save the customer detail
                    customer_name[total_customer] = name;
                    customer_email[total_customer] = email;
                    customer_ids[total_customer] = next_customer_id;
                    total_customer++;
                    System.out.println("Customer registered! Your id is: " + next_customer_id);
                    next_customer_id++;
                    break;

                case 2:
                    //create account
                    System.out.println("==Create Account==");
                    System.out.print("Enter your customer id: ");
                    String id_input = s.nextLine();

                    //check it's a number
                    boolean id_is_num = true;
                    for (int z = 0; z < id_input.length(); z++) {
                        if (!Character.isDigit(id_input.charAt(z))) {
                            id_is_num = false;
                        }
                    }
                    if (!id_is_num || id_input.isEmpty()) {
                        System.out.println("id must be a number");
                        break;
                    }
                    int customer_id = Integer.parseInt(id_input);

                    //find the customer

                    for (int i = 0; i < total_customer; i++) {
                        if (customer_ids[i] == customer_id) {
                            customer_index = i;
                        }
                    }
                    if (customer_index == -1) {
                        System.out.println("customer not found");
                        break;
                    }

                    System.out.print("Account type(saving or checking): ");
                    type = s.nextLine();

                    if (!type.equals("savings") && !type.equals("checking")) {
                        System.out.println("must be saving or checking account");
                        break;
                    }

                    //save the account
                    acc_number[total_acc] = next_acc_number;
                    acc_type[total_acc] = type;
                    acc_balances[total_acc] = 0.0;
                    acc_open[total_acc] = true;
                    acc_owner_index[total_acc] = customer_index;
                    total_acc++;
                    System.out.println("Account created!!! Account number is: " + next_acc_number);
                    next_acc_number++;
                    break;

                case 3:
                    //view accounts
                    System.out.println("Enter account number: ");
                    String acc_input = s.nextLine();

                    boolean acc_is_num = true;
                    for (int x = 0; x < acc_input.length(); x++) {
                        if (!Character.isDigit(acc_input.charAt(x))) {
                            acc_is_num = false;
                        }
                    }
                    if (!acc_is_num || acc_input.isEmpty()) {
                        System.out.println("must be a number");
                        break;
                    }

                    int acc_num = Integer.parseInt(acc_input);
                    /* find account*/
                    int acc_index = -1;
                    for (int c = 0; c < total_acc; c++) {
                        if (acc_number[c] == acc_num) {
                            acc_index = c;
                        }
                    }

                    if (acc_index == -1) {
                        System.out.println("account not found");
                        break;
                    }

                    /*show account info*/
                    System.out.println("--- Account Info ---");
                    System.out.println("Account Number: " + acc_number[acc_index]);
                    System.out.println("Type: " + acc_type[acc_index]);
                    System.out.println("Balance: $" + acc_balances[acc_index]);
                    System.out.println("Status: " + (acc_open[acc_index] ? "Open" : "Closed"));
                    System.out.println("Owner: " + customer_name[acc_owner_index[acc_index]]);
                    break;

                case 4:
                    //update customer info
                    System.out.println("-- Update Customer Info --");
                    System.out.print("Enter your customer ID: ");
                    String idInput4 = s.nextLine();

                    boolean id_is_num4 = true;
                    for (int y = 0; y < idInput4.length(); y++) {
                        if (!Character.isDigit(idInput4.charAt(y))) {
                            id_is_num4 = false;
                        }
                    }
                    if (!id_is_num4 || idInput4.isEmpty()) {
                        System.out.println("ID must be a number");
                        break;
                    }

                    int customerId4 = Integer.parseInt(idInput4);

                    customer_index = -1;
                    for (int i = 0; i < total_customer; i++) {
                        if (customer_ids[i] == customerId4) {
                            customer_index = i;
                        }
                    }

                    if (customer_index == -1) {
                        System.out.println("customer not found");
                        break;
                    }

                    System.out.println("Current name: " + customer_name[customer_index]);
                    System.out.print("Enter new name (or press enter to keep same): ");
                    String newName = s.nextLine();

                    if (!newName.isEmpty()) {
                        customer_name[customer_index] = newName;
                        System.out.println("Name updated!");
                    }

                    System.out.println("Current email: " + customer_email[customer_index]);
                    System.out.print("Enter new email (or press enter to keep same): ");
                    String newEmail = s.nextLine();

                    if (!newEmail.isEmpty()) {
                        //check new email has @
                        boolean hasAt4 = false;
                        for (int i = 0; i < newEmail.length(); i++) {
                            if (newEmail.charAt(i) == '@') {
                                hasAt4 = true;
                                break;
                            }
                        }
                        if (!hasAt4) {
                            System.out.println("Error: email must have @ in it");
                            break;
                        }
                        customer_email[customer_index] = newEmail;
                        System.out.println("Email updated!");
                    }
                    break;

                case 5:
                    //deposit
                    System.out.print("Enter account number: ");
                    String acc_input5 = s.nextLine();

                    boolean acc_is_num5 = true;
                    for (int j = 0; j < acc_input5.length(); j++) {
                        if (!Character.isDigit(acc_input5.charAt(j))) {
                            acc_is_num5 = false;
                        }
                    }

                    if (!acc_is_num5 || acc_input5.isEmpty()) {
                        System.out.println("Error: must be a number");
                        break;
                    }

                    int acc_num5 = Integer.parseInt(acc_input5);
                    int accIndex5 = -1;

                    for (int q = 0; q < total_acc; q++) {
                        if (acc_number[q] == acc_num5) {
                            accIndex5 = q;
                        }
                    }

                    if (accIndex5 == -1) {
                        System.out.println("account not found");
                        break;
                    }

                    if (!acc_open[accIndex5]) {
                        System.out.println("this account is closed");
                        break;
                    }

                    System.out.print("Enter amount to deposit: ");
                    String amtInput5 = s.nextLine();

                    double amount5 = 0;
                    boolean validAmount5 = true;
                    try {
                        amount5 = Double.parseDouble(amtInput5);
                    } catch (Exception e) {
                        validAmount5 = false;
                    }

                    if (!validAmount5 || amount5 <= 0) {
                        System.out.println("amount must be a number bigger than 0");
                        break;
                    }
                    acc_balances[accIndex5] = acc_balances[accIndex5] + amount5;
                    System.out.println("Deposited $" + amount5 + " successfully");
                    System.out.println("New balance: $" + acc_balances[accIndex5]);
                    break;

                case 6:
                    //withdraw
                    System.out.print("Enter account number: ");
                    String accInput6 = s.nextLine();

                    boolean accIsNum6 = true;
                    for (int i = 0; i < accInput6.length(); i++) {
                        if (!Character.isDigit(accInput6.charAt(i))) {
                            accIsNum6 = false;
                        }
                    }

                    if (!accIsNum6 || accInput6.isEmpty()) {
                        System.out.println("must be a number");
                        break;
                    }

                    int accNum6 = Integer.parseInt(accInput6);
                    int accIndex6 = -1;

                    for (int x = 0; x < total_acc; x++) {
                        if (acc_number[x] == accNum6) {
                            accIndex6 = x;
                        }
                    }
                    if (accIndex6 == -1) {
                        System.out.println("account not found");
                        break;
                    }

                    if (!acc_open[accIndex6]) {
                        System.out.println("this account is closed");
                        break;
                    }

                    System.out.print("Enter amount to withdraw: ");
                    String amtInput6 = s.nextLine();

                    double amount6 = 0;
                    boolean validAmount6 = true;
                    try {
                        amount6 = Double.parseDouble(amtInput6);
                    } catch (Exception e) {
                        validAmount6 = false;
                    }

                    if (!validAmount6 || amount6 <= 0) {
                        System.out.println("amount must be a number bigger than 0");
                        break;
                    }
                    //check enough balance
                    if (amount6 > acc_balances[accIndex6]) {
                        System.out.println("not enough money in account");
                        break;
                    }

                    acc_balances[accIndex6] = acc_balances[accIndex6] - amount6;
                    System.out.println("Withdrew $" + amount6 + " successfully");
                    System.out.println("New balance: $" + acc_balances[accIndex6]);
                    break;

                case 7:
                    // transfer
                    System.out.print("Enter YOUR account number: ");
                    String accInput1 = s.nextLine();

                    System.out.print("Enter DESTINATION account number: ");
                    String accInput2 = s.nextLine();

                    boolean acc1IsNum = true;
                    boolean acc2IsNum = true;

                    for (int o = 0; o < accInput1.length(); o++) {
                        if (!Character.isDigit(accInput1.charAt(o))) {
                            acc1IsNum = false;
                        }
                    }
                    for (int i = 0; i < accInput2.length(); i++) {
                        if (!Character.isDigit(accInput2.charAt(i))) {
                            acc2IsNum = false;
                        }
                    }

                    if (!acc1IsNum || !acc2IsNum || accInput1.isEmpty() || accInput2.isEmpty()) {
                        System.out.println("account numbers must be numbers");
                        break;
                    }

                    int accNum1 = Integer.parseInt(accInput1);
                    int accNum2 = Integer.parseInt(accInput2);

                    int accIndex1 = -1;
                    int accIndex2 = -1;

                    for (int m = 0; m < total_acc; m++) {
                        if (acc_number[m] == accNum1) {
                            accIndex1 = m;
                        }
                        if (acc_number[m] == accNum2) {
                            accIndex2 = m;
                        }
                    }
                    if (accIndex1 == -1) {
                        System.out.println("your account was not found");
                        break;
                    }

                    if (accIndex2 == -1) {
                        System.out.println("destination account was not found");
                        break;
                    }

                    if (!acc_open[accIndex1]) {
                        System.out.println("your account is closed");
                        break;
                    }

                    if (!acc_open[accIndex2]) {
                        System.out.println("destination account is closed");
                        break;
                    }

                    System.out.print("Enter amount to transfer: ");
                    String amtInput7 = s.nextLine();

                    double amount7 = 0;
                    boolean validAmount7 = true;
                    try {
                        amount7 = Double.parseDouble(amtInput7);
                    } catch (Exception e) {
                        validAmount7 = false;
                    }
                    if (!validAmount7 || amount7 <= 0) {
                        System.out.println("amount must be a number bigger than 0");
                        break;
                    }

                    if (amount7 > acc_balances[accIndex1]) {
                        System.out.println("not enough balance to transfer");
                        break;
                    }

                    // do the transfer
                    acc_balances[accIndex1] = acc_balances[accIndex1] - amount7;
                    acc_balances[accIndex2] = acc_balances[accIndex2] + amount7;
                    System.out.println("Transfer done! Sent $" + amount7 + " to account #" + accNum2);
                    System.out.println("Your new balance: $" + acc_balances[accIndex1]);
                    break;

                case 8:
                    // close account
                    System.out.print("Enter account number to close: ");
                    String accInput8 = s.nextLine();

                    boolean accIsNum8 = true;
                    for (int t = 0; t < accInput8.length(); t++) {
                        if (!Character.isDigit(accInput8.charAt(t))) {
                            accIsNum8 = false;
                        }
                    }
                    if (!accIsNum8 || accInput8.isEmpty()) {
                        System.out.println("must be a number");
                        break;
                    }

                    int acc_num8 = Integer.parseInt(accInput8);
                    int accIndex8 = -1;

                    for (int j = 0; j < total_acc; j++) {
                        if (acc_number[j] == acc_num8) {
                            accIndex8 = j;
                        }
                    }

                    if (accIndex8 == -1) {
                        System.out.println("account not found");
                        break;
                    }

                    if (!acc_open[accIndex8]) {
                        System.out.println("account is already closed");
                        break;
                    }

                    acc_open[accIndex8] = false;
                    System.out.println("Account #" + acc_num8 + " has been closed");
                    break;

                case 9:
                    //view customer
                    System.out.print("Enter customer ID: ");
                    String idInput9 = s.nextLine();

                    boolean idIsNum9 = true;
                    for (int i = 0; i < idInput9.length(); i++) {
                        if (!Character.isDigit(idInput9.charAt(i))) {
                            idIsNum9 = false;
                        }
                    }

                    if (!idIsNum9 || idInput9.isEmpty()) {
                        System.out.println("ID must be a number");
                        break;
                    }

                    int customerId9 = Integer.parseInt(idInput9);
                    int customerIndex9 = -1;

                    for (int i = 0; i < total_customer; i++) {
                        if (customer_ids[i] == customerId9) {
                            customerIndex9 = i;
                        }
                    }

                    if (customerIndex9 == -1) {
                        System.out.println("Error: customer not found");
                        break;
                    }

                    System.out.println("--- Customer Info ---");
                    System.out.println("ID: " + customer_ids[customerIndex9]);
                    System.out.println("Name: " + customer_name[customerIndex9]);
                    System.out.println("Email: " + customer_email[customerIndex9]);
                    break;

                case 10:
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("that option doesnt exist, please try again");
                    break;
            }
        }
    }
}