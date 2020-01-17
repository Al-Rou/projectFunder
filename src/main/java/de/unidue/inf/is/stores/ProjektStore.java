package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class ProjektStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public ProjektStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }

    public void addProjekt(Projekt projektToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into projekt (kennung,titel,beschreibung,vorgaenger," +
                            "kategorie,status,finanzierungslimit,ersteller) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setString(1, projektToAdd.getKennung().toString());
            preparedStatement.setString(2, projektToAdd.getTitel());
            preparedStatement.setString(3, projektToAdd.getBeschreibung());
            preparedStatement.setString(4, projektToAdd.getVorgaenger().toString());
            preparedStatement.setString(5, projektToAdd.getKategorie().toString());
            preparedStatement.setString(6, projektToAdd.getStatus());
            preparedStatement.setString(7, projektToAdd.getFinanzierungslimit().toString());
            preparedStatement.setString(8, projektToAdd.getErsteller());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public Integer findenLetzteKennung() throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select kennung from projekt order by kennung desc fetch first rows only");
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer result = resultSet.getInt(1);
            return result;

        } catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public List<Projekt> findenOffenProjekte() throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from projekt where status = ?");
            preparedStatement.setString(1, "offen");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Projekt> result = new ArrayList<>();
            while (resultSet.next())
            {
                Projekt newProj = new Projekt(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(7),
                        resultSet.getString(4),
                        resultSet.getString(8),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                result.add(newProj);
            }
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
        finally {
            try {
                connection.close();
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
        }
    }
    public List<Projekt> findenAbgeschlosseneProjekte() throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from projekt where status != ?");
            preparedStatement.setString(1, "offen");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Projekt> result = new ArrayList<>();
            while (resultSet.next())
            {
                Projekt newProj = new Projekt(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(7),
                        resultSet.getString(4),
                        resultSet.getString(8),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                result.add(newProj);
            }
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public List<Projekt> findenErstellteProjekteVon(String email) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from projekt where ersteller = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Projekt> result = new ArrayList<>();
            while (resultSet.next())
            {
                Projekt newProj = new Projekt(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(7),
                        resultSet.getString(4),
                        resultSet.getString(8),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
                result.add(newProj);
            }
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public Projekt findenProjektMitKennung(Integer kennung) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from projekt where kennung = ?");
            preparedStatement.setInt(1, kennung);
            ResultSet resultSet = preparedStatement.executeQuery();
            Projekt result = new Projekt(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getDouble(7),
                        resultSet.getString(4),
                        resultSet.getString(8),
                        resultSet.getInt(5),
                        resultSet.getInt(6));
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public List<Projekt> findenUnterstuezteProjekteVon(String email) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select projekt from spenden where spender = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Integer> result = new ArrayList<>();
            while (resultSet.next())
            {
                result.add(resultSet.getInt(1));
            }
            List<Projekt> resultList = new ArrayList<>();
            for(Integer i : result)
            {
                resultList.add(findenProjektMitKennung(i));
            }
            return resultList;

        } catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public int findenAnzahlErstellteProjekteVon(String email) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select count(*) from projekt where ersteller = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            int result = resultSet.getInt(1);
            return result;

        } catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public int findenAnzahlUnterstuezteProjekteVon(String email) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select count(*) from spenden where spender = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            int result = resultSet.getInt(1);
            return result;

        } catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }

    public List<String> vorgaengerList(String email) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select titel from projekt where ersteller = ?");
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> result = new ArrayList<>();
            while (resultSet.next())
            {
                result.add(resultSet.getString(1));
            }
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }


    public void complete() {
        complete = true;
    }


    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (complete) {
                    connection.commit();
                }
                else {
                    connection.rollback();
                }
            }
            catch (SQLException e) {
                throw new StoreException(e);
            }
            finally {
                try {
                    connection.close();
                }
                catch (SQLException e) {
                    throw new StoreException(e);
                }
            }
        }
    }

}
