import javax.swing.*;
import java.awt.*;

public class Object {
  
    public Image imj;
    public int x;
    public int y;
    public int widt;
    public int heigh;


    public Object() {
        this(0, 0, 0, 0,"");
    }

        public Object(Object r) {
        this(r.x, r.y, r.widt, r.heigh,"");
    }


    public Object(int x, int y, int width, int height, String loc ) {
        this.x = x;
        this.y = y;
        this.widt = width;
        this.heigh = height;
        this.imj=new ImageIcon(loc).getImage();
           }

    public Object(int width, int height) {
        this(0, 0, width, height,"");
    }

    public boolean intersects(Object r) {
        int tw = this.widt;
        int th = this.heigh;
        int rw = r.widt;
        int rh = r.heigh;
        if (rw <= 0 || rh <= 0 || tw <= 0 || th <= 0) {
            return false;
        }
        int tx = this.x;
        int ty = this.y;
        int rx = r.x;
        int ry = r.y;
        rw += rx;
        rh += ry;
        tw += tx;
        th += ty;
       
        return ((rw < rx || rw > tx) &&
                (rh < ry || rh > ty) &&
                (tw < tx || tw > rx) &&
                (th < ty || th > ry));
    }
}