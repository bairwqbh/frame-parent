package per.cby.frame.gis.geometry;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * 几何要素集类
 * 
 * @author chenboyang
 *
 */
public class GeometryCollection extends Geometry implements List<Geometry> {

    /** 要素列表 */
    private List<Geometry> list = new ArrayList<Geometry>();

    /**
     * 实例化几何要素集
     */
    public GeometryCollection() {
        this(new SpatialReference());
    }

    /**
     * 实例化几何要素集
     * 
     * @param wkid 坐标系编号
     */
    public GeometryCollection(int wkid) {
        this(new SpatialReference(wkid));
    }

    /**
     * 实例化几何要素集
     * 
     * @param spatialReference 空间参考
     */
    public GeometryCollection(SpatialReference spatialReference) {
        this.spatialReference = spatialReference;
        this.type = Geometry.GEOMETRY_COLLECTION;
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<Geometry> iterator() {
        return list.iterator();
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(Geometry e) {
        return list.add(e);
    }

    @Override
    public boolean remove(Object o) {
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends Geometry> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends Geometry> c) {
        return list.addAll(index, c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public Geometry get(int index) {
        return list.get(index);
    }

    @Override
    public Geometry set(int index, Geometry element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, Geometry element) {
        list.add(index, element);
    }

    @Override
    public Geometry remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<Geometry> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<Geometry> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<Geometry> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }

}
