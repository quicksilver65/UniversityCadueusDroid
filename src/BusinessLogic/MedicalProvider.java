package BusinessLogic;

public class MedicalProvider {
	private int Id;
	private String FirstName;
	private String MiddleName;
	private String LastName;
	private String Address;
	private String City;
	private String State;
	private int ZipCode;
	private String Suite;
	private String Specialty;
	private String Facility;
	private long PhoneNumber;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getFirstName() {
		return FirstName;
	}

	public void setFirstName(String firstName) {
		FirstName = firstName;
	}

	public String getMiddleName() {
		return MiddleName;
	}

	public void setMiddleName(String middleName) {
		MiddleName = middleName;
	}

	public String getLastName() {
		return LastName;
	}

	public void setLastName(String lastName) {
		LastName = lastName;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public int getZipCode() {
		return ZipCode;
	}

	public void setZipCode(int zipCode) {
		ZipCode = zipCode;
	}

	public String getSuite() {
		return Suite;
	}

	public void setSuite(String suite) {
		Suite = suite;
	}

	public String getSpecialty() {
		return Specialty;
	}

	public void setSpecialty(String specialty) {
		Specialty = specialty;
	}

	public String getFacility() {
		return Facility;
	}

	public void setFacility(String facility) {
		Facility = facility;
	}

	public long getPhoneNumber() {
		return PhoneNumber;
	}

	public void setPhoneNumber(long phoneNumber) {
		PhoneNumber = phoneNumber;
	}

}
