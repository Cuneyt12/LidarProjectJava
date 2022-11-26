package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class GetLidarInfo {

    private  List<Double> distance = Collections.synchronizedList(new ArrayList<>());
    private List<Double> angle = Collections.synchronizedList(new ArrayList<>());

    public GetLidarInfo(){
        System.out.println("oluşturuldu");
    }

    public List<Double> getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle.add(angle);
    }

    public List<Double> getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance.add(distance);
    }

    public int getDistanceSize(){
        return this.getDistance().size();
    }
}
