package Utilities.LogisticLoader;

import Model.Order.CargoSpecs;
import Model.Transport.TransportSpecs;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class XmlDataLoader implements DataLoader {
    private String path;

    public XmlDataLoader(String path) {
        this.path = path;
    }

    @Override
    public Map<String, CargoSpecs> loadCargoSpecs() {
        Map<String, CargoSpecs> cargoData = new HashMap<>();
        try {
            Document doc = getDocument();
            NodeList nList = doc.getElementsByTagName("CargoItems");
            if (nList.getLength() > 0) {
                Node cargoItemsNode = nList.item(0);
                NodeList items = ((Element) cargoItemsNode).getElementsByTagName("Item");

                for (int i = 0; i < items.getLength(); i++) {
                    Element item = (Element) items.item(i);
                    String name = getTagValue("Name", item);
                    double mass = Double.parseDouble(getTagValue("Mass", item));
                    double costPerKg = Double.parseDouble(getTagValue("CostPerKg", item));

                    cargoData.put(name, new CargoSpecs(name, mass, costPerKg));
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения XML (cargo): " + e.getMessage());
        }
        return cargoData;
    }

    @Override
    public Map<String, TransportSpecs> loadTransportSpecs() {
        Map<String, TransportSpecs> transportData = new HashMap<>();
        try {
            Document doc = getDocument();
            NodeList nList = doc.getElementsByTagName("TransportItems");
            if (nList.getLength() > 0) {
                Node transportItemsNode = nList.item(0);
                NodeList items = ((Element) transportItemsNode).getElementsByTagName("Item");

                for (int i = 0; i < items.getLength(); i++) {
                    Element item = (Element) items.item(i);
                    String name = getTagValue("Name", item);
                    String type = getTagValue("Type", item);
                    double consumption = Double.parseDouble(getTagValue("Consumption", item));
                    double speed = Double.parseDouble(getTagValue("Speed", item));

                    transportData.put(name, new TransportSpecs(name, type, consumption, speed));
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка чтения XML (transport): " + e.getMessage());
        }
        return transportData;
    }

    private Document getDocument() throws Exception {
        File inputFile = new File(path);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputFile);
        doc.getDocumentElement().normalize();
        return doc;
    }

    private String getTagValue(String tag, Element element) {
        NodeList nodeList = element.getElementsByTagName(tag).item(0).getChildNodes();
        Node node = nodeList.item(0);
        return node.getNodeValue();
    }

    public String getPath() { return path; }
    public void setPath(String path) { this.path = path; }
}