
/**
 * Write a description of class Cup here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Cup
{
    private int cupHeight;
    private Lid ofLid;
    private String colorCup;
    private Rectangle[] cupRectangles;
    private int xPositionCup;
    private int yPositionCup;
    private int cupNumber;
    private int cupWidth;
    
    

    /**
     * Constructor for objects of class Cup
     */
    public Cup(int cupNumber, int cupWidth)
    {
        this.cupNumber = cupNumber;
        this.cupHeight = cupNumber;
        this.cupWidth = cupWidth;
        this.ofLid = null;
        this.colorCup = assignColor(cupNumber);
        this.xPositionCup = 0;
        this.yPositionCup = 0;
        this.cupRectangles = new Rectangle[3];
        construirTaza();
    }

    private String assignColor(int number) {
        String[] colors = {"yellow","blue","red","green","green","orange"};
        return colors[(number-1) % colors.length];
    }
    
    private void construirTaza(){
        int grosor = 3;
        cupRectangles[0] = new Rectangle();
        cupRectangles[0].changeSize(cupHeight, grosor);
        cupRectangles[0].changeColor(colorCup);
        
        cupRectangles[1] = new Rectangle();
        cupRectangles[1].changeSize(cupHeight, grosor);
        cupRectangles[1].changeColor(colorCup);
        
        cupRectangles[2] = new Rectangle();
        cupRectangles[2].changeSize(grosor, cupWidth);
        cupRectangles[2].changeColor(colorCup);
        
    }
    
    public void setPosition(int x, int y){
        this.xPositionCup = x;
        this.yPositionCup = y;
        int width = cupWidth;
        int grosor = 3;
        
        cupRectangles[0].moveHorizontal(x - cupRectangles[0].getXPosition());
        cupRectangles[0].moveVertical(y - cupRectangles[0].getYPosition());
        cupRectangles[1].moveHorizontal((x + width - grosor) - cupRectangles[1].getXPosition());
        cupRectangles[1].moveVertical(y - cupRectangles[1].getYPosition());
        cupRectangles[2].moveHorizontal(x - cupRectangles[2].getXPosition());
        cupRectangles[2].moveVertical((y + cupHeight - grosor) - cupRectangles[2].getYPosition());
    }
    
    public boolean hasLid()
    {
        return ofLid != null;
    }
    
    public int getCupHeight()
    {
        return cupHeight;
    }
    
    public int getCupNumber()
    {
        return cupNumber;
    }
    
    public String getCupColor()
    {
        return colorCup;
    }
    
    public void setCupColor(String color)
    {
        this.colorCup = color;
        for (Rectangle r : cupRectangles){
            r.changeColor(color);
        }
    }
    
    public void makeVisible()
    {
        for (Rectangle r: cupRectangles){
            r.makeVisible();
        }
    }
    
    public void makeInvisible()
    {
        for (Rectangle r: cupRectangles){
            r.makeInvisible();
        }
    }
    
    public void setLid(Lid lid)
    {
        this.ofLid = lid;
    }   
    
    public Lid getLid(){
        return ofLid;
    }
    
}