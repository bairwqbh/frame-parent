package per.cby.frame.gis.util;

import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.OperatorBuffer;
import com.esri.core.geometry.OperatorImportFromWkt;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.geometry.WktImportFlags;

import lombok.experimental.UtilityClass;

/**
 * ESRI空间几何分析帮助类(未完善)
 * 
 * @author chenboyang
 *
 */
@UtilityClass
public class ESRIUtil {

    /**
     * 解析WKT数据
     * 
     * @param wkt WKT数据
     * @return 几何要素
     */
    public Geometry wktParser(String wkt) {
        return OperatorImportFromWkt.local().execute(WktImportFlags.wktImportDefaults,
                com.esri.core.geometry.Geometry.Type.Unknown, wkt, null);
    }

    /**
     * 缓冲区分区
     * 
     * @param geometry 几何要素
     * @param wkid     坐标系ID
     * @param distance 缓冲距离
     * @return 分析后的几何要素
     */
    public Geometry buffer(Geometry geometry, int wkid, double distance) {
        return OperatorBuffer.local().execute(geometry, SpatialReference.create(wkid), distance, null);
    }

}
