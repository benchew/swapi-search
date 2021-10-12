package com.benchew.swapisearch;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.SubmitLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.LambdaModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;

import com.benchew.swapisearch.SWAPI.Film;
import com.benchew.swapisearch.SWAPI.People;
import com.benchew.swapisearch.SWAPI.Species;
import com.benchew.swapisearch.SWAPI.Starship;
import com.benchew.swapisearch.SWAPI.Vehicle;

public class HomePage extends WebPage {
	private static final long serialVersionUID = 1L;
	private String searchName;
	private Integer count;
	private SWAPI.SearchResult searchResult;
	
	public HomePage(final PageParameters parameters) {
		super(parameters);
	}
	
	@Override
	@SuppressWarnings("serial")
	protected void onInitialize() {
		super.onInitialize();
			
		queue(new Form<Void>("form") {
			@Override
			protected void onSubmit() {
				super.onSubmit();
				
				SWAPI swapi = new SWAPI();
				
				searchResult = swapi.peopleSearch(searchName);
				count = searchResult.count;
			}
		});
		queue(new TextField<String>("searchName", LambdaModel.of(this::getSearchName, this::setSearchName)));
		queue(new SubmitLink("search"));
		queue(new WebMarkupContainer("results") {
			@Override
			public boolean isVisible() {
				return (super.isVisible() && searchResult != null);
			}
		});	
		queue(new Label("searchString", LambdaModel.of(this::getSearchName)));		
		queue(new Label("count", LambdaModel.of(this::getCount, this::setCount)));
		queue(new Label("resultString", LambdaModel.of(() -> {
			return (getCount() == 1) ? "result" : "results";
		})));
		queue(new ListView<People>("people", LambdaModel.of(() -> {
			return getSearchResult().results;
		})) {
			@Override
			protected void populateItem(ListItem<People> item) {
				People people = item.getModelObject();
				
				item.add(new Label("name", people.name));
				item.add(new Label("birthYear", people.birth_year));
				item.add(new Label("gender", people.gender));
				item.add(new Label("height", people.height));
				item.add(new Label("mass", people.mass));
				item.add(new Label("hairColor", people.hair_color));
				item.add(new Label("eyeColor", people.eye_color));
				item.add(new Label("skinColor", people.skin_color));
				item.add(new Label("homeworld", people.homeworldPlanet.name));				
				item.add(new ListView<Film>("films", LambdaModel.of(() -> {
					return people.getFilmObjects();
				})) {
					@Override
					protected void populateItem(ListItem<Film> item) {
						Film film = item.getModelObject();
						
						item.add(new Label("filmTitle", film.title));
					}				
				});
				item.add(new ListView<Species>("species", LambdaModel.of(() -> {
					return people.getSpeciesObjects();
				})) {
					@Override
					protected void populateItem(ListItem<Species> item) {
						Species species = item.getModelObject();
						
						item.add(new Label("speciesName", species.name));
					}				
				});
				item.add(new ListView<Starship>("starships", LambdaModel.of(() -> {
					return people.getStarshipObjects();
				})) {
					@Override
					protected void populateItem(ListItem<Starship> item) {
						Starship starship = item.getModelObject();
						
						item.add(new Label("starshipName", starship.name));
					}				
				});
				item.add(new ListView<Vehicle>("vehicles", LambdaModel.of(() -> {
					return people.getVehicleObjects();
				})) {
					@Override
					protected void populateItem(ListItem<Vehicle> item) {
						Vehicle vehicle = item.getModelObject();
						
						item.add(new Label("vehicleName", vehicle.name));
					}				
				});
			}
		});
	}

	public String getSearchName() {
		return searchName;
	}

	public void setSearchName(String searchName) {
		this.searchName = searchName;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}
	
	public SWAPI.SearchResult getSearchResult() {
		return searchResult;
	}

	public void setSearchResult(SWAPI.SearchResult searchResult) {
		this.searchResult = searchResult;
	}
}
