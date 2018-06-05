package com.pluarsight.xpath;

public class Address {

	 private String addressType;
	    private String street1;
	    private String street2;
	    private String city;
	    private String state;
	    private String zipCode;

	    public String getAddressType() {
	        return addressType;
	    }

	    public void setAddressType(String addressType) {
	        this.addressType = addressType;
	    }

	    public String getStreet1() {
	        return street1;
	    }

	    public void setStreet1(String street1) {
	        this.street1 = street1;
	    }

	    public String getStreet2() {
	        return street2;
	    }

	    public void setStreet2(String street2) {
	        this.street2 = street2;
	    }

	    public String getCity() {
	        return city;
	    }

	    public void setCity(String city) {
	        this.city = city;
	    }

	    public String getState() {
	        return state;
	    }

	    public void setState(String state) {
	        this.state = state;
	    }

	    public String getZipCode() {
	        return zipCode;
	    }

	    public void setZipCode(String zipCode) {
	        this.zipCode = zipCode;
	    }

	    @Override
	    public String toString() {
	        return "Address{" +
	                "addressType='" + addressType + '\'' +
	                ", street1='" + street1 + '\'' +
	                ", street2='" + street2 + '\'' +
	                ", city='" + city + '\'' +
	                ", state='" + state + '\'' +
	                ", zipCode='" + zipCode + '\'' +
	                '}';
	    }
}
