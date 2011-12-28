/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.Arrays;
/**
 *
 * @author cgc
 */
public class RoomManager {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Room[] rooms = new Room[] {
            new Room(Room.Type.Double,2,2,0,0), 
            new Room(Room.Type.Triple,3,3,3,2), 
            new Room(Room.Type.Quad,4,4,2,2) 
        }; 
        
        //System.out.println (Arrays.deepToString(rooms));
        //Arrays.sort(rooms, new Room.occSort() );
        
        Room[] rom = new Room[] {
            new Room(Room.Type.Double,2,0,1,1), 
            new Room(Room.Type.Triple,3,1,2,0), 
            new Room(Room.Type.Quad,4,4,0,0) 
        }; 
        
        System.out.println ("Version 123");
        System.out.println (Arrays.deepToString(rom));
        Arrays.sort(rom, new Room.okSort() );
        System.out.println (Arrays.deepToString(rom));
  
    }
   
}
