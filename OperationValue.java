import java.util.Date;

public class OperationValue
{
    private String idValute;
    private Date date;
    private double value;

    public OperationValue(String idValute, Date date, double value)
    {
        this.idValute = idValute;
        this.date = date;
        this.value = value;
    }

    // GET //

    public String getIdValute()
    {
        return this.idValute;
    }

    public Date getDate()
    {
        return this.date;
    }

    public double getValue()
    {
        return this.value;
    }

    // SET //

    public void setIdValute(String idValute)
    {
        this.idValute = idValute;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public void setValue(double value)
    {
        this.value = value;
    }

}
