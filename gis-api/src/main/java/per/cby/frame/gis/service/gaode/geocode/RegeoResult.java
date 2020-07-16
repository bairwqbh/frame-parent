package per.cby.frame.gis.service.gaode.geocode;

import java.util.ArrayList;

import per.cby.frame.gis.service.gaode.GaodeResult;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 逆向地理编码响应结果类
 * 
 * @author chenboyang
 *
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class RegeoResult extends GaodeResult {

    /** 逆向地理编码信息 */
    private Regeocode regeocode;

    /** 逆向地理编码信息列表 */
    private ArrayList<Regeocode> regeocodes;

    /**
     * 逆向地理编码信息类
     * 
     * @author chenboyang
     *
     */
    @Data
    @Accessors(chain = true)
    public static class Regeocode {

        /** 结构化地址信息 */
        private String formatted_address;

        /** 地址元素 */
        private AddressComponent addressComponent;

        /** poi信息列表 */
        private ArrayList<Poi> pois;

        /** 道路信息列表 */
        private ArrayList<Road> roads;

        /** 道路交叉口列表 */
        private ArrayList<Roadinter> roadinters;

        /** aoi信息列表 */
        private ArrayList<Aoi> aois;

        /**
         * 地址元素信息类
         * 
         * @author chenboyang
         *
         */
        @Data
        @Accessors(chain = true)
        public static class AddressComponent {

            /** 坐标点所在国家名称 */
            private String country;

            /** 坐标点所在省名称 */
            private String province;

            /** 坐标点所在城市名称 */
            private String city;

            /** 城市编码 */
            private String citycode;

            /** 坐标点所在区 */
            private String district;

            /** 行政区编码 */
            private String adcode;

            /** 坐标点所在乡镇、街道 */
            private String township;

            /** 乡镇街道编码 */
            private String towncode;

            /** 社区信息 */
            private Info neighborhood;

            /** 楼信息 */
            private Info building;

            /** 门牌信息 */
            private StreetNumber streetNumber;

            /** 所属海域 */
            private String seaArea;

            /** 经纬度所属商圈 */
            private ArrayList<BusinessArea> businessAreas;

            /**
             * 信息类
             * 
             * @author chenboyang
             *
             */
            @Data
            @Accessors(chain = true)
            public static class Info {

                /** 名称 */
                private String name;

                /** 类型 */
                private String type;

            }

            /**
             * 门牌信息类
             * 
             * @author chenboyang
             *
             */
            @Data
            @Accessors(chain = true)
            public static class StreetNumber {

                /** 街道名称 */
                private String street;

                /** 门牌号 */
                private String number;

                /** 坐标点 */
                private String location;

                /** 方向 */
                private String direction;

                /** 距离 */
                private double distance;

            }

            /**
             * 所属商圈类
             * 
             * @author chenboyang
             *
             */
            @Data
            @Accessors(chain = true)
            public static class BusinessArea {

                /** 商圈信息 */
                private String businessArea;

                /** 商圈中心点经纬度 */
                private String location;

                /** 商圈名称 */
                private String name;

                /** 商圈所在的地区编码 */
                private String id;

            }

        }

        /**
         * poi信息类
         * 
         * @author chenboyang
         *
         */
        @Data
        @Accessors(chain = true)
        public static class Poi {

            /** poi的id */
            private String id;

            /** poi点名称 */
            private String name;

            /** poi类型 */
            private String type;

            /** 电话 */
            private String tel;

            /** 方向 */
            private String direction;

            /** 该POI到请求坐标的距离 */
            private double distance;

            /** 坐标点 */
            private String location;

            /** poi地址信息 */
            private String address;

            /** poi信息权重 */
            private double poiweight;

            /** poi所在商圈名称 */
            private String businessarea;

        }

        /**
         * 道路信息类
         * 
         * @author chenboyang
         *
         */
        @Data
        @Accessors(chain = true)
        public static class Road {

            /** 道路id */
            private String id;

            /** 道路名称 */
            private String name;

            /** 道路到请求坐标的距离 */
            private String direction;

            /** 方位 */
            private double distance;

            /** 坐标点 */
            private String location;

        }

        /**
         * 道路交叉口类
         * 
         * @author chenboyang
         *
         */
        @Data
        @Accessors(chain = true)
        public static class Roadinter {

            /** 方位 */
            private String direction;

            /** 距离 */
            private double distance;

            /** 路口经纬度 */
            private String location;

            /** 第一条道路id */
            private String first_id;

            /** 第一条道路名称 */
            private String first_name;

            /** 第二条道路id */
            private String second_id;

            /** 第二条道路名称 */
            private String second_name;

        }

        /**
         * aoi信息类
         * 
         * @author chenboyang
         *
         */
        @Data
        @Accessors(chain = true)
        public static class Aoi {

            /** 所属aoi的id */
            private String id;

            /** 所属aoi名称 */
            private String name;

            /** 所属aoi所在区域编码 */
            private String adcode;

            /** 所属aoi中心点坐标 */
            private String location;

            /** 所属aoi点面积 */
            private double area;

            /** 距离 */
            private double distance;

            /** aoi类型 */
            private String type;

        }

    }

}
