import java.util.*;
import java.io.*;
import javax.xml.stream.*;

public class ComputerXMLFile implements ComputerDAO {
	private String computersFilename = "computers.xml";
	private File computersFile = null;
	private ArrayList<Computer> computers = null;
	
	public ComputerXMLFile() {
		computersFile = new File(computersFilename);
		computers = init();
	}
	
	private void checkFile() throws IOException {
		if (!computersFile.exists()) {
			computersFile.createNewFile();
		}
	}
	
	private boolean saveComputers(ArrayList<Computer> comps) {
		XMLOutputFactory out = XMLOutputFactory.newInstance();
		
		try {
			this.checkFile();
			XMLStreamWriter writer = out.createXMLStreamWriter(new FileWriter(computersFilename));
			writer.writeStartDocument("1.0");
			writer.writeStartElement("Computers");
			
			for (Computer c : comps) {
				writer.writeStartElement("Computer");
				writer.writeAttribute("Description", c.getName());
				
				writer.writeStartElement("Name");
				writer.writeCharacters(c.getName());
				writer.writeEndElement();
				
				writer.writeStartElement("Cost");
				writer.writeCharacters(Double.toString(c.getCost()));
				writer.writeEndElement();
				
				writer.writeStartElement("HD-Size");
				writer.writeCharacters(Integer.toString(c.getHDSize()));
				writer.writeEndElement();
				
				writer.writeStartElement("RAM");
				writer.writeCharacters(Integer.toString(c.getRAM()));
				writer.writeEndElement();
				
				writer.writeStartElement("Screen-Size");
				writer.writeCharacters(Double.toString(c.getScreenSize()));
				writer.writeEndElement();
				
				writer.writeStartElement("Screen-Resolution");
				writer.writeCharacters(Integer.toString(c.getScreenRes()));
				writer.writeEndElement();
				
				writer.writeStartElement("Touch-Screen");
				writer.writeCharacters(Integer.toString(c.hasTouchScreen()));
				writer.writeEndElement();
				
				writer.writeEndElement();
			}
			writer.writeEndElement();
			writer.flush();
			writer.close();
		} catch (IOException e) {
            e.printStackTrace();
            return false;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}
	
	private ArrayList<Computer> init() {
		ArrayList<Computer> computers = new ArrayList<Computer>();
		Computer c = null;
		
		XMLInputFactory input = XMLInputFactory.newInstance();
		try {
			this.checkFile();
			XMLStreamReader reader = input.createXMLStreamReader(new FileReader(computersFilename));
			
			while (reader.hasNext()) {
				int event = reader.getEventType();
				switch (event) {
					case XMLStreamConstants.START_ELEMENT:
						String element = reader.getLocalName();
						if (element.equals("Computer")) {
							c = new Computer();
							c.setName(reader.getAttributeValue(0));
						}
						if (element.equals("Name")) {
							c.setName(reader.getElementText());
						}
						if (element.equals("Cost")) {
							double cost = Double.parseDouble(reader.getElementText());
							c.setCost(cost);
						}
						if (element.equals("HD-Size")) {
							int size = Integer.parseInt(reader.getElementText());
							c.setHDSize(size);
						}
						if (element.equals("RAM")) {
							int ram = Integer.parseInt(reader.getElementText());
							c.setRAM(ram);
						}
						if (element.equals("Screen-Size")) {
							double size = Double.parseDouble(reader.getElementText());
							c.setScreenSize(size);
						}
						if (element.equals("Screen-Resolution")) {
							int res = Integer.parseInt(reader.getElementText());
							c.setScreenRes(res);
						} 
						if (element.equals("Touch-Screen")) {
							int ts = Integer.parseInt(reader.getElementText());
							c.setHasTouchScreen(ts);
						}
						break;
					case XMLStreamConstants.END_ELEMENT:
						element = reader.getLocalName();
						if (element.equals("Computer")) {
							computers.add(c);
						}
						break;
					default:
						break;
				}
				reader.next();
			}
		} catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (XMLStreamException e) {
            e.printStackTrace();
            return null;
        }
		return computers;
	}
	
	public ArrayList<Computer> getComputers() {
		return this.computers;
	}
	
	//todo: sort computer list so we can binary search. But sorting is nlogn, this method is only n.
	public Computer getComputer(String name) {
		for (Computer c : this.computers) {
			if (c.getName().equals(name)) {
				return c;
			}
		}
		return null;
	}
	
	public boolean addComputer(Computer c) {
		this.computers.add(c);
		return this.saveComputers(this.computers);
	}
	
	//should I make this O(logn) by sorting? But sorting itself is nlogn, and I'd have to resort every time 
	//I addded. 
	public boolean deleteComputer(Computer c) {
		this.computers.remove(c);
		return this.saveComputers(this.computers);
	}
	
	//Made this a little faster by taking advantage of ArrayList's set(i, E) method.
	//I've also changed the ComputerReader interface to make this method a little more useful
	public boolean updateComputer(Computer c, String oldCompName) {
		Computer old = this.getComputer(oldCompName);
		int index = this.computers.indexOf(old);
		this.computers.set(index, c);
		return this.saveComputers(this.computers);
	}
}