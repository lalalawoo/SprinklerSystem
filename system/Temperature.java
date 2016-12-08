package system;

public class Temperature {
	int val;
	String unit;
	
	public Temperature(int val){
		this.val=val;
		this.unit = "F";
	}
	public Temperature(int val, String unit){
		this.val=val;
		this.unit = unit;
	}
	
	public void setTemp(int val){
		this.val = val;
	}
	
	public int getTemp(){
		return this.val;
	}
}
