package Model;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import Model.Exception.informationAlert;
import javafx.scene.control.DatePicker;

public class Apartment extends RentalProperty{
	private int atLeastRentalDays;
	
	public Apartment(String propertyId,String strName,String strNum,String suburb,int numOfBedRoom,String statusString,
			String propertyType,String img, String description) {
		super(propertyId, strName, strNum, suburb, numOfBedRoom, statusString, propertyType, img, description);
	}
	
	public void rentDate(String customer, DatePicker rentDate, DatePicker returnDate) throws informationAlert { 
		// check property status
		// at least 2 or 3 day; return date - rent date >=2 or 3 ; no more than 28 days
		
		String rentday = localDateChangeToString(rentDate);
		String returnday = localDateChangeToString(returnDate);
		
		if(!isPropertyStatus()) {  
			throw new informationAlert("Sorry, this apartment has been rented.");
		}
		else if(!ismaintenance()) {
			throw new informationAlert("Sorry, this apartment under mainteance.");
		}
		else if(intervalPeriod(rentDate, returnDate) >= 29) {
			throw new informationAlert("the maximum rental days is 28.");
		}
		else if(weekend(rentDate.getValue()) == 5 || weekend(rentDate.getValue()) ==6) {
			atLeastRentalDays = 3;
			if(intervalPeriod(rentDate, returnDate) <= 2) {
				throw new informationAlert("From Friday to Saturday, the minimum rental day is 3 days.");
			}
			else {
				setPropertyStatus(false);
				setStatusString("Rented");
				getRentalRecord().createRecord(getPropertyId(), customer, rentday, returnday);
				throw new informationAlert("You have rented this property successfully! \n" + getRentalRecord().getDetails());
			}
		}
		else if(weekend(rentDate.getValue()) == 8) {
			atLeastRentalDays = 2;
			if(intervalPeriod(rentDate, returnDate) <= 1) {
				throw new informationAlert("From Sunday to Thursday the minimum rental day is 2 days.");
			}
			else {
				setPropertyStatus(false);
				setStatusString("Rented");
				getRentalRecord().createRecord(getPropertyId(), customer, rentday, returnday);
				throw new informationAlert("You have rented this property successfully! \n" + getRentalRecord().getDetails());
			}
		}
	}
	
	
	private int intervalPeriod(DatePicker rentDate, DatePicker returnDate) {
		Period intervalPeriod = Period.between(rentDate.getValue(), returnDate.getValue());
		return intervalPeriod.getDays();
	}
	
	private int weekend(LocalDate rentDate) {
//		if friday or saturday, return 5 or 6; else return 8
		
		DayOfWeek weekday = rentDate.getDayOfWeek();
		int today = 0;
		switch (weekday) {
		case FRIDAY:
			today = 5;
			break;
		case SATURDAY:
			today = 6;
			break;
		default:
			today = 8;
			break;
		}
		return today;
	}
	

	private String localDateChangeToString(DatePicker datePicker) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String selectDate = formatter.format(datePicker.getValue());
		return selectDate;
	}
	
	
	public void returnDate(DatePicker actuRe) throws informationAlert { 
		// check property status
		// check mainteance status
		// returnDate can not before than rentDate
		// as for apartment, actual return day also should be larger than 2 or 3
		
		String actuReDay = localDateChangeToString(actuRe);
		
		
		if(isPropertyStatus()) {
			throw new informationAlert("Can not return, as it is available");
		}
		else if(!ismaintenance()) {
			throw new informationAlert("Can not return, as it is under mainteance");
		}
		else if(calIntervalDays(getRentalRecord().getRentDate(), actuReDay)<= atLeastRentalDays-1 ) { 
			throw new informationAlert("The minimum rental day is " +atLeastRentalDays+" , can not return");
		}
		else if( calIntervalDays(getRentalRecord().getRentDate(), actuReDay)>=29) {
			throw new informationAlert("The maximum rental day is 28, can not return");
		}
		else {
			getRentalRecord().setActReDate(actuReDay);
			feeApartment(calActAndLate(actuReDay)[0],calActAndLate(actuReDay)[1]); 
			setPropertyStatus(true);  
			setStatusString("Available");
			getRentalRecord().updateRecord(); 
			throw new informationAlert("You have returned this property successfully! \n" + getRentalRecord().getDetails());
		}
	}
	
	
	public void completeMaintenance(DatePicker completionDate) throws informationAlert {
//		check property status, if being rented, no complete
//		check maintance status, if no mainteance, no complete
		
		if(!isPropertyStatus()) {
			throw new informationAlert("Apartment is being rented, no allow to perform maintenance");
		}
		else if(ismaintenance()) {
			throw new informationAlert("sorry, Apartment haven't performed maintenance, no allow complete maintenance");
		}
		else {
			setmaintenance(true);
			setStatusString("Available");
			throw new informationAlert("You have complete maintenance this property successfully!");
		}
	} 
	
	private void feeApartment(int n1, int n2) { // update the actually rental fee and delay return fee
		double p = 0.00;
		if(getNumOfBedRoom()==1) p=143;
		if(getNumOfBedRoom()==2) p=210;
		if(getNumOfBedRoom()==3) p=319;
		getRentalRecord().setRentalFee(keepTwoDotDecimal(n1*p));
		getRentalRecord().setLateFee(keepTwoDotDecimal(n2*p));
	}
	
	
	public String toString() { 

		return getPropertyId()+":"+getStrNum()+":"+getStrName()+":"+getSuburb()+":"+getPropertyType()+":"+getNumOfBedRoom()+":"+ getStatusString();			
	}
	

	public String getPropertyInfo() {
		String info = getPropertyId()+":"+getStrNum()+":"+getStrName()+":"+getSuburb()+":"+getPropertyType()+":"+getNumOfBedRoom()
			+":"+getStatusString()+":"+"null"+":"+getImg()+":"+getDescription();
		return info;
	}
	
	
	public String getDetails() {
		
		String detail = getBasicPropertyInfo();
	
		if(getRentalRecord().getRentalRecords()[0]==null) { // only check for property without any rental record
			detail = detail +   
					String.format("%-30s%s", "Status:", getStatusString())+"\n"+
					String.format("%-30s%s", "RENTAL RECORD:", "Empty")+"\n";
			return detail;
		}
		
		int k = 1;
		if(isPropertyStatus()) { // only check for the first one record, true mean can rent
			 k=0;
			detail = detail + 
					String.format("%-30s%s", "Status:", getStatusString())+"\n"+
					"------------------------------------------------------";			
		}
		else { detail = detail+
				String.format("%-30s%s", "Status:", getStatusString())+"\n"+
				getRecordDatails(getRentalRecord().getRentalRecords()[0],3); // have been rented
		} 
		
		for(int i = k;i<getRentalRecord().getRentalRecords().length;i++) {
			if(getRentalRecord().getRentalRecords()[i]==null) break; // if record is empty, over
			detail = detail + "\n" +getRecordDatails(getRentalRecord().getRentalRecords()[i],6)+"\n"; // handle each record
		}
		return detail;
	}
	
}
