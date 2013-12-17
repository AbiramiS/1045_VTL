/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.view;
import java.util.Observer;
import java.util.Observable;
import java.util.Vector;
import proxyserver.model.URLObserver;
import proxyserver.view.JtableA;


/**
 * @author admin
 */

public class Adapter extends Observer{

    
    public var uo : URLObserver =new URLObserver() on replace { uo.addObserver(this)}
    public var rows:JtableA.TableRow[];
    
    override public function update (arg0 : Observable, arg1 : Object) : Void {
        
         FX.deferAction(
             function(): Void {
                if(arg1 instanceof String){
                    var arg:String=arg1 as String;
                    if(arg.equalsIgnoreCase("values")){
                        var values:Vector=uo.getTagNeighbours();
                        insert JtableA.TableRow{
                            cells: [
                                for(i in values)
                                    JtableA.TableCell{
                                        text:i.toString()
                                    }
                            ]
                        } into rows;
                    }
                }
             }
         );
    }
}
