package jdbcCon;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;

public class JDBC
{
    private Statement stmt = null;
    private ResultSet rs = null;
    private Connection con = null;

    private String url = "jdbc:mysql://localhost:3306/UR5?useSSL=false&serverTimezone=EST";
    private String usr;
    private String pwd;
    
    private String date;
    private String pic;
    private int posX;
    private int posY;
    private int throwNr;
    
    private int imagePTime;
    private int pickUpTime;
    private int throwTime;
    private int totalTime;
    
    private double radius;
    private String colour;
    private String shape;
    private boolean hitTarget;
    private boolean pickTarget;

    /*
     * Kontruktor der opretter con objektet givent et username og et password.
     * Forbindelses URL'en er en konstant værdi sat øverst.
     */
    public JDBC(String usr, String pwd)
    {
        this.usr = usr;
        this.pwd = pwd;
        
        try
        {
            con = DriverManager.getConnection(url, this.usr, this.pwd);

        } 
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }
    
    /*
     * Denne funktion bruges til at slætte en entry i databasen. Den starter med at 
     * slette de tabeller, der ikke indenholder den overordnede primary key og sletter dem først.
     * Herefter bliver det første tabel slettet.
     */
    public void deleteRow(int throwNr)
    {
        String deletePick = "DELETE FROM PickUpObject WHERE throwNr = " + throwNr;
        String deletePos = "DELETE FROM PickUpLoc WHERE throwNr = " + throwNr;
        String deleteTime = "DELETE FROM ExeTime WHERE throwNr = " + throwNr;
        
        String[] queries = {deletePick, deleteTime, deletePos};
        try
        {
            stmt = con.createStatement();
            for(String s : queries)
            {
                stmt.executeUpdate(s);
            }
            
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    
    /*
     * Henter et objekt af objekt value datatypen. Dette array bruges til at opbevare 
     * al den information der skal sættes ind i GUI'ens interface. Der er herunder 3 funktioner
     * mere der gør det samme for de forskellige dataklasser.
     */
    public ObjectVal[] getObjectArray()
    {
        ObjectVal[] vals = null;
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PickUpObject;");

            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            
            rs.beforeFirst();

            vals = new ObjectVal[numOfRows];
            
            for(int i = 0; rs.next(); i++)
            {
                radius = rs.getInt("radius");
                colour = rs.getString("color");
                shape = rs.getString("shape");
                throwNr = rs.getInt("throwNr");
                pic = rs.getString("pic");
                hitTarget = rs.getBoolean("hitTarget");
                pickTarget = rs.getBoolean("pickTarget");
                

                vals[i] = new ObjectVal(throwNr, radius, colour, shape, pic, hitTarget, pickTarget);
            }
                
        } 
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        return vals.clone();
    }
    
    public PickUpVal[] getPickUpArray()
    {
        PickUpVal[] vals = null;
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM PickUpLoc;");
            
            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            
            rs.beforeFirst();

            vals = new PickUpVal[numOfRows];
            
            for(int i = 0; rs.next(); i++)
            {
                date = rs.getString("time");
                posX = rs.getInt("posX");
                posY = rs.getInt("posY");
                throwNr = rs.getInt("throwNr");

                vals[i] = new PickUpVal(throwNr, date, posX, posY);
            }
                
        } 
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        return vals.clone();
    }

    public TimeVal[] getTimeArray()
    {
        TimeVal[] vals = null;
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT * FROM ExeTime;");

            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            
            rs.beforeFirst();

            vals = new TimeVal[numOfRows];
            
            for(int i = 0; rs.next(); i++)
            {
                imagePTime = rs.getInt("openCVTime");
                pickUpTime = rs.getInt("pickUpTime");
                throwTime = rs.getInt("throwTime");
                totalTime = rs.getInt("cycleTime");
                throwNr = rs.getInt("throwNr");

                vals[i] = new TimeVal(throwNr, pickUpTime, imagePTime, throwTime, totalTime);
            }
                
        } 
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        return vals.clone();
    }
    
    public void updateObject(String query)
    {
        try
        {
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
    
    /*
     * Denne metode henter alle værdier fra alle tabeller og ligger dem ind i det
     * objekt der håndterer alle informationer. Herunder er den samme funktion igen uden et parameter
     * og med to paramtere.
     * Funktionen med parameteret håndterer hvis en enkelt værdi bliver valgt
     * på individual tab, hvor funktionen uden parameter håndterer alle værdier.
     * Den tredje og sidste funktion tager to paramtre, så det er muligt at hente værdierne
     * mellem to throwNrs.
     */
    public AllVal[] getThrowNum(int num)
    {
        AllVal[] vals = null;
        PickUpVal p = null;
        TimeVal tv = null;
        ObjectVal o = null;

        String condition = " WHERE throwNr = " + num + ";";
        String select = "SELECT * FROM ";
        String[] tables = {"ExeTime", "PickUpObject", "PickUpLoc"};
        
        try
        {
            stmt = con.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM PickUpLoc");
            
            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            
            vals = new AllVal[numOfRows];
            
            for(int j = 0; j < 1; j++)
            {
                for(String t : tables)
                {
                    rs.beforeFirst();
                    rs = stmt.executeQuery(select + t + condition);

                    if(t.equals(tables[0]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            imagePTime = rs.getInt("openCVTime");
                            pickUpTime = rs.getInt("pickUpTime");
                            throwTime = rs.getInt("throwTime");
                            totalTime = rs.getInt("cycleTime");
                            throwNr = rs.getInt("throwNr");
                        }
                        tv = new TimeVal(throwNr, pickUpTime, imagePTime, throwTime, totalTime);
                    }
                    else if(t.equals(tables[1]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            radius = rs.getInt("radius");
                            colour = rs.getString("color");
                            shape = rs.getString("shape");
                            throwNr = rs.getInt("throwNr");
                            pic = rs.getString("pic");
                            hitTarget = rs.getBoolean("hitTarget");
                            pickTarget = rs.getBoolean("pickTarget");
                        }
                        
                        o = new ObjectVal(throwNr, radius, colour, shape, pic, hitTarget, pickTarget);
                    }
                    else if(t.equals(tables[2]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            date = rs.getString("time");
                            posX = rs.getInt("posX");
                            posY = rs.getInt("posY");
                            throwNr = rs.getInt("throwNr");
                        }
                        
                        p = new PickUpVal(throwNr, date, posX, posY);
                    }
                }
                
                vals[j] = new AllVal(p, tv, o);
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        return vals;
    }
    
    public AllVal[] getThrowNum()
    {
        AllVal[] vals = null;
        ArrayList<PickUpVal> p = new ArrayList<>();
        ArrayList<TimeVal> tv = new ArrayList<>();
        ArrayList<ObjectVal> o = new ArrayList<>();

        String select = "SELECT * FROM ";
        String[] tables = {"ExeTime;", "PickUpObject;", "PickUpLoc;"};

        try
        {
            stmt = con.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM PickUpLoc");
            
            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            
            vals = new AllVal[numOfRows];
            
            for(int j = 0; j < numOfRows; j++)
            {
                for(String t : tables)
                {
                    rs.beforeFirst();
                    rs = stmt.executeQuery(select + t);

                    if(t.equals(tables[0]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            imagePTime = rs.getInt("openCVTime");
                            pickUpTime = rs.getInt("pickUpTime");
                            throwTime = rs.getInt("throwTime");
                            totalTime = rs.getInt("cycleTime");
                            throwNr = rs.getInt("throwNr");
                            
                            tv.add(new TimeVal(throwNr, pickUpTime, imagePTime, throwTime, totalTime));
                        }
                        
                    }
                    else if(t.equals(tables[1]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            radius = rs.getInt("radius");
                            colour = rs.getString("color");
                            shape = rs.getString("shape");
                            throwNr = rs.getInt("throwNr");
                            pic = rs.getString("pic");
                            hitTarget = rs.getBoolean("hitTarget");
                            pickTarget = rs.getBoolean("pickTarget");
                            
                            o.add(new ObjectVal(throwNr, radius, colour, shape, pic, hitTarget, pickTarget));
                        }
                    }
                    else if(t.equals(tables[2]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            date = rs.getString("time");
                            posX = rs.getInt("posX");
                            posY = rs.getInt("posY");
                            throwNr = rs.getInt("throwNr");
                            
                            p.add(new PickUpVal(throwNr, date, posX, posY));
                        }
                        
                        
                    }
                }
            vals[j] = new AllVal(p.get(j), tv.get(j), o.get(j));          
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
            return vals;
    }
    
    public AllVal[] getThrowNum(int num1, int num2)
    {
        AllVal[] vals = null;
        
        String condition = " WHERE throwNr BETWEEN " + num1 + " AND " + num2 + ";";
        String select = "SELECT * FROM ";
        String[] tables = {"ExeTime", "PickUpObject", "PickUpLoc"};
        
        try
        {
            stmt = con.createStatement();
            
            rs = stmt.executeQuery("SELECT * FROM PickUpLoc WHERE throwNr BETWEEN " + num1 + " AND " + num2);
            
            int numOfRows = 0;
            
            while(rs.next())
            {
                numOfRows++;
            }
            rs.beforeFirst();
            
            vals = new AllVal[numOfRows];
            PickUpVal[] p = new PickUpVal[numOfRows];
            TimeVal[] tv = new TimeVal[numOfRows];
            ObjectVal[] o = new ObjectVal[numOfRows];
                
            for(int j = 0; j < numOfRows; j++)
            {
                for(String t : tables)
                {
                    
                    rs = stmt.executeQuery(select + t + condition);

                    if(t.equals(tables[0]))
                    {
                        for(int i = 0; rs.next(); i++)
                        {
                            imagePTime = rs.getInt("openCVTime");
                            pickUpTime = rs.getInt("pickUpTime");
                            throwTime = rs.getInt("throwTime");
                            totalTime = rs.getInt("cycleTime");
                            throwNr = rs.getInt("throwNr");
                            
                            tv[i] = new TimeVal(throwNr, pickUpTime, imagePTime, throwTime, totalTime);
                        }
                    }
                    else if(t.equals(tables[1]))
                    {
                        rs.beforeFirst();
                        
                        for(int i = 0; rs.next(); i++)
                        {
                            radius = rs.getInt("radius");
                            colour = rs.getString("color");
                            shape = rs.getString("shape");
                            throwNr = rs.getInt("throwNr");
                            pic = rs.getString("pic");
                            int hitTargetBuf = rs.getInt("hitTarget");
                            int pickTargetBuf = rs.getInt("pickTarget");
                            
                            if(hitTargetBuf == 1)
                            {
                                hitTarget = true;
                            }
                            else
                            {
                                hitTarget = false;
                            }
                            
                            if(pickTargetBuf == 1)
                            {
                                pickTarget = true;
                            }
                            else
                            {
                                pickTarget = false;
                            }
                            
                            o[i] = new ObjectVal(throwNr, radius, colour, shape, pic, hitTarget, pickTarget);
                        }
                    }
                    else if(t.equals(tables[2]))
                    {
                        rs.beforeFirst();
                        
                        for(int i = 0; rs.next(); i++)
                        {
                            date = rs.getString("time");
                            posX = rs.getInt("posX");
                            posY = rs.getInt("posY");
                            throwNr = rs.getInt("throwNr");
                            
                            p[i] = new PickUpVal(throwNr, date, posX, posY);
                        }
                    }
                }
                
                vals[j] = new AllVal(p[j], tv[j], o[j]);
            }
        }
        catch (SQLException e)
        {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
        
        return vals;
    }
    
    /*
     * Henter værdierne hitTarget og pickTarget så der kan laves statistik på det.
     */
    public ArrayList<Boolean> getTrueFalseHit(int num1, int num2)
    {
        ArrayList<Boolean> bufList = new ArrayList<>();
        
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT hitTarget FROM PickUpObject WHERE throwNr BETWEEN " + num1 + " AND " + num2 +";");
            
            while(rs.next())
            {
                bufList.add(rs.getBoolean("hitTarget"));
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }        
        return bufList;
    }
    public ArrayList<Boolean> getTrueFalsePickUp(int num1, int num2)
    {
        ArrayList<Boolean> bufList = new ArrayList<>();
        
        try
        {
            stmt = con.createStatement();
            rs = stmt.executeQuery("SELECT pickTarget FROM PickUpObject WHERE throwNr BETWEEN " + num1 + " AND " + num2 +";");
            
            while(rs.next())
            {
                bufList.add(rs.getBoolean("pickTarget"));
            }
        } 
        catch (SQLException e)
        {
            e.printStackTrace();
        }        
        return bufList;
    }
}
