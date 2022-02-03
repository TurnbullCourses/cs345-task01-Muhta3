package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     * checks if amount is valid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email) ){
            this.email = email;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }

        if(isAmountValid(startingBalance)){
            this.balance = startingBalance;
        }
        else{
            throw new IllegalArgumentException("Invalid balance");
        }
    }
    /**
     * 
     * @return balance
     */
    public double getBalance(){
        return balance;
    }
    /***
     * 
     * @return email
     */
    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     * withdraw nmakes sure a bank account can withdraw properly
     * Checks amount if greater than balance, too amny aig figs and negative amount.
     * I reuse isamount valid function
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(!isAmountValid(balance) || !isAmountValid(amount)){
            throw new IllegalArgumentException("Too many significant digits!");
        }
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }



    /**
     * 
     * @param email Proper email is feb@mail.com 
     * If email not valid returns false
     * @return boolean
     */
    public static boolean isEmailValid(String email){

        String[] emailAddress = email.split("@"); //seperate prefix and domain

        if ((emailAddress[0].indexOf(".") == emailAddress[0].length()-1) || email.equals("")) { 
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
    /**
     * 
     * @param balance starting balance is checked if valid
     * Not valid if negative balance, too many sig figs
     * @return returns a boolean
     */
    public static boolean isAmountValid(double balance){
        String balanceString = Double.toString(balance); //Turns double to a string
        String[] splitBalanceString = balanceString.split("\\."); //splits it to an array, splitting by period
        if(balance < 0 ){   
            return false;
        }
        if(balanceString.contains(".")){
            if(splitBalanceString[1].length()>2){ //looks after the period or domain to see if its more than 2 letters.
                return false;
            }
            else{
                return true;
            }
    
        }                
        else{
            return true;
        }
    }
    /**
     * 
     * @param amount Amount of money to deposit
     * @throws InsufficientFundsException throws is negative deposit or too many sig figs. 
     */
    public void deposit(double amount)throws InsufficientFundsException{
        if(!isAmountValid(amount)){
            throw new InsufficientFundsException("Deposit amount cant be negative or have more than 2 significant digits.");
        }
        else{
            balance+=amount;
        }


        
    }
    /**
     * 
     * @param amount This is the amount of money wanting to be transferred
     * @param otherAccount This is another Bank account to transfer the money to.
     * @throws InsufficientFundsException exception if the amount wanting to send if more than the balance one has in the account, thus cant send more than you have. Also, for too
     * many significant decimals and negative transfers.
     */

    public void transfer(double amount, BankAccount otherAccount)throws InsufficientFundsException{
        if(isAmountValid(amount) && amount<balance){
            balance-=amount;
            otherAccount.balance+=amount; 
        }
        else{
            throw new InsufficientFundsException("Transfer amount more than balance or amount has too many significant deciamls");

        }
    }
}