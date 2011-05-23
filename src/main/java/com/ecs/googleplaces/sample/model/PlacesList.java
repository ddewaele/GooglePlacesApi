package com.ecs.googleplaces.sample.model;

import java.util.List;

import com.google.api.client.util.Key;

public class PlacesList {

	@Key
	public String status;

	@Key
	public List<Place> results;

}
