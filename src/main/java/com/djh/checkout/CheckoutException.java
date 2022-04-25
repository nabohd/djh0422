package com.djh.checkout;

/**
 * Simple checkout exception.
 */
public class CheckoutException extends Exception{
    public CheckoutException(String message){
        super(message);
    }
}
