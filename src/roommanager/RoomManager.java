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
import java.util.SortedSet;
import java.util.TreeSet;
/**
 *
 * @author cgc
 */
public class RoomManager {

    private Room[] rooms;
    private SortedSet<RoomSet> res;
    
    public RoomSet[] findBest (Set<Room> roomlist, RoomCount rcount, Pax pax) {
    
        rooms = (Room[]) roomlist.toArray(new Room[0]);
        Arrays.sort(rooms, new Room.occSort() );
        res = new TreeSet<RoomSet>() {};
        findBest_Loop ( rcount, pax, new RoomSet (), 1);
        return (RoomSet[]) res.toArray(new RoomSet[0]);
    }
    
    private void findBest_Loop (RoomCount rcount, Pax pax, RoomSet rref, int depth) {
        
        if ( pax.getTotal() <= 0) {
            if ( pax.getTotal() == 0)  res.add(rref); 
            return;  }
    
        List<Room> ok = evalEachRoom (rcount, pax);
        //System.out.println (Arrays.deepToString(ok.toArray()));
        Collections.sort (ok, new Room.okSort());
        
        for (Room room : ok ) {
            //System.out.println (".");
            findBest_Loop (rcount.getDecCount(room.getType()),pax.subtract(room.getPax()), rref.Append(room) , depth+1);
        }
        
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
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RoomCount rcount;
        Pax pax ;
        RoomManager rman = new RoomManager();   
        RoomSet[] rset ;

         Set<Room> rooms = new HashSet<Room>();
            rooms.add( new Room(Room.Type.Double,2,2,0,0));
            rooms.add( new Room(Room.Type.Triple,3,3,3,2));
            rooms.add( new Room(Room.Type.Quad,4,4,2,2));
            
            
        // example 1
        
        rcount = new RoomCount();
            rcount.setCount( Room.Type.Double,2);
            rcount.setCount( Room.Type.Triple,2);
            rcount.setCount( Room.Type.Quad,0); 
    
        pax = new Pax(4,1,0);
         
        rset =  rman.findBest (rooms, rcount, pax);
        
        System.out.println ("Example 1");
        System.out.println (pax); System.out.println (rcount); 
        System.out.println (Arrays.deepToString(rset));
        
        // example 2

        rcount = new RoomCount();
            rcount.setCount( Room.Type.Double,4);
            rcount.setCount( Room.Type.Triple,1);
            rcount.setCount( Room.Type.Quad,1); 
    
        pax = new Pax(6,0,0);
        
        rset =  rman.findBest (rooms, rcount, pax);
        
        System.out.println ("Example 2");
        System.out.println (pax); System.out.println (rcount); 
        System.out.println (Arrays.deepToString(rset));
    }
   
}
