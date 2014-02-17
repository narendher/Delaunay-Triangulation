import java.applet.Applet;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class delaunay extends Applet implements ActionListener{
	long initialTime;
	public Graphics g;
	int pnum=0;
	static int edgecount=0;	
	public static double closestPoint_X;
	public static double closestPoint_Y;
	Point po[];
	static edges tester_Edges;
	static int ytr;
	static int temporaryint=0;
	static int triangle_validity_count=0;
	static int[] triangles_intersection_count=new int[10000];
	static triangles parent=new triangles();
	static triangles[] arr=new triangles[50000];
	static edges[] edge=new edges[50000];
	static edges[] path=new edges[10000];
	static double[] distant=new double[1000];
	static int[] edgenumbers=new int[100];
	static edges[] perpendicular_lines=new edges[1000];
	static int perpendiculatcount=0;
	static int pathnumber=0;
	static int Trianglecount=0;
	static int numofpoints=150;
	static Point closestPoint=new Point();
	static int intersectingcount=0;
	static triangles roottriangles=new triangles();
	static triangles finder=new triangles();
	static edges testingedge=new edges();
	static boolean finalresult;
	static Point jkl = new Point();
	static Point test=new Point();
	 int f = 0;
	@Override
	public void init() {
	     this.setSize(1280, 710);
	}
	public void paint( Graphics g ) 
	{
		this.g = g;
		initialTime = System.currentTimeMillis();		
		Random rnd = new Random();
		po = new Point[numofpoints];
		Point jkl = new Point();
		g.setColor(Color.blue);
		g.setColor(Color.red);	
		for(int i=0;i<100;i++)
		{
			edgenumbers[i]=0;
		}
		for(int i=0;i<10000;i++)
	    {	 
	    	  arr[i]=new triangles();	    	  
	    }
	    g.setColor(Color.black);
	    Point top=new Point(640,5);
	    Point bl= new Point(270,640);
	    Point br=new Point(1010,640);
	    edge[0]=new edges(top,bl,null,arr[0],null,br);
	    edge[0].validity=0;
	    edge[1]=new edges(bl,br,null,arr[0],null,top);
	    edge[1].validity=0;
	    edge[2]=new edges(top,br,null,arr[0],null,bl);
	    edge[2].validity=0;
	    arr[0]=new triangles(top,bl,br,edge[0],edge[1],edge[2],null);
	    arr[0].validity=1;
	    displaytri(g,arr[0]);
	    int p=0;
	   while(p<numofpoints)
	    {
              int x = 280+rnd.nextInt(700);
              int y = 50+rnd.nextInt(600);
              jkl=new Point(x,y);
              if(PointInTriangle(jkl,arr[0])!=2)
              {
                  po[p]=new Point(x,y);
                  g.drawOval(po[p].x,po[p].y, 2, 2);
                  p++;		    	  
              }
            }
	    int ex=651;
	    for(int i=0;i<numofpoints;i++)
	    {
              if(po[i].y<ex)
              {
                      ex=po[i].y;
                      f=i;
              }
	    }
	    parent=arr[0];
	    edge[3]=new edges(po[f],parent.p1,arr[1],arr[3],parent.p2,parent.p3);
	    edge[3].validity=0;
	    edge[4]=new edges(po[f],parent.p2,arr[2],arr[1],parent.p1,parent.p3);
	    edge[4].validity=0;
	    edge[5]=new edges(po[f],parent.p3,arr[3],arr[2],parent.p1,parent.p2);
	    edge[5].validity=0;
	    arr[1]=new triangles(po[f],parent.p1,parent.p2,edge[3],parent.e1,edge[4],parent);
	    arr[1].validity=0;
	    arr[2]=new triangles(po[f],parent.p2,parent.p3,edge[4],parent.e2,edge[5],parent);
	    arr[2].validity=0;
	    arr[3]=new triangles(po[f],parent.p3,parent.p1,edge[5],parent.e3,edge[3],parent);   
	    arr[3].validity=0;
	    edge[3].sh1=arr[3];
	    edge[3].sh2=arr[1];
	    edge[4].sh1=arr[2];
	    edge[4].sh2=arr[1];
	    edge[5].sh1=arr[3];
	    edge[5].sh2=arr[2];
	    test=po[f];
	    arr[0].setChildren(arr[1], arr[2], arr[3]);
	    updateEdges(arr[0],test);	    	   
	    displaytri(g, arr[1]);
	    displaytri(g, arr[2]);
	    displaytri(g, arr[3]);
	    for (int i=f;i<numofpoints-1;i++)
	    {
	    	po[i]=po[i+1];
	    }
	    po[numofpoints-1]=null;
	    Trianglecount=4;
	    edgecount=6;
		while(pnum<numofpoints-1)
		{	
                    test=po[pnum];
                    triangles tri=new triangles();
                    tri=arr[0];
                    // Search from root to leaf to search triangle with null children
                    while(tri.children[0]!=null)
                    {
                            if(PointInTriangle(test,tri.children[0])!=2)
                            {
                                    tri=tri.children[0];
                                    continue;
                            }
                            else if(PointInTriangle(test,tri.children[1])!=2)
                            {
                                    tri=tri.children[1];
                                    continue;
                            }
                            if(tri.children[2]!=null)
                            {
                                    if(PointInTriangle(test,tri.children[2])!=2)
                                    {
                                            tri=tri.children[2];
                                            continue;
                                    }
                            }
                    }	
            //	System.out.println(test);
                    int qw=PointInTriangle(test, tri);
                    if(qw==3 || qw==4 || qw==5)
                    {
                            if(qw==3)
                            {
                                    System.out.println("yahooo");
                                    CreateEdgesTriangles4(g,test, tri,tri.p1,tri.p2);
                                    legalizeTriangle2(tri,test,tri.p1,tri.p2);
                            }
                            else if(qw==4)
                            {
                                    System.out.println("yahooo");
                                    CreateEdgesTriangles4(g,test, tri, tri.p2, tri.p3);
                                    legalizeTriangle2(tri,test,tri.p2,tri.p3);
                            }
                            else
                            {
                                    System.out.println("yahooo");
                                    CreateEdgesTriangles4(g,test, tri, tri.p3, tri.p1);
                                    legalizeTriangle2(tri,test,tri.p3,tri.p1);
                            }
                    }
                    else
                    {
                            CreateEdgesTriangles(test,tri);
                            updateEdges(tri,test);
                            displaytri(g, test, tri);
                            legalizeTriangle(tri,test);
                    }
                    pnum++;
		}
		randomwalk(g);
		intersectingpath(g);
		System.out.println("Total Path Length"+pathnumber);
		System.out.println("Intesecting path length"+intersectingcount);
	}
	public void enlargetriangle(Graphics g)
	{
		double ratiodistance=Math.sqrt(Math.log(numofpoints));
		Point xy1=jkl;
		Point xy2=edge[ytr].a1;
		Point xy3=edge[ytr].a2;
		double d1=Math.sqrt(Math.pow((xy2.x-xy3.x), 2)+Math.pow((xy2.y-xy3.y), 2));
		double d2=Math.sqrt(Math.pow((xy3.x-xy1.x), 2)+Math.pow((xy3.y-xy1.y), 2));
		double d3=Math.sqrt(Math.pow((xy1.x-xy2.x), 2)+Math.pow((xy1.y-xy2.y), 2));
		double incenter_X=((((d1*xy1.x)+(d2*xy2.x)+(d3*xy3.x))/(d1+d2+d3)));
		double incenter_Y=((((d1*xy1.y)+(d2*xy2.y)+(d3*xy3.y))/(d1+d2+d3)));
		Point incenter= new Point((int)((((d1*xy1.x)+(d2*xy2.x)+(d3*xy3.x))/(d1+d2+d3))),(int)(((d1*xy1.y)+(d2*xy2.y)+(d3*xy3.y))/(d1+d2+d3)));
		double inradius=Math.sqrt(((-d1+d2+d3)*(d1-d2+d3)*(d1+d2-d3))/(d1+d2+d3))/2;
		double ratio_distance=(inradius+ratiodistance)/inradius;
		Point xy1_2=new Point((int)(incenter_X+((ratio_distance)*(xy1.x-incenter_X))),(int)(incenter_Y+((ratio_distance)*(xy1.y-incenter_Y))));
		Point xy2_2=new Point((int)(incenter_X+((ratio_distance)*(xy2.x-incenter_X))),(int)(incenter_Y+((ratio_distance)*(xy2.y-incenter_Y))));
		Point xy3_2=new Point((int)(incenter_X+((ratio_distance)*(xy3.x-incenter_X))),(int)(incenter_Y+((ratio_distance)*(xy3.y-incenter_Y))));
		g.setColor(Color.red);
		g.drawLine(xy1_2.x, xy1_2.y, xy2_2.x, xy2_2.y);
		g.drawLine(xy1_2.x, xy1_2.y, xy3_2.x, xy3_2.y);
		g.drawLine(xy3_2.x, xy3_2.y, xy2_2.x, xy2_2.y);
		edges enlargededges=new edges(xy2_2,xy3_2,null,null,null,null);
		edge[ytr]=enlargededges;  //change this lines it will cause problem if not
		jkl=xy1_2;
	}
	public void randomwalk2(Graphics g3) {
		System.out.println("Final count");
		Point yui=new Point((int)closestPoint_X,(int)closestPoint_Y); 
		testingedge=tester_Edges;	
		finalresult=true;
		boolean po1;
		boolean po2;
		Point temp_point;
		while(finalresult==true)
		{
			boolean bh1=(PointInTriangle(perpendicular_lines[perpendiculatcount].a2, testingedge.sh1)!=2);
			boolean bh2=(PointInTriangle(perpendicular_lines[perpendiculatcount].a2, testingedge.sh2)!=2);	
			if(bh1!=true && bh2!=true)
			{	
				po1=sign(testingedge.a1, testingedge.a2, testingedge.b1) <0.0f;
				po2=sign(testingedge.a1, testingedge.a2, yui) <0.0f;
				if(po1==po2)
				{
					temp_point=testingedge.b1;
				}
				else
				{
					temp_point=testingedge.b2;
				}
				finder=Findtrainglewithpoints(testingedge.a1,testingedge.a2,temp_point,testingedge);
				ArrayList<edges> Y2=new ArrayList<edges>();
				Y2.add(finder.e1); 
				Y2.add(finder.e2); 
				Y2.add(finder.e3);
				Y2.remove(testingedge);
				if(intersectioncheck(Y2.get(0).a1, Y2.get(0).a2, perpendicular_lines[perpendiculatcount].a1, perpendicular_lines[perpendiculatcount].a2))
				{
					triangles_intersection_count[perpendiculatcount]++;
					testingedge=Y2.get(0);
				}
				else 
				{
					triangles_intersection_count[perpendiculatcount]++;
					testingedge=Y2.get(1);
				}
			}
			else
			{
				System.out.println(triangles_intersection_count[perpendiculatcount]+1);
				finalresult=false;
			}
		}
	}
	public void cocircle (Point p1, Point p2, Point p3,Graphics g)
	 {
		Point q=new Point();
		double r;
		
		int x12 = p2.x-p1.x;
		int y12 = p2.y-p1.y;
		int x13 = p3.x-p1.x;
		int y13 = p3.y-p1.y;
		int z2 = x12 *(p1.x + p2.x)+ y12 *(p1.y + p2.y);
		int z3 = x13 *(p1.x + p3.x)+ y13 *(p1.y + p3.y);
		int d = 2 *(x12 *(p3.y-p2.y)-y12 *(p3.x-p2.x)); 
		q.x = (int) ((y13 *z2-y12 *z3)/d);
		q.y = (int) ((x12 *z3-x13 *z2)/d);
		System.out.println(q.x + " incenter points"+q.y);
		r = Math.sqrt(((q.x-p1.x)*(q.x-p1.x))+((q.y-p1.y)*(q.y-p1.y)));
		g.drawOval(q.x, q.y, 2*(int)r,2*(int) r);
	 }
	
	public void intersectingpath(Graphics g2) {
		int opiu=0;
		for(int i=0;i<pathnumber-1;i++)
		{
			temporaryint=i;
			if(PointInTriangle(path[i].a1, jkl,edge[ytr].a1,edge[ytr].a2) || PointInTriangle(path[i].a2, jkl,edge[ytr].a1,edge[ytr].a2))
			{ 
				intersectingcount++;
				g2.setColor(Color.blue);
				g2.drawLine(path[i].a1.x,path[i].a1.y,path[i].a2.x,path[i].a2.y);
			}
			else if(intersectioncheck(jkl, edge[ytr].a1, path[i].a1,path[i].a2) || intersectioncheck(jkl, edge[ytr].a2, path[i].a1, path[i].a2))
			{
				intersectingcount++;
				g2.setColor(Color.blue);
				g2.drawLine(path[i].a1.x,path[i].a1.y,path[i].a2.x,path[i].a2.y);
			}
			else
			{ 
				int fx=(path[i].a1.x+path[i].a2.x)/2;
				int fy=(path[i].a1.y+path[i].a2.y)/2;
				Point midpoint=new Point(fx,fy);
				g.drawOval(fx, fy, 2, 2);
				tester_Edges=path[i];
				if(distanceToSegment(jkl, edge[ytr].a1, midpoint,g2)<=distanceToSegment(jkl, edge[ytr].a2,midpoint,g2))
				//if((sign(path[i].a1, jkl, edge[ytr].a1) <= 0.0f)==(sign(path[i].a2, jkl, edge[ytr].a1) <= 0.0f))
				{
					if((distanceToSegment(jkl, edge[ytr].a1,midpoint,g2))<=(distanceToSegment(jkl, edge[ytr].a1, midpoint,g2)))
					{
						distanceToSegment(jkl, edge[ytr].a1, midpoint,g2);
					//	System.out.println("dsjbf"+edgenumbers[i]);
						g.drawLine(midpoint.x, midpoint.y, (int)closestPoint_X, (int)closestPoint_Y);
						perpendiculatcount++;
						perpendicular_lines[perpendiculatcount]=new edges(midpoint,new Point((int)closestPoint_X, (int)closestPoint_Y),null,null,null,null);
						distant[opiu]=DistanceOfPointToLine2(jkl, edge[ytr].a1, midpoint);
						opiu++;
						randomwalk2(g);
					}
					else
					{
					//	System.out.println("edgecount"+edgecount);
						distanceToSegment(jkl, edge[ytr].a1,midpoint,g2);
						g.drawLine(midpoint.x, midpoint.y, (int)closestPoint_X, (int)closestPoint_Y);
						System.out.println("dsjbf"+edgenumbers[i]);
						perpendiculatcount++;
						perpendicular_lines[perpendiculatcount]=new edges(midpoint,new Point((int)closestPoint_X, (int)closestPoint_Y),null,null,null,null);						
						distant[opiu]=DistanceOfPointToLine2(jkl, edge[ytr].a1, midpoint);
						opiu++;
						randomwalk2(g);
					}
				}
				else
				{
					if((distanceToSegment(jkl, edge[ytr].a2, midpoint,g2))<=(distanceToSegment(jkl, edge[ytr].a2, midpoint,g2)))
					{
				//		System.out.println("edgecount"+edgecount);
						distanceToSegment(jkl, edge[ytr].a2, midpoint,g2);
						g.drawLine(midpoint.x,midpoint.y, (int)closestPoint_X, (int)closestPoint_Y);
				//		System.out.println("dsjbf"+edgenumbers[i]);
						perpendiculatcount++;
						perpendicular_lines[perpendiculatcount]=new edges(midpoint,new Point((int)closestPoint_X, (int)closestPoint_Y),null,null,null,null);						
						distant[opiu]=DistanceOfPointToLine2(jkl, edge[ytr].a2, midpoint);
						opiu++;
						randomwalk2(g);
					}
					else
					{
					//	System.out.println("edgecount"+edgecount);
						distanceToSegment(jkl, edge[ytr].a2, midpoint,g2);
						g.drawLine(midpoint.x, midpoint.y, (int)closestPoint_X, (int)closestPoint_Y);
					//	System.out.println("dsjbf"+edgenumbers[i]);
						distant[opiu]=DistanceOfPointToLine2(jkl, edge[ytr].a2, midpoint);
						perpendiculatcount++;
						perpendicular_lines[perpendiculatcount]=new edges(midpoint,new Point((int)closestPoint_X, (int)closestPoint_Y),null,null,null,null);
						opiu++;
						randomwalk2(g);
					}
				}
			}
		}	
	}
    public static double distanceToSegment(Point p1, Point p2, Point p3,Graphics g) {
		double xDelta = p2.x - p1.x;
		double yDelta = p2.y - p1.y;
		if ((xDelta == 0) && (yDelta == 0)) {
		    throw new IllegalArgumentException("p1 and p2 cannot be the same point");
		}
		double u = ((p3.x - p1.x) * xDelta + (p3.y - p1.y) * yDelta) / (xDelta * xDelta + yDelta * yDelta);
		 closestPoint_X=p1.x + (u * xDelta);
		 closestPoint_Y=p1.y + (u * yDelta);
		double diu=Math.sqrt(Math.pow((closestPoint_X-p3.x), 2)+Math.pow((closestPoint_Y-p3.y), 2));
		return diu;
    }
	public void randomwalk(Graphics g3) {
		System.out.println("randomwalk");
		Random rnd = new Random();
		ytr = 0;
		boolean uio=true;
		 int x = 470+rnd.nextInt(340);
	      int y = 300+rnd.nextInt(340);
	      jkl=new Point(x,y);
	      g.setColor(Color.GREEN);
	    	g.drawOval(jkl.x,jkl.y, 5, 5);
	    boolean iop=true;
		while(iop==true)
		{
			ytr = rnd.nextInt(edgecount);
			if(edge[ytr].validity==0)
			{
				iop=false;
			}
		}
		path[pathnumber]=edge[ytr];
		pathnumber++;
		g.setColor(Color.green);
		g.drawLine(edge[ytr].a1.x, edge[ytr].a1.y, edge[ytr].a2.x, edge[ytr].a2.y);
		testingedge=edge[ytr];	
		roottriangles=new triangles(testingedge.a1, testingedge.a2,jkl,null,null,null, null);
		g.drawLine(testingedge.a1.x,testingedge.a1.y,jkl.x,jkl.y);
		g.drawLine(testingedge.a2.x,testingedge.a2.y,jkl.x,jkl.y);
		finalresult=true;
		boolean po1;
		boolean po2;
		while(finalresult==true)
		{
			if(testingedge.b1!=null && testingedge.b2!=null)
			{
				po1=sign(testingedge.a1, testingedge.a2, testingedge.b1) <0.0f;
				po2=sign(testingedge.a1, testingedge.a2, jkl) <0.0f;
				if( po1 == po2)
				{
					finder=Findtrainglewithpoints(testingedge.a1,testingedge.a2,testingedge.b1,testingedge);
					if(PointInTriangle(jkl,finder)!=2)
					{
						finalresult=false;
					}
					else
					{
						pickaedge(testingedge,finder);
					}
				}
				else
				{
					//System.out.println("hsdjfkvbzcmxfl22222222222");
					finder=Findtrainglewithpoints(testingedge.a1,testingedge.a2,testingedge.b2,testingedge);
				//	System.out.println(finder);
					if(PointInTriangle(jkl,finder)!=2)
					{
						finalresult=false;						
					}
					else
					{
						pickaedge(testingedge,finder);
					}
				}
			}
			else 
			{
				if(testingedge.b1==null)
				{
					finder=Findtrainglewithpoints(testingedge.a1,testingedge.a2,testingedge.b2,testingedge);
					System.out.println(finder);
					if(PointInTriangle(jkl,finder)!=2)
					{
						finalresult=false;
						
					}
					else
					{
						pickaedge(testingedge,finder);
					}
				}
				else
				{
					finder=Findtrainglewithpoints(testingedge.a1,testingedge.a2,testingedge.b1,testingedge);
					System.out.println(finder);
					if(PointInTriangle(jkl,finder)!=2)
					{
						finalresult=false;
						
					}
					else
					{
						pickaedge(testingedge,finder);
					}
				}
			}
		}
		drawfinaltriangle(g,finder);
	}
	public void drawfinaltriangle(Graphics g2, triangles finder) {
		g.setColor(Color.ORANGE);
		g.drawLine(finder.e1.a1.x, finder.e1.a1.y,finder.e1.a2.x,finder.e1.a2.y);
		g.drawLine(finder.e2.a1.x, finder.e2.a1.y,finder.e2.a2.x,finder.e2.a2.y);
		g.drawLine(finder.e3.a1.x, finder.e3.a1.y,finder.e3.a2.x,finder.e3.a2.y);
	}
	public void  pickaedge(edges ed5, triangles finder) {
		// TODO Auto-generated method stub
		ArrayList<edges> Y1=new ArrayList<edges>();
		ArrayList<Point> Y2=new ArrayList<Point>();
		ArrayList<Point> Y3=new ArrayList<Point>();
		ArrayList<Point> temp=new ArrayList<Point>();
		Y1.add(finder.e1);
		Y1.add(finder.e2);
		Y1.add(finder.e3);
		Y2.add(finder.p1);
		Y2.add(finder.p2);
		Y2.add(finder.p3);
		Y3.add(finder.p1);
		Y3.add(finder.p2);
		Y3.add(finder.p3);
		Y1.remove(ed5);
		Y3.remove(Y1.get(0).a1);
		Y3.remove(Y1.get(0).a2);
		Y2.remove(Y1.get(1).a1);
		Y2.remove(Y1.get(1).a2);
		boolean po1 = sign(Y1.get(0).a1, Y1.get(0).a2,Y3.get(0)) <0.0f; 
		boolean po2 = sign(Y1.get(0).a1, Y1.get(0).a2, jkl) <0.0f;
		boolean po3 = sign(Y1.get(1).a1, Y1.get(1).a2,Y2.get(0)) <0.0f;
		boolean po4 = sign(Y1.get(1).a1, Y1.get(1).a2, jkl) <0.0f;
		g.setColor(Color.RED);
		if(po1 != po2 && po3 != po4)
		{
			// implement the idea of perpendicularity between those two edges and choose the one which is more perpendicular to the line joining query point
			// and the midpoint of the testing edge
		/*	Point midpoint=new Point((int)(edge[ytr].a1.x+edge[ytr].a2.x)/2,(int)(edge[ytr].a1.y+edge[ytr].a2.y)/2);
			double slope_mainline=(jkl.y-midpoint.y)/(jkl.x-midpoint.x);
			double slope_edge0=(double)(Y1.get(0).a1.y-Y1.get(0).a2.y)/(double)(Y1.get(0).a1.x-Y1.get(0).a2.x);
			double slope_edge1=(double)(Y1.get(1).a1.y-Y1.get(1).a2.y)/(double)(Y1.get(1).a1.x-Y1.get(1).a2.x);
			double product0=slope_edge0*slope_mainline;
			double product1=slope_edge1*slope_mainline;
		//	System.out.println("Product1"+product0);
		//	System.out.println("Product2"+product1);
			if(product0<product1)
			{ */
			//	System.out.println("Choosen edge1");
				testingedge=Y1.get(0);
				path[pathnumber]=testingedge;
				pathnumber++;
				g.drawLine(testingedge.a1.x, testingedge.a1.y, testingedge.a2.x, testingedge.a2.y);
				/*	}
			else
			{
			//	System.out.println("Choosen edge2");
				testingedge=Y1.get(1);
				path[pathnumber]=testingedge;
				pathnumber++;
				g.drawLine(testingedge.a1.x, testingedge.a1.y, testingedge.a2.x, testingedge.a2.y);	
			} */
		}
		else if( po1 != po2)
		{
			testingedge=Y1.get(0);
			path[pathnumber]=testingedge;
			pathnumber++;
			g.drawLine(testingedge.a1.x, testingedge.a1.y, testingedge.a2.x, testingedge.a2.y);
		}
		else
		{
			testingedge=Y1.get(1);
			path[pathnumber]=testingedge;
			pathnumber++;
			g.drawLine(testingedge.a1.x, testingedge.a1.y, testingedge.a2.x, testingedge.a2.y);
		}
//		System.out.println(Y1.size());
//		testingedge=Y1.get(0);
		
	}
	
	public float DistanceOfPointToLine2(Point p1, Point p2, Point p)
	{
	  double ch = (p1.x - p2.y) * p.x + (p2.x - p1.x) * p.y + (p1.x * p2.y - p2.x * p1.y);
	  double del = (Math.pow(p2.x - p1.x, 2) + Math.pow(p2.y - p1.y, 2));
	  double d = ch / del;
	 // System.out.println("distance measurement"+d);
	  return (float)d;
	}
	public triangles Findtrainglewithpoints(Point a1, Point a2, Point b1, edges ed) {
            ArrayList<Point> U1=new ArrayList<Point>();
            U1.add(ed.sh1.p1);
            U1.add(ed.sh1.p2);
            U1.add(ed.sh1.p3);
            ArrayList<Point> U2=new ArrayList<Point>();
            U2.add(ed.sh2.p1);
            U2.add(ed.sh2.p2);
            U2.add(ed.sh2.p3);
            ArrayList<Point> U3=new ArrayList<Point>();
            U3.add(a1);
            U3.add(a2);
            U3.add(b1);
            U1.removeAll(U3);
            U2.removeAll(U3);
            if(U1.size()==0)
            {
                    return ed.sh1;
            }
            else
            {
                    return ed.sh2;
            }
	}
	public void legalizeTriangle2(triangles t, Point t8, Point n1,Point n2) {
		edges yt=updatetri(n1,n2,t);
		if(yt.sh1!=null && yt.sh2!=null)
		{		
		ArrayList<edges> U1=new ArrayList<edges>();
		U1.add(yt.sh1.e1);
		U1.add(yt.sh1.e2);
		U1.add(yt.sh1.e3);
		ArrayList<edges> U2=new ArrayList<edges>();
		U2.add(yt.sh2.e1);
		U2.add(yt.sh2.e2);
		U2.add(yt.sh2.e3);
		ArrayList<edges> U3=new ArrayList<edges>();
		U3.add(yt);
		U1.removeAll(U3);
		U2.removeAll(U3);
		if(!legalizeEdges(t8,U1.get(0)))
		{
			legalizeopposite(Childsearch(yt.sh1,U1.get(0),t8), t8,U1.get(0));
		}
		if(!legalizeEdges(t8,U1.get(1)))
		{
			legalizeopposite(Childsearch(yt.sh1,U1.get(1),t8), t8,U1.get(1));
		}
		if(!legalizeEdges(t8,U2.get(0)))
		{
			legalizeopposite(Childsearch(yt.sh2,U2.get(0),t8), t8,U2.get(0));
		}
		if(!legalizeEdges(t8,U2.get(1)))
		{
			legalizeopposite(Childsearch(yt.sh2,U2.get(1),t8), t8,U2.get(1));
		}
	}
	}
	public void CreateEdgesTriangles4(Graphics g3,Point t9, triangles t,Point m1,Point m2) {
		edges trg=updatetri(m2,m1,t);
		if(trg.b1!=null && trg.b2!=null)
		{
		g3.setColor(Color.BLACK);
		g3.drawLine(t9.x, t9.y, trg.b1.x, trg.b1.y);
		g3.drawLine(t9.x, t9.y, trg.b2.x, trg.b2.y);
		g3.drawLine(t9.x, t9.y, trg.a1.x, trg.a1.y);
		g3.drawLine(t9.x, t9.y, trg.a2.x, trg.a2.y);
		ArrayList<Point> A1=new ArrayList<Point>();
		A1.add(t.p1);
		A1.add(t.p2);
		A1.add(t.p3);
		ArrayList<Point> A2=new ArrayList<Point>();
		A2.add(m1);
		A2.add(m2);
		A1.removeAll(A2);
		Point kl=A1.get(0);
		edge[edgecount]=new edges(t9,trg.a1,arr[Trianglecount],arr[Trianglecount+3],trg.b1,trg.b2);
	    edgecount=edgecount+1;
		edge[edgecount]=new edges(t9,kl,arr[Trianglecount],arr[Trianglecount+1],trg.a1,trg.a2);
		edgecount=edgecount+1;
		edge[edgecount]=new edges(t9,trg.a2,arr[Trianglecount+1],arr[Trianglecount+2],trg.b1,trg.b2);
		edgecount=edgecount+1;
		if(trg.b1==kl)
		{
			edge[edgecount]=new edges(t9,trg.b2,arr[Trianglecount+2],arr[Trianglecount+3],trg.a1,trg.a2);
			edgecount=edgecount+1;
		}
		else
		{
			edge[edgecount]=new edges(t9,trg.b1,arr[Trianglecount+2],arr[Trianglecount+3],trg.a1,trg.a2);
			edgecount=edgecount+1;
		}
		edges jk=updatetri(trg.a1,kl,t);
		arr[Trianglecount]=new triangles(t9,trg.a1,kl,edge[edgecount-4],jk,edge[edgecount-3],t);
		PointSearch(t, jk, t9);
		if(jk.sh1==t)
		{
			jk.sh1=arr[Trianglecount];
		}
		else
		{
			jk.sh2=arr[Trianglecount];
		}
		Trianglecount=Trianglecount+1;
		jk=updatetri(kl,trg.a2,t);
		arr[Trianglecount]=new triangles(t9,kl,trg.a2,edge[edgecount-3],jk,edge[edgecount-2],t);
		PointSearch(t, jk, t9);
		if(jk.sh1==t)
		{
			jk.sh1=arr[Trianglecount];
		}
		else
		{
			jk.sh2=arr[Trianglecount];
		}
		Trianglecount=Trianglecount+1;
		if(trg.b1==kl)
		{
			if(trg.sh1==t)
			{
				jk=updatetri(trg.a2,trg.b2,trg.sh2);
				arr[Trianglecount]=new triangles(t9,trg.a2,trg.b2,edge[edgecount-2],jk,edge[edgecount-1],trg.sh2);
				PointSearch(trg.sh2, jk, t9);
				if(jk.sh1==trg.sh2)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
				jk=updatetri(trg.b2,trg.a1,trg.sh2);
				arr[Trianglecount]=new triangles(t9,trg.b2,trg.a1,edge[edgecount-1],jk,edge[edgecount-3],trg.sh2);
				PointSearch(trg.sh2, jk, t9);
				if(jk.sh1==trg.sh2)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
			}
			else
			{
				jk=updatetri(trg.a2,trg.b2,trg.sh1);
				arr[Trianglecount]=new triangles(t9,trg.a2,trg.b2,edge[edgecount-2],jk,edge[edgecount-1],trg.sh1);
				PointSearch(trg.sh1, jk, t9);
				if(jk.sh1==trg.sh1)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
				jk=updatetri(trg.b2,trg.a1,trg.sh1);
				arr[Trianglecount]=new triangles(t9,trg.b2,trg.a1,edge[edgecount-1],jk,edge[edgecount-3],trg.sh1);
				PointSearch(trg.sh1, jk, t9);
				if(jk.sh1==trg.sh1)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;	
			}
		}
		else
		{
			if(trg.sh1==t)
			{
				jk=updatetri(trg.a2,trg.b1,trg.sh2);
				arr[Trianglecount]=new triangles(t9,trg.a2,trg.b1,edge[edgecount-2],jk,edge[edgecount-1],trg.sh2);
				PointSearch(trg.sh1, jk, t9);
				if(jk.sh1==trg.sh2)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
				jk=updatetri(trg.b1,trg.a1,trg.sh2);
				arr[Trianglecount]=new triangles(t9,trg.b1,trg.a1,edge[edgecount-1],jk,edge[edgecount-3],trg.sh2);
				PointSearch(trg.sh2, jk, t9);
				if(jk.sh1==trg.sh2)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
			}
			else
			{
				jk=updatetri(trg.a2,trg.b1,trg.sh1);
				arr[Trianglecount]=new triangles(t9,trg.a2,trg.b1,edge[edgecount-2],jk,edge[edgecount-1],trg.sh1);
				PointSearch(trg.sh1, jk, t9);
				if(jk.sh1==trg.sh1)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;
				jk=updatetri(trg.b1,trg.a1,trg.sh1);
				arr[Trianglecount]=new triangles(t9,trg.b1,trg.a1,edge[edgecount-1],jk,edge[edgecount-3],trg.sh1);
				PointSearch(trg.sh1, jk, t9);
				if(jk.sh1==trg.sh1)
				{
					jk.sh1=arr[Trianglecount];
				}
				else
				{
					jk.sh2=arr[Trianglecount];
				}
				Trianglecount=Trianglecount+1;	
			}
		}
		edge[edgecount-4].sh1=arr[Trianglecount-4];
		edge[edgecount-4].sh2=arr[Trianglecount-1];
	    edge[edgecount-3].sh1=arr[Trianglecount-4];
	    edge[edgecount-3].sh2=arr[Trianglecount-3];
	    edge[edgecount-2].sh1=arr[Trianglecount-3];
	    edge[edgecount-2].sh2=arr[Trianglecount-2];
	    edge[edgecount-1].sh1=arr[Trianglecount-2];
	    edge[edgecount-1].sh2=arr[Trianglecount-1];
	    t.setChildren(arr[Trianglecount-4], arr[Trianglecount-3], null);
	    if(trg.sh1==t)
	    {
	    	trg.sh2.setChildren(arr[Trianglecount-2], arr[Trianglecount-1], null);
	    }
	    else
	    {
	    	trg.sh1.setChildren(arr[Trianglecount-2], arr[Trianglecount-1], null);
	    }
	    //t.setChildren(arr[Trianglecount-3], arr[Trianglecount-2], arr[Trianglecount-1]);
		}
	}

	public void testing2(Graphics g) {
		int plook=0;
		for (int i=1;i<Trianglecount;i++)
		{
			if(arr[i].validity==0)
			{
				plook++;
				System.out.println(plook);
				for (int j=0;j<numofpoints-1;j++)
				{
					if(po[j]!=arr[i].p1 && po[j]!=arr[i].p2 && po[j]!=arr[i].p3)
					{
						//System.out.println(po[j]);
						if(circumcircleTest(po[j],arr[i]))
						{
							System.out.println("okaayyy");
						}
						else
						{
							System.out.println("mistake" + po[j].x +"  "+po[j].y);
							g.drawOval(po[j].x,po[j].y,5,5);
							cocircle(arr[i].p1,arr[i].p2,arr[i].p3,g);
						}
					}
				}
				
			}
		}
	}
	public boolean circumcircleTest(Point py1, triangles ty1) {
		if(!rightTurn(ty1.p1, ty1.p2, ty1.p3))
		{
			if(signDet4(ty1.p1, ty1.p2, ty1.p3,py1)>0)
			{
				return false; //It is not a delaunay and need edge fliping
			}
			else
			{
				return true;
			}
		}
		else
		{
			if(signDet4(ty1.p1, ty1.p3, ty1.p2,py1)>0)
			{
				return false; //It is not a delaunay and need edge fliping
			}
			else
			{
				return true;
			}
		}	
	}
	public void testing() {
		for(int i=3;i<edgecount;i++)
		{
			if(edge[i].validity==0)
			{
				ArrayList<Point> A1=new ArrayList<Point>();
				A1.add(edge[i].sh1.p1);
				A1.add(edge[i].sh1.p2);
				A1.add(edge[i].sh1.p3);
				ArrayList<Point> A2=new ArrayList<Point>();
				A2.add(edge[i].sh2.p1);
				A2.add(edge[i].sh2.p2);
				A2.add(edge[i].sh2.p3);
				ArrayList<Point> A3=new ArrayList<Point>();
				A3.add(edge[i].a1);
				A3.add(edge[i].a2);
				A1.removeAll(A3);
				A2.removeAll(A3);
				if(A1.get(0)==edge[i].b1)
				{
					if(!legalizeEdges(edge[i].b2, edge[i]))
					{
						System.out.println("fuck");
					}
					else
					{
						System.out.println("ok");
					}
				}
				else
				{
					if(!legalizeEdges(edge[i].b1, edge[i]))
					{
						System.out.println("Fuck");
					}
					else
					{
						System.out.println("ok");
					}
				}
			}
		}
	}
	public void updateEdges(triangles t,Point l) {	
		if(t.e1.sh1==t)
		{
			PointSearch(t,t.e1,l); // will update the opposite point of the edge PointSearch search the t.e1 for b1 and b2 to update with test
			t.e1.sh1=Childsearch(t, t.e1,l); //Search the children which has et.ed1 as a edge and return the child
		}
		else
		{
			PointSearch(t,t.e1,l);
			t.e1.sh2=Childsearch(t, t.e1,l);
		}
		if(t.e2.sh1==t)
		{
			PointSearch(t,t.e2,l);
			t.e2.sh1=Childsearch(t, t.e2,l);
		}
		else
		{
			PointSearch(t,t.e2,l);
			t.e2.sh2=Childsearch(t, t.e2,l);
		}
		if(t.e3.sh1==t)
		{
			PointSearch(t,t.e3,l);
			t.e3.sh1=Childsearch(t, t.e3,l);			
		}
		else
		{
			PointSearch(t,t.e3,l);
			t.e3.sh2=Childsearch(t, t.e3,l);
		}		
	}
	public triangles Childsearch(triangles t, edges e1,Point g) {
		ArrayList<Point> A1=new ArrayList<Point>();
		A1.add(t.children[0].p1);
		A1.add(t.children[0].p2);
		A1.add(t.children[0].p3);
		ArrayList<Point> A2=new ArrayList<Point>();
		A2.add(t.children[1].p1);
		A2.add(t.children[1].p2);
		A2.add(t.children[1].p3);
		ArrayList<Point> A3=new ArrayList<Point>();
		if(t.children[2]!=null)
		{
		A3.add(t.children[2].p1);
		A3.add(t.children[2].p2);
		A3.add(t.children[2].p3);
		}
		ArrayList<Point> s=new ArrayList<Point>();
		s.add(e1.a1);
		s.add(e1.a2);
		s.add(g);
		A1.removeAll(s);
		A2.removeAll(s);
		A3.removeAll(s);
		if(A1.size()==0)
		{
			return t.children[0];
		}
		else if(A2.size()==0)
		{
			return t.children[1];
		}
		else if(A3.size()==0)
		{
			return t.children[2];
		}
		else
		{
			return null;
		}
	}
	public void PointSearch(triangles t1,edges o,Point p) {
		ArrayList<Point> A=new ArrayList<Point>();
		A.add(t1.p1);
		A.add(t1.p2);
		A.add(t1.p3);
		ArrayList<Point> B=new ArrayList<Point>();
		B.add(o.a1);
		B.add(o.a2);
		A.removeAll(B);
		if(o.b1==A.get(0))
		{
			o.b1=p;
		}
		else
		{
			o.b2=p;
		}
	}
	public void displaytri(Graphics g2, Point t5, triangles tri) {
		g2.setColor(Color.black);
		g2.drawLine(t5.x, t5.y, tri.p1.x, tri.p1.y);
		g2.drawLine(t5.x, t5.y, tri.p2.x, tri.p2.y);
		g2.drawLine(t5.x, t5.y, tri.p3.x, tri.p3.y);		
	}
	public void CreateEdgesTriangles(Point t9, triangles t) {
		edge[edgecount]=new edges(t9,t.p1,arr[Trianglecount],arr[Trianglecount+2],t.p2,t.p3);
		edge[edgecount].validity=0;
	    edgecount=edgecount+1;
		edge[edgecount]=new edges(t9,t.p2,arr[Trianglecount+1],arr[Trianglecount],t.p1,t.p3);
		edge[edgecount].validity=0;
		edgecount=edgecount+1;
		edge[edgecount]=new edges(t9,t.p3,arr[Trianglecount+2],arr[Trianglecount+1],t.p1,t.p2);
		edge[edgecount].validity=0;
		edgecount=edgecount+1;
		arr[Trianglecount]=new triangles(t9,t.p1,t.p2,edge[edgecount-3],updatetri(t.p1,t.p2,t),edge[edgecount-2],t);
		arr[Trianglecount].validity=0;
		Trianglecount=Trianglecount+1;
		arr[Trianglecount]=new triangles(t9,t.p2,t.p3,edge[edgecount-2],updatetri(t.p2,t.p3,t),edge[edgecount-1],t);
		arr[Trianglecount].validity=0;
		Trianglecount=Trianglecount+1;
	    arr[Trianglecount]=new triangles(t9,t.p3,t.p1,edge[edgecount-1],updatetri(t.p3,t.p1,t),edge[edgecount-3],t);
	    arr[Trianglecount].validity=0;
	    Trianglecount=Trianglecount+1;
	    edge[edgecount-3].sh1=arr[Trianglecount-3];
	    edge[edgecount-3].sh2=arr[Trianglecount-1];
	    edge[edgecount-2].sh1=arr[Trianglecount-2];
	    edge[edgecount-2].sh2=arr[Trianglecount-3];
	    edge[edgecount-1].sh1=arr[Trianglecount-1];
	    edge[edgecount-1].sh2=arr[Trianglecount-2];
	    t.setChildren(arr[Trianglecount-3], arr[Trianglecount-2], arr[Trianglecount-1]);
	    t.validity=1;
	}
	
	public edges pointInTriangle(Point a,triangles b)
	{
		ArrayList<Point> A1=new ArrayList<Point>();
		A1.add(b.p1);
		A1.add(b.p2);
		A1.add(b.p3);
		ArrayList<Point> A2=new ArrayList<Point>();
		A2.add(a);
		A1.removeAll(A2);
		return updatetri(A1.get(0), A1.get(1), b);
	}
	public void legalizeTriangle(triangles tri, Point n) {
			if(!legalizeEdges(n,tri.e1))
			{
				System.out.println(tri.e1.a1+" E1 "+tri.e1.a2);
				legalizeopposite(Childsearch(tri,tri.e1,n), n,tri.e1);
			}
			if(!legalizeEdges(n,tri.e2))
			{
				System.out.println(tri.e2.a1+" e2   "+tri.e2.a2);
				legalizeopposite(Childsearch(tri,tri.e2,n), n,tri.e2);
			}
			if(!legalizeEdges(n,tri.e3))
			{
				System.out.println(tri.e3.a1+"  E3  "+tri.e3.a2);
				legalizeopposite(Childsearch(tri,tri.e3,n), n,tri.e3);
		
			} 
			
	}
	public edges updatetri(Point a,Point b,triangles v)   //Return the edge where a1 and b1 is present in sh1
	{
		try{
			ArrayList<Point> A1=new ArrayList<Point>();
		//	System.out.println(v.e1);
		//	System.out.println(v.e1.a1);
			A1.add(v.e1.a1);
			A1.add(v.e1.a2);
			ArrayList<Point> A2=new ArrayList<Point>();
			A2.add(v.e2.a1);
			A2.add(v.e2.a2);
			ArrayList<Point> A3=new ArrayList<Point>();
			A3.add(v.e3.a1);
			A3.add(v.e3.a2);
			ArrayList<Point> s=new ArrayList<Point>();
			s.add(a);
			s.add(b);
			A1.removeAll(s);
			A2.removeAll(s);
			A3.removeAll(s);
			if(A1.size()==0)
			{
				return v.e1;
			}
			else if(A2.size()==0)
			{
				return v.e2;
			}
			else if(A3.size()==0)
			{
				return v.e3;
			}
			else
			{
				return null;
			}
		}
		catch(NullPointerException ex)
		{
		/*	for(int i=1;i<Trianglecount;i++)
			{
				System.out.println("triangles number"+i);
				generalInfo(arr[i]);
			}
*/			System.out.println("---"+ex.getMessage());
			ex.printStackTrace();
		//	System.exit(0);
			return null;
		}
		// return null;
	}
	public boolean legalizeEdges(Point m,edges e) {	
		if((e.b1!=null && e.b2!=null ))
		{
			if(!rightTurn(e.a1, e.a2, m))
			{
				if(e.b1==m)
				{
					if(signDet4(e.a1,e.a2,m,e.b2)>0)
					{
						return false; //It is not a delaunay and need edge fliping
					}
					else
					{
						return true;
					}
					
				}	
				else
				{
					if(signDet4(e.a1, e.a2, m, e.b1)>0)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
			}
			else
			{
				if(e.b1==m)
				{
					if(signDet4(e.a2,e.a1,m,e.b2)>0)
					{
						return false;
					}
					else
					{
						return true;
					}
				}	
				else
				{
					if(signDet4(e.a2, e.a1, m, e.b1)>0)
					{
						return false;
					}
					else
					{
						return true;
					}
				}
			}
		}
		else
		{
			return true;
		}
	}
	public boolean rightTurn(Point a, Point b, Point c) {
	    long temp = (a.x*(b.y-c.y) + b.x*(c.y-a.y) + c.x*(a.y-b.y));
	    // Check if it's degenerate
	    if (temp == 0)
	      System.out.println("Exception right turn 0");
	    return(temp > 0);
	 }
	public void legalizeopposite(triangles t,Point r,edges e) // update the edges opposite points
	{
		e.sh1.validity=1;
		e.sh2.validity=1;
		if(e.sh1!=null && e.sh2!=null)
		{
			t.validity=1;
			g.setColor(Color.white);
			g.drawLine(e.a1.x, e.a1.y, e.a2.x, e.a2.y);
			e.validity=1;
			edges X1=new edges();
			edges Y1=new edges();
			edges X2=new edges();
			edges Y2=new edges();
			edge[edgecount]=new edges(e.b1,e.b2,arr[Trianglecount],arr[Trianglecount+1],e.a1,e.a2);
			edge[edgecount].validity=0;
			edges q=edge[edgecount];
			g.setColor(Color.black);
			g.drawLine(q.a1.x, q.a1.y, q.a2.x, q.a2.y);
			if(e.b1==r)
			{
				if(e.sh1==t)
				{
					X1=updatetri(e.a1, e.b1, e.sh1);
					Y1=updatetri(e.a1,e.b2,e.sh2);
					X2=updatetri(e.a2,e.b1,e.sh1);
					Y2=updatetri(e.a2,e.b2,e.sh2);
					arr[Trianglecount]=new triangles(e.b1,e.a1,e.b2,X1,Y1,q,e.sh1);
					arr[Trianglecount].validity=0;
					e.sh1.children[0]=arr[Trianglecount];
					e.sh2.children[0]=arr[Trianglecount];
					if(X1.sh1==t)
					{
						X1.sh1=arr[Trianglecount];
					}
					else
					{
						X1.sh2=arr[Trianglecount];
					}
					if(X1.b1==e.a2)
					{
						X1.b1=e.b2;
					}
					else
					{
						X1.b2=e.b2;
					}
					if(Y1.sh1==e.sh2)
					{
						Y1.sh1=arr[Trianglecount];
					}
					else
					{
						Y1.sh2=arr[Trianglecount];
					}
					if(Y1.b1==e.a2)
					{
						Y1.b1=e.b1;
					}
					else
					{
						Y1.b2=e.b1;
					}
					arr[Trianglecount+1]=new triangles(e.b1,e.a2,e.b2,X2,Y2,q,e.sh1);
					arr[Trianglecount+1].validity=0;
					e.sh1.children[1]=arr[Trianglecount+1];
					e.sh2.children[1]=arr[Trianglecount+1];		
					if(X2.sh1==t)
					{
						X2.sh1=arr[Trianglecount+1];
					}
					else
					{
						X2.sh2=arr[Trianglecount+1];
					}
					if(X2.b1==e.a1)
					{
						X2.b1=e.b2;
					}
					else
					{
						X2.b2=e.b2;
					}
					if(Y2.sh1==e.sh2)
					{
						Y2.sh1=arr[Trianglecount+1];
					}
					else
					{
						Y2.sh2=arr[Trianglecount+1];
					}
					if(Y2.b1==e.a1)
					{
						Y2.b1=e.b1;
					}
					else
					{
						Y2.b2=e.b1;
					}
				}
				else
				{
					X1=updatetri(e.a1, e.b1, e.sh2);
					Y1=updatetri(e.a1,e.b2,e.sh1);
					X2=updatetri(e.a2,e.b1,e.sh2);
					Y2=updatetri(e.a2,e.b2,e.sh1);
					arr[Trianglecount]=new triangles(e.b1,e.a1,e.b2,X1,Y1,q,e.sh1);
					arr[Trianglecount].validity=0;
					e.sh1.children[0]=arr[Trianglecount];
					e.sh2.children[0]=arr[Trianglecount];
					if(X1.sh1==t)
					{
						X1.sh1=arr[Trianglecount];
					}
					else
					{
						X1.sh2=arr[Trianglecount];
					}
					if(X1.b1==e.a2)
					{
						X1.b1=e.b2;
					}
					else
					{
						X1.b2=e.b2;
					}
					if(Y1.sh1==e.sh1)
					{
						Y1.sh1=arr[Trianglecount];
					}
					else
					{
						Y1.sh2=arr[Trianglecount];
					}
					if(Y1.b1==e.a2)
					{
						Y1.b1=e.b1;
					}
					else
					{
						Y1.b2=e.b1;
					}
					arr[Trianglecount+1]=new triangles(e.b1,e.a2,e.b2,X2,Y2,q,e.sh1);
					arr[Trianglecount+1].validity=0;
					e.sh1.children[1]=arr[Trianglecount+1];
					e.sh2.children[1]=arr[Trianglecount+1];
					
					if(X2.sh1==t)
					{
						X2.sh1=arr[Trianglecount+1];
					}
					else
					{
						X2.sh2=arr[Trianglecount+1];
					}
					if(X2.b1==e.a1)
					{
						X2.b1=e.b2;
					}
					else
					{
						X2.b2=e.b2;
					}
					if(Y2.sh1==e.sh1)
					{
						Y2.sh1=arr[Trianglecount+1];
					}
					else
					{
						Y2.sh2=arr[Trianglecount+1];
					}
					if(Y2.b1==e.a1)
					{
						Y2.b1=e.b1;
					}
					else
					{
						Y2.b2=e.b1;
					}
				}
			}
			else
			{
				if(e.sh1==t)
				{
					X1=updatetri(e.a1, e.b2, e.sh1);
					Y1=updatetri(e.a1,e.b1,e.sh2);
					X2=updatetri(e.a2,e.b2,e.sh1);
					Y2=updatetri(e.a2,e.b1,e.sh2);
					arr[Trianglecount]=new triangles(e.b1,e.a1,e.b2,X1,Y1,q,e.sh1);
					arr[Trianglecount].validity=0;
					e.sh1.children[0]=arr[Trianglecount];
					e.sh2.children[0]=arr[Trianglecount];
					if(X1.sh1==t)
					{
						X1.sh1=arr[Trianglecount];
					}
					else
					{
						X1.sh2=arr[Trianglecount];
					}
					if(X1.b1==e.a2)
					{
						X1.b1=e.b1;
					}
					else
					{
						X1.b2=e.b1;
					}
					if(Y1.sh1==e.sh2)
					{
						Y1.sh1=arr[Trianglecount];
					}
					else
					{
						Y1.sh2=arr[Trianglecount];
					}
					if(Y1.b1==e.a2)
					{
						Y1.b1=e.b2;
					}
					else
					{
						Y1.b2=e.b2;
					}
					arr[Trianglecount+1]=new triangles(e.b1,e.a2,e.b2,X2,Y2,q,e.sh1);
					arr[Trianglecount+1].validity=0;
					e.sh1.children[1]=arr[Trianglecount+1];
					e.sh2.children[1]=arr[Trianglecount+1];
					
					if(X2.sh1==t)
					{
						X2.sh1=arr[Trianglecount+1];
					}
					else
					{
						X2.sh2=arr[Trianglecount+1];
					}
					if(X2.b1==e.a1)
					{
						X2.b1=e.b1;
					}
					else
					{
						X2.b2=e.b1;
					}
					if(Y2.sh1==e.sh2)
					{
						Y2.sh1=arr[Trianglecount+1];
					}
					else
					{
						Y2.sh2=arr[Trianglecount+1];
					}
					if(Y2.b1==e.a1)
					{
						Y2.b1=e.b2;
					}
					else
					{
						Y2.b2=e.b2;
					}
				}
				else
				{
					//System.out.println(e.a1+"-----, "+e.b2+"-----, "+e.sh2);
					X1=updatetri(e.a1,e.b2,e.sh2);
				//	System.out.println(e.a1+"---, "+e.b1+"--, "+e.sh1);
					Y1=updatetri(e.a1,e.b1,e.sh1);
				//	System.out.println(e.a2+"---, "+e.b2+"--, "+e.sh2);
					X2=updatetri(e.a2,e.b2,e.sh2);
					Y2=updatetri(e.a2,e.b1,e.sh1);
					arr[Trianglecount]=new triangles(e.b1,e.a1,e.b2,X1,Y1,q,e.sh1);
					arr[Trianglecount].validity=0;
					e.sh1.children[0]=arr[Trianglecount];
					e.sh2.children[0]=arr[Trianglecount];
					if(X1.sh1==t)
					{
						X1.sh1=arr[Trianglecount];
					}
					else
					{
						X1.sh2=arr[Trianglecount];
					}
					if(X1.b1==e.a2)
					{
						X1.b1=e.b1;
					}
					else
					{
						X1.b2=e.b1;
					}
					if(Y1.sh1==e.sh1)
					{
						Y1.sh1=arr[Trianglecount];
					}
					else
					{
						Y1.sh2=arr[Trianglecount];
					}
					if(Y1.b1==e.a2)
					{
						Y1.b1=e.b2;
					}
					else
					{
						Y1.b2=e.b2;
					}
					arr[Trianglecount+1]=new triangles(e.b1,e.a2,e.b2,X2,Y2,q,e.sh1);
					arr[Trianglecount+1].validity=0;
					e.sh1.children[1]=arr[Trianglecount+1];
					e.sh2.children[1]=arr[Trianglecount+1];
					
					if(X2.sh1==t)
					{
						X2.sh1=arr[Trianglecount+1];
					}
					else
					{
						X2.sh2=arr[Trianglecount+1];
					}
					if(X2.b1==e.a1)
					{
						X2.b1=e.b1;
					}
					else
					{
						X2.b2=e.b1;
					}
					if(Y2.sh1==e.sh1)
					{
						Y2.sh1=arr[Trianglecount+1];
					}
					else
					{
						Y2.sh2=arr[Trianglecount+1];
					}
					if(Y2.b1==e.a1)
					{
						Y2.b1=e.b2;
					}
					else
					{
						Y2.b2=e.b2;
					}
				}
			}
			edge[edgecount].sh1=arr[Trianglecount];
			edge[edgecount].sh2=arr[Trianglecount+1];
			edgecount=edgecount+1;
			Trianglecount=Trianglecount+2;
			if(!legalizeEdges(r,pointInTriangle(r, arr[Trianglecount-2])))
			{
				legalizeopposite(arr[Trianglecount-2], r,pointInTriangle(r, arr[Trianglecount-2]));
			}
			if(!legalizeEdges(r,pointInTriangle(r,arr[Trianglecount-1])))
			{
				legalizeopposite(arr[Trianglecount-1], r,pointInTriangle(r, arr[Trianglecount-1]));
			}
		}
	}
	public void generalInfo(triangles t)
	{
	//	System.out.println(t+"----"+t.p1+"----"+t.p2+"------"+t.p3);
	//	System.out.println("edges1"+t.e1+"edges"+t.e2+"edges"+t.e3);
	/*	System.out.println("edgesPoints"+t.e1.a1);
		System.out.println("edgesPoints"+t.e1.a2); */
		System.out.println("edges1111");
		System.out.println(t.e1.sh2.e1+", "+ t.e1.sh2.e2+", "+t.e1.sh2.e3);
	System.out.println(t.e1.sh1.e1+", "+ t.e1.sh1.e2+", "+t.e1.sh1.e3);
	
		// System.out.println(t.e1.sh2.e2);
		System.out.println("edges222222");
		System.out.println(t.e2.sh2.e1+", "+ t.e2.sh2.e2+", "+t.e2.sh2.e3);
	System.out.println(t.e2.sh1.e1+", "+ t.e2.sh1.e2+", "+t.e2.sh1.e3);
		
		System.out.println("edges3333");
		System.out.println(t.e3.sh2.e1+", "+ t.e3.sh2.e2+", "+t.e3.sh2.e3);
		System.out.println(t.e3.sh1.e1+", "+ t.e3.sh1.e2+", "+t.e3.sh1.e3);
		

		/*
		System.out.println(t.e1.b1);
		System.out.println(t.e1.b2);
		System.out.println("edge2");
		System.out.println(t.e2.sh1);
		System.out.println(t.e2.sh2);
		System.out.println(t.e2.b1);
		System.out.println(t.e2.b2);

		System.out.println("edgesPoints"+t.e1.a1);
		System.out.println("edgesPoints"+t.e1.a2);
		System.out.println("edge3");
		System.out.println(t.e3.sh1);
		System.out.println(t.e3.sh2);
		System.out.println(t.e3.b1);
		System.out.println(t.e3.b2); */
	}
	public void displaytri(Graphics g, triangles tre) {
		// 
		g.setColor(Color.ORANGE);
		g.drawLine(tre.e1.a1.x, tre.e1.a1.y, tre.e1.a2.x, tre.e1.a2.y);
		g.drawLine(tre.e2.a1.x, tre.e2.a1.y, tre.e2.a2.x, tre.e2.a2.y);
		g.drawLine(tre.e3.a1.x, tre.e3.a1.y, tre.e3.a2.x, tre.e3.a2.y);
	}
	public int signDet4(Point p, Point q, Point r, Point s){
	    
	    // to prevent overflow I used long
	    long pz = p.x*p.x + p.y*p.y;
	    long qz = q.x*q.x + q.y*q.y;
	    long rz = r.x*r.x + r.y*r.y;
	    long sz = s.x*s.x + s.y*s.y;

	    long x = det(1,p.x,1,q.x)*det(r.y,rz,s.y,sz) -
	      det(1,p.y,1,q.y)*det(r.x,rz,s.x,sz) + 
	      det(1,pz,1,qz)*det(r.x,r.y,s.x,s.y) + 
	      det(p.x,p.y,q.x,q.y)*det(1,rz,1,sz) - 
	      det(p.x,pz,q.x,qz)*det(1,r.y,1,s.y) +
	      det(p.y,pz,q.y,qz)*det(1,r.x,1,s.x);
	    if (x>0)
	      return (1);
	    else 
	      return (-1);
	  }
	public long det(long a, long b, long c, long d) {
	    return (a*d - b*c);
	  }
	public void displaytri(Graphics g,Point v1, Point v2, Point v3) { 
			g.setColor(Color.ORANGE);
			g.drawLine(v1.x,v1.y,v2.x,v2.y);
			g.drawLine(v2.x,v2.y,v3.x,v3.y);
			g.drawLine(v3.x,v3.y,v1.x,v1.y);			
	}
	public float sign(Point p1, Point p2, Point p3)
	{
	  return (p1.x - p3.x) * (p2.y - p3.y) - (p2.x - p3.x) * (p1.y - p3.y);
	}
	public int PointInTriangle(Point pt,triangles t)
	{
		boolean b1, b2, b3;
		if(sign(pt, t.p1, t.p2)!=0.0)
		{
			b1 = (sign(pt, t.p1, t.p2) < 0.0f);
		}
		else
		{
			return 3;
		}
		if(sign(pt, t.p2, t.p3)!=0.0)
		{
		  b2 = (sign(pt, t.p2, t.p3) < 0.0f);
		}
		else
		{
		  return 4;
		  
		}
		if(sign(pt, t.p3, t.p1)!=0.0)
		{
		  b3 = (sign(pt, t.p3, t.p1) < 0.0f);
		}
		else
		{
			return 5;			
		}
		if((b1 == b2) && (b2 == b3))
		{
			return 0;	
		}
		else
		{
			return 2;
		}
		//return ((b1 == b2) && (b2 == b3));		
	}

	public boolean intersectioncheck(Point p1,Point p2,Point p3,Point p4)
	{
		boolean b1,b2,b3,b4;
		b1 = sign(p3, p1, p2) < 0.0f;
		b2 = sign(p4, p1, p2) < 0.0f;
		b3 = sign(p1, p3, p4) < 0.0f;
		b4 = sign(p2, p3, p4) < 0.0f;
		if(b1!=b2 && b3!=b4)
		{
			return true; // they intersect
		}
		else
		{
			return false;
		}
	}

	public boolean PointInTriangle(Point pt, Point v1, Point v2, Point v3)
	{
	  boolean b1, b2, b3;
	  b1 = sign(pt, v1, v2) < 0.0f;
	  b2 = sign(pt, v2, v3) < 0.0f;
	  b3 = sign(pt, v3, v1) < 0.0f;
	  return ((b1 == b2) && (b2 == b3));
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		
	}
	public void firsttriangle(Graphics g)
	{
		
	}
}
