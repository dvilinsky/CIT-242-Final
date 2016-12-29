public interface ComputerWriter {
	boolean addComputer(Computer c);
	boolean updateComputer(Computer c, String oldComputerName);
	boolean deleteComputer(Computer c);
}