package per.cby.frame.gis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import per.cby.frame.gis.geometry.Extent;
import per.cby.frame.gis.geometry.Geometry;
import per.cby.frame.gis.geometry.GeometryCollection;
import per.cby.frame.gis.geometry.MultiPoint;
import per.cby.frame.gis.geometry.Point;
import per.cby.frame.gis.geometry.Polygon;
import per.cby.frame.gis.geometry.Polyline;

import lombok.experimental.UtilityClass;

/**
 * WKT帮助类
 * 
 * @author chenboyang
 * 
 */
@UtilityClass
public class WKTUtil {

    /** WKT点 */
    public final String POINT = "POINT";

    /** WKT点簇 */
    public final String MULTI_POINT = "MULTIPOINT";

    /** WKT线 */
    public final String LINESTRING = "LINESTRING";

    /** WKT多线 */
    public final String MULTI_LINESTRING = "MULTILINESTRING";

    /** WKT面 */
    public final String POLYGON = "POLYGON";

    /** WKT多面 */
    public final String MULTI_POLYGON = "MULTIPOLYGON";

    /** WKT要素集合 */
    public final String GEOMETRY_COLLECTION = "GEOMETRYCOLLECTION";

    /**
     * WKT转空间要素
     * 
     * @param wkt WKT数据
     * @return 空间要素
     */
    public Geometry wktToGeometry(String wkt) {
        Geometry geometry = null;
        if (StringUtils.isNotBlank(wkt)) {
            String shape = getShape(wkt);
            String type = shape.substring(0, shape.indexOf("(")).trim();
            switch (type) {
                case POINT:
                    geometry = wktToPoint(wkt);
                    break;
                case MULTI_POINT:
                    geometry = wktToMultiPoint(wkt);
                    break;
                case LINESTRING:
                    geometry = wktToPolyline(wkt);
                    break;
                case MULTI_LINESTRING:
                    geometry = wktToMultiPolyline(wkt);
                    break;
                case POLYGON:
                    geometry = wktToPolygon(wkt);
                    break;
                case MULTI_POLYGON:
                    geometry = wktToMultiPolygon(wkt);
                    break;
                case GEOMETRY_COLLECTION:
                    geometry = wktToMultiGeometryCollection(wkt);
                    break;
                default:
                    break;
            }
        }
        return geometry;
    }

    /**
     * 获取坐标系编号
     * 
     * @param wkt WKT数据
     * @return 坐标系编号
     */
    public int getSrid(String wkt) {
        int srid = 4326;
        String[] wktArr = wkt.split(";");
        if (wktArr.length >= 2) {
            String[] sridArr = wktArr[0].split("=");
            if (sridArr.length >= 2) {
                srid = Integer.valueOf(sridArr[1]);
            }
        }
        return srid;
    }

    /**
     * 获取几何数据
     * 
     * @param wkt WKT数据
     * @return 几何数据
     */
    public String getShape(String wkt) {
        String shape = "";
        String[] wktArr = wkt.split(";");
        if (wktArr.length >= 2) {
            shape = wktArr[1];
        } else {
            shape = wktArr[0];
        }
        return shape;
    }

    /**
     * WKT转点要素
     * 
     * @param wkt WKT数据
     * @return 点要素
     */
    public Point wktToPoint(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\((.+?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] coords = group.split(" ");
            if (coords != null && coords.length >= 2) {
                return new Point(Double.valueOf(coords[0].trim()), Double.valueOf(coords[1].trim()), srid);
            }
        }
        return null;
    }

    /**
     * WKT转点簇要素
     * 
     * @param wkt WKT数据
     * @return 点簇要素
     */
    public MultiPoint wktToMultiPoint(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\((.+?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] points = group.split(",");
            if (ArrayUtils.isNotEmpty(points)) {
                double[][] pointArray = new double[points.length][];
                for (int i = 0; i < points.length; i++) {
                    String[] coords = points[i].trim().split(" ");
                    if (coords != null && coords.length >= 2) {
                        pointArray[i] = new double[] { Double.valueOf(coords[0].trim()),
                                Double.valueOf(coords[1].trim()) };
                    }
                }
                return new MultiPoint(pointArray, srid);
            }
        }
        return null;
    }

    /**
     * WKT转线要素
     * 
     * @param wkt WKT数据
     * @return 线要素
     */
    public Polyline wktToPolyline(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\((.+?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] points = group.split(",");
            if (ArrayUtils.isNotEmpty(points)) {
                double[][] pointArray = new double[points.length][];
                for (int i = 0; i < points.length; i++) {
                    String[] coords = points[i].trim().split(" ");
                    if (coords != null && coords.length >= 2) {
                        pointArray[i] = new double[] { Double.valueOf(coords[0].trim()),
                                Double.valueOf(coords[1].trim()) };
                    }
                }
                return new Polyline(pointArray, srid);
            }
        }
        return null;
    }

    /**
     * WKT转多线要素
     * 
     * @param wkt WKT数据
     * @return 多线要素
     */
    public Polyline wktToMultiPolyline(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\(\\((.+?)\\)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] paths = group.split("\\),\\(");
            if (ArrayUtils.isNotEmpty(paths)) {
                double[][][] pathArray = new double[paths.length][][];
                for (int i = 0; i < paths.length; i++) {
                    String[] points = paths[i].trim().split(",");
                    if (ArrayUtils.isNotEmpty(points)) {
                        double[][] pointArray = new double[points.length][];
                        for (int j = 0; j < points.length; j++) {
                            String[] coords = points[j].trim().split(" ");
                            if (coords != null && coords.length >= 2) {
                                pointArray[j] = new double[] { Double.valueOf(coords[0].trim()),
                                        Double.valueOf(coords[1].trim()) };
                            }
                        }
                        pathArray[i] = pointArray;
                    }
                }
                return new Polyline(pathArray, srid);
            }
        }
        return null;
    }

    /**
     * WKT转面要素
     * 
     * @param wkt WKT数据
     * @return 面要素
     */
    public Polygon wktToPolygon(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\(\\((.+?)\\)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] points = group.split(",");
            if (ArrayUtils.isNotEmpty(points)) {
                double[][] pointArray = new double[points.length][];
                for (int i = 0; i < points.length; i++) {
                    String[] coords = points[i].trim().split(" ");
                    if (coords != null && coords.length >= 2) {
                        pointArray[i] = new double[] { Double.valueOf(coords[0].trim()),
                                Double.valueOf(coords[1].trim()) };
                    }
                }
                return new Polygon(pointArray, srid);
            }
        }
        return null;
    }

    /**
     * WKT转多面要素
     * 
     * @param wkt WKT数据
     * @return 多面要素
     */
    public Polygon wktToMultiPolygon(String wkt) {
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\(\\(\\((.+?)\\)\\)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] paths = group.split("\\)\\),\\(\\(");
            if (ArrayUtils.isNotEmpty(paths)) {
                double[][][] pathArray = new double[paths.length][][];
                for (int i = 0; i < paths.length; i++) {
                    String[] points = paths[i].trim().split(",");
                    if (ArrayUtils.isNotEmpty(points)) {
                        double[][] pointArray = new double[points.length][];
                        for (int j = 0; j < points.length; j++) {
                            String[] coords = points[j].trim().split(" ");
                            if (coords != null && coords.length >= 2) {
                                pointArray[j] = new double[] { Double.valueOf(coords[0].trim()),
                                        Double.valueOf(coords[1].trim()) };
                            }
                        }
                        pathArray[i] = pointArray;
                    }
                }
                return new Polygon(pathArray, srid);
            }
        }
        return null;
    }

    /**
     * WKT转要素集
     * 
     * @param wkt WKT数据
     * @return 要素集
     */
    public GeometryCollection wktToMultiGeometryCollection(String wkt) {
        GeometryCollection geometryCollection = null;
        int srid = getSrid(wkt);
        String shape = getShape(wkt);
        String regex = ".+?\\((.+?)\\)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(shape);
        if (matcher.matches()) {
            String group = matcher.group(1).trim();
            String[] geometrys = group.split(",");
            if (ArrayUtils.isNotEmpty(geometrys)) {
                geometryCollection = new GeometryCollection(srid);
                for (String string : geometrys) {
                    geometryCollection.add(wktToGeometry(string.trim()));
                }
            }
        }
        return geometryCollection;
    }

    /**
     * 空间要素转WKT数据
     * 
     * @param geometry 空间要素
     * @return WKT数据
     */
    public String geometryToWkt(Geometry geometry) {
        String wkt = null;
        if (geometry != null) {
            if (geometry instanceof Point) {
                wkt = pointToWkt((Point) geometry);
            } else if (geometry instanceof MultiPoint) {
                wkt = multiPointToWkt((MultiPoint) geometry);
            } else if (geometry instanceof Polyline) {
                Polyline polyline = (Polyline) geometry;
                if (polyline.getPaths().length > 1) {
                    wkt = multiPolylineToWkt(polyline);
                } else {
                    wkt = polylineToWkt(polyline);
                }
            } else if (geometry instanceof Polygon) {
                Polygon polygon = (Polygon) geometry;
                if (polygon.getRings().length > 1) {
                    wkt = multiPolygonToWkt(polygon);
                } else {
                    wkt = polygonToWkt(polygon);
                }
            } else if (geometry instanceof Extent) {
                wkt = polygonToWkt(((Extent) geometry).getPolygon());
            } else if (geometry instanceof GeometryCollection) {
                wkt = geometryCollectionToWkt((GeometryCollection) geometry);
            }
        }
        return wkt;
    }

    /**
     * 点要素转WKT数据
     * 
     * @param point 点要素
     * @return WKT数据
     */
    public String pointToWkt(Point point) {
        if (point != null && point.getCoords() != null && point.getCoords().length >= 2) {
            String f1 = "POINT(%s %s)";
            double[] coords = point.getCoords();
            return String.format(f1, coords[0], coords[1]);
        }
        return null;
    }

    /**
     * 点簇要素转WKT数据
     * 
     * @param multiPoint 点簇要素
     * @return WKT数据
     */
    public String multiPointToWkt(MultiPoint multiPoint) {
        if (multiPoint != null && ArrayUtils.isNotEmpty(multiPoint.getPoints())) {
            String f1 = "MULTIPOINT(%s)";
            double[][] points = multiPoint.getPoints();
            String[] pointArr = new String[points.length];
            for (int i = 0; i < points.length; i++) {
                if (points[i] != null && points[i].length >= 2) {
                    String f2 = "%s %s";
                    pointArr[i] = String.format(f2, points[i][0], points[i][1]);
                }
            }
            return String.format(f1, StringUtils.join(pointArr, ","));
        }
        return null;
    }

    /**
     * 线要素转WKT数据
     * 
     * @param polyline 线要素
     * @return WKT数据
     */
    public String polylineToWkt(Polyline polyline) {
        if (polyline != null && ArrayUtils.isNotEmpty(polyline.getPaths())
                && ArrayUtils.isNotEmpty(polyline.getPaths()[0])) {
            String f1 = "LINESTRING(%s)";
            double[][] points = polyline.getPaths()[0];
            String[] pointArr = new String[points.length];
            for (int i = 0; i < points.length; i++) {
                if (points[i] != null && points[i].length >= 2) {
                    String f2 = "%s %s";
                    pointArr[i] = String.format(f2, points[i][0], points[i][1]);
                }
            }
            return String.format(f1, StringUtils.join(pointArr, ","));
        }
        return null;
    }

    /**
     * 多线要素转WKT数据
     * 
     * @param polyline 多线要素
     * @return WKT数据
     */
    public String multiPolylineToWkt(Polyline polyline) {
        if (polyline != null && ArrayUtils.isNotEmpty(polyline.getPaths())) {
            String f1 = "MULTILINESTRING((%s))";
            double[][][] paths = polyline.getPaths();
            String[] pathsArr = new String[paths.length];
            for (int i = 0; i < paths.length; i++) {
                if (ArrayUtils.isNotEmpty(paths[i])) {
                    String[] pointArr = new String[paths[i].length];
                    for (int j = 0; j < paths[i].length; j++) {
                        if (paths[i][j] != null && paths[i][j].length >= 2) {
                            String f2 = "%s %s";
                            pointArr[j] = String.format(f2, paths[i][j][0], paths[i][j][1]);
                        }
                    }
                    pathsArr[i] = StringUtils.join(pointArr, ",");
                }
            }
            return String.format(f1, StringUtils.join(pathsArr, "),("));
        }
        return null;
    }

    /**
     * 面要素转WKT数据
     * 
     * @param polygon 面要素
     * @return WKT数据
     */
    public String polygonToWkt(Polygon polygon) {
        if (polygon != null && ArrayUtils.isNotEmpty(polygon.getRings())
                && ArrayUtils.isNotEmpty(polygon.getRings()[0])) {
            String f1 = "POLYGON((%s))";
            double[][] points = polygon.getRings()[0];
            String[] pointArr = new String[points.length];
            for (int i = 0; i < points.length; i++) {
                if (points[i] != null && points[i].length >= 2) {
                    String f2 = "%s %s";
                    pointArr[i] = String.format(f2, points[i][0], points[i][1]);
                }
            }
            return String.format(f1, StringUtils.join(pointArr, ","));
        }
        return null;
    }

    /**
     * 多面要素转WKT数据
     * 
     * @param polygon 多面要素
     * @return WKT数据
     */
    public String multiPolygonToWkt(Polygon polygon) {
        if (polygon != null && ArrayUtils.isNotEmpty(polygon.getRings())) {
            String f1 = "MULTIPOLYGON(((%s)))";
            double[][][] rings = polygon.getRings();
            String[] pathsArr = new String[rings.length];
            for (int i = 0; i < rings.length; i++) {
                if (ArrayUtils.isNotEmpty(rings[i])) {
                    String[] pointArr = new String[rings[i].length];
                    for (int j = 0; j < rings[i].length; j++) {
                        if (rings[i][j] != null && rings[i][j].length >= 2) {
                            String f2 = "%s %s";
                            pointArr[j] = String.format(f2, rings[i][j][0], rings[i][j][1]);
                        }
                    }
                    pathsArr[i] = StringUtils.join(pointArr, ",");
                }
            }
            return String.format(f1, StringUtils.join(pathsArr, ")),(("));
        }
        return null;
    }

    /**
     * 要素集转WKT数据
     * 
     * @param geometryCollection 要素集
     * @return WKT数据
     */
    public String geometryCollectionToWkt(GeometryCollection geometryCollection) {
        if (CollectionUtils.isNotEmpty(geometryCollection)) {
            String f1 = "GEOMETRYCOLLECTION(((%s)))";
            String[] geometrys = new String[geometryCollection.size()];
            for (int i = 0; i < geometrys.length; i++) {
                geometrys[i] = geometryToWkt(geometryCollection.get(i));
            }
            return String.format(f1, StringUtils.join(geometrys, ","));
        }
        return null;
    }

}
