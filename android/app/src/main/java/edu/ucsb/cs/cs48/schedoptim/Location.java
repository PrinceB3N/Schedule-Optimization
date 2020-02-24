package edu.ucsb.cs.cs48.schedoptim;

import com.google.android.gms.maps.model.LatLng;

class Location{
    private LatLng location;
    private String name;

    public Location(LatLng l, String n){
        this.location=l;
        this.name=n;
    }
    public Location(Location l){
        this(l.location,l.name);
    }
    public boolean equals(Location l1) {
        if(l1==null)
            return false;
        return this.location.equals(l1.location)
                && this.name.equals(l1.name);
    }
    public LatLng getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }
}