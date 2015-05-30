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

import android.content.res.Configuration;
import android.support.v4.app.Fragment;

/**
 * Helper class to keep track of whether fragment has been rotated vs being freshly viewed.
 */
public class FragmentScreenViewHelper
{
	private int mOrientation = Configuration.ORIENTATION_UNDEFINED;

	/**
	 * Call this method from onResume to check whether this is a new screen view (first time load
	 * or return from background - Not an orientation change)
	 *
	 * @param context Fragment
	 * @return whether this is a new screen view (not an orientation change)
	 */
	public boolean isNewScreenView(Fragment context)
	{
		boolean bNewView = true;

		int iNewOrientation = context.getResources().getConfiguration().orientation;
		if ((mOrientation != Configuration.ORIENTATION_UNDEFINED) && (iNewOrientation != mOrientation))
		{
			bNewView = false;
		}

		// Set the new orientation
		mOrientation = iNewOrientation;

		return bNewView;
	}
}
