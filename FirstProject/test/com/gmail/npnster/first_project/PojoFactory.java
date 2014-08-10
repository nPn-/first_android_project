package com.gmail.npnster.first_project;

import com.gmail.npnster.first_project.api_params.GetMapMarkersResponse.Marker;


import net.karneim.pojobuilder.FactoryProperties;
import net.karneim.pojobuilder.GeneratePojoBuilder;

public class PojoFactory {
	
	@GeneratePojoBuilder
	public static Marker createMarker() {
		return new Marker();
		
	}
	
//	@GeneratePojoBuilder
//	public static MapMarker createMapMarker() {
//		return new MapMarker();
//		
//	}

}
