package Model;

import Model.Exception.informationAlert;

import java.util.ArrayList;
import java.util.HashMap;


public class FlexiRentSystem {
	
	private HashMap<String, RentalProperty> flexiProperty = new HashMap<>();
	private ArrayList<String> propertyName = new ArrayList<>();
	

	public void readFromDB(String stNum,String stName,String suburb, String numberOfBedrooms,String propertyType,String status,String lastMainteance,String img, String deString) throws informationAlert {
//		handle data which come from DB;
//		check if flexiProperty contain this property, if no, create it
//		as for each property, will create an instance for them
//		as for each property, will create a unique Property ID
		
		String propertyId = createPropertyId(stNum,stName,suburb,propertyType);
		
		if(!flexiProperty.containsKey(propertyId)) {
			if(propertyType.equals("Apartment")) {
				Apartment A = new Apartment(propertyId, stName, stNum, suburb, Integer.valueOf(numberOfBedrooms), status,propertyType,img,deString);
				saveToProperties(A,propertyId);
			}
			
			if (propertyType.equals("Premium Suite")) {
				PremiumSuite P = new PremiumSuite(propertyId, stName, stNum, suburb,Integer.valueOf(numberOfBedrooms),status,propertyType,changeToDateTime(lastMainteance),img,deString);
				saveToProperties(P,propertyId);
			}
		}
	}
	
	private void saveToProperties(RentalProperty property, String propertyId) throws informationAlert{
//		if create an instance in readFromDB(); will call this method and save it to the hashMap
		
		if(flexiProperty.size()>=50 ) {
			throw new informationAlert("There are more than 50 properties in your DB, some data may not loading");
		}
		else {
			flexiProperty.put(propertyId, property);
			propertyName.add(propertyId);
		}
		
	}
	
	public String createPropertyId(String strNum, String strName, String suburb,String propertyTypeText) {
//		create a unique Property ID
//		this method is also used in addProperty function, so it is public
		
		String type = null;
		if(propertyTypeText.equals("Apartment")) type = "A_";
		if(propertyTypeText.endsWith("Premium Suite")) type = "S_";
		String id = type + strNum;
		String[] strNameList = strName.split(" ");
		for(int i=0;i<strNameList.length;i++) id = id + strNameList[i].toUpperCase().charAt(0);
		String[] suburbList = suburb.split(" ");
		for(int i=0;i<suburbList.length;i++) id = id + suburbList[i].toUpperCase().charAt(0);
		return id;
	}
	
	
	public DateTime changeToDateTime(String s1) { 
//		change string into DateTime format

		int[] ch = new int[3];
		String[] s = s1.split("/");
		for(int i=0;i<s.length;i++) {ch[i] = Integer.valueOf(s[i]);}
		DateTime maintain = new DateTime(ch[0], ch[1], ch[2]);
		return maintain;
	}
		
	
	public HashMap<String, RentalProperty> getFlexiProperty() {
		return flexiProperty;
	}
	
	public ArrayList<String> getPropertyName() {
		return propertyName;
	}

}