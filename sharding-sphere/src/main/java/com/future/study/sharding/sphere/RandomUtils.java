package com.future.study.sharding.sphere;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 随机数生产工具
 * @author Dexterleslie
 * @date 2018年7月29日
 * @time 上午9:55:29
 */
public class RandomUtils {
	private static Random random=new Random();
	private static List<String> listNumberOnly=new ArrayList<String>();
	private static List<String> listNumberAndCharacter=new ArrayList<String>();
	
	static{
		for(int i=0;i<=9;i++){
			listNumberOnly.add(String.valueOf(i));
			listNumberAndCharacter.add(String.valueOf(i));
		}
		
		int rangeMin='a';
		int rangeMax='z';
		for(int i=rangeMin;i<=rangeMax;i++){
			char char1=Character.toChars(i)[0];
			listNumberAndCharacter.add(String.valueOf(char1));
		}
		
		rangeMin='A';
		rangeMax='Z';
		for(int i=rangeMin;i<=rangeMax;i++){
			char char1=Character.toChars(i)[0];
			listNumberAndCharacter.add(String.valueOf(char1));
		}
	}
	
	/**
	 * 只生成包含数字的随机字符串
	 * @param codeLength
	 * @return
	 */
	public static String getNumberOnly(int length){
		if(length<=0)
			length=5;
		if(length>20)
			length=20;
		
		int size=listNumberOnly.size();
		String randomString="";
		for(int i=0;i<length;i++){
			int randomIndex=random.nextInt(size);
			String cTemp1=listNumberOnly.get(randomIndex);
			randomString+=cTemp1;
		}
		return randomString;
	}
	
	/**
	 * 生成包含数字和英文的随机字符串
	 * @param length
	 * @return
	 */
	public static String getCharacterAndNumber(int length){
		if(length<=0)
			length=5;
		if(length>20)
			length=20;
		
		int size=listNumberAndCharacter.size();
		String randomString="";
		for(int i=0;i<length;i++){
			int randomIndex=random.nextInt(size);
			String cTemp1=listNumberAndCharacter.get(randomIndex);
			randomString+=cTemp1;
		}
		return randomString;
	}
}
