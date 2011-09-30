package de.unisaarland.cs.sopra.common.view;

import static de.unisaarland.cs.sopra.common.PlayerColors.BLACK;
import static de.unisaarland.cs.sopra.common.view.Textures.catapultTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.fieldMarkTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.fieldTextureMap;
import static de.unisaarland.cs.sopra.common.view.Textures.harborTextureMap;
import static de.unisaarland.cs.sopra.common.view.Textures.intersectionMarkRedTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.intersectionMarkTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.numberTextureMap;
import static de.unisaarland.cs.sopra.common.view.Textures.pathMarkTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.pathMarkTextureRed;
import static de.unisaarland.cs.sopra.common.view.Textures.robberTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.streetTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.townTexture;
import static de.unisaarland.cs.sopra.common.view.Textures.villageTexture;
import static de.unisaarland.cs.sopra.common.view.Util.setColor;

import java.util.List;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.opengl.Texture;

import de.unisaarland.cs.sopra.common.Setting;
import de.unisaarland.cs.sopra.common.model.Field;
import de.unisaarland.cs.sopra.common.model.FieldType;
import de.unisaarland.cs.sopra.common.model.HarborType;
import de.unisaarland.cs.sopra.common.model.Intersection;
import de.unisaarland.cs.sopra.common.model.Location;
import de.unisaarland.cs.sopra.common.model.ModelReader;
import de.unisaarland.cs.sopra.common.model.Path;
import de.unisaarland.cs.sopra.common.model.Point;

public class RenderBoard {
	
	private static int orgX, orgY, orgZ;
	public static int x,y,z;
	public static int maxX, maxY, maxZ; //TODO use it
	public static int minX, minY, minZ;
	public static int windowWidth;
	public static int windowHeight;
	public static float aspectRatio;
	private static float ZOOM_FACTOR = 0.00909090909090909091f;
	private static ModelReader mr;
	
	public static float rotate = 0.0f;
	
	public static void initiateRenderBoard(ModelReader mr) {
		RenderBoard.mr = mr;
		int boardwidth = mr.getBoardWidth();
		int boardheight = mr.getBoardHeight();
		windowWidth = Setting.getDisplayMode().getWidth();
		windowHeight = Setting.getDisplayMode().getHeight();
		aspectRatio = ((float)windowWidth)/windowHeight;
		//center to the area where the first field is drawn
		x = (int)(812.8125f*aspectRatio);
		y = 745;
		z = -1450;
		//center camera on map according to map size
		x += (boardwidth-1)*-106;
		y += (boardheight-1)*-170;
		z += Math.max((boardwidth-1)*450,(boardheight-1)*450);
	
		orgX = x;
		orgY = y;
		orgZ = z;
		
		//set the max and min for camera
		maxZ = z;
		minZ = -1500;
	}
	
	public static void drawSquareMid(int width, int height) {
	     GL11.glBegin(GL11.GL_POLYGON);
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex3i(-width/2, -height/2, 0);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex3i(width/2, -height/2, 0);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex3i(width/2, height/2, 0);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex3i(-width/2, height/2, 0);
	     GL11.glEnd();
	}
	
	public static void drawSquareLeftTop(int width, int height) {
	     GL11.glBegin(GL11.GL_POLYGON);
	       GL11.glTexCoord2f(0,0);
	       GL11.glVertex3i(0, 0, 0);
	       GL11.glTexCoord2f(1,0);
	       GL11.glVertex3i(width, 0, 0);
	       GL11.glTexCoord2f(1,1);
	       GL11.glVertex3i(width, height, 0);
	       GL11.glTexCoord2f(0,1);
	       GL11.glVertex3i(0, height, 0);
	     GL11.glEnd();
	}
	
	public static void renderFieldMark(List<Point> selectionPoint) {
		for (Point p : selectionPoint) {
		    int fx = 0;
		    int fy = 0;
		    switch(p.getY()%2) {
		    case 0:
			   fx = p.getX()*250;
			   fy = p.getY()*215; 
			   break;
		    case 1:
			   fx = p.getX()*250-125;
			   fy = p.getY()*215;
			   break;
		    }
		    GL11.glPushMatrix();
		    fieldMarkTexture.bind();
		    GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		    GL11.glTranslatef(fx+x, fy+y, 2+z);
		    drawSquareMid(300, 300);
		    GL11.glPopMatrix();
		}
	}
	
	public static void renderPathMark(boolean red, List<Location> selectionLocation) {
		for (Location l : selectionLocation) {
			int px = 0;
			int py = 0;
			int po = 0;
			
			
			switch(l.getY()%2) {
			   case 0:
				   px = l.getX()*250;
				   py = l.getY()*215; 
				   break;
			   case 1:
			   case -1:
				   px = l.getX()*250-125;
				   py = l.getY()*215;
				   break;
		   }
		  switch(l.getOrientation()) {
			   case 0:
				   px+=74;
				   py+=-96;
				   po+=30;
				   break;
			   case 1:
				   px+=118;
				   py+=18;
				   po+=90;
				   break;
			   case 2:
				   px+=38;
				   py+=116;
				   po+=150;
				   break;
			   case 3:
				   px+=-84;
				   py+=93;
				   po+=210;
				   break;
			   case 4:
				   px+=-128;
				   py+=-21;
				   po+=270;
				   break;
			   case 5:
				   px+=-47;
				   py+=-120;
				   po+=330;
				   break;
			   default:
				   throw new IllegalArgumentException();
		   }
				GL11.glPushMatrix();
				GL11.glTranslatef(px+x, py+y, 1+z);
				GL11.glRotatef(po, 0, 0, 1);
			    if (!red) 
			    	pathMarkTexture.bind();
			    else
			    	pathMarkTextureRed.bind();
			    GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
			    drawSquareMid(200,25);
			    GL11.glPopMatrix();
		}
	}
	
	public static void renderIntersectionMark(boolean red, List<Location> selectionLocation) {
		for (Location l : selectionLocation) {
			int ix = 0;
			int iy = 0;
			   switch(l.getY()%2) {
				   case 0:
					   ix = l.getX()*250;
					   iy = l.getY()*215; 
					   break;
				   case 1:
				   case -1:
					   ix = l.getX()*250-125;
					   iy = l.getY()*215;
					   break;
			   }
			  switch(l.getOrientation()) {
				   case 0:
					   iy+=-135;
					   break;
				   case 1:
					   ix+=125;
					   iy+=-70;
					   break;
				   case 2:
					   ix+=125;
					   iy+=80;
					   break;
				   case 3:
					   iy+=140;
					   break;
				   case 4:
					   ix+=-120;
					   iy+=80;
					   break;
				   case 5:
					   ix+=-120;
					   iy+=-70;
					   break;
				   default:
					   throw new IllegalArgumentException();
			   }
		    GL11.glPushMatrix();
		    if (!red)
		    	intersectionMarkTexture.bind();
		    else
		    	intersectionMarkRedTexture.bind();
		    GL11.glColor4f(0.5f, 0.5f, 0.5f, 0.5f);
		    GL11.glTranslatef(ix+x+25, iy+y+22, 3+z);
		    drawSquareMid(150, 150);
		    GL11.glPopMatrix();
		}
	}

	public static void renderField(Field f) {
		int fx = 0;
		int fy = 0;
		   switch(f.getLocation().getY()%2) {
		   case 0:
			   fx = f.getLocation().getX()*250;
			   fy = f.getLocation().getY()*215; 
			   break;
		   case 1:
		   case -1:
			   fx = f.getLocation().getX()*250-125;
			   fy = f.getLocation().getY()*215;
			   break;
		   }
		   GL11.glPushMatrix(); 
		   setColor(BLACK);
		   GL11.glTranslatef(fx+x, fy+y, 0+z);
		   fieldTextureMap.get(f.getFieldType()).bind(); 
		   if (f.hasRobber()) {
			   if(f.getFieldType().equals(FieldType.DESERT) || f.getFieldType().equals(FieldType.WATER)) {
				   drawSquareMid(300, 300);
				   GL11.glTranslatef(20, 0, 0);
				   robberTexture.bind();
				   drawSquareMid(160, 160);
			   }
			   else {
			   drawSquareMid(300, 300);
			   GL11.glTranslatef(-30, 20, 0);
			   robberTexture.bind();
			   drawSquareMid(160, 160);
			   GL11.glTranslatef(60, -20, 0);
			   }
		   }
		   else {
			   drawSquareMid(300, 300);
		   }
		   
		   if (f.getFieldType() != FieldType.DESERT && f.getFieldType() != FieldType.WATER) { 
			   numberTextureMap.get(f.getNumber()).bind();
			   drawSquareMid(300, 300);
		   }
		   GL11.glPopMatrix();
	}
	
	public static void renderIntersection(Intersection i) {
		if (i.hasOwner()) {
			int ix = 0;
			int iy = 0;
			   switch(i.getLocation().getY()%2) {
				   case 0:
					   ix = i.getLocation().getX()*250;
					   iy = i.getLocation().getY()*215; 
					   break;
				   case 1:
				   case -1:
					   ix = i.getLocation().getX()*250-125;
					   iy = i.getLocation().getY()*215;
					   break;
			   }
			  switch(i.getLocation().getOrientation()) {
				   case 0:
					   ix+=5;
					   iy+=-136;
					   break;
				   case 1:
					   ix+=128;
					   iy+=-80;
					   break;
				   case 2:
					   ix+=133;
					   iy+=80;
					   break;
				   case 3:
					   ix+=20;
					   iy+=155;
					   break;
				   case 4:
					   ix+=-117;
					   iy+=80;
					   break;
				   case 5:
					   ix+=-122;
					   iy+=-80;
					   break;
				   default:
					   throw new IllegalArgumentException();
			   }
			   GL11.glPushMatrix();
			   switch (i.getBuildingType()) {
			   case Village:
				   setColor(i.getOwner());
				   GL11.glTranslatef(ix+x, iy+y, 1+z);
				   villageTexture.bind();
				   drawSquareMid(125, 125);
				   break;
			   case Town:
				   setColor(i.getOwner());
				   GL11.glTranslatef(ix+x, iy+y, 1+z);
				   townTexture.bind();
				   drawSquareMid(125, 125);
				   break;
			   }
			   GL11.glPopMatrix();
		}
	}

	public static void renderPath(Path p) {
		if (p.hasStreet()) {
			renderStreet(p);
		}
		if (p.hasCatapult()) {
			renderCatapult(p);
		}
		if (p.getHarborType() != null) {
			renderHarbor(p);
		}
	}
	
	public static void renderHarbor(Path p) {
		int o = p.getLocation().getOrientation();
		for (Field f: mr.getFieldsFromPath(p)) {
			if (f.getFieldType().equals(FieldType.WATER)) {
				int xField = f.getLocation().getX();
				int yField = f.getLocation().getY();
				int orient = o;
				if (xField != p.getLocation().getX() || yField != p.getLocation().getY()) {
					if (orient <3) {
						orient += 3;
					}
					else {
						orient -=3;
					}
				}
				
				HarborType h = p.getHarborType();
				int px = 0;
				int py = 0;
				int po = 0;
				switch(yField%2) {
				   case 0:
					   px = xField*250;
					   py = yField*215; 
					   break;
				   case 1:
				   case -1:
					   px = xField*250-125;
					   py = yField*215;
					   break;
			   }
			  switch(orient) {
				   case 0:
					   px+=95;  // 74
					   py+=-35;  // -96
					   po+=30;
					   break;
				   case 1:
					   px+=68;
					   py+=63;
					   po+=90;
					   break;
				   case 2:
					   px+=-27;
					   py+=91;
					   po+=150;
					   break;
				   case 3:
					   px+=-99;
					   py+=23;
					   po+=210;
					   break;
				   case 4:
					   px+=-78;
					   py+=-71;
					   po+=270;
					   break;
				   case 5:
					   px+=18;
					   py+=-100;
					   po+=330;
					   break;
				   default:
					   throw new IllegalArgumentException();
			   }
			  	Texture harborTexture = harborTextureMap.get(h);
				GL11.glPushMatrix();
				GL11.glTranslatef(px+x, py+y, 1+z);
				GL11.glRotatef(po-90, 0, 0, 1);
				setColor(BLACK);
				harborTexture.bind();
			    drawSquareMid(170,312);
			    GL11.glPopMatrix();
				}
				}
			}
		
	public static void renderCatapult(Path p) {
		int px = 0;
		int py = 0;
	
		  switch(p.getLocation().getY()%2) {
			   case 0:
				   px = p.getLocation().getX()*250;
				   py = p.getLocation().getY()*215; 
				   break;
			   case 1:
			   case -1:
				   px = p.getLocation().getX()*250-125;
				   py = p.getLocation().getY()*215;
				   break;
		   }
		  switch(p.getLocation().getOrientation()) {
			   case 0:
				   px+=66;
				   py+=-108;
				   break;
			   case 1:
				   px+=130;
				   py+=-1;
				   break;
			   case 2:
				   px+=56;
				   py+=112;
				   break;
			   case 3:
				   px+=-52;
				   py+=115;
				   break;
			   case 4:
				   px+=-128;
				   py+=-1;
				   break;
			   case 5:
				   px+=-72;
				   py+=-100;
				   break;
			   default:
				   throw new IllegalArgumentException();
		   }
			GL11.glPushMatrix();
			GL11.glTranslatef(px+x, py+y, 1+z);
		    catapultTexture.bind();
		    setColor(p.getCatapultOwner());
		    drawSquareMid(70, 70);
		    GL11.glPopMatrix();
	}
	
	public static void renderStreet(Path p) {
		int px = 0;
		int py = 0;
		int po = 0;
	
		  switch(p.getLocation().getY()%2) {
			   case 0:
				   px = p.getLocation().getX()*250;
				   py = p.getLocation().getY()*215; 
				   break;
			   case 1:
			   case -1:
				   px = p.getLocation().getX()*250-125;
				   py = p.getLocation().getY()*215;
				   break;
		   }
		  switch(p.getLocation().getOrientation()) {
			   case 0:
				   px+=74;
				   py+=-96;
				   po+=30;
				   break;
			   case 1:
				   px+=118;
				   py+=18;
				   po+=90;
				   break;
			   case 2:
				   px+=38;
				   py+=116;
				   po+=150;
				   break;
			   case 3:
				   px+=-84;
				   py+=93;
				   po+=210;
				   break;
			   case 4:
				   px+=-128;
				   py+=-21;
				   po+=270;
				   break;
			   case 5:
				   px+=-47;
				   py+=-120;
				   po+=330;
				   break;
			   default:
				   throw new IllegalArgumentException();
		   }
			GL11.glPushMatrix();
			GL11.glTranslatef(px+x, py+y, 1+z);
			GL11.glRotatef(po, 0, 0, 1);
		    streetTexture.bind();
		    setColor(p.getStreetOwner());
		    drawSquareMid(200,25);
		    GL11.glPopMatrix();
	}
	
	public static void resetCamera() {
		x = orgX;
		y = orgY;
		z = orgZ;
		rotate = 0.0f;
	}
	
	public static void camLeft() {
		x += ZOOM_FACTOR  * (2000+z);
	}
	public static void camRight() {
		x -= ZOOM_FACTOR * (2000+z);
	}
	public static void camTop() {
		y += ZOOM_FACTOR * (2000+z);
	}
	public static void camDown() {
		y -= ZOOM_FACTOR * (2000+z);
	}
	public static void camOut() {
		int newz = (int)((z+1550)*1.05f)-1550;
		if (newz < maxZ)
			z = newz;
	}
	public static void camIn() {
		int newz = (int)((z+1550)*0.95f)-1550;
		if (newz > minZ)
			z = newz;
	}

	public static int getOrgZ() {
		return orgZ;
	}
	
	public static int getOglx() {
		//Do not touch. took me 2 days to get it to work!
		return (int) (Mouse.getX()*screenToOpenGLx(z)+(585*aspectRatio)+((1450+z)*-0.415f*aspectRatio))-x+128;
	}
	
	public static int getOgly() {
		//Do not touch. took me 2 days to get it to work!
		return (int) ((windowHeight-Mouse.getY())*screenToOpenGLy(z)+592+((1450+z)*-0.415f))-y+148;
	}
	
	public static float screenToOpenGLx (int zoom) {
		float oglwidth = (float)(2000+zoom)*0.82841f*aspectRatio;
		return oglwidth/windowWidth;
	}
	
	public static float screenToOpenGLy (int zoom) {
		float oglheight = (float)(2000+zoom)*0.82841f;
		return oglheight/windowHeight;
	}
	
	public static void tiltUp() {
		if (rotate-1.0f>-45.0f)
			rotate -= 1.0f;
	}

	public static void tiltDown() {
		if (rotate+1.0f<0.0f)
			rotate += 1.0f;
	}
	
}
