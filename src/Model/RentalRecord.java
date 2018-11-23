package Model;

public class RentalRecord {

	private String[] rentalRecords;
	private String recordId;
	private String rentDate; 
	private String estReDate;
	private String actReDate = "null";
	private String rentalFee = "null";
	private String lateFee = "null";
	private String customerID;
	
	
	public RentalRecord() {
		this.rentalRecords = new String[10];
	}
	
	
	public void createRecord(String propertyId,String customer, String rentaldate, String estimatedate) { 
		customerID = customer;
		rentDate = rentaldate; // dd/MM/yyyy
		estReDate = estimatedate; // dd/MM/yyyy
		String[] tempList = rentDate.split("/"); // deal with the format
		String tempStr = tempList[0]+tempList[1]+tempList[2];
		recordId = propertyId+"_"+customer+"_"+tempStr;
		for(int i=9;i>0;i--) { rentalRecords[i] = rentalRecords[i-1];} // change position and then update the first one record
		rentalRecords[0] = recordId +":"+ rentDate +":"+ estReDate +":"+actReDate+":"+rentalFee+":"+lateFee;	
		System.out.println(getDetails());
	}
	
	public void updateRecord() {
		rentalRecords[0] = recordId +":"+ rentDate +":"+ estReDate +":"+actReDate+":"+rentalFee+":"+lateFee;
	}
	
	public String toString() {
		if(actReDate.equals("null")) { 
			return recordId+":"+rentDate+":"+estReDate+":"+"null"+":"+"null"+":"+"null";
		}
		return recordId+":"+rentDate+":"+estReDate+":"+actReDate+":"+rentalFee+":"+lateFee;
	}

	public String getDetails() {
		if(actReDate.equals("null")) { 
			return  String.format("%-30s%s", "Record ID:", recordId)+"\n"+
					String.format("%-30s%s", "Rent Date:", rentDate)+"\n"+
					String.format("%-30s%s", "Estimated Return Date:", estReDate);
		}
		else {
			return 	String.format("%-30s%s", "Record ID:", recordId)+"\n"+
					String.format("%-30s%s", "Rent Date:", rentDate)+"\n"+
					String.format("%-30s%s", "Estimated Return Date:", estReDate)+"\n"+
					String.format("%-30s%s", "Actual Return Date:", actReDate)+"\n"+
					String.format("%-30s%s", "Rental Fee:", rentalFee)+"\n"+
					String.format("%-30s%s", "Late Fee:", lateFee);
		}
	}
	
	
	public String[] getRentalRecords() {
		return rentalRecords;
	}

	public String getRecordId() {
		return recordId;
	}
	
	public String getRentDate() {
		return rentDate;
	}

	public String getEstReDate() {
		return estReDate;
	}

	public String getActReDate() {
		return actReDate;
	}

	public void setRentalFee(String rentalFee) {
		this.rentalFee = rentalFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}

	public void setActReDate(String actReDate) {
		this.actReDate = actReDate;
	}

	public String getCustomerID() {
		return customerID;
	}
		
}
