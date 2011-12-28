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
        map = new TreeMap<Room.Type,Integer>();
        
    }

    public int getCount (Room.Type type) {
        if (type != null) {
            try {  return (map.get(type).intValue()); } 
            catch (Exception e) { }
        }  return 0; 
    }
    
    public void setCount (Room.Type type,int count) {
        if (type == null) return; 
        if (count > 0)  map.put(type, new Integer(count));
        else  map.remove(type);
    }
    
    public RoomCount getDecCount (Room.Type type) {
    
        RoomCount dec = new RoomCount();
        
        try {
            for (Map.Entry<Room.Type, Integer> me : this.map.entrySet()) {
                if (me.getKey() == type) {
                    int val = me.getValue().intValue() - 1;
                    if (val > 0) {
                        dec.map.put(type, new Integer(val));
                    }                    
                } else if (me.getValue().intValue() > 0) {
                    dec.map.put(me.getKey(), me.getValue());
                }
            }
        } catch (Exception e) { return new RoomCount(); }
        return dec;
    }

    @Override
    public String toString() {
        
        StringBuilder ret = new StringBuilder ( "RoomCount{ "); 
        
        for (Map.Entry<Room.Type, Integer> me : this.map.entrySet()) {
            ret.append(me.getKey()); ret.append(":"); 
            ret.append(me.getValue()) ; ret.append(" "); 
        }
        ret.append("}"); 
        return ret.toString();
    }
    
}
