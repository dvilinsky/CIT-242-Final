public class DAOFactory
{
	// this method maps the ProductDAO interface
	// to the appropriate data storage mechanism
	public static ComputerDAO getComputerDAO()
	{
		ComputerDAO cDAO = new ComputerXMLFile();
		return cDAO;
	}
}