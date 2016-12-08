package system;

//read temperature periodically
//send to system monitor
public class Sensor {
	
	String sensorID;
	Temperature currTemp;
	static int defaultTemp = 70;
	
	public Sensor(String id){
		sensorID=id;
		currTemp = new Temperature(defaultTemp);
	}	
	
	public int getTemperature(){
		return currTemp.val;
	}
	
	public void setSensorTemp(int val){
		currTemp.val = val;
	}

}
