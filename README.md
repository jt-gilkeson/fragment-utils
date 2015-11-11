# fragment-utils
Small library of classes useful for dealing with fragments

## SimpleFragmentActivity
Basic Activity that handles displaying a custom fragment.

### How to use it

Add SimpleFragmentActivity to your AndroidManifest
```xml
	<activity android:name="com.jt.activity.SimpleFragmentActivity" />
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
			.setTitle(getString(R.string.app_name))
			.create();
	
	testIntent.putExtra("MyArgument", "MyValue");
	
	startActivity(testIntent);
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
