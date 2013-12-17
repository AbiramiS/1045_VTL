package com.Activity;

import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

public class HomeScreen extends TabActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tabhost);
		Resources ressources = getResources(); 
		TabHost tabHost = getTabHost(); 
		  
		// Registration tab
		Intent intentRegistration = new Intent().setClass(this, User_Registration.class);
		TabSpec tabSpecRegistration = tabHost
			.newTabSpec("Registration")
			.setIndicator("", ressources.getDrawable(R.drawable.rg3))
			.setContent(intentRegistration); 
  
		// Login tab  
		Intent intentLogin = new Intent().setClass(this, User_Login.class);
		TabSpec tabSpecLogin = tabHost
			.newTabSpec("Login")
			.setIndicator("", ressources.getDrawable(R.drawable.ll3))
			.setContent(intentLogin);
		
		// About_Us tab
		Intent intentAboutUs = new Intent().setClass(this, About_Us.class);
		TabSpec tabSpecAboutUs = tabHost
			.newTabSpec("AboutUs")
			.setIndicator("", ressources.getDrawable(R.drawable.a1))
			.setContent(intentAboutUs);
		
		// Contact_Us tab
		Intent intentContactUs = new Intent().setClass(this, Contact_Us.class);
		TabSpec tabSpecContactUs = tabHost
			.newTabSpec("ContactUs")
			.setIndicator("", ressources.getDrawable(R.drawable.c1))
			.setContent(intentContactUs);
	
		// add all tabs 
		tabHost.addTab(tabSpecRegistration);
		tabHost.addTab(tabSpecLogin);
		tabHost.addTab(tabSpecAboutUs);
		tabHost.addTab(tabSpecContactUs);		
		
		//set Windows tab as default (zero based)
		tabHost.setCurrentTab(0);
	}

}