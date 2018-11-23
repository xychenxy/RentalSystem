package Model;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

import Model.Exception.informationAlert;
import javafx.scene.control.DatePicker;


public abstract class RentalProperty implements RentalAction{
	private String propertyId;
	private String strName;
	private String strNum;
	private String suburb;
	private int numOfBedRoom;
	private String propertyType;
	private String img;
	private String description;
	private String statusString;
	private boolean isPropertyStatus = true;
	private RentalRecord rentalRecord = new RentalRecord();
	
	private DateTime maintainDate;  // for premium suite, to store the last maintain date 
	private String estMaintenanceDate; // when calling perform method, create today date and store into it.
	private DateTime nextCompleteDate; // for premium suite, to store the next complete date; 
	private boolean maintenance = true; // this mean this property can be performed maintenance
		
	
	
	public RentalProperty(String propertyId,String strName,String strNum,String suburb,int numOfBedRoom,String statusString,String propertyType,
			String img, String description) {
		this.propertyId = propertyId;
		this.strName = strName;
		this.strNum = strNum;
		this.suburb = suburb;
		this.numOfBedRoom = numOfBedRoom;
		this.propertyType = propertyType;
		this.img = img;
		this.description = description;
		this.statusString = statusString;
	}
	
	public RentalProperty(String propertyId, String strName, String strNum, String suburb, int numOfBedRoom,String statusString, String propertyType, 
			DateTime maintainDate,String img, String description) {
		this(propertyId, strName, strNum, suburb, numOfBedRoom, statusString, propertyType, img, description);
		this.maintainDate = maintainDate;
		this.nextCompleteDate = new DateTime(maintainDate,10);
	}
	
	public abstract void rentDate(String customer, DatePicker rentdate, DatePicker returnDate) throws informationAlert;
	
	public abstract void returnDate(DatePicker actuRe) throws informationAlert;
		
	public abstract void completeMaintenance(DatePicker completionDate) throws informationAlert;
	
		
	public String getCompleteAddress() {
		return strNum + " " + strName + " " + suburb;
	}
	
	public void performMaintenance() throws informationAlert {
//		check property status
//		check maintenance status
//		if match, create today time as maintenance date
		
		if(!isPropertyStatus()) {
			throw new informationAlert("Cannot perform maintenane, as being rented");
		}
		else if(!ismaintenance()) {
			throw new informationAlert("Cannot perform maintenane, as under maintenance");
		}
		else {
			DateTime today = new DateTime();
			setEstMaintenanceDate(today.getFormattedDate());
			setmaintenance(false);
			setStatusString("Maintenance");
			throw new informationAlert("You have performed this property successfully!");
		}
	} 
	
	
	public int calIntervalDays(String before,String after){ 
		// return value is (after - before), is used to computer interval days.
		// this method will be called by premium suite or apartment
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date bDate = null;
		Date aDate = null;
		try {
			bDate = sdf.parse(before); // 2018-08-01 with 2018-08-02; interval is 1;
			aDate = sdf.parse(after);
		}
		catch (ParseException e) {
//			System.out.println("Date Formate is wrong.//calIntervalDays");
		}
		long days = (aDate.getTime()-bDate.getTime())/(1000*3600*24);
		int daysInt = Integer.parseInt(String.valueOf(days));
		return daysInt;
	}
	
	
	public int[] calActAndLate(String actuReDay) { 
		// to computer the actually rental day and delay date
		// this method is used as computer fee
		
		int[] compareDay = new int[2];
		int temp = calIntervalDays(getRentalRecord().getEstReDate(), getRentalRecord().getActReDate());
		int temp1 = calIntervalDays(getRentalRecord().getRentDate(), getRentalRecord().getEstReDate());
		int temp2 = calIntervalDays(getRentalRecord().getRentDate(), actuReDay);
		if(temp>0) { // delay return
			compareDay[0] = temp1; // update rental fee
			compareDay[1] = temp; //update late fee
		}
		else {
			compareDay[0] = temp2; // update rental fee
			compareDay[1] = 0; //update late fee
		}
		return compareDay;
	}
	
	

	public String keepTwoDotDecimal(double s) {
//		this method focus on fee format
		
		DecimalFormat df = new DecimalFormat("#.00");
		if(s==0) return "0.00";
		return df.format(s);
	}
		
	public String getRecordDatails(String str,int i) {// record info; i is 3 or 6 
		String[] s = str.split(":");	
		String detail = "please attention to value of i";
		String former =  String.format("%-30s%s", "Record ID:", s[0])+"\n"+
						String.format("%-30s%s", "Rent Date:", s[1])+"\n"+
						String.format("%-30s%s", "Estimated Return Date:", s[2])+"\n";
		String later =  String.format("%-30s%s", "Actual Return Date:", s[3])+"\n"+
						String.format("%-30s%s", "Rental Fee:", s[4])+"\n"+
						String.format("%-30s%s", "Late Fee:", s[5])+"\n";
		if(i==3) detail = former;
		if(i==6) detail = former + later;
		return detail + "\n" +
			"----------------------------------------------";
	}
	
	public String getBasicPropertyInfo() {
		String detail = String.format("%-30s%s", "Property ID:", getPropertyId())+"\n"+
				String.format("%-30s%s", "Address:", getStrNum()+" "+getStrName()+" "+getSuburb())+"\n"+
				String.format("%-30s%s", "Type:", getPropertyType())+"\n"+
				String.format("%-30s%s", "Bedroom:", getNumOfBedRoom())+"\n";
		return  detail;
	}
	
//	public String getPropertyInfo() {
//		String info = getPropertyId()+":"+getStrNum()+":"+getStrName()+":"+getSuburb()+":"+getPropertyType()+":"+getNumOfBedRoom()
//			+":"+getStatusString()+":"+getMaintainDate()+":"+getImg()+":"+getDescription();
//		return info;
//	}
	
	public boolean isPropertyStatus() {
		return isPropertyStatus;
	}
	public void setPropertyStatus(boolean isPropertyStatus) {
		this.isPropertyStatus = isPropertyStatus;
	}
	
	public String getPropertyId() {
		return propertyId;
	}
	public String getStrName() {
		return strName;
	}
	public String getStrNum() {
		return strNum;
	}
	public String getSuburb() {
		return suburb;
	}
	public int getNumOfBedRoom() {
		return numOfBedRoom;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public RentalRecord getRentalRecord() {
		return rentalRecord;
	}
	public DateTime getMaintainDate() {
		return maintainDate;
	}
	public void setMaintainDate(DateTime maintainDate) {
		this.maintainDate = maintainDate;
	}
	public String getEstMaintenanceDate() {
		return estMaintenanceDate;
	}
	public void setEstMaintenanceDate(String estMaintenanceDate) {
		this.estMaintenanceDate = estMaintenanceDate;
	}
	public DateTime getNextCompleteDate() {
		return nextCompleteDate;
	}
	public boolean ismaintenance() {
		return maintenance;
	}
	public void setmaintenance(boolean maintenance) {
		this.maintenance = maintenance;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getStatusString() {
		return statusString;
	}

	public void setStatusString(String statusString) {
		this.statusString = statusString;
	}

	public void setNextCompleteDate(DateTime nextCompleteDate) {
		this.nextCompleteDate = nextCompleteDate;
	}
	
	
	
}
