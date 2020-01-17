package de.unidue.inf.is.stores;

import java.io.Closeable;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.unidue.inf.is.domain.Schreibt;
import de.unidue.inf.is.domain.User;
import de.unidue.inf.is.utils.DBUtil;



public final class UserStore implements Closeable {

    private Connection connection;
    private boolean complete;


    public UserStore() throws StoreException {
        try {
            connection = DBUtil.getExternalConnection();
            connection.setAutoCommit(false);
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public void addUser(User userToAdd) throws StoreException {
        try {
            PreparedStatement preparedStatement = connection
                            .prepareStatement("insert into benutzer (name,email,beschreibung) values (?, ?, ?)");
            preparedStatement.setString(1, userToAdd.getFirstname()+
                    userToAdd.getLastname());
            preparedStatement.setString(2, userToAdd.getEmail());
            preparedStatement.setString(3, userToAdd.getExplanation());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            throw new StoreException(e);
        }
    }
    public List<Schreibt> findenSchreibtVonProjekt(Integer kennung) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select * from schreibt where projekt = ?");
            preparedStatement.setInt(1,kennung);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Schreibt> result = new ArrayList<>();
            while (resultSet.next())
            {
                Schreibt neuSchreibt = new Schreibt(resultSet.getInt(2),
                        resultSet.getString(1),
                        resultSet.getInt(3));
                result.add(neuSchreibt);
            }
            return result;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public String findenTextVomKommentar(Integer id) throws StoreException
    {
        try {
            PreparedStatement preparedStatement = connection
                    .prepareStatement("select text from kommentar where id = ?");
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            String resultText = resultSet.getString(1);
            return resultText;
        }catch (SQLException e)
        {
            throw new StoreException(e);
        }
    }
    public HashMap<String,String> werSagteWas(List<Schreibt> result)
    {
        if(result != null)
        {
            HashMap<String,String> resultInMap = new HashMap<>();
            for (int j = 0; j < result.size(); j++)
            {
                resultInMap.put(result.get(j).getBenutzer(),
                        findenTextVomKommentar(result.get(j).getKommentarId()));
            }
            return resultInMap;
        }
        else
        {
            return null;
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
