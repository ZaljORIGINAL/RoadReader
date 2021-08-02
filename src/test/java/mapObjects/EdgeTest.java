package mapObjects;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class EdgeTest {

    @Test
    public void calculateLengthTest(){
        //Точки собраны с дороги: 42098194
        List<Node> nodes = new ArrayList<>();
        nodes.add(new Node(523086781L,55.7330839, 52.3910512));
        nodes.add(new Node(523086876L, 55.7330384, 52.3911574));
        nodes.add(new Node(7685119703L, 55.7329941, 52.3912285));
        nodes.add(new Node(523086878L, 55.7329701, 52.3912669));
        nodes.add(new Node(534108442L, 55.7327306, 52.3916358));
        nodes.add(new Node(7685098474L, 55.7327065, 52.3916784));
        nodes.add(new Node(534108441L, 55.73266, 52.3917607));
        nodes.add(new Node(523634933L, 55.7326383, 52.3918005));
        nodes.add(new Node(7685119784L, 55.7320391, 52.3927089));
        nodes.add(new Node(523086870L, 55.7316842, 52.3932469));
        nodes.add(new Node(7685119786L, 55.7316817, 52.3940243));
        nodes.add(new Node(523086968L, 55.7317156, 52.3941244));

        Edge edge = new Edge(
                1,
                nodes,
                60
        );

        //Ранее вычислимый результат 264.15647911734584
        double length = edge.getLength();
        if (length < 260 || length > 270)
            fail();
    }

    @Test
    public void calculatePointToPointDistanceTest(){
        //Дорога, id 56054390
        //Расстояние: 77 метров
        /*Node node1 = new Node(703583137L, 55.7533889, 52.4035223);
        Node node2 = new Node(703583265L, 55.7529123, 52.4026438);*/

        //Бульвар Бердаха, id 42392235
        //Расстояние: 750
        Node node1 = new Node(529157935L, 55.7517270, 52.4063369);
        Node node2 = new Node(529157927,  55.7565519, 52.3981278);

        double distance = Edge.calculatePointToPointDistance(node1, node2);

        //Ранее вычислимый результат 743.5996738753779
        System.out.println("Дистанция: " + distance);
        if (distance < 740 || distance > 750)
            fail();
    }
}