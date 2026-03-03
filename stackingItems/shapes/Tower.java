import java.util.ArrayList;
/**
 * Write a description of class Tower here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Tower
{
    private static final int SCALE = 20;
    private static final int margen = 30;
    private int heightTower;
    private int widthTower;
    private boolean isVisible;
    private boolean itsOk;
    private ArrayList<Cup> cups;
    private ArrayList<Lid> lids;
    private int currentHeight;
    private boolean lastOperationOk;
    
    
    /**
     * Constructor for objects of class Tower
     */
    public Tower(int width, int maxHeight)
    {
        this.widthTower = width * SCALE;
        this.heightTower = maxHeight;
        this.currentHeight = 0;
        this.isVisible = false;
        this.lastOperationOk = true;
        this.cups = new ArrayList<Cup>();
        this.lids = new ArrayList<Lid>();
    }

    public Tower(int numCups){
        this.cups = new ArrayList<Cup>();
        this.lids = new ArrayList<Lid>();
        this.currentHeight = 0;
        this.isVisible = false;
        this.lastOperationOk = true;
        int totalHeight = 0;
        int maxWidth = 0;
        for (int i = 1; i <= numCups; i++){
            totalHeight += (2 * i - 1);
            if ((2*i-1) > maxWidth){
                maxWidth = (2 * i - 1);
            }
        }
        this.heightTower = totalHeight;
        this.widthTower = maxWidth * SCALE;
        for (int i = 1; i <= numCups; i++){
            int cupHiegthPixels = (2 * i - 1)* SCALE;
            Cup cup = new Cup(i, widthTower);
            int yPixels = (heightTower - currentHeight) * SCALE - cupHeightPixels;
            cup.setPosition(margen, yPixels);
            cups.add(cup);
            currentHeight += (2 * i - 1);
        }
    }

    
    public void pushCup(int i)
    {
        if (!existeCup(i) && cabe(i)){
            Cup cup = new Cup(i, widthTower);
            int yPixels = (heightTower - currentHeight - i)* SCALE;
            cup.setPosition(margen, yPixels);
            if(isVisible){
                cup.makeVisible();
            }
            cups.add(cup);
            currentHeight += i;
            lastOperationOk = true;
        
        }else{
            lastOperationOk = false;
        }
        
    }
    
    public void popCup()
    {
        if (!cups.isEmpty() && ultimoElementoEnTaza()){
            Cup top = cups.get(cups.size()-1);
            currentHeight -= top.getCupHeigth();
            top.makeInvisible();
            cups.remove(cups.size()-1);
            lastOperationOk = true;
        }else{
            lastOperationOk = false;
        }
        
    }
    
    public void removeCup(int i){
        if (existeCup(i)){
            Cup cup = buscarCup(i);
            if (cup.hasLid()){
                cup.getLid().setCup(null);
                cup.setLid(null);
            }
            currentHeight -= cup.getCupHeight();
            cup.makeInvisible();
            cups.remove(cup);
            redibujarElementos();
            lastOperationOk = true;
        }else{
            lastOperationOk = false;
        }
    }
    
    public void pushLid(int i){
        if (!existeLid(i) && currentHeight + 1 <= heightTower){
            Lid lid = new Lid(i, widthTower);
            int yPixels = (heightTower - currentHieght - 1)* SCALE;
            lid.setPosition(margen, yPixels);
            if (isVisible){
                lid.makeVsisible();
            }
            lids.add(Lid);
            if (existeCup(i)){
                Cup cup =buscarCup(i);
                cup.setLid(lid);
                lid.setCup(cup);
            }
            currentHeight += 1;
            lastOperationOk = true;
        }else{
            lastOperationOk = false;
        }
    }
    
    public void popLid(){
        if (!lids.isEmpty() && ultimoElementoEsTapa()){
            Lid top = lids.get(lids.size()-1);
            if (top.getCup != null){
                top.getCup().setLid(null);
                top.setCup(null);
            }
            currentHeight -= 1;
            top.makeInvisible();
            lids.remove(lids.size() - 1);
            lastOperationOk = true;
        }else{
            lastOperationOk = false;
        }
    }
    
    public void removeLid(int i)
    {
     if (existeLid(i)){
         Lid lid = buscarLid(i);
         if (lid.getCup() != null){
             lid.getCup().setLid(null);
             lid.setCup(null);
         }
         currentHieght -= 1;
         lid.makeVisible();
         lids.remove(lid);
         redibujarElementos();
         lastOperationOk = true;
     }else{
         lastOperationOk = false;
     }
        
    }
    
    public void orderTower()
    {
        ArrayList<Object[]> elementos = recolectarElementos();
        ordenarElementos(elementos, false);
        ArraList<Object[]> queCaben = calcularQueCaben(elementos);
        redibujarConUnidades(queCaben);
        lastOperationOk = true;
    }
    
    public void reverseTower()
    {
        ArrayList<Object[]> units = recolectarElementos();
        ordenarElementos(elementos, true);
        ArrayList<Object[]> queCaben = calcularQueCaben(units);
        redibujarConUnidades(queCaben);
        lastOperationOk = true;
    }
    
    public void swap(String[] o1, String[] o2){
        if (existeObjeto(o1) && existeObjeto(o2)){
            intercambiarPosiciones(o1, o2);
            lastOperationOk = true;
        }else{
            lastOperationOk = false;
        }
    }
    
    public void cover(){
        for (Cup cup : cups){
            if (!cup.hasLid() && existeLid(cup.getCupNumber())){
                Lid lid = buscarLid(cup.getCupNumber());
                cup.setLid(lid);
                lid.setCup(cup);
            }
        }
        redibujarElementos();
        lastOperationOk = true;
    }
    public int height()
    {
       return currentHeight; 
    }
    
    public int[] lidedCups()
    {
       ArrayList<Integer> result = new ArrayList<Integer>();
       for(Cup cup : cups){
           if (cup.hasLid()){
               result.add(cup.getCupNumber());
           }
           Collections.sort(result);
           int[] arr = new int[result.size()];
           for (int i = 0; i < result.size(); i++){
               arr[i] = result.get(i);
           }
       }
       return arr;
    }
    
    public String[][] stackingItems()
    {
        ArrayList<String[]> items = new ArrayList<String[]>();
        for (Cup cup : cups){
            items.add(new String[]{"cup", String.valueOf(cup.getCupNumber())});
            if (cup.hasLid()){
                items.add(new String[]{"lid", String.valueOf(lid.getLidNumber())});
            }
        }
        return items.toArray(new String[0][]);
    }  
    
    public  String[][] swapToReduce(){
        ArrayList<Object[]> units = recolectarElementos();
        int alturaActual = calcularAlturaElementos();
        for (int i = 0; i < units.size() - 1; i++){
            for (int j = i + 1; j < units.size(); j++){
                if (simularIntercambio(units, i,j) < alturaActual){
                    Cup c1 = (Cup) units.get(i)[0];
                    Cup c2 = (Cup) units.get(j)[0];
                    return new String[][]{
                        {"cup", String.valueOf(c1.getCupNumber())},
                        {"cup", String.valueOf(c2.getCupNumber())}
                    };
                }
            }
        }
        return null;
    }
    public void makeVisible()
    {
       isVisible = true;
       Canvas.getCanvas().setVisible(true);
       for (Cup cup : cups){
           cup.makeVisible();
       }
       
       for (Lid lid: lids){
           lid.makeVisible();
       }
    }
    
    public void makeInvisible()
    {
        isVisible = false;
        Canvas.getCanvas().setVsisible(false);
        for (Cup cup: cups){
            cup.makeInvisible();
        }
        
        for (Lid lid: lids){
            lid.makeInvisible();
        }
    }

    public void exit()
    {
        makeInvisible();
    }
    
    public boolean ok()
    {
        return lastOperationOk;
    }
    
    private boolean existeCup(int i){
        return buscarCup(i) != null;
    }
    
    private boolean existeLid(int i){
        return buscarLid(i) != null;
    }
    
    private boolean existeObjeto(String[] o){
        int num = 
    }
}
