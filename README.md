[![Release](https://jitpack.io/v/jtv7/RippleSwitch.svg)](https://jitpack.io/#jtv7/RippleSwitch)

# RippleSwitch

This library is a custom Switch widget inspired by this [dribbble shot](https://dribbble.com/shots/4148855-Switcher-XXXIII). 

![GifSample](https://raw.githubusercontent.com/jtv7/RippleSwitch/master/switch.gif)

## Gradle 
Add the JitPack repository in your build.gradle at the end of repositories:
```
allprojects {
    repositories {
    	...
        maven { url 'https://jitpack.io' }
    }
}
```
And add the dependencies
```
dependencies {
    compile 'com.github.jtv7:RippleSwitch:1.0'
}
```
## Sample
Please see the [sample app](app/src/main) for a library usage example.

## Wiki
#### Usage:
Add RippleSwitch to your view hieararchy. Either programatically or using xml:
```xml
<com.jtv7.rippleswitchlib.RippleSwitch
        android:id="@+id/rippleSwitch"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"/>
```

### API
#### General

Default selection can be set using:
```xml
<com.jtv7.rippleswitchlib.RippleSwitch
  app:rs_checked="true|false"/>
```
Setting colors:
```xml
<com.jtv7.rippleswitchlib.RippleSwitch
  app:rs_checked_color="#FFFFFF"
  app:rs_unchecked_color="#2C2C2C"/>
```

Can also be set with the following setter methods:
```java
rippleSwitch.setChecked(true);
rippleSwitch.setCheckedColor(Color.WHITE);
rippleSwitch.setUncheckedColor(Color.parseColor("#2C2C2C"));                          
```

#### Other

Getting current checked state:
```java
rippleSwitch.isChecked()
```
#### Callback
To listen for the check changed events use:
```java
rippleSwitch.setOnCheckedChangeListener(this);

public interface OnCheckedChangeListener {
        void onCheckChanged(boolean checked);
    }

```

## License
```
Copyright 2018 jtv7

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
