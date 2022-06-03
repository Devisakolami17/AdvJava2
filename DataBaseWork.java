package DataBase;

import DataModel.Country;
import DataModel.Covid;
import DataModel.Users;
import DataModel.LoginBean;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DataBaseWork {

    Connection conn;
    PreparedStatement pst;
    ResultSet res;
    ArrayList<Users> userArr;
    ArrayList<LoginBean> loginBeansArr;
    ArrayList<Covid> covidArr;
    ArrayList<Country> countryArr;

    public String LoginCheck(String userName, String pass) {
        String result = "";
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT userType FROM user WHERE email=? and password=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, userName);
            pst.setString(2, pass);
            res = pst.executeQuery();
            while (res.next()) {
                result = res.getString("userType");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void insertCountry(String country) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "INSERT INTO country (Country,IsVisible) VALUES(?,?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, country);
            pst.setInt(2, 1);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int getCountryCount() {
        int result = 0;
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT COUNT(*) FROM country";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                result = res.getInt(0);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void blankCountry() {
        try {
            conn = Db_Connection.dbconnector();
            String query = "Truncate table country";
            pst = conn.prepareStatement(query);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void blankCovidData() {
        try {
            conn = Db_Connection.dbconnector();
            String query = "Truncate table coviddata";
            pst = conn.prepareStatement(query);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public int checkForAutoUpdate() {
        int result = 0;
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT IsAutoUpdate FROM adminControl WHERE ID = 1";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                result = res.getInt("IsAutoUpdate");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public String checkForLastUpdateDate() {
        String result = "";
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT lastUpdated FROM adminControl WHERE ID = 1";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                result = res.getString("lastUpdated");
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public void insertCovidData(String continent, String country, String population, String newCases, String activeCases, String criticalCases, String recoveredCases, String casesPer1mPopulation, String totalCases, String newDeaths, String deathsPer1MPopulation, String totalDeaths, String testsPer1MPopulation, String totalTests) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "INSERT INTO coviddata (Continent, Country, Population, NewCases, ActiveCases, CriticalCases, RecoveredCases, CasesPer1mPopulation, TotalCases, NewDeaths, DeathsPer1MPopulation, TotalDeaths, TestsPer1MPopulation, TotalTests) VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, continent);
            pst.setString(2, country);
            pst.setString(3, population);
            pst.setString(4, newCases);
            pst.setString(5, activeCases);
            pst.setString(6, criticalCases);
            pst.setString(7, recoveredCases);
            pst.setString(8, casesPer1mPopulation);
            pst.setString(9, totalCases);
            pst.setString(10, newDeaths);
            pst.setString(11, deathsPer1MPopulation);
            pst.setString(12, totalDeaths);
            pst.setString(13, testsPer1MPopulation);
            pst.setString(14, totalTests);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateLastUpdatedOn(String date) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "UPDATE adminControl SET lastUpdated=? WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, date);
            pst.setInt(2, 1);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
        public void updateAutoUpdate(int autoUpdate) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "UPDATE adminControl SET isAutoUpdate=? WHERE ID=1";
            pst = conn.prepareStatement(query);
            pst.setInt(1, autoUpdate);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Users> getUsersData() {
        try {
            userArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM user ORDER BY id";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                Users users = new Users(res.getInt("id"), res.getString("username"), res.getString("surname"), res.getString("email"), res.getString("usertype"), res.getString("password"));
                users.setUserID(res.getInt("id"));
                users.setName(res.getString("username"));
                users.setSurName(res.getString("surname"));
                users.setEmailID(res.getString("email"));
                users.setUserType(res.getString("usertype"));
                users.setPassword(res.getString("password"));
                userArr.add(users);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userArr;
    }

    public List<Users> getOnlyUsersData() {
        try {
            userArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM user WHERE usertype='User' ORDER BY id";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                Users users = new Users(res.getInt("id"), res.getString("username"), res.getString("surname"), res.getString("email"), res.getString("usertype"), res.getString("password"));
                users.setUserID(res.getInt("id"));
                users.setName(res.getString("username"));
                users.setSurName(res.getString("surname"));
                users.setEmailID(res.getString("email"));
                users.setUserType(res.getString("usertype"));
                users.setPassword(res.getString("password"));
                userArr.add(users);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return userArr;
    }

    public ArrayList<LoginBean> getUserInfo(String email) {
        try {
            loginBeansArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM user WHERE email=? ";
            pst = conn.prepareStatement(query);
            pst.setString(1, email);
            res = pst.executeQuery();
            while (res.next()) {
                LoginBean loginBean = new LoginBean();
                loginBean.setUserId(res.getInt("id"));
                loginBean.setEmail(res.getString("email"));
                loginBean.setUsername(res.getString("username"));
                loginBean.setSurname(res.getString("surname"));
                loginBean.setPassword(res.getString("password"));
                loginBean.setUsertype(res.getString("usertype"));
                loginBeansArr.add(loginBean);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return loginBeansArr;
    }

    public void updateUserInfo(String name, String surname, String pass, String email) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "UPDATE user SET username=?,password=?,surname=? WHERE email=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, pass);
            pst.setString(3, surname);
            pst.setString(4, email);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public void updateCovidInfo(int ID, String continent, String country, String newCases, String newDeaths, String totalCases, String recoveredCases, String criticalCases, String totalDeaths, String totalTests) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "UPDATE coviddata SET Continent=?,Country=?,NewCases=?, NewDeaths=?, TotalCases=?, RecoveredCases=?, CriticalCases=?, TotalDeaths=?,TotalTests=? WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setString(1, continent);
            pst.setString(2, country);
            pst.setString(3, newCases);
            pst.setString(4, newDeaths);
            pst.setString(5, totalCases);
            pst.setString(6, recoveredCases);
            pst.setString(7, criticalCases);
            pst.setString(8, totalDeaths);
            pst.setString(9, totalTests);
            pst.setInt(10, ID);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
    
    public void updateCountryInfo(int ID, int isActive) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "UPDATE country SET isVisible=? WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, isActive);
            pst.setInt(2, ID);
            pst.executeUpdate();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public List<Covid> covidData() {
        try {
            covidArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT coviddata.* FROM coviddata INNER JOIN country ON country.Country = coviddata.Country WHERE country.IsVisible = 1 ORDER BY coviddata.Country";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                Covid cvd = new Covid(res.getInt("ID"), res.getString("Continent"), res.getString("Country"), res.getString("Population"), res.getString("NewCases"), res.getString("ActiveCases"), res.getString("CriticalCases"), res.getString("RecoveredCases"), res.getString("CasesPer1mPopulation"), res.getString("TotalCases"), res.getString("NewDeaths"), res.getString("DeathsPer1MPopulation"), res.getString("TotalDeaths"), res.getString("TestsPer1MPopulation"), res.getString("TotalTests"));

                cvd.setID(res.getInt("ID"));

                cvd.setContinent(res.getString("Continent"));
                cvd.setCountry(res.getString("Country"));
                cvd.setPopulation(res.getString("Population"));

                cvd.setNewCases(res.getString("NewCases"));
                cvd.setActiveCases(res.getString("ActiveCases"));
                cvd.setCriticalCases(res.getString("CriticalCases"));
                cvd.setRecoveredCases(res.getString("RecoveredCases"));
                cvd.setPop1mCases(res.getString("CasesPer1mPopulation"));
                cvd.setTotalCases(res.getString("TotalCases"));

                cvd.setNewDeaths(res.getString("NewDeaths"));
                cvd.setPop1mDeaths(res.getString("DeathsPer1MPopulation"));
                cvd.setTotalDeaths(res.getString("TotalDeaths"));

                cvd.setPop1mTests(res.getString("TestsPer1MPopulation"));
                cvd.setTotalTests(res.getString("TotalTests"));

                covidArr.add(cvd);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return covidArr;
    }

    public List<Country> countryData() {
        try {
            countryArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM country ORDER BY Country";
            pst = conn.prepareStatement(query);
            res = pst.executeQuery();
            while (res.next()) {
                Country cntry = new Country(res.getInt("ID"), res.getString("Country"), res.getInt("IsVisible"));
                cntry.setID(res.getInt("ID"));
                cntry.setIsActive(res.getInt("IsVisible"));
                cntry.setCountry(res.getString("Country"));

                countryArr.add(cntry);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return countryArr;
    }

    public Users readOneUser(int id) {
        Users user = null;
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM user WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            res = pst.executeQuery();
            while (res.next()) {
                user = new Users(res.getInt("id"), res.getString("username"), res.getString("surname"), res.getString("email"), res.getString("usertype"), res.getString("password"));
                user.setUserID(res.getInt("id"));
                user.setName(res.getString("username"));
                user.setSurName(res.getString("surname"));
                user.setEmailID(res.getString("email"));
                user.setUserType(res.getString("usertype"));
                user.setPassword(res.getString("password"));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return user;
    }

    public void removeUser(int userId) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "DELETE FROM user WHERE id=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public Covid readOneCountry(int id) {
        Covid cvd = null;
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM coviddata WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            res = pst.executeQuery();
            while (res.next()) {
                cvd = new Covid(res.getInt("ID"), res.getString("Continent"), res.getString("Country"), res.getString("Population"), res.getString("NewCases"), res.getString("ActiveCases"), res.getString("CriticalCases"), res.getString("RecoveredCases"), res.getString("CasesPer1mPopulation"), res.getString("TotalCases"), res.getString("NewDeaths"), res.getString("DeathsPer1MPopulation"), res.getString("TotalDeaths"), res.getString("TestsPer1MPopulation"), res.getString("TotalTests"));

                cvd.setID(res.getInt("ID"));

                cvd.setContinent(res.getString("Continent"));
                cvd.setCountry(res.getString("Country"));
                cvd.setPopulation(res.getString("Population"));

                cvd.setNewCases(res.getString("NewCases"));
                cvd.setActiveCases(res.getString("ActiveCases"));
                cvd.setCriticalCases(res.getString("CriticalCases"));
                cvd.setRecoveredCases(res.getString("RecoveredCases"));
                cvd.setPop1mCases(res.getString("CasesPer1mPopulation"));
                cvd.setTotalCases(res.getString("TotalCases"));

                cvd.setNewDeaths(res.getString("NewDeaths"));
                cvd.setPop1mDeaths(res.getString("DeathsPer1MPopulation"));
                cvd.setTotalDeaths(res.getString("TotalDeaths"));

                cvd.setPop1mTests(res.getString("TestsPer1MPopulation"));
                cvd.setTotalTests(res.getString("TotalTests"));
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cvd;
    }

    public Country readOneCountryData(int id) {
        Country cntry = null;
        try {
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM country WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, id);
            res = pst.executeQuery();
            while (res.next()) {
                cntry = new Country(res.getInt("ID"), res.getString("Country"), res.getInt("IsVisible"));
                cntry.setID(res.getInt("ID"));
                cntry.setIsActive(res.getInt("IsVisible"));
                cntry.setCountry(res.getString("Country"));

            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return cntry;
    }

    public void removeCovidData(int userId) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "DELETE FROM coviddata WHERE ID=?";
            pst = conn.prepareStatement(query);
            pst.setInt(1, userId);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    public ArrayList<LoginBean> getUsers() {
        try {
            loginBeansArr = new ArrayList<>();
            conn = Db_Connection.dbconnector();
            String query = "SELECT * FROM user WHERE usertype=? ";
            pst = conn.prepareStatement(query);
            pst.setString(1, "Member");
            res = pst.executeQuery();
            while (res.next()) {
                LoginBean loginBean = new LoginBean();
                loginBean.setUserId(res.getInt("id"));
                loginBean.setEmail(res.getString("email"));
                loginBean.setUsername(res.getString("username"));
                loginBean.setSurname(res.getString("surname"));
                loginBean.setPassword(res.getString("password"));
                loginBean.setUsertype(res.getString("usertype"));
                loginBeansArr.add(loginBean);
            }
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
        return loginBeansArr;
    }

    public void createNewUser(String name, String surname, String email, String usertype, String password) {
        try {
            conn = Db_Connection.dbconnector();
            String query = "INSERT INTO user (username,password,surname,email,usertype) VALUES(?,?,?,?,?)";
            pst = conn.prepareStatement(query);
            pst.setString(1, name);
            pst.setString(2, password);
            pst.setString(3, surname);
            pst.setString(4, email);
            pst.setString(5, usertype);
            pst.execute();
            conn.close();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }
}
