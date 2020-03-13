# 5pm-schedule-optimization

![Android CI](https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/workflows/Android%20CI/badge.svg?event=push)

__**Project summary**__

This Android app helps you store events and gets the route between all the locations you need to go to in a given day! If you are a user that is new to the area, forgetful, or just want to optimize your travel, this app is perfect for you! Simply input your schedule with the addresses you need to go to in order, which will then display the routes between them in an adjustable map. The best part is that the app saves those routes, so all you have to do is click the "Get Routes" button again to display the routes of the current day!

__**Features**__

- When the application starts for the first time, there is a tutorial highlighting the features of the application.
- The application has a bottom navigation bar between the Calendar, Map and Settings.
- In the Calendar,
  - The user can add events for a particular day, specifying the time, location, and color label.
  - If the user wants the event to be routed in the map, they should check the “Add to my routes” checkbox and choose the travel mode for the event.
  - If they user wants to be notified of the event, they check the “Notify me!” switch.
  - The user can change the date of the calendar by clicking the date and choosing the date from the popup. The user could go to the previous and next day through the left and right arrow buttons.
  - Next to the date is the ToDo List button, which would allow the user to add events to the calendar with set duration parameters based on priority.
- In the Map,
  - The user could route their events for the day specified in the Calendar (i.e. if the Calendar is set to March 12, the Map will the route the events of March 12).
  - The user could show their current location on the map by pressing the upper left button.
  - The Map displays the locations of the events with the routes color coded in a list below the map. The list includes information like when the user should begin traveling in order to arrive to their event on time.
- In the Settings,
  - The user could delete all the tasks and todos for the current day.
  - The user could delete all the application data from the device.
  - The user could review the contents of the tutorial again.

__**Installation Prerequisites**__

Your Android devices/emulator must run on Android Marshmallow/6.0 (API 23) or newer.
  
__**Dependencies**__

Google Maps API: Performs map based actions like displaying, drawing, and moving.  
Google Directions API: Allows for app to send https requests and grab directions to be displayed on the map.  
GSON: Convert between Classes and Json files.    

__**Installation Steps**__

Running on real devices or standalone emulator (not in Android Studio):  
1. Download the latest version of our APK file from  
`GitHub release:`https://github.com/ucsb-cs48-w20/5pm-schedule-optimization/releases/download/v1.1/ScheduleOptimiztion-final.apk  
or  
`Google Drive link:` https://drive.google.com/file/d/12UpLTgH5u6mfCoCGoJT2a__983JNLdxo/view?usp=sharing
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
  
**Contributing**

Fork it!
Create your feature branch: git checkout -b my-new-feature
Commit your changes: git commit -am 'Add some feature'
Push to the branch: git push origin my-new-feature
Submit a pull request :D
  
**License**
https://choosealicense.com/licenses/mit/
