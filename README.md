# fragment-utils
Small library of classes useful for dealing with fragments

### Installation
Add the following dependency to your build.gradle file
```xml
	repositories {
		maven { url "https://jitpack.io" }
	}
	
	dependencies {
		compile 'com.github.jt-gilkeson:fragment-utils:1.0'
	}
```

## SimpleFragmentActivity
Basic Activity that handles displaying a custom fragment.

### How to use it

Add SimpleFragmentActivity to your AndroidManifest
```xml
	<activity android:name="com.jt.fragutils.SimpleFragmentActivity" />
```

#### Basic Usage
To launch your custom fragment in this wrapper activity, simply use the IntentBuilder and then start the activity.

```java
	Intent testIntent = new SimpleFragmentActivity.IntentBuilder(this, Fragment.class).create();
	startActivity(testIntent);
```

#### Advanced Options
The IntentBuilder allows you to customize the activity's title, theme, and fragment tag.  You can also add your own arguments for the fragment as extras.

```java
	Intent testIntent = new SimpleFragmentActivity.IntentBuilder(this, Fragment.class)
			.setFragmentTag("MyTag")
			.setTheme(R.style.AppTheme)
			.setTitle(R.string.app_name) // or .setTitle("title")
			.create();
	
	testIntent.putExtra("MyArgument", "MyValue");
	
	startActivity(testIntent);
```

You can also set a listener for navigation (back pressed or home pressed).

```java
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		// Get our parent activity
		SimpleFragmentActivity activity = (SimpleFragmentActivity)getActivity();
		activity.setNavigationListener(new FragmentNavigationListener()
		{
			@Override
			public boolean onBackPressed()
			{
				if (condition)
				{
					// Intercept the back button
					return true;
				}

				return false;
			}

			@Override
			public boolean onHomePressed()
			{
				return false;
			}
		});
	...
	}
```


#### Subclassing
You can also subclass this activity and use the IntentBuilder constructor that takes in the subclass (this allows you to customize the subclass's style, label, usage restrictions, etc in AndroidManifest).

```java
	Intent testIntent = new SimpleFragmentActivity.IntentBuilder(this, MySimpleFragmentActivity.class, Fragment.class).create();
	startActivity(testIntent);
```

## ScreenViewFragment
Abstract Fragment class that provides support for detecting whether the fragment is presenting a new screen view vs being resumed after a rotation.

### How to use it
Extend your fragments from ScreenViewFragment and then override `onNewScreenView()` with the desired behaviour when a new screen view event occurs.

```java
public CustomFragment extends ScreenViewFragment
{
	...

	@Override
	protected void onNewScreenView()
	{
		// Action to take when the screen is newly displayed (not due to rotation)
	}
}
```

## DisplayHeper
Utility class that has useful functions for dealing with Tablet vs Phone orietation issues.

### How to use it
Call static methods from DisplayHelper

```java
	@Override
	protected void onCreate()
	{
		// Lock the orientation for phones
		DisplayHelper.forcePortraitForPhone(getActivity());
		
		// Use a different layout manager for a tablet in landscape mode vs any device in portrait mode
		if (displayHelper.isTabletLandscape())
		{
			// Two columns for tablet landscape
			layoutManager = new GridLayoutManager(getContext(), 2);
		}
		else
		{
			layoutManager = new LinearLayoutManager(getContext());
		}
	}
```
