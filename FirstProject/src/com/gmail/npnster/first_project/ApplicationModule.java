package com.gmail.npnster.first_project;

import dagger.Module;
import dagger.Provides;

@Module(injects = MapMarker.class)
public class ApplicationModule {

	@Provides
	GoogleMapMarkerParameters provideGoogleMapMarkerParameters() {
		System.out.println("inside dagger -non mock");
		return new GoogleMapMarkerParameters();

	}

}
