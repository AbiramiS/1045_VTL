/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.model;

import java.util.Observable;
import java.util.Vector;

/**
 *
 * @author admin
 */
public class URLObserver extends Observable{

    private Vector values;

    public void setTagNeighbours(Vector values){
        this.values=values;
        fireNotify("values");
    }

    public Vector getTagNeighbours(){
        return values;
    }

    private void fireNotify(String message){
        setChanged();
        notifyObservers(message);
    }

}