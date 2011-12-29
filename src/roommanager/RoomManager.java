/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
/**
 *
 * @author cgc
 */
public class RoomManager {

    private Room[] rooms;
    private LinkedList<RoomSet> res;
    
    public RoomSet[] findBest (Set<Room> roomlist, RoomCount rcount, Pax pax) {
    
        rooms = (Room[]) roomlist.toArray(new Room[0]);
        Arrays.sort(rooms, new Room.occSort() );
        res = new LinkedList<RoomSet>();
        findBest_Loop ( rcount, pax, new RoomSet (), 1);
        return (RoomSet[]) res.toArray(new RoomSet[0]);
    }
    
    private void findBest_Loop (RoomCount rcount, Pax pax, RoomSet rref, int depth) {
        
        List<Room> ok = evalEachRoom (rcount, pax);
        System.out.println (Arrays.deepToString(ok.toArray()));
        Collections.sort (ok, new Room.okSort());
        System.out.println (Arrays.deepToString(ok.toArray()));
    }    
    
    private  List<Room> evalEachRoom (RoomCount rcount, Pax pax) {

        LinkedList<Room> ok = new LinkedList<Room>();
        
        for (Room room : rooms ) {
            
            if (rcount.getCount(room.getType()) <= 0 ) continue;
            Pax px = new Pax();
            
            if (room.getInfants() > 0 && pax.infants >0 && pax.adults > 0 ) {
                px.adults = 1; px.infants =  Math.min(room.getInfants(), pax.getInfants());
            }
            if (room.getChildrens() > 0 && pax.childrens >0 && pax.adults > 0 && px.adults + px.infants < room.getOccup() ) {
                px.adults = 1; px.childrens =  Math.min(pax.childrens, Math.min( room.getChildrens(), room.getOccup() - px.adults - px.infants ));
            }
            if ( room.getAdults() > 0 && pax.adults > px.adults && px.getTotal() < room.getOccup() ) {
                px.adults += Math.min(room.getOccup()- px.getTotal(), pax.adults - px.adults);
            }
            if (px.getTotal() >0) 
                ok.add(new Room(room.getType(),room.getOccup(),px.adults,px.childrens,px.infants));
        }
        return ok;
    }
    
    public Set<Room> getRooms () {
        Set<Room> rms = new HashSet<Room>();
        rms.add( new Room(Room.Type.Double,2,2,0,0));
        rms.add( new Room(Room.Type.Triple,3,3,3,2));
        rms.add( new Room(Room.Type.Quad,4,4,2,2));
        return rms;
    }

    public RoomCount getRCount () {
        RoomCount rc = new RoomCount();
        rc.setCount( Room.Type.Double,5);
        rc.setCount( Room.Type.Triple,3);
        rc.setCount( Room.Type.Quad,3); 
        return rc;
    }
    
    public void runAlog () {
        
        findBest (getRooms(), getRCount (), new Pax(2,2,1));
        
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
  
        new RoomManager().runAlog();
         
        
        //System.out.println (Arrays.deepToString(rooms));
        //Arrays.sort(rooms, new Room.occSort() );
        
        
    }
   
}
