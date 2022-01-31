package edu.ithaca.dturnbull.bank;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    @Test
    void getBalanceTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals(200, bankAccount.getBalance(), 0.001);//upper border case --- positive

        BankAccount noBalanceAccount = new BankAccount("a@b.com", 0);
        assertEquals(0, noBalanceAccount.getBalance());//middle border case -- zero balance


    }

    @Test
    void withdrawTest() throws InsufficientFundsException{
        BankAccount bankAccount = new BankAccount("a@b.com", 200);
        bankAccount.withdraw(100);

        assertEquals(100, bankAccount.getBalance(), 0.001);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); // Amount greater than balance -- Greater than border case
        bankAccount.withdraw(100);
        assertThrows(InsufficientFundsException.class, () -> bankAccount.withdraw(300)); //Border case =0 -- no balance

        BankAccount noBalance = new BankAccount("a@b.com", 0);
        assertThrows(InsufficientFundsException.class, () -> noBalance.withdraw(300)); //no balance -- Middle border case

        BankAccount negativeBalance = new BankAccount("a@b.com", 400);
        assertThrows(IllegalArgumentException.class, () -> negativeBalance.withdraw(-300)); // negative amount --  border case less than 0

        BankAccount bankAccount2 = new BankAccount("a@b.com", 100);
        assertThrows(InsufficientFundsException.class, () -> bankAccount2.withdraw(300)); //Testing for withdrawing more than amount

        BankAccount bankAccount3 = new BankAccount("a@b.com", 400); //Testing for too many significant digits.
        assertThrows(IllegalArgumentException.class, () -> bankAccount3.withdraw(300.453));

        

    }

    @Test
    void isEmailValidTest(){
        assertTrue(BankAccount.isEmailValid( "a@b.com"));   // valid email address
        assertFalse( BankAccount.isEmailValid(""));         // empty string

        assertFalse(BankAccount.isEmailValid("1234@com"));//Domain -- no period in the domain
        assertFalse(BankAccount.isEmailValid("@.com")); //Prefix -- no prefix only domain
        assertFalse(BankAccount.isEmailValid("hello32@com"));  //Domain -- no period in the domain
        
        //PreFix Tests
        assertTrue(BankAccount.isEmailValid("amg-crv@mail.com")); //Valid email
        assertFalse(BankAccount.isEmailValid("bmw..@mail.com")); //Prefix may not contain two or more periods
        assertFalse(BankAccount.isEmailValid(".maz3@gmail.com")); //Cant start with period
        assertFalse(BankAccount.isEmailValid("amg#crv@mail.com")); //Cant have a # in prefix
        assertFalse(BankAccount.isEmailValid("...@mail.com")); //No words in prefix
        
        //Domain tests
        assertTrue(BankAccount.isEmailValid("hello@mail.com")); //Valid email
        assertFalse(BankAccount.isEmailValid("hello@mailcom")); //No period in domain
        assertFalse(BankAccount.isEmailValid("hello@mail.c")); //Needs 2 or more letters in the domain
        assertFalse(BankAccount.isEmailValid("helloAtmail.com")); //Domain needs an @
        assertFalse(BankAccount.isEmailValid("hello@mail")); //Needs a domain
        assertFalse(BankAccount.isEmailValid("hello@mail..com")); //Cant contain 2 periods in domain

        //Middle tests
        assertTrue(BankAccount.isEmailValid("muhta%@mail.com")); //Valid email
        assertFalse(BankAccount.isEmailValid("@mail.com")); //No prefix
        assertFalse(BankAccount.isEmailValid("hello@muhta@mail.com")); //Prefix cant contain @


        
    }

    @Test
    void constructorTest() {
        BankAccount bankAccount = new BankAccount("a@b.com", 200);

        assertEquals("a@b.com", bankAccount.getEmail());
        assertEquals(200, bankAccount.getBalance(), 0.001);
        //check for exception thrown correctly
        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("", 100));

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", -100)); //Testing for negative balance

        assertThrows(IllegalArgumentException.class, ()-> new BankAccount("a@b.com", 100.489)); //Testing for too many significant digits


        

        

    }

    @Test
    void isAmountValid(){
        assertTrue(BankAccount.isAmountValid(150.01));//Valid Amount --- Anything over $0 -- 2 decimal points

        assertFalse(BankAccount.isAmountValid(-100.00)); //Negative number --border case

        assertFalse(BankAccount.isAmountValid(200.0405));//More than 2 decimal places -- border case

        assertTrue(BankAccount.isAmountValid(100));//Doesnt have any decimal points -- border case

        assertFalse(BankAccount.isAmountValid(-0.450));//Is negative -- border case

    }

    @Test
    void depositTest(){
        BankAccount bankAccount = new BankAccount("a@b.com", 0);
        bankAccount.deposit(100);

        assertEquals(100, bankAccount.getBalance());//Valid deposit

        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(-20) );//negative deposit
        assertThrows(IllegalArgumentException.class, ()-> bankAccount.deposit(20.456));//too many significant digits

    }

}