/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package proxyserver.controller;

import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.stage.StageStyle;
import javafx.scene.paint.Color;
import javafx.scene.layout.Stack;
import javafx.scene.shape.Rectangle;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import proxyserver.view.MainScreen;
import proxyserver.view.TrafficViewer;
import proxyserver.view.Adapter;
import proxyserver.model.URLObserver;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
/**
 * @author admin
 */

public class Controller {

    var stylesheets : String = "{__DIR__}MyStyle.css";
    var conten:Node;
    var stageDragInitialX:Number;
    var stageDragInitialY:Number;
    var uo:URLObserver=new URLObserver();
    var adapter:Adapter=Adapter{uo:uo};

    var main:MainScreen=MainScreen{
        controller:this
        uo:uo
    };

    var close:ImageView=ImageView{
        translateX:bind myscene.width-45
        translateY:bind myscene.height-45
        fitHeight:30
        fitWidth:30
        image:Image{
            url:"{__DIR__}Close_Icon2.png"
        }
        onMouseClicked:function(e){
            stage.close();
        }
    }

    var stage:Stage;
    public-read var myscene:Scene=Scene{
                width:700
                height:450
                fill:Color.TRANSPARENT
                stylesheets: bind stylesheets
                content:bind[
                    Stack{
                        nodeHPos:HPos.CENTER
                        nodeVPos:VPos.CENTER
                        onMousePressed:function(e) {
                            if("{__PROFILE__}" != "browser") {
                                stageDragInitialX = e.screenX - stage.x;
                                stageDragInitialY = e.screenY - stage.y;
                            }
                        }
                        onMouseDragged:function(e) {
                            if("{__PROFILE__}" != "browser") {
                                stage.x = e.screenX - stageDragInitialX;
                                stage.y = e.screenY - stageDragInitialY;
                            }
                        }
                        content: [
                            Rectangle{
                                width: bind myscene.width-50
                                height: bind myscene.height-50
                                fill: LinearGradient {
                                    startX: 0.0
                                    startY: 0.0
                                    endX: 0.0
                                    endY: 1.0
                                    stops: [
                                        Stop {
                                            color: Color.color(1.0, 1.0, 0.4);
                                            offset: 0.0
                                        },
                                        Stop {
                                            color: Color.color(0.0, 0.0, 0.0);
                                            offset: 1.0
                                        },
                                    ]
                                }
                                arcHeight:100
                                arcWidth:100
                                
                            }
                            ,
                            Rectangle{
                                width: bind myscene.width-5
                                height: bind myscene.height-5
                                fill:Color.TRANSPARENT
                                arcHeight:100
                                arcWidth:100
                                strokeWidth:5
                                stroke:Color.YELLOWGREEN
                            }
                        ]
                    },
                    close,
                    conten
                ]
            };
    public function run(){
        stage=Stage{
            style:StageStyle.TRANSPARENT
            scene:bind myscene
        }
        initialize();
    }

    public function initialize(){
        conten=main;
    }

    public function viewTraffic(){
        adapter=Adapter{uo:uo};
        conten=TrafficViewer{
            controller:this
            adapter:adapter
        };
    }

}

public function run(){
    var control:Controller=new Controller();
    control.run();
}