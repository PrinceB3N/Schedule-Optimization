# 5pm-schedule-optimization

[![Actions Status](https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/workflows/Android%20CI/badge.svg)](https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/actions)

__**Link to Documentation**__
https://docs.google.com/document/d/1lqX9ADpm9k8lWUsylWCo2AAwucAzDUpmERm8nf_Pv3M/edit

__**Project summary**__

This Android app helps you get and store the fastest route between all the locations you need to go to in a given day!

__**Additional information about the project**__

If you are a user that is new to the area, forgetful, or just want to optimize your travel, this app is perfect for you! Simply type in the addresses you need to go to in order, which will then display the routes between them in an adjustable map. The best part is that the app saves those routes, so all you have to do is click the "Get Routes" button again to display it again!

__**Installation Prerequisites**__

Your Android devices/emulator must run on Android Marshmallow/6.0 (API 23) or newer.
  
__**Dependencies**__

Google Maps API: Performs map based actions like displaying, drawing, and moving.  
Google Directions API: Allows for app to send https requests and grab directions to be displayed on the map.  
GSON: Convert between Classes and Json files.    

__**Installation Steps**__

Running on real devices or standalone emulator (not in Android Studio):  
1. Download the latest version of our APK file from  
`GitHub release:`https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/releases/tag/Alpha  
or  
`Google Drive link:` https://drive.google.com/open?id=10MfhRckwyJCeOagAWYM_lPNlEgulmgbT
2. Find the APK file in Download application
3. Click it and follow the system instruction

Running on development environment (Android Studio):
1. Download the latest version of Android Studio here: https://developer.android.com/studio
2. Run Android Studio
3. Import project using this project's Github SSH
  
**Functionality**

1. Run the app on an emulator or real Android device.
2. Add new locations that you want routes between by clicking on the (+) button.
3. Enter the address of the place in the top text box.
4. Click "Add" to add the location, and "Cancel" to just go back to the map.
5. Repeat 2,3, and 4 as necessary.
6. Click the button under the map and the map will move to your routes and locations.
7. Done!
Note: The app stores your locations after you do #6, so if you reboot the app, you may jump to #6.
  
**Known Problems**
- No options to store a subset of locations, nor advanced modification.
- Can only store one Schedule at a time.
- No way to only display locations, nor change their color.
- No way to customize these locations and routes.
- Accidentally putting a wrong address will mean you have to restart the process.
- File management: Views must specify the file locations + Multiple instances of the Controller which store those file locations 
  might mean bad news if we decide to store more Schedules...
  
**Contributing**

Fork it!
Create your feature branch: git checkout -b my-new-feature
Commit your changes: git commit -am 'Add some feature'
Push to the branch: git push origin my-new-feature
Submit a pull request :D
  
**License**
https://choosealicense.com/licenses/mit/
