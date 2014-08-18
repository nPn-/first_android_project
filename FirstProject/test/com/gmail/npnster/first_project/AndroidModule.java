package com.gmail.npnster.first_project;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AndroidModule {
  private final Context mApplicationContext;

  public AndroidModule(Context applicationContext) {
	  System.out.println("inside test version of android module"); 
    mApplicationContext = applicationContext;
  }

  /**
   * Allow the application context to be injected but require that it be annotated with
   * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
   */
  @Provides @Singleton @ForApplication Context provideApplicationContext() {
    return mApplicationContext;
  }


}