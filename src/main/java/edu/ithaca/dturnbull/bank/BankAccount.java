package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }




    public static boolean isEmailValid(String email){

        String[] emailAddress = email.split("@"); //seperate prefix and domain

        if ((emailAddress[0].indexOf(".") == emailAddress[0].length()-1)) { 
            return false;
        }
        else if (email.indexOf('.') == -1 || email.indexOf('@') == -1){ 
            return false;
        }
        else if (emailAddress.length>2 || emailAddress[1].indexOf(".") == emailAddress[1].length()-2){
            return false;
        }
        else if (email.indexOf('@') == 0 || email.indexOf(".") == 0){
            return false;
        }
        else if (email.contains("..") || email.contains("#")){
            return false;
        }
        else {
            return true;
        }
    }

    public static boolean isAmountValid(double balance){
        String balanceString = String.valueOf(balance);
        String[] splitBalanceString = balanceString.split(".");
        if(balance < 0 || splitBalanceString[1].length()>2 ){
            return false;
        }
        else{
            return true;
        }
    }
}