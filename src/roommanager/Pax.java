/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package roommanager;

/**
 *
 * @author cgc
 */
public class Pax {
    
    public int adults;
    public int childrens;
    public int infants;

    public Pax( ) {
        adults = 0;
        childrens = 0;
        infants = 0;
    }

   
    public Pax(int adults, int childrens, int infants) {
        this.adults = (adults>0)? adults: 0;
        this.childrens = (childrens>0)? childrens : 0;
        this.infants = (infants>0)?infants : 0;
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
    
    public int getTotal() {
        return adults +childrens + infants;
    }
    
    public int getKids() {
        return childrens + infants;
    }    
    public void setAdults(int adults) {
        this.adults = adults;
    }

    public void setChildrens(int childrens) {
        this.childrens = childrens;
    }

    public void setInfants(int infants) {
        this.infants = infants;
    }
    
    public void incAdults(int adults) {
        this.adults += adults;
    }

    public void incChildrens(int childrens) {
        this.childrens += childrens;
    }

    public void incInfants(int infants) {
        this.infants += infants;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Pax)) return false;
        Pax ext = (Pax)  obj;
        return (adults == ext.adults && childrens == ext.childrens && infants == ext.infants  ); 
                
    }

    public Pax subtract (Pax ext) {
        return new Pax(adults - ext.adults, childrens - ext.childrens, infants - ext.infants );
    }
    
    @Override
    public String toString() {
        return "Pax{" + "adults=" + adults + ", childrens=" + childrens + ", infants=" + infants + '}';
    }
    
    
}
