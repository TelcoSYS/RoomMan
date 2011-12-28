/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author cgc
 */
public class RoomCount {
    
    private Map<Room.Type,Integer> map;
    
    public RoomCount () {
        map = new TreeMap();
        
    }

    public int getCount (Room.Type type) {
        if (type != null) {
            try {  return (map.get(type).intValue()); } 
            catch (Exception e) { }
        }  return 0; 
    }
    
    public void setCount (Room.Type type,int count) {
        if (type == null) return; 
        try {  map.put(type, new Integer(count));
        } catch (Exception e) {  }
    }
    
}
