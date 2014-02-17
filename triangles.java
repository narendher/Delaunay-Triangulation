import java.awt.Point;
public class triangles
{
	public
		edges e1;
		edges e2;
		edges e3;
		Point p1;
		Point p2;
		Point p3;
		triangles parent;
		int validity; //0 if it is delaunay 1 if it is not
		triangles children[]=new triangles[3];	
		triangles()
		{
			
		}
		public void setChildren(triangles ch1,triangles ch2,triangles ch3)
		{
			this.children[0]=ch1;
			this.children[1]=ch2;
			this.children[2]=ch3;
		}
		triangles(Point p1,Point p2,Point p3,edges e1,edges e2,edges e3,triangles parent)
		{
			this.p1=p1;
			this.p2=p2;
			this.p3=p3;
			this.e1=e1;
			this.e2=e2;
			this.e3=e3;
			this.parent=parent;
		}
}
