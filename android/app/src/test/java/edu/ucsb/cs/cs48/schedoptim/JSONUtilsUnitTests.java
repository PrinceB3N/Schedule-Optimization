package edu.ucsb.cs.cs48.schedoptim;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class JSONUtilsUnitTests {
    @Test
    public void formatLocation_FormatPerfectInput_EqualsExpected_ReturnsTrue(){
        String formatted = JSONUtils.formatLocation("879 Embarcadero del Norte, Goleta, CA 93117");
        assertEquals("Equal",formatted, "879+Embarcadero+del+Norte+Goleta+CA+93117");
    }
    @Test
    public void formatLocation_FormatInput_NoCommas_EqualsExpected_ReturnsTrue(){
        String formatted = JSONUtils.formatLocation("879 Embarcadero del Norte Goleta CA 93117");
        assertEquals("Equal",formatted, "879+Embarcadero+del+Norte+Goleta+CA+93117");
    }
    @Test
    public void placesToURL_PerfectInput_EqualsExpected_ReturnsTrue(){
        ArrayList<String> locations = new ArrayList<>(Arrays.asList("879 Embarcadero del Norte, Goleta, CA 93117"
                                                        ,"525 UCEN Rd, Santa Barbara, CA 93106"));
        String url = JSONUtils.placesToUrl(locations,"bicycling");
        assertEquals("Equal",url,"https://maps.googleapis.com/maps/api/directions/json?origin=" +
                "879+Embarcadero+del+Norte+Goleta+CA+93117"+"&destination="+"525+UCEN+Rd+Santa+Barbara+CA+93106" +
                "&mode="+"bicycling"+"&key=AIzaSyDxNa3D3bhhY_-3IhwIixcHEUpuR-yJvm4");
    }
}
