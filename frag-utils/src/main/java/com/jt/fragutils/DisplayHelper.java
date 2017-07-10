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

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;

import com.jt.frag_utils.R;

/**
 * Helper for figuring out size and orientation of device
 */
public class DisplayHelper
{
	private final int DISPLAY_MODE_PHONE;
	private final int DISPLAY_MODE_TABLET;

	private int mDisplayMode;
	private int mOrientation;

	public DisplayHelper(Context context)
	{
		Resources res = context.getResources();

		DISPLAY_MODE_PHONE = res.getInteger(R.integer.display_mode_phone);
		DISPLAY_MODE_TABLET = res.getInteger(R.integer.display_mode_tablet);

		mDisplayMode = res.getInteger(R.integer.current_display_mode);
		mOrientation = res.getConfiguration().orientation;
	}

	public static boolean forcePortraitForPhone(Activity activity)
	{
		DisplayHelper displayHelper = new DisplayHelper(activity);

		if (!displayHelper.isTablet())
		{
			// lock screen to portrait for phones
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
			return true;
		}

		return false;
	}

	public static void restoreNormalRotation(Activity activity)
	{
		if (activity != null)
		{
			activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
		}
	}

	public boolean isTablet()
	{
		return mDisplayMode == DISPLAY_MODE_TABLET;
	}

	public boolean isTabletLandscape()
	{
		return mDisplayMode == DISPLAY_MODE_TABLET && mOrientation == Configuration.ORIENTATION_LANDSCAPE;
	}

	public boolean isTabletPortrait()
	{
		return mDisplayMode == DISPLAY_MODE_TABLET && mOrientation == Configuration.ORIENTATION_PORTRAIT;
	}
}
