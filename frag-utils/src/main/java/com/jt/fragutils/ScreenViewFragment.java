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

import android.os.Bundle;
import android.support.v4.app.Fragment;

/**
 * Abstract Fragment class that provides support for detecting whether the fragment is presenting
 * a new screen view vs being resumed after a rotation.
 */
public abstract class ScreenViewFragment extends Fragment
{
	private FragmentScreenViewHelper mScreenViewHelper = new FragmentScreenViewHelper();

	@Override
	public void onResume()
	{
		super.onResume();

		if (mScreenViewHelper.isNewScreenView(this))
		{
			onNewScreenView();
		}
	}

	/**
	 * Called during onResume when Fragment is being freshly presented (as opposed to
	 * being resumed due to a rotation).
	 */
	protected void onNewScreenView() {}
}
