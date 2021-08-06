package mapObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public abstract class OsmParseHelper {
    public static final String NODE_TAG = "node";
    public static final String WAY_TAG = "way";
    public static final String LINK_TO_NODE_TAG = "nd";
    public static final String TAG = "tag";

    //Атрибуты объектов
    public static final String ID_ATTRIBUTE = "id";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String KEY_ATTRIBUTE = "k";
    public static final String VALUE_ATTRIBUTE = "v";
    public static final String LAT_ATTRIBUTE = "lat"; //Долгота (ось x)
    public static final String LON_ATTRIBUTE = "lon"; //Широта (ось x)

    //Параметры объектов
    public static final String HIGHWAY_FIELD = "highway";
    public static final String ONEWAY_FIELD = "oneway";
    public static final String MAX_SPEED_FIELD = "maxspeed";

    public static final String MOTORWAY_TYPE = "motorway";
    public static final String TRUNK_TYPE = "trunk";
    public static final String PRIMARY_TYPE = "primary";
    public static final String SECONDARY_TYPE = "secondary";
    public static final String TERTIARY_TYPE = "tertiary";
    public static final String UNCLASSIFIED_TYPE = "unclassified";
    public static final String RESIDENTIAL_TYPE = "residential";
    public static final String MOTORWAY_LINK_TYPE = "motorway_link";
    public static final String TRUNK_LINK_TYPE = "trunk_link";
    public static final String PRIMARY_LINK_TYPE = "primary_link";
    public static final String SECONDARY_LINK_TYPE = "secondary_link";
    public static final String TERTIARY_LINK_TYPE = "tertiary_link";
    public static final String LIVING_STREET_TYPE = "living_street";
    public static final String SERVICE_TYPE = "service";
    public static final String TRACK_TYPE = "track";

    public static final double NO_ROAD_SPEED = -1;
}
