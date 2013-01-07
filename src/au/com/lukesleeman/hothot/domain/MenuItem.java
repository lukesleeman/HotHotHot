package au.com.lukesleeman.hothot.domain;

import java.io.Serializable;

/**
 * Represents an item that can be ordered.  The Order contains a number of menu items.
 * 
 * @author lukesleeman
 */
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
