package com.gmail.npnster.first_project;

import static org.mockito.Mockito.mock;

import javax.inject.Singleton;

import org.mockito.Mockito;

import dagger.Module;
import dagger.Provides;

@Module(injects = { MapMarker.class, MapMarkerTest.class })
public class ApplicationModule {

	@Provides @Singleton
	GoogleMapMarkerParameters provideGoogleMapMarkerParameters() {
		System.out.println("inside dagger - mock");
		return mock(GoogleMapMarkerParameters.class);

	}

}
