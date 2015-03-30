package huiswerknakijken.hu.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import huiswerknakijken.hu.domein.Student;
import huiswerknakijken.hu.util.OracleConnectionPool;

public class StudentDAO implements DAOInterface<Student> {
	public List<Student> retrieveAll(int layerLevel) {
		ResultSet rs = null;
		ArrayList<Student> Students = null;
		Connection connection = OracleConnectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students");
			rs = statement.executeQuery();
			Students = resultSetExtractor(rs, layerLevel, connection);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Students;
	}
	
	public List<Student> retrieveAllMatching(String Studentname, String email, String firstName, String lastName, int role, int layerLevel) {
		ResultSet rs = null;
		ArrayList<Student> Students = null;
		Connection connection = OracleConnectionPool.getConnection();
		try {
			PreparedStatement statement = null;
			if(role != 0){
				statement = connection.prepareStatement("SELECT * FROM Students WHERE lower(Studentname) LIKE lower(?) AND lower(email) LIKE lower(?) AND lower(first_name) LIKE lower(?) AND lower(last_name) LIKE lower(?) AND role_id = ?");
				statement.setInt(5, role);
			}
			else{
				statement = connection.prepareStatement("SELECT * FROM Students WHERE lower(Studentname) LIKE lower(?) AND lower(email) LIKE lower(?) AND lower(first_name) LIKE lower(?) AND lower(last_name) LIKE lower(?)");
			}
			statement.setString(1, "%"+Studentname+"%");
			statement.setString(2, "%"+email+"%");
			statement.setString(3, "%"+firstName+"%");
			statement.setString(4, "%"+lastName+"%");
			
			rs = statement.executeQuery();
			Students = resultSetExtractor(rs, layerLevel, connection);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Students;
	}

	@Override
	public boolean delete(Student u) {
		return false;
	}

	@Override
	public boolean add(Student u) {
		boolean b = false;
		Connection connection = OracleConnectionPool.getConnection();
		try {
			connection.setAutoCommit(false);
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {

			String sql = "INSERT INTO StudentS(Studentname, password, first_name, last_name, phone, role_id, email, credits, status) VALUES (?,?,?,?,?,?,?,?,?)";
			String generatedColumns[] = { "Student_ID" };
			PreparedStatement statement = connection.prepareStatement(sql, generatedColumns);
			statement.setString(1, u.getStudentname());
			statement.setString(2, u.getPassword());
			statement.setString(3, u.getFirstName());
			statement.setString(4, u.getLastName());
			statement.setString(5, u.getPhonenumber());
			statement.setInt(6, u.getRole().getIndex()); // role id
			statement.setString(7, u.getEmail());
			statement.setDouble(8,u.getCredits());
			statement.setInt(9,u.getStatus().index());
			statement.executeUpdate();
			int StudentID = 0;

			ResultSet rsid = statement.getGeneratedKeys();
			if (rsid != null && rsid.next()) {
				StudentID = rsid.getInt(1);
				u.setStudentid(StudentID);
			}
			statement.close();


			b = true;
			connection.commit();
			connection.close();
		} catch (SQLIntegrityConstraintViolationException e) {
			e.printStackTrace();
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Unique constraint error");
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public boolean update(Student u) {
		boolean b = false;
		Connection connection = OracleConnectionPool.getConnection();
		//Service.getService().getStudents().put(u.getStudentid(), u);
		try {
			connection.setAutoCommit(false);
			PreparedStatement statement = connection.prepareStatement("UPDATE Students SET Studentname=?, password=?, first_name=?, last_name=?, phone=?,role_id=?,email=?,credits=?, status=? WHERE Student_id=?");
			statement.setString(1, u.getStudentname());
			statement.setString(2, u.getPassword());
			statement.setString(3, u.getFirstName());
			statement.setString(4, u.getLastName());
			statement.setString(5, u.getPhonenumber());
			statement.setInt(6, u.getRole().getIndex()); // role id
			statement.setString(7, u.getEmail());
			statement.setDouble(8, u.getCredits());
			statement.setInt(9, u.getStatus().index());
			statement.setInt(10, u.getStudentid());
			statement.executeUpdate();
			statement.close();


			connection.commit();
			connection.close();
			b = true;
		} catch (SQLException e) {
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return b;
	}

	public Student retrieve(String s, int layerLevel) {
		int uid = Integer.parseInt(s);
		return retrieve(uid, layerLevel);
	}

	public Student retrieve(int Studentid, int layerLevel) {
		Student retrievedStudent = new Student();
		Connection connection = OracleConnectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE Student_id = ?");
			statement.setInt(1, Studentid);
			ResultSet rs = statement.executeQuery();
			ArrayList<Student> Student = resultSetExtractor(rs, layerLevel, connection);
			retrievedStudent = Student.get(0);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retrievedStudent;

	}
	
	public Student retrieve(int Studentid, int layerLevel, Connection connection) {
		Student retrievedStudent = new Student();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE Student_id=?");
			statement.setInt(1, Studentid);
			ResultSet rs = statement.executeQuery();
			ArrayList<Student> Student = resultSetExtractor(rs, layerLevel, connection);
			retrievedStudent = Student.get(0);
			statement.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retrievedStudent;

	}

	public Student retrieveByEmail(String s, int layerLevel) {
		Student retrievedStudent = null;
		Connection connection = OracleConnectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE email=?");
			statement.setString(1, s);
			ResultSet rs = statement.executeQuery();
			ArrayList<Student> eU = resultSetExtractor(rs, layerLevel, connection);
			if (eU.size() > 0)
				retrievedStudent = eU.get(0);
			statement.close();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return retrievedStudent;
	}

	public Student retrieveByStudentname(String s, int layerLevel) {
		Student retrievedStudent = null;
		Connection connection = OracleConnectionPool.getConnection();
		try {
			PreparedStatement statement = connection.prepareStatement("SELECT * FROM Students WHERE lower(Studentname)=?");
			statement.setString(1, s.toLowerCase());
			ResultSet rs = statement.executeQuery();
			ArrayList<Student> eU = resultSetExtractor(rs, layerLevel, connection);
			if (eU.size() > 0)
				retrievedStudent = eU.get(0);
			statement.close();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return retrievedStudent;
	}

	private ArrayList<Student> resultSetExtractor(ResultSet rs, int layerLevel, Connection connection) {
		ArrayList<Student> extractedStudents = new ArrayList<Student>();
		
		try {
			while (rs.next()) {
				boolean notInCache = true;
				int StudentID = rs.getInt("Student_id");
				/*if (cacheStudents.containsKey(StudentID)) {
					Student cacheStudent = cacheStudents.get(StudentID);
					if (cacheStudent.getLayerLevel() >= layerLevel) {
						extractedStudents.add(cacheStudents.get(StudentID));
						notInCache = false;
					}
				}*/
				if (notInCache) {
					Student u = new Student();
					u.setStudentid(StudentID);
					u.setFirstName(rs.getString("first_name"));
					u.setLastName(rs.getString("last_name"));
					u.setEmail(rs.getString("email"));
					u.setPassword(rs.getString("password"));
					u.setPhonenumber(rs.getString("phone"));
					u.setLayerLevel(layerLevel);
					u.setCredits(rs.getDouble("credits"));
					int role = rs.getInt("role_id");
					for (StudentRole us : StudentRole.values()) {
						if (us.getIndex() == role) {
							u.setRole(us);
						}
					}
					int status = rs.getInt("status");

					for (StudentStatus us : StudentStatus.values()) {
						if (us.index() == status) {
							u.setStatus(us);
						}
					}
					
					u.setStudentname(rs.getString("Studentname"));

					if (layerLevel > 1) {
						AddressDAO adao = new AddressDAO();
						String sqlKoppel;
						PreparedStatement statementKoppel;
						sqlKoppel = "SELECT * FROM Student_ADDRESSES WHERE Student_id = ? AND date_to IS NULL";
						statementKoppel = connection.prepareStatement(sqlKoppel);
						statementKoppel.setInt(1, u.getStudentid());
						ResultSet addressStudent = statementKoppel.executeQuery();
						while (addressStudent.next()) {
							int addressInt = addressStudent.getInt("address_id");
							int addressType = addressStudent.getInt("address_type_code");
							Address a = adao.retrieveByID(addressInt);
							for (AddressType at : AddressType.values()) {
								if (at.index() == addressType) {
									a.setAddressType(at);
									break;
								}
							}
							u.getAddresses().add(a);
						}
						statementKoppel.close();
					}

					if (layerLevel > 2) {
						AuctionDAO audao = new AuctionDAO();
						u.setAuctions((ArrayList<Auction>) audao.retrieveAllBySeller(u,AuctionStatus.ALL,1));
					}

					cacheStudents.put(u.getStudentid(), u);
					extractedStudents.add(u);
				}
			}
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return extractedStudents;
	}

	@Override
	public List<Student> retrieveAll() {
		return retrieveAll(4);
	}

	@Override
	public Student retrieve(String s) {
		return retrieve(s, 10);
	}
}
