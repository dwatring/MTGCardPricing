package com.watring.derek.mtgcardpricing;

public class QueryList {
	int size = 0;
	Query[] list = new Query[150];
	
	void addQuery(Query newQuery){
		list[size] = newQuery;
		size++;
	}
}
