package mapObjects;

public abstract class ParseHelper {
    public static final String NODE_TAG = "node";
    public static final String WAY_TAG = "way";
    public static final String LINK_TO_NODE_TAG = "nd";
    public static final String OTHER_TAG = "tag"; //TODO Подумать над этим наименованием

    //Атрибуты объектов
    public static final String ID_ATTRIBUTE = "id";
    public static final String REF_ATTRIBUTE = "ref";
    public static final String KEY_ATTRIBUTE = "k";
    public static final String VALUE_ATTRIBUTE = "v";

    //Параметры объектов
    public static final String HIGHWAY_FIELD = "highway";
    public static final String ONEWAY_FIELD = "oneway";
    public static final String MAX_SPEED_FIELD = "maxspeed";

    public static final String[] MOTOR_ROADS_LIST = {
            "motorway", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dmotorway
            "trunk", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dtrunk
            "primary", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dprimary
            "secondary", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dsecondary
            "tertiary", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dtertiary
            "unclassified", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dunclassified
            "residential", //https://wiki.openstreetmap.org/wiki/Tag:highway%3Dresidential
            "motorway_link", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dmotorway_link
            "trunk_link", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dtrunk_link
            "primary_link", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dprimary_link
            "secondary_link", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dsecondary_link
            "tertiary_link", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dtertiary_link
            "living_street", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dliving_street
            "service", //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dservice
            "track" //https://wiki.openstreetmap.org/wiki/RU:Tag:highway%3Dtrack
    };

    public static final String[] WAY_DIRECTION = {
            "yes",
            "no",
            "-1",
            "reversible"
    };

    public static byte getWayDirectionType(String value){
        if (value.equals(WAY_DIRECTION[0]))
            return 0;
        else if (value.equals(WAY_DIRECTION[1]))
            return 1;
        else if (value.equals(WAY_DIRECTION[2]))
            return 2;
        else if (value.equals(WAY_DIRECTION[3]))
            return 3;

        return -1;
    }
}
