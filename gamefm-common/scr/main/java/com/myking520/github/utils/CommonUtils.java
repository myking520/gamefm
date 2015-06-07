package com.myking520.github.utils;

import java.util.Random;

public final class CommonUtils {
	private CommonUtils(){
		
	}
	/**
	 * 
	 * @param max 最大几率 <1表示由几率自动计算
	 * @param odds   几率列表
	 * @return 抽中的索引 <0表示未抽中
	 */
	public static int bingo(int max,int[] odds){
		int[] uodds=new int[odds.length];
		if(max<1){
			for(int i=0;i<odds.length;i++){
				max+=odds[i];
				uodds[i]=max;
			}
			return bingoCal(max, uodds);
		}
		int calOdds=0;
		for(int i=0;i<odds.length;i++){
			calOdds+=odds[i];
			uodds[i]=calOdds;
		}
		return bingoCal(max, uodds);
	}
	private static int bingoCal(int max,int[] odds){
		if(max<1){
			return -1;
		}
		int r=new Random().nextInt(max);
		r++;
		int rindex=-1;
		for(int i=0;i<odds.length;i++){
			if(r<=odds[i]){
				rindex=i;
				break;
			}
		}
		return rindex;
	}
}
