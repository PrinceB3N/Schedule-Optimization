# 5pm-schedule-optimization

![Android CI](https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/workflows/Android%20CI/badge.svg?event=push)

__**Documentation**__
https://docs.google.com/document/d/1lqX9ADpm9k8lWUsylWCo2AAwucAzDUpmERm8nf_Pv3M/edit?usp=sharing

__**Project summary**__

This Android app helps you store events and gets the route between all the locations you need to go to in a given day! If you are a user that is new to the area, forgetful, or just want to optimize your travel, this app is perfect for you! Simply input your schedule with the addresses you need to go to in order, which will then display the routes between them in an adjustable map. The best part is that the app saves those routes, so all you have to do is click the "Get Routes" button again to display the routes of the current day!

__**Features**__

1. A Calendar to keep track of your events. Includes a ToDo list feature that would input events into your schedule with the set parameters.
2. A Map to track the routes between your individual events for a single day.

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
2. Add events to your schedule in the calendar by clicking the (+) button.
3. Enter the information of the event in their fields. If you want this event to be routed, check the "Add to my routes" checkbox. Select your travel mode for this event. The default is set to "Walking".
4. Click the (✓) to add the event, or the (x) to go back to the calendar.
5. Click the list symbol, next to the date, to see your todo list.
6. Add events to your todo list by clicking the (+) button. Select the priority of the event.
7. Add the todo list events to your calendar by clicking the "Add ToDo List to Schedule" button.
8. If there are at least two events in your calendar for the selected day, click the map button and the map will move to your routes and location. If permit the app to access your location data, click the top-right button on the map will move the map to your schedule.
  
**Known Problems**
- No options to store a subset of locations, nor advanced modification.
- Can only store one Schedule at a time.
- No way to customize these locations and routes.
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
