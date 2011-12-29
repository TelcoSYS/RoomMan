/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author cgc
 */
public class RoomSet {
    
    private LinkedList<Room> list;  // Set dont allow repeat object and rooms with same type are equals

    public RoomSet() {
        list = new LinkedList<Room> () ;
    }
    
    private RoomSet (RoomSet base, Room last) {
        list = new LinkedList<Room> (base.list) ;
        list.add(last);
    }
    
    public RoomSet Append (Room room) {
        return new RoomSet (this,room);
    }
    
    
}
