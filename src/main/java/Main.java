import Routers.GraphHopperRouter;
import Routers.Router;
import Routers.ZaljRouter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static Routers.CommandsHelper.*;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Router router = executeStartArgs(args);
        router.run();
    }

    private static Router executeStartArgs(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Path osmFile = null;
        double minLat = -1;
        double minLon = -1;
        double maxLat = -1;
        double maxLon = -1;
        Path speedConfigFile = null;

        String arg;
        for (int index = 1; index < args.length; index++) {
            arg = args[index];
            String key = arg.substring(0, arg.indexOf(" "));
            String param = arg.substring(
                    arg.indexOf(" ") + 1);
            switch (key) {
                case "osm:" -> osmFile = Paths.get(param);
                case "minLat:" -> minLat = Double.parseDouble(param);
                case "minLon:" -> minLon = Double.parseDouble(param);
                case "maxLat:" -> maxLat = Double.parseDouble(param);
                case "maxLon:" -> maxLon = Double.parseDouble(param);
                case "speedConfig:" -> speedConfigFile = Paths.get(param);
            }
        }

        if (args[0].equals(ZALJ_ROUTER))
            return new ZaljRouter(osmFile, minLat, minLon, maxLat, maxLon, speedConfigFile);
        else if (args[0].equals(GRAPH_HOPPER_ROUTER))
            return new GraphHopperRouter();

        return null;
    }
}
