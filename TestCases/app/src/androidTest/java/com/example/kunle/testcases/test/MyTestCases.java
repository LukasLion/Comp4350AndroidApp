package com.example.kunle.testcases.test;

import com.example.kunle.testcases.MainActivity;
import com.robotium.solo.*;
import android.test.ActivityInstrumentationTestCase2;


public class MyTestCases extends ActivityInstrumentationTestCase2<MainActivity> {
  	private Solo solo;
  	
  	public MyTestCases() {
		super(MainActivity.class);
  	}

  	public void setUp() throws Exception {
        super.setUp();
		solo = new Solo(getInstrumentation());
		getActivity();
  	}
  
   	@Override
   	public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        super.tearDown();
  	}
  
	public void testRun() {
        //Wait for activity: 'com.example.kunle.testcases.MainActivity'
		solo.waitForActivity(com.example.kunle.testcases.MainActivity.class, 2000);
        //Click on ImageView
		solo.clickOnView(solo.getView(com.example.kunle.testcases.R.id.fab));
        //Click on Settings
		solo.clickInList(1, 0);
        //Assert that: 'Hello World!' is shown
		assertTrue("'Hello World!' is not shown!", solo.waitForText(java.util.regex.Pattern.quote("Hello World!"), 1, 20000, true, true));
	}
}
