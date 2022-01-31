public class Valute
{
    private String id;
    private int numCode;
    private String chrCode;
    private int nominal;
    private String name;

    public Valute(String id, int numCode, String chrCode, int nominal, String name)
    {
        this.id = id;
        this.numCode = numCode;
        this.chrCode = chrCode;
        this.nominal = nominal;
        this.name = name;
    }

    // GET //

    public String getId()
    {
        return this.id;
    }

    public int getNumCode()
    {
        return this.numCode;
    }

    public String getChrCode()
    {
        return this.chrCode;
    }

    public int getNominal()
    {
        return this.nominal;
    }

    public String getName()
    {
        return this.name;
    }

    // SET //

    public void setId(String id)
    {
        this.id = id;
    }

    public void setNumCode(int numCode)
    {
        this.numCode = numCode;
    }

    public void setChrCode(String chrCode)
    {
        this.chrCode = chrCode;
    }

    public void setNominal(int nominal)
    {
        this.nominal = nominal;
    }

    public void setName(String name)
    {
        this.name = name;
    }

}
