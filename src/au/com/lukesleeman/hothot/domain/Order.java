package au.com.lukesleeman.hothot.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Order implements Serializable {

	private static final long serialVersionUID = 1510375838862655528L;

	// Half a long neck each
	private static final double BEER_MULTIPLICATION_FACTOR = 0.5;
	
	private double chilliMultiplicationFactor;
	private int beerPrice;
	private int numPeople;
	private int numPeopleDrinking;
	private boolean hungry;
	
	private List<MenuItem> menu = new ArrayList<MenuItem>();
	
	private List<String> order = new ArrayList<String>();
	private String withBeerPrice = "$0.00";
	private String withoutBeerPrice = "$0.00";

	public Order(double chilliMultiplicationFactor, int beerPrice) {
		super();
		this.chilliMultiplicationFactor = chilliMultiplicationFactor;
		this.beerPrice = beerPrice;
	}
	
	public void calculateOrder(){
		// Figure out how many items to order
		int numItems;
		if(hungry){
			numItems = (int)Math.ceil((double)numPeople * chilliMultiplicationFactor);
		}
		else{
			numItems = (int)Math.floor((double)numPeople * chilliMultiplicationFactor);
			if(numItems == 0){
				// We have to eat something!
				numItems = 1;
			}
		}
		
		
		// Order the items
		Map<String, Integer> calculatedOrder = new LinkedHashMap<String, Integer>();
		int itemCost = 0;
		
		for(int i = 0; i < numItems; i++){
			MenuItem toOrder = menu.get(i % menu.size());
			if(calculatedOrder.containsKey(toOrder.getName())){
				calculatedOrder.put(toOrder.getName(), calculatedOrder.get(toOrder.getName()) + 1);
			}
			else{
				calculatedOrder.put(toOrder.getName(), 1);
			}
			itemCost += toOrder.getPrice();
		}
		
		// Add on the beers.  Always round up.  There is no such thing
		// as to much beer!
		int numBeers = (int)Math.ceil((double)numPeopleDrinking * BEER_MULTIPLICATION_FACTOR);
		if(numBeers > 0){
			calculatedOrder.put("Beer", numBeers);
		}
		int beerCost = numBeers * beerPrice;
		
		// Now we have figured out what we are getting, record it!
		order = new ArrayList<String>(calculatedOrder.keySet().size());
		for (String toOrder : calculatedOrder.keySet()) {
			order.add(calculatedOrder.get(toOrder) + " x " + toOrder);
		}
		
		if(numPeopleDrinking == numPeople){
			withBeerPrice = formatCentsAsPrice(itemCost / numPeople);
			withoutBeerPrice = "$0.00";
		}
		else if(numPeopleDrinking < numPeople && numPeopleDrinking > 0){
			withoutBeerPrice = formatCentsAsPrice(itemCost / (numPeople));
			withBeerPrice = formatCentsAsPrice((beerCost / numPeopleDrinking) + (itemCost / numPeople));
		}
		else{
			withBeerPrice = withoutBeerPrice = formatCentsAsPrice(itemCost / numPeople);
		}

	}

	private String  formatCentsAsPrice(int price) {
		int withoutBeerDollars = price / 100;
		int withoutBeerCents = price % 100;
		String toReturn = "$" + withoutBeerDollars + ".";
		if(withoutBeerCents < 10){
			toReturn += "0" + withoutBeerCents;
		}
		else {
			toReturn +=  withoutBeerCents;
		}
		
		return toReturn;
	}
	
	public void addMenuItem(String name, int price){
		menu.add(new MenuItem(name, price));
	}
	
	public List<String> getOrder(){
		return order;
	}
	
	public String getPriceWithBeer(){
		return withBeerPrice;
	}
	
	public String getPriceWithoutBeer(){
		return withoutBeerPrice;
	}

	public double getChilliMultiplicationFactor() {
		return chilliMultiplicationFactor;
	}

	public void setChilliMultiplicationFactor(double chilliMultiplicationFactor) {
		this.chilliMultiplicationFactor = chilliMultiplicationFactor;
	}

	public int getBeerPrice() {
		return beerPrice;
	}

	public void setBeerPrice(int beerPrice) {
		this.beerPrice = beerPrice;
	}

	public int getNumPeople() {
		return numPeople;
	}

	public void setNumPeople(int numPeople) {
		this.numPeople = numPeople;
	}

	public int getNumPeopleDrinking() {
		return numPeopleDrinking;
	}

	public void setNumPeopleDrinking(int numPeopleDrinking) {
		this.numPeopleDrinking = numPeopleDrinking;
	}

	public List<MenuItem> getMenu() {
		return menu;
	}

	public boolean isHungry() {
		return hungry;
	}

	public void setHungry(boolean hungry) {
		this.hungry = hungry;
	}
	
	public String toString(){
		String orderText = "";
		for (String orderItem : order) {
			orderText += orderItem + "\n";
		}
		
		return orderText;
	}
	
	
}
