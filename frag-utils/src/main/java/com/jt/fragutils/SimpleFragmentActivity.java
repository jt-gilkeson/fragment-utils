/*
 * Copyright (C) 2015 J.T. Gilkeson
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jt.fragutils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Basic Activity that handles displaying a custom fragment.
 */
public class SimpleFragmentActivity extends AppCompatActivity
{
	private static final String TAG           = "SimpleFragmentActivity";

	private static final String TITLE         = "sfaActivityTitle";
	private static final String THEME         = "sfaActivityTheme";
	private static final String FRAGMENT_NAME = "sfaFragmentName";
	private static final String FRAGMENT_TAG  = "sfaFragmentTag";

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		Bundle extras = getIntent().getExtras();

		// set theme if specified
		if (extras != null)
		{
			int theme = extras.getInt(THEME);
			if (theme > 0)
			{
				setTheme(theme);
			}
		}

		super.onCreate(savedInstanceState);

		if (extras == null || !extras.containsKey(FRAGMENT_NAME) || !extras.containsKey(FRAGMENT_TAG))
		{
			Log.e(TAG, "Missing Fragment Information");
			finish();
			return;
		}

		// set title if specified
		String title = extras.getString(TITLE);
		if (title != null)
		{
			setTitle(title);
		}

		if (savedInstanceState == null)
		{
			Fragment frag = Fragment.instantiate(this, extras.getString(FRAGMENT_NAME), extras);

			getSupportFragmentManager()
					.beginTransaction()
					.add(android.R.id.content, frag, extras.getString(FRAGMENT_TAG))
					.commit();
		}
	}

	public static class IntentBuilder implements TitleStringBuilder, TitleResourceBuilder
	{
		private Context mContext;
		private Intent mIntent;
		private String mFragmentName;
		private String mTag;

		/**
		 * Constructor using a context and fragment class for this builder and the SimpleFragmentActivity it creates.
		 *
		 * @param context The calling context being used to instantiate the activity.
		 * @param fragmentClass The fragment class that is to be launched inside this activity.
		 */
		public IntentBuilder(Context context, Class<?> fragmentClass)
		{
			this(context, SimpleFragmentActivity.class, fragmentClass);
		}

		/**
		 * Constructor using a context, derived activity class, and fragment class for this builder
		 * and the derived activity it creates.
		 *
		 * @param context The calling context being used to instantiate the activity.
		 * @param activityClass ass Optional activity class (use for inherited classes), defaults to SimpleFragmentActivity.
		 * @param fragmentClass The fragment class that is to be launched inside this activity.
		 */
		public IntentBuilder(Context context, Class<?> activityClass, Class<?> fragmentClass)
		{
			mContext = context;
			mIntent = new Intent(context, activityClass);
			mFragmentName = fragmentClass.getName();
		}

		/**
		 * Set the title using the specified string.
		 *
		 * @return This Builder object to allow for chaining of calls to set methods
		 */
		@Override
		public TitleStringBuilder setTitle(String title)
		{
			mIntent.putExtra(TITLE, title);
			return this;
		}

		/**
		 * Set the title using the specified resource id.
		 *
		 * @return This Builder object to allow for chaining of calls to set methods
		 */
		@Override
		public TitleResourceBuilder setTitle(int title)
		{
			mIntent.putExtra(TITLE, mContext.getString(title));
			return this;
		}

		/**
		 * Set the theme using the specified theme resource id.
		 *
		 * @return This Builder object to allow for chaining of calls to set methods
		 */
		@Override
		public IntentBuilder setTheme(int themeId)
		{
			mIntent.putExtra(THEME, themeId);
			return this;
		}

		/**
		 * Set the fragment tag using the specified string.
		 *
		 * @return This Builder object to allow for chaining of calls to set methods
		 */
		@Override
		public IntentBuilder setFragmentTag(String tag)
		{
			mTag = tag;
			return this;
		}

		/**
		 *  Creates an Intent to launch the SimpleFragmentActivity (or specified inherited Activity)
		 *  with the arguments supplied to this builder.  It does not start the activity.  This allows
		 *  the user to add additional extras before starting the activity.  It also allows the user to
		 *  decide whether to startActivity or startActivityForResult with this intent.
		 */
		@Override
		public Intent create()
		{
			mIntent.putExtra(FRAGMENT_NAME, mFragmentName);
			mIntent.putExtra(FRAGMENT_TAG, mTag == null ? mFragmentName : mTag);
			return mIntent;
		}
	}

	// These interfaces prevent setting title as both a string and an int
	public interface TitleStringBuilder
	{
		TitleStringBuilder setTitle(String title);
		TitleStringBuilder setTheme(int themeId);
		TitleStringBuilder setFragmentTag(String tag);
		Intent create();
	}

	public interface TitleResourceBuilder
	{
		TitleResourceBuilder setTitle(int titleResource);
		TitleResourceBuilder setTheme(int themeId);
		TitleResourceBuilder setFragmentTag(String tag);
		Intent create();
	}
}