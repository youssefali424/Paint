/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainPackage;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 *
 * @author youssef ali
 */
public class Polygon extends Shape{
    int points;
    int[] pointsX,pointsY;
    public Polygon(int[] x1,int[] y1,int nPoints,Color c1,boolean filled){
    super(x1[0],y1[0],c1,filled);
    points=nPoints;
    pointsX=x1;pointsY=y1;
    }
    @Override
    public void draw(Graphics2D g) {
        g.setColor(getColor());
        g.drawPolygon(pointsX, pointsX, points);
    }

    @Override
    public void fill(Graphics2D g) {
       g.setColor(getColor());
       g.fillPolygon(pointsX, pointsX, points); 
    }
    
}
