/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

import java.util.Comparator;

/**
 *
 * @author cgc
 */
public class Room {

    private Type type;
    private int occup;
    private int adults;
    private int childrens;
    private int infants;

    public Room(Type type, int occup, int adults, int childrens, int infants) {
        this.type = type;
        this.occup = occup;
        this.adults = adults;
        this.childrens = childrens;
        this.infants = infants;
    }
    
    public int getAdults() {
        return adults;
    }

    public int getChildrens() {
        return childrens;
    }

    public int getInfants() {
        return infants;
    }

    public int getOccup() {
        return occup;
    }

    public Type getType() {
        return type;
    }
  
    public int getTotal () {
        return (adults + childrens + infants);
    }
    
    public int getKids () {
        return (childrens + infants);
    }

    public int getWastage () {
        return (occup - adults - childrens - infants);
    }
    
    public static class occSort implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            if (o1 == null || o2 == null )  throw new ClassCastException();
            return (o2.getOccup() - o1.getOccup());
        }
    }
    
    public static class okSort implements Comparator<Room> {
        @Override
        public int compare(Room o1, Room o2) {
            if (o1 == null || o2 == null )  throw new ClassCastException();
            int dif = o1.getWastage() - o2.getWastage();
            if (dif != 0 ) return dif;
            if ((dif = o2.getKids() - o1.getKids()) != 0 ) return dif;
            return (o2.getTotal() - o1.getTotal());
        }
    }

    @Override
    public String toString() {
        return "Room{" + "type=" + type + ", occup=" + occup + ", adults=" + adults + ", childrens=" + childrens + ", infants=" + infants + 
                ", total=" + getTotal() + ", wastage=" + getWastage()  + ", kids=" + getKids() + '}' ;
    }
    
    
    public enum Type {
        Double, Triple, Quad
    }
    
}
