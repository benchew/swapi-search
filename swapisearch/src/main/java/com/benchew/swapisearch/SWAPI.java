package com.benchew.swapisearch;

import java.io.Serializable;
import java.util.ArrayList;

import com.benchew.swapisearch.utils.AsyncHttpClient;
import com.benchew.swapisearch.utils.SWAPISearchUtils;
import com.google.gson.Gson;

public class SWAPI {
	static final private String SWAPI_BASE_URL = "https://swapi.dev/api";
	static final private Gson gson = new Gson();
	static final private AsyncHttpClient asyncHttpClient = new AsyncHttpClient();
	
	public SearchResult peopleSearch(String searchString) {
		String url = SWAPI_BASE_URL + "/people/?search=" + SWAPISearchUtils.urlEncode(searchString);
		String searchResponse = asyncHttpClient.sendRequest(url);		
		SearchResult results = gson.fromJson(searchResponse, SearchResult.class); 
		
		for (People aPeople : results.results) {			
			String homeworldResponse = asyncHttpClient.sendRequest(aPeople.homeworld);
			aPeople.homeworldPlanet = gson.fromJson(homeworldResponse, Planet.class);
			
			for (String aFilmUrl : aPeople.films) {
				String filmResponse = asyncHttpClient.sendRequest(aFilmUrl);
				aPeople.getFilmObjects().add(gson.fromJson(filmResponse, Film.class)); 
			}
			
			for (String aSpeciesUrl : aPeople.species) {
				String speciesResponse = asyncHttpClient.sendRequest(aSpeciesUrl);
				aPeople.getSpeciesObjects().add(gson.fromJson(speciesResponse, Species.class)); 
			}
			
			for (String aStarshipUrl : aPeople.starships) {
				String starshipResponse = asyncHttpClient.sendRequest(aStarshipUrl);
				aPeople.getStarshipObjects().add(gson.fromJson(starshipResponse, Starship.class)); 
			}
			
			for (String aVehicleUrl : aPeople.vehicles) {
				String vehicleResponse = asyncHttpClient.sendRequest(aVehicleUrl);
				aPeople.getVehicleObjects().add(gson.fromJson(vehicleResponse, Vehicle.class)); 
			}
		}
		
		return results;
	}
	
	class SearchResult implements Serializable {
		private static final long serialVersionUID = 1L;

		Integer count;
		String next;
		String previous;
		ArrayList<People> results;
	}
	
	class People implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		String birth_year;
		String eye_color;
		String gender;
		String hair_color;
		String height;
		String mass;
		String skin_color;
		String homeworld; //url
		Planet homeworldPlanet;
		ArrayList<String> films; //urls
		private ArrayList<Film> filmObjects;
		ArrayList<String> species; //urls
		private ArrayList<Species> speciesObjects;
		ArrayList<String> starships; //urls
		private ArrayList<Starship> starshipObjects;
		ArrayList<String> vehicles; //urls
		private ArrayList<Vehicle> vehicleObjects;
		String url;
		String created;
		String edited;
		
		public ArrayList<Film> getFilmObjects() {
			 if (filmObjects == null) {
				 filmObjects = new ArrayList<Film>();
			 }
			 
			 return filmObjects;
		}
		
		public ArrayList<Species> getSpeciesObjects() {
			 if (speciesObjects == null) {
				 speciesObjects = new ArrayList<Species>();
			 }
			 
			 return speciesObjects;
		}
		
		public ArrayList<Starship> getStarshipObjects() {
			 if (starshipObjects == null) {
				 starshipObjects = new ArrayList<Starship>();
			 }
			 
			 return starshipObjects;
		}
		
		public ArrayList<Vehicle> getVehicleObjects() {
			 if (vehicleObjects == null) {
				 vehicleObjects = new ArrayList<Vehicle>();
			 }
			 
			 return vehicleObjects;
		}
	}
	
	class Film implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String title;
		String episode_id;
		String opening_crawl;
		String director;
		String producer;
		String release_date;
		ArrayList<String> species; //urls
		ArrayList<String> characters; //urls
		ArrayList<String> planets; //urls
		ArrayList<String> starships; //urls
		ArrayList<String> vehicles; //urls
		String url;
		String created;
		String edited;
	}
	
	class Species implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		String classification;
		String designation;
		String average_height;
		String average_lifespan;
		String eye_colors;
		String hair_colors;
		String skin_colors;
		String language;
		String homeworld; //url
		ArrayList<String> people; //urls
		ArrayList<String> films; //urls
		String url;
		String created;
		String edited;
	}
	
	//TODO: subclass of vehicle
	class Starship implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		String model;
		String starship_class;
		String manufacturer;
		String cost_in_credits;
		String length;
		String crew;
		String passengers;
		String max_atmosphering_speed;
		String hyperdrive_rating;
		String MGLT;
		String cargo_capacity;
		String consumables;
		ArrayList<String> films; //urls
		ArrayList<String> pilots; //urls
		String url;
		String created;
		String edited;
	}
	
	class Vehicle implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		String model;
		String vehicle_class;
		String manufacturer;
		String cost_in_credits;
		String length;
		String crew;
		String passengers;
		String max_atmosphering_speed;
		String cargo_capacity;
		String consumables;
		ArrayList<String> films; //urls
		ArrayList<String> pilots; //urls
		String url;
		String created;
		String edited;
	}
	
	class Planet implements Serializable {
		private static final long serialVersionUID = 1L;
		
		String name;
		String diameter;
		String rotation_period;
		String orbital_period;
		String gravity;
		String population;
		String climate;
		String terrain;
		String surface_water;
		ArrayList<String> residents; //urls
		ArrayList<String> films; //urls
		String url;
		String created;
		String edited;
	}
}
