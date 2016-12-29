import java.text.NumberFormat;

public class Computer implements Comparable {
	private double cost;
	private int HDSize;
	private int RAM;
	private String name;
	private double screenSize;
	private int screenRes;
	private int hasTouchScreen; //no booleans so need to use int;
	
	public Computer() {
		this(0, 0, 0, "", 0, 0, 0);
	}
	
	public Computer(double cost, int HDSize, int RAM, String name, double screenSize, int screenRes, int hasTouchScreen) {
		this.cost = cost;
		this.HDSize= HDSize;
		this.RAM = RAM;
		this.name = name;
		this.screenSize = screenSize;
		this.screenRes = screenRes;
		this.hasTouchScreen = hasTouchScreen;
	}
	
	public void setCost(double cost) {
		this.cost = cost;
	}
	
	public double getCost() {
		return this.cost;
	}
	
	public void setHDSize(int HDSize) {
		this.HDSize = HDSize;
	}
	
	public int getHDSize() {
		return this.HDSize;
	}
	
	public void setRAM(int RAM) {
		this.RAM = RAM; 
	}
	
	public int getRAM() {
		return this.RAM;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setScreenSize(double screenSize) {
		this.screenSize = screenSize;
	}
	
	public double getScreenSize() {
		return this.screenSize;
	}
	
	public void setScreenRes(int screenRes) {
		this.screenRes = screenRes;
	}
	
	public int getScreenRes() {
		return this.screenRes;
	}
	
	public int hasTouchScreen() {
		return this.hasTouchScreen;
	}
	
	public void setHasTouchScreen(int hasTouchScreen) {
		this.hasTouchScreen = hasTouchScreen;
	}
	
	public String getFormattedPrice() {
		NumberFormat currency = NumberFormat.getCurrencyInstance();
		return currency.format(this.cost);
	}
	
	public boolean equals(Object other) {
		if (other instanceof Computer) {
			Computer c2 = (Computer) other;
			return cost==c2.getCost() && HDSize==c2.getHDSize()
				   && RAM==c2.getRAM() && name.equals(c2.getName()) 
				   && screenSize==c2.getScreenSize() && screenRes==c2.getScreenRes()
				   && hasTouchScreen==c2.hasTouchScreen();
		}
		return false; 
	}
	
	public String toString() {
		return "Cost:        " + cost + "\n" +
			   "HDSize       " + HDSize + "\n" +
			   "RAM:         " + RAM + "\n" +
			   "Name:        " + name + "\n" +
			   "Screen Size: " + screenSize + "\n" +
			   "Screen Res:  " + screenRes + "\n" +
			   "TouchScreen: " + (hasTouchScreen == 0 ? "no" : "yes") + "\n";
	}
	
	//I've arbirtarily decided that one computer is "less" than another if its specs are weaker: less
	//RAM, smaller HD, smaller screen, lower screen res. 
	public int compareTo(Object other) {
		Computer c2 = (Computer) other;
		int n1 = this.RAM - c2.getRAM();
		int n2 = (this.HDSize - c2.getHDSize()); 
		int n3 = ( (int) this.screenSize - (int) c2.getScreenSize());
		int n4 = (this.screenRes - c2.getScreenRes());
		if ((n1 == 0) && (n2 == 0) && (n3 == 0) && (n4 == 0)) {
			return 0;
		} else if ((n2 == 0) && (n3 == 0) && (n4 ==0)) {
			return n1; 
		} else if ((n3 ==0) && (n4 == 0)) {
			return n2;
		} else if (n4 == 0) {
			return n3;
		} else {
			return n4;
		}
	}
}