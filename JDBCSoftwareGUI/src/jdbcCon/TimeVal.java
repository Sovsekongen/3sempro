package jdbcCon;

import java.util.logging.Logger;

/*
 * Opbevarer data fra den sidste tabel, der håndterer de forskellige tider.
 */
public class TimeVal 
{

    private int pickUpTime;
    private int imagePTime;
    private int throwTime;
    private int totalTime;
    private int throwNr;

    public TimeVal(int throwNr, int pickUpTime, int imagePTime, int throwTime, int totalTime)
    {
        this.throwNr = throwNr;
        this.pickUpTime = pickUpTime;
        this.imagePTime = imagePTime;
        this.throwTime = throwTime;
        this.totalTime = totalTime;
    }

    public int getPickUpTime()
    {
        return pickUpTime;
    }

    public void setPickUpTime(int pickUpTime)
    {
        this.pickUpTime = pickUpTime;
    }

    public int getImagePTime()
    {
        return imagePTime;
    }

    public void setImagePTime(int imagePTime)
    {
        this.imagePTime = imagePTime;
    }

    public int getThrowTime()
    {
        return throwTime;
    }

    public void setThrowTime(int throwTime)
    {
        this.throwTime = throwTime;
    }

    public int getTotalTime()
    {
        return totalTime;
    }

    public void setTotalTime(int totalTime)
    {
        this.totalTime = totalTime;
    }

    public int getThrowNr()
    {
        return throwNr;
    }

    public void setThrowNr(int throwNr)
    {
        this.throwNr = throwNr;
    }
    
    
}
