package boris.narodov.catsinsquares;

import java.util.ArrayList;
import java.util.List;

public class TetrisObjects {

    private ArrayList[] tetrises = {(ArrayList) object1(), (ArrayList) object2(), (ArrayList) object3(), (ArrayList) object4(),
            (ArrayList) object5(), (ArrayList) object6(), (ArrayList) object7(), (ArrayList) object8(), (ArrayList) object9(),
            (ArrayList) object10(), (ArrayList) object11(), (ArrayList) object12(), (ArrayList) object13(), (ArrayList) object14(),
            (ArrayList) object15(), (ArrayList) object16(), (ArrayList) object17(), (ArrayList) object18(), (ArrayList) object19(),
            (ArrayList) object20(), (ArrayList) object21(), (ArrayList) object22(), (ArrayList) object23(), (ArrayList) object24()};

    private int a;
    public TetrisObjects() {
        a = (int) (Math.random()*tetrises.length);
    }

    public ArrayList getFigure() {
        return tetrises[a];
    }

    private List<GameObject> object1(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object = new GameObject(5,0);
        tetrisObj.add(object);
        return tetrisObj;
    }

    private List<GameObject> object2(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        GameObject object3 = new GameObject(7,0);
        GameObject object4 = new GameObject(6,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object3(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,1);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(7,1);
        GameObject object4 = new GameObject(6,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object4(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(5,2);
        GameObject object4 = new GameObject(6,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object5(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(6,0);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(6,2);
        GameObject object4 = new GameObject(5,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object6(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(5,2);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        return tetrisObj;
    }

    private List<GameObject> object7(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        GameObject object3 = new GameObject(7,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        return tetrisObj;
    }

    private List<GameObject> object8(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        GameObject object3 = new GameObject(6,1);
        GameObject object4 = new GameObject(7,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object9(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,1);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(6,0);
        GameObject object4 = new GameObject(7,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object10(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(6,1);
        GameObject object4 = new GameObject(6,2);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object11(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,2);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(6,1);
        GameObject object4 = new GameObject(6,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object12(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(6,0);
        GameObject object4 = new GameObject(6,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object13(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        return tetrisObj;
    }

    private List<GameObject> object14(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        return tetrisObj;
    }

    private List<GameObject> object15(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        GameObject object3 = new GameObject(7,0);
        GameObject object4 = new GameObject(7,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object16(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(6,0);
        GameObject object3 = new GameObject(7,0);
        GameObject object4 = new GameObject(5,1);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object17(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,1);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(7,1);
        GameObject object4 = new GameObject(7,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object18(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,1);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(7,1);
        GameObject object4 = new GameObject(5,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object19(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(5,2);
        GameObject object4 = new GameObject(6,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object20(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(5,2);
        GameObject object4 = new GameObject(6,2);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object21(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(6,0);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(6,2);
        GameObject object4 = new GameObject(5,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object22(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(6,0);
        GameObject object2 = new GameObject(6,1);
        GameObject object3 = new GameObject(6,2);
        GameObject object4 = new GameObject(5,2);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object23(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(5,0);
        GameObject object2 = new GameObject(5,1);
        GameObject object3 = new GameObject(5,2);
        GameObject object4 = new GameObject(5,3);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }

    private List<GameObject> object24(){
        List<GameObject> tetrisObj = new ArrayList<>();
        GameObject object1 = new GameObject(4,0);
        GameObject object2 = new GameObject(5,0);
        GameObject object3 = new GameObject(6,0);
        GameObject object4 = new GameObject(7,0);
        tetrisObj.add(object1);
        tetrisObj.add(object2);
        tetrisObj.add(object3);
        tetrisObj.add(object4);
        return tetrisObj;
    }
}
