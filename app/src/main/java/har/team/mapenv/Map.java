package har.team.mapenv;

/**
 * Created by Invix on 3/10/2018.
 */
import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class Map {
    private String mapId;
    private String mapLatitude;
    private String mapLongitude;

    public Map() {
        //this constructor is required
    }

    public Map(String mapId, String mapLatitude, String mapLongitude) {
        this.mapId = mapId;
        this.mapLatitude = mapLatitude;
        this.mapLongitude = mapLongitude;
    }

    public String getMapId() {
        return mapId;
    }

    public String getMapLatitude() {
        return mapLatitude;
    }

    public String getMapLongitude() {
        return mapLongitude;
    }
}
