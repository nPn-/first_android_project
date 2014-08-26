package com.gmail.npnster.first_project;

import java.util.Arrays;
import java.util.List;

import android.content.Context;
import dagger.ObjectGraph;

public class Injector {

	private static Injector mInjector;
	private ObjectGraph mObjectGraph;
	private MyApp mApp;

	private Injector() {

	}

	public static Injector getInstance() {
		if (mInjector == null) {
			mInjector = new Injector();
		}
		return mInjector;
	}

	protected List<Object> getModules() {
		return Arrays.asList(
								new ApplicationModule(mApp),
								new AndroidModule(mApp)
				             );
	}

	public void inject(Object object) {
		getObjectGraph().inject(object);
	}
	
	public void injectWith(Object object, Object... modules) {
//		System.out.println(String.format("orig object graph = %s",getObjectGraph()));
//		System.out.println(String.format("modules  = %s",modules));
		ObjectGraph newGraph = getObjectGraph().plus(modules);
//		System.out.println("here");
//		System.out.println(String.format("***new  object graph = %s ***",newGraph));
		getObjectGraph().plus(modules).inject(object);
	}
	
	public ObjectGraph getObjectGraph() {
		return mObjectGraph;
	}
	
	public Injector initialize(MyApp app) {
		mApp = app;
		mObjectGraph = ObjectGraph.create(getModules().toArray());  
//		System.out.println(String.format("init object graph = %s",mObjectGraph.toString()));
		return mInjector;
				
	}

}

