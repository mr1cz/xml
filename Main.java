import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Main
{
    private TreeMap<String, Valute> map = new TreeMap<String, Valute>();
    private ArrayList<OperationValue> arr = new ArrayList<OperationValue>();

    public static void main (String[] args)
    {
        new Main();
    }

    public Main ()
    {
        this.fillArrays();
        this.average();
    }

    private void fillArrays()
    {
        String id = null;
        int numCode = 0;
        String charCode = null;
        int nominal = 0;
        String name = null;
        Date date;
        double value = 0.0;

        try
        {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("124.xml");

            Node root = document.getDocumentElement();

            NodeList oper1 = ((Element) root).getElementsByTagName("ValCurs");

            for (int i=0; i<oper1.getLength(); i++)
            {
                if(oper1.item(i).getNodeType() != Node.TEXT_NODE)
                {
                    date = new SimpleDateFormat("dd.MM.yyyy").parse(oper1.item(i).getAttributes().getNamedItem("Date").getNodeValue());
                    NodeList oper2 = oper1.item(i).getChildNodes();

                    for (int j=0; j<oper2.getLength(); j++)
                    {
                        if(oper2.item(j).getNodeType() != Node.TEXT_NODE)
                        {
                            id = oper2.item(j).getAttributes().getNamedItem("ID").getNodeValue();
                            NodeList oper3 = oper2.item(j).getChildNodes();

                            for (int n=0; n<oper3.getLength(); n++)
                            {
                                if(oper3.item(n).getNodeType() != Node.TEXT_NODE)
                                {
                                    if (oper3.item(n).getNodeName().equalsIgnoreCase("numCode")){ numCode = Integer.parseInt(oper3.item(n).getChildNodes().item(0).getTextContent()); continue; }
                                    if (oper3.item(n).getNodeName().equalsIgnoreCase("charCode")) { charCode = oper3.item(n).getChildNodes().item(0).getTextContent(); continue; }
                                    if (oper3.item(n).getNodeName().equalsIgnoreCase("nominal")){ nominal = Integer.parseInt(oper3.item(n).getChildNodes().item(0).getTextContent()); continue; }
                                    if (oper3.item(n).getNodeName().equalsIgnoreCase("name")) { name = oper3.item(n).getChildNodes().item(0).getTextContent(); continue; }
                                    if (oper3.item(n).getNodeName().equalsIgnoreCase("value")) { value = Double.parseDouble(oper3.item(n).getChildNodes().item(0).getTextContent().replace(',', '.')); }
                                }
                            }
                            this.map.put(id, new Valute(id, numCode, charCode, nominal, name));
                            this.arr.add(new OperationValue(id, date, value));
                        }
                    }
                }
            }
        }
        catch (ParserConfigurationException ex)
        {
            ex.printStackTrace(System.out);
        }
        catch (SAXException ex)
        {
            ex.printStackTrace(System.out);
        }
        catch (IOException ex)
        {
            ex.printStackTrace(System.out);
        }
        catch (ParseException e)
        {
            e.printStackTrace();
        }

    }

    private void average()
    {
        Date min = null;
        Date max = null;
        long now90 = (new Date()).getTime()-7776000000l;

        double valMin = 0.0;
        double valMax = 0.0;
        double val = 0.0;
        int count = 0;

        Set<Map.Entry<String ,Valute>> set = this.map.entrySet();

        for (Map.Entry<String ,Valute> m : set)
        {
            for (OperationValue o : this.arr)
            {
                if (m.getKey().equals(o.getIdValute()) && (now90 < o.getDate().getTime()))
                {
                    if (valMin == 0.0) { valMin = o.getValue(); }

                    val += o.getValue();
                    count++;

                    if (valMax < o.getValue()) { valMax = o.getValue(); max = o.getDate(); }
                    if (valMin > o.getValue()) { valMin = o.getValue(); min = o.getDate(); }
                }
            }

            val = val / count;

            if (min!=null && max!=null)
            {
                System.out.println(
                        "[" + m.getValue().getChrCode() + "] \t " +
                        "min: " + valMin + " (" + new SimpleDateFormat("dd.MM.yyyy").format(min) + ") \t "+
                        "max: " + valMax + " (" + new SimpleDateFormat("dd.MM.yyyy").format(max)+ ") \t "+
                        "ave: " + new DecimalFormat("#0.0000").format(val) );
            }

            valMax = 0.0;
            valMin= 0.0;
            val= 0.0;
            count = 0;
        }
    }

}
