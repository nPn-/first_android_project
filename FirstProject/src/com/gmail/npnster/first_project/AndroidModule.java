package com.gmail.npnster.first_project;

import javax.inject.Singleton;

import android.content.Context;
import dagger.Module;
import dagger.Provides;

@Module(library = true)
public class AndroidModule {
  private final MyApp application;

  public AndroidModule(MyApp application) {
    this.application = application;
  }

  /**
   * Allow the application context to be injected but require that it be annotated with
   * {@link ForApplication @Annotation} to explicitly differentiate it from an activity context.
   */
  @Provides @Singleton @ForApplication Context provideApplicationContext() {
    return application;
  }


}