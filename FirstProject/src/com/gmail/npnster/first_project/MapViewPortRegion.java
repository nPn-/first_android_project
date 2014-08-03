package com.gmail.npnster.first_project;

import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;

public class MapViewPortRegion {
	
	Rect mScreenBounds;  
	RectF mMapBounds;
	LatLngBounds projectionBounds ;
	
	public MapViewPortRegion (Projection mapProjection ) {
		projectionBounds = mapProjection.getVisibleRegion().latLngBounds;
		mMapBounds = boundsToRectF(projectionBounds);
		Point lowerLeft = mapProjection.toScreenLocation(projectionBounds.southwest);
		Point upperRight = mapProjection.toScreenLocation(projectionBounds.northeast);
		mScreenBounds = new Rect(lowerLeft.x,upperRight.y,upperRight.x,lowerLeft.y);
		
	}
	
	public static RectF boundsToRectF(LatLngBounds bounds) {
		float bottom = (float) bounds.southwest.latitude;
		float top = (float) bounds.northeast.latitude;
		float left = (float) bounds.southwest.longitude;
		float right = (float) bounds.northeast.longitude;
		return new RectF(left,top,right,bottom);  
	}
	
	public static LatLngBounds rectFToBounds(RectF rectf) {
		LatLng southwest = new LatLng(rectf.bottom, rectf.left);
		LatLng northeast = new LatLng(rectf.top, rectf.right);
		LatLngBounds bounds = new LatLngBounds(southwest,northeast);
		return bounds;
	}
	
	public Point getScreenLL() {
		return new Point(mScreenBounds.bottom,mScreenBounds.left);
		
	}
	
	public Point getScreenUR() {
		return new Point(mScreenBounds.top,mScreenBounds.right);
		
	}
	
	public PointF getMapLL() {
		return new PointF(mMapBounds.bottom,mMapBounds.left);
		
	}
	
	public PointF getMapUR() {
		return new PointF(mMapBounds.top,mMapBounds.right);
		
	}
	
	public float getDegreesPerPixelX() {
		System.out.println(String.format("x map = %f, x screen = %d, ratio = %f", mMapBounds.width(),mScreenBounds.width(),mMapBounds.width() / mScreenBounds.width()));

		return mMapBounds.width() / mScreenBounds.width();
	}
	
	public float getDegreesPerPixelY() {
		return Math.abs(mMapBounds.height() / mScreenBounds.height());
	}
	
	public float pixelsToDegreesX(int pixels) {
		System.out.println(String.format("x pixels in = %d, deg out = %f", pixels, pixels * getDegreesPerPixelX()));
		return pixels * getDegreesPerPixelX();
	}
	
	public float pixelsToDegreesY(int pixels) {
		System.out.println(String.format("y pixels in = %d, deg out = %f", pixels, pixels * getDegreesPerPixelY()));
		return pixels * getDegreesPerPixelY();
	}
	
	public RectF getCheckingRectangle(int xPixelMargin, int yPixelMargin) {
		float xDegMargin = pixelsToDegreesX(xPixelMargin);
		float yDegMargin = pixelsToDegreesY(yPixelMargin);
		float left = mMapBounds.left + xDegMargin ;
		float top = mMapBounds.top - yDegMargin ;
		float right = mMapBounds.right - xDegMargin ;
		float bottom = mMapBounds.bottom + yDegMargin ;
		RectF checkingRectangle = new RectF(left, top, right, bottom);
		return checkingRectangle;
		
	}
	
	public boolean canViewPortSizeContain(LatLngBounds bounds, int xPixelMargin, int yPixelMargin) {
		RectF checkingRectangle = getCheckingRectangle(xPixelMargin, yPixelMargin);
		float boundsWidth = (float) Math.abs(bounds.northeast.longitude - bounds.southwest.longitude);
		float boundsHeight = (float) Math.abs(bounds.northeast.latitude - bounds.southwest.latitude);
		System.out.println(String.format("bw = %f, bh = %f, cw = %f, ch = %f", boundsWidth, boundsHeight, checkingRectangle.width(), Math.abs(checkingRectangle.height()) ));
		if (Math.abs(checkingRectangle.width()) >= boundsWidth && Math.abs(checkingRectangle.height()) >= boundsHeight) {
			return true;
		} else {
			return false;
		}
				
	}
	
	public LatLngBounds getNextBounds(LatLngBounds bounds, int xPixelMargin, int yPixelMargin) {
		LatLngBounds nextBounds = null;
		System.out.println(String.format("current map bounds = %s", projectionBounds));
		System.out.println(String.format("required bounds = %s", bounds));
		if (canViewPortSizeContain(bounds, xPixelMargin, yPixelMargin)) {
			System.out.println("map size is OK");
			RectF boundsRectF = boundsToRectF(bounds);
			System.out.println(String.format("cxb = %f, cxm = %f, cyb = %f, cym = %f", boundsRectF.centerX(), mMapBounds.centerX(),boundsRectF.centerY(), mMapBounds.centerY()));
			float deltaX = boundsRectF.centerX() - mMapBounds.centerX();
			float deltaY = boundsRectF.centerY() - mMapBounds.centerY();
			RectF nextRectF = new RectF(mMapBounds);
			nextRectF.offset(deltaX, deltaY);
			nextBounds = rectFToBounds(nextRectF);			
		} else {
			System.out.println("map is being expanded");
			System.out.println(String.format("lly = %f, llx = %f, dy = %f , dx = %f", bounds.southwest.latitude , bounds.southwest.longitude, pixelsToDegreesY(yPixelMargin) , pixelsToDegreesX(xPixelMargin) ));
			LatLng nextSouthwest = new LatLng(bounds.southwest.latitude - pixelsToDegreesY(yPixelMargin),
					bounds.southwest.longitude - pixelsToDegreesX(xPixelMargin)); 
			LatLng nextNortheast = new LatLng(bounds.northeast.latitude + pixelsToDegreesY(yPixelMargin),
					bounds.northeast.longitude + pixelsToDegreesX(xPixelMargin)); 
			nextBounds = new LatLngBounds(nextSouthwest, nextNortheast);
			
		}
		System.out.println(String.format("next bounds = %s", nextBounds));
		return nextBounds;
	}
	
	

}
