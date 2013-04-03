package BusinessLogic;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import org.apache.http.protocol.HTTP;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class DataStore {

	public static ArrayList<String> Specialites = new ArrayList<String>();
	public static ArrayList<String> Cities = new ArrayList<String>();
	public static ArrayList<MedicalProvider> Providers = new ArrayList<MedicalProvider>();

	public static String storageName = "providerList";

	public static MedicalProvider ProviderById(int id){
		if(Providers!=null && Providers.size()!=0){
			for(MedicalProvider obj:Providers){
				if(obj.getId()==id)
					return obj;	
			}
		}
		return null;
	
	}
	
	public static double[] GetLatLong(int id){
		double[] results = new double[2];
		RestClient client = new RestClient(
				"http://www.azdevelop.net/client/mobileprovider/api/Provider/GetLatLong?Id="+id);
		try{
			client.execute(RequestMethod.GET);
			String response = client.getResponse();
			JSONObject obj = new JSONObject(response);
			double latitude = obj.getDouble("Latitude");
			double Longtitude = obj.getDouble("Longtitude");
			results=  new double[]{latitude,Longtitude};
		}catch(Exception ex){
			results = new double[2];
		}
		return results;
	}
	
	public static void LoadProviders(String specialty, String city,
			String lastName) {
		Providers.clear();
		RestClient client = new RestClient(
				"http://www.azdevelop.net/client/mobileprovider/api/Provider/GetProvidersByDimensionCollection");

		try {
			JSONArray array = new JSONArray();

			if (notEmpty(specialty)) {
				JSONObject obj = new JSONObject();
				obj.put("Selector", "specialty");
				obj.put("Parameter", specialty);
				array.put(obj);
			}
			if (notEmpty(city)) {
				JSONObject obj = new JSONObject();
				obj.put("Selector", "city");
				obj.put("Parameter", city);
				array.put(obj);
			}
			if (notEmpty(lastName)) {
				JSONObject obj = new JSONObject();
				obj.put("Selector", "lastName");
				obj.put("Parameter", lastName);
				array.put(obj);
			}

			if (array.length() == 0) {
				JSONObject obj = new JSONObject();
				obj.put("Selector", "Nothing");
				obj.put("Parameter", "Nothing");
				array.put(obj);
			}

			client.addHeader(HTTP.CONTENT_TYPE, "application/json");
			client.setJSONString(array.toString());
			try {
				client.execute(RequestMethod.POST);
				String response = client.getResponse();
				JSONArray mpArray = new JSONArray(response);
				for (int x = 0; x < mpArray.length(); x++) {
					JSONObject obj = mpArray.getJSONObject(x);
					MedicalProvider mp = new MedicalProvider();
					mp.setId(obj.getInt("Id"));
					mp.setFirstName(obj.getString("FirstName"));
					mp.setLastName(obj.getString("LastName"));
					mp.setAddress(obj.getString("Address"));
					mp.setCity(obj.getString("City"));
					mp.setFacility(obj.getString("Facility"));
					mp.setMiddleName(obj.getString("MiddleName"));
					mp.setPhoneNumber(obj.getLong("PhoneNumber"));
					mp.setSpecialty(obj.getString("Specialty"));
					mp.setZipCode(obj.getInt("ZipCode"));
					mp.setState(obj.getString("State"));
					mp.setSuite(obj.getString("Suite"));
					Providers.add(mp);
				}

			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static boolean notEmpty(String s) {
		return (s != null && s.length() > 0);
	}

	public static void LoadCities() {
		RestClient client = new RestClient(
				"http://www.azdevelop.net/client/mobileprovider/api/Provider/GetDimensions?field=city");

		try {
			client.execute(RequestMethod.GET);
			String response = client.getResponse();

			try {
				JSONArray array = new JSONArray(response);
				for (int x = 0; x < array.length(); x++) {
					Cities.add(array.getString(x));
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void LoadSpecialties() {
		RestClient client = new RestClient(
				"http://www.azdevelop.net/client/mobileprovider/api/Provider/GetDimensions?field=specialty");
		try {
			client.execute(RequestMethod.GET);
			String response = client.getResponse();

			try {
				JSONArray array = new JSONArray(response);
				for (int x = 0; x < array.length(); x++) {
					Specialites.add(array.getString(x));
				}
			} catch (JSONException e) {

				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static ArrayList<MedicalProvider> GetFavorites(FileInputStream fis) {
		ArrayList<MedicalProvider> list = new ArrayList<MedicalProvider>();
		try {
			byte[] byteArray = new byte[fis.available()];
			fis.read(byteArray, 0, byteArray.length);
			fis.close();
			String json = new String(byteArray);
			if (json.length() != 0) {
				list = ConvertJsonToProviderArray(json);
			}

		} catch (Exception ex) {
			list = new ArrayList<MedicalProvider>();
		}
		return list;
	}

	public static void SaveFavorites(FileOutputStream fos,
			ArrayList<MedicalProvider> list) {
		try {
			String data = ConvertProviderListToJson(list);
			fos.write(data.getBytes());
			fos.flush();
			fos.close();
		} catch (Exception ex) {
		}
	}

	private static String ConvertProviderListToJson(
			ArrayList<MedicalProvider> list) {
		String returnValue = null;
		try {
			JSONArray array = new JSONArray();
			for (MedicalProvider obj : list) {
				JSONObject jObj = new JSONObject();
				jObj.put("Id", obj.getId());
				jObj.put("FirstName", obj.getFirstName());
				jObj.put("MiddleName", obj.getMiddleName());
				jObj.put("LastName", obj.getLastName());
				jObj.put("Specialty", obj.getSpecialty());
				jObj.put("Address", obj.getAddress());
				jObj.put("Suite", obj.getSuite());
				jObj.put("City", obj.getCity());
				jObj.put("State", obj.getState());
				jObj.put("ZipCode", obj.getZipCode());
				jObj.put("PhoneNumber", obj.getPhoneNumber());
				array.put(jObj);
			}
			returnValue = array.toString();
		} catch (Exception ex) {
			returnValue = null;
		}
		return returnValue;
	}

	private static ArrayList<MedicalProvider> ConvertJsonToProviderArray(
			String json) {
		ArrayList<MedicalProvider> list = new ArrayList<MedicalProvider>();
		try {
			JSONArray mpArray = new JSONArray(json);
			for (int x = 0; x < mpArray.length(); x++) {
				JSONObject obj = mpArray.getJSONObject(x);
				MedicalProvider mp = new MedicalProvider();
				mp.setId(obj.getInt("Id"));
				mp.setFirstName(obj.getString("FirstName"));
				mp.setLastName(obj.getString("LastName"));
				mp.setAddress(obj.getString("Address"));
				mp.setCity(obj.getString("City"));
				// mp.setFacility(obj.getString("Facility"));
				mp.setMiddleName(obj.getString("MiddleName"));
				mp.setPhoneNumber(obj.getLong("PhoneNumber"));
				mp.setSpecialty(obj.getString("Specialty"));
				mp.setZipCode(obj.getInt("ZipCode"));
				mp.setState(obj.getString("State"));
				mp.setSuite(obj.getString("Suite"));
				list.add(mp);
			}

		} catch (Exception ex) {
			list = new ArrayList<MedicalProvider>();
		}
		return list;
	}
}