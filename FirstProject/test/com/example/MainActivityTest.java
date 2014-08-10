package com.example;



	import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.gmail.npnster.first_project.MainActivity;
import com.gmail.npnster.first_project.R;

	import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

	@RunWith(RobolectricTestRunner.class)
	public class MainActivityTest {

	    MainActivity activity;

	    @Before
	    public void setup()
	    {
	        this.activity = Robolectric.buildActivity(MainActivity.class).create().get();
	    }

	    @Test
	    public void shouldHaveHappySmiles() throws Exception 
	    {
	        String hello = this.activity.getString(R.string.hello_world);
	        assertThat(hello, equalTo("Hello world!"));
	    }
	}
	

