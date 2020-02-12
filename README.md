# 5pm-schedule-optimization

**Schedule Optimization/n
Project summary**

This Android app helps you get and store the fastest route between all the locations you need to go to in a given day!

__**Additional information about the project**__

If you are a user that is new to the area, forgetful, or just want to optimize your travel, this app is perfect for you! Simply type in the addresses you need to go to in order, which will then display the routes between them in an adjustable map. The best part is that the app saves those routes, so all you have to do is click the "Get Routes" button again to display it again!
__**Installation
Prerequisites**__

__**Dependencies**__

Google Maps API: Performs map based actions like displaying, drawing, and moving.
Google Directions API: Allows for app to send https requests and grab directions to be displayed on the map.
GSON: Convert between Classes and Json files./n

__**Installation Steps**__
**TODO:** Describe the installation process (making sure you give complete instructions to get your project going from scratch). Instructions need to be such that a user can just copy/paste the commands to get things set up and running. Note that with the use of GitHub Actions, these instructions can eventually be fully automated (e.g. with act, you can run GitHub Actions locally).

1. Download the latest version of Android Studio here: https://developer.android.com/studio
2. Run Android Studio
3. Import project using this project's Github SSH
/n
**Functionality**

1. Run the app on an emulator or real Android device.
2. Add new locations that you want routes between by clicking on the (+) button.
3. Enter the address of the place in the top text box.
4. Click "Add" to add the location, and "Cancel" to just go back to the map.
5. Repeat 2,3, and 4 as necessary.
6. Click the button under the map and the map will move to your routes and locations.
7. Done!
Note: The app stores your locations after you do #6, so if you reboot the app, you may jump to #6.
/n
**Known Problems**
- No options to store a subset of locations, nor advanced modification.
- Can only store one Schedule at a time.
- Horrendous UI
- No way to only display locations, nor change their color.
- No way to customize these locations and routes.
- Accidentally putting a wrong address will mean you have to restart the process.
- File management: Views must specify the file locations + Multiple instances of the Controller which store those file locations 
  might mean bad news if we decide to store more Schedules...
/n
**Contributing**

Fork it!
Create your feature branch: git checkout -b my-new-feature
Commit your changes: git commit -am 'Add some feature'
Push to the branch: git push origin my-new-feature
Submit a pull request :D
/n
**License**
https://choosealicense.com/licenses/mit/

Main Google Map API key: `AIzaSyBOosE3N-P47nolpIZoNjN43y1mQq5uIhw`  
Backup key: `AIzaSyDPK90I_k-x7Yq5Y5Q3lupXDcWQ7s_h1K0`  
