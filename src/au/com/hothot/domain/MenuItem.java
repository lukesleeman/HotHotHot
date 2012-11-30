package au.com.hothot.domain;

import java.io.Serializable;

public class MenuItem implements Serializable{
	private int price;
	private String name;
	
	public MenuItem(String name, int price) {
		this.price = price;
		this.name = name;
	}
	
	public int getPrice() {
		return price;
	}
	
	public void setPrice(int price) {
		this.price = price;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(){
		return getName();
	}
	
	

}
