/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.view;

import javafx.scene.CustomNode;
import javafx.scene.Node;
import javafx.scene.Group;
import proxyserver.controller.Controller;
/**
 * @author admin
 */

public abstract class Design extends CustomNode{

    public-init var userid:String;
    protected var nodes:Node;
    public var controller:Controller;
    public var common:Node;
    var viewNodes:Node[]=[];
    protected abstract function createView():Void;

    public override function create():Node{
        createView();
        nodes.translateX=75;
        nodes.translateY=100;
        insert nodes into viewNodes;
        return Group{
            content:bind viewNodes
        }
    }
}
