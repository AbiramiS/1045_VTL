/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.view;

import proxyserver.view.Design;
import javafx.scene.Group;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
/**
 * @author admin
 */

public class TrafficViewer extends Design{

    public var adapter:Adapter;

    var text:Text=Text {
        styleClass:"styleText1"
        content:"Request and Response Details"
        textAlignment:TextAlignment.CENTER
        blocksMouse: true
        translateX:130
        translateY:-20
    };

    var table:JtableA=JtableA{
        columns: [
            JtableA.TableColumn{
                text:"Host"
            },
            JtableA.TableColumn{
                text:"Local Port"
            },
            JtableA.TableColumn{
                text:"Server"
            }
        ]
        rows: bind adapter.rows
    }

    var back:Button=Button{
        text:"Back"
        translateX:250
        translateY:285
        focusTraversable:false
        action:function():Void{
            controller.initialize();
        }
    }

    public override function createView(){
        nodes=Group{
            content:[
                text,
                table,
                back
            ]
        }
    }
}
