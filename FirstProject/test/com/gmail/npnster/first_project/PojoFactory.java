package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.RailsMarker;


import net.karneim.pojobuilder.FactoryProperties;
import net.karneim.pojobuilder.GeneratePojoBuilder;

public class PojoFactory {
	
	@GeneratePojoBuilder
	public static RailsMarker createMarker() {
		return new RailsMarker();
		
	}
	
	@GeneratePojoBuilder
	public static MapMarker createMapMarker() {
		return new MapMarker();
		
	}

}
