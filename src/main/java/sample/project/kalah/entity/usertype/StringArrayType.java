package sample.project.kalah.entity.usertype;

import java.io.Serializable;
import java.sql.Array;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Arrays;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.usertype.UserType;

public class StringArrayType implements UserType
{
    private static final int[] SQL_TYPES = new int[]{Types.ARRAY};

    @Override
    public int[] sqlTypes()
    {
        return SQL_TYPES;
    }

    @Override
    public Class<String[]> returnedClass()
    {
        return String[].class;
    }

    @Override
    public boolean equals(Object o1, Object o2) throws HibernateException
    {
        if (o1 == null && o2 == null)
            return true;
        else if (o1 == null || o2 == null)
            return false;
        return Arrays.equals((String[]) o1, (String[]) o2);
    }

    @Override
    public Object nullSafeGet(ResultSet resultSet, String[] names, SharedSessionContractImplementor sessionImplementor, Object owner) throws HibernateException, SQLException
    {
        Array rsArray = resultSet.getArray(names[0]);
        if (rsArray == null)
        {
            return null;
        }
        String[] array = (String[]) rsArray.getArray();
        return array;
    }

    @Override
    public void nullSafeSet(PreparedStatement preparedStatement, Object value, int i, SharedSessionContractImplementor sessionImplementor) throws HibernateException, SQLException
    {
        if (value == null)
        {
            preparedStatement.setNull(i, SQL_TYPES[0]);
        }
        else
        {
            String[] castObject = (String[]) value;
            Array array = sessionImplementor.connection().createArrayOf("text", castObject);
            preparedStatement.setArray(i, array);
        }
    }

    @Override
    public Object deepCopy(Object o) throws HibernateException
    {
        if (o == null)
        {
            return null;
        }
        else if (o instanceof String[])
        {
            String[] array = (String[]) o;
            return array.clone();
        }
        else
        {
            throw new IllegalArgumentException("Invalid type to copy: " + o.getClass().getName());
        }
    }

    @Override
    public boolean isMutable()
    {
        return true;
    }

    @Override
    public int hashCode(Object o) throws HibernateException
    {
        return o.hashCode();
    }

    @Override
    public Serializable disassemble(Object o) throws HibernateException
    {
        return (Serializable) o;
    }

    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }
}
