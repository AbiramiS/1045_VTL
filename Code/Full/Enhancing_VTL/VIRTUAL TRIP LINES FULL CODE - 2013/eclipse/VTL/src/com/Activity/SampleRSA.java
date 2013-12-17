package com.Activity;

import java.math.BigInteger ;
import java.util.Random ;
import java.io.* ;

public class SampleRSA
{
	/**
	 * Bit length of each prime number.
	 */
	int primeSize ;

	/**
	 * Two distinct large prime numbers p and q.
	 */
	BigInteger p, q ;

	/**
	 * Modulus N.
	 */
	BigInteger N ;

	/**
	 * r = ( p - 1 ) * ( q - 1 )
	 */
	BigInteger r ;

	/**
	 * Public exponent E and Private exponent D
	 */
	BigInteger E, D ;


	public SampleRSA(){
	}
	
	public String encrypt( String message )
	{
		int i ;
		byte[] temp = new byte[1] ;


		byte[] digits = message.getBytes() ;

		BigInteger[] bigdigits = new BigInteger[digits.length] ;

		for( i = 0 ; i < bigdigits.length ; i++ )
		{
			temp[0] = digits[i] ;
			bigdigits[i] = new BigInteger( temp ) ;
		}

		BigInteger[] encrypted = new BigInteger[bigdigits.length] ;

		for( i = 0 ; i < bigdigits.length ; i++ )
			encrypted[i] = bigdigits[i].modPow( E, N ) ;


		return encrypted.toString();
	}
}