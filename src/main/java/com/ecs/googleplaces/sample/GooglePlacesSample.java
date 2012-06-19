package com.ecs.googleplaces.sample;

import com.ecs.googleplaces.sample.model.Place;
import com.ecs.googleplaces.sample.model.PlaceAutoComplete;
import com.ecs.googleplaces.sample.model.PlaceDetail;
import com.ecs.googleplaces.sample.model.PlacesAutocompleteList;
import com.ecs.googleplaces.sample.model.PlacesList;
import com.google.api.client.googleapis.GoogleHeaders;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.apache.ApacheHttpTransport;
import com.google.api.client.http.json.JsonHttpParser;
import com.google.api.client.json.jackson.JacksonFactory;

public class GooglePlacesSample {

	// Create our transport.
	private static final HttpTransport transport = new ApacheHttpTransport();
	
	// Fill in the API key you want to use.
	private static final String API_KEY = "INSERT YOUR API KEY HERE";
	
	// The different Places API endpoints.
	private static final String PLACES_SEARCH_URL =  "https://maps.googleapis.com/maps/api/place/search/json?";
	private static final String PLACES_AUTOCOMPLETE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?";
	private static final String PLACES_DETAILS_URL = "https://maps.googleapis.com/maps/api/place/details/json?";
	
	private static final boolean PRINT_AS_STRING = false;
	
	// Moscone Center, Howard Street, San Francisco, CA, United States
	double latitude = 37.784147;
	double longitude = -122.402115;
	
	// telenet
	//double latitude = 51.034823;
	//double longitude = 4.483774;

	
	public static void main(String[] args) throws Exception {
		GooglePlacesSample sample = new GooglePlacesSample();
		sample.performSearch();
		sample.performDetails("CnRtAAAATk9IL_xAKeSvHXp8_HgRIeYBg4WEKXPdaTp1SbYumSWBQOXsxCSIe1vE8wb3V4beQymGJrKXTUgpWXlnYIxoLCTijO-aMyObxzS_aQOAxTFQqfQohb9YuBddllTaeiDhNeTh8sB4LUP7BOYfu1o0zRIQpdJKnwdPABlgFUs3BIVTkhoUdmJJq1AIbISzW2JpY497I5lYIqo");
		sample.performAutoComplete();
	}
	
	
	
	
	
	public void performSearch() throws Exception {
		try {
			System.out.println("Perform Search ....");
			System.out.println("-------------------");
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_SEARCH_URL));
			request.url.put("key", API_KEY);
			request.url.put("location", latitude + "," + longitude);
			request.url.put("radius", 500);
			request.url.put("sensor", "false");
			
			if (PRINT_AS_STRING) {
				System.out.println(request.execute().parseAsString());
			} else {
				
				PlacesList places = request.execute().parseAs(PlacesList.class);
				System.out.println("STATUS = " + places.status);
				for (Place place : places.results) {
					System.out.println(place);
				}
			}
			

		} catch (HttpResponseException e) {
			System.err.println(e.response.parseAsString());
			throw e;
		}
	}
	
	public void performDetails(String reference) throws Exception {
		try {
			System.out.println("Perform Place Detail....");
			System.out.println("-------------------");
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_DETAILS_URL));
			request.url.put("key", API_KEY);
			request.url.put("reference", reference);
			request.url.put("sensor", "false");
			
			if (PRINT_AS_STRING) {
				System.out.println(request.execute().parseAsString());
			} else {
				PlaceDetail place = request.execute().parseAs(PlaceDetail.class);
				System.out.println(place);
			}

		} catch (HttpResponseException e) {
			System.err.println(e.response.parseAsString());
			throw e;
		}
	}
	
	
	
	public void performAutoComplete() throws Exception {
		try {
			System.out.println("Perform Autocomplete ....");
			System.out.println("-------------------------");
			
			HttpRequestFactory httpRequestFactory = createRequestFactory(transport);
			HttpRequest request = httpRequestFactory.buildGetRequest(new GenericUrl(PLACES_AUTOCOMPLETE_URL));
			request.url.put("key", API_KEY);
			request.url.put("input", "mos");
			request.url.put("location", latitude + "," + longitude);
			request.url.put("radius", 500);
			request.url.put("sensor", "false");
			PlacesAutocompleteList places = request.execute().parseAs(PlacesAutocompleteList.class);
			if (PRINT_AS_STRING) {
				System.out.println(request.execute().parseAsString());
			} else {
				for (PlaceAutoComplete place : places.predictions) {
					System.out.println(place);
				}
			}

		} catch (HttpResponseException e) {
			System.err.println(e.response.parseAsString());
			throw e;
		}
	}	
	
	public static HttpRequestFactory createRequestFactory(final HttpTransport transport) {
			   
		  return transport.createRequestFactory(new HttpRequestInitializer() {
		   public void initialize(HttpRequest request) {
		    GoogleHeaders headers = new GoogleHeaders();
		    headers.setApplicationName("Google-Places-DemoApp");
		    request.headers=headers;
		    JsonHttpParser parser = new JsonHttpParser();
		    parser.jsonFactory = new JacksonFactory();
		    request.addParser(parser);
		   }
		});
	}
}
