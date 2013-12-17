/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.view;

import proxyserver.view.Design;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.TextBox;
import javafx.scene.control.Button;
import java.lang.System;
import proxyserver.model.JProxy;
import javafx.stage.Alert;
import java.lang.Exception;
import proxyserver.model.URLObserver;

/**
 * @author admin
 */

public class MainScreen extends Design{

    var jp:JProxy;

    public var uo:URLObserver;

    var text:Text=Text {
        styleClass:"styleText1"
        content:"                             PROXY SERVER"
        textAlignment:TextAlignment.CENTER
        blocksMouse: true
    };

    var port_label:Label=Label{
        text:"Enter the Port No:"
        styleClass:"styleText"
        translateY:50
        translateX:60
    };

    var port_no:TextBox=TextBox{
        translateY:52
        translateX:260
        columns:25
    }

    var cloud_ip_label:Label=Label{
        text:"Enter System IP:"
        styleClass:"styleText"
        translateY:100
        translateX:60
    };

    var cloud_ip:TextBox=TextBox{
        translateY:102
        translateX:260
        columns:25
    }

    var cloud_port_label:Label=Label{
        text:"Enter System Port:"
        styleClass:"styleText"
        translateY:150
        translateX:60
    };

    var cloud_port:TextBox=TextBox{
        translateY:152
        translateX:260
        columns:25
    }
    

    var proxy_starter:Button=Button{
        text:"Start Proxy"
        translateY:225
        translateX:150
        action:function():Void{
            var done=true;
            if(port_no.text.equals("")){
                Alert.inform("Status","Enter proxy port");
                done=done and false;
                port_no.requestFocus();
            }else if(cloud_ip.text.equals("")){
                Alert.inform("Status","Enter cloud ip");
                done=done and false;
                cloud_ip.requestFocus();
            }else if(cloud_port.text.equals("")){
                Alert.inform("Status","Enter cloud port");
                done=done and false;
                cloud_port.requestFocus();
            }

            if(done){
                try{
                    var fwdProxyServer = "";
                    var fwdProxyPort = 0;
                    jp = new JProxy(Integer.parseInt(port_no.text.trim()), fwdProxyServer, fwdProxyPort, 20 , uo, cloud_ip.text, Integer.parseInt(cloud_port.text.trim()));
                    jp.setDebug(1, System.out);
                    jp.starter();
                    proxy_starter.disable=true;
                    Alert.inform("Status","Proxy Started Successfully");
                }catch(e:Exception){
                    Alert.inform("Status","Please Enter the valid port number");
                }
            }
        }
    }

    var view_traffic:Button=Button{
        text:"View Traffic"
        translateY:225
        translateX:260
        focusTraversable:false
        action:function():Void{
            if(proxy_starter.disabled){
                controller.viewTraffic();
            }
            else{
                Alert.inform("Status","Proxy Not Started, Please start it...");
            }
        }
    }

    public override function createView(){
        nodes=Group{
            content:[
                text,
                port_label,
                port_no,
                cloud_ip_label,
                cloud_ip,
                cloud_port_label,
                cloud_port,
                proxy_starter,
                view_traffic
            ]
        }
    }
}
