package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import de.unidue.inf.is.domain.Projekt;
import de.unidue.inf.is.domain.Spenden;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class ProjektStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public ProjektStore() throws StoreException {
        /*try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
            setComplete();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }*/
    }
    public void setComplete()
    {
        complete = false;
    }
    public Connection makeConn() throws StoreException
    {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
            setComplete();
            return connection;
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public void addProjekt(Projekt projektToAdd) throws StoreException
    {
        makeConn();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("insert into dbp032.projekt (kennung,titel,beschreibung,vorgaenger,kategorie,status,finanzierungslimit,ersteller) values (?,?,?,?,?,?,?,?)");
            preparedStatement.setInt(1, projektToAdd.getKennung());
            preparedStatement.setString(2, projektToAdd.getTitel());
            preparedStatement.setString(3, projektToAdd.getBeschreibung());
            preparedStatement.setInt(4, projektToAdd.getVorgaenger());
            preparedStatement.setInt(5, projektToAdd.getKategorie());
            preparedStatement.setString(6, projektToAdd.getStatus());
            preparedStatement.setDouble(7, projektToAdd.getFinanzierungslimit());
            preparedStatement.setString(8, projektToAdd.getErsteller());
            preparedStatement.executeUpdate();

        preparedStatement.close();
        complete();
        close();
    } catch (SQLException | IOException e) {
        throw new StoreException(e);
    }
    }
    public void editProjekt(Projekt projektToAdd) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("update projekt set titel = ?, beschreibung = ?, vorgaenger = ?, kategorie = ?, status = ?, finanzierungslimit = ?, ersteller = ? where kennung = ?");

            preparedStatement.setString(1, projektToAdd.getTitel());
            preparedStatement.setString(2, projektToAdd.getBeschreibung());
            preparedStatement.setInt(3, projektToAdd.getVorgaenger());
            preparedStatement.setInt(4, projektToAdd.getKategorie());
            preparedStatement.setString(5, projektToAdd.getStatus());
            preparedStatement.setDouble(6, projektToAdd.getFinanzierungslimit());
            preparedStatement.setString(7, projektToAdd.getErsteller());
            preparedStatement.setInt(8, projektToAdd.getKennung());
            preparedStatement.executeUpdate();

            preparedStatement.close();
            complete();
            close();

        } catch (SQLException | IOException e) {
            throw new StoreException(e);
        }
    }
    public Integer findenLetzteKennung() throws StoreException
    {
        makeConn();
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select kennung from dbp032.projekt order by kennung desc fetch first rows only");
            ResultSet resultSet = preparedStatement.executeQuery();

            Integer result = null;
            if (resultSet.next()){
            result = resultSet.getInt(1);
            }
        resultSet.close();
        preparedStatement.close();
        complete();
        close();
        return result;
    } catch (SQLException | IOException e) {
        throw new StoreException(e);
    }
    }
    public List<Projekt> findenOffeneProjekte() throws StoreException
    {
        //if(connection == null)
        //{
            makeConn();
        //}
            try {
                PreparedStatement preparedStatement = connection
                        .prepareStatement("select * from dbp032.projekt where status = ?");

                preparedStatement.setString(1, "offen");
                ResultSet resultSet = preparedStatement.executeQuery();
                List<Projekt> result = new ArrayList<>();
                while (resultSet.next()) {
                    result.add(new Projekt(resultSet.getInt("kennung"),
                            resultSet.getString("titel"),
                            resultSet.getString("beschreibung"),
                            resultSet.getDouble("finanzierungslimit"),
                            resultSet.getString("status"),
                            resultSet.getString("ersteller"),
                            resultSet.getInt("vorgaenger"),
                            resultSet.getInt("kategorie")));
                }
                if (result.isEmpty())
                {
                    result.add(new Projekt(0,
                            "Es gibt kein offenes Projekt!",
                            "",
                            0.00,
                            "offen",
                            "",
                            null,
                            1));
                }
                resultSet.close();
                preparedStatement.close();
                complete();
                close();
                return result;
            } catch (SQLException | IOException e) {
                throw new StoreException(e);
            }
    }

    public List<Projekt> findenAbgeschlosseneProjekte() throws StoreException
    {
        //if(connection == null)
        //{
            makeConn();
        //}
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from dbp032.projekt where status != ?");
            preparedStatement.setString(1, "offen");
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Projekt> result = new ArrayList<>();
            while (resultSet.next()) {
                result.add(new Projekt(resultSet.getInt("kennung"),
                        resultSet.getString("titel"),
                        resultSet.getString("beschreibung"),
                        resultSet.getDouble("finanzierungslimit"),
                        resultSet.getString("status"),
                        resultSet.getString("ersteller"),
                        resultSet.getInt("vorgaenger"),
                        resultSet.getInt("kategorie")));
            }
            if (result.isEmpty())
            {
                result.add(new Projekt(0,
                        "Es gibt kein abgeschlossenes Projekt!",
                        "",
                        0.00,
                        "offen",
                        "",
                        null,
                        1));
            }
            resultSet.close();
            preparedStatement.close();
            complete();
            close();
            return result;
        } catch (SQLException | IOException e)
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
    public Integer findenKennungVon(String titel) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select kennung from projekt where titel = ?");
            preparedStatement.setString(1, titel);
            ResultSet resultSet = preparedStatement.executeQuery();
            Integer result = resultSet.getInt(1);
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
        makeConn();
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select titel from dbp032.projekt where ersteller = ?");
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
    public String findenBeschreibungVon(String titel) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select beschreibung from projekt where titel = ?");
            preparedStatement.setString(1, titel);
            ResultSet resultSet = preparedStatement.executeQuery();
            String result = resultSet.getString(1);
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public Double findenFinanzierungsLimitVon(String titel) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select finanzierungslimit from projekt where titel = ?");
            preparedStatement.setString(1, titel);
            ResultSet resultSet = preparedStatement.executeQuery();
            Double result = resultSet.getDouble(1);
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public Double findenTotalSpendeVomProjekt(Integer kennung) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select sum(spendenbetrag) from spenden where projekt = ?");
            preparedStatement.setInt(1, kennung);
            ResultSet resultSet = preparedStatement.executeQuery();
            Double result = resultSet.getDouble(1);
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public String findenStatusVomProjekt(Integer kennung) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select status from projekt where kennung = ?");
            preparedStatement.setInt(1, kennung);
            ResultSet resultSet = preparedStatement.executeQuery();
            String result = resultSet.getString(1);
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public List<Spenden> findenSpenderVomProjekt(Integer kennung) throws StoreException
    {
        try
        {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from spenden where projekt = ?");
            preparedStatement.setInt(1, kennung);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Spenden> result = new ArrayList<>();
            while (resultSet.next())
            {
                Spenden neuSpenden = new Spenden(resultSet.getInt(2),
                        resultSet.getString(1),
                        resultSet.getDouble(3),
                        resultSet.getString(4));
                result.add(neuSpenden);
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
