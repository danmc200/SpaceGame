#include <cstdio>

struct point
{
    float x;
    float y;
};

// simple linear interpolation between two points
void lerp(point& dest, const point& a, const point& b, const float t)
{
    dest.x = a.x + (b.x-a.x)*t;
    dest.y = a.y + (b.y-a.y)*t;
}

// evaluate a point on a bezier-curve. t goes from 0 to 1.0
void bezier(point &dest, const point& a, const point& b, const point& c, const point& d, const float t)
{
    point ab,bc,cd,abbc,bccd;
    lerp(ab, a,b,t);           // point between a and b (green)
    lerp(bc, b,c,t);           // point between b and c (green)
    lerp(cd, c,d,t);           // point between c and d (green)
    lerp(abbc, ab,bc,t);       // point between ab and bc (blue)
    lerp(bccd, bc,cd,t);       // point between bc and cd (blue)
    lerp(dest, abbc,bccd,t);   // point on the bezier-curve (black)
}

// small test program.. just prints the points
int main()
{
    // 4 points define the bezier-curve. These are the points used
    // for the example-images on this page.
    point a = { 40, 100 };
    point b = { 80, 20  };
    point c = { 150, 180 };
    point d = { 260, 100 };

    for (int i=0; i<1000; ++i)
    {
	point p;
	float t = static_cast<float>(i)/999.0;
	bezier(p,a,b,c,d,t);
	printf("%f %f\n", p.x, p.y);
    }

    return 0;
}

