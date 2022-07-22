package com.marsh.mpext.postgis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.postgis.Point;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimplePoint {

    private double x;

    private double y;


    public SimplePoint(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    public Point toGis2Point() {
        Point point = new Point();
        point.setSrid(4326);
        point.setX(this.x);
        point.setY(this.y);
        point.dimension = 2;
        return point;
    }
}
