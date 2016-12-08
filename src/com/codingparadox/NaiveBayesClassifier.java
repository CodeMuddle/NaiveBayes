package com.codingparadox;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.codingparadox.SentimentClass;

public class NaiveBayesClassifier {
	private Map<String, List<String>> dataSet;
	
	private Map<String, Map<String, Long>> frequencyMap;
	
	public NaiveBayesClassifier(){
		dataSet = new HashMap<String, List<String>>();

		dataSet.put(SentimentClass.POSITIVE.name(), initPositiveData());
		dataSet.put(SentimentClass.NEGATIVE.name(), initNegativeData());
		
		this.frequencyMap = new HashMap<String, Map<String, Long>>();
	}
	
	private List<String> initPositiveData(){
		List<String> positive = new ArrayList<String>();
		positive.add("i");
		positive.add("love");
		positive.add("you");
		positive.add("like");
		positive.add("awesome");
		positive.add("love");
		positive.add("good");
		positive.add("better");
		positive.add("awesome");
		positive.add("nice");
		positive.add("nice");
		positive.add("book");
		return positive;
	}
	
	private List<String> initNegativeData(){
		List<String> negative = new ArrayList<String>();
		negative.add("hate");
		negative.add("you");
		negative.add("loathe");
		negative.add("dislike");
		negative.add("hate");
		negative.add("unlike");
		negative.add("no");
		negative.add("book");
		return negative;
	}
	
	public void generateFrequencyMap(){
		this.frequencyMap.put(SentimentClass.POSITIVE.name(), 
				this.generateFrequencyMapType(SentimentClass.POSITIVE));
		this.frequencyMap.put(SentimentClass.NEGATIVE.name(), 
				this.generateFrequencyMapType(SentimentClass.NEGATIVE));
	}
	
	public Map<String, Map<String, Long>> getFrequencyMap(){
		return this.frequencyMap;
	}
	
	private Map<String, Long> generateFrequencyMapType(SentimentClass sentType){
		String[] words = (String[]) this.dataSet.get(sentType.name()).
				toArray(new String[this.dataSet.get(sentType.name()).size()]);
		
		Map<String, Long> frequencyMap = this.getFrequencyMap(words);
		return frequencyMap;

	}
	
	private Map<String, Long> getFrequencyMap(String[] strings){
		HashMap<String, Long> frequencyMap = new HashMap<String, Long>();
		for(String s : strings){
			if(!frequencyMap.containsKey(s)){
				frequencyMap.put(s, (long)1);
			}
			else{
				frequencyMap.put(s, frequencyMap.get(s)+1);
			}
		}
		return frequencyMap;
	}

	public SentimentClass classify(String text){
		String[] splitted = text.split("\\s+");
		double positiveCount = this.dataSet.get(SentimentClass.POSITIVE.name()).size();
		double negativeCount = this.dataSet.get(SentimentClass.NEGATIVE.name()).size();
		double positiveSentimentProbability = 1;
		double negativeSentimentProbablity = 1;
		
		//Map<String, Long> frequencyMap = this.getFrequencyMap(splitted);
		
		for(String s : splitted){
			positiveSentimentProbability *= this.calculateProbability1(s, 
							SentimentClass.POSITIVE);
			negativeSentimentProbablity *= this.calculateProbability1(s, 
							SentimentClass.NEGATIVE);
		}
		
		positiveSentimentProbability *=( positiveCount / (positiveCount + negativeCount) );
		negativeSentimentProbablity *= (negativeCount / (positiveCount + negativeCount));
		
		System.out.println("positive probablity : " + positiveSentimentProbability);
		System.out.println("negative probablity : " + negativeSentimentProbablity);
		
		SentimentClass sentType = (positiveSentimentProbability > negativeSentimentProbablity) ? 
				SentimentClass.POSITIVE : SentimentClass.NEGATIVE;
		
		return sentType;
	}
	
	public double calculateProbability(String word, SentimentClass sentType){
		Map<String, Long> fm = this.frequencyMap.get(sentType.name());
		System.out.println(fm.get(word));
		double totalCount = this.dataSet.get(sentType.name()).size();
		double wordCount = (this.frequencyMap.get(sentType.name())).get(word);
		return wordCount / totalCount;
	}
	
	public double calculateProbability1(String word, SentimentClass sentType ){
		double totalCount = this.dataSet.get(sentType.name()).size();
		double wordCount = 0;
		
		for(String s : this.dataSet.get(sentType.name())){
			if(word.equals(s)){
				wordCount += 1;
			}
		}
		return wordCount / totalCount;
	}
}






