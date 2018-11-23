package Model;


import java.time.format.DateTimeFormatter;

import Model.Exception.informationAlert;
import javafx.scene.control.DatePicker;


public class PremiumSuite extends RentalProperty{
	
	public PremiumSuite(String propertyId, String strName, String strNum, String suburb, int numOfBedRoom, String statusString,
			String propertyType, DateTime maintainDate,String img, String description) {
		super(propertyId, strName, strNum, suburb, numOfBedRoom, statusString, propertyType, maintainDate, img, description);
	}
	
	
	public void rentDate(String customer, DatePicker rentdate, DatePicker returnDate) throws informationAlert { // return true means it can be booked
		
		// check status
		// check maintenance status
		// check over the next completely maintenance day.
		
		String rentday = localDateChangeToString(rentdate);  // dd/mm/yyyy
		String returnday = localDateChangeToString(returnDate);
		
		if(!isPropertyStatus()) { 
			throw new informationAlert("Sorry, this premium suite has been rented.");
		}
		else if(!ismaintenance()) {
			throw new informationAlert("The Premium Suite is under maintenance.");	
		}
		else if( !(calIntervalDays(returnday, getNextCompleteDate().getFormattedDate())>0)) {
			throw new informationAlert("Sorry, you cannot rent the premium suite over the next completely maintenance day.");
		}
		else {
			setPropertyStatus(false);  
			setStatusString("Rented");
			getRentalRecord().createRecord(getPropertyId(), customer, rentday, returnday); 
			throw new informationAlert("You have rented this property successfully! \n" + getRentalRecord().getDetails());
		}
	}	
	
	
	private String localDateChangeToString(DatePicker datePicker) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String selectDate = formatter.format(datePicker.getValue());
		return selectDate;
	}
	
	
	public void returnDate(DatePicker actuRe) throws informationAlert{ 
//		check rent status
//		return date cannot before rent date
//		return date cannot over complete mainteance date
		
		String actuReturnDay = localDateChangeToString(actuRe);
		if(isPropertyStatus()) {
			throw new informationAlert("You don't need to return the premium suite, because you haven't rented it before.");
		}
		else if(!ismaintenance()) {
			throw new informationAlert("You don't need to return the premium suite, because it under mainteance.");
		}
		else {
			if(calIntervalDays(actuReturnDay, getNextCompleteDate().getFormattedDate())<=0) {
				throw new informationAlert("Sorry, you cannot return the premium suite over the next completely maintenance day.");
			}
			else if(calIntervalDays(getRentalRecord().getRentDate(), actuReturnDay)<=0) {
				throw new informationAlert("Your return day cannot before your rental day. ");
			} 
			else {
				getRentalRecord().setActReDate(actuReturnDay);
				feePremiumSuite(calActAndLate(actuReturnDay)[0],calActAndLate(actuReturnDay)[1]); // update the fee
				setPropertyStatus(true);
				setStatusString("Available");
				getRentalRecord().updateRecord();
				throw new informationAlert("You have returned this property successfully!\n" + getRentalRecord().getDetails());
			}
		} 
	}
	
	
	public void completeMaintenance(DatePicker completionDate) throws informationAlert {
//		check property status, if being rented, no complete
//		check maintance status, if no mainteance, no complete
		
		
		String completeday = localDateChangeToString(completionDate);
		
		if(!isPropertyStatus()) {
			throw new informationAlert("The Premium Suite is being rented, no allow to complete maintenance.");
		}
		else if(ismaintenance()) {
			throw new informationAlert("The Premium Suite is no under maintenance. pls perform maintenance at first.");
		}
		else {
			int intervalDay1 = calIntervalDays(getEstMaintenanceDate(), completeday);
			int intervalDay2 = calIntervalDays(completeday, getNextCompleteDate().getFormattedDate());
			if(intervalDay1<0 || intervalDay2<0){
				throw new informationAlert("complete maintenance day should between perform maintenance day and the next complete maintenance day.");
			}
			else {
				setmaintenance(true);
				setStatusString("Available");
				setMaintainDate(changeToDateTime(completeday));
				setNextCompleteDate(new DateTime(changeToDateTime(completeday),10));
				throw new informationAlert("You have completed maintenance this property successfully!");
			}
		}
	}
	
	
	private DateTime changeToDateTime(String s1) { 
		// change string into DateTime format 
		
		int[] ch = new int[3];
		String[] s = s1.split("/");
		for(int i=0;i<s.length;i++) {ch[i] = Integer.valueOf(s[i]);}
		DateTime maintain = new DateTime(ch[0], ch[1], ch[2]);
		return maintain;
	}
	
	
	private void feePremiumSuite(int n1, int n2) {
		getRentalRecord().setRentalFee(keepTwoDotDecimal(n1*554));
		getRentalRecord().setLateFee(keepTwoDotDecimal(n2*662));		
	}

	
	public String getPropertyInfo() {
		String info = getPropertyId()+":"+getStrNum()+":"+getStrName()+":"+getSuburb()+":"+getPropertyType()+":"+getNumOfBedRoom()
			+":"+getStatusString()+":"+getMaintainDate()+":"+getImg()+":"+getDescription();
		return info;
	}
	
	public String toString() {
		return getPropertyId()+":"+getStrNum()+":"+getStrName()+":"+getSuburb()+":"+getPropertyType()+":"+getNumOfBedRoom()+":"+getStatusString()+":"+getMaintainDate().getFormattedDate();
	}
	
	public String getDetails() {
		int k = 1;
		if(isPropertyStatus()) {
			k = 0;
		}
		
		
		String detail = getBasicPropertyInfo();
                
		if(getRentalRecord().getRentalRecords()[0]==null) { // only check for property without any rental record
			detail = detail +   
					String.format("%-30s%s", "Status:", "Available")+"\n"+
					String.format("%-30s%s", "Last maintenance:", getMaintainDate().getFormattedDate())+"\n"+
					String.format("%-30s%s", "RENTAL RECORD:", "Empty")+"\n";
			return detail;
		}
		
		if(isPropertyStatus()) { // only check for the first one record, true mean can rent
			detail = detail + 
					String.format("%-30s%s", "Status:", getStatusString())+"\n"+
					String.format("%-30s%s", "Last maintenance:", getMaintainDate().getFormattedDate())+"\n"+
					"----------------------------------------------";
		}
		else { detail = detail + 
				String.format("%-30s%s", "Status:", getStatusString())+"\n"+
				String.format("%-30s%s", "Last maintenance:", getMaintainDate().getFormattedDate())+"\n"+
				getRecordDatails(getRentalRecord().getRentalRecords()[0],3);} // have been rented
		
		for(int i = k;i<getRentalRecord().getRentalRecords().length;i++) {
			if(getRentalRecord().getActReDate()=="none" && getRentalRecord().getRentalRecords()[1]==null) break;
			if(getRentalRecord().getRentalRecords()[i]==null) break; // if record is empty, over
			detail = detail +"\n" + getRecordDatails(getRentalRecord().getRentalRecords()[i],6)+"\n"; // handle each record
		}
		return detail;
	}

	
}
