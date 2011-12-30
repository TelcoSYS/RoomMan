/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;

/**
 *
 * @author cgc
 */
public class RoomSet  implements Comparable<RoomSet> {
    
    private LinkedList<Room> list;  // Set dont allow repeat object and rooms with same type are equals
    private static Comparator<Room> cmp ;
    private int wastage;
    private int hashCode;
    
    
    public RoomSet() {
        if (cmp == null) cmp = new Room.occSort();
        list = new LinkedList<Room> () ;
        hashCode = 0; wastage = 0;
    }
    
    private RoomSet (RoomSet base, Room last) {
        if (cmp == null) cmp = new Room.typeSort();
        list = new LinkedList<Room> (base.list) ;
        list.add(last);  Collections.sort(list, cmp); // requered for equals
        wastage = base.getWastage() + last.getWastage();
        hashCode = base.hashCode + last.getType().hashCode() * 3; 
    }
    
    public RoomSet Append (Room room) {
        return new RoomSet (this,room);
    }

    public int getRooms () {
       return list.size();    
    }
    
    public int getWastage () {
        return wastage;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RoomSet))  return false;
        final RoomSet other = (RoomSet) obj;
        if (this.hashCode != other.hashCode)  return false;
        if (this.list.size() != other.list.size()) return false;
        // segun codigo perl no evalua wastage ni ...
        Iterator<Room> itt = this.list.iterator();
        Iterator<Room> ito = other.list.iterator();
        while ( itt.hasNext() && ito.hasNext() ) {
            if (! itt.next().getType().equals(ito.next().getType())) return false;    
        }
        return true;
    }

    @Override
    public int hashCode() {
        return hashCode;
    }


    @Override
    public String toString() {
        return "RoomSet{rooms=" + getRooms() + ", wastage=" + wastage + ", list=" + list +  '}';
    }

    @Override
    public int compareTo(RoomSet obj) {
        if (obj == null ) return 0;
        
        if (this.wastage != obj.wastage) return (this.wastage - obj.wastage);
        return (this.list.size() - obj.list.size());
        
    }
    
    
}
