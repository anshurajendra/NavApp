package com.example.navapp;

public class Position {
 
	private double x;
	private double y;
	
	public Position(){
		this.x=0.0;
		this.y=0.0;
	}
	public Position(double x,double y){
		this.x=x;
		this.y=y;
	}
	public void setPosX(double x){
		this.x=x;
	}
	public void setPosY(double y){
		this.y=y;
	}
	public double getPosX(){
		return this.x;
	}
	
	public double getPosY(){
		return this.y;
	}
}
