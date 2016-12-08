package com.codingparadox;

import com.codingparadox.NaiveBayesClassifier;

public class Main {
	
	public static void main(String[] args){
		System.out.println("i am paradox");
		
		NaiveBayesClassifier nbc = new NaiveBayesClassifier();
		nbc.generateFrequencyMap();
		System.out.println(nbc.getFrequencyMap());
		SentimentClass sentType = nbc.classify("i like book");
		System.out.println(sentType);
	}
}
