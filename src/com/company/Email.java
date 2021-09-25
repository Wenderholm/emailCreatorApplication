package com.company;

import com.company.exception.NoSuchOptionException;
import org.w3c.dom.DocumentType;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Email {
    private String firstName;
    private String lastName;
    private String password;
    private String departmentSuffix;
    private String department;
    private String email;
    private int mailboxCapacity = 50;
    private String alternateEmail;
    private static int PASSWORD_LENGTH = 8;
    private String companySuffix = "firma.com";

    Scanner in = new Scanner(System.in);

    public Email(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        System.out.println("Mail został stworzony: " + this.firstName + " " + this.lastName);
        setDepart();
        System.out.println("Wydział wybrany przez Ciebie to: " + this.department);
        this.password = randomPassword(PASSWORD_LENGTH);
        email = firstName.toLowerCase() + "." + lastName.toLowerCase() + "@" + department + "." + companySuffix;
    }

    /**
     * PIERWOTNA WERSJA PROGRAMU ZWYBORAMI 
     */
//    private String setDepartment() {
//        System.out.print("Wybierz wydział\n1 dla Sprzedaży\n2 dla Developerów\n3 dla Księgowości\n0 inny wydział\nWybrany kod wydziału: ");
//        Scanner in = new Scanner(System.in);
//        int userChoice = in.nextInt();
//        if (userChoice == 1) {
//            return "salse";
//        } else if (userChoice == 2) {
//            return "dev";
//        } else if (userChoice == 3) {
//            return "acct";
//        } else {
//            return "other";
//        }
//    }

    public void setDepart() {
        Option option;
        printOptions();
        option = getOption();
        switch (option) {
            case SELL:
                sellDep();
                break;
            case DEV:
                decDep();
                break;
            case ACOUNT:
                acoDep();
                break;
            case OTHER:
                otherDep();
                break;
            case EXIT:
                exit();
                break;
            default:
                System.out.println("Nie ma takiej opcji, wprowadź ponownie: ");
        }
    }

    private void sellDep() {
        this.departmentSuffix = "salse";
        this.department = Option.SELL.getDescription();
    }

    private void decDep() {
        this.departmentSuffix = "dev";
        this.department = Option.DEV.getDescription();
    }

    private void acoDep() {
        this.departmentSuffix = "acct";
        this.department = Option.ACOUNT.getDescription();
    }

    private void otherDep() {
        this.departmentSuffix = "other";
        this.department = Option.OTHER.getDescription();
    }

    private void exit() {
        System.out.println("Dziękujemy za wybór wydziału\n");
    }


    private Option getOption() {
        boolean optionOk = false;
        Option option = null;
        while (!optionOk) {
            try {
                option = Option.createFromInt(getInt());
                optionOk = true;
            } catch (NoSuchOptionException e) {
                System.out.println(e.getMessage());
            } catch (InputMismatchException e) {
                System.out.println("wprowadzono wartość, która nie jest liczbą. podaj ponownie");
                in.nextLine();
            }
        }
        return option;
    }

    private int getInt() {
        return in.nextInt();
    }


    private void printOptions() {
        System.out.println("Wybierz odpowiedni wydział: ");
        for (Option option : Option.values()) {
            System.out.println(option.toString());
        }
    }

    private enum Option {
        EXIT(0, "zakończ wybór"),
        SELL(1, "Sprzedaż"),
        DEV(2, "Developerski"),
        ACOUNT(3, "Finansowy"),
        OTHER(4, "Inny");
        private final int value;
        private final String description;

        Option(int value, String description) {
            this.value = value;
            this.description = description;
        }

        public int getValue() {
            return value;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return value + " - " + description;
        }

        static Option createFromInt(int option) throws NoSuchOptionException {
            try {
                return Option.values()[option];
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new NoSuchOptionException("Brak opcji o id " + option);
            }

        }
    }


    private String randomPassword(int length) {
        String passwordSet = "ABCDEFGHIJKLMNOPRSTUWXYZ0123456789!@#$%^&*()";
        char[] password = new char[length];
        for (int i = 0; i < length; i++) {
            int rand = (int) (Math.random() * passwordSet.length());
            password[i] = passwordSet.charAt(rand);
        }
        return new String(password);
    }

    public void setMailboxCapacity(int capacity) {
        this.mailboxCapacity = capacity;
    }

    public void setAlternateEmail(String altEmail) {
        this.alternateEmail = altEmail;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public int getMailboxCapacity() {
        return mailboxCapacity;
    }

    public String getAlternateEmail() {
        return alternateEmail;
    }

    public String getPassword() {
        return password;
    }

    public String showInfo() {
        return "Imię i nazwisko: " + firstName + " " + lastName +
                "\nfirmowy adres email: " + email +
                "\npojemność skrzynki email: " + mailboxCapacity + "mb";
    }
}
