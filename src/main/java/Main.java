import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Main {
    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Application application = executeStartArgs(args);
        application.run();
    }

    private static Application executeStartArgs(String[] args) throws ParserConfigurationException, IOException, SAXException {
        Path osmFile = null;
        double minLat = -1;
        double minLon = -1;
        double maxLat = -1;
        double maxLon = -1;
        Path speedConfigFile = null;
        for (String arg : args) {
            String key = arg.substring(arg.indexOf(" "));
            String param = arg.substring(
                    arg.indexOf(" ") + 2,
                    args.length);
            switch (key) {
                case "osm:" -> {
                    try {
                        osmFile = Paths.get(
                                Objects.requireNonNull(Main.class.getResource(param)).toURI());
                    } catch (URISyntaxException exception) {
                        //Повторно запросить путь к osm
                    }
                }
                case "minLat:" -> minLat = Double.parseDouble(param);
                case "minLon:" -> minLon = Double.parseDouble(param);
                case "maxLat" -> maxLat = Double.parseDouble(param);
                case "maxLon" -> maxLon = Double.parseDouble(param);
                case "speedConfig" -> {
                    try {
                        speedConfigFile = Paths.get(
                                Objects.requireNonNull(Main.class.getResource(param)).toURI());
                    } catch (URISyntaxException exception) {
                        //Повторно запросить путь к osm
                    }
                }
            }
        }

        return new Application(osmFile, minLat, minLon, maxLat, maxLon, speedConfigFile);
    }
}
