import java.awt.Point;


public class edges {
	Point a1;
	Point a2;
	triangles sh1;
	triangles sh2;
	Point b1;
	Point b2;
	int validity;
	edges(Point a1,Point a2,triangles sh1,triangles sh2,Point b1,Point b2)
	{
		this.a1=a1;
		this.a2=a2;
		this.sh1=sh1;
		this.sh2=sh2;
		this.b1=b1;
		this.b2=b2;
	}
	public edges() {
		// TODO Auto-generated constructor stub
	}
}
